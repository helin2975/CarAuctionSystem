package model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Bid implements Comparable<Bid>{

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private String       bidId;
    private String       bidderId;
    private String       bidderName;
    private String       auctionId;
    private double       amount;
    private LocalDateTime timestamp;

    public Bid(String bidId, String bidderId, String bidderName,
               String auctionId, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Bid amount must be positive.");
        this.bidId      = bidId;
        this.bidderId   = bidderId;
        this.bidderName = bidderName;
        this.auctionId  = auctionId;
        this.amount     = amount;
        this.timestamp  = LocalDateTime.now();
    }

    @Override
    public int compareTo(Bid other) {
        return Double.compare(other.amount, this.amount);
    }

    public String        getBidId()      { return bidId; }
    public String        getBidderId()   { return bidderId; }
    public String        getBidderName() { return bidderName; }
    public String        getAuctionId()  { return auctionId; }
    public double        getAmount()     { return amount; }
    public LocalDateTime getTimestamp()  { return timestamp; }

    @Override
    public String toString() {
        return String.format("Bid[%s] by %s — Rs %.2f at %s",
                bidId, bidderName, amount, timestamp.format(FMT));
    }
}
