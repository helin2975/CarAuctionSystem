package model;

public class Motorcycle extends Vehicle {


    private int    engineCC;
    private String bikeType;  
    private boolean hasSidecar;

    public Motorcycle(String vehicleId, String make, String model, int year,
                      double basePrice, String color, int mileage,
                      int engineCC, String bikeType, boolean hasSidecar) {
        super(vehicleId, make, model, year, basePrice, color, mileage);
        this.engineCC   = engineCC;
        this.bikeType   = bikeType;
        this.hasSidecar = hasSidecar;
    }

    @Override
    public double calculateRegistrationFee() {
        return getBasePrice() * 0.05;
    }

    @Override
    public String getVehicleType() {
        return "MOTORCYCLE";
    }

    @Override
    public String getSpecifications() {
        return String.format("Engine: %d CC | Type: %s | Sidecar: %s",
                engineCC, bikeType, hasSidecar ? "Yes" : "No");
    }

    @Override
    public String getVehicleDetails() {
        return super.getVehicleDetails() + "\n  Specs: " + getSpecifications();
    }

    public int    getEngineCC()   { return engineCC; }
    public String getBikeType()   { return bikeType; }
    public boolean isHasSidecar() { return hasSidecar; }
}
