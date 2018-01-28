/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katniss.everdeen.services.rethinkdb;

import java.util.Map;
import katniss.everdeen.model.MarketType;
import katniss.everdeen.util.Conversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gonzalo
 */
@Service
public class MarketTypeServiceRepository {
    @Autowired RethinkdbService repository;
    
    private final String db = "katniss";
    private final String table = "market_type";
    
    public Map insert(MarketType market) {
        if (!repository.existsTable(db, table)) {
            return null;
        }
        return repository.insert(db, table, market);
    }
    
    public boolean update(MarketType market) {
        if (!repository.existsTable(db, table)) {
            return false;
        }
        return repository.updateAll(db, table, Conversion.toMap(MarketType.class, market));
    }
    
    public boolean remove(MarketType market) {
        if (!repository.existsTable(db, table)) {
            return false;
        }
        if (market == null) {
            return false;
        }
        if (market.getId() == null) {
            return false;
        }
        return repository.delete(db, table, market.getId());
    }
}
