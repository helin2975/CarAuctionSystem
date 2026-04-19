package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Bidder implements Serializable {
    private static final long serialVersionUID = 1L;
    private String bidderId;
    private String name;
    private String email;
    private String phone;
    private String password;
    private double walletBalance;
    private List<String> auctionsWon;
    private List<String> bidHistory;
    public Bidder(String bidderId, String name, String email,
                  String phone, String password, double walletBalance) {
        if (bidderId == null || bidderId.trim().isEmpty())
            throw new IllegalArgumentException("Bidder ID cannot be empty.");
        if (walletBalance < 0)
            throw new IllegalArgumentException("Wallet balance cannot be negative.");
        this.bidderId      = bidderId;
        this.name          = name;
        this.email         = email;
        this.phone         = phone;
        this.password      = password;
        this.walletBalance = walletBalance;
        this.auctionsWon   = new ArrayList<>();
        this.bidHistory    = new ArrayList<>();
    }
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
    public void deductBalance(double amount) {
        if (amount > walletBalance)
            throw new IllegalStateException("Insufficient wallet balance.");
        this.walletBalance -= amount;
    }
    public void addBalance(double amount) {
        if (amount < 0) throw new IllegalArgumentException("Cannot add negative amount.");
        this.walletBalance += amount;
    }
    public void addAuctionWon(String auctionId)  { auctionsWon.add(auctionId); }
    public void addBidRecord(String record)       { bidHistory.add(record); }
    public String       getBidderId()     { return bidderId; }
    public String       getName()         { return name; }
    public String       getEmail()        { return email; }
    public String       getPhone()        { return phone; }
    public double       getWalletBalance(){ return walletBalance; }
    public List<String> getAuctionsWon()  { return auctionsWon; }
    public List<String> getBidHistory()   { return bidHistory; }
    public void setName(String name)   { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    @Override
    public String toString() {
        return String.format("Bidder[%s] %s | Email: %s | Balance: Rs %.2f",
                bidderId, name, email, walletBalance);
    }
}
