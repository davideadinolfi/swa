package org.univaq.swa.framework.model;

public class Storico_op {
    private int id;
    private Operatore operatore;
    private Missione missione;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Operatore getOperatore() {
        return operatore;
    }
    public void setOperatore(Operatore operatore) {
        this.operatore = operatore;
    }
    public Missione getMissione() {
        return missione;
    }
    public void setMissione(Missione missione) {
        this.missione = missione;
    }
}
