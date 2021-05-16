package tn.esprit.pidev.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import tn.esprit.pidev.entities.Film;
import tn.esprit.pidev.utils.Database;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilmService {
    public ArrayList<Film> filmArrayList;
    public static FilmService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public FilmService() {
        req = new ConnectionRequest();
    }

    public static FilmService getInstance() {
        if (instance == null) {
            instance = new FilmService();
        }
        return instance;
    }

    public ArrayList<Film> parseFilm(String jsonText) { //Parsing Issues with id and date type
        try {
            filmArrayList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> filmListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) filmListJson.get("root");
            for (Map<String, Object> obj : list) {
                Film film = new Film();
                film.setId((int) Float.parseFloat(obj.get("id").toString()));
                film.setTitre(obj.get("titre").toString());
                film.setDescription(obj.get("description").toString());
                film.setDuree(obj.get("duree").toString().substring(11,18));
                film.setDateSortie(obj.get("datesortie").toString().substring(0,10));
                film.setNote(Double.parseDouble(obj.get("note").toString()));
                film.setRealisateur(obj.get("realisepar").toString());
                film.setImage(obj.get("image").toString());
                filmArrayList.add(film);
            }
        } catch (IOException ex) {
        }
        return filmArrayList;
    }

    public ArrayList<Film> showAll() {
        String url = Database.BASE_URL + "film/api/show"; // Add Symfony URL Here
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                filmArrayList = parseFilm(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return filmArrayList;
    }

    public ArrayList<Film> getByIdCategory(int idCategory) {
        String url = Database.BASE_URL + "film/api/show/category?idCategory=" + idCategory; // Add Symfony URL Here
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                filmArrayList = parseFilm(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return filmArrayList;
    }
}
