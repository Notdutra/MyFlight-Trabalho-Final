package pucrs.myflight.gui;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.HashSet;

import javafx.collections.ObservableList;
import javafx.geometry.*;

public class ListaDeRotas {

    static double resposta;

    private static Stage window;
    private static Scene scene;
    private static Button button;
    private static ListView<String> listView;
    private static ArrayList<String> rotas;


public static ArrayList<String> todasRotas(ArrayList<String> total) {
    window = new Stage();
    window.setTitle("Mapas com JavaFX");

    // ListView, The default selection mode is only one
    listView = new ListView<>();
    listView.getItems().addAll(total);
    listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // This changes the selection mode from only one to multiple

    
    button = new Button("Selecionar");
    button.setOnAction(e -> buttonClicked());


    // VBox
    VBox layout = new VBox();
    layout.setPadding(new Insets(20));
    layout.getChildren().addAll(button, listView);

    // Scene
    scene = new Scene(layout, 300, 300);
    window.setScene(scene);
    window.showAndWait();

    return rotas;

}

private static void buttonClicked() {
    String message = "";
    ObservableList<String> movies;
    movies = listView.getSelectionModel().getSelectedItems();

    for (String movie : movies) {
        message += movie + ";";
    }

    System.out.println(message);

    System.out.println("Deu?");

}
}





