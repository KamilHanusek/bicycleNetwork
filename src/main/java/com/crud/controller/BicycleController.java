package com.crud.controller;

import com.crud.instance.Bicycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.crud.service.BicycleService;

import java.util.Collection;

@RestController
public class BicycleController {

    @Autowired
    BicycleService bicycleService;

    @RequestMapping(value = "/bicycles",method = RequestMethod.GET)
    public Collection<Bicycle> getBicycles(){
        return bicycleService.getBicycles();
    }

    @RequestMapping(value = "/bicycles", method = RequestMethod.POST)
    public void addBicycle(@RequestBody Bicycle bicycle){
        bicycleService.addBicycle(bicycle);
    }

    @RequestMapping(value = "/bicycles/{id}", method = RequestMethod.PUT)
    public String changeBicycleById(@PathVariable ( value = "id") Integer id, @RequestBody Bicycle bicycle){
        return bicycleService.changeBicycleById(id, bicycle);
    }
}
