/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katniss.everdeen.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author gonzalo
 */
public class Publication implements Serializable{
    
    private String id;
    private Date created;
    private Date lastModification;
    private int caracteresMaximos;
    private boolean editable;
    private boolean acceptImage;
    private byte[] image;
    private Usuario owner;
    private String country;
    private String city;
    private String lat;
    private String lng;
    private String content;
    private List<Usuario> viewed;
    private MarketType market;
    private PublicationState state;
    
}
