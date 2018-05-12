package com.arlebois.formation.mylittledoctor.entite;

import java.util.List;

import client.mylittledoctor.formation.com.mylittledoctor.entite.Atelier;
import client.mylittledoctor.formation.com.mylittledoctor.entite.TopoUrl;
import client.mylittledoctor.formation.com.mylittledoctor.entite.Utilisateur;


public class Topo {

    private Long id;
    private String titre;
    private String description;
    private String dateSaisie;
    private String dateUpdate;
    private Integer etape;
    private Utilisateur creator;
    private Atelier atelier;
    private List<TopoUrl> topoUrlList;

    protected Topo() {

    }

    public Topo(String titre, String description, String dateSaisie, String dateUpdate, Utilisateur creator) {
        this.titre = titre;
        this.description = description;
        this.dateSaisie = dateSaisie;
        this.dateUpdate = dateUpdate;
        this.creator = creator;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateSaisie() {
        return dateSaisie;
    }

    public void setDateSaisie(String dateSaisie) {
        this.dateSaisie = dateSaisie;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Integer getEtape() {
        return etape;
    }

    public void setEtape(Integer etape) {
        this.etape = etape;
    }

    public Utilisateur getCreator() {
        return creator;
    }

    public void setCreator(Utilisateur creator) {
        this.creator = creator;
    }

    public Atelier getAtelier() {
        return atelier;
    }

    public void setAtelier(Atelier atelier) {
        this.atelier = atelier;
    }

    public List<TopoUrl> getTopoUrlList() {
        return topoUrlList;
    }

    public void setTopoUrlList(List<TopoUrl> topoUrlList) {
        this.topoUrlList = topoUrlList;
    }
}
