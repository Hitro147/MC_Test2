package com.example.mc_test2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class MC_Test2_2ndPage extends AppCompatActivity {

    //Initialize Variable
    Button submit_Button;
    private String selected_category;
    private Bitmap bmp;
    JSONObject json = new JSONObject();
    String message;
    String filename;

    public static final MediaType JSON = MediaType.parse("application/json");

    private String url = "http://" + "10.5.0.2" + ":" + 8080 + "/upload";
    private String postBodyString;
    private MediaType mediaType;
    private RequestBody requestBody;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_mc_test22nd_page);
        submit_Button = findViewById(R.id.submit_button);
        Spinner category_spinner =findViewById(R.id.category_spinner);

        submit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_category = (String) category_spinner.getSelectedItem();
                filename = UUID.randomUUID().toString();

                try {
                    json.put("category", selected_category);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    json.put("filename", filename);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                Log.v("Json Body Message", String.valueOf(json));

                postRequest(String.valueOf(json), url);
            }
        });

        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.category_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        category_spinner.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        byte[] byteArrayImg = extras.getByteArray("BitmapImage");

        Bitmap bmp = BitmapFactory.decodeByteArray(byteArrayImg, 0, byteArrayImg.length);
        String encoded = Base64.encodeToString(byteArrayImg, Base64.DEFAULT);
        Log.v("base64_image", encoded);
        try {
            json.put("base64_image", encoded);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ImageView image = (ImageView) findViewById(R.id.image_view2);
        image.setImageBitmap(bmp);

    }



    private void postRequest(String json, String URL) {

        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody body = RequestBody.create(json, JSON); // new
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(MC_Test2_2ndPage.this, "Something went wrong:" + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        call.cancel();

                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Toast.makeText(MC_Test2_2ndPage.this, response.body().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }


}

