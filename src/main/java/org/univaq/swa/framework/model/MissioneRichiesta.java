package org.univaq.swa.framework.model;

public class MissioneRichiesta {
    int idRichiesta;
    String obiettivo;
    String commenti;
    public int getIdRichiesta() {
        return idRichiesta;
    }
    public void setIdRichiesta(String idRichiesta) {
        this.idRichiesta = Integer.parseInt(idRichiesta);
    }
    public String getObiettivo() {
        return obiettivo;
    }
    public void setObiettivo(String obiettivo) {
        this.obiettivo = obiettivo;
    }
    public String getCommenti() {
        return commenti;
    }
    public void setCommenti(String commenti) {
        this.commenti = commenti;
    }
}
