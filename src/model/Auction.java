package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class Auction  {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private String        auctionId;
    private String        vehicleId;
    private String        vehicleDescription;
    private double        startingPrice;
    private double        minimumBidIncrement;
    private double        currentHighestBid;
    private String        currentHighestBidderId;
    private String        currentHighestBidderName;
    private AuctionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private String        winnerId;
    private String        winnerName;
    private double        winningAmount;

    private PriorityQueue<Bid> bids;
    private List<String>       registeredBidderIds;

    public Auction(String auctionId, String vehicleId, String vehicleDescription,
                   double startingPrice, double minimumBidIncrement) {
        if (startingPrice <= 0)
            throw new IllegalArgumentException("Starting price must be positive.");
        if (minimumBidIncrement <= 0)
            throw new IllegalArgumentException("Minimum bid increment must be positive.");

        this.auctionId            = auctionId;
        this.vehicleId            = vehicleId;
        this.vehicleDescription   = vehicleDescription;
        this.startingPrice        = startingPrice;
        this.minimumBidIncrement  = minimumBidIncrement;
        this.currentHighestBid    = startingPrice;
        this.status               = AuctionStatus.UPCOMING;
        this.createdAt            = LocalDateTime.now();
        this.bids                 = new PriorityQueue<>();
        this.registeredBidderIds  = new ArrayList<>();
    }

    public void openAuction() {
        this.status   = AuctionStatus.OPEN;
        this.openedAt = LocalDateTime.now();
    }

    public void closeAuction() {
        this.status   = AuctionStatus.CLOSED;
        this.closedAt = LocalDateTime.now();
    }

    public void cancelAuction() {
        this.status = AuctionStatus.CANCELLED;
    }

    public void declareWinner() {
        if (bids.isEmpty()) {
            this.status = AuctionStatus.CANCELLED;
            return;
        }
        Bid topBid   = bids.peek();
        this.winnerId       = topBid.getBidderId();
        this.winnerName     = topBid.getBidderName();
        this.winningAmount  = topBid.getAmount();
        this.status         = AuctionStatus.COMPLETED;
    }

    public void addBid(Bid bid) {
        bids.add(bid);
        this.currentHighestBid        = bid.getAmount();
        this.currentHighestBidderId   = bid.getBidderId();
        this.currentHighestBidderName = bid.getBidderName();
    }

    public void registerBidder(String bidderId) {
        if (!registeredBidderIds.contains(bidderId))
            registeredBidderIds.add(bidderId);
    }

    public boolean isBidderRegistered(String bidderId) {
        return registeredBidderIds.contains(bidderId);
    }

    public List<Bid> getAllBidsSorted() {
        List<Bid> sorted = new ArrayList<>(bids);
        sorted.sort((a, b) -> Double.compare(b.getAmount(), a.getAmount()));
        return sorted;
    }

    public String        getAuctionId()               { return auctionId; }
    public String        getVehicleId()               { return vehicleId; }
    public String        getVehicleDescription()      { return vehicleDescription; }
    public double        getStartingPrice()            { return startingPrice; }
    public double        getMinimumBidIncrement()      { return minimumBidIncrement; }
    public double        getCurrentHighestBid()        { return currentHighestBid; }
    public String        getCurrentHighestBidderId()   { return currentHighestBidderId; }
    public String        getCurrentHighestBidderName() { return currentHighestBidderName; }
    public AuctionStatus getStatus()                  { return status; }
    public LocalDateTime getCreatedAt()               { return createdAt; }
    public String        getWinnerId()                { return winnerId; }
    public String        getWinnerName()              { return winnerName; }
    public double        getWinningAmount()            { return winningAmount; }
    public int           getTotalBids()               { return bids.size(); }
    public List<String>  getRegisteredBidderIds()     { return registeredBidderIds; }

    @Override
    public String toString() {
        return String.format(
            "Auction[%s] Vehicle: %s | Start: Rs %.2f | Current: Rs %.2f | Status: %s | Bids: %d",
            auctionId, vehicleDescription, startingPrice, currentHighestBid, status, bids.size()
        );
    }
}
