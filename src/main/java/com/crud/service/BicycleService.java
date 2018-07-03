package com.crud.service;

import com.crud.dao.BicycleRepository;
import com.crud.instance.Bicycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BicycleService {

    @Autowired
    BicycleRepository bicycleRepository;

    public Collection<Bicycle> getBicycles() {
        return bicycleRepository.getAllBicycles();
    }

    public void addBicycle(Bicycle bicycle) {
        bicycleRepository.addBicycle(bicycle);
    }

    public String changeBicycleById(Integer id, Bicycle bicycle) {
        return bicycleRepository.changeBicycleById(id,bicycle);
    }

    public String deleteBicycleById(Integer id) {
        return bicycleRepository.deleteBicycleById(id);
    }
}
