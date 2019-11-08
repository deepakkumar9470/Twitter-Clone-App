
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity {

  EditText editText1,editText2;
  Button button;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    redirectUser();
    setTitle("Twitter:Login");

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

      public void signUpLogin(View view) {
         editText1=findViewById(R.id.username);
         editText2=findViewById(R.id.password);
          ParseUser.logInInBackground(editText1.getText().toString(), editText2.getText().toString(), new LogInCallback() {
              @Override
              public void done(ParseUser user, ParseException e) {
                  if (e==null)
                  {
                      Log.i("Info","LoggedIn");
                      redirectUser();
                  }else {
                      ParseUser parseUser=new ParseUser();
                      parseUser.setUsername(editText1.getText().toString());
                      parseUser.setPassword(editText2.getText().toString());
                      parseUser.signUpInBackground(new SignUpCallback() {
                          @Override
                          public void done(ParseException e) {
                              if (e==null)
                              {
                                  Log.i("Info","SingedUp");
                                  redirectUser();
                                  //Toast.makeText(MainActivity.this, "SignedUp", Toast.LENGTH_SHORT).show();
                              }else {
                                  Toast.makeText(MainActivity.this,e.getMessage().substring(e.getMessage().indexOf(" ")) , Toast.LENGTH_SHORT).show();
                              }
                          }
                      });
                  }
              }
          });

       }
       public void redirectUser(){
         if (ParseUser.getCurrentUser()!=null)
         {
             Intent intent=new Intent(MainActivity.this,Main2Activity.class);
             startActivity(intent);
         }
       }
}