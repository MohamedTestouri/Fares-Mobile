package tn.esprit.pidev.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import tn.esprit.pidev.entities.Category;
import tn.esprit.pidev.entities.Film;
import tn.esprit.pidev.utils.Database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryService {
    public ArrayList<Category> categoryArrayList;
    public static CategoryService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public CategoryService() {
        req = new ConnectionRequest();
    }

    public static CategoryService getInstance() {
        if (instance == null) {
            instance = new CategoryService();
        }
        return instance;
    }

    public ArrayList<Category> parseCategory(String jsonText){ //Parsing Issues with id and date type
        try {
            categoryArrayList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> categoryListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>)categoryListJson.get("root");
            for(Map<String,Object> obj : list){
                Category category = new Category();
                category.setId((int) Float.parseFloat(obj.get("id").toString()));
                category.setName(obj.get("type").toString());
                categoryArrayList.add(category);
            }
        } catch (IOException ex) {
        }
        return categoryArrayList;
    }
    public ArrayList<Category> showAll(){
        String url = Database.BASE_URL+"categorie/api/show"; // Add Symfony URL Here
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                categoryArrayList = parseCategory(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return categoryArrayList;
    }
}
