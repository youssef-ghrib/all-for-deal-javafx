package entities;

public class AppelOffre {

    private int id;
    private String sujet;
    private String description;
    private String dateDebut;
    private String dateFin;
    private String lieu;
    private String remarques;
    private Membre consommateur;

    public AppelOffre() {
    }

    public AppelOffre(int id, String sujet, String description, String dateDebut, String dateFin, String lieu, String remarques, Membre consommateur) {
        this.id = id;
        this.sujet = sujet;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.lieu = lieu;
        this.remarques = remarques;
        this.consommateur = consommateur;
    }

    public AppelOffre(String sujet, String description, String dateDebut, String dateFin, String lieu, String remarques, Membre consommateur) {
        this.sujet = sujet;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.lieu = lieu;
        this.remarques = remarques;
        this.consommateur = consommateur;
    }

    @Override
    public String toString() {
        return "Membre{" + "id=" + id + ", sujet=" + sujet + ", description=" + description + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", lieu=" + lieu + ", remarques=" + remarques + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AppelOffre other = (AppelOffre) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getRemarques() {
        return remarques;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    public Membre getConsommateur() {
        return consommateur;
    }

    public void setConsommateur(Membre consommateur) {
        this.consommateur = consommateur;
    }

}
