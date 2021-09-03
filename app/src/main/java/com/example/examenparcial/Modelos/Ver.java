package com.example.examenparcial.Modelos;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html;
import android.widget.TextView;

import com.example.examenparcial.R;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@NonReusable
@Layout(R.layout.item_ediciones)
public class Ver {
    @View(R.id.txtEdicion)
    TextView txtTitulo;

    @View(R.id.txtAutores)
    TextView txtAutores;

    @Click(R.id.btnPDF)
    public void onVolumenViewClick() {
        new PDF().execute();
    }
    Context ctx;
    JSONObject obj_json;

    @Click(R.id.btnHTML)

    public void onHTMLViewClick() throws JSONException {
        JSONArray array_galeria = obj_json.getJSONArray("galeys");
        for (int i = 0; i < array_galeria.length(); i++) {
            JSONObject object_galeria = array_galeria.getJSONObject(i);
            String url_string = object_galeria.getString("UrlViewGalley");
            String nombre = obj_json.getString("section") + "-" + obj_json.getString("publication_id") ;

         }
    }
    public class PDF extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                JSONArray array_galeria = obj_json.getJSONArray("galeys");
                for (int i = 0; i < array_galeria.length(); i++) {
                    JSONObject object_galeria = array_galeria.getJSONObject(i);
                    String url_string = object_galeria.getString("UrlViewGalley");
                    String nombre = obj_json.getString("section") + "-" + obj_json.getString("publication_id") + ".pdf";
                    try {
                        byte[] todo_contenido = null;
                        byte[] parte_contenido = new byte[1024];
                        ByteArrayOutputStream stream_arreglo = new ByteArrayOutputStream();
                        URL url = new URL(url_string);
                        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                        conexion.connect();

                        int contador = 0;

                        while ((contador = conexion.getInputStream().read(parte_contenido)) != -1) {
                            stream_arreglo.write(parte_contenido, 0, contador);
                            stream_arreglo.flush();
                        }

                        todo_contenido = stream_arreglo.toByteArray();

                        File archivo = new File(Environment.getRootDirectory(), nombre);
                        FileOutputStream stream_archivo = new FileOutputStream(archivo);
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(stream_archivo);

                        bufferedOutputStream.write(todo_contenido);
                        bufferedOutputStream.close();

                        System.out.println("Achivo descargado como PDF");

                    } catch (Exception exm) {
                        System.out.println("Achivo no descargado" + exm.getMessage());
                    }
                    break;
                }
            } catch (JSONException ex) {
            }
            return  null;
        }
    }

    public Ver(Context context, JSONObject item_obj_revista) {
        ctx = context;
        obj_json = item_obj_revista;
    }

    protected void onResolved() {
        try {
            this.txtTitulo.setText(Html.fromHtml(obj_json.getString("title")));
            JSONArray array_authors = obj_json.getJSONArray("authors");
            for (int i = 0; i < array_authors.length(); i++) {
                JSONObject object_author = array_authors.getJSONObject(i);
                txtAutores.append(object_author.getString("nombres"));
                if (i < array_authors.length() - 1)
                    txtAutores.append(", ");
            }
        } catch (JSONException ex) {
        }
    }
}
