/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katniss.everdeen.util;

import com.google.gson.Gson;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 *
 * @author gonzalo
 */
public class Conversion {
    
    public static Object toEntity(Class type, HashMap c) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Gson g = new Gson();
        String json = g.toJson(c);
        
        return g.fromJson(json, type);
    }
    
    public static Map toMap(Class type, Object o) {
        Gson g = new Gson();
        String json = g.toJson(o);
        
        return g.fromJson(json, HashMap.class);
    }
    
    public static String toGMT(Date date) {
        final Date currentTime = new Date();

        final SimpleDateFormat sdf =
                new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a z");

        // Give it to me in GMT time.
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(currentTime);
    }
}
