package pucrs.myflight.gui;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.collections.ObservableList;
import javafx.geometry.*;

public class ListaDeRotas {

    private static Stage window;
    private static Scene scene;
    private static Button button;
    private static ListView<String> listView;
    private static ArrayList<String> rotas;
    private static GerenciadorConsultas gc = GerenciadorConsultas.getInstance();

public static ArrayList<String> todasRotas(ArrayList<String> total, GerenciadorMapa gerMapa) {
    
    window = new Stage();
    window.setTitle("Mapas com JavaFX");

    // ListView, The default selection mode is only one
    listView = new ListView<>();
    listView.getItems().addAll(total);
    listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // This changes the selection mode from only one to multiple

    
    button = new Button("Selecionar");
    button.setOnAction(e -> {
       rotas = buttonClicked();
       gc.limpar(gerMapa);
       gc.plotarRota(rotas, gerMapa, Color.RED);
    });


    // VBox
    VBox layout = new VBox();
    layout.setPadding(new Insets(20));
    layout.getChildren().addAll(button, listView);

    // Scene
    scene = new Scene(layout, 480, 300);
    window.setScene(scene);
    window.showAndWait();

    return rotas;

}

    private static ArrayList<String> buttonClicked() {
        ObservableList<String> rotas;
        rotas = listView.getSelectionModel().getSelectedItems();
        ArrayList<String> selecao = new ArrayList<>(rotas);
        return selecao;
    }
}





