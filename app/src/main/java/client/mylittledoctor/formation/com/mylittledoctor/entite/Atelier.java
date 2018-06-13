package client.mylittledoctor.formation.com.mylittledoctor.entite;

import com.arlebois.formation.mylittledoctor.entite.Topo;

import java.util.List;

public class Atelier {


    private Long id;
    private String titre;

    private String dateDebut;
    private String dateFin;
    private String description;
    private String lieu;


    private Topo topo;


    private Utilisateur createur;
    private Utilisateur formateur;


    private List<Geste> geste;

    protected Atelier() {

    }

    public Atelier(String titre, String dateDebut, String dateFin, String description, String lieu,
                   Utilisateur createur) {
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description = description;
        this.lieu = lieu;
        this.createur = createur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Topo getTopo() {
        return topo;
    }

    public void setTopo(Topo topo) {
        this.topo = topo;
    }

    public Utilisateur getCreateur() {
        return createur;
    }

    public void setCreateur(Utilisateur createur) {
        this.createur = createur;
    }

    public List<Geste> getGeste() {
        return geste;
    }

    public void setGeste(List<Geste> geste) {
        this.geste = geste;
    }


    public Utilisateur getFormateur() {
        return formateur;
    }

    public void setFormateur(Utilisateur formateur) {
        this.formateur = formateur;
    }


}
