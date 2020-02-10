/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import objetosNegocio.Film;

/**
 * Clase que conecta con la base de datos.
 * @author alfonso felix
 */
public class DB {
    /**
     * Método para obtener los datos de las películas.
     * @return Lista con las películas de tipo Film.
     */
    public static ArrayList obtenerDatosPeliculas(){
        String user = "root";
        String password = "1234";
        final String connectionString = 
                "jdbc:mysql://localhost:3306/sakila?serverTimezone=UTC";
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Film> lstFilms = null;
        
        try {
            connection = DriverManager
                    .getConnection(connectionString,
                            user,
                            password);
            statement = connection.createStatement();
            
            resultSet = statement.executeQuery("SELECT film_id, title, description, release_year FROM film");
            
            lstFilms = new ArrayList<>();
            
            while (resultSet.next()) {
                lstFilms.add(new Film(
                        resultSet.getInt("film_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getShort("release_year")
                ));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            // Clean up
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {}
            }
            return lstFilms;
        }
    }
}
