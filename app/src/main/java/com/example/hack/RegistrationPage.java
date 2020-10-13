package com.example.hack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationPage extends AppCompatActivity {

    private EditText Name,Email,Password;
    private Button SignUp,GoBack;
    private FirebaseAuth firebaseAuth;
    private String username,mailID,password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        Name = findViewById(R.id.username);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void returnToLoginPage(View view) {
        startActivity(new Intent(RegistrationPage.this, LoginPage.class));
    }

    private Boolean properIPcheck(){
        Boolean result = false;

        username = Name.getText().toString();
        password = Password.getText().toString();
        mailID = Email.getText().toString();


        if(username.isEmpty() || password.isEmpty() || mailID.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }

    public void signUp(View view) {
        if(properIPcheck()){
            String userEmail = Email.getText().toString().trim();
            String userPassword = Password.getText().toString().trim();

            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(RegistrationPage.this, "Sign up complete", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrationPage.this,LoginPage.class));
                    }
                    else {
                        Toast.makeText(RegistrationPage.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
}
