package tn.esprit.pidev.views;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.messaging.Message;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import tn.esprit.pidev.entities.Film;
import tn.esprit.pidev.utils.Database;

import java.util.ArrayList;

public class FilmDetailForm extends Form {
    Form current;
    ArrayList<Film> filmArrayList = new ArrayList<>();

    public FilmDetailForm(Form previous, Film film) {
        /* *** *CONFIG SCREEN* *** */
        current = this;
        setTitle("Film Details");
        setLayout(BoxLayout.y());
        /* *** *YOUR CODE GOES HERE* *** */
        /* *THIS CODE USED TO DISPLAY IMAGE* */
        int deviceWidth = Display.getInstance().getDisplayWidth();
        Image placeholder = Image.createImage(deviceWidth, deviceWidth, 0xbfc9d2);
        EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
        Image image = URLImage.createToStorage(encImage, film.getTitre() + film.getId(), Database.UPLOAD_IMAGE + film.getImage() + ".jpeg", URLImage.RESIZE_SCALE);
        ImageViewer imageViewer = new ImageViewer();
        imageViewer.setImage(image.fill(deviceWidth, deviceWidth));
        Label titleLabel = new Label(film.getTitre());
        Label realisateurLabel = new Label("Réalisateur: " + film.getRealisateur());
        Label dateLabel = new Label("Date de sortie: " + film.getDateSortie());
        Label noteLabel = new Label("Note: " + film.getNote() + " Stars");
        Label dureeLabel = new Label("Durée: " + film.getDuree() + " mins");
        SpanLabel descriptionSpanLabel = new SpanLabel("Description: " + film.getDescription());
              Button showButton = new Button("Watch this movie!");
        addAll(imageViewer, titleLabel, dureeLabel, realisateurLabel, noteLabel, dateLabel, descriptionSpanLabel, showButton); // DO NOT FORGET THEM HERE TOO
        /* *** *BACK BUTTON* *** */
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        /* *** *OVERFLOW MENU* *** */
        getToolbar().addCommandToOverflowMenu("Share", FontImage.createMaterial(FontImage.MATERIAL_SHARE, UIManager.getInstance().getComponentStyle("TitleCommand")), (evt) -> {
            //SENDING EMAIL
            Display.getInstance().sendMessage(new String[]{""}, "Let's watch this!", new Message("Check out this show: " + film.getTitre() + " it's: " + film.getNote() + " stars"));
        });
    }
}
