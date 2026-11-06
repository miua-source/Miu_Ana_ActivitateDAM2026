package com.example.laborator6;

import android.os.Parcel;
import android.os.Parcelable;

public class Calculator implements Parcelable {

    private String denumire;
    private String marca;
    private String procesor;
    private int memorieRAM;
    private int capacitateStocare;
    private float rating;

    public Calculator(String denumire, String marca, String procesor,
                      int memorieRAM, int capacitateStocare, float rating) {
        this.denumire = denumire;
        this.marca = marca;
        this.procesor = procesor;
        this.memorieRAM = memorieRAM;
        this.capacitateStocare = capacitateStocare;
        this.rating = rating;
    }

    protected Calculator(Parcel in) {
        denumire = in.readString();
        marca = in.readString();
        procesor = in.readString();
        memorieRAM = in.readInt();
        capacitateStocare = in.readInt();
        rating = in.readFloat();
    }

    public static final Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(denumire);
        dest.writeString(marca);
        dest.writeString(procesor);
        dest.writeInt(memorieRAM);
        dest.writeInt(capacitateStocare);
        dest.writeFloat(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getDenumire() {
        return denumire;
    }

    public String getMarca() {
        return marca;
    }

    public String getProcesor() {
        return procesor;
    }

    public int getMemorieRAM() {
        return memorieRAM;
    }

    public int getCapacitateStocare() {
        return capacitateStocare;
    }

    public float getRating() {
        return rating;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setProcesor(String procesor) {
        this.procesor = procesor;
    }

    public void setMemorieRAM(int memorieRAM) {
        this.memorieRAM = memorieRAM;
    }

    public void setCapacitateStocare(int capacitateStocare) {
        this.capacitateStocare = capacitateStocare;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Denumire: " + denumire +
                "\nMarca: " + marca +
                "\nProcesor: " + procesor +
                "\nMemorie RAM: " + memorieRAM + " GB" +
                "\nCapacitate stocare: " + capacitateStocare + " GB" +
                "\nRating: " + rating;
    }
}