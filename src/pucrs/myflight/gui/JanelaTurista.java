package pucrs.myflight.gui;

import javafx.stage.*;
import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.GerenciadorAeroportos;
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

    private static ArrayList<String> semDupsorario = new ArrayList<>();
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
            codigoDosAeroportos.add("Codigo do aeroporto: " + aeroporto.getCodigo() + " - Pais: " + gerPaises.pegaNomeDoPaisPorCodigo(aeroporto.getNome()) + ".");
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
            semDupsorario = addRoute();            
            for (String pal : semDupsorario) {
                origem += pal;
            }
        });

        btnSegundo.setOnAction( e ->{
            segundo = "";
            semDupsorario = addRoute();            
            for (String pal : semDupsorario) {
                segundo += pal;
            }
        });

        btnTerceiro.setOnAction( e ->{
            terceiro = "";
            semDupsorario = addRoute();            
            for (String pal : semDupsorario) {
                terceiro += pal;
            }
        });

        btnQuarto.setOnAction( e ->{
            quarto = "";
            semDupsorario = addRoute();            
            for (String pal : semDupsorario) {
                quarto += pal;
            }
        });

        btnQuinto.setOnAction( e ->{
            quinto = "";
            semDupsorario = addRoute();            
            for (String pal : semDupsorario) {
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


    //("Codigo do aeroporto: " + aeroporto.getCodigo() + " -  Pais: " + gerPaises.pegaNomeDoPaisPorCodigo(aeroporto.getNome()) + ".");

    private static ArrayList<String> tirarDuplicados(ArrayList<String> comDups) {
        ArrayList<String> temp = new ArrayList<>();
        
        for (String s : comDups) {
            String[] aeros = s.split("Codigo do aeroporto: | - .*?\\.");
            for (String sAero : aeros) {
                if (sAero != (" |") || sAero != null) {
                    temp.add(sAero);                    
                }
            }
        }
        
        ArrayList<String> semDups = new ArrayList<>();

        GerenciadorAeroportos gerAero = GerenciadorAeroportos.getInstance();
        for (String string : temp) {
            if (gerAero.buscarCodigo(string) != null) {
                semDups.add(string);
            }
        }        
        
        return semDups;
    }

}





