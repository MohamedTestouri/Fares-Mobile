package tn.esprit.pidev.views;

import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import tn.esprit.pidev.entities.Film;
import tn.esprit.pidev.services.FilmService;
import tn.esprit.pidev.utils.Database;

import java.util.ArrayList;
import java.util.Collections;

public class FilmForm extends Form {
    Form current;
    FilmService filmService = new FilmService();
    ArrayList<Film> filmArrayList = new ArrayList<>();

    public FilmForm() {
        /* *** *CONFIG SCREEN* *** */
        current = this;
        setTitle("Film List");
        setLayout(BoxLayout.y());
        /* *** *YOUR CODE GOES HERE* *** */
        filmArrayList = filmService.showAll();
        Collections.reverse(filmArrayList);
        fillData();
        /* *** *SEARCHBAR* *** */
        getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : getContentPane()) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
                            line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);

                }
                getContentPane().animateLayout(150);
            }
        }, 4);
        /* *** *OVERFLOW MENU* *** */
        getToolbar().addCommandToOverflowMenu("Trier par Titre", null, (evt)->{
            removeAll();
            Collections.sort(filmArrayList, Film.titleComparator);
            fillData();
        });
        getToolbar().addCommandToOverflowMenu("Trier par Realisateur", null, (evt)->{
            removeAll();
            Collections.sort(filmArrayList, Film.realisateurComparator);
            fillData();
        });
        /* *** *SIDE MENU* *** */
        getToolbar().addCommandToLeftSideMenu("", null, (evt) -> {});
        getToolbar().addCommandToLeftSideMenu("Home", FontImage.createMaterial(FontImage.MATERIAL_HOME, UIManager.getInstance().getComponentStyle("TitleCommand")), (evt) -> new HomeScreen().show());
        getToolbar().addCommandToLeftSideMenu("Film", FontImage.createMaterial(FontImage.MATERIAL_MOVIE, UIManager.getInstance().getComponentStyle("TitleCommand")), (evt) -> new FilmForm().show());
        getToolbar().addCommandToLeftSideMenu("Category", FontImage.createMaterial(FontImage.MATERIAL_ARCHIVE, UIManager.getInstance().getComponentStyle("TitleCommand")), (evt) -> new CategoryForm().show());
    }

    public void fillData() {
        for (Film film : filmArrayList) {
            int deviceWidth = Display.getInstance().getDisplayWidth();
            Image placeholder = Image.createImage(deviceWidth / 3, deviceWidth / 4, 0xbfc9d2);
            EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
            Image image = URLImage.createToStorage(encImage, film.getTitre() + film.getId(), Database.UPLOAD_IMAGE + film.getImage()+ ".jpeg", URLImage.RESIZE_SCALE);
            MultiButton multiButton = new MultiButton();
            multiButton.setTextLine1(film.getTitre());
            multiButton.setTextLine2(film.getRealisateur());
            multiButton.setTextLine3(film.getNote()+"Stars");
            multiButton.setIcon(image);
            multiButton.setUIID(film.getId() + "");
            multiButton.setEmblem(FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT, "", 10.0f));
            multiButton.addActionListener(l -> new FilmDetailForm(current, film).show());
            add(multiButton);
        }
    }
}
