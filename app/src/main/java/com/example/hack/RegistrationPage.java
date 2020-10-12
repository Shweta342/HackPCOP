package com.example.hack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegistrationPage extends AppCompatActivity {

    private TextView  signup_text;
    private EditText email, userName, password;
    private Button signUp_Btn;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        signUp_Btn = findViewById(R.id.signup_btn);
        signup_text = findViewById(R.id.login_page);
        email = findViewById(R.id.email);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.pass);

        firebaseAuth = firebaseAuth.getInstance();

        signUp_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate())
                {
                    //valid data entered or all fields filled
                    String user_email = email.getFontFeatureSettings().toLowerCase().trim();
                    String user_password  = password.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                                sendEmailVerification();
                            else
                                Toast.makeText(RegistrationPage.this, "RegistartioRegistration failed!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(RegistrationPage.this, "Empty Fields!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to the login page
                //send verification email

                startActivity(new Intent(RegistrationPage.this, MainActivity.class));

            }
        });

    }


    public boolean validate()
    {
        return (email.getText() != null && password.getText() != null && userName.getText() != null);
    }


    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(RegistrationPage.this, "Registartion Successful, Verification email sent!!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegistrationPage.this, MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(RegistrationPage.this, "Couldn't send verification email!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


}