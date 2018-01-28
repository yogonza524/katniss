/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import katniss.everdeen.model.Country;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author gonzalo
 */
public class CountryTest {
    
    public CountryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     @Ignore
     public void listAllTest() throws FileNotFoundException {
        Type REVIEW_TYPE = new TypeToken<List<Country>>() {
        }.getType();
        Gson gson = new Gson();
        
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("json/countries.json").getFile());
        
        JsonReader reader = new JsonReader(new FileReader(file));
        List<Country> data = gson.fromJson(reader, REVIEW_TYPE);
        
        for(Country c : data) {
            System.out.println(c.getName());
        }
     }
}
