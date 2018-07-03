package com.crud.instance;

import java.util.concurrent.atomic.AtomicInteger;

public class Bicycle {
    private static AtomicInteger count = new AtomicInteger(1);
    private Integer id;
    private boolean isRented = false;
    public Bicycle(){id = count.getAndIncrement();}

    //=====getters and setters=====
    public Integer getId() {
        return id;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }
    public void setId(Integer id){this.id = id;}
}
