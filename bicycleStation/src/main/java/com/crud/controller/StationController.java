package com.crud.controller;

import com.crud.instance.Bicycle;
import com.crud.instance.Stand;
import com.crud.instance.Station;
import com.crud.service.BicycleService;
import com.crud.service.StationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController()
public class StationController {

    @Autowired
    BicycleService bicycleService;
    @Autowired
    StationService stationService;

    @RequestMapping(value = "/stations")
    public Collection<Station> getAllStations(){
        return stationService.getAllStations();
    }

    @RequestMapping(value = "/stations/{name}",method = RequestMethod.GET)
    public Station getStationByName(@PathVariable(value = "name") String name){
        return stationService.getStationByName(name);
    }

    @RequestMapping(value = "/stations", method = RequestMethod.POST)
    public String addStation(@RequestBody Station station){
        if(!station.getBicycleByBicycleId().isEmpty())
            for(Bicycle bicycle : station.getBicycleByBicycleId())
                bicycleService.addBicycle(bicycle);

        return stationService.addStation(station);
    }

    @RequestMapping(value = "/stations/{name}",method = RequestMethod.PUT)
    public void changeStationByName(@RequestBody Station station,
                                    @PathVariable(value = "name") String name){
        stationService.updateStation(station,name);
    }

    @RequestMapping(value = "/stations/{name}", method = RequestMethod.DELETE)
    public String removeStationByName(@PathVariable(value = "name") String name){
        return stationService.removeStationByName(name);
    }

    @RequestMapping(value = "/stations/{name}/bicycles",method = RequestMethod.GET)
    public Collection<Bicycle> getAddedBicycles(@PathVariable(value = "name") String name){
        return stationService.getAddedBicycles(name);
    }

    @RequestMapping(value = "/stations/{name}/bicycles", method = RequestMethod.POST)
    public String returnBicycle(@RequestBody Bicycle bicycle,
                                    @PathVariable(value = "name") String name){
        return stationService.returnBicycle(bicycle,name);
    }

    @RequestMapping(value = "/stations/{name}/bicycles/{bicycleId}",method = RequestMethod.DELETE)
    public String takeBicycle(@PathVariable ( value = "name") String name,
                                    @PathVariable(value = "bicycleId") int bicycleId){
        return stationService.takeBicycle(name,bicycleId);
    }

    @RequestMapping(value = "/stations/{name}/stands",method = RequestMethod.GET )
    public Collection getStandsInStation(@PathVariable(value = "name") String name){
        return stationService.getStandsInStation(name);
    }

    @RequestMapping(value = "/stations/{name}/stands",method = RequestMethod.POST)
    public void addStandToStation(@PathVariable(value = "name") String name,
                                  @RequestBody Stand stand){
        stationService.addStandToStation(name,stand);
    }

    @RequestMapping(value = "/stations/{name}/stands/{standId}", method = RequestMethod.PUT)
    public String changeStandById(@PathVariable (value = "standId") int standId,
                                  @PathVariable ( value = "name") String name,
                                  @RequestBody Stand stand){
        return stationService.changeStandById(standId,name,stand);
    }

    @RequestMapping(value = "/stations/{name}/stands/{standId}", method = RequestMethod.DELETE)
    public String deleteStandById(@PathVariable(value = "name") String name,
                                  @PathVariable(value = "standId") int standId){
        return stationService.deleteStandById(name,standId);
    }
}
