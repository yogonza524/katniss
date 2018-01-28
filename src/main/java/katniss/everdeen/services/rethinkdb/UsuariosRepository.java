/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katniss.everdeen.services.rethinkdb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Cursor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import katniss.everdeen.model.Country;
import katniss.everdeen.model.Usuario;
import katniss.everdeen.util.Conversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

/**
 *
 * @author gonzalo
 */
@Service
public class UsuariosRepository {
    
    @Autowired RethinkdbService repository;
    
    private final String db = "katniss";
    private final String table = "usuarios";
    
    public Map insert(Usuario u) {
        if (!repository.existsTable(db, table)) {
            return null;
        }
        if (u != null) {
            return repository.insert(db, table, u);
        }
        return null;
    }
    
    public boolean remove(Usuario u) {
        if (!repository.existsTable(db, table)) {
            return false;
        }
        return u != null && u.getId() != null ? repository.delete(db, table, u.getId()) : false;
    }
    
    public boolean update(Usuario u) {
        if (!repository.existsTable(db, table)) {
            return false;
        }
        return u != null && u.getId() != null ? repository.updateAll(db, table, Conversion.toMap(Usuario.class, u)) : false;
    }
    
    public Usuario byId(Usuario u) {
        return u != null && u.getId() != null ? 
                this.byId(u.getId()) : null;
    }
    
    public Usuario byId(String id) {
        if (!repository.existsTable(db, table)) {
            return null;
        }
        if (id != null) {
            Object o = repository.byId(db, table, Usuario.class, id);
            if (o != null) {
                try {
                    return (Usuario)o;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public Usuario byUsername(String username) {
        if (username != null) {
            Object o = repository.byField(db, table, Usuario.class, "username", username);
            if (o != null) {
                try {
                    return (Usuario)o;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public Usuario byEmail(String email) {
        if (email != null) {
            Object o = repository.byField(db, table, Usuario.class, "email", email);
            if (o != null) {
                try {
                    return (Usuario)o;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public boolean isActive(Usuario u) {
        if (!repository.existsTable(db, table)) {
            return false;
        }
        if (u == null) {
            return false;
        }
        if (u.getUsername() == null) {
            return false;
        }
        if (u.getPassword() == null) {
            return false;
        }
        RethinkDB r = repository.getR();
        Cursor<HashMap> m = r.db(db).table(table)
                .filter(r.hashMap("username", u.getUsername()))
                .filter(r.hashMap("active", "true"))
                .run(repository.getRethinkDb());
        
        return m != null && m.hasNext();
    }
    
    public boolean isActive(String id) {
        if (!repository.existsTable(db, table)) {
            System.out.println("No existe la db");
            return false;
        }
        if (id == null) {
            System.out.println("No hay id");
            return false;
        }
        RethinkDB r = repository.getR();
        Cursor<HashMap> m = r.db(db).table(table)
                .filter(r.hashMap("id", id))
                .filter(r.hashMap("active", true))
                .run(repository.getRethinkDb());
        
        System.out.println("Size: " + m.toList().size());
        
        return m != null && m.hasNext();
    }
    
    public boolean existUser(Usuario u) {
        if (!repository.existsTable(db, table)) {
            return false;
        }
        if (u == null) {
            return false;
        }
        if (u.getUsername() == null) {
            return false;
        }
        if (u.getPassword() == null) {
            return false;
        }
        if (u.getEmail() == null) {
            return false;
        }
        RethinkDB r = repository.getR();
        Cursor<HashMap> m = r.db(db).table(table)
                .filter(r.hashMap("email", u.getEmail()))
                .run(repository.getRethinkDb());
        
        return m != null && m.hasNext();
    }
    
    public boolean login(String username, String email, String password) {
        if (!repository.existsTable(db, table)) {
            return false;
        }
        if (username == null && email == null) {
            return false;
        }
        if (password == null) {
            return false;
        }
        RethinkDB r = repository.getR();
        Cursor<HashMap> m = r.db(db).table(table)
                .filter(row -> row.g("username").eq(username)
                        .or(row.g("email").eq(email)))
                .filter(r.hashMap("password", password))
                .run(repository.getRethinkDb());
        
        return m != null && m.hasNext();
    }

    public List<Usuario> all() {
        Cursor<HashMap> cur = repository.getR().db(db).table(table).run(repository.getRethinkDb());
        
        if (cur != null && cur.hasNext()) {
            List<Usuario> result = new ArrayList<>();
            cur.forEach(c -> {
                try {
                    result.add((Usuario)Conversion.toEntity(Usuario.class, c));
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                    Logger.getLogger(UsuariosRepository.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return result;
        }
        
        return null;
    }
}
