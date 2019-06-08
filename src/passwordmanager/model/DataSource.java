/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordmanager.model;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Gaurav_Sharma
 */
public class DataSource {
    private static final String DB_NAME = "gaurav";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERPASSWORD = "password";
    private static final String COLUMN_USERUSERNAME = "username";
        
    private static final String COLUMN_WEBSITE = "website";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PHONE = "phone";
    
    public Connection conn;
    
    public void open(){
         try {
           Class.forName("java.sql.Driver");
           conn = DriverManager.getConnection("jdbc:mysql://localhost/gaurav",USER,PASSWORD);
           System.out.println("Connection Created");
        } catch (Exception ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(""+ex.getMessage());
        }
    }
    
    
    
    
    public void close(){
        try{
            if(conn != null){
                conn.close();
            }
        }catch(SQLException e){
            System.out.println("Couldn't close database : "+e.getMessage());
        }
    }
    
    
    
    public boolean newUser(String username, String password){
        Statement st = null;
        try{
            if(username.length() > 20 || password.length() > 20){
                System.out.println("password or username should be less than 20 characters long");
                    return false;
            }
            st = conn.createStatement();
            st.executeUpdate("INSERT INTO "+TABLE_USERS+" VALUES('"+username+"','"+password+"')");
            st.executeUpdate("CREATE TABLE "+username+"("+COLUMN_WEBSITE+" varchar(50),"+COLUMN_EMAIL+" varchar(50),"+COLUMN_PASSWORD+" varchar(50),"+COLUMN_USERNAME+" varchar(50),"+COLUMN_PHONE+" int(10))");
            st.executeUpdate("ALTER TABLE "+username+" MODIFY  "+COLUMN_WEBSITE+" varchar(50) COLLATE utf8_bin;");
            st.executeUpdate("ALTER TABLE "+username+" MODIFY  "+COLUMN_EMAIL+" varchar(50) COLLATE utf8_bin;");
            st.executeUpdate("ALTER TABLE "+username+" MODIFY  "+COLUMN_PASSWORD+" varchar(50) COLLATE utf8_bin;");
            st.executeUpdate("ALTER TABLE "+username+" MODIFY  "+COLUMN_USERNAME+" varchar(50) COLLATE utf8_bin;");
            return true;
        }catch(SQLException e){
            System.out.println("Cannot create table : "+e.getMessage());
            return false;
        }finally{
           try{
               if(st != null)
                 st.close();  
           }catch(SQLException e){
               System.out.println(e.getMessage());
           }
        }
    }
    
    
    
    
    public boolean addData(String user,String email, String password, String website, String username, String phone ){
        Statement st = null;
        try{
            if(user != null){
                st = conn.createStatement();
                st.executeUpdate("INSERT INTO "+user+" VALUES('"+website+"','"+email+"','"+password+"','"+username+"','"+phone+"');");
                st.close();
                return true;
            }
            return false;
        }catch(Exception e){
            System.out.println("Cannot Insert data :"+e.getMessage());
            return false;
        }
    }
    
    
    
    
  public User queryUser (String username, String password) {
      Statement st = null;
      ResultSet rs = null;
      String query = "select * from "+TABLE_USERS+" where "+COLUMN_USERUSERNAME+" ='"+username+"' ;";
      try{
          st = conn.createStatement();
         rs = st.executeQuery(query);
          User user = new User();
          if(rs.next()){
              if(rs.getString(COLUMN_PASSWORD).equals(password)){
              user.setUsername(rs.getString(COLUMN_USERUSERNAME));
              user.setPassword(rs.getString(COLUMN_USERPASSWORD));
              return user;
            }
              System.out.println("Wrong Credentials");
              return user;
          }
          System.out.println("Wrong UserName or Password");
          return user;
      }catch(SQLException e){
          System.out.println("Query failed :"+e.getMessage());
        }finally{
           try{
               if(st != null)
                 st.close();  
           }catch(SQLException e){
               System.out.println(e.getMessage());
           }
        }
      return null;
  }
  
  
  
  public List<Data> queryData(String username){
      Statement st = null;
      ResultSet rs = null;
      String query = "select * from "+username+";";
      try{
          st = conn.createStatement();
         rs = st.executeQuery(query);
          List<Data> datas = new ArrayList<>();
          while(rs.next())
          {
              Data data = new Data();
              data.setData(rs.getString(COLUMN_EMAIL), rs.getString(COLUMN_PASSWORD), 
                      rs.getString(COLUMN_WEBSITE),rs.getString(COLUMN_USERNAME) , 
                      rs.getString(COLUMN_PHONE));
              datas.add(data);
          }
          rs.close();
          st.close();
          return datas;
      }catch(SQLException e){
          System.out.println("Query failed :"+e.getMessage());
        }
      return null;
  }
  
   public String queryData(String username, String website){
       Statement st = null;
      ResultSet rs = null;
      String password = null;
      String query = "select "+COLUMN_PASSWORD+" from "+username+" where website ='"+website+"' ;";
      try{
          st = conn.createStatement();
         rs = st.executeQuery(query);
          if(rs.next())
          {
               password = rs.getString("password");
          }
          rs.close();
          st.close();
          return password;
      }catch(SQLException e){
          System.out.println("Query failed :"+e.getMessage());
        }
       return password;
   }
   
   
   
   public boolean updateData(String user, String website, String password, String username, String phone){
       Statement st = null;
      String query = "update "+user+" set "+COLUMN_PASSWORD+" = '"+password+"' , "+COLUMN_USERNAME+" = '"+username+"' , "+COLUMN_PHONE+" = '"+phone+"' where "+COLUMN_WEBSITE+" = '"+website+"';";
      try{
          st = conn.createStatement();
          st.executeUpdate(query);
          st.close();
          return true;
      }catch(SQLException e){
          System.out.println("Query failed :"+e.getMessage());
        }
      return false;
   }
   
   
   public boolean deleteData(String user, String website, String email){
       Statement st = null;
       String query = "delete from "+user+" where "+COLUMN_WEBSITE+" = '"+website+"' && "+COLUMN_EMAIL+" = '"+email+"';";
      try{
          st = conn.createStatement();
          st.executeUpdate(query);
          st.close();
          return true;
      }catch(SQLException e){
          System.out.println("Query failed :"+e.getMessage());
        }
      return false;
   }
   
}
