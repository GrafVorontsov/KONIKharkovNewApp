package ua.kharkov.koni.konikharkov;

import java.io.Serializable;

public class Amortizator implements Serializable {

    private int id;
    private String marka_name;
    private String model_name;
    private String car_name;
    private String correction;
    private String year;
    private String range;
    private String install;
    private String art_number;
    private String info;
    private String info_lowering;
    private String jpg;
    private String pdf;
    private String status;
    private String price_euro;

    public Amortizator(String marka_name, String model_name, String car_name, String correction, String year, String range, String install, String art_number, String status, String price_euro) {
        this.id = id;
        this.marka_name = marka_name;
        this.model_name = model_name;
        this.car_name = car_name;
        this.correction = correction;
        this.year = year;
        this.range = range;
        this.install = install;
        this.art_number = art_number;
        this.status = status;
        this.price_euro = price_euro;
    }

    public Amortizator(int id, String marka_name, String model_name, String car_name, String art_number, String price_euro) {
        this.id = id;
        this.marka_name = marka_name;
        this.model_name = model_name;
        this.car_name = car_name;
        this.art_number = art_number;
        this.price_euro = price_euro;

    }

    public Amortizator(String marka_name, String model_name, String car_name, String correction, String year, String range, String install, String art_number, String info, String info_lowering, String jpg, String pdf, String status, String price_euro) {
        this.marka_name = marka_name;
        this.model_name = model_name;
        this.car_name = car_name;
        this.correction = correction;
        this.year = year;
        this.range = range;
        this.install = install;
        this.art_number = art_number;
        this.info = info;
        this.info_lowering = info_lowering;
        this.jpg = jpg;
        this.pdf = pdf;
        this.status = status;
        this.price_euro = price_euro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarka_name() {
        return marka_name.toUpperCase().charAt(0) + marka_name.substring(1);
    }

    public void setMarka_name(String marka_name) {
        this.marka_name = marka_name;
    }

    public String getModel_name() {
        return model_name.toUpperCase().charAt(0) + model_name.substring(1);
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCorrection() {
        return correction;
    }

    public void setCorrection(String correction) {
        this.correction = correction;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getInstall() {
        return install;
    }

    public void setInstall(String install) {
        this.install = install;
    }

    public String getArt_number() {
        return art_number;
    }

    public void setArt_number(String art_number) {
        this.art_number = art_number;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo_lowering() {
        return info_lowering;
    }

    public void setInfo_lowering(String info_lowering) {
        this.info_lowering = info_lowering;
    }

    public String getJpg() {
        return jpg;
    }

    public void setJpg(String jpg) {
        this.jpg = jpg;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice_euro() {
        return price_euro;
    }

    public void setPrice_euro(String price_euro) {
        this.price_euro = price_euro;
    }
}