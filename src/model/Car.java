package model;

public class Car extends Vehicle {


    private int numberOfDoors;
    private String fuelType;       
    private String transmissionType; 

    public Car(String vehicleId, String make, String model, int year,
               double basePrice, String color, int mileage,
               int numberOfDoors, String fuelType, String transmissionType) {
        super(vehicleId, make, model, year, basePrice, color, mileage);
        this.numberOfDoors    = numberOfDoors;
        this.fuelType         = fuelType;
        this.transmissionType = transmissionType;
    }

    @Override
    public double calculateRegistrationFee() {
        return getBasePrice() * 0.08;
    }

    @Override
    public String getVehicleType() {
        return "CAR";
    }

    @Override
    public String getSpecifications() {
        return String.format("Doors: %d | Fuel: %s | Transmission: %s",
                numberOfDoors, fuelType, transmissionType);
    }

    @Override
    public String getVehicleDetails() {
        return super.getVehicleDetails() + "\n  Specs: " + getSpecifications();
    }

    public int    getNumberOfDoors()    { return numberOfDoors; }
    public String getFuelType()         { return fuelType; }
    public String getTransmissionType() { return transmissionType; }
}
