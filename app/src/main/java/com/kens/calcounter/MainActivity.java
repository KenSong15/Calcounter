package com.kens.calcounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Food;

public class MainActivity extends AppCompatActivity {

    private EditText foodName,foodCals;
    private Button submitButtion;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = new DatabaseHandler(MainActivity.this);

        foodName = (EditText) findViewById(R.id.foodEditText);
        foodCals = (EditText) findViewById(R.id.caloriesEditText);
        submitButtion = (Button) findViewById(R.id.submitButton);

        submitButtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveDatetoDB();

            }
        });

    }

    private void saveDatetoDB() {

        Food food = new Food();
        String name = foodName.getText().toString().trim();
        String calsString = foodCals.getText().toString().trim();

        int cals = Integer.parseInt(calsString);

        if(name.equals("") || calsString.equals("")) {
            Toast.makeText(getApplicationContext(), "No empty fields allowed", Toast.LENGTH_SHORT).show();
        } else {
            food.setFoodName(name);
            food.setCalories(cals);

            //save the food
            dba.addFood(food);
            dba.close();

            //clear the form;
            foodName.setText("");
            foodCals.setText("");

            //take user to next activity and show the list
            //startActivity(new Intent(MainActivity.this, DisplayFoodsActivity.class));

        }

    }
}
