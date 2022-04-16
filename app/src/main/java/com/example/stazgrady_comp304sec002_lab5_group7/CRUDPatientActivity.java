package com.example.stazgrady_comp304sec002_lab5_group7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class CRUDPatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudpatient);

        EditText nameEdit = findViewById(R.id.nameEdit);
        EditText ageEdit = findViewById(R.id.ageEdit);
        EditText diseaseEdit = findViewById(R.id.diseaseEdit);
        EditText billEdit = findViewById(R.id.billEdit);

        Button addBtn = findViewById(R.id.addBtn);
        Button updateBtn = findViewById(R.id.updateBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);

        PatientDAO dao = new PatientDAO();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Patient patient = new Patient(nameEdit.getText().toString(),
                        Integer.parseInt(ageEdit.getText().toString()),
                        diseaseEdit.getText().toString(),
                        Double.parseDouble(billEdit.getText().toString()));

                dao.insert(patient);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", nameEdit.getText().toString());
                hashMap.put("age", Integer.parseInt(ageEdit.getText().toString()));
                hashMap.put("disease", diseaseEdit.getText().toString());
                hashMap.put("bill", Double.parseDouble(billEdit.getText().toString()));

                dao.update(nameEdit.getText().toString(), hashMap);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao.delete(nameEdit.getText().toString());
            }
        });
    }
}