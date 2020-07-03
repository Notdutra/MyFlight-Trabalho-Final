package pucrs.myflight.gui;

import javafx.stage.*;
import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.GerenciadorPaises;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.collections.ObservableList;
import javafx.geometry.*;

public class JanelaTurista {

    private static Stage window;
    private static Scene scene;
    private static Button button;
    private static ListView<String> listView;
    private static ArrayList<String> rotas;
    private static GerenciadorConsultas gc = GerenciadorConsultas.getInstance();


    //* Create window with dropdown
    //* have all airports in the dropdown
    //* all selections will be routed withe existing method

    public static void todasRotas(ArrayList<Aeroporto> listaDeAeroportos, GerenciadorMapa gerMapa) {
        GerenciadorPaises gerPaises = GerenciadorPaises.getInstance();
        window = new Stage();
        window.setTitle("Mapas com JavaFX");

        ArrayList<String> codigoDosAeroportos = new ArrayList<>();
        for (Aeroporto aeroporto : listaDeAeroportos) {
            codigoDosAeroportos.add("Codigo do aeroporto: " + aeroporto.getCodigo() + " -  Pais: " + gerPaises.pegaNomeDoPaisPorCodigo(aeroporto.getNome()));
        }
        
        //todo fazer todo o resto ---------------------------------------------------------
        

        listView = new ListView<>();
        listView.getItems().addAll(codigoDosAeroportos);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // This changes the selection mode from only one to multiple

        
        button = new Button("Selecionar");
        button.setOnAction(e -> {
        rotas = buttonClicked();
        gc.limpar(gerMapa);
        gc.plotarRota(rotas, gerMapa, Color.magenta);
        });


        // VBox
        VBox layout = new VBox();
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(button, listView);

        // Scene
        scene = new Scene(layout, 480, 300);
        window.setScene(scene);
        window.showAndWait();

    }

    private static ArrayList<String> buttonClicked() {
        ObservableList<String> rotas;
        rotas = listView.getSelectionModel().getSelectedItems();
        ArrayList<String> selecao = new ArrayList<>(rotas);
        return selecao;
    }
}





