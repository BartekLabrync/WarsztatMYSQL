package pl.coderslab;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException {

       Connection connection = DbUtil.getConnection();
        System.out.println(connection);

    }

}
