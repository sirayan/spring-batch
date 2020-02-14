package com.ryan.springbatchexample.batch;

import java.util.List;

import com.ryan.springbatchexample.telco.Telco;
import com.ryan.springbatchexample.telco.TelcoService;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DatabaseWriter implements ItemWriter<Telco> {

    private TelcoService telcoService;

    @Autowired
    public DatabaseWriter(TelcoService telcoService) {
        this.telcoService = telcoService;
    }

    @Override
    public void write(List<? extends Telco> telcos) throws Exception {
        log.info("Writing to database... " + telcos);
        telcoService.saveTelcos(telcos);
        log.info("Telcos saved successfully!");
    }

}
