package com.edigest.mysecondproject.cache;

import com.edigest.mysecondproject.entity.ConfigJournalAppEntity;
import com.edigest.mysecondproject.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//most frequent used data like api url in case inko  db me store karane ke liye used caching so that connect adhik na badhe
// we can either store in propertis and use @value to inject value otherwise store in db and use postconstruct caching to avoid hardcodinbg of any essential or frequnt used data
@Component  //used to creatre bean
public class AppCache {

    @Autowired  // used to inject bean not create beanm
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String , String>  appCache ;
//bean create hone ke just baad koi method run karna ho to postconstruct use kate hai uss m,ethod npe
    @PostConstruct  //it runs only once after bean creation and every tyime will give same intialized object and
    //if want differnet every time so to have to start again project ot get updated data
    // so avoiding restart manually everytiume will use controller , expose an endpoitn (call this method in it) which will automatically restart this method
    // after each call on taht endpoint and give updated value from db


    public void init(){  // ye method db se data larah and entry karrha
        appCache = new HashMap<>();  //on every start initialize new empty map to update the new entry for clearing old cache
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity : all) {
            appCache.put(configJournalAppEntity.getKey() , configJournalAppEntity.getValue());  // key value jo stored loye bhai db ,me repository ke help se uthake appcache mestore kararhe
        }

    }

}

/* how postconstruct method run sutomatic after bean creation(seeing @component)
Spring starts the application.
Spring sees @Component on AppCache and creates its object (bean).
Spring injects configJournalAppRepository using @Autowired.
Then @PostConstruct (init()) runs automatically once.
After that, AppCache is ready, and whenever another class uses @Autowired AppCache,
 it gets the same initialized object.

 When Spring sees @Component:

✅ Creates AppCache
✅ Injects configJournalAppRepository
✅ Calls init() automatically
✅ Cache is ready before your application starts handling requests
 */