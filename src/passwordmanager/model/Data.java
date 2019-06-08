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
public class Data {
    private  String username;
    private  String password;
    private  String website;
    private  String phone;
    private  String email;
    
     public void setData(String email, String password, String website, String username, String phone) {
         this.website = website;
         this.email = email;
         this.username = username;
         this.password = password;
         this.phone = phone;
    }

    public  String getUsername() {
        return username;
    }

    public  String getPassword() {
        return password;
    }

    public  String getWebsite() {
        return website;
    }

    public  String getPhone() {
        return phone;
    }

    public  String getEmail() {
        return email;
    }

   public Data(){
       
   }
    
}
