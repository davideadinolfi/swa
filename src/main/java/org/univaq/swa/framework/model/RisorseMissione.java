package org.univaq.swa.framework.model;

public class RisorseMissione {
    private int id;
    private Object risorsa; // User oppure Risorsa
    private String commento;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Object getRisorsa() {
        return risorsa;
    }
    public void setRisorsa(Object risorsa) {
        if(risorsa.getClass() != User.class && risorsa.getClass() != Risorsa.class) {
            throw new IllegalArgumentException("Risorsa deve essere di tipo User o Risorsa");
        }
        else
            this.risorsa = risorsa;
    }
    public String getCommento() {
        return commento;
    }
    public void setCommento(String commento) {
        this.commento = commento;
    }

}
