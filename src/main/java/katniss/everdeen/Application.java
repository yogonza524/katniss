/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katniss.everdeen;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import katniss.everdeen.model.Country;
import katniss.everdeen.model.ArgentinaLocation;
import katniss.everdeen.model.MarketType;
import katniss.everdeen.model.PublicationState;
import katniss.everdeen.services.LocalidadesArgentinaService;
import katniss.everdeen.services.rethinkdb.UsuariosRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = UsuarioController.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    List<Country> paises() {
        Type REVIEW_TYPE = new TypeToken<List<Country>>() {
        }.getType();
        Gson gson = new Gson();
        
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("json/countries.json").getFile());
        
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reader != null? gson.fromJson(reader, REVIEW_TYPE) : null;
    }
    
    @Bean
    List<ArgentinaLocation> localidadesArgentina() {
        Type REVIEW_TYPE = new TypeToken<List<ArgentinaLocation>>() {
        }.getType();
        Gson gson = new Gson();
        
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("json/argentina.json").getFile());
        
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reader != null? gson.fromJson(reader, REVIEW_TYPE) : null;
    }
    
    @Bean
    List<MarketType> markets() {
        List<MarketType> result = new ArrayList<>();
        result.add(new MarketType("Autos usados"));
        result.add(new MarketType("Autos nuevos"));
        result.add(new MarketType("Motos y bicicletas"));
        result.add(new MarketType("Alquileres"));
        result.add(new MarketType("Inmobiliaria Compra venta"));
        result.add(new MarketType("Compra Venta"));
        result.add(new MarketType("Ofrecidos"));
        result.add(new MarketType("Enseñanza"));
        result.add(new MarketType("Materiales de Contrucción"));
        result.add(new MarketType("Computación"));
        result.add(new MarketType("Telefonía"));
        result.add(new MarketType("Profesionales"));
        result.add(new MarketType("Articulos Varios"));
        result.add(new MarketType("Empleos"));
        result.add(new MarketType("Supermercados"));
        result.add(new MarketType("Mayoristas"));
        result.add(new MarketType("Talleres Repuestos y Accesorios"));
        result.add(new MarketType("Nautica"));
        result.add(new MarketType("Personal Doméstico"));
        result.add(new MarketType("Refacciones del hogar"));
        
        Collections.sort(result, (MarketType t, MarketType t1) -> t.getName().compareTo(t1.getName()));
        
        return result;
    }
    
    @Bean
    List<PublicationState> publicationStates() {
        List<PublicationState> result = new ArrayList<>();
        
        result.add(new PublicationState("Expirado"));
        result.add(new PublicationState("Patrocinado"));
        result.add(new PublicationState("Activo"));
        result.add(new PublicationState("Inactivo"));
        result.add(new PublicationState("Cancelado"));
        
        return result;
    }
    
    @Bean
    LocalidadesArgentinaService localidadesArgentinaService() {
        return new LocalidadesArgentinaService();
    }
    
    /*
    RethinkDB 
    */
    @Bean
    Connection rethinkDb() {
        RethinkDB r = RethinkDB.r;
        return r.connection().hostname("localhost").port(28015).connect();
    }
    
    @Bean
    UsuariosRepository usuariosRepository() {
        return new UsuariosRepository();
    }
}
