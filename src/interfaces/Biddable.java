package interfaces;


public interface Biddable {
    boolean placeBid(double amount) throws exceptions.BidTooLowException,
                                           exceptions.AuctionClosedException;
    double  getCurrentHighestBid();
    int     getTotalBids();
}
