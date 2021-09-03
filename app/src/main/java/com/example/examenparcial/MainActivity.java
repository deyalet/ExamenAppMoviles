package com.example.examenparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.examenparcial.Modelos.Revista;
import com.example.examenparcial.WebService.Asynchtask;
import com.example.examenparcial.WebService.WebService;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    String URL = "https://revistas.uteq.edu.ec/ws/journals.php";
    SwipePlaceHolderView placeHolderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, String> datos_map = new HashMap<String, String>();
        WebService web = new WebService(URL, datos_map, MainActivity.this, MainActivity.this);
        web.execute("GET");
        placeHolderView = findViewById(R.id.place_revista);

    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONArray json_array = new JSONArray(result);
        for (int i = 0; i < json_array.length(); i++){
            JSONObject json_obj_revista = json_array.getJSONObject(i);
            placeHolderView.addView(new Revista(getApplicationContext(), json_obj_revista));
        }
    }
}