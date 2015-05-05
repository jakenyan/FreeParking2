package com.example.stef.freeparking;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;


public class createAccount extends ActionBarActivity {
    EditText inputFirstName;
    EditText inputLastName;
    EditText inputUsername;
    EditText inputEmail;
    EditText inputPassword;
    Button btnRegister;

    String newusers = "";

    public static String sqlMain(String query) {
        String r = "";
        String url = "jdbc:mysql://maxwell.sju.edu:3306/";
        Connection con;
        Statement stmt;
        ResultSet rs;
        String db = "stefandb";

        try {
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
            stmt.executeUpdate(query);



            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //Get Refferences of Views
        inputFirstName = (EditText) findViewById(R.id.fname);
        inputLastName = (EditText) findViewById(R.id.lname);
        inputUsername = (EditText) findViewById(R.id.uname);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.pword);
        btnRegister = (Button) findViewById(R.id.register);

        Button login = (Button) findViewById(R.id.bktologin);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
            }

        });


        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
// TODO Auto-generated method stub
                final String userName = inputUsername.getText().toString();
                final String firstName = inputFirstName.getText().toString();
                final String lastName = inputLastName.getText().toString();
                final String encrypted_password = inputPassword.getText().toString();
                final String email = inputEmail.getText().toString();

// check if any of the fields are vaccant
                if (userName.equals("") || firstName.equals("") || lastName.equals("") || encrypted_password.equals("") || email.equals("")) {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    new Thread(new Runnable() {
                        public void run() {

                            newusers = sqlMain("insert into user(firstname,lastname,username,email,encrypted_password) values('"+firstName+"','"+lastName+"','"+userName+"','"+email+"','"+encrypted_password+"')");
        }
    }).start();
    //Toast.makeText(getApplicationContext(), newusers, Toast.LENGTH_SHORT).show();
    Intent newAcc = new Intent(view.getContext(), MainActivity.class);
    startActivityForResult(newAcc, 0);
    finish();
}
}
        });
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
    //***

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_create_account, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
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