package com.example.covid19update;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocalActivity extends AppCompatActivity {
    BarChart barChart;
    ListView listViewbd;
    LocalModel localModel;
    SimpleArcLoader simpleArcLoader;
    public static List<LocalModel> localModelList = new ArrayList<>();
    MyCustomAdapter2 myCustomAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        getSupportActionBar().setTitle("Local Stats");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        listViewbd = findViewById(R.id.listviewbd);
        barChart = findViewById(R.id.barchart);
        simpleArcLoader = findViewById(R.id.loaderc2);

        fetchData();
        barChart.addBar(new BarModel(25, 0xFF123456));
        barChart.addBar(new BarModel(35,  0xFF343456));
        barChart.addBar(new BarModel(40, 0xFF563456));
        barChart.addBar(new BarModel(60, 0xFF873F56));
        barChart.addBar(new BarModel(50, 0xFF56B7F1));
        barChart.addBar(new BarModel(32,  0xFF343456));
        barChart.addBar(new BarModel(42, 0xFF1FF4AC));
        barChart.addBar(new BarModel(25,  0xFF1BA4E6));

        barChart.startAnimation();

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void fetchData()
    {
        String url = "https://corona-bd.herokuapp.com/district";
        simpleArcLoader.start();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i=0; i < jsonArray.length();i++)
                    {
                        JSONObject area = jsonArray.getJSONObject(i);
                        String state = area.getString("name");
                        String cases = area.getString("count");

                        localModel = new LocalModel(state,cases);
                        localModelList.add(localModel);
                    }
                    myCustomAdapter2 = new MyCustomAdapter2(LocalActivity.this,localModelList);
                    listViewbd.setAdapter(myCustomAdapter2);
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                Toast.makeText(LocalActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}
