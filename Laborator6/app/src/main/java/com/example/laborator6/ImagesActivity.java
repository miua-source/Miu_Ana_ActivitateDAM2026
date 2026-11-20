package com.example.laborator6;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Intent;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImagesActivity extends AppCompatActivity {

    private ListView lv;
    private List<ImaginiDomeniu> lista;

    private Executor executor;
    private Handler handler;

    private List<String> linkuriImagini;
    private List<Bitmap> imagini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        lv = findViewById(R.id.imagini);

        lista = new ArrayList<>();
        imagini = new ArrayList<>();
        linkuriImagini = new ArrayList<>();

        linkuriImagini.add("https://commons.wikimedia.org/wiki/Special:FilePath/Simple_Calculator.jpg");
        linkuriImagini.add("https://commons.wikimedia.org/wiki/Special:FilePath/A_modern_scientific_calculator.jpeg");
        linkuriImagini.add("https://commons.wikimedia.org/wiki/Special:FilePath/Graphing_calculator_image.jpg");
        linkuriImagini.add("https://commons.wikimedia.org/wiki/Special:FilePath/Financial_Calculator_Hewlett-Packard_HP-12C_built_from_1981,_this_item_was_produced_in_1988.jpg");
        linkuriImagini.add("https://commons.wikimedia.org/wiki/Special:FilePath/HP-12C_programmable_calculator.jpg");

        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {

                for (String link : linkuriImagini) {

                    HttpURLConnection con = null;
                    try {
                        URL url = new URL(link);
                        con = (HttpURLConnection) url.openConnection();

                        con.setRequestMethod("GET");
                        con.connect();

                        InputStream is = con.getInputStream();

                        Bitmap bitmap = BitmapFactory.decodeStream(is);

                        imagini.add(bitmap);

                        is.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                        imagini.add(null);
                    } finally {
                        if (con != null) {
                            con.disconnect();
                        }
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        String[] descrieri = {
                                "Calculator simplu - pentru operatii aritmetice de baza",
                                "Calculator stiintific - pentru functii matematice avansate",
                                "Calculator grafic - pentru reprezentarea functiilor",
                                "Calculator financiar - pentru calcule financiare si dobanzi",
                                "Calculator programabil - pentru operatii programate"
                        };

                        String[] linkuriPagini = {
                                "https://en.wikipedia.org/wiki/Calculator",
                                "https://en.wikipedia.org/wiki/Scientific_calculator",
                                "https://en.wikipedia.org/wiki/Graphing_calculator",
                                "https://en.wikipedia.org/wiki/Financial_calculator",
                                "https://en.wikipedia.org/wiki/Programmable_calculator"
                        };

                        for (int i = 0; i < imagini.size(); i++) {

                            if (imagini.get(i) != null) {

                                lista.add(new ImaginiDomeniu(
                                        descrieri[i],
                                        imagini.get(i),
                                        linkuriPagini[i]
                                ));
                            }
                        }

                        ImagineAdapter adapter = new ImagineAdapter(
                                lista,
                                ImagesActivity.this,
                                R.layout.image_layout
                        );

                        lv.setAdapter(adapter);

                        lv.setOnItemClickListener((parent, view, position, id) -> {

                            Intent intent = new Intent(
                                    ImagesActivity.this, WebViewActivity.class);

                            intent.putExtra("link", lista.get(position).getLink());

                            startActivity(intent);
                        });
                    }
                });
            }
        });
    }
}