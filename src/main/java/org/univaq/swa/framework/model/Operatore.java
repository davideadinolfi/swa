package org.univaq.swa.framework.model;

import java.util.LinkedList;

public class Operatore extends User {
    private StatoOp stato;
    private LinkedList<String> informazioni;
    private LinkedList<Missione> missioni;

    public Operatore() {
        super();
        
    }

    public StatoOp getStato() {
        return stato;
    }

    public void setStato(StatoOp stato) {
        this.stato = stato;
    }

    public LinkedList<String> getInformazioni() {
        return informazioni;
    }

    public void addInformazioni(String informazione) {
        if (informazioni == null) {
            informazioni = new LinkedList<>();
        }
        informazioni.add(informazione);
    }

    public void addMissione(Missione missione) {
        if (missioni == null) {
            missioni = new LinkedList<>();
        }
        missioni.add(missione);
    }


    public LinkedList<Missione> getMissioni() {
        return missioni;
    }

    public void setMissioni(LinkedList<Missione> missioni) {
        this.missioni = missioni;
    }
    
}
