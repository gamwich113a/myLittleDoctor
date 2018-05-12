package client.mylittledoctor.formation.com.mylittledoctor.entite;

public class Evaluation {

    private Long id;
    private String dateSaisie;
    private int note;
    private String commentaire;
    private int nombrePratiqueAn;
    private Utilisateur createur;
    private Atelier atelier;
    private Geste geste;
    private Utilisateur formateur;

    protected Evaluation() {

    }

    public Evaluation(String dateSaisie, int note, String commentaire, Utilisateur createur, Atelier atelier) {
        this.dateSaisie = dateSaisie;
        this.note = note;
        this.commentaire = commentaire;
        this.createur = createur;
        this.atelier = atelier;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateSaisie() {
        return dateSaisie;
    }

    public void setDateSaisie(String dateSaisie) {
        this.dateSaisie = dateSaisie;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getNombrePratiqueAn() {
        return nombrePratiqueAn;
    }

    public void setNombrePratiqueAn(int nombrePratiqueAn) {
        this.nombrePratiqueAn = nombrePratiqueAn;
    }

    public Utilisateur getCreateur() {
        return createur;
    }

    public void setCreateur(Utilisateur createur) {
        this.createur = createur;
    }

    public Atelier getAtelier() {
        return atelier;
    }

    public void setAtelier(Atelier atelier) {
        this.atelier = atelier;
    }

    public Geste getGeste() {
        return geste;
    }

    public void setGeste(Geste geste) {
        this.geste = geste;
    }

    public Utilisateur getFormateur() {
        return formateur;
    }

    public void setFormateur(Utilisateur formateur) {
        this.formateur = formateur;
    }
}
