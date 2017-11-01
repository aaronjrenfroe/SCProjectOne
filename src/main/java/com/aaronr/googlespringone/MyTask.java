package com.aaronr.googlespringone;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
public class MyTask {

    RestTemplate rT = new RestTemplate();
    String[] contents = {"Hello World", "Bye World"};
    int indx = 0;

    @Scheduled(cron = "*/5 * * * * *")
    private void postGreeting(){

        String url = "http://localhost:8080/updateGreeting";
        rT.put(url, contents[indx++]);

        url = "http://localhost:8080/greeting";
        Greeting g = rT.getForObject(url, Greeting.class);
        System.out.println(g.getContent());

        if(indx == contents.length){
            indx = 0;
        }
    }
}


//
//    //@Scheduled(cron = "*/10 * * * * *")
//    public void periodicTask1(){
//        System.out.println("the time is now " +  new Date());
//    }
//