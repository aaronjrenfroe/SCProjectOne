package com.aaronr.googlespringone;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by AaronR on 10/17/17.
 * for ?
 */
@RestController
public class HelloController {

//    @RequestMapping("/greeting")
//    public Greeting greeting(@RequestParam(value="name", defaultValue = "World") String name){
//        return new Greeting(27, name);
//    }

//    @RequestMapping(value = "/createGreeting", method = RequestMethod.POST)
//    public Greeting newGreeting(@RequestParam(value="content", defaultValue = "World") String name){
//        return new Greeting(19, name);
//    }


//    @RequestMapping(value = "/createGreeting", method = RequestMethod.POST)
//    public Greeting how(@RequestBody String content){
//        return new Greeting(19, content);
//    }


    @RequestMapping(value = "/createGreeting", method = RequestMethod.POST)
    public Greeting how(@RequestBody String data) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        Greeting greeting = mapper.readValue(data, Greeting.class);
        mapper.writeValue(new File("./message.txt"), greeting);
        return greeting;
    }

    @RequestMapping(value="/greeting", method = RequestMethod.GET)
    public Greeting greeting()throws IOException{

        // ObjectMapper provides functionality for reading and writing json
        ObjectMapper mapper = new ObjectMapper();

        String message = FileUtils.readFileToString(new File("./message.txt"), CharEncoding.UTF_8);
        // deserialize JSON to greeting Object
        Greeting greeting = mapper.readValue(message, Greeting.class);
        return greeting;

    }

    @RequestMapping(value="/updateGreeting", method = RequestMethod.PUT)
    public Greeting updateGreeting(@RequestBody String newMessage)throws IOException{
        // ObjectMapper provides functionality for reading and writing json
        ObjectMapper mapper = new ObjectMapper();
        String message = FileUtils.readFileToString(new File("./message.txt"), CharEncoding.UTF_8);
        // deserialize JSON to greeting Object
        Greeting greeting = mapper.readValue(message, Greeting.class);

        // update message
        greeting.setContent(newMessage);
        //serialize greeting to object JSON
        mapper.writeValue(new File("./message.txt"), greeting);
        return greeting;
    }

    @RequestMapping(value="/deleteGreeting", method = RequestMethod.DELETE)
    public void deleteGreeting(@RequestBody int id)throws IOException{

        // ObjectMapper provides functionality for reading and writing json
        ObjectMapper mapper = new ObjectMapper();

        String message = FileUtils.readFileToString(new File("./message.txt"), StandardCharsets.UTF_8.name());

        // deserialize JSON to greeting Object
        Greeting greeting = mapper.readValue(message, Greeting.class);
        if(greeting.getId() == id) {
            FileUtils.writeStringToFile(new File("./message.txt"),"", StandardCharsets.UTF_8.name());
        }
    }

    //    @RequestMapping(value = "/somegreeting", method = RequestMethod.POST)
//    public Greeting someGreeting(@RequestBody Greeting greeting){
//        return greeting;
//    }
//
//
//    @RequestMapping(value = "/gethighestgreeting", method = RequestMethod.POST)
//    public Greeting getHighestGreeting(@RequestBody List<Greeting> greetings){
//        if(greetings.size() == 0){
//            return null;
//        }
//
//        Greeting highest = greetings.get(0);
//
//        for (Greeting greeting: greetings) {
//            if(highest.getId() < greeting.getId()){
//                highest = greeting;
//            }
//        }
//        return highest;
//    }
}
