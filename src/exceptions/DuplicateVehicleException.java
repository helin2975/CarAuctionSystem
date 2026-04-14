package exceptions;

public class DuplicateVehicleException extends Exception {
    public DuplicateVehicleException(String vehicleId) {
        super("Vehicle with ID [" + vehicleId + "] already exists in the system.");
    }
}
