package tn.esprit.pidev.views;

import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import tn.esprit.pidev.entities.Category;
import tn.esprit.pidev.entities.Film;
import tn.esprit.pidev.services.CategoryService;
import tn.esprit.pidev.services.FilmService;
import tn.esprit.pidev.utils.Database;

import java.util.ArrayList;
import java.util.Collections;


public class CategoryForm extends Form {
    Form current;
    CategoryService categoryService = new CategoryService();
    ArrayList<Category> categoryArrayList = new ArrayList<>();

    public CategoryForm() {
        /* *** *CONFIG SCREEN* *** */
        current = this;
        setTitle("Category List");
        setLayout(BoxLayout.y());
        /* *** *YOUR CODE GOES HERE* *** */
        categoryArrayList = categoryService.showAll();
        //Collections.reverse(categoryArrayList);
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
        getToolbar().addCommandToOverflowMenu("Trier par Nom", null, (evt) -> {
            removeAll();
            Collections.sort(categoryArrayList, Category.nameComparator);
            fillData();
        });
        /* *** *SIDE MENU* *** */
        getToolbar().addCommandToLeftSideMenu("", null, (evt) -> {
        });
        getToolbar().addCommandToLeftSideMenu("Home", FontImage.createMaterial(FontImage.MATERIAL_HOME, UIManager.getInstance().getComponentStyle("TitleCommand")), (evt) -> new HomeScreen().show());
        getToolbar().addCommandToLeftSideMenu("Film", FontImage.createMaterial(FontImage.MATERIAL_MOVIE, UIManager.getInstance().getComponentStyle("TitleCommand")), (evt) -> new FilmForm().show());
        getToolbar().addCommandToLeftSideMenu("Category", FontImage.createMaterial(FontImage.MATERIAL_ARCHIVE, UIManager.getInstance().getComponentStyle("TitleCommand")), (evt) -> new CategoryForm().show());

    }

    private void fillData() {
        for (Category category : categoryArrayList) {
            MultiButton multiButton = new MultiButton("#" + category.getId() + "    " + category.getName());
            //multiButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ARCHIVE, UIManager.getInstance().getComponentStyle("TitleCommand")));
            multiButton.setUIID(category.getId() + "");
            multiButton.setEmblem(FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT, "", 10.0f));
            multiButton.addActionListener(l -> new CategoryDetailForm(current, category).show());
            add(multiButton);
        }
    }
}
