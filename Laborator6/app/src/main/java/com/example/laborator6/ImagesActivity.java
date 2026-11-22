package com.example.laborator6;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

    private List<ImaginiDomeniu> imagini;
    private ImagineAdapter adapter;

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        ListView listaImagini = findViewById(R.id.imagini);

        imagini = new ArrayList<>();

        imagini.add(new ImaginiDomeniu(
                "Desktop PC",
                null,
                "https://www.lenovo.com/ro/ro/desktops/"
        ));

        imagini.add(new ImaginiDomeniu(
                "Gaming PC",
                null,
                "https://www.lenovo.com/ro/ro/p/desktops/legion-desktops/legion-t-series-towers/lenovo-legion-tower-5i-gen-10-intel/len102g0009"
        ));

        imagini.add(new ImaginiDomeniu(
                "All-in-One PC",
                null,
                "https://www.lenovo.com/ro/ro/c/desktops/thinkcentre/"
        ));

        imagini.add(new ImaginiDomeniu(
                "Mini PC",
                null,
                "https://www.lenovo.com/gb/en/c/desktops/ideacentre/"
        ));

        imagini.add(new ImaginiDomeniu(
                "Workstation PC",
                null,
                "https://www.lenovo.com/ro/ro/p/workstations/thinkstationp/thinkstation-p360-tiny/len102s0005"
        ));

        adapter = new ImagineAdapter(
                imagini,
                this,
                R.layout.image_layout
        );

        listaImagini.setAdapter(adapter);

        listaImagini.setOnItemClickListener((parent, view, position, id) -> {
            String link = imagini.get(position).getLink();

            android.content.Intent intent =
                    new android.content.Intent(ImagesActivity.this, WebViewActivity.class);

            intent.putExtra("link", link);
            startActivity(intent);
        });

        incarcaImagini();
    }

    private void incarcaImagini() {

        String[] linkuriImagini = {
                "https://s13emagst.akamaized.net/products/144868/144867708/images/res_6d6cc350d9e6ea9808bc7c36dee67ec8.jpg",
                "https://s13emagst.akamaized.net/products/91018/91017675/images/res_b4b2483e29e502bbc60488aa1228bca3.jpg",
                "https://www.coreinnovationsinc.com/wp-content/uploads/2024/02/CC2400-1-600x600.jpg",
                "https://gomagcdn.ro/domains/dualstore.ro/files/product/original/mini-pc-blackview-mp60-negru-4k-60hz-16gb-ram-lpddr4-512gb-rom-ssd-intel-n95-windows-11-pro-display-dual-4k-wi-fi-bt-4-2-copie-1430966093.jpg",
                "https://www.hp.com/content/dam/sites/worldwide/personal-computers/commercial/workstations/desktop-workstation-pc/z-2-mini-g-9-3.png"
        };

        for (int i = 0; i < linkuriImagini.length; i++) {

            final int pozitie = i;
            final String link = linkuriImagini[i];

            executor.execute(() -> {

                Bitmap bitmap = descarcaImagine(link);

                handler.post(() -> {
                    if (bitmap != null) {
                        ImaginiDomeniu imagine = imagini.get(pozitie);

                        imagini.set(pozitie, new ImaginiDomeniu(
                                imagine.getTextAfisat(),
                                bitmap,
                                imagine.getLink()
                        ));

                        adapter.notifyDataSetChanged();
                    }
                });
            });
        }
    }

    private Bitmap descarcaImagine(String link) {

        HttpURLConnection conexiune = null;

        try {
            URL url = new URL(link);
            conexiune = (HttpURLConnection) url.openConnection();
            conexiune.setConnectTimeout(10000);
            conexiune.setReadTimeout(10000);
            conexiune.setRequestMethod("GET");

            InputStream input = conexiune.getInputStream();

            Bitmap bitmap = BitmapFactory.decodeStream(input);

            input.close();

            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            if (conexiune != null) {
                conexiune.disconnect();
            }
        }
    }
}