/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DataSource;

/**
 *
 * @author daly
 */
public class AuthentificationDao {
 private PreparedStatement pst;
    private ResultSet rs;

    private Connection connection ;
    
    public AuthentificationDao()
    {
        connection = DataSource.getInstance().getConnection();
    }
 

   
}

  