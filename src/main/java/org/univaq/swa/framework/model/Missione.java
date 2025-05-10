package org.univaq.swa.framework.model;

import java.time.LocalDateTime;

public class Missione {
    private int id;
    private String obiettivo;
    private LocalDateTime inizio;
    private LocalDateTime fine;
    private StatoMissione stato;
    private String commenti;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getObiettivo() {
        return obiettivo;
    }
    public void setObiettivo(String obiettivo) {
        this.obiettivo = obiettivo;
    }
    public LocalDateTime getInizio() {
        return inizio;
    }
    public void setInizio(LocalDateTime inizio) {
        this.inizio = inizio;
    }
    public LocalDateTime getFine() {
        return fine;
    }
    public void setFine(LocalDateTime fine) {
        this.fine = fine;
    }
    public StatoMissione getStato() {
        return stato;
    }
    public void setStato(StatoMissione stato) {
        this.stato = stato;
    }


    public String getCommenti() {
        return commenti;
    }
    public void setCommenti(String commenti) {
        this.commenti = commenti;
    }
}
