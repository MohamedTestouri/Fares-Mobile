package tn.esprit.pidev.views;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import tn.esprit.pidev.entities.Category;
import tn.esprit.pidev.entities.Film;
import tn.esprit.pidev.services.CategoryService;
import tn.esprit.pidev.services.FilmService;
import tn.esprit.pidev.utils.Database;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class CategoryDetailForm extends Form {
    Form current;
    CategoryService categoryService = new CategoryService();
    FilmService filmService = new FilmService();
    ArrayList<Film> filmArrayList = new ArrayList<>();

    public CategoryDetailForm(Form previous, Category category) {
        /* *** *CONFIG SCREEN* *** */
        current = this;
        setTitle("Category List");
        setLayout(BoxLayout.y());
        setScrollableY(false);
        /* *** *YOUR CODE GOES HERE* *** */
        /* *SHOW FORMATION DETAILS* */
        /* CREATING ITEMS DETAILS */
        Label titleLabel = new Label("#" + category.getId() + "     " + category.getName());
        Container formationDetailsContainer = BoxLayout.encloseY();
        formationDetailsContainer.add(titleLabel);
        formationDetailsContainer.setScrollableY(false);

        /* *SHOW COURSES LIST* */
        filmArrayList = filmService.getByIdCategory(category.getId());
        Collections.reverse(filmArrayList);
        Container scrollableListContainer = BoxLayout.encloseY();
        fillData(scrollableListContainer);
        scrollableListContainer.setScrollableY(true);
        scrollableListContainer.getStyle().setMarginTop(16);
        /* ADDING ALL ITEMS */
        addAll(formationDetailsContainer, scrollableListContainer);
        /* *** *OVERFLOW MENU* *** */
        getToolbar().addCommandToOverflowMenu("Trier par Titre", null, (evt) -> {
            scrollableListContainer.removeAll();
            Collections.sort(filmArrayList, Film.titleComparator);
            fillData(scrollableListContainer);
        });
        getToolbar().addCommandToOverflowMenu("Trier par Realisateur", null, (evt) -> {
            scrollableListContainer.removeAll();
            Collections.sort(filmArrayList, Film.realisateurComparator);
            fillData(scrollableListContainer);
        });
        /* *** *BACK BUTTON* *** */
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        /* *** *SEARCHBAR* *** */
        getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : scrollableListContainer) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                scrollableListContainer.animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : scrollableListContainer) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
                            line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);

                }
                scrollableListContainer.animateLayout(150);
            }
        }, 4);
    }

    private void fillData(Container scrollableListContainer) {
        if (!filmArrayList.isEmpty()){
        for (Film film : filmArrayList) {
            int deviceWidth = Display.getInstance().getDisplayWidth();
            Image placeholder = Image.createImage(deviceWidth / 3, deviceWidth / 4, 0xbfc9d2);
            EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
            Image image = URLImage.createToStorage(encImage, film.getTitre() + film.getId(), Database.UPLOAD_IMAGE + film.getImage()+ ".jpeg", URLImage.RESIZE_SCALE);
            MultiButton multiButton = new MultiButton();
            multiButton.setTextLine1(film.getTitre());
            multiButton.setTextLine2(film.getRealisateur());
            multiButton.setTextLine3(film.getNote().toString());
            multiButton.setIcon(image);
            multiButton.setUIID(film.getId() + "");
            multiButton.setEmblem(FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT, "", 10.0f));
            multiButton.addActionListener(l -> new FilmDetailForm(current, film).show());
            scrollableListContainer.add(multiButton);
        } }else {
            scrollableListContainer.add(new Label("There is no movie in this category!"));
        }
    }
}
