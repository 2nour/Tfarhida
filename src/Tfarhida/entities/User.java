/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.entities;

/**
 *
 * @author hp
 */
public class User {

    int id;
    String email;
    String username;
    String password;
    String confirm_password;
    String roles;
    int code;
    int Auth;

    public User(int id, String email, String username, String password, String confirm_password, String roles) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirm_password = confirm_password;
        this.roles = roles;
        this.code = code;

    }

    public User() {
    }

    public User(String email, String username, String password, String confirm_password, String roles, int Auth) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirm_password = confirm_password;
        this.roles = roles;
        this.Auth = Auth;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuth() {
        return Auth;
    }

    public void setAuth(int Auth) {
        this.Auth = Auth;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

//   
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", username=" + username + ", password=" + password + ", confirm_password=" + confirm_password + ", roles=" + roles + ", code=" + code + ", Auth=" + Auth + '}';
    }

}