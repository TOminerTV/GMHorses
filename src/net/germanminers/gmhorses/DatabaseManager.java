package net.germanminers.gmhorses;

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager
{
	private FileConfiguration config = GMHorses.getMyConfig();

	private Connection connection;
    private String host, database, username, password;
    private int port;
    
    public DatabaseManager()
    {
    	host = config.getString("host");
    	port = config.getInt("port");
    	database = config.getString("database");
    	username = config.getString("username");
    	password = config.getString("password");
	}

    public Connection openConnection() throws SQLException, ClassNotFoundException
    {
        if(connection != null && !connection.isClosed())
        {
            return connection;
        }
     
        synchronized(this)
        {
            if(connection != null && !connection.isClosed())
            {
                return connection;
            }
            
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database, this.username, this.password);
        }
        
        return connection;
    }
}