package com.crud.dao;

import com.crud.instance.Bicycle;
import com.crud.instance.Stand;
import com.crud.instance.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class StationRepository {
    @Autowired
    BicycleRepository bicycleRepository;

    public static Map<String,Station> stations;

    static{
        stations = new HashMap<String, Station>(){
            {
                put("kornicka",new Station("kornicka")) ;
                put("wroclawska",new Station("wroclawska"));
            }

        };
    }

    public Collection<Station> getAllStations(){
        return stations.values();
    }

    public String addStation(Station station) {
        if(!stations.containsKey(station.getName())) {
            if(!station.getBicycleArray().isEmpty()){
                for(Bicycle bicycle : station.getBicycleArray())
                    if(!bicycleRepository.getAllBicycles().contains(bicycle.getId()))
                        bicycleRepository.addBicycle(bicycle);
            }
            stations.put(station.getName(), station);
            return "You have been created station with name:\" " + station.getName() + " \"";
        }
        return "Station with that name exists";
    }

    public void updateStation(Station station,String name) {
        if(stations.containsKey(name)){
            Station s = stations.get(name);
            s.setStandArray(station.getStandArray());
            s.setBicycleArray(station.getBicycleArray());
            if(!station.getBicycleArray().isEmpty()){
                for(Bicycle bicycle : station.getBicycleArray())
                    if(!bicycleRepository.getAllBicycles().contains(bicycle.getId()))
                        bicycleRepository.addBicycle(bicycle);
            }
            stations.replace(name,s);
        }
    }

    public String removeStationByName(String name) {
        if(stations.containsKey(name)){
            stations.remove(name);
            return "Station with name: \"" + name + "\" has been removed";
        }
        return "Station with that name doesn't exists";
    }

    public Station getStationByName(String name) {
        if(stations.containsKey(name))
            return stations.get(name);
        return null;
    }

    public Collection<Bicycle> getAddedBicycles(String name) {
        if(stations.containsKey(name)){
            return  stations.get(name).getBicycleArray();
        }
        return null;
    }

    public void addStandToStation(String name, Stand stand) {
        if(stations.containsKey(name)){
            if(!bicycleRepository.getAllBicycles().contains(stand.getBicycleId()))
                stand.setBicycleId(0);
            stations.get(name).addStand(stand);
        }
    }

    public Collection getStandsInStation(String name) {
        return stations.get(name).getStandArray();
    }

    public String changeStandById(int standId, String name, Stand stand) {
        if(stations.containsKey(name)){
            if(stations.get(name).haveStand(standId)){
                Stand stand1 = stations.get(name).getStandById(standId);
                stand1.setFree(stand.isFree());
                stand1.setBicycleId(stand.getBicycleId());
                stations.get(name).setStand(stand1,standId);
                return "Stand with id: " + standId + " has been changed";
            }
        }
        return "something goes wrong";
    }
    //=============take and return bicycle==============
    public synchronized String takeBicycle(String name, int bicycleId) {
        if(stations.get(name).isHereBicycle(bicycleId) == true){
            for(Stand stand : stations.get(name).getStandArray())
                if(stand.getBicycleId() == bicycleId){
                    stand.setBicycleId(0);
                    break;
                }
            if(bicycleRepository.getBicycleById(bicycleId).isRented() == false){
                bicycleRepository.getBicycleById(bicycleId).setRented(true);
                stations.get(name).takeBicycle(bicycleRepository.getBicycleById(bicycleId));
            }
            return "Bicycle with id: " + bicycleId + " has been taken";
        }
        return "Something goes wrong";
    }

    synchronized public String returnBicycle(Bicycle bicycle, String name) {
        if(stations.containsKey(name)){
            if(!bicycleRepository.getAllBicycles().contains(bicycle.getId()))
                bicycleRepository.addBicycle(bicycle);
            stations.get(name).returnBicycle(bicycleRepository.getBicycleById(bicycle.getId()));
            return "You have been returned bicycle with id: " + bicycle.getId();
        }
        return "Something goes wrong";
    }

    public String deleteStandById(String name, int standId) {
        if(stations.containsKey(name)){
            stations.get(name).deleteStandById(standId);
            return "Stand with id: " + standId + " has been removed";
        }
        return "Something goes wrong";
    }
}
