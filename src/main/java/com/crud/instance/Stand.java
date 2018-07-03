package com.crud.instance;


public class Stand {
    private int id;
    private boolean free = true;
    private Integer bicycleId;

    public Stand(){}

    public Stand(boolean free, Integer bicycleId){
        this.free = free;
        this.bicycleId = bicycleId;
    }

    public void setBicycleId(Integer bicycleId){
        this.bicycleId = bicycleId;
        free = false;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public Integer getBicycleId() {
        return bicycleId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
