package interfaces;

public interface Payable {
    boolean processPayment(double amount);
    double  getWalletBalance();
    void    addFunds(double amount);
}
