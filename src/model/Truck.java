package model;

public class Truck extends Vehicle {


    private double payloadCapacityTons;
    private int    numberOfAxles;
    private boolean hasRefrigeration;

    public Truck(String vehicleId, String make, String model, int year,
                 double basePrice, String color, int mileage,
                 double payloadCapacityTons, int numberOfAxles, boolean hasRefrigeration) {
        super(vehicleId, make, model, year, basePrice, color, mileage);
        this.payloadCapacityTons = payloadCapacityTons;
        this.numberOfAxles       = numberOfAxles;
        this.hasRefrigeration    = hasRefrigeration;
    }

    @Override
    public double calculateRegistrationFee() {
        return getBasePrice() * 0.12;
    }

    @Override
    public String getVehicleType() {
        return "TRUCK";
    }

    @Override
    public String getSpecifications() {
        return String.format("Payload: %.1f tons | Axles: %d | Refrigerated: %s",
                payloadCapacityTons, numberOfAxles, hasRefrigeration ? "Yes" : "No");
    }

    @Override
    public String getVehicleDetails() {
        return super.getVehicleDetails() + "\n  Specs: " + getSpecifications();
    }

    public double  getPayloadCapacityTons() { return payloadCapacityTons; }
    public int     getNumberOfAxles()       { return numberOfAxles; }
    public boolean isHasRefrigeration()     { return hasRefrigeration; }
}
