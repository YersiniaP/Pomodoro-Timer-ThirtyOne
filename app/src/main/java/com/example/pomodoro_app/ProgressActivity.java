package com.example.pomodoro_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ProgressActivity extends AppCompatActivity {

    private Button progress_button_level;
    private Button progress_button_sign_out;
    private Button progress_button_create_task;
    private String active_email; // email of active user
    public static final String EXTRA_EMAIL = "com.example.pomodoro_app.EXTRA_EMAIL";
    private PieChart pie_chart;
    private Description pie_description;
    private Legend pie_legend;
    ArrayList<PieEntry> pie_entries = new ArrayList<>(); // Converted Category objects
    ArrayList<Category> pie_categories = new ArrayList<>(); // Category objects


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        // Grabs email from login page
        Intent intent_user = getIntent();
        active_email = intent_user.getStringExtra(LoginActivity.EXTRA_EMAIL);

        // Binds UI event listeners
        BindButtons();

        // Pie chart
        GeneratePie();
    }

    @Override
    public void onBackPressed() {
        /* This prevents the app from automatically closing and insteads returns the user back
        to the login page */
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        finish();
        startActivity(intent);
    }


    // Binds UI elements
    private void BindButtons(){
        // Clicking Level button initializes the Rewards page.
        progress_button_level = (Button) findViewById(R.id.progress_button_level);
        progress_button_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenRewardsPage();
            }
        });

        // Clicking Sign Out button switches to the Login page.
        progress_button_sign_out = (Button) findViewById(R.id.progress_button_sign_out);
        progress_button_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Clicking Create Task button switches to the Task Creation page.
        progress_button_create_task = (Button) findViewById(R.id.progress_button_create);
        progress_button_create_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTaskCreationPage();
            }
        });
    }

    // Adds category data to pie chart and renders it
    private void GeneratePie(){
        pie_chart = (PieChart) findViewById(R.id.progress_pie_chart);

        // Sample Category objects
        pie_categories.add(new Category("cooking", 5));
        pie_categories.add(new Category("gaming", 3));
        pie_categories.add(new Category("cleaning", 4));
        pie_categories.add(new Category("coding", 10));

        // Converts all Category objects to PieEntry objects
        for (Category category : pie_categories){
            // Placeholder data; category data should be added here
            pie_entries.add(new PieEntry(category.GetFrequency(), category.GetName()));
        }

        // Customizes pie's appearance
        PieDataSet pie_data_set = new PieDataSet(pie_entries, "");
        pie_data_set.setColors(ColorTemplate.COLORFUL_COLORS);
        pie_data_set.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pie_data_set.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pie_data_set.setValueTextSize(16);
        pie_chart.setHoleRadius(0);
        //pie_chart.setNoDataText("You haven't completed any tasks today!");
        pie_chart.setTransparentCircleRadius(0);
        pie_description = pie_chart.getDescription();
        pie_description.setEnabled(false);
        pie_chart.setDescription(pie_description);
        pie_legend = pie_chart.getLegend();
        pie_legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        pie_legend.setWordWrapEnabled(true);
        pie_chart.animateXY(1000, 1000); // Refreshes pie chart

        // Ties data to pie object
        PieData pie_data = new PieData(pie_data_set);
        pie_chart.setData(pie_data);
    }

    // When the user clicks on the Level button from the Progress page, the rewards page opens.
    public void OpenRewardsPage() {
        Intent intent = new Intent(getApplicationContext(), RewardsActivity.class);
        intent.putExtra(EXTRA_EMAIL, active_email); // Passes active email to Rewards page.
        startActivity(intent);
    }

    // When the user clicks on the Level button from the Progress page, the rewards page opens.
    public void OpenTaskCreationPage() {
        Intent intent = new Intent(getApplicationContext(), TaskCreationActivity.class);
        startActivity(intent);
    }

    public void SignOut() {
        Toast.makeText(ProgressActivity.this, "Signed Off, GoodBye!", Toast.LENGTH_SHORT).show();
        finish();
    }


}