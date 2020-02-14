package com.ryan.springbatchexample.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ryan.springbatchexample.telco.Telco;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Processor implements ItemProcessor<Telco, Telco> {

    private static final Map<String, String> TELCO_NAME = new HashMap<>();

    public Processor() {
        TELCO_NAME.put("01", "SUN");
        TELCO_NAME.put("02", "SMART");
        TELCO_NAME.put("03", "GLOBE");
        TELCO_NAME.put("04", "CHERRY MOBILE");
        TELCO_NAME.put("05", "ABS CBN");
    }

    @Override
    public Telco process(Telco telco) throws Exception {

        log.info("Processing...");
        String telcoCode = telco.getTelco();
        String telcoName = TELCO_NAME.get(telcoCode);
        telco.setTelco(telcoName);
        telco.setDateIssued(new Date());
        log.info(String.format("Converted from %s to %s", telcoCode, telcoName));

        return telco;
    }

}