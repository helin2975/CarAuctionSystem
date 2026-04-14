package exceptions;

public class AuctionClosedException extends Exception {
    private String auctionId;

    public AuctionClosedException(String auctionId) {
        super("Auction [" + auctionId + "] is not open for bidding.");
        this.auctionId = auctionId;
    }

    public String getAuctionId() { return auctionId; }
}
