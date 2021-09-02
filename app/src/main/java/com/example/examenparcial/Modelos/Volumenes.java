package com.example.examenparcial.Modelos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.examenparcial.EdicionActivity;
import com.example.examenparcial.R;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import org.json.JSONException;
import org.json.JSONObject;

@NonReusable
@Layout(R.layout.item_volumen)
public class Volumenes {

    // Acciones para cambiar los valores por la API
    @View(R.id.txt_R_tvolumen)
    TextView txtTitulo;

    @View(R.id.txt_fech_volumen)
    TextView txtFecha;

    @View(R.id.txt_V_doi)
    TextView txtDOI;

    @View(R.id.img_volumen)
    ImageView img_revista;

    @Click(R.id.btn_D_edicion)

    public void onVolumenViewClick() {
        try {
            Intent intent = new Intent(ctx.getApplicationContext(), EdicionActivity.class);

            Bundle cambio_app = new Bundle();
            cambio_app.putString("issue_id", obj_volumen_json.getString("issue_id"));
            intent.putExtras(cambio_app);
            ctx.startActivity(intent);

        } catch (JSONException ex) {
        }
    }

    Context ctx;
    JSONObject obj_volumen_json;

    // Constructor que recibira el context de la aplicación y un JSONObjet
    public Volumenes(Context context, JSONObject item_obj_revista) {
        ctx = context;
        obj_volumen_json = item_obj_revista;
    }

    // Lectura de la API y cambiandolo al layout creado
    @Resolve
    protected void onResolved() {
        try {
            // Html.fromHtml() "Sirve para convertir HTML en texto normal"
            this.txtTitulo.setText(Html.fromHtml(obj_volumen_json.getString("title")));
            this.txtFecha.setText(Html.fromHtml(obj_volumen_json.getString("date_published")));
            this.txtDOI.setText(Html.fromHtml(obj_volumen_json.getString("doi")));
            Glide.with(ctx).load(obj_volumen_json.getString("cover"))
                    .into(img_revista);
        } catch (JSONException ex) {
        }
    }
}
