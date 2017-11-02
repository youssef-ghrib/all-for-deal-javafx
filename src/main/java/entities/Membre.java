package entities;

import java.util.Date;

public class Membre {

    private int id;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String genre;
    private String adresse;
    private String type;

    private int points;
    private String login;
    private String password;
    private String mail;
    private int num;
    private String imageName;

    public Membre() {
    }

    public Membre(String nom, String prenom, Date dateNaissance, String genre, int points, String adresse, String login, String mail, int num, String imageName) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.genre = genre;
        this.points = points;
        this.adresse = adresse;
        this.login = login;
        this.mail = mail;
        this.num = num;
        this.imageName = imageName;
    }

    public Membre(String nom, String prenom, Date dateNaissance, String adresse, String login, String mail, int num) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.login = login;
        this.mail = mail;
        this.num = num;

    }

    public Membre(int id, String nom, String prenom, String login, String password, int num) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.password = password;
        this.num = num;
    }

    public Membre(int id, String nom, String prenom, Date dateNaissance, String genre, String adresse, String type, int points, String login, String password, int num, String imageName) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.genre = genre;
        this.adresse = adresse;
        this.type = type;
        this.num = num;
        this.points = points;
        this.login = login;
        this.password = password;
        this.imageName = imageName;
    }

    public Membre(String nom, String prenom, Date dateNaissance, String genre, String type, String adresse, String login, String password, String mail, int num, String imageName) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.genre = genre;
        this.type = type;
        this.adresse = adresse;
        this.login = login;
        this.password = password;
        this.mail = mail;
        this.num = num;
        this.imageName = imageName;

    }

    public Membre(String nom, String prenom, Date dateNaissance, String genre, String adresse, String type, int points, String login, String password, int num) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.genre = genre;
        this.adresse = adresse;
        this.type = type;
        this.num = num;
        this.points = points;
        this.login = login;
        this.password = password;

    }

    @Override
    public String toString() {
        return this.nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

}
