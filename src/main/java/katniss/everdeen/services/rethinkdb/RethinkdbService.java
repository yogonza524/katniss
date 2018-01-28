/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katniss.everdeen.services.rethinkdb;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.ast.Filter;
import com.rethinkdb.gen.ast.ReqlFunction0;
import com.rethinkdb.gen.ast.Update;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import katniss.everdeen.model.Usuario;
import katniss.everdeen.util.Conversion;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gonzalo
 */
@Service
public class RethinkdbService {
    
    @Autowired private Connection rethinkDb;
    
    private final RethinkDB r = RethinkDB.r;
    
    public HashMap createDbIfNotExist(String name) {
        return r.dbList().contains(name)
                .do_((ReqlFunction0) () -> r.branch(
                    r.dbList().contains(name),
                    r.hashMap("db_" + name +"_already_exists", "yes"),
                    r.dbCreate(name)))
                .run(rethinkDb);
    }
    
    public HashMap createTableIfNotExists(String db,String name) {
        this.createDbIfNotExist(db);
        return r.db(db).tableList().contains(name)
            .do_((ReqlFunction0) () -> r
                .branch(
                    r.db(db).tableList().contains(name), 
                    r.hashMap("table_already_exists_in_" + db, "yes"), 
                    r.db(db).tableCreate(name)
                )
            ).run(rethinkDb);
     }

    public Connection getRethinkDb() {
        return rethinkDb;
    }

    public RethinkDB getR() {
        return r;
    }
    
    public Map insert(String db, String table, Object o) {
        //Verify table
        createTableIfNotExists(db, table);
        
        //Instance map for conversion
        HashMap<String,Object> map = null;
        try {
            map = (HashMap<String,Object>) BeanUtils.describe(o);
        } catch (IllegalAccessException   | 
                InvocationTargetException | 
                NoSuchMethodException ex) 
        {
            Logger.getLogger(
                RethinkdbService.class.getName()).log(Level.SEVERE,
                null, 
                ex
            );
        }
        
        return r
            .db(db)
            .table(table)
            .insert(map)
            .run(rethinkDb);
    }
    
    public List findLike(String db, String table, Class type, Object key, Object value) {
         Cursor<HashMap> cur = r
                .db(db)
                .table(table)
                .filter(row -> row.g(key).match(value))
                .run(rethinkDb);
         List result = new ArrayList();
         cur.forEach(u -> {
             try {
                 result.add(Conversion.toEntity(Usuario.class, u));
             } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                 Logger.getLogger(RethinkdbService.class.getName()).log(Level.SEVERE, null, ex);
             }
         });
         
         return result;
     }
    
    public Object byId(String db, String table, Class type, String value) {
        Cursor<HashMap> cur = r.db(db).table(table).filter(r.hashMap("id", value)).run(rethinkDb);
        
        if (cur.hasNext()) {
            try {
                return Conversion.toEntity(type, cur.next());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(RethinkdbService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
     }
    
    public Object byField(String db, String table, Class type, String field, String value) {
        if (!existsTable(db, table)) {
            return new Object();
        }
        
        Cursor<HashMap> cur = r.db(db).table(table)
                .filter(r.hashMap(field, value))
                .run(rethinkDb);
        
        if (cur.hasNext()) {
            try {
                return Conversion.toEntity(type, cur.next());
            } catch (
                    InstantiationException | 
                    IllegalAccessException | 
                    InvocationTargetException ex
            ) {
                Logger.getLogger(RethinkdbService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public boolean update(String db, String table, String id, String key, Object value){ 
         Map result = r.db(db).table(table).filter(r.hashMap("id", id)).update(r.hashMap(key, value)).run(rethinkDb);
         return Integer.valueOf(result.get("replaced").toString()) > 0;
    }
    
    public boolean updateAll(String db, String table, Map<String,Object> values){ 
        if (values == null) {
            return false;
        } 
        if (values.get("id") == null) {
            return false;
        }
        
        Map result = r.db(db).table(table).filter(r.hashMap("id", values.get("id"))).update(values).run(rethinkDb);
        return result != null && !result.isEmpty() && Integer.valueOf(result.get("replaced").toString()) > 0;
    }
    
    public boolean delete(String db, String table, String id){ 
         Map result = r.db(db).table(table).filter(r.hashMap("id", id)).delete().run(rethinkDb);
         return Integer.valueOf(result.get("deleted").toString()) > 0;
    }
    
    public Map deleteWithHistory(String db, String table, String id){ 
        return r.db(db).table(table).filter(r.hashMap("id", id)).delete()
            .optArg("return_changes", true) 
            .run(rethinkDb);
    }
    
    public boolean existsDb(String name) {
        List result = r.dbList().run(rethinkDb);
        
        return result.stream().filter(db -> db.equals(name)).count() > 0;
    }
    
    public boolean existsTable(String db, String table) {
        if (!existsDb(db)) {
            return false;
        }
        
        List result = r.db(db).tableList().run(rethinkDb);
        
        return result.stream().filter(t -> t.equals(table)).count() > 0;
    }
}
