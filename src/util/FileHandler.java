package util;

import model.*;
import service.*;
import java.io.*;
import java.util.Map;

public class FileHandler {

    private static final String DATA_DIR      = "data/";
    private static final String VEHICLES_FILE = DATA_DIR + "vehicles.dat";
    private static final String BIDDERS_FILE  = DATA_DIR + "bidders.dat";
    private static final String AUCTIONS_FILE = DATA_DIR + "auctions.dat";

    public FileHandler() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();
    }

    @SuppressWarnings("unchecked")
    public void saveAll(VehicleService vs, BidderService bs, AuctionService as) {
        saveObject(vs.getVehicleMap(), VEHICLES_FILE, "Vehicles");
        saveObject(bs.getBidderMap(),  BIDDERS_FILE,  "Bidders");
        saveObject(as.getAuctionMap(), AUCTIONS_FILE, "Auctions");
    }
    private void saveObject(Object obj, String filePath, String label) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(obj);
            System.out.println("  [Saved] " + label + " → " + filePath);
        } catch (IOException e) {
            System.out.println("  [Error] Could not save " + label + ": " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadAll(VehicleService vs, BidderService bs, AuctionService as) {
        Map<String, Vehicle> vehicles = (Map<String, Vehicle>)
                loadObject(VEHICLES_FILE, "Vehicles");
        if (vehicles != null) vs.loadVehicles(vehicles);

        Map<String, Bidder> bidders = (Map<String, Bidder>)
                loadObject(BIDDERS_FILE, "Bidders");
        if (bidders != null) bs.loadBidders(bidders);

        Map<String, Auction> auctions = (Map<String, Auction>)
                loadObject(AUCTIONS_FILE, "Auctions");
        if (auctions != null) as.loadAuctions(auctions);
    }

    private Object loadObject(String filePath, String label) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("  [Info] No saved data found for " + label + ". Starting fresh.");
            return null;
        }
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = ois.readObject();
            System.out.println("  [Loaded] " + label + " ← " + filePath);
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("  [Error] Could not load " + label + ": " + e.getMessage());
            return null;
        }
    }

    public void exportAuctionResult(Auction auction) {
        String filename = DATA_DIR + "result_" + auction.getAuctionId() + ".txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("===== AUCTION RESULT =====");
            pw.println("Auction ID : " + auction.getAuctionId());
            pw.println("Vehicle    : " + auction.getVehicleId());
            pw.println("Status     : " + auction.getStatus());
            pw.printf ("Starting   : Rs %.2f%n", auction.getStartingPrice());
            pw.printf ("Winning Bid: Rs %.2f%n", auction.getWinningAmount());
            pw.println("Winner     : " + auction.getWinnerName()
                    + " (" + auction.getWinnerId() + ")");
            pw.println("Total Bids : " + auction.getTotalBids());
            pw.println("--------------------------");
            pw.println("BID HISTORY:");
            for (Bid b : auction.getAllBidsSorted()) {
                pw.println("  " + b);
            }
            pw.println("==========================");
            System.out.println("  [Exported] Result saved to " + filename);
        } catch (IOException e) {
            System.out.println("  [Error] Could not export result: " + e.getMessage());
        }
    }
}
