package com.example.laborator9;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WeatherActivity extends AppCompatActivity {

    EditText etOras;
    Button btnCauta;
    TextView tvRezultat;

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final String API_KEY = "API_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        etOras = findViewById(R.id.etOras);
        btnCauta = findViewById(R.id.btnCauta);
        tvRezultat = findViewById(R.id.tvRezultat);

        btnCauta.setOnClickListener(v -> {

            String oras = etOras.getText().toString().trim();

            if (oras.isEmpty()) {
                etOras.setError("Introduceti un oras!");
                return;
            }

            tvRezultat.setText("Se cauta...");

            cautaOras(oras);
        });
    }

    private void cautaOras(String oras) {

        executor.execute(() -> {

            String rezultat;

            try {

                String orasCodificat = URLEncoder.encode(oras, "UTF-8");

                String link = "https://dataservice.accuweather.com/locations/v1/cities/search" + "?apikey=" + API_KEY + "&q=" + orasCodificat;
                //TREBUIE NEAPARAT HTTPS NU HTTP

                URL url = new URL(link);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder builder = new StringBuilder("");

                String linie;

                while ((linie = reader.readLine()) != null) {
                    builder.append(linie);
                }

                reader.close();
                conn.disconnect();

                JSONArray vector = new JSONArray(builder.toString());

                if (vector.length() == 0) {
                    rezultat = "Orasul nu a fost gasit!";
                } else {

                    JSONObject obiect = vector.getJSONObject(0);

                    String cheieOras = obiect.getString("Key");

                    rezultat = "Codul orasului: " + cheieOras;
                }

            } catch (Exception e) {

                rezultat = "Eroare: " + e.getMessage();
            }

            String rezultatFinal = rezultat;

            handler.post(() -> {
                tvRezultat.setText(rezultatFinal);
            });

        });
    }
}