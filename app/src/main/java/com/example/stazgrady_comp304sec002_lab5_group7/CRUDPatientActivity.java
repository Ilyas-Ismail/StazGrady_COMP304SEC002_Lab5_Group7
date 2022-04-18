package com.example.stazgrady_comp304sec002_lab5_group7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;

public class CRUDPatientActivity extends AppCompatActivity {

    private DatabaseReference dbReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudpatient);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbReference = db.getReference(Patient.class.getSimpleName());
        mAuth = FirebaseAuth.getInstance();

        EditText nameEdit = findViewById(R.id.nameEdit);
        EditText ageEdit = findViewById(R.id.ageEdit);
        EditText diseaseEdit = findViewById(R.id.diseaseEdit);
        EditText billEdit = findViewById(R.id.billEdit);

        Button addBtn = findViewById(R.id.addBtn);
        Button updateBtn = findViewById(R.id.updateBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);
        Button readBtn = findViewById(R.id.readBtn);
        Button infoBtn = findViewById(R.id.infoBtn);
        Button logoutBtn = findViewById(R.id.logoutBtn);

        PatientDAO dao = new PatientDAO();

        //if all fields are filled, add the patient into the database once button is pressed
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(nameEdit.getText().toString()) &&
                        !TextUtils.isEmpty(ageEdit.getText().toString()) &&
                        !TextUtils.isEmpty(diseaseEdit.getText().toString()) &&
                        !TextUtils.isEmpty(billEdit.getText().toString())) {

                    Patient patient = new Patient(nameEdit.getText().toString(),
                            Integer.parseInt(ageEdit.getText().toString()),
                            diseaseEdit.getText().toString(),
                            Double.parseDouble(billEdit.getText().toString()));

                    dao.insert(patient).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(CRUDPatientActivity.this, getResources().getString(R.string.successAdd), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CRUDPatientActivity.this, getResources().getString(R.string.failure), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(CRUDPatientActivity.this, getResources().getString(R.string.emptyFields), Toast.LENGTH_SHORT).show();
                }

            }
        });

        //if all fields are filled, update the patient from the database
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(nameEdit.getText().toString()) &&
                        !TextUtils.isEmpty(ageEdit.getText().toString()) &&
                        !TextUtils.isEmpty(diseaseEdit.getText().toString()) &&
                        !TextUtils.isEmpty(billEdit.getText().toString())) {

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("name", nameEdit.getText().toString());
                    hashMap.put("age", Integer.parseInt(ageEdit.getText().toString()));
                    hashMap.put("disease", diseaseEdit.getText().toString());
                    hashMap.put("bill", Double.parseDouble(billEdit.getText().toString()));

                    dao.update(nameEdit.getText().toString(), hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(CRUDPatientActivity.this, getResources().getString(R.string.successUpdate), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CRUDPatientActivity.this, getResources().getString(R.string.failure), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(CRUDPatientActivity.this, getResources().getString(R.string.emptyFields), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //deletes the patient from the database based on name
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao.delete(nameEdit.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CRUDPatientActivity.this, getResources().getString(R.string.successDelete), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CRUDPatientActivity.this, getResources().getString(R.string.failure), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //checks if the database has an entry with the inputted name, if so, display data in appropriate fields
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbReference.child(nameEdit.getText().toString().toUpperCase(Locale.ROOT)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            Patient patient = snapshot.getValue(Patient.class);
                            ageEdit.setText(String.valueOf(patient.getAge()));
                            diseaseEdit.setText(patient.getDisease());
                            billEdit.setText(String.valueOf(patient.getBill()));
                        } else {
                            Toast.makeText(CRUDPatientActivity.this, getResources().getString(R.string.none), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CRUDPatientActivity.this, PatientListActivity.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(CRUDPatientActivity.this, MainActivity.class));
        });
    }
}