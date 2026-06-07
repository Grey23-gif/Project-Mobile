package com.re.backendmobile.repository;

import com.re.backendmobile.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findByOwnerNameContainingIgnoreCase(String ownerEmail);

    Vehicle findFirstByPlateNumber(String plateNumber);
}
