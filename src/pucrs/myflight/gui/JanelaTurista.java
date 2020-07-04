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

    private static GridPane layout;
    private static Stage window;
    private static Scene scene;
    private static ListView<String> listView;
    private static ArrayList<String> rotas;
    private static GerenciadorConsultas gc = GerenciadorConsultas.getInstance();


    //* Create window with dropdown
    //* have all airports in the dropdown
    //* all selections will be routed withe existing method

    public static ArrayList<String> todasRotas(ArrayList<Aeroporto> listaDeAeroportos, GerenciadorMapa gerMapa) {
        GerenciadorPaises gerPaises = GerenciadorPaises.getInstance();
        window = new Stage();
        window.setTitle("Mapas com JavaFX");

        ArrayList<String> codigoDosAeroportos = new ArrayList<>();
        for (Aeroporto aeroporto : listaDeAeroportos) {
            codigoDosAeroportos.add("Codigo do aeroporto: " + aeroporto.getCodigo() + " -  Pais: " + gerPaises.pegaNomeDoPaisPorCodigo(aeroporto.getNome()) + ".");
        }
        
        //todo fazer todo o resto ---------------------------------------------------------
        

        listView = new ListView<>();
        listView.getItems().addAll(codigoDosAeroportos);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // This changes the selection mode from only one to multiple

        Button criarRota = new Button("Criar roteiro");
        Button btnOrigem = new Button("Origem");
        Button btnSegundo = new Button("Segundo");
        Button btnTerceiro = new Button("Terceiro");
        Button btnQuarto = new Button("Quarto");
        Button btnQuinto = new Button("Quinto");


        btnOrigem.setOnAction(e -> {
            rotas = buttonClicked();
            gc.limpar(gerMapa);
            //TODO Tratar a consulta 5 rotas
            System.out.println(rotas);
            //gc.plotarRota(rotas, gerMapa, Color.magenta);
        });

        
        GridPane bottomLeft = new GridPane();
        GridPane bottomRight = new GridPane();
        BorderPane pane = new BorderPane();
        BorderPane bottom = new BorderPane();


        // bottomLeft.setAlignment(Pos.BOTTOM_LEFT);
        // bottomLeft.setHgap(10);
        // bottomLeft.setVgap(10);
        // bottomLeft.setPadding(new Insets(10, 10, 10, 10));


        // bottomRight.setAlignment(Pos.BOTTOM_RIGHT);
        // bottomRight.setHgap(10);
        // bottomRight.setVgap(10);
        // bottomRight.setPadding(new Insets(10, 10, 10, 10));
        

        // bottomLeft.add(btnOrigem, 0, 0);
        // bottomRight.add(criarRota, 0, 0);  
        
        
        // bottom.getChildren().addAll(bottomLeft, bottomRight);
        // pane.getChildren().addAll(btnOrigem, criarRota);

        HBox grupoDeBotoes = new HBox(10);
        grupoDeBotoes.setPadding(new Insets(10));
        grupoDeBotoes.getChildren().addAll(btnOrigem, btnSegundo, btnTerceiro, btnQuarto , btnQuinto, criarRota);

        pane.setCenter(listView);
        pane.setBottom(grupoDeBotoes);

        // Scene
        scene = new Scene(pane, 437, 280);
        window.setScene(scene);
        window.showAndWait();

        return rotas;

    }

    private static ArrayList<String> buttonClicked() {
        ObservableList<String> rotas;
        rotas = listView.getSelectionModel().getSelectedItems();
        System.out.println(rotas);
        ArrayList<String> selecao = new ArrayList<>(rotas);
        return selecao;
    }
}





