package client.mylittledoctor.formation.com.mylittledoctor.entite;

import java.util.Date;


public class Utilisateur {

    private Long id;
    private String nom;
    private String prenom;
    private String mdp;
    private String email;
    private Date dateNaissance;
    private int sexe;
    private int role;
    private Cursus cursus;
    private Atelier atelierEnCours;

    protected Utilisateur() {

    }

    public Utilisateur(String nom, String prenom, String mdp, String email, Date dateNaissance, int sexe, Cursus cursus,
                       int role) {
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.cursus = cursus;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public int getSexe() {
        return sexe;
    }

    public void setSexe(int sexe) {
        this.sexe = sexe;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Cursus getCursus() {
        return cursus;
    }

    public void setCursus(Cursus cursus) {
        this.cursus = cursus;
    }

    public Atelier getAtelierEnCours() {
        return atelierEnCours;
    }

    public void setAtelierEnCours(Atelier atelierEnCours) {
        this.atelierEnCours = atelierEnCours;
    }
}
