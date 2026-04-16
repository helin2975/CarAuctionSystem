package service;

import exceptions.DuplicateVehicleException;
import model.Vehicle;

import java.util.*;
import java.util.stream.Collectors;

public class VehicleService {

    private Map<String, Vehicle> vehicleMap = new HashMap<>();

    public void addVehicle(Vehicle vehicle) throws DuplicateVehicleException {
        if (vehicleMap.containsKey(vehicle.getVehicleId()))
            throw new DuplicateVehicleException(vehicle.getVehicleId());
        vehicleMap.put(vehicle.getVehicleId(), vehicle);
        System.out.println("  Vehicle registered: " + vehicle.getVehicleId());
    }

    public Vehicle getVehicle(String vehicleId) {
        return vehicleMap.get(vehicleId);
    }

    public boolean removeVehicle(String vehicleId) {
        return vehicleMap.remove(vehicleId) != null;
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicleMap.values());
    }

    public List<Vehicle> getAvailableVehicles() {
        return vehicleMap.values().stream()
                .filter(Vehicle::isAvailable)
                .collect(Collectors.toList());
    }

    public List<Vehicle> searchByMake(String make) {
        return vehicleMap.values().stream()
                .filter(v -> v.getMake().equalsIgnoreCase(make))
                .collect(Collectors.toList());
    }

    public List<Vehicle> searchByType(String type) {
        return vehicleMap.values().stream()
                .filter(v -> v.getVehicleType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesSortedByPrice() {
        return vehicleMap.values().stream()
                .sorted(Comparator.comparingDouble(Vehicle::getBasePrice))
                .collect(Collectors.toList());
    }

    public boolean vehicleExists(String vehicleId) {
        return vehicleMap.containsKey(vehicleId);
    }

    public int getTotalVehicles() {
        return vehicleMap.size();
    }

    public void loadVehicles(Map<String, Vehicle> loaded) {
        this.vehicleMap = loaded;
    }

    public Map<String, Vehicle> getVehicleMap() {
        return vehicleMap;
    }
}
