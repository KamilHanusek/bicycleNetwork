package com.crud.dao;

import com.crud.instance.Bicycle;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BicycleRepository {
    private static Map<Integer, Bicycle> bicycles;
    private Integer idNum = 7;
    static {

        bicycles = new HashMap<Integer, Bicycle>(){
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
        return bicycles.values();
    }

    public void addBicycle(Bicycle bicycle) {
        if(!bicycles.containsKey(bicycle.getId()))
            bicycles.put(idNum++,bicycle);
    }

    public Bicycle getBicycleById(Integer id){
        if(bicycles.containsKey(id))
            return bicycles.get(id);
        return null;}

    public String changeBicycleById(Integer id, Bicycle bicycle) {
        if(bicycles.containsKey(id)) {
            Bicycle bicycle1 = bicycles.get(id);
            bicycle1.setRented(bicycle.isRented());
            bicycles.replace(id,bicycle1);
            return "bicycle with id: " + id + " has been changed";
        }
        return "Something goes wrong";
    }

    public String deleteBicycleById(Integer id) {
        if(bicycles.containsKey(id)){
            bicycles.remove(id);
            return  "Bicycle with id: " + id + " has been removed";
        }
        return "Something goes wrong";
    }
}
