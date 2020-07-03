package pucrs.myflight.gui;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.util.HashSet;

import javafx.collections.ObservableList;
import javafx.geometry.*;

public class ListaDeRotas {

    static double resposta;

    private static Stage window;
    private static Scene scene;
    private static Button button;
    private static ListView<String> listView;
    private static HashSet<String> rotas;


public static HashSet<String> todasRotas() {
    window = new Stage();
    window.setTitle("Favorite Marvel Movies");

    button = new Button("Send");
    button.setOnAction(e -> buttonClicked());

    // ListView, The default selection mode is only one
    listView = new ListView<>();
    listView.getItems().addAll("Iron Man", "The Incredible Hulk", "Iron Man 2", "Thor", 
    "Captain America", "Avengers", "Iron Man 3", "Thor 2", "Captain America 2", 
    "Guardians of the Galaxy", "Avengers 2", "Ant-Man", "Captain America 3", "Doctor Strange",
    "Guardians of the Galaxy 2", "Spider-Man: Homecoming", "Thor 3", "Black Panther", "Avengers 3",
    "Ant-Man and the Wasp", "Captain Marvel", "Avengers: Endgame", "Spider-Man: Far From Home");
    listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // This changes the selection mode from only one to multiple

    // VBox
    VBox layout = new VBox();
    layout.setPadding(new Insets(20));
    layout.getChildren().addAll(button, listView);

    // Scene
    scene = new Scene(layout, 300, 300);
    window.setScene(scene);
    window.show();

    return rotas;

}

private static void buttonClicked() {
    String message = "";
    ObservableList<String> movies;
    movies = listView.getSelectionModel().getSelectedItems();

    for (String movie : movies) {
        message += movie + "\n";
    }

    System.out.println(message);
}
}





