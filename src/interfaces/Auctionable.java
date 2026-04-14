package interfaces;

public interface Auctionable {
    void openAuction(String auctionId);
    void closeAuction(String auctionId);
    void declareWinner(String auctionId);
    void cancelAuction(String auctionId);
}
