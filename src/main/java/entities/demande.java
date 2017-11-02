/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;

/**
 *
 * @author Pato
 */
public class demande {
    
    
    
    Membre id_membre;
    Service id_service;
    String datdebu;
    String datefin;
    String lieu;
    String message;

    public demande(String datdebu, String datefin, String lieu, String message) {
        this.datdebu = datdebu;
        this.datefin = datefin;
        this.lieu = lieu;
        this.message = message;
    }

    public demande(Membre id_membre, Service id_service, String datdebu, String datefin, String lieu, String message) {
        this.id_membre = id_membre;
        this.id_service = id_service;
        this.datdebu = datdebu;
        this.datefin = datefin;
        this.lieu = lieu;
        this.message = message;
    }
    

    public Membre getId_membre() {
        return id_membre;
    }

    public void setId_membre(Membre id_membre) {
        this.id_membre = id_membre;
    }

    public Service getId_service() {
        return id_service;
    }

    public void setId_service(Service id_service) {
        this.id_service = id_service;
    }

    public String getDatdebu() {
        return datdebu;
    }

    public void setDatdebu(String datdebu) {
        this.datdebu = datdebu;
    }

    public String getDatefin() {
        return datefin;
    }

    public void setDatefin(String datefin) {
        this.datefin = datefin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "demande{" + "id_membre=" + id_membre + ", id_service=" + id_service + ", datdebu=" + datdebu + ", datefin=" + datefin + ", lieu=" + lieu + ", message=" + message + '}';
    }
    
    
}
