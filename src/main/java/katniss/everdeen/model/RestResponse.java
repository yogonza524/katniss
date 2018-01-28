/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katniss.everdeen.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author gonzalo
 */
public class RestResponse implements Serializable{
    private int status;
    private String message;
    private Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.status;
        hash = 67 * hash + Objects.hashCode(this.message);
        hash = 67 * hash + Objects.hashCode(this.data);
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
        final RestResponse other = (RestResponse) obj;
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RestResponse{" + "status=" + status + ", message=" + message + ", data=" + data + '}';
    }
    
    public static class Builder {
        private RestResponse instance;
        
        private int code;
        private String message;
        private Object data;
        
        public Builder code(int code) {
            this.code = code;
            return this;
        }
        
        public Builder message(String message) {
            this.message = message;
            return this;
        }
        
        public Builder data(Object data) {
            this.data = data;
            return this;
        }
        
        public RestResponse create() {
            this.instance = new RestResponse();
            instance.setData(this.data);
            instance.setMessage(this.message);
            instance.setStatus(this.code);
            
            return instance;
        }
    }
}
