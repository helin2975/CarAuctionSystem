package model;

import java.io.Serializable;
public abstract class Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;
    private String vehicleId;
    private String make;
    private String model;
    private int year;
    private double basePrice;
    private String color;
    private int mileage;
    private boolean isAvailable;
    public Vehicle(String vehicleId, String make, String model, int year,
                   double basePrice, String color, int mileage) {
        if (vehicleId == null || vehicleId.trim().isEmpty())
            throw new IllegalArgumentException("Vehicle ID cannot be empty.");
        if (basePrice < 0)
            throw new IllegalArgumentException("Base price cannot be negative.");
        if (year < 1886 || year > 2025)
            throw new IllegalArgumentException("Invalid vehicle year.");
        this.vehicleId  = vehicleId;
        this.make       = make;
        this.model      = model;
        this.year       = year;
        this.basePrice  = basePrice;
        this.color      = color;
        this.mileage    = mileage;
        this.isAvailable = true;
    }
    public abstract double calculateRegistrationFee();
    public abstract String getVehicleType();
    public abstract String getSpecifications();
    public String getVehicleDetails() {
        return String.format(
            "[%s] %s %s %d | Color: %s | Mileage: %d km | Base Price: Rs %.2f | Fee: Rs %.2f",
            getVehicleType(), make, model, year, color, mileage, basePrice, calculateRegistrationFee()
        );
    }
    public String getVehicleId()  { return vehicleId; }
    public String getMake()       { return make; }
    public String getModel()      { return model; }
    public int    getYear()       { return year; }
    public double getBasePrice()  { return basePrice; }
    public String getColor()      { return color; }
    public int    getMileage()    { return mileage; }
    public boolean isAvailable()  { return isAvailable; }
    public void setBasePrice(double basePrice) {
        if (basePrice < 0) throw new IllegalArgumentException("Price cannot be negative.");
        this.basePrice = basePrice;
    }
    public void setAvailable(boolean available) { this.isAvailable = available; }
    @Override
    public String toString() {
        return getVehicleDetails();
    }
}
