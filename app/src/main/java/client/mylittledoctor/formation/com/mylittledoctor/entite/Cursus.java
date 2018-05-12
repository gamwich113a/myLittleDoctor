package client.mylittledoctor.formation.com.mylittledoctor.entite;

public class Cursus {

    // il faudrait en fait cree les cursus par l'admin et que les users selectionne
    // leur cursus !!

    private long id;
    private String ecole;
    private String specialite;
    private Integer anneeDes;

    protected Cursus() {

    }

    public Cursus(String ecole, String specialite, Integer anneeDes) {
        this.ecole = ecole;
        this.specialite = specialite;
        this.anneeDes = anneeDes;
    }

}
