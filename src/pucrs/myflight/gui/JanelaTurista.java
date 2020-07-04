package pucrs.myflight.gui;

import javafx.stage.*;
import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.GerenciadorPaises;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.*;

public class JanelaTurista {
    private static Stage window;
    private static Scene scene;
    private static ListView<String> listView;

    private static ArrayList<String> temporario = new ArrayList<>();
    private static ArrayList<String> total = new ArrayList<>();

    private static String origem = "";
    private static String segundo = "";
    private static String terceiro = "";
    private static String quarto = "";
    private static String quinto = "";

    public static ArrayList<String> todasRotas(ArrayList<Aeroporto> listaDeAeroportos, GerenciadorMapa gerMapa) {
        GerenciadorPaises gerPaises = GerenciadorPaises.getInstance();
        
        window = new Stage();
        window.setTitle("Mapas com JavaFX");        

        ArrayList<String> codigoDosAeroportos = new ArrayList<>();
        for (Aeroporto aeroporto : listaDeAeroportos) {
            codigoDosAeroportos.add("Codigo do aeroporto: " + aeroporto.getCodigo() + " -  Pais: " + gerPaises.pegaNomeDoPaisPorCodigo(aeroporto.getNome()) + ".");
        }

        listView = new ListView<>();
        listView.getItems().addAll(codigoDosAeroportos);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); 

        Button criarRota = new Button("Criar roteiro");
        Button btnOrigem = new Button("Origem");
        Button btnSegundo = new Button("Segundo");
        Button btnTerceiro = new Button("Terceiro");
        Button btnQuarto = new Button("Quarto");
        Button btnQuinto = new Button("Quinto");        

        btnOrigem.setOnAction( e ->{
            origem = "";
            temporario = addRoute();            
            for (String pal : temporario) {
                origem += pal;
            }
        });

        btnSegundo.setOnAction( e ->{
            segundo = "";
            temporario = addRoute();            
            for (String pal : temporario) {
                segundo += pal;
            }
        });

        btnTerceiro.setOnAction( e ->{
            terceiro = "";
            temporario = addRoute();            
            for (String pal : temporario) {
                terceiro += pal;
            }
        });

        btnQuarto.setOnAction( e ->{
            quarto = "";
            temporario = addRoute();            
            for (String pal : temporario) {
                quarto += pal;
            }
        });

        btnQuinto.setOnAction( e ->{
            quinto = "";
            temporario = addRoute();            
            for (String pal : temporario) {
                quinto += pal;
            }
        });        
        
        criarRota.setOnAction(e -> {
            total = juntarTudo();
            window.close();
        });

        BorderPane pane = new BorderPane();

        HBox grupoDeBotoes = new HBox(10);
        grupoDeBotoes.setPadding(new Insets(10));
        grupoDeBotoes.getChildren().addAll(btnOrigem, btnSegundo, btnTerceiro, btnQuarto , btnQuinto, criarRota);

        pane.setCenter(listView);
        pane.setBottom(grupoDeBotoes);

        scene = new Scene(pane, 437, 280);
        window.setScene(scene);
        window.showAndWait();

        return tirarDuplicados(total);

    }

    public static String getOrigem() {
        if (origem != null) {
            return origem;
        }
        return null;        
    }

    public static String getSegundo() {
        if (segundo != null) {
            return segundo;
        }
        return null; 
    }

    public static String getTerceiro() {
        if (terceiro != null) {
            return terceiro;
        }
        return null; 
    }

    public static String getQuarto() {
        if (quarto != null) {
            return quarto;
        }
        return null; 
    }

    public static String getQuinto() {
        if (quinto != null) {
            return quinto;
        }
        return null; 
    }

    private static ArrayList<String> addRoute() {
        ObservableList<String> rotas;
        rotas = listView.getSelectionModel().getSelectedItems();
        ArrayList<String> selecao = new ArrayList<>(rotas);
        return selecao;
        
    }

    private static ArrayList<String> juntarTudo() {
        ArrayList<String> juntando = new ArrayList<>();
        juntando.add(origem);
        juntando.add(segundo);
        juntando.add(terceiro);
        juntando.add(quarto);
        juntando.add(quinto);

        return juntando;
    }

    private static ArrayList<String> tirarDuplicados(ArrayList<String> comDups) { 
        ArrayList<String> semDups = new ArrayList<String>(); 

        for (String pal : comDups) { 
            if (!semDups.contains(pal)) { 
                String[] x = pal.split("-> |->|Codigo do aeroporto: | |-  Pais: .*? .");
                for (String somenteAeroPorto : x) {
                    semDups.add(somenteAeroPorto);
                }                 
            } 
        } 

        
        return semDups; 
    } 

}





