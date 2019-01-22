package com.example.gadkiyabram.gyrodatalithium;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BatteryDao implements DAO<BatteryDetails> {

    private List<BatteryDetails> batteries = new ArrayList<>();

    @Override
    public Optional<BatteryDetails> get(int _id){
        return Optional.ofNullable(batteries.get((int) _id));
    }

    @Override
    public List<BatteryDetails> getAllBatteries(){
        return batteries;
    }
}
