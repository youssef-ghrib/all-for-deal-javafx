package entities;

import java.util.Objects;

public class AppelOffreMembre {
    private Membre fournisseur;
    private AppelOffre appelOffre;

    public AppelOffreMembre() {
    }

    public AppelOffreMembre(Membre fournisseur, AppelOffre appelOffre) {
        this.fournisseur = fournisseur;
        this.appelOffre = appelOffre;
    }

    @Override
    public String toString() {
        return "AppelOffreMembre{" + "fournisseur=" + fournisseur + ", appelOffre=" + appelOffre + '}';
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
        final AppelOffreMembre other = (AppelOffreMembre) obj;
        if (!Objects.equals(this.fournisseur, other.fournisseur)) {
            return false;
        }
        if (!Objects.equals(this.appelOffre, other.appelOffre)) {
            return false;
        }
        return true;
    }

    public Membre getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Membre fournisseur) {
        this.fournisseur = fournisseur;
    }

    public AppelOffre getAppelOffre() {
        return appelOffre;
    }

    public void setAppelOffre(AppelOffre appelOffre) {
        this.appelOffre = appelOffre;
    }
}
