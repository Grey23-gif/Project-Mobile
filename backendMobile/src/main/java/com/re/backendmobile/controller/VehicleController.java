package com.re.backendmobile.controller;

import com.re.backendmobile.dto.ApiResponse;
import com.re.backendmobile.entity.Vehicle;
import com.re.backendmobile.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@CrossOrigin("*")
public class VehicleController {

    private final VehicleRepository vehicleRepository;

    @GetMapping
    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    @PostMapping
    public Vehicle add(@RequestBody Vehicle request) {

        Vehicle vehicle = new Vehicle();

        vehicle.setPlateNumber(request.getPlateNumber());
        vehicle.setType(request.getType());
        vehicle.setOwnerName(request.getOwnerName());

        return vehicleRepository.save(vehicle);
    }

    @PutMapping("/{id}")
    public Vehicle update(@PathVariable Integer id, @RequestBody Vehicle request) {
        Vehicle old = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy xe"));

        old.setPlateNumber(request.getPlateNumber());
        old.setType(request.getType());
        old.setOwnerName(request.getOwnerName());

        return vehicleRepository.save(old);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> delete(@PathVariable Integer id) {
        vehicleRepository.deleteById(id);
        return new ApiResponse<>(true, "Xóa xe thành công", null, null);
    }
}