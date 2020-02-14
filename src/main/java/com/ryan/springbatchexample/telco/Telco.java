package com.ryan.springbatchexample.telco;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "telco")
public class Telco {

    @Id
    private String id;

    @Field(value = "mobile_prefix")
    private String mobilePrefix;

    @Field(value = "telco")
    private String telco;

    @Field(value = "plan_type")
    private String planType;

    @Field(value = "date_issued")
    private Date dateIssued;

}