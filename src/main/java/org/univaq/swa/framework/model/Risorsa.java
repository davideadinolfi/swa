package org.univaq.swa.framework.model;

public class Risorsa {
    private int id;
    private StatoOp stato;
    private String info;
    private TipoRisorsa tipo;
    private String nome;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public StatoOp getStato() {
        return stato;
    }
    public void setStato(StatoOp stato) {
        this.stato = stato;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public TipoRisorsa getTipo() {
        return tipo;
    }
    public void setTipo(TipoRisorsa tipo) {
        this.tipo = tipo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
