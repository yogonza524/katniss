/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.ast.Filter;
import com.rethinkdb.gen.ast.ReqlFunction0;
import com.rethinkdb.gen.ast.Update;
import com.rethinkdb.model.MapObject;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import katniss.everdeen.model.Usuario;
import katniss.everdeen.util.Conversion;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
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
public class Rethinkdbtest {
    
    private final RethinkDB r = RethinkDB.r;
    private Connection conn;
    
    public Rethinkdbtest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        conn = r.connection().hostname("localhost").port(28015).connect();
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     @Ignore
     public void createDbifNotExistsTest() {
         System.out.println(createDbIfNotExists("katniss"));
     }
     
     @Test
     @Ignore
     public void createTableTest() {
        //Create Table
        Map o = (Map)createTableIfNotExists("katniss", "usuarios");
         
        o.forEach((key,value) -> {
            System.out.println(key + ": " + value);
        });
     }
     
     @Test
     @Ignore
     public void insertTest() {
         Usuario u = new Usuario();
         u.setActive(true);
         u.setUsername("gonzalo");
         u.setPassword("1234");
         u.setTel("3794267413");
         u.setCountry("Argentina");
         
         insert("katniss", "usuarios", u);
     }
     
     @Test
     @Ignore
     public void findTest() {
         String key = "username";
         String username = "Gonza";
         
         List<Usuario> users = findList("katniss", "usuarios", Usuario.class, key, username);
         
         users.forEach(u -> {
            System.out.println(u.getUsername());
         });
     }
     
     @Test
     @Ignore
     public void byIdTest() {
         String id = "0a0d410d-2cab-4ac8-a9ba-fac3918e329";
         
         System.out.println("-> " + byId("katniss", "usuarios", Usuario.class, id));
     }
     
     @Test
//     @Ignore
     public void updateTest() {
         Usuario u = new Usuario();
         u.setId("9849f4d1-2b69-41e8-9677-dbccef41f2c7");
         u.setActive(true);
         u.setUsername("Gonzalo Genial");
         u.setPassword("1234");
         u.setTel("3794267413");
         u.setCountry("Argentina");
         u.setBirthday(new Date());
         
        boolean result = updateAll("katniss", "usuarios", Conversion.toMap(Usuario.class, u));
        
        System.out.println("Updated: " + result);
     }
     
     @Test
     @Ignore
     public void updateAllTest() {
         Usuario u = new Usuario();
         u.setId("e9e34c11-dba3-42d8-a039-bbfd74e3cf67");
         u.setActive(true);
         u.setUsername("Nuevo");
         u.setPassword("111111111111");
         u.setTel("1");
         u.setCountry("Pepe");
         
         System.out.println("Updated all: " + updateAll("katniss", "usuarios", Conversion.toMap(Usuario.class, u)));
     }
     
     @Test
     @Ignore
     public void deleteTest() {
         System.out.println("Deleted: " + delete("katniss", "usuarios", "fcfb213c-a307-4651-be57-f24303c01958"));
     }
     
     @Test
     @Ignore
     public void checkDbExists() {
//         List result = r.dbList().run(conn);
//         result.forEach(u -> {
//             System.out.println(u);
//         });
        List result = r.dbList().run(conn);
        
        System.out.println("Exists: " + (result.stream().filter(db -> db.equals("katniss")).count() > 0));
     }
     
     @Test
     @Ignore
     public void tableExistsTest() {
         System.out.println("Exists: " + existsTable("katniss", "usuarios"));
     }
     
     @Test
     @Ignore
     public void usuarioActiveTest() {
         Cursor<HashMap> m = r.db("katniss").table("usuarios")
                .filter(r.hashMap("username", "gonzalo"))
                .filter(r.hashMap("password", "1234"))
                .filter(r.hashMap("active", "true"))
                .run(conn);
         if (m != null && m.hasNext()) {
             System.out.println(m.next());
         }
     }
     
     @Test
     @Ignore
     public void loginTest() {
         System.out.println("Login: " + login("gonzalo", "sasasdsdf", "1234"));
     }
     
     public boolean login(String username, String email, String password) {
        if (username == null && email == null) {
            return false;
        }
        if (password == null) {
            return false;
        }
        Cursor<HashMap> m = r.db("katniss").table("usuarios")
                .filter(row -> row.g("username").eq(username)
                        .or(row.g("email").eq(email)))
                .filter(r.hashMap("password", password))
                .run(conn);
        
        return m != null && m.hasNext();
    }
     
     public boolean existsTable(String db, String table) {
        List result = r.db(db).tableList().run(conn);
        
        result.forEach(t -> {
            System.out.println(t);
        });
        
        return result.stream().filter(t -> t.equals(table)).count() > 0;
    }
     
     public boolean update(String db, String table, String id, String key, Object value){ 
         Map result = r.db(db).table(table).filter(r.hashMap("id", id)).update(r.hashMap(key, value)).run(conn);
         return Integer.valueOf(result.get("replaced").toString()) > 0;
     }
     
     public boolean updateAll(String db, String table, Map<String,Object> values){ 
        if (values == null) {
            return false;
        } 
        if (values.get("id") == null) {
            return false;
        }
        
        Map result = r.db(db).table(table).filter(r.hashMap("id", values.get("id"))).update(values).run(conn);
        return !result.isEmpty() && Integer.valueOf(result.get("replaced").toString()) > 0;
    }
     
     public boolean delete(String db, String table, String id){ 
         Map result = r.db(db).table(table).filter(r.hashMap("id", id)).delete()
                .optArg("return_changes", true) 
                .run(conn);
         return Integer.valueOf(result.get("deleted").toString()) > 0;
     }
     
     private Object createDbIfNotExists(String name) {
         return r.dbList().contains(name)
            .do_((ReqlFunction0) () -> r
                .branch(
                    r.dbList().contains(name),
                    r.hashMap("db_already_exists", "yes"), 
                    r.dbCreate("katniss")
                )
            )
            .run(conn);
     }
     
     private HashMap createTableIfNotExists(String db,String name) {
         return r.db(db).tableList().contains(name)
            .do_((ReqlFunction0) () -> r
                .branch(
                    r.db(db).tableList().contains(name), 
                    r.hashMap("table_already_exists", "yes"), 
                    r.db(db).tableCreate(name)
                )
            ).run(conn);
     }
     
     public Map insert(String db, String table, Usuario u) {
        //Verify table
        this.createTableIfNotExists(db, table);
        
        HashMap<String,Object> map = null;
        try {
            map = (HashMap<String,Object>) BeanUtils.describe(u);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(Rethinkdbtest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return r
            .db(db)
            .table(table)
            .insert(map)
            .run(conn);
    }
     
     public List findList(String db, String table, Class type, Object key, Object value) {
         Cursor<HashMap> cur = r.db(db).table(table).filter(row -> row.g(key).match(value)).run(conn);
         List result = new ArrayList();
         cur.forEach(u -> {
             try {
                 result.add(convert(Usuario.class, u));
             } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                 Logger.getLogger(Rethinkdbtest.class.getName()).log(Level.SEVERE, null, ex);
             }
         });
         
         return result;
     }
     
     public Object byId(String db, String table, Class type, String value) {
        Cursor<HashMap> cur = r.db(db).table(table).filter(r.hashMap("id", value)).run(conn);
        
        if (cur.hasNext()) {
            try {
                return Conversion.toEntity(type, cur.next());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(Rethinkdbtest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
     }
     
     public Object convert(Class type, HashMap c) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Gson g = new Gson();
        String json = g.toJson(c);
        
        return g.fromJson(json, type);
     }
}
