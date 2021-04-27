/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.base;

/**
 *
 * @author hp
 */
public class UserSession {
    
    private  static UserSession instance=null;
    private static int id;
    private static String email;
    private static String username;
    private static String password;
    private static String confirm_password;
    private static String roles;
    private static int Auth;

    public UserSession(int id, String email, String username, String password, String confirm_password,String roles,  int Auth  ) {
        
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirm_password = confirm_password;
        this.roles = roles;
        this.Auth = Auth;

    }
    
   public static UserSession getInstance (int id,String email, String username, String password, String confirm_password,String roles, int Auth  ){
   
       if(instance == null) {
            instance = new UserSession(id,email, username, password,confirm_password,roles,Auth);
        }
        return instance;
   }
    
      public UserSession getInstance() {
        return instance;
    }

    public static String getEmail() {
        return email;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getConfirm_password() {
        return confirm_password;
    }

    public static String getRoles() {
        return roles;
    }

    public static int getAuth() {
        return Auth;
    }

    public static int getId() {
        return id;
    }
      
      
      
       public void cleanUserSession() {
       instance = null;
        
    }
 
}
