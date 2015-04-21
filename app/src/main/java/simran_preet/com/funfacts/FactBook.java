package simran_preet.com.funfacts;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by jc on 12/20/14.
 */
public class FactBook {
    private static final String TAG = "FactBook";
    private static FactBook instance = null;
    private static List<String> factsList;
    private static Map<String, String> factsMap;

    public static FactBook getInstance() {

        if (instance == null) {
            instance = new FactBook();
            factsList = new ArrayList<String>();
            factsMap = new HashMap<String, String>();
        }
        return instance;
    }

    public void retrieveFactsFromParse() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("FactsDB");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> factList, ParseException e) {
                if (e == null) {
                    for (final ParseObject parseObject : factList) {
                        String fact = parseObject.get("fact").toString();
                        String id = parseObject.getObjectId();
                        factsList.add(fact);
                        factsMap.put(id, fact);
                    }

                } else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }


    private static String[] facts = {
            "Ants stretch when they wake up in the morning.",
            "Ostriches can run faster than horses.",
            "Olympic gold medals are actually made mostly of silver.",
            "You are born with 300 bones; by the time you are an adult you will have 206.",
            "It takes about 8 minutes for light from the Sun to reach Earth.",
            "Some bamboo plants can grow almost a meter in just one day.",
            "The state of Florida is bigger than England.",
            "Some penguins can leap 2-3 meters out of the water.",
            "On average, it takes 66 days to form a new habit.",
            "Mammoths still walked the earth when the Great Pyramid was being built.",
            "Ketchup was sold in the 1830s as medicine.",
            "The Amazon is the world’s largest river, 3,890 miles (6,259 km) long.",
            "Grapes explode when you put them in the microwave. Please do not try this at your " +
                    "home",
            "A pound of houseflies contains more protein than a pound of beef",
            "Nepal is the only country that doesn’t have a rectangular flag.",
            "Nerve impulses to and from the brain travel as fast as 170 miles (274 km) per hour.",
            "In May 1948, Mt Ruapehu and Mt Ngauruhoe, both in New Zealand, " +
                    "erupted simultaneously.",
            "A rhinoceros horn is made of compacted hair.",
            "The international telephone dialing code for Antarctica is 672.",
            "It’s illegal to spit on the sidewalk in Norfolk, Virginia.",
            "In the four professional major north american sports (baseball, basketball, " +
                    "football and hockey) only 7 teams have nicknames that do not end with an s.",
            "A lion in the wild usually makes no more than 20 kills a year.",
            "A rhinoceros horn is made of compacted hair.",
            "The international telephone dialing code for Antarctica is 672.",
            "It’s illegal to spit on the sidewalk in Norfolk, Virginia.",
            "Woodpecker scalps, porpoise teeth, and giraffe tails have all been used as money.",
            "Shakespeare invented the words “assassination” and “bump.”",
            "There is no solid proof of who built the Taj Mahal.",
            "Australian Rules football was originally designed to give cricketers something to " +
                    "play during the off season.",
            "Australian Rules football was originally designed to give cricketers something to " +
                    "play during the off season.",
            "Camels chew in a figure 8 pattern.",
            "India has a Bill of Rights for cows.",
            "Jackals have one more pair of chromosomes than dogs or wolves.",
            "Dartboards are made out of horse hairs.",
            "The average life of a taste bud is 10 days.",
            "In 1980, a Las Vegas hospital suspended workers for betting on when patients would " +
                    "die.",
            "Dibble means to drink like a duck.",
            "It was once against the law to have a pet dog in a city in Iceland.",
            "A B-25 bomber crashed into the 79th floor of the Empire State Building on July 28, " +
                    "1945.",
            "A giraffe can clean its ears with its 21-inch tongue!",
            "In France, there’s a place called Y.",
            "One in every 4 Americans has appeared on television at least once in their life.",
            "In ancient Rome, when a man testified in court he would swear on his testicles.",
            "The average human will shed 40 pounds of skin in a lifetime.",
            "A Virginia law requires all bathtubs to be kept inside the house",
            "Truck driving is the most dangerous occupation by accidental deaths (799 in 2001).",
            "A group of toads is called a knot.",
            "At any given time, there are at least 1,800 thunderstorms in progress over the " +
                    "earth’s atmosphere.",
            "The top butterfly flight speed is 12 miles per hour. Some moths can fly 25 miles per" +
                    " hour!",
            "More than 30% of the people in the world have never made or received a telephone " +
                    "call.",
            "Termites have been known to eat food twice as fast when heavy metal music is playing.",
            "All female bees in a given hive are sisters.",
            "Owls are the only birds that can see the color blue.",
            "The Automated Teller Machine (ATM) was introduced in England in 1965."
            // http://uselessfacts.net/page/4/
    };


    public Map<String, String> getFacts() {
        return factsMap;
    }

    public Fact getRandomFact() {

        Fact fact = new Fact();

        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(factsMap.size());
        List<String> keys = new ArrayList<String>(getFacts().keySet());
        String randomKey = keys.get(randomNumber);
        String factTitle = getFacts().get(randomKey);
        String factId = keys.get(randomNumber);
        fact.setFact(factTitle);
        fact.setObjectId(factId);

        return fact;
    }


}
