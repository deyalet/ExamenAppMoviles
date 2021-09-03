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
            cambio_app.putString("journal_id", obj_json.getString("journal_id"));
            intent.putExtras(cambio_app);
            ctx.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP));

        } catch (JSONException ex) {
        }
    }

    Context ctx;
    JSONObject obj_json;
    public Revista(Context context, JSONObject item_obj_revista) {
        ctx = context;
        obj_json = item_obj_revista;
    }
    @Resolve
    protected void onResolved() {
        try {
            this.txtTitulo.setText(Html.fromHtml(obj_json.getString("name")));
            this.txtDescripcion.setText(Html.fromHtml(obj_json.getString("description")));
            Glide.with(ctx).load(obj_json.getString("portada"))
                    .into(img_revista);
        } catch (JSONException ex) {
        }
    }
}
