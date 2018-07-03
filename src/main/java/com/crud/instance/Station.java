package com.crud.instance;

import com.fasterxml.jackson.annotation.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@JsonPropertyOrder({"name", "Free stands", "Occupied stands", "Available bicycles"})
public class Station {

    private static int standCounter = 1;
    private String name;
    private Map <Integer,Bicycle> bicycleArray = new HashMap();
    private Map<Integer,Stand> standArray = new HashMap<>();

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
        for(Stand stand : standArray.values())
            if(stand.isFree() == true)
                i++;
        return i;
    }

    @JsonGetter("Available bicycles")
    public int getNumOfBicycles(){
        return bicycleArray.size();
    }

    @JsonGetter("Occupied stands")
    public int getNumOfOccupiedStands(){
        int i = 0;
        for(Stand stand : standArray.values())
            if(stand.isFree() == false)
                i++;
        return i;
    }

    //=====JsonSetters=====
    @JsonSetter
    public void setStandArray(Collection<Stand> standArray) {
        this.standArray.clear();
        standCounter = 1;
        for(Stand stand : standArray){
            stand.setId(standCounter);
            this.standArray.put(standCounter++,stand);
        }
    }

    @JsonSetter
    public void setBicycleArray(Collection<Bicycle> bicycleArray) {
        this.bicycleArray.clear();
        for(Bicycle bicycle : bicycleArray)
            this.bicycleArray.put(bicycle.getId(),bicycle);
    }
    //=====================
    public String getName() {
        return name;
    }

    //=====return and take bicycle============
    public void returnBicycle(Bicycle bicycle){
        bicycle.setRented(false);
        bicycleArray.put(bicycle.getId(),bicycle);
        for(Stand stand : standArray.values())
            if(stand.isFree() == true){
                stand.setFree(false);
                break;
            }
    }
    public void takeBicycle(Bicycle bicycle){
            bicycleArray.remove(bicycle.getId());
    }
    //=========================================
    @JsonIgnore
    public Collection<Stand> getStandArray() {
        return standArray.values();
    }
    @JsonIgnore
    public Collection<Bicycle> getBicycleArray() {
        return bicycleArray.values();
    }

    public void addStand(Stand stand) {
        stand.setId(standCounter);
        standArray.put(standCounter++,stand);
    }

    public boolean isHereBicycle(int bicycleId){
        for(Bicycle bicycle : bicycleArray.values())
            if(bicycle.getId() == bicycleId)
                return true;
        return false;
    }

    public boolean haveStand(int standId) {
        if(standArray.containsKey(standId))
            return true;
        return false;
    }

    public void setStand(Stand stand, int id) {
        standArray.put(id,stand);
    }

    public Integer getStandsBycicleId(int standId) {
        return standArray.get(standId).getBicycleId();
    }

    public void deleteStandById(int standId) {
        standArray.remove(standId);
    }

    public Stand getStandById(int standId) {
        return standArray.get(standId);
    }
}
