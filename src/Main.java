import exceptions.*;
import model.*;
import service.*;
import util.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static VehicleService vehicleService = new VehicleService();
    private static BidderService bidderService = new BidderService();
    private static AuctionService auctionService = new AuctionService(vehicleService, bidderService);
    private static ReportService reportService = new ReportService(auctionService, vehicleService, bidderService);
    private static FileHandler fileHandler = new FileHandler();

    private static Scanner scanner = new Scanner(System.in);
    private static InputValidator input = new InputValidator(scanner);

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public static void main(String[] args) {
        banner();
        fileHandler.loadAll(vehicleService, bidderService, auctionService);
        mainMenu();
        fileHandler.saveAll(vehicleService, bidderService, auctionService);
        System.out.println("\n  Data saved. Goodbye!\n");
        scanner.close();
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\n" + sep('=', 50));
            System.out.println("        CAR AUCTION MANAGEMENT SYSTEM");
            System.out.println(sep('=', 50));
            System.out.println("  1. Admin Login");
            System.out.println("  2. Bidder Login");
            System.out.println("  3. Register as Bidder");
            System.out.println("  4. View Open Auctions");
            System.out.println("  0. Exit");
            System.out.println(sep('-', 50));

            int choice = input.readIntInRange("  Enter choice: ", 0, 4);
            switch (choice) {
                case 1 -> adminLogin();
                case 2 -> bidderLogin();
                case 3 -> registerBidder();
                case 4 -> reportService.printOpenAuctions();
                case 0 -> {
                    return;
                }
            }
        }
    }

    private static void adminLogin() {
        System.out.println("\n  --- Admin Login ---");
        String user = input.readString("  Username: ");
        String pass = input.readString("  Password: ");
        if (user.equals(ADMIN_USERNAME) && pass.equals(ADMIN_PASSWORD)) {
            System.out.println("  Login successful. Welcome, Admin!");
            adminMenu();
        } else {
            System.out.println("  Invalid credentials.");
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n" + sep('=', 50));
            System.out.println("           ADMIN MENU");
            System.out.println(sep('=', 50));
            System.out.println("  --- Vehicle Management ---");
            System.out.println("  1.  Add Car");
            System.out.println("  2.  Add Truck");
            System.out.println("  3.  Add Motorcycle");
            System.out.println("  4.  View All Vehicles");
            System.out.println("  5.  Remove Vehicle");
            System.out.println("  --- Auction Management ---");
            System.out.println("  6.  Create Auction");
            System.out.println("  7.  Open Auction");
            System.out.println("  8.  Close Auction");
            System.out.println("  9.  Declare Winner");
            System.out.println("  10. Cancel Auction");
            System.out.println("  11. View All Auctions");
            System.out.println("  12. View Auction Detail");
            System.out.println("  13. Export Auction Result");
            System.out.println("  --- Reports ---");
            System.out.println("  14. View All Bidders");
            System.out.println("  15. View Completed Auctions");
            System.out.println("  16. Save Data Now");
            System.out.println("  0.  Logout");
            System.out.println(sep('-', 50));

            int choice = input.readIntInRange("  Enter choice: ", 0, 16);
            switch (choice) {
                case 1 -> addCar();
                case 2 -> addTruck();
                case 3 -> addMotorcycle();
                case 4 -> reportService.printAllVehicles();
                case 5 -> removeVehicle();
                case 6 -> createAuction();
                case 7 -> openAuction();
                case 8 -> closeAuction();
                case 9 -> declareWinner();
                case 10 -> cancelAuction();
                case 11 -> reportService.printAllAuctions();
                case 12 -> viewAuctionDetail();
                case 13 -> exportResult();
                case 14 -> reportService.printAllBidders();
                case 15 -> reportService.printCompletedAuctions();
                case 16 -> fileHandler.saveAll(vehicleService, bidderService, auctionService);
                case 0 -> {
                    System.out.println("  Admin logged out.");
                    return;
                }
            }
        }
    }

    private static void addCar() {
        System.out.println("\n  --- Add Car ---");
        try {
            String id = input.readString("  Vehicle ID (e.g. V001): ");
            String make = input.readString("  Make (e.g. Toyota): ");
            String model = input.readString("  Model (e.g. Camry): ");
            int year = input.readIntInRange("  Year: ", 1990, 2025);
            double price = input.readDouble("  Base Price (Rs): ");
            String color = input.readString("  Color: ");
            int km = input.readInt("  Mileage (km): ");
            int doors = input.readIntInRange("  No. of Doors: ", 2, 6);
            String fuel = input.readString("  Fuel Type (Petrol/Diesel/Electric/Hybrid): ");
            String trans = input.readString("  Transmission (Manual/Automatic): ");

            Car car = new Car(id, make, model, year, price, color, km, doors, fuel, trans);
            vehicleService.addVehicle(car);
            System.out.println("  Car added successfully.");
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private static void addTruck() {
        System.out.println("\n  --- Add Truck ---");
        try {
            String id = input.readString("  Vehicle ID: ");
            String make = input.readString("  Make: ");
            String model = input.readString("  Model: ");
            int year = input.readIntInRange("  Year: ", 1990, 2025);
            double price = input.readDouble("  Base Price (Rs): ");
            String color = input.readString("  Color: ");
            int km = input.readInt("  Mileage (km): ");
            double payload = input.readDouble("  Payload Capacity (tons): ");
            int axles = input.readIntInRange("  Number of Axles: ", 2, 8);
            boolean fridge = input.readYesNo("  Has Refrigeration?");

            Truck truck = new Truck(id, make, model, year, price, color, km, payload, axles, fridge);
            vehicleService.addVehicle(truck);
            System.out.println("  Truck added successfully.");
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private static void addMotorcycle() {
        System.out.println("\n  --- Add Motorcycle ---");
        try {
            String id = input.readString("  Vehicle ID: ");
            String make = input.readString("  Make: ");
            String model = input.readString("  Model: ");
            int year = input.readIntInRange("  Year: ", 1990, 2025);
            double price = input.readDouble("  Base Price (Rs): ");
            String color = input.readString("  Color: ");
            int km = input.readInt("  Mileage (km): ");
            int cc = input.readInt("  Engine CC: ");
            String type = input.readString("  Bike Type (Sport/Cruiser/Touring/Off-road): ");
            boolean sidecar = input.readYesNo("  Has Sidecar?");

            Motorcycle moto = new Motorcycle(id, make, model, year, price, color, km, cc, type, sidecar);
            vehicleService.addVehicle(moto);
            System.out.println("  Motorcycle added successfully.");
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private static void removeVehicle() {
        String id = input.readString("  Enter Vehicle ID to remove: ");
        if (vehicleService.removeVehicle(id))
            System.out.println("  Vehicle removed.");
        else
            System.out.println("  Vehicle not found.");
    }

    private static void createAuction() {
        System.out.println("\n  --- Create Auction ---");
        reportService.printAvailableVehicles();
        try {
            String vehicleId = input.readString("  Vehicle ID to auction: ");
            double startPrice = input.readDouble("  Starting Price (Rs): ");
            double minIncr = input.readDouble("  Minimum Bid Increment (Rs): ");
            Auction a = auctionService.createAuction(vehicleId, startPrice, minIncr);
            System.out.println("  Auction created: " + a.getAuctionId());
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private static void openAuction() {
        String id = input.readString("  Enter Auction ID to open: ");
        try {
            auctionService.openAuction(id);
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private static void closeAuction() {
        String id = input.readString("  Enter Auction ID to close: ");
        try {
            auctionService.closeAuction(id);
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private static void declareWinner() {
        String id = input.readString("  Enter Auction ID to declare winner: ");
        try {
            auctionService.declareWinner(id);
            Auction a = auctionService.getAuction(id);
            if (a != null && a.getStatus() == AuctionStatus.COMPLETED) {
                fileHandler.exportAuctionResult(a);
            }
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private static void cancelAuction() {
        String id = input.readString("  Enter Auction ID to cancel: ");
        try {
            auctionService.cancelAuction(id);
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private static void viewAuctionDetail() {
        String id = input.readString("  Enter Auction ID: ");
        reportService.printAuctionDetail(id);
    }

    private static void exportResult() {
        String id = input.readString("  Enter Auction ID to export: ");
        Auction a = auctionService.getAuction(id);
        if (a == null) {
            System.out.println("  Auction not found.");
            return;
        }
        fileHandler.exportAuctionResult(a);
    }

    private static void registerBidder() {
        System.out.println("\n  --- Register as Bidder ---");
        try {
            String name = input.readString("  Full Name: ");
            String email = input.readString("  Email: ");
            if (!input.isValidEmail(email)) {
                System.out.println("  Invalid email format.");
                return;
            }
            String phone = input.readString("  Phone (10 digits): ");
            if (!input.isValidPhone(phone)) {
                System.out.println("  Invalid phone number.");
                return;
            }
            String pass = input.readString("  Password: ");
            double balance = input.readDouble("  Initial Wallet Balance (Rs): ");

            Bidder b = bidderService.registerBidder(name, email, phone, pass, balance);
            System.out.println("  Registration successful! Your Bidder ID: " + b.getBidderId());
            System.out.println("  Please save your ID to login.");
        } catch (InvalidBidderException e) {
            System.out.println("  Registration failed: " + e.getMessage());
        }
    }

    private static void bidderLogin() {
        System.out.println("\n  --- Bidder Login ---");
        try {
            String id = input.readString("  Bidder ID: ");
            String pass = input.readString("  Password: ");
            Bidder b = bidderService.login(id, pass);
            System.out.println("  Welcome, " + b.getName() + "!");
            bidderMenu(b);
            bidderService.logout();
        } catch (InvalidBidderException e) {
            System.out.println("  Login failed: " + e.getMessage());
        }
    }

    private static void bidderMenu(Bidder bidder) {
        while (true) {
            System.out.println("\n" + sep('=', 50));
            System.out.printf("           BIDDER MENU — %s%n", bidder.getName());
            System.out.println(sep('=', 50));
            System.out.println("  1. View Open Auctions");
            System.out.println("  2. View Auction Detail");
            System.out.println("  3. Place Bid");
            System.out.println("  4. My Profile & Bid History");
            System.out.println("  5. Add Funds to Wallet");
            System.out.println("  6. View All Vehicles");
            System.out.println("  0. Logout");
            System.out.println(sep('-', 50));

            int choice = input.readIntInRange("  Enter choice: ", 0, 6);
            switch (choice) {
                case 1 -> reportService.printOpenAuctions();
                case 2 -> viewAuctionDetail();
                case 3 -> placeBid(bidder);
                case 4 -> reportService.printBidderProfile(bidder);
                case 5 -> addFunds(bidder);
                case 6 -> reportService.printAvailableVehicles();
                case 0 -> {
                    System.out.println("  Logged out.");
                    return;
                }
            }
        }
    }

    private static void placeBid(Bidder bidder) {
        System.out.println("\n  --- Place Bid ---");
        reportService.printOpenAuctions();

        List<Auction> open = auctionService.getOpenAuctions();
        if (open.isEmpty()) {
            System.out.println("  No open auctions available.");
            return;
        }

        String auctionId = input.readString("  Enter Auction ID: ");
        Auction auction = auctionService.getAuction(auctionId);
        if (auction == null) {
            System.out.println("  Auction not found.");
            return;
        }

        System.out.printf("  Current highest bid: Rs %.2f%n", auction.getCurrentHighestBid());
        System.out.printf("  Minimum next bid   : Rs %.2f%n",
                auction.getCurrentHighestBid() + auction.getMinimumBidIncrement());
        System.out.printf("  Your wallet balance: Rs %.2f%n", bidder.getWalletBalance());

        double amount = input.readDouble("  Enter your bid amount (Rs): ");

        try {
            auctionService.placeBid(auctionId, bidder.getBidderId(), amount);
            System.out.printf("  Bid of Rs %.2f placed successfully!%n", amount);
        } catch (BidTooLowException e) {
            System.out.println("  " + e.getMessage());
        } catch (AuctionClosedException e) {
            System.out.println("  " + e.getMessage());
        } catch (InvalidBidderException e) {
            System.out.println("  " + e.getMessage());
        }
    }

    private static void addFunds(Bidder bidder) {
        double amount = input.readDouble("  Amount to add (Rs): ");
        bidder.addBalance(amount);
        System.out.printf("  Rs %.2f added. New balance: Rs %.2f%n",
                amount, bidder.getWalletBalance());
    }

    private static String sep(char c, int len) {
        return String.valueOf(c).repeat(len);
    }

    private static void banner() {
        System.out.println(sep('=', 55));
        System.out.println("       CAR AUCTION MANAGEMENT SYSTEM");
        System.out.println("       Java Core OOP Project");
        System.out.println(sep('=', 55));
        System.out.println("  Loading saved data...");
    }
}
