/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordmanager.model;

/**
 *
 * @author Gaurav_Sharma
 */
public class User {
   private String username = "";
   private String password = "";

    public String getUsername() {
        return username;
    }

//    public String getPassword() {
//        return password;
//    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   
    public User(){
        this.username = "No User";
        this.password = "No Password";
    }
    
    
}
