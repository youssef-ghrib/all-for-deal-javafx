package entities;

import java.util.List;

public class Commande {
    
    private int id;
    private String date;
    private float total;
    
    private List<LigneCmd> lignesCmd;
    
    private Membre consommateur;

    public Commande() {
    }

    public Commande(int id, String date, float total, Membre consommateur) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.consommateur = consommateur;
    }

    public Commande(String date, float total, Membre consommateur) {
        this.date = date;
        this.total = total;
        this.consommateur = consommateur;
    }

    @Override
    public String toString() {
        return "Commande{" + "id=" + id + ", date=" + date + ", total=" + total + ", consommateur=" + consommateur + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Membre getConsommateur() {
        return consommateur;
    }

    public void setConsommateur(Membre consommateur) {
        this.consommateur = consommateur;
    }

    public List<LigneCmd> getLignesCmd() {
        return lignesCmd;
    }

    public void setLignesCmd(List<LigneCmd> lignesCmd) {
        this.lignesCmd = lignesCmd;
    }
    
}
