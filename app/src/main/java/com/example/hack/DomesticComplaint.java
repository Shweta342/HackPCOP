package com.example.hack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DomesticComplaint extends AppCompatActivity {

    private Button FirebaseButton;

    private DatabaseReference mDatabase;
    private EditText Name, Contact, Description, Age;
    private Spinner dropdown;
    static Complaint complaint;
    long Count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domestic_complaint);
        FirebaseButton = findViewById(R.id.submit);

        dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"Theft", "Sexual or Physical Abuse", "Economic Abuse", "Verbal or Emotional Abuse", "Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        Name = findViewById(R.id.PersonName);
        Age = findViewById(R.id.Age);
        Contact = findViewById(R.id.Phone);
        Description = findViewById(R.id.DescribeCrime);
        complaint = new Complaint();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Type2");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Count = dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating child in root object and adding some value to it

                String getName = Name.getText().toString().trim();
                String getAge = Age.getText().toString().trim();
                String getNo = Contact.getText().toString().trim();
                String getType = dropdown.getSelectedItem().toString().trim();
                String getDetail = Description.getText().toString().trim();

                complaint.setName(getName);
                complaint.setAge(getAge);
                complaint.setPhone(getNo);
                complaint.setType(getType);
                complaint.setDescription(getDetail);
                mDatabase.child(String.valueOf(Count+1)).setValue(complaint);

                Toast.makeText(DomesticComplaint.this,"Registration Successful",Toast.LENGTH_LONG).show();
            }
        });


    }


    public class Complaint
    {
        private String Name;
        private String Age;
        private String Phone;
        private String Description;
        private String Type;

        public Complaint()
        {

        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getAge() {

            return Age;
        }

        public void setAge(String age) {
            Age = age;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String phone) {
            Phone = phone;
        }

        public String getDescription() {
            return Description;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public void setDescription(String description) {
            Description = description;
        }

    }
}
