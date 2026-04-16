package service;

import model.*;

import java.util.List;

public class ReportService {

    private AuctionService auctionService;
    private VehicleService vehicleService;
    private BidderService bidderService;

    public ReportService(AuctionService auctionService,
            VehicleService vehicleService,
            BidderService bidderService) {
        this.auctionService = auctionService;
        this.vehicleService = vehicleService;
        this.bidderService = bidderService;
    }

    private String line(char c, int len) {
        return String.valueOf(c).repeat(len);
    }

    public void printAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        System.out.println("\n" + line('=', 70));
        System.out.println("  ALL REGISTERED VEHICLES (" + vehicles.size() + ")");
        System.out.println(line('=', 70));
        if (vehicles.isEmpty()) {
            System.out.println("  No vehicles registered.");
        } else {
            for (Vehicle v : vehicles) {
                System.out.println("  " + v.getVehicleDetails());
                System.out.printf("  Available: %s | Reg. Fee: Rs %.2f%n",
                        v.isAvailable() ? "Yes" : "No",
                        v.calculateRegistrationFee());
                System.out.println(line('-', 70));
            }
        }
    }

    public void printAvailableVehicles() {
        List<Vehicle> vehicles = vehicleService.getAvailableVehicles();
        System.out.println("\n" + line('=', 70));
        System.out.println("  AVAILABLE VEHICLES (" + vehicles.size() + ")");
        System.out.println(line('=', 70));
        if (vehicles.isEmpty()) {
            System.out.println("  No vehicles currently available.");
        } else {
            for (Vehicle v : vehicles) {
                System.out.println("  " + v.getVehicleDetails());
                System.out.println(line('-', 70));
            }
        }
    }

    public void printAllAuctions() {
        List<Auction> auctions = auctionService.getAllAuctions();
        System.out.println("\n" + line('=', 70));
        System.out.println("  ALL AUCTIONS (" + auctions.size() + ")");
        System.out.println(line('=', 70));
        if (auctions.isEmpty()) {
            System.out.println("  No auctions created yet.");
        } else {
            for (Auction a : auctions) {
                printAuctionSummary(a);
            }
        }
    }

    public void printOpenAuctions() {
        List<Auction> auctions = auctionService.getOpenAuctions();
        System.out.println("\n" + line('=', 70));
        System.out.println("  OPEN AUCTIONS (" + auctions.size() + ")");
        System.out.println(line('=', 70));
        if (auctions.isEmpty()) {
            System.out.println("  No auctions currently open.");
        } else {
            for (Auction a : auctions) {
                printAuctionSummary(a);
            }
        }
    }

    public void printAuctionDetail(String auctionId) {
        Auction a = auctionService.getAuction(auctionId);
        if (a == null) {
            System.out.println("  Auction not found: " + auctionId);
            return;
        }
        System.out.println("\n" + line('=', 70));
        System.out.println("  AUCTION DETAIL: " + auctionId);
        System.out.println(line('=', 70));
        System.out.println("  Vehicle    : " + a.getVehicleDescription());
        System.out.printf("  Start Price: Rs %.2f%n", a.getStartingPrice());
        System.out.printf("  Min. Incr. : Rs %.2f%n", a.getMinimumBidIncrement());
        System.out.printf("  Highest Bid: Rs %.2f%n", a.getCurrentHighestBid());
        System.out.println("  Status     : " + a.getStatus());
        System.out.println("  Total Bids : " + a.getTotalBids());

        if (a.getStatus() == AuctionStatus.COMPLETED) {
            System.out.println(line('-', 70));
            System.out.println("  WINNER: " + a.getWinnerName()
                    + " (" + a.getWinnerId() + ")");
            System.out.printf("  Winning Bid: Rs %.2f%n", a.getWinningAmount());
        }

        List<Bid> bids = a.getAllBidsSorted();
        if (!bids.isEmpty()) {
            System.out.println(line('-', 70));
            System.out.println("  BID HISTORY (highest first):");
            for (int i = 0; i < bids.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + bids.get(i));
            }
        }
        System.out.println(line('=', 70));
    }

    public void printAllBidders() {
        List<Bidder> bidders = bidderService.getAllBidders();
        System.out.println("\n" + line('=', 70));
        System.out.println("  ALL BIDDERS (" + bidders.size() + ")");
        System.out.println(line('=', 70));
        if (bidders.isEmpty()) {
            System.out.println("  No bidders registered.");
        } else {
            for (Bidder b : bidders) {
                System.out.println("  " + b);
                System.out.println("  Auctions Won: " + b.getAuctionsWon().size()
                        + " | Bid Records: " + b.getBidHistory().size());
                System.out.println(line('-', 70));
            }
        }
    }

    public void printBidderProfile(Bidder bidder) {
        System.out.println("\n" + line('=', 70));
        System.out.println("  MY PROFILE");
        System.out.println(line('=', 70));
        System.out.println("  ID     : " + bidder.getBidderId());
        System.out.println("  Name   : " + bidder.getName());
        System.out.println("  Email  : " + bidder.getEmail());
        System.out.println("  Phone  : " + bidder.getPhone());
        System.out.printf("  Wallet : Rs %.2f%n", bidder.getWalletBalance());

        if (!bidder.getAuctionsWon().isEmpty()) {
            System.out.println(line('-', 70));
            System.out.println("  AUCTIONS WON:");
            for (String aid : bidder.getAuctionsWon()) {
                Auction a = auctionService.getAuction(aid);
                if (a != null)
                    System.out.printf("    - %s | Vehicle: %s | Paid: Rs %.2f%n",
                            aid, a.getVehicleId(), a.getWinningAmount());
            }
        }

        if (!bidder.getBidHistory().isEmpty()) {
            System.out.println(line('-', 70));
            System.out.println("  MY BID HISTORY:");
            for (String rec : bidder.getBidHistory()) {
                System.out.println("    " + rec);
            }
        }
        System.out.println(line('=', 70));
    }

    public void printCompletedAuctions() {
        List<Auction> completed = auctionService.getCompletedAuctions();
        System.out.println("\n" + line('=', 70));
        System.out.println("  COMPLETED AUCTIONS (" + completed.size() + ")");
        System.out.println(line('=', 70));
        if (completed.isEmpty()) {
            System.out.println("  No completed auctions.");
        } else {
            for (Auction a : completed) {
                System.out.printf("  [%s] %s%n", a.getAuctionId(), a.getVehicleId());
                System.out.printf("  Winner: %s | Amount: Rs %.2f%n",
                        a.getWinnerName(), a.getWinningAmount());
                System.out.println(line('-', 70));
            }
        }
    }

    private void printAuctionSummary(Auction a) {
        System.out.printf("  [%s] Status: %-10s | Vehicle: %s%n",
                a.getAuctionId(), a.getStatus(), a.getVehicleId());
        System.out.printf("  Start: Rs %.2f | Current High: Rs %.2f | Bids: %d%n",
                a.getStartingPrice(), a.getCurrentHighestBid(), a.getTotalBids());
        if (a.getCurrentHighestBidderName() != null)
            System.out.println("  Leading: " + a.getCurrentHighestBidderName());
        System.out.println(line('-', 70));
    }
}
