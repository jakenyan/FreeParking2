package com.example.stef.freeparking;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.app.Dialog;
import java.sql.*;

public class MainActivity extends ActionBarActivity {
    ;
    String check = "";
    String user = "";

    Button btnSignIn;
    Button btnSignUp;
    EditText inputUserName;
    EditText inputPassword;
    private TextView loginErrorMsg;

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
            rs = stmt.executeQuery(query);
            r = iterateResult(rs);

            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputUserName = (EditText) findViewById(R.id.editTextUserName);
        inputPassword = (EditText) findViewById(R.id.editTextPassword);
        btnSignIn = (Button) findViewById(R.id.buttonSignIn);
        btnSignUp = (Button) findViewById(R.id.buttonSignUp);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newUser = new Intent(view.getContext(), createAccount.class);
                startActivityForResult(newUser, 0);
                finish();
            }
        });


        /**
         * Login button event
         */

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final String userName = inputUserName.getText().toString();
                String password = inputPassword.getText().toString();
                if(userName.equals("")&& password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(userName.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter userName", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{

                    new Thread(new Runnable() {
                        public void run() {
                            check = sqlMain("select encrypted_password from user where username = '"+userName+"'");
                            user = sqlMain("select username from user where username = '"+userName+"'");
                        }
                    }).start();

                    if ((password.equals(check))) {
                        Toast.makeText(getApplicationContext(), "Welcome "+ user, Toast.LENGTH_LONG).show();
                        Intent log = new Intent(view.getContext(), Login.class);
                        startActivity(log);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Entry", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });
    }

    private static String iterateResult(ResultSet rs)
            throws SQLException
    {
        String result = "";
        int numCols = rs.getMetaData().getColumnCount();

        result = "";
        for (int j=1; j<=numCols; j++)
            rs.getMetaData().getColumnName(j);
        while (rs.next())
        {
            for (int i=1; i<=numCols; i++)
                result += (rs.getString(i));

        }
        return result;
    }



        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
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

