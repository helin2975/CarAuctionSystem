package service;

import exceptions.*;
import interfaces.Auctionable;
import model.*;

import java.util.*;
import java.util.stream.Collectors;

public class AuctionService implements Auctionable {

    private Map<String, Auction> auctionMap = new HashMap<>();
    private int auctionCounter = 1;
    private int bidCounter = 1;

    private VehicleService vehicleService;
    private BidderService bidderService;

    public AuctionService(VehicleService vehicleService, BidderService bidderService) {
        this.vehicleService = vehicleService;
        this.bidderService = bidderService;
    }

    public String generateAuctionId() {
        return "AUC" + String.format("%03d", auctionCounter++);
    }

    public String generateBidId() {
        return "BID_R" + String.format("%04d", bidCounter++);
    }

    public Auction createAuction(String vehicleId, double startingPrice,
            double minIncrement) throws Exception {
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);
        if (vehicle == null)
            throw new Exception("Vehicle not found: " + vehicleId);
        if (!vehicle.isAvailable())
            throw new Exception("Vehicle is already in an auction: " + vehicleId);

        String auctionId = generateAuctionId();
        Auction auction = new Auction(auctionId, vehicleId,
                vehicle.getVehicleDetails(), startingPrice, minIncrement);
        auctionMap.put(auctionId, auction);
        vehicle.setAvailable(false);
        System.out.println("  Auction created: " + auctionId + " for vehicle " + vehicleId);
        return auction;
    }

    @Override
    public void openAuction(String auctionId) {
        Auction auction = getAuctionOrThrow(auctionId);
        if (auction.getStatus() != AuctionStatus.UPCOMING)
            throw new IllegalStateException("Auction is not in UPCOMING state.");
        auction.openAuction();
        System.out.println("  Auction " + auctionId + " is now OPEN.");
    }

    @Override
    public void closeAuction(String auctionId) {
        Auction auction = getAuctionOrThrow(auctionId);
        if (auction.getStatus() != AuctionStatus.OPEN)
            throw new IllegalStateException("Auction is not OPEN.");
        auction.closeAuction();
        System.out.println("  Auction " + auctionId + " is now CLOSED.");
    }

    @Override
    public void declareWinner(String auctionId) {
        Auction auction = getAuctionOrThrow(auctionId);
        if (auction.getStatus() != AuctionStatus.CLOSED)
            throw new IllegalStateException("Auction must be CLOSED before declaring winner.");

        auction.declareWinner();

        if (auction.getStatus() == AuctionStatus.COMPLETED) {

            Bidder winner = bidderService.getBidder(auction.getWinnerId());
            if (winner != null) {
                winner.deductBalance(auction.getWinningAmount());
                winner.addAuctionWon(auctionId);
            }

            Vehicle v = vehicleService.getVehicle(auction.getVehicleId());
            if (v != null)
                v.setAvailable(false);

            System.out.printf("  Winner declared: %s with bid Rs %.2f%n",
                    auction.getWinnerName(), auction.getWinningAmount());
        } else {

            Vehicle v = vehicleService.getVehicle(auction.getVehicleId());
            if (v != null)
                v.setAvailable(true);
            System.out.println("  No bids placed. Auction cancelled.");
        }
    }

    @Override
    public void cancelAuction(String auctionId) {
        Auction auction = getAuctionOrThrow(auctionId);
        auction.cancelAuction();
        Vehicle v = vehicleService.getVehicle(auction.getVehicleId());
        if (v != null)
            v.setAvailable(true);
        System.out.println("  Auction " + auctionId + " cancelled.");
    }

    public void placeBid(String auctionId, String bidderId, double amount)
            throws BidTooLowException, AuctionClosedException, InvalidBidderException {

        Auction auction = getAuctionOrThrow(auctionId);

        if (auction.getStatus() != AuctionStatus.OPEN)
            throw new AuctionClosedException(auctionId);

        if (!bidderService.bidderExists(bidderId))
            throw new InvalidBidderException("Bidder not found: " + bidderId);

        Bidder bidder = bidderService.getBidder(bidderId);

        auction.registerBidder(bidderId);

        double required = auction.getCurrentHighestBid() + auction.getMinimumBidIncrement();
        if (amount < required)
            throw new BidTooLowException(amount, required);

        if (bidder.getWalletBalance() < amount)
            throw new InvalidBidderException(
                    "Insufficient balance. Wallet: Rs " + bidder.getWalletBalance()
                            + ", Bid: Rs " + amount);

        String bidId = generateBidId();
        Bid bid = new Bid(bidId, bidderId, bidder.getName(), auctionId, amount);
        auction.addBid(bid);
        bidder.addBidRecord(bid.toString());

        System.out.printf("  Bid placed: %s by %s - Rs %.2f%n",
                bidId, bidder.getName(), amount);
    }

    public Auction getAuction(String auctionId) {
        return auctionMap.get(auctionId);
    }

    public List<Auction> getAllAuctions() {
        return new ArrayList<>(auctionMap.values());
    }

    public List<Auction> getOpenAuctions() {
        return auctionMap.values().stream()
                .filter(a -> a.getStatus() == AuctionStatus.OPEN)
                .collect(Collectors.toList());
    }

    public List<Auction> getCompletedAuctions() {
        return auctionMap.values().stream()
                .filter(a -> a.getStatus() == AuctionStatus.COMPLETED)
                .collect(Collectors.toList());
    }

    public List<Auction> getUpcomingAuctions() {
        return auctionMap.values().stream()
                .filter(a -> a.getStatus() == AuctionStatus.UPCOMING)
                .collect(Collectors.toList());
    }

    private Auction getAuctionOrThrow(String auctionId) {
        Auction a = auctionMap.get(auctionId);
        if (a == null)
            throw new NoSuchElementException("Auction not found: " + auctionId);
        return a;
    }

    public void loadAuctions(Map<String, Auction> loaded) {
        this.auctionMap = loaded;
        this.auctionCounter = loaded.size() + 1;
    }

    public Map<String, Auction> getAuctionMap() {
        return auctionMap;
    }
}