package client.mylittledoctor.formation.com.mylittledoctor.entite;


public class Geste {


    private Long id;
    private String description;
    private Atelier atelier;

    protected Geste() {

    }

    public Geste(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Atelier getAtelier() {
        return atelier;
    }

    public void setAtelier(Atelier atelier) {
        this.atelier = atelier;
    }
}
