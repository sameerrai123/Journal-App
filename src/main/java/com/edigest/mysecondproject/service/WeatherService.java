package com.edigest.mysecondproject.service;

import com.edigest.mysecondproject.api.response.WeatherResponse;
import com.edigest.mysecondproject.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Value("${weather.api.key}")    //weather.api.key is property name where api key is stored

    private  String apikey ;   // not static bcoz spring agar isse chhedega to puri class me may be galti hisakti bcoz statc belongs to class not object

//    private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
@Autowired
private AppCache appcache;

@Autowired
private RedisService redisService;

    @Autowired
    private RestTemplate restTemplate;   // restTemplate is used to hit http request on api from code ,, using it automate kardega request ko bar bar browser pe na karne apde suatomatic data fetch karega and yaha embedd kardega
       //it prcoess request and give us response

            public WeatherResponse getWeather(String city) {
                WeatherResponse weatherResponse = redisService.get("Weather of " + city, WeatherResponse.class);
                if (weatherResponse != null) {
                    return weatherResponse;
                } else {
                    String finalAPI = appcache.appCache.get("weather_api").replace("<city>", city).replace("<apikey>", apikey);   //final url of each request
                    ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);   // null bcoz enitiyrequest(header(extra info) , body) is null we are getting data not posting or sending values to fetch data accrodinglt and weatherResponse.class is class used to convert json in pojo created in api.response package
                    //httpheader is used here for extra info like in some cases in documentation its mentioned send api key thorugh header not api url in that case use it
                    //jo json api request see aayega woh Weatherresponse.class ke format me yani pjo(java object) me convert hojayega (deseriazlization)
                    //Get use karte hai jab hame kuchh retrive karna ho and post use karte hai jab hame kuchh url ke sath data send karna ho and usse process karna ho
                    WeatherResponse body = response.getBody();
                    if(body != null){
                        redisService.set("Weather of " + city , body ,300l);
                    }
                    return body;
                }
            }
            //website pe api documentaion me given hota hai like access key me api key dalna or query me city name dalana hai wagera
}


/*   sending post request on api (we can send request body and headers alomng with request) using post requet


   public WeatherResponse getWeather(String city){
                String finalAPI = API.replace("CITY" , city).replace("API_KEY", apikey);   //final url of each request

               String requestbody = "{json body}"; //(used to send json info along with post) requestbody if want to send, pass in httpentity to convert


              HttpHeaders httpheaders = new HttpHeaders();
              httpheaders.set("key", "value");
              //Header (additional information) often used to send api on some website by header
               User user = User.builder().userName("sam").password("sam").build();
                HttpEntity<User> httpentity = new HttpEntity<>(user , httpheader);



                ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI , HttpMethod.POST , httpentity , WeatherResponse.class );   // null bcoz enitiyrequest(header(extra info) , body) is null we are getting data not posting or sending values to fetch data accrodinglt and weatherResponse.class is class used to convert json in pojo created in api.response package
                //httpheader is used here for extra info like in some cases in documentation its mentioned send api key thorugh header not api url in that case use it
                //jo json api request see aayega woh Weatherresponse.class ke format me yani pjo(java object) me convert hojayega (deseriazlization)
              //Get use karte hai jab hame kuchh retrive karna ho and post use karte hai jab hame kuchh url ke sath data send karna ho and usko process kArke result lena ho
                WeatherResponse body = response.getBody();
                return body;
            }
            */



/*

@Service
public class ElevenLabsService {

    @Value("${elevenlabs.api.key}")
    private String apiKey;

    private static final String API =
            "https://api.elevenlabs.io/v1/text-to-speech/VOICE_ID";

    @Autowired
    private RestTemplate restTemplate;

    public byte[] generateAudio(String text) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("xi-api-key", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);  // tells we sending json data

        String body = "{\"text\":\"" + text + "\"}";  //jo bhi trext inpur karenge woh post req ke sath bhejenge , spi process karega then output retrive kardega

        HttpEntity<String> entity =
                new HttpEntity<>(body, headers);

        ResponseEntity<byte[]> response =
                restTemplate.exchange(
                        API,
                        HttpMethod.POST,  // post use karte hai jab khud se data send kare yani custom data ko process  kar output retrive karna ho
                        entity,
                        byte[].class);  // output we want in mp3 not in java object(pojo) so using byte[].class and not creating any class like weather one

        byte[] audio = response.getBody();

        Files.write(
    Paths.get("journal.mp3"),
    audio
);
    }
}
 */