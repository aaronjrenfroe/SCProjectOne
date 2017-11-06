package com.aaronr.googlespringone;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.CharEncoding;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AaronR on 10/17/17.
 * for ?
 */
@RestController
public class HelloController {

    private String fileLocation = "./";
    private String fileName = "inventory.txt";


    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) throws IOException {
        if (vehicle.getMakeModel() != null) {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(vehicle);
            FileUtils.writeStringToFile(new File(fileLocation + fileName), json + "\n", true);
            return vehicle;
        }
        return null;
    }

    @RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) throws IOException {

        // ObjectMapper provides functionality for reading and writing json
        ObjectMapper mapper = new ObjectMapper();

        List<String> jsonStrings = FileUtils.readLines(new File(fileLocation + fileName), CharEncoding.UTF_8);
        for (String json : jsonStrings) {
            // deserialize JSON to vehicle Object
            Vehicle vehicle = mapper.readValue(json, Vehicle.class);
            if (vehicle.getId() == id) {
                return vehicle;
            }
        }
        return null;

    }

    @RequestMapping(value = "/updateVehicle", method = RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle vehicle) throws IOException {

        Vehicle v = updateOrDelete(vehicle, true);
        return v;
    }

    @RequestMapping(value = "/deleteVehicle/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") int id) throws IOException {
        // ObjectMapper provides functionality for reading and writing json
        HttpHeaders responseHeaders = new HttpHeaders();

        Vehicle v = updateOrDelete(new Vehicle(id, "", 0, 0.0), false);
        if (v!=null){
            return new ResponseEntity("Deleted Vehicle With ID: " + id,responseHeaders,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity("ID Not Found:" + id,responseHeaders,HttpStatus.ACCEPTED);
    }

    private Vehicle updateOrDelete(Vehicle vehicle, boolean update) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        List<String> jsonStrings = FileUtils.readLines(new File(fileLocation + fileName), CharEncoding.UTF_8);
        if (jsonStrings.size() > 0) {

            for (int i = 0; i < jsonStrings.size(); i++) {

                String json = jsonStrings.get(i);
                // deserialize JSON to vehicle Object
                Vehicle curVehicle = mapper.readValue(json, Vehicle.class);
                if (curVehicle.getId() == vehicle.getId()) {
                    jsonStrings.remove(i);
                    if (update) {
                        String newJson = mapper.writeValueAsString(vehicle);
                        jsonStrings.add(i, newJson);
                    }
                    StringBuilder sb = new StringBuilder();
                    for (String jsonTemp : jsonStrings) {
                        sb.append(jsonTemp + "\n");
                    }
                    FileUtils.writeStringToFile(new File(fileLocation + fileName), sb.toString(), false);
                    return vehicle;
                }
            }
        }

        return null;
    }

    @RequestMapping(value = "/getLatestVehicle", method = RequestMethod.GET)
    public List<Vehicle> getLatestVehicle() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> jsonStrings = FileUtils.readLines(new File(fileLocation + fileName), CharEncoding.UTF_8);
        List<Vehicle> vList = new ArrayList();
        if (jsonStrings.size() > 0) {
            int max = jsonStrings.size();
            int min = Math.max(0,max - 10);

            for (String json : jsonStrings.subList(min,max)) {
                vList.add(mapper.readValue(json, Vehicle.class));
            }
        }
        return vList;
    }

}