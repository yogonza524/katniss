/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katniss.everdeen.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import katniss.everdeen.model.ArgentinaLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalidadesArgentinaService {
    
    @Autowired private List<ArgentinaLocation> localidadesArgentina;
//    @Autowired private LocalidadArgentina localidadNoEncontrada;
    
    public List<ArgentinaLocation> byProvince(String name) {
        List<ArgentinaLocation> prov = localidadesArgentina
                .stream()
                .filter(localidad -> localidad.getProvincia().contains(name))
                .collect(Collectors.toList());
        return prov;
    }
    
    public List<ArgentinaLocation> byLocalidad(String name) {
        List<ArgentinaLocation> loc = localidadesArgentina
                .stream()
                .filter(
                    localidad -> localidad.getLocalidad()
                        .stream()
                        .filter(l -> l.contains(name))
                        .count() > 0
                )
                .collect(Collectors.toList());
        return loc;
    }
    
    public List<ArgentinaLocation> all() {
        return this.localidadesArgentina;
    }
}
