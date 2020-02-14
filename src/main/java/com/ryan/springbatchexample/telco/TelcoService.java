package com.ryan.springbatchexample.telco;

import java.util.List;

public interface TelcoService {

    void saveTelcos(List<? extends Telco> telco);
}