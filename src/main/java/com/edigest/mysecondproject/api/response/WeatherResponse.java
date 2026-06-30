package com.edigest.mysecondproject.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.coyote.Request;

import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.List;


// pojo (plane old java object) me so jo chahiye usek accordiung ham yaha remove AND ADD kar sakte hai
//website ke api url run karke waha se json ko pojo me convert karke paste here
@Data
public class WeatherResponse {


    private Current current;




@Data
/* 🧠 Why getters/setters are needed here
1. 🔄 JSON → Java mapping (MOST IMPORTANT)

Weather API returns JSON like:

{
  "current": {
    "temperature": 33,
    "feelslike": 40
  }
}

Spring (Jackson) needs:

👉 setters to PUT data into Java object

setTemperature(33)
setFeelslike(40)

Without setters:
❌ data will NOT be mapped
❌ fields will stay null

 */
    public class Current {
        @JsonProperty("observation_time")   // @JsonProperty is used to replace the camel case name in object and redirect to actual one
        private String observationTime;

        private Integer temperature;

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;

        private Integer feelslike;


    }
}

