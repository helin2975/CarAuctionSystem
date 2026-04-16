package service;

import exceptions.InvalidBidderException;
import model.Bidder;

import java.util.*;

public class BidderService implements interfaces.Payable {

    private Map<String, Bidder> bidderMap = new HashMap<>();
    private int bidderCounter = 1;

    private Bidder loggedInBidder = null;

    public String generateBidderId() {
        return "BID" + String.format("%03d", bidderCounter++);
    }

    public Bidder registerBidder(String name, String email,
            String phone, String password,
            double initialBalance) throws InvalidBidderException {
        for (Bidder b : bidderMap.values()) {
            if (b.getEmail().equalsIgnoreCase(email))
                throw new InvalidBidderException("Email already registered: " + email);
        }
        String id = generateBidderId();
        Bidder bidder = new Bidder(id, name, email, phone, password, initialBalance);
        bidderMap.put(id, bidder);
        System.out.println("  Bidder registered: " + id + " — " + name);
        return bidder;
    }

    public Bidder login(String bidderId, String password) throws InvalidBidderException {
        Bidder bidder = bidderMap.get(bidderId);
        if (bidder == null)
            throw new InvalidBidderException("Bidder ID not found: " + bidderId);
        if (!bidder.authenticate(password))
            throw new InvalidBidderException("Incorrect password for: " + bidderId);
        this.loggedInBidder = bidder;
        return bidder;
    }

    public void logout() {
        this.loggedInBidder = null;
    }

    public Bidder getBidder(String bidderId) {
        return bidderMap.get(bidderId);
    }

    public List<Bidder> getAllBidders() {
        return new ArrayList<>(bidderMap.values());
    }

    public boolean bidderExists(String bidderId) {
        return bidderMap.containsKey(bidderId);
    }

    public Bidder getLoggedInBidder() {
        return loggedInBidder;
    }

    @Override
    public boolean processPayment(double amount) {
        if (loggedInBidder == null)
            return false;
        try {
            loggedInBidder.deductBalance(amount);
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    @Override
    public double getWalletBalance() {
        if (loggedInBidder == null)
            return 0;
        return loggedInBidder.getWalletBalance();
    }

    @Override
    public void addFunds(double amount) {
        if (loggedInBidder == null)
            throw new IllegalStateException("No bidder logged in.");
        loggedInBidder.addBalance(amount);
    }

    public void loadBidders(Map<String, Bidder> loaded) {
        this.bidderMap = loaded;
        this.bidderCounter = loaded.size() + 1;
    }

    public Map<String, Bidder> getBidderMap() {
        return bidderMap;
    }
}
