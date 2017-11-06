package com.aaronr.googlespringone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

@Component
public class MyTask {

    RestTemplate rT = new RestTemplate();
    int index = 0;

    @Scheduled(cron = "*/1 * * * * *")
    private void addVehicle(){

        Vehicle v = getRandomVehicle(index++);

        String url = "http://localhost:8080/addVehicle";
        rT.postForEntity(url, v, Vehicle.class);

    }

    @Scheduled(cron = "0 0 * * * *")
    private void deleteVehicle(){

        int id = (int) (Math.random()*100);
        String url = "http://localhost:8080/deleteVehicle/" + id;
        rT.delete(url);


    }

    @Scheduled(cron = "0 0 * * * *")
    private void getVehicle(){

        int id = (int) (Math.random()*100);
        String url = "http://localhost:8080/getVehicle/" + id;
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void updateVehicle(){

        int id = (int) (Math.random()*101);
        Vehicle v = getRandomVehicle(id);
        String url = "http://localhost:8080/updateVehicle";
        rT.put(url, v);
        url = "http://localhost:8080/getVehicle/" + id;
        v = rT.getForEntity(url,Vehicle.class).getBody();
        System.out.println(v);
    }

    @Scheduled(cron = "0 * * * * *")
    private void latestVehiclesReport()throws IOException{
        String url = "http://localhost:8080/getLatestVehicle";
        Object[] vList =  rT.getForEntity(url,Object[].class).getBody();

        for (int i = 0; i < vList.length; i++) {

            System.out.println(vList[i].toString());
        }

    }

    private Vehicle getRandomVehicle(int id){

        int year = ((int) (Math.random() * (2016-1986))) + 1986;
        int mpg = (int) (Math.random() * 35) + 7;
        double msrp = ((int)((Math.random()*(45000-15000))) /1) + 15000;
        String make = "RAND-M&M"+RandomStringUtils.randomAlphabetic((int) (Math.random()*10) + 2);
        return new Vehicle(id,make,year,msrp);

    }
}
