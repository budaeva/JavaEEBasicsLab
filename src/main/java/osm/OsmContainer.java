package osm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class OsmContainer {
    Map<String, Integer> userChanges = new HashMap<>();
    Map<String, Integer> keysCount = new TreeMap<>();
    Logger logger = LoggerFactory.getLogger(OsmContainer.class);

    public Map<String, Integer> getKeysCount() {
        logger.info("getKeysCount");
        return keysCount;
    }

    public Map<String, Integer> getUserChanges() {
        logger.info("getUserChanges");
        return userChanges;
    }

    public void addUserChange(String user) {
//        logger.info("addUserChange");
        if (userChanges.containsKey(user)) {
            int oldValue = userChanges.get(user);
            userChanges.put(user, oldValue+1);
        }
        else
            userChanges.put(user, 1);
    }

    public void addKeyCount(String key) {
//        logger.info("addKeyCount");
        if (keysCount.containsKey(key)) {
            int oldValue = keysCount.get(key);
            keysCount.put(key, oldValue+1);
        }
        else
            keysCount.put(key, 1);
    }

    public List<Map.Entry<String, Integer>> getUserChangesSortedByValue() {
        List<Map.Entry<String, Integer> > list =
                new ArrayList<>(userChanges.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);
        return list;
    }


}
