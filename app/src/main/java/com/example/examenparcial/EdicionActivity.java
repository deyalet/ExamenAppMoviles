package com.example.examenparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.examenparcial.Modelos.Ver;
import com.example.examenparcial.WebService.Asynchtask;
import com.example.examenparcial.WebService.WebService;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EdicionActivity extends AppCompatActivity implements Asynchtask {
    String URL = "https://revistas.uteq.edu.ec/ws/pubs.php?i_id=";
    SwipePlaceHolderView placeHolderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);


        Bundle recibir_datos = this.getIntent().getExtras();
        Map<String, String> datos_map = new HashMap<String, String>();
        WebService web = new WebService(URL + recibir_datos.getString("issue_id"), datos_map, EdicionActivity.this, EdicionActivity.this);
        web.execute("GET");
        placeHolderView = findViewById(R.id.place_d_volumen);
    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONArray json_array = new JSONArray(result);
        for (int i = 0; i < json_array.length(); i++){
            JSONObject json_obj_descarga = json_array.getJSONObject(i);
            placeHolderView.addView(new Ver(getApplicationContext(), json_obj_descarga));
        }
    }
}