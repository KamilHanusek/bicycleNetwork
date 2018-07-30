package com.crud.instance;

import com.fasterxml.jackson.annotation.*;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@JsonPropertyOrder({"name", "Free stands", "Occupied stands", "Available bicycles"})
public class Station {

    private static int standCounter = 1;
    private String name;
    private Map <Integer,Bicycle> bicycleByBicycleId = new HashMap();
    private Map<Integer,Stand> standByStandId = new ConcurrentHashMap<>();

    public Station(){}


    //=====Json creator and getters=====

    @JsonCreator
    public Station(String name){
        this.name = name;
    }

    //======JsonGetters=======
    @JsonGetter("Free stands")
    public int getNumOfFreeStands(){
        int i = 0;
        for(Stand stand : standByStandId.values())
            if(stand.isFree() == true)
                i++;
        return i;
    }

    @JsonGetter("Available bicycles")
    public int getNumOfBicycles(){
        return bicycleByBicycleId.size();
    }

    @JsonGetter("Occupied stands")
    public int getNumOfOccupiedStands(){
        int i = 0;
        for(Stand stand : standByStandId.values())
            if(stand.isFree() == false)
                i++;
        return i;
    }

    //=====JsonSetters=====
    @JsonSetter
    public void setStandByStandId(Collection<Stand> standByStandId) {
        this.standByStandId.clear();
        standCounter = 1;
        for(Stand stand : standByStandId){
            stand.setId(standCounter);
            this.standByStandId.put(standCounter++,stand);
        }
    }

    @JsonSetter
    public void setBicycleByBicycleId(Collection<Bicycle> bicycleByBicycleId) {
        this.bicycleByBicycleId.clear();
        for(Bicycle bicycle : bicycleByBicycleId)
            this.bicycleByBicycleId.put(bicycle.getId(),bicycle);
    }
    //=====================
    public String getName() {
        return name;
    }

    //=====return and take bicycle============
    public void returnBicycle(Bicycle bicycle){
        bicycle.setRented(false);
        bicycleByBicycleId.put(bicycle.getId(),bicycle);
        for(Stand stand : standByStandId.values())
            if(stand.isFree() == true){
                stand.setFree(false);
                break;
            }
    }
    public void takeBicycle(Bicycle bicycle){
            bicycleByBicycleId.remove(bicycle.getId());
    }
    //=========================================
    @JsonIgnore
    public Collection<Stand> getStandByStandId() {
        return standByStandId.values();
    }
    @JsonIgnore
    public Collection<Bicycle> getBicycleByBicycleId() {
        return bicycleByBicycleId.values();
    }

    public void addStand(Stand stand) {
        stand.setId(standCounter);
        standByStandId.put(standCounter++,stand);
    }

    public boolean isHereBicycle(int bicycleId){
        for(Bicycle bicycle : bicycleByBicycleId.values())
            if(bicycle.getId() == bicycleId)
                return true;
        return false;
    }

    public boolean haveStand(int standId) {
        if(standByStandId.containsKey(standId))
            return true;
        return false;
    }

    public void setStand(Stand stand, int id) {
        standByStandId.put(id,stand);
    }

    public Integer getStandsBycicleId(int standId) {
        return standByStandId.get(standId).getBicycleId();
    }

    public void deleteStandById(int standId) {
        standByStandId.remove(standId);
    }

    public Stand getStandById(int standId) {
        return standByStandId.get(standId);
    }
}
