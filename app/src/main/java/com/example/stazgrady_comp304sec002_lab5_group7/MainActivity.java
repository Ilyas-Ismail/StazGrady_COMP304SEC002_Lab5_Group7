package com.example.stazgrady_comp304sec002_lab5_group7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class MainActivity extends AppCompatActivity {

    EditText emailTxt;
    EditText passwordTxt;
    Button loginBtn;
    TextView registerTV;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        loginBtn = findViewById(R.id.loginBtn);
        registerTV = findViewById(R.id.signUpTxt);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(view -> {
            loginUser();
        });

        registerTV.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(MainActivity.this, CRUDPatientActivity.class));
        }
    }

    private void loginUser() {
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();

        if(TextUtils.isEmpty(email)){
            emailTxt.setError("Email cannot be empty");
            emailTxt.requestFocus();
        }else if(TextUtils.isEmpty(password)) {
            passwordTxt.setError("Password cannot be empty");
            passwordTxt.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(MainActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(MainActivity.this, CRUDPatientActivity.class));
                   } else {
                       Toast.makeText(MainActivity.this, "Login Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                   }
                }
            });
        }
    }
}