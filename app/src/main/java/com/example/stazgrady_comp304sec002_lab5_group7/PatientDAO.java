package com.example.stazgrady_comp304sec002_lab5_group7;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PatientDAO {

    private DatabaseReference dbReference;

    public PatientDAO() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbReference = db.getReference(Patient.class.getSimpleName());
    }

    //insert a patient into the database with their name as the key
    public Task<Void> insert(Patient patient) {
        return dbReference.child(patient.getName()).setValue(patient);
    }

    //update the patient based on the name
    public Task<Void> update(String key, HashMap<String, Object> hashMap) {
        return dbReference.child(key).updateChildren(hashMap);
    }

    //delete the patient based on the name
    public Task<Void> delete(String key) {
        return dbReference.child(key).removeValue();
    }
}
