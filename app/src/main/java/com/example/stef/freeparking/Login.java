package com.example.stef.freeparking;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;


public class Login extends ActionBarActivity {
    String theresult = "";
    String data = "";
    String first = "";
    String last = "";
    String user = "";
    String email = "";


    public static String sqlMain ( String query )
    {
        String r="";
        String url = "jdbc:mysql://maxwell.sju.edu:3306/";
        Connection   con;
        Statement    stmt;
        ResultSet    rs;
        String db = "stefandb";

        try
        {
            // append the dbname to the URL
            url = url + db;

            // Load the jdbc-odbc bridge driver

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            // Attempt to connect to a driver.
            con = DriverManager.getConnection(url, "stefan", "sp2015android");

            //  Create a Statement object so we can submit
            //  SQL statements to the driver
            stmt = con.createStatement();

            // Submit a query, creating a ResultSet object
            rs = stmt.executeQuery(query);
            r = iterateResult(rs);

            stmt.close();
            con.close();
        }
        catch (Exception e)
        {e.printStackTrace();}
        return r;
    }
    public void dbCall(View view){
        new Thread(new Runnable(){
            public void run(){
                theresult = sqlMain("select latitude from parkinglot where name = 'mandeville';");
            }

        }).start();
        Toast.makeText(getApplicationContext(), theresult, Toast.LENGTH_SHORT).show();

    }

    public void info(View view) {
        new Thread(new Runnable(){
            public void run(){
                data = sqlMain("select * from user where username = 'jake';");
                first = sqlMain("select firstname from user where username = 'jake';");
                last = sqlMain("select lastname from user where username = 'jake';");
                user = sqlMain("select username from user where username = 'jake';");
                email = sqlMain("select email from user where username = 'jake';");

            }

        }).start();
        Toast.makeText(getApplicationContext(), first, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), last, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), user, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
    }

    public void toMaps(View view) {
        Intent intent = new Intent(Login.this, mapsPage.class);
        startActivity(intent);
    }



    //***
    private static String iterateResult(ResultSet rs)
            throws SQLException
    {
        String result = "";
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
        return result;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
