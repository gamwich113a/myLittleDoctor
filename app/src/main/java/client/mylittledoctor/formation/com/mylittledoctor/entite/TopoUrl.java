package client.mylittledoctor.formation.com.mylittledoctor.entite;

import com.arlebois.formation.mylittledoctor.entite.Topo;

public class TopoUrl {

    private Long id;
    private String titre;
    private String url;
    private Integer type;
    private com.arlebois.formation.mylittledoctor.entite.Topo topo;

    protected TopoUrl() {

    }

    public TopoUrl(String titre, String url, com.arlebois.formation.mylittledoctor.entite.Topo topo) {
	this.titre = titre;
	this.url = url;
	this.topo = topo;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Topo getTopo() {
        return topo;
    }

    public void setTopo(Topo topo) {
        this.topo = topo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
