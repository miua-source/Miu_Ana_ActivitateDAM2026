package com.example.laborator6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CalculatorAdapter extends BaseAdapter {

    private List<Calculator> calculatoare = null;
    private Context ctx;
    private int resurseLayout;

    public CalculatorAdapter(List<Calculator> calculatoare,
                             Context ctx, int resurseLayout) {
        this.calculatoare = calculatoare;
        this.ctx = ctx;
        this.resurseLayout = resurseLayout;
    }

    @Override
    public int getCount() {
        return calculatoare.size();
    }

    @Override
    public Object getItem(int i) {
        return calculatoare.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(resurseLayout, parent, false);

        TextView denumire = v.findViewById(R.id.denumire);
        TextView marca = v.findViewById(R.id.marca);
        TextView procesor = v.findViewById(R.id.procesor);
        TextView memorieRAM = v.findViewById(R.id.memorieRAM);
        TextView capacitateStocare = v.findViewById(R.id.capacitateStocare);
        TextView rating = v.findViewById(R.id.rating);

        Calculator calculator = (Calculator) getItem(position);

        denumire.setText(calculator.getDenumire());
        marca.setText(calculator.getMarca());
        procesor.setText(calculator.getProcesor());
        memorieRAM.setText(String.valueOf(calculator.getMemorieRAM()));
        capacitateStocare.setText(String.valueOf(calculator.getCapacitateStocare()));
        rating.setText(String.valueOf(calculator.getRating()));

        return v;
    }
}