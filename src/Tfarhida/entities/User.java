/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.entities;

import java.util.Objects;

/**
 *
 * @author PC
 */
public class User {
    private int id;

    public User(int id, String email, String username, String password, String confirm_password, String activation_token, String roles, String reset_token) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirm_password = confirm_password;
        this.activation_token = activation_token;
        this.roles = roles;
        this.reset_token = reset_token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
 private String email; 
 private String username;
 private String password;
 private String confirm_password;
 private String activation_token;
 private String roles;
 private String reset_token;

    public User() {
    }

 

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getActivation_token() {
        return activation_token;
    }

    public void setActivation_token(String activation_token) {
        this.activation_token = activation_token;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getReset_token() {
        return reset_token;
    }

    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.email);
        hash = 17 * hash + Objects.hashCode(this.username);
     
        hash = 17 * hash + Objects.hashCode(this.activation_token);
        hash = 17 * hash + Objects.hashCode(this.roles);
        hash = 17 * hash + Objects.hashCode(this.reset_token);
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
        final User other = (User) obj;
        if (this.password != other.password) {
            return false;
        }
        if (this.confirm_password != other.confirm_password) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.activation_token, other.activation_token)) {
            return false;
        }
        if (!Objects.equals(this.roles, other.roles)) {
            return false;
        }
        if (!Objects.equals(this.reset_token, other.reset_token)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "email=" + email + ", username=" + username + ", password=" + password + ", confirm_password=" + confirm_password + ", activation_token=" + activation_token + ", roles=" + roles + ", reset_token=" + reset_token + '}';
    }
 
 
 
 
 
         
    
}
