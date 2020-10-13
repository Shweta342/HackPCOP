package com.example.hack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WorkplaceComplaint extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText nameText, ageText, address, pincode, email, subCategory, company, problem, otherType;
    private Spinner spinner;
    private String name, age, emailId, pin, add, sub_category, prob, comp, other;

    NewCrimeComplaint complaint;
    public static DatabaseReference ref;
    static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workplace_complaint);

        inflate_layout();

        getValues();

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.subCat, android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        complaint = new NewCrimeComplaint();

        ref = FirebaseDatabase.getInstance().getReference().child("Complaint");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    count = (int) snapshot.getChildrenCount();
                Log.i("main ", String.valueOf(count));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void inflate_layout()
    {
        nameText = findViewById(R.id.name_applicant);
        ageText = findViewById(R.id.age_applicant);
        address = findViewById(R.id.address);
        otherType = findViewById(R.id.other_problem);
        pincode = findViewById(R.id.pincode);
        email= findViewById(R.id.username);
        problem = findViewById(R.id.problem);
        spinner = findViewById(R.id.sub_category_spinner);
        company = findViewById(R.id.company);
    }

    private void getValues()
    {
        name = nameText.getText().toString().trim();
        age = ageText.getText().toString().trim();
        add = address.getText().toString().trim();
        pin = pincode.getText().toString().trim();
        emailId = email.getText().toString().trim();
        comp = company.getText().toString().trim();
        prob = problem.getText().toString().trim();

        //Logging the values

        Log.i("Name:",name);
        Log.i("Age:",age);
        Log.i("Address:",add);
        Log.i("Email id:",emailId);
        Log.i("Pincode:",pin);
        Log.i("Company:",comp);
        Log.i("Problem:",prob);
    }


    public void register(View view)
    {
        getValues();

        complaint.setAge(age);
        complaint.setName(name);
        complaint.setComp(comp);
        complaint.setAdd(add);
        complaint.setEmailId(emailId);
        complaint.setSub_category(sub_category);
        complaint.setPin(pin);
        complaint.setProb(prob);
        complaint.setOther(other);

        ref.child("Complaint"+(count+1)).setValue(complaint);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        sub_category = parent.getItemAtPosition(position).toString();
        Log.i("Sub Category: ", sub_category);

        if(sub_category.equals("Others"))
            sub_category = otherType.getText().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Toast.makeText(WorkplaceComplaint.this,"Select something", Toast.LENGTH_SHORT).show();

    }

}