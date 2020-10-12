package com.example.hack;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.Log;

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


public class HomePage extends AppCompatActivity {


    static int count = 0;

    com.github.mikephil.charting.charts.PieChart pieChart;
    DatabaseReference ref;

    static HashMap<String, Float> crimeCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        pieChart =  findViewById(R.id.pieChart);

        crimeCount = new HashMap<String, Float>();

        countNumber();

        //to show the Pie Chart
        pieChartFunc();

    }

    private void countNumber() {

        ref = FirebaseDatabase.getInstance().getReference().child("Complaint");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    count = (int) snapshot.getChildrenCount();
                Log.i("pie", String.valueOf(count));

                retrieveData();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void retrieveData() {

        Log.i("count:", String.valueOf(count));


        for(int i = 1; i <= count; i++)
        {
            ref = FirebaseDatabase.getInstance().getReference().child("Complaint").child("Complaint"+Integer.toString(i));

            final int finalI = i;
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String str = snapshot.child("sub_category").getValue().toString();
                    Float val;
                    Log.i("Type of crime: ", str);
                    if(crimeCount.containsKey(str))
                    {
                        val = crimeCount.get(str);
                        crimeCount.put(str, (val+1));
                    }
                    else {
                        crimeCount.put(str, 1f);
                    }

                    Log.i("crimeCount"+String.valueOf(finalI), String.valueOf(crimeCount.size()));

                    pieChartFunc();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    private void pieChartFunc() {


        ArrayList<PieEntry> types = new ArrayList<PieEntry>();

        Log.i("crime size", String.valueOf(crimeCount.size()));


        Iterator crimeItr = crimeCount.entrySet().iterator();

        while (crimeItr.hasNext()) {
            HashMap.Entry mapElement = (HashMap.Entry)crimeItr.next();
            Float value = ((Float)mapElement.getValue()), mul = 100.0f;
            value = (value / (float)(count)) *mul;
            Log.i("pieEntry",mapElement.getKey() + " : " + value);

            types.add(new PieEntry(value, mapElement.getKey()));

        }



        /*Set keys = crimeCount.keySet();


        for(Iterator i = keys.iterator(); i.hasNext();) {
            String key = (String) i.next();
            Integer value = (Integer) crimeCount.get(key);

            types.add(new PieEntry(value, key));

            Log.i("pieEntry", value+" "+key);

        }*/

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
}
