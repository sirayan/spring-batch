package com.ryan.springbatchexample.telco;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TelcoRepository extends MongoRepository<Telco, String> {

}