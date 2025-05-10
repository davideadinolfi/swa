package org.univaq.swa.framework.model;

public class Richiesta {
    private int id;
    private String email;
    private String indirizzo;
    private StatoRichiesta stato;
    private boolean convalida;
    private Missione missione;
    public Missione getMissione() {
        return missione;
    }
    public void setMissione(Missione missione) {
        this.missione = missione;
    }
    private int successo;

    public boolean isConvalida() {
        return convalida;
    }
    public void setConvalida(boolean convalida) {
        this.convalida = convalida;
    }
    public Richiesta(){}
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getIndirizzo() {
        return indirizzo;
    }
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    public StatoRichiesta getStato() {
        return stato;
    }
    public void setStato(StatoRichiesta stato) {
        this.stato = stato;
    }
    public int getSuccesso() {
        return successo;
    }
    public void setSuccesso(int successo) {
        if(successo > 5)
            successo = 5;
        else if(successo < 0)
            successo = 0;
        this.successo = successo;
    }

}
