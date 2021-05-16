package tn.esprit.pidev.entities;

import java.util.Comparator;
import java.util.Date;

public class Film {
   private int id;
   private int idCategorie;
   private String titre;
   private String description;
   private String duree;
   private String dateSortie;
   private Double note;
   private String realisateur;
   private String image;

    public Film() {
    }

    public Film(int idCategorie, String titre, String description, String duree, String dateSortie, Double note, String realisateur, String image) {
        this.idCategorie = idCategorie;
        this.titre = titre;
        this.description = description;
        this.duree = duree;
        this.dateSortie = dateSortie;
        this.note = note;
        this.realisateur = realisateur;
        this.image = image;
    }

    public Film(int id, int idCategorie, String titre, String description, String duree, String dateSortie, Double note, String realisateur, String image) {
        this.id = id;
        this.idCategorie = idCategorie;
        this.titre = titre;
        this.description = description;
        this.duree = duree;
        this.dateSortie = dateSortie;
        this.note = note;
        this.realisateur = realisateur;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
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

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(String dateSortie) {
        this.dateSortie = dateSortie;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Comparator<Film> titleComparator = new Comparator<Film>() {
        @Override
        public int compare(Film o1, Film o2) {
            return (int) (o1.getTitre().toLowerCase().compareTo(o2.getTitre().toLowerCase()));
        }
    };
    public static Comparator<Film> realisateurComparator = new Comparator<Film>() {
        @Override
        public int compare(Film o1, Film o2) {
            return (int) (o1.getRealisateur().toLowerCase().compareTo(o2.getRealisateur().toLowerCase()));
        }
    };
}
