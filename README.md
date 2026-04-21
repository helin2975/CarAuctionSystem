# Car Auction Management System

A terminal-based Java application that simulates a fully operational vehicle auction platform. Administrators can manage vehicle inventory and auction lifecycles, while registered bidders compete in live bidding sessions to acquire vehicles at market-driven prices.

---

## 👥 Team Members

| Name | Role |
|------|------|
| Helin Chaudhari | Java Developer |
| Zaid Gajdhar | Java Developer |

---

## 📐 Project Overview

The system models a real-world auction ecosystem with two roles:
- **Admin** — registers vehicles, creates and manages auctions, declares winners
- **Bidder** — registers with a wallet balance, places bids on open auctions

---

## 🛠️ Tech Stack

- **Language:** Java (JDK 17+)
- **Type:** Terminal / Console Based
- **Storage:** Java Object Serialization (`.dat` files)
- **Collections:** HashMap, PriorityQueue, ArrayList

---

## 📦 Project Structure
CarAuctionSystem/
├── src/
│   ├── model/          → Vehicle, Car, Truck, Motorcycle, Bid, Bidder, Auction
│   ├── interfaces/     → Biddable, Payable, Auctionable
│   ├── exceptions/     → BidTooLowException, AuctionClosedException, InvalidBidderException
│   ├── service/        → VehicleService, BidderService, AuctionService, ReportService
│   ├── util/           → FileHandler, InputValidator
│   └── Main.java
├── out/                → Compiled .class files (auto-generated, not pushed)
├── data/               → Saved .dat and result .txt files (auto-generated, not pushed)
└── .gitignore

---

## 🧠 OOP Concepts Covered

| Concept | Where Used |
|--------|------------|
| Abstraction | `Vehicle.java` — abstract base class |
| Inheritance | `Car`, `Truck`, `Motorcycle` extend `Vehicle` |
| Polymorphism | `calculateRegistrationFee()` behaves differently per vehicle type |
| Encapsulation | All fields private with getters/setters |
| Interfaces | `Biddable`, `Payable`, `Auctionable` |
| Custom Exceptions | `BidTooLowException`, `AuctionClosedException`, `InvalidBidderException` |
| Generics | `HashMap<String, Auction>`, `PriorityQueue<Bid>` |
| Serialization | `FileHandler.java` saves and loads all data |
| Enumerations | `AuctionStatus` — UPCOMING, OPEN, CLOSED, COMPLETED |
| Collections | HashMap, PriorityQueue, ArrayList used throughout |

---

## ▶️ How to Run

**Step 1 — Create output folder**
```bash
mkdir out
```

**Step 2 — Compile**
```bash
javac -d out -sourcepath src src/Main.java
```

**Step 3 — Run**
```bash
java -cp out Main
```

---

## 🔐 Admin Login

| Field | Value |
|-------|-------|
| Username | `admin` |
| Password | `admin123` |

---

## 📁 Data Persistence

All auction data, bidder profiles, and vehicle records are automatically saved to the `data/` folder when the program exits and reloaded on the next run using Java Serialization.