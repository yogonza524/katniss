/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katniss.everdeen;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import katniss.everdeen.model.Country;
import katniss.everdeen.model.ArgentinaLocation;
import katniss.everdeen.services.LocalidadesArgentinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeoController {

    private static final Logger LOG = Logger.getLogger(GeoController.class.getName());
    
    @Autowired private List<Country> paises;
    @Autowired private LocalidadesArgentinaService localidadesArgentinaService;
    
    @Autowired private HttpServletRequest context;

    @RequestMapping("/paises")
    public List<Country> paises() {
        return paises;
    }
    
    @RequestMapping("/paises/{nombre}")
    public List<Country> paisesByNombre(@PathVariable String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            return paises.stream().filter(p -> p.getName().contains(nombre)).collect(Collectors.toList());
        }
        return paises;
    }
    
    @RequestMapping("/argentina/provincia/")
    public List<ArgentinaLocation> provincias() {
        return localidadesArgentinaService.all();
    }
    
    @RequestMapping("/argentina/provincia/{name}")
    public List<ArgentinaLocation> provinciasArgentina(@PathVariable String name) {
        if (name != null && !name.isEmpty()) {
            return localidadesArgentinaService.byProvince(name);
        }
        return localidadesArgentinaService.all();
    }
    
    @RequestMapping("/argentina/localidad")
    public List<ArgentinaLocation> localidades() {
        return localidadesArgentinaService.all();
    }
    
    @RequestMapping("/argentina/localidad/{name}")
    public List<ArgentinaLocation> localidadesArgentina(@PathVariable String name) {
        if (name != null && !name.isEmpty()) {
            return localidadesArgentinaService.byLocalidad(name);
        }
        return localidadesArgentinaService.all();
    }
}