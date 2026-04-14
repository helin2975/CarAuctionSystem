package exceptions;

public class BidTooLowException extends Exception {
    private double bidAmount;
    private double requiredAmount;

    public BidTooLowException(double bidAmount, double requiredAmount) {
        super(String.format(
            "Bid of Rs %.2f is too low. Minimum required: Rs %.2f",
            bidAmount, requiredAmount));
        this.bidAmount      = bidAmount;
        this.requiredAmount = requiredAmount;
    }

    public double getBidAmount()      { return bidAmount; }
    public double getRequiredAmount() { return requiredAmount; }
}
