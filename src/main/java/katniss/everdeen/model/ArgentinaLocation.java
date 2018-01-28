/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katniss.everdeen.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author gonzalo
 */
public class ArgentinaLocation implements Serializable{
    
    private String iso_31662;
    private String provincia;
    private String capital;
    private List<String> localidad;

    public String getIso_31662() {
        return iso_31662;
    }

    public void setIso_31662(String iso_31662) {
        this.iso_31662 = iso_31662;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public List<String> getLocalidad() {
        return localidad;
    }

    public void setLocalidad(List<String> localidad) {
        this.localidad = localidad;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.iso_31662);
        hash = 89 * hash + Objects.hashCode(this.provincia);
        hash = 89 * hash + Objects.hashCode(this.capital);
        hash = 89 * hash + Objects.hashCode(this.localidad);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ArgentinaLocation other = (ArgentinaLocation) obj;
        if (!Objects.equals(this.iso_31662, other.iso_31662)) {
            return false;
        }
        if (!Objects.equals(this.provincia, other.provincia)) {
            return false;
        }
        if (!Objects.equals(this.capital, other.capital)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LocalidadArgentina{" + "iso_31662=" + iso_31662 + ", provincia=" + provincia + ", capital=" + capital + ", localidad=" + localidad + '}';
    }
    
    
}
