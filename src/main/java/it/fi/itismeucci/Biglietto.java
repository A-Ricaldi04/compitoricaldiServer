package it.fi.itismeucci;

public class Biglietto {
    private int identificativo;
    private String Nbiglietto;
    public Biglietto(String nbiglietto) {
        identificativo+=1;
        Nbiglietto = nbiglietto;
    }
    public int getIdentificativo() {
        return identificativo;
    }
    public void setIdentificativo(int identificativo) {
        this.identificativo = identificativo;
    }
    public String getNbiglietto() {
        return Nbiglietto;
    }
    public void setNbiglietto(String nbiglietto) {
        Nbiglietto = nbiglietto;
    }
    
}
