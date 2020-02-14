package com.ryan.springbatchexample.telco;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelcoServiceImpl implements TelcoService {

    private TelcoRepository telcoRepository;

    @Autowired
    public TelcoServiceImpl(TelcoRepository telcoRepository) {
        this.telcoRepository = telcoRepository;
    }

    @Override
    public void saveTelcos(List<? extends Telco> telco) {
        telcoRepository.saveAll(telco);
    }

}