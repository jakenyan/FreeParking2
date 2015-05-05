package com.example.stef.freeparking;

//*** SQL.java
import java.sql.*;
import java.lang.*;

public class SQL
{
   static String result;

   //***
   public static void sqlMain (String uname, String passwd,
                               String db, String query)
   {
        String url = "jdbc:mysql://maxwell.sju.edu:3306/";
        Connection   con;
        Statement    stmt;
        ResultSet    rs;

        try
           {
           // append the dbname to the URL
           url = url + db;

           // Load the jdbc-odbc bridge driver

		 Class.forName("com.mysql.jdbc.Driver").newInstance();

           // Attempt to connect to a driver.
           con = DriverManager.getConnection(url, uname,passwd);

           //  Create a Statement object so we can submit
           //  SQL statements to the driver
           stmt = con.createStatement();

           // Submit a query, creating a ResultSet object
           rs = stmt.executeQuery(query);
           iterateResult(rs);

           stmt.close();
           con.close();
           }
        catch (Exception e)
           {e.printStackTrace();}
   }

   public void info(String userName, String password)
   {



   }
   //***
   private static void iterateResult(ResultSet rs)
                        throws SQLException
   {
        int numCols = rs.getMetaData().getColumnCount();

        result = "";
        for (int j=1; j<=numCols; j++)
            result +=
                   (rs.getMetaData().getColumnName(j)+"\t");
        result += "\n";

        while (rs.next())
        {
           for (int i=1; i<=numCols; i++)
               result += (rs.getString(i) + "\t");
           result += "\n";
        }
   }
}
