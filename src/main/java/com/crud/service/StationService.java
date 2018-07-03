package com.crud.service;

import com.crud.dao.StationRepository;
import com.crud.instance.Bicycle;
import com.crud.instance.Stand;
import com.crud.instance.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StationService {
    @Autowired
    StationRepository stationRepository;

    public Collection<Station> getAllStations(){
        return stationRepository.getAllStations();
    }

    public void updateStation(Station station,String name) {
        stationRepository.updateStation(station, name);
    }

    public String addStation(Station station){
        return stationRepository.addStation(station);
    }

    public String removeStationByName(String name) {
        return stationRepository.removeStationByName(name);
    }

    public Station getStationByName(String name) {
        return stationRepository.getStationByName(name);
    }

    public String returnBicycle(Bicycle bicycle, String name) {
        return stationRepository.returnBicycle(bicycle,name);
    }

    public Collection<Bicycle> getAddedBicycles(String name) {
        return stationRepository.getAddedBicycles(name);
    }

    public void addStandToStation(String name, Stand stand) {
        stationRepository.addStandToStation(name,stand);
    }

    public Collection getStandsInStation(String name) {
        return stationRepository.getStandsInStation(name);
    }

    public String changeStandById(int standId, String name, Stand stand) {
        return stationRepository.changeStandById(standId, name, stand);
    }

    public String deleteStandById(String name, int standId) {
        return stationRepository.deleteStandById(name, standId);
    }

    public String takeBicycle(String name, int bicycleId) {
        return stationRepository.takeBicycle(name, bicycleId);
    }
}
