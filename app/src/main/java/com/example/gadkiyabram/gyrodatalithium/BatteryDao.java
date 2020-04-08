package com.example.gadkiyabram.gyrodatalithium;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BatteryDao implements DAO<BatteryModel> {

    private List<BatteryModel> batteries = new ArrayList<>();

    @Override
    public Optional<BatteryModel> get(int _id){
        return Optional.ofNullable(batteries.get((int) _id));
    }

    @Override
    public List<BatteryModel> getAllBatteries(){
        return batteries;
    }
}
