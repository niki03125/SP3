package src;

import java.util.ArrayList;

public class Search {
    public Media searchByTitle(ArrayList<Media> medias){
        String input = TextUI.promptText("Search: ");
        Media currentMedia = null;

        for (int i = 0; i < medias.size(); i++) {
            if (medias.get(i).getMediaName().equalsIgnoreCase(input)) {
                currentMedia = medias.get((i));
                return currentMedia;
            }
        }
        TextUI.displayMSG("Media not found");
        return searchByTitle(medias);
    }
}
