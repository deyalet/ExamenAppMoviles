package com.example.examenparcial.Modelos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.examenparcial.R;
import com.example.examenparcial.VolumeActivity;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import org.json.JSONException;
import org.json.JSONObject;

@NonReusable
@Layout(R.layout.item_revista)
public class Revista {
    // Cambiar los valores por la API
    @View(R.id.txtTitulo)
    TextView txtTitulo;

    @View(R.id.txtDescripcion)
    TextView txtDescripcion;

    @View(R.id.img_revista)
    ImageView img_revista;

    @Click(R.id.btnVerRevista)
    public void onRevistaViewClick() {
        try {
            Intent intent = new Intent(ctx.getApplicationContext(), VolumeActivity.class);

            Bundle cambio_app = new Bundle();
            cambio_app.putString("journal_id", obj_revista_json.getString("journal_id"));
            intent.putExtras(cambio_app);
            ctx.startActivity(intent);

        } catch (JSONException ex) {
        }
    }

    Context ctx;
    JSONObject obj_revista_json;

    // Constructor que recibira el context de la aplicación y un JSONObjet
    public Revista(Context context, JSONObject item_obj_revista) {
        ctx = context;
        obj_revista_json = item_obj_revista;
    }

    // Lectura de la API y cambiandolo al layout creado
    @Resolve
    protected void onResolved() {
        try {
            this.txtTitulo.setText(Html.fromHtml(obj_revista_json.getString("name")));
            this.txtDescripcion.setText(Html.fromHtml(obj_revista_json.getString("description")));
            Glide.with(ctx).load(obj_revista_json.getString("portada"))
                    .into(img_revista);
        } catch (JSONException ex) {
        }
    }
}
