/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katniss.everdeen;

import com.rethinkdb.net.Cursor;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import katniss.everdeen.model.Country;
import katniss.everdeen.model.RestResponse;
import katniss.everdeen.model.Usuario;
import katniss.everdeen.services.rethinkdb.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author gonzalo
 */
@RestController
public class UsuarioController {

    private static final Logger LOG = Logger.getLogger(UsuarioController.class.getName());

    @Autowired private UsuariosRepository usuariosRepository;
    
    @RequestMapping("/usuario/")
    public RestResponse usuarios() {
        List<Usuario> u = usuariosRepository.all();
        return new RestResponse.Builder()
                .code(u != null && u.size() > 0 ? 200 : 404)
                .message(u != null && u.size() > 0 ? "Users found at " + new Date() : "0 Users found")
                .data(u)
                .create();
    }
    
    @RequestMapping("/usuario")
    public RestResponse usuario() {
        List<Usuario> u = usuariosRepository.all();
        return new RestResponse.Builder()
                .code(u != null && u.size() > 0 ? 200 : 404)
                .message(u != null && u.size() > 0 ? "Users found at " + new Date() : "0 Users found")
                .data(u)
                .create();
    }
    
    @RequestMapping("/usuario/username/{username}")
    public RestResponse usuario(@PathVariable String username) {
        Usuario u = usuariosRepository.byUsername(username);
        return new RestResponse.Builder()
                .code(u != null ? 200 : 404)
                .message(u != null ? "User found at " + new Date() : "0 users found")
                .data(u)
                .create();
    }
    
    @RequestMapping("/usuario/id/{id}")
    public RestResponse usuarioById(@PathVariable String id) {
        Usuario u = usuariosRepository.byId(id);
        return new RestResponse.Builder()
                .code(u != null ? 200 : 404)
                .message(u != null ? "User found at " + new Date() : "User not found with id = " + id)
                .data(u)
                .create();
    }
    
    @RequestMapping("/usuario/email/{email}")
    public RestResponse usuarioByEmail(@PathVariable String email) {
        Usuario u = usuariosRepository.byEmail(email);
        return new RestResponse.Builder()
                .code(u != null ? 200 : 404)
                .message(u != null ? "User found at " + new Date() : "User not found with email = " + email)
                .data(u)
                .create();
    }
    
    @RequestMapping(name = "/usuario/login", method = RequestMethod.POST)
    public RestResponse login(@RequestBody Usuario user) {
        if (user.getUsername() == null) {
            return new RestResponse.Builder()
                    .code(403)
                    .message("Username is mandatory field for this request")
                    .create();
        }
        if (user.getPassword() == null) {
            return new RestResponse.Builder()
                    .code(403)
                    .message("password is mandatory field for this request")
                    .create();
        }
        boolean success = usuariosRepository
                .login(user.getUsername(), user.getEmail(), user.getPassword());
        return new RestResponse.Builder()
                    .code(success? 200 : 404)
                    .message(success? "User correct (" + new Date() + ")" : "User not valid or inactive")
                    .data("Success!")
                    .create();
    }
    
    @RequestMapping("/usuario/isActive/{id}")
    public RestResponse isActive(@PathVariable String id) {
        Usuario user = new Usuario();
        user.setId(id);
        boolean isActive = usuariosRepository.isActive(user);
        
        return new RestResponse.Builder()
            .code(isActive? 200 : 400)
            .message(isActive ? "User is active at " + new Date() : "User is inactive")
            .data("Is active: " + isActive)
            .create();
    }
}
