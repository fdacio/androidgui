package br.com.daciosoftware.meumapa.db;

import java.io.Serializable;

public class Localizacao implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int id;
    private double latitude;
    private double longitude;
    private String nomeLocal;
    public double getLatitude() {
        return latitude;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getId() {
        return id;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getNomeLocal() {
        return nomeLocal;
    }
    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }
    
}
