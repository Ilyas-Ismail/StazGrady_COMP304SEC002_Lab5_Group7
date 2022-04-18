package com.example.stazgrady_comp304sec002_lab5_group7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText emailTxt;
    EditText passwordTxt;
    Button registerBtn;
    TextView mainPageTV;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailTxt = findViewById(R.id.regEmailTxt);
        passwordTxt = findViewById(R.id.regPasswordTxt);
        registerBtn = findViewById(R.id.registerBtn);
        mainPageTV = findViewById(R.id.returnTxt);

        mAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(view -> {
            createUser();
        });

        mainPageTV.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        });
    }

    private void createUser() {
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();

        if(TextUtils.isEmpty(email)){
            emailTxt.setError("Email cannot be empty");
            emailTxt.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            passwordTxt.setError("Password cannot be empty");
            passwordTxt.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}