package com.evilstan.sensorstest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    ListView sensorsListView;
    ArrayAdapter<String> sensorsAdapter;
    List<String> sensorsList;
    List<Class<?>> activitiesList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.check_sensors);
        initComponents();
    }

    private void initComponents() {
        activitiesList = new ArrayList<>();
        activitiesList.add(TestVibrator.class);
        activitiesList.add(TestFlash.class);

        sensorsList = Arrays.asList(getResources().getStringArray(R.array.sensors));
        sensorsAdapter = new ArrayAdapter<>(
            MainActivity.this, android.R.layout.simple_list_item_1, sensorsList);

        sensorsListView = findViewById(R.id.list_view);
        sensorsListView.setAdapter(sensorsAdapter);
        sensorsListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startIntent(i);
    }

    private void startIntent(int activityIndex) {
        Intent intent = new Intent(this, activitiesList.get(activityIndex));
        startActivity(intent);
    }
}