package com.example.hack;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import static android.media.CamcorderProfile.get;


public class HomePage extends AppCompatActivity {

    static int totalCount = 0;
    ArrayList<Integer> typeCount;
    String[] category = {"Workplace", "Domestic", "Student"};

    com.github.mikephil.charting.charts.PieChart pieChart;
    DatabaseReference ref;

    static HashMap<String, Float> crimeCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        pieChart = findViewById(R.id.pieChart);

        crimeCount = new HashMap<String, Float>();

        countNumber();


    }

    private void countNumber() {

        //getting type 1  type 1 - workplace, type 2- domestic. type 3- student

        typeCount = new ArrayList<>();

        for(int i = 1; i <= 3; i++)
        {
            ref = FirebaseDatabase.getInstance().getReference().child("Type" + Integer.toString(i));

            final int finalI = i;
            final int[] val = new int[1];
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                        val[0] = ((int) snapshot.getChildrenCount());


                    typeCount.add(val[0]);
                    totalCount += val[0];

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        pieChartFunc();

    }


        private void pieChartFunc() {


            ArrayList<PieEntry> types = new ArrayList<PieEntry>();

            for (int i = 0; i < typeCount.size(); i++) {
                Float value = (typeCount.get(i) / (float) totalCount) * 100.0f;

                types.add(new PieEntry(value, category[i]));

            }

            PieDataSet dataSet = new PieDataSet(types, "Types Of Crimes");

            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            dataSet.setValueTextColors(Collections.singletonList(Color.BLACK));
            dataSet.setValueTypeface(Typeface.SANS_SERIF);
            dataSet.setValueTextSize(16f);

            PieData pieData = new PieData(dataSet);

            pieChart.setData(pieData);
            pieData.setDrawValues(true);
            pieChart.getDescription().setEnabled(false);
            pieChart.setRotationEnabled(true);
            pieChart.setCenterText("Complaints");
            pieChart.setAlpha(0.8f);
            pieChart.animate();

        }


    public void workplace (View view)
    {
        startActivity(new Intent(HomePage.this, WorkplaceComplaint.class));
    }

    public void domestic (View view)
    {
        startActivity(new Intent(HomePage.this, DomesticComplaint.class));
    }

    public void student(View view) {
        startActivity(new Intent(HomePage.this, StudentComplaint.class));
    }

    public void seeAllComplaints(View view) {
        startActivity(new Intent(HomePage.this, ComplaintList.class));
    }
}
