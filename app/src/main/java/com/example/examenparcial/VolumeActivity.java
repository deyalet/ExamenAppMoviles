package com.example.examenparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.examenparcial.Modelos.Volumenes;
import com.example.examenparcial.WebService.Asynchtask;
import com.example.examenparcial.WebService.WebService;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolumeActivity extends AppCompatActivity implements Asynchtask {
    String URL = "https://revistas.uteq.edu.ec/ws/issues.php?j_id=";
    SwipePlaceHolderView placeHolderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);

        Bundle recibir_datos = this.getIntent().getExtras();
        Map<String, String> datos_map = new HashMap<String, String>();
        WebService web = new WebService(URL + recibir_datos.getString("journal_id"), datos_map, VolumeActivity.this, VolumeActivity.this);
        web.execute("GET");

        placeHolderView = findViewById(R.id.place_r_volumenes);
    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONArray json_array = new JSONArray(result);
        for (int i = 0; i < json_array.length(); i++){
            JSONObject json_obj_volumen = json_array.getJSONObject(i);
            placeHolderView.addView(new Volumenes(getApplicationContext(), json_obj_volumen));
        }
    }
}