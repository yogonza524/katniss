/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katniss.everdeen.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gonzalo
 */
public class Usuario implements Serializable {
    
    private String id;
    private String username;
    private String password;
    private String email;
    private String address;
    private String tel;
    private String cel;
    private String city;
    private String country;
    private String lastConnection;
    private String createdAt;
    private String ip;
    private boolean active;
    private String aditionalInfo;
    private byte[] image;
    private String facebook;
    private String tweeter;
    private String role;
    private Date birthday;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTweeter() {
        return tweeter;
    }

    public void setTweeter(String tweeter) {
        this.tweeter = tweeter;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(String lastConnection) {
        this.lastConnection = lastConnection;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAditionalInfo() {
        return aditionalInfo;
    }

    public void setAditionalInfo(String aditionalInfo) {
        this.aditionalInfo = aditionalInfo;
    }

    public Usuario() {
        this.id = UUID.randomUUID().toString();
    }
    
    public Usuario(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Usuario(String username, String password, String email, String address, String tel, String cel, String city, String country, String lastConnection, String createdAt, String ip, boolean active, String aditionalInfo) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.tel = tel;
        this.cel = cel;
        this.city = city;
        this.country = country;
        this.lastConnection = lastConnection;
        this.createdAt = createdAt;
        this.ip = ip;
        this.active = active;
        this.aditionalInfo = aditionalInfo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.username);
        hash = 83 * hash + Objects.hashCode(this.password);
        hash = 83 * hash + Objects.hashCode(this.email);
        hash = 83 * hash + Objects.hashCode(this.address);
        hash = 83 * hash + Objects.hashCode(this.tel);
        hash = 83 * hash + Objects.hashCode(this.cel);
        hash = 83 * hash + Objects.hashCode(this.city);
        hash = 83 * hash + Objects.hashCode(this.country);
        hash = 83 * hash + Objects.hashCode(this.lastConnection);
        hash = 83 * hash + Objects.hashCode(this.createdAt);
        hash = 83 * hash + Objects.hashCode(this.ip);
        hash = 83 * hash + (this.active ? 1 : 0);
        hash = 83 * hash + Objects.hashCode(this.aditionalInfo);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", address=" + address + ", tel=" + tel + ", cel=" + cel + ", city=" + city + ", country=" + country + ", lastConnection=" + lastConnection + ", createdAt=" + createdAt + ", ip=" + ip + ", active=" + active + ", aditionalInfo=" + aditionalInfo + '}';
    }
    
    public static class Builder {
        private Usuario instancia;
        
        private String username = "";
        private String password = "";
        private String email = "";
        private String address = "";
        private String tel = "";
        private String cel = "";
        private String city = "";
        private String country = "";
        private String lastConnection = "";
        private String createdAt = "";
        private String ip = "";
        private boolean active;
        private String aditionalInfo = "";
        
        public Builder username(String username) {
            this.username = username;
            return this;
        }
        
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        
        public Builder address(String address) {
            this.address = address;
            return this;
        }
        
        public Builder tel(String tel) {
            this.tel = tel;
            return this;
        }
        
        public Builder city(String city) {
            this.city = city;
            return this;
        }
        
        public Builder country(String country) {
            this.country = country;
            return this;
        }
        
        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }
        
        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        
        public Builder lastConnection(String lastConnection) {
            this.lastConnection = lastConnection;
            return this;
        }
        
        public Builder cel(String cel) {
            this.cel = cel;
            return this;
        }
        
        public Builder aditionalInfo(String aditionalInfo) {
            this.aditionalInfo = aditionalInfo;
            return this;
        }
        
        public Usuario create() {
            this.instancia = new Usuario(username, password, email, address, tel, cel, city, country, lastConnection, createdAt, ip, active, aditionalInfo);
            return instancia;
        }
    }
}
