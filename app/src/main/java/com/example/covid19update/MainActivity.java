package com.example.covid19update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tvcases,tvrecovered,tvcritical,tvactive,tvtodaycases,tvaffectedcountries,tvtodaydeath,tvtotaldeath;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;
    Button btntrack,btnbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvcases = findViewById(R.id.tv_cases);
        tvrecovered = findViewById(R.id.tv_recovered);
        tvcritical = findViewById(R.id.tv_critical);
        tvactive = findViewById(R.id.tv_active);
        tvtodaycases = findViewById(R.id.tv_todaycases);
        tvaffectedcountries = findViewById(R.id.tv_affectedcountry);
        tvtodaydeath = findViewById(R.id.tv_todaydeath);
        tvtotaldeath = findViewById(R.id.tv_totaldeath);
        simpleArcLoader = findViewById(R.id.loader);
        scrollView = findViewById(R.id.scrollViewstats);
        pieChart = findViewById(R.id.piechart);
        btntrack = findViewById(R.id.btntrack);
        btnbd = findViewById(R.id.btnbd);

        fetchData();
        btntrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CountryActivity.class);
                startActivity(intent);
            }
        });
        btnbd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LocalActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchData() {
        String url = "https://corona.lmao.ninja/v2/all/";
        simpleArcLoader.start();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //we get our data here
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    tvcases.setText(jsonObject.getString("cases"));
                    tvrecovered.setText(jsonObject.getString("recovered"));
                    tvcritical.setText(jsonObject.getString("critical"));
                    tvactive.setText(jsonObject.getString("active"));
                    tvtodaycases.setText(jsonObject.getString("todayCases"));
                    tvtotaldeath.setText(jsonObject.getString("deaths"));
                    tvtodaydeath.setText(jsonObject.getString("todayDeaths"));
                    tvaffectedcountries.setText(jsonObject.getString("affectedCountries"));

                    pieChart.addPieSlice(new PieModel("cases", Integer.parseInt(tvcases.getText().toString()), Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(new PieModel("recovered", Integer.parseInt(tvrecovered.getText().toString()), Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(new PieModel("deaths", Integer.parseInt(tvtotaldeath.getText().toString()), Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(new PieModel("active", Integer.parseInt(tvactive.getText().toString()), Color.parseColor("#29B6F6")));
                    pieChart.startAnimation();

                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);



                }
                catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
