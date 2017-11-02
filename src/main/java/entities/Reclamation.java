package entities;

import java.util.Date;
import java.util.Objects;

public class Reclamation {

    private String sujet;
    private String message;
    private String date;
    private Membre membre;
    private Service service;
    private int id_membre;
    private String reponse;

    public Reclamation(String sujet, String date, Service service, Membre membre) {
        this.sujet = sujet;
        this.date = date;
        this.service = service;
        this.membre = membre;
    }

    public Reclamation() {

    }

    public Reclamation(String sujet, String message, String date) {
        this.sujet = sujet;
        this.message = message;
        this.date = date;
    }

    public Reclamation(Membre membre, Service service, String sujet, String message, String date) {
        this.sujet = sujet;
        this.message = message;
        this.date = date;
        this.membre = membre;
        this.service = service;
    }

    public Reclamation(Membre membre, Service service, String sujet, String message, String date, String reponse) {
        this.sujet = sujet;
        this.message = message;
        this.date = date;
        this.membre = membre;
        this.service = service;
        this.reponse = reponse;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "sujet=" + sujet + ", message=" + message + ", date=" + date + '}';
    }

    @Override
    public int hashCode() {
        return 5;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reclamation other = (Reclamation) obj;
        if (!Objects.equals(this.sujet, other.sujet)) {
            return false;
        }
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

}
