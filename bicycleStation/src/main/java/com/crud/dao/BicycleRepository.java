package com.crud.dao;

import com.crud.instance.Bicycle;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BicycleRepository {
    private static Map<Integer, Bicycle> bicycleByBicycleId;
    private Integer idNum = 7;
    static {

        bicycleByBicycleId = new HashMap<Integer, Bicycle>(){
            {
                put(1,new Bicycle());
                put(2,new Bicycle());
                put(3,new Bicycle());
                put(4,new Bicycle());
                put(5,new Bicycle());
                put(6,new Bicycle());
            }
        };
    }
    public Collection<Bicycle> getAllBicycles(){
        return bicycleByBicycleId.values();
    }

    public void addBicycle(Bicycle bicycle) {
        if(!bicycleByBicycleId.containsKey(bicycle.getId()))
            bicycleByBicycleId.put(idNum++,bicycle);
    }

    public Bicycle getBicycleById(Integer id){
        if(bicycleByBicycleId.containsKey(id))
            return bicycleByBicycleId.get(id);
        return null;}

    public String changeBicycleById(Integer id, Bicycle bicycle) {
        if(bicycleByBicycleId.containsKey(id)) {
            Bicycle bicycle1 = bicycleByBicycleId.get(id);
            bicycle1.setRented(bicycle.isRented());
            bicycleByBicycleId.replace(id,bicycle1);
            return "bicycle with id: " + id + " has been changed";
        }
        return "Something goes wrong";
    }

    public String deleteBicycleById(Integer id) {
        if(bicycleByBicycleId.containsKey(id)){
            bicycleByBicycleId.remove(id);
            return  "Bicycle with id: " + id + " has been removed";
        }
        return "Something goes wrong";
    }
}
