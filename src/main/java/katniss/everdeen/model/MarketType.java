/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katniss.everdeen.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author gonzalo
 */
public class MarketType implements Serializable{
    
    private String id;
    private String name;
    private MarketType subMarketType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MarketType(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public MarketType getSubMarketType() {
        return subMarketType;
    }

    public void setSubMarketType(MarketType subMarketType) {
        this.subMarketType = subMarketType;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.name);
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
        final MarketType other = (MarketType) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MarketType{" + "id=" + id + ", name=" + name + ", subMarketType=" + subMarketType + '}';
    }

    
}
