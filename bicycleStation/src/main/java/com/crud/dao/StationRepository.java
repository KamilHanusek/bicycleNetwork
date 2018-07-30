package com.crud.dao;

import com.crud.instance.Bicycle;
import com.crud.instance.Stand;
import com.crud.instance.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StationRepository {
    @Autowired
    BicycleRepository bicycleRepository;

    private static Map<String,Station> stationByStationName;

    static{
        stationByStationName = new ConcurrentHashMap<String, Station>(){
            {
                put("kornicka",new Station("kornicka")) ;
                put("wroclawska",new Station("wroclawska"));
            }

        };
    }

    public Collection<Station> getAllStations(){
        return stationByStationName.values();
    }

    public String addStation(Station station) {
        if(!stationByStationName.containsKey(station.getName())) {
            if(!station.getBicycleByBicycleId().isEmpty()){
                for(Bicycle bicycle : station.getBicycleByBicycleId())
                    if(!bicycleRepository.getAllBicycles().contains(bicycle.getId()))
                        bicycleRepository.addBicycle(bicycle);
            }
            stationByStationName.put(station.getName(), station);
            return "You have been created station with name:\" " + station.getName() + " \"";
        }
        return "Station with that name exist";
    }

    public void updateStation(Station station,String name) {
        if(stationByStationName.containsKey(name)){
            Station s = stationByStationName.get(name);
            s.setStandByStandId(station.getStandByStandId());
            s.setBicycleByBicycleId(station.getBicycleByBicycleId());
            if(!station.getBicycleByBicycleId().isEmpty()){
                for(Bicycle bicycle : station.getBicycleByBicycleId())
                    if(!bicycleRepository.getAllBicycles().contains(bicycle.getId()))
                        bicycleRepository.addBicycle(bicycle);
            }
            stationByStationName.replace(name,s);
        }
    }

    public String removeStationByName(String name) {
        if(stationByStationName.containsKey(name)){
            for(Bicycle bicycle : stationByStationName.get(name).getBicycleByBicycleId())
                bicycle.setRented(false);
            stationByStationName.remove(name);
            return "Station with name: \"" + name + "\" has been removed";
        }
        return "Station with that name doesn't exist";
    }

    public Station getStationByName(String name) {
        if(stationByStationName.containsKey(name))
            return stationByStationName.get(name);
        return null;
    }

    public Collection<Bicycle> getAddedBicycles(String name) {
        if(stationByStationName.containsKey(name)){
            return  stationByStationName.get(name).getBicycleByBicycleId();
        }
        return null;
    }

    public void addStandToStation(String name, Stand stand) {
        if(stationByStationName.containsKey(name)){
            if(!bicycleRepository.getAllBicycles().contains(stand.getBicycleId()))
                stand.setBicycleId(0);
            stationByStationName.get(name).addStand(stand);
        }
    }

    public Collection getStandsInStation(String name) {
        return stationByStationName.get(name).getStandByStandId();
    }

    public String changeStandById(int standId, String name, Stand stand) {
        if(stationByStationName.containsKey(name)){
            if(stationByStationName.get(name).haveStand(standId)){
                Stand stand1 = stationByStationName.get(name).getStandById(standId);
                stand1.setFree(stand.isFree());
                stand1.setBicycleId(stand.getBicycleId());
                stationByStationName.get(name).setStand(stand1,standId);
                return "Stand with id: " + standId + " has been changed";
            }
        }
        return "something goes wrong";
    }
    //=============take and return bicycle==============
    public synchronized String takeBicycle(String name, int bicycleId) {
        if(stationByStationName.get(name).isHereBicycle(bicycleId) == true){
            for(Stand stand : stationByStationName.get(name).getStandByStandId())
                if(stand.getBicycleId() == bicycleId){
                    stand.setBicycleId(0);
                    break;
                }
            if(bicycleRepository.getBicycleById(bicycleId).isRented() == false){
                bicycleRepository.getBicycleById(bicycleId).setRented(true);
                stationByStationName.get(name).takeBicycle(bicycleRepository.getBicycleById(bicycleId));
            }
            return "Bicycle with id: " + bicycleId + " has been taken";
        }
        return "Something goes wrong";
    }

    synchronized public String returnBicycle(Bicycle bicycle, String name) {
        if(stationByStationName.containsKey(name)){
            if(!bicycleRepository.getAllBicycles().contains(bicycle.getId()))
                bicycleRepository.addBicycle(bicycle);
            stationByStationName.get(name).returnBicycle(bicycleRepository.getBicycleById(bicycle.getId()));
            return "You have been returned bicycle with id: " + bicycle.getId();
        }
        return "Something goes wrong";
    }

    public String deleteStandById(String name, int standId) {
        if(stationByStationName.containsKey(name)){
            stationByStationName.get(name).deleteStandById(standId);
            return "Stand with id: " + standId + " has been removed";
        }
        return "Something goes wrong";
    }
}
