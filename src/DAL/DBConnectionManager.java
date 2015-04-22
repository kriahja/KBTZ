
package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;


public class DBConnectionManager {
    private final SQLServerDataSource ds;
    private static DBConnectionManager instance = null;
    
    public static DBConnectionManager getInstance() throws IOException
    {
        if(instance == null)
        {
            instance = new DBConnectionManager("Biv.cfg");
        }
        return instance;
    }

   private DBConnectionManager(String fileName) throws IOException
    {       
       
        ds = new SQLServerDataSource();
        
        Properties props = new Properties();
        props.load(new FileReader(fileName));
        ds.setServerName(props.getProperty("SERVER"));
        ds.setDatabaseName(props.getProperty("DATABASE"));
        ds.setPortNumber(Integer.parseInt(props.getProperty("PORT")));
        ds.setUser(props.getProperty("USER"));
        ds.setPassword(props.getProperty("PASSWORD"));
    }
   
    public Connection getConnection() throws SQLServerException
    {
        return ds.getConnection();
    }  
    
    
}
