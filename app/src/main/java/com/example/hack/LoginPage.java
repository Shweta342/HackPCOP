package com.example.hack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginPage extends AppCompatActivity {

    int count=5;
    TextView attempts;
    Button login;
    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        login = findViewById(R.id.signup_btn);

        attempts = findViewById(R.id.attempts_remainig);
        EditText password = findViewById(R.id.password);
        EditText user_email = findViewById(R.id.userName);
        TextView direct_to_registration = findViewById(R.id.signUp_page);


        firebaseAuth = firebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null)        //it means the user is already logged in
        {
            finish();
            startActivity(new Intent(LoginPage.this, HomePage.class));
        }

        if(count==0)
        {
            login.setEnabled(false);
        }


        final String username, pass;
        username = user_email.getText().toString();
        pass = password.getText().toString();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(username, pass);
                if(count<=0)
                {
                    login.setEnabled(false);
                }
            }
        });

        direct_to_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, RegistrationPage.class));
            }
        });

    }



    public void validate( String username, String pass)
    {
        progressDialog.setMessage("You can't subscribe to my channel until verifed!");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(username, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    // Toast.makeText(getApplicationContext(), "Login Successful!!", Toast.LENGTH_SHORT).show();
                    checkEmailVerified();
                }
                else {
                    count--;
                    attempts.setText("No of attempts remaining: "+count);
                    Toast.makeText(getApplicationContext(), "Login failed!!", Toast.LENGTH_SHORT).show();
                    if(count == 0)
                        login.setEnabled(false);
                }
            }
        });
    }

    private void checkEmailVerified()
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        boolean emailFlag = firebaseUser.isEmailVerified();

        if(emailFlag)
        {
            finish();
            startActivity( new Intent(LoginPage.this, HomePage.class));
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Verify your email id!", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}

