package com.kens.calcounter;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.CustomListviewAdapter;
import data.DatabaseHandler;
import model.Food;
import util.Utils;

public class DisplayFoodsActivity extends AppCompatActivity {

    private DatabaseHandler dba;
    private ArrayList<Food> dbFoods = new ArrayList<>();
    private CustomListviewAdapter foodAdapter;
    private ListView listView;

    private Food myFood;
    private TextView totalCals, totalFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_foods);

        listView = (ListView) findViewById(R.id.list);
        totalCals = (TextView) findViewById(R.id.totalAmountTextView);
        totalFoods = (TextView) findViewById(R.id.totalItemTextView);

        refreshData();

    }

    private void refreshData(){

        dbFoods.clear();
        dba = new DatabaseHandler(getApplicationContext());

        ArrayList<Food> foodsFromDB = dba.getFoods();

        int calsValue = dba.totalCalories();
        int totalItems = dba.getTotalItems();

        String formattedValue = Utils.formatNumber(calsValue);
        String formattedItems = Utils.formatNumber(totalItems);

        totalCals.setText("Total Calories: "+ formattedValue);
        totalFoods.setText("Total Foods: "+ formattedItems);

        for(int i = 0; i < foodsFromDB.size(); i++){

            String name = foodsFromDB.get(i).getFoodName();
            String dateText = foodsFromDB.get(i).getRecordDate();
            int cals = foodsFromDB.get(i).getCalories();
            int foodId = foodsFromDB.get(i).getFoodId();

            Log.v("Food IDS: ", String.valueOf(foodId));

            myFood = new Food();
            myFood.setFoodName(name);
            myFood.setRecordDate(dateText);
            myFood.setCalories(cals);
            myFood.setFoodId(foodId);

            dbFoods.add(myFood);

        }

        dba.close();

        //setup adapter
        foodAdapter = new CustomListviewAdapter(DisplayFoodsActivity.this, R.layout.list_row, dbFoods);
        listView.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();

    }

}
