/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katniss.everdeen.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import katniss.everdeen.model.MarketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gonzalo
 */
@Service
public class MarketTypeService {
    
    @Autowired List<MarketType> markets;
    
    public List<MarketType> listByName(String name) {
        return this.markets.stream().filter(m -> m.getName().contains(name)).collect(Collectors.toList());
    }
    
    public MarketType uniqueByName(String name) {
        Optional<MarketType> result = this.markets.stream().filter(m -> m.getName().contains(name)).findFirst();
        return result.isPresent()? result.get() : null;
    }
    
    public MarketType uniqueById(String id) {
        Optional<MarketType> result = this.markets.stream().filter(m -> m.getId().contains(id)).findFirst();
        return result.isPresent()? result.get() : null;
    }
}
