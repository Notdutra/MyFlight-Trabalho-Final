package pucrs.myflight.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.SwingUtilities;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
// import pucrs.myflight.modelo.Aeroporto;
// import pucrs.myflight.modelo.CiaAerea;
// import pucrs.myflight.modelo.Geo;
// import pucrs.myflight.modelo.GerenciadorAeronaves;
// import pucrs.myflight.modelo.GerenciadorAeroportos;
// import pucrs.myflight.modelo.GerenciadorCias;
// import pucrs.myflight.modelo.GerenciadorRotas;
// import pucrs.myflight.modelo.Rota;
import pucrs.myflight.modelo.*;

public class JanelaFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    final SwingNode mapkit = new SwingNode();

    private GerenciadorConsultas gerCons;
    private GerenciadorCias gerCias;
    private GerenciadorAeronaves gerAvioes;
    private GerenciadorAeroportos gerAero;
    private GerenciadorRotas gerRotas;

    private GerenciadorMapa gerenciador;

    private EventosMouse mouse;

    private ComboBox<CiaAerea> comboCia;
    private ComboBox<Aeroporto> comboAero1;
    private ComboBox<Aeroporto> comboAero2;
    private ComboBox<Double> comboHoras;
    Stage janela = new Stage();

    public void start(Stage primaryStage) throws Exception {
        janela = primaryStage;
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        setup();

        GeoPosition poa = new GeoPosition(-30.05, -51.18);
        gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
        mouse = new EventosMouse();
        gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
        gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);

        createSwingContent(mapkit);

        BorderPane pane = new BorderPane();
        GridPane leftPane = new GridPane();

        leftPane.setAlignment(Pos.CENTER);
        leftPane.setHgap(10);
        leftPane.setVgap(10);
        leftPane.setPadding(new Insets(10, 10, 10, 10));

        Button btnLimpar = new Button("Limpar");
        Button btnConsulta1 = new Button("Consulta 1");
        Button btnConsulta2 = new Button("Consulta 2");
        Button btnConsulta3 = new Button("Consulta 3");
        Button btnConsulta4 = new Button("Consulta 4");

        ObservableList<CiaAerea> olCiaAerea = FXCollections.observableArrayList(gerCias.listarTodas());
        comboCia = new ComboBox<>(olCiaAerea);
        ObservableList<Aeroporto> olAero1 = FXCollections.observableArrayList(gerAero.listarTodos());
        comboAero1 = new ComboBox<>(olAero1);
        ObservableList<Aeroporto> olAero2 = FXCollections.observableArrayList(gerAero.listarTodos());
        comboAero2 = new ComboBox<>(olAero2);

        comboHoras = new ComboBox<>();
        comboHoras.setEditable(true);

        leftPane.add(btnLimpar, 0, 0);
        leftPane.add(btnConsulta1, 1, 0);
        leftPane.add(btnConsulta2, 2, 0);
        leftPane.add(btnConsulta3, 3, 0);
        leftPane.add(btnConsulta4, 4, 0);
        leftPane.add(comboCia, 5, 0);
        leftPane.add(comboAero1, 6, 0);
        leftPane.add(comboAero2, 7, 0);

        btnLimpar.setOnAction(e -> {
            limpar();
            clicado = null;
        });

        btnConsulta1.setOnAction(e -> {
            if (comboCia.getValue() != null) {
                ArrayList<Rota> rotasDaCia = gerRotas.getRotasPorCia(comboCia.getValue().getCodigo());
                gerCons.plotarAeroPorCia(gerenciador, rotasDaCia);
            }
        });

        btnConsulta2.setOnAction(e -> {
            HashMap<String, Integer> traffic = gerRotas.getAirTraffic();
            gerCons.setTraffic(gerenciador, gerAero, traffic);
        });


        btnConsulta3.setOnAction(e -> {
            if (comboAero1.getValue() != null && comboAero2.getValue() != null) {
                gerCons.consulta3(comboAero1.getValue().getCodigo(), comboAero2.getValue().getCodigo(),gerenciador);
            }
            
        });


        btnConsulta4.setOnAction(e -> {           
            if (clicado != null) {         
                double tempoMax = BotarTempo.pegaHorario();       
                gerenciador.clear();
                HashSet<String> resultado = gerRotas.consulta4Arthur(tempoMax,clicado);         
                gerCons.plotarNoMapa(gerenciador, gerAero, resultado);
            }            
        });

        pane.setCenter(mapkit);
        pane.setTop(leftPane);

        Scene scene = new Scene(pane, 1000, 1000);
        janela.setScene(scene);
        janela.setTitle("Mapas com JavaFX");
        janela.show();

    }

    private void closeProgram() {
        Boolean resposta = ComfirmBox.display("Mapas com JavaFX", "Voce quer sair?");
        if (resposta == true) {
            janela.close();
        }
    }


    // Inicializando os dados aqui...
    private void setup() {

        gerCons = GerenciadorConsultas.getInstance();
        // Load the airlines to memory
        gerCias = GerenciadorCias.getInstance();
        gerCias.carregaDados("airlines.dat");
        // Load the aircrafts to memory
        gerAvioes = GerenciadorAeronaves.getInstance();
        gerAvioes.carregaDados("equipment.dat");
        // Load the airports to memory
        gerAero = GerenciadorAeroportos.getInstance();
        gerAero.carregaDados("airports.dat");
        // Load the routes to memory
        gerRotas = GerenciadorRotas.getInstance();
        gerRotas.carregaDados("routes.dat");

    }

    private void limpar() {
        gerCons.limpar(gerenciador);
    }

    private static Aeroporto clicado;

    private class EventosMouse extends MouseAdapter {
        private int lastButton = -1;

        @Override
        public void mousePressed(MouseEvent e) {
            JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
            GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
            lastButton = e.getButton();
            if (lastButton == MouseEvent.BUTTON3) {
                gerenciador.setPosicao(loc);
                gerenciador.getMapKit().repaint();
                GeoPosition pos = gerenciador.getPosicao();
                clicado = gerCons.getAirportFromCoord(pos);

                // inves de printar o 'clicado' ele tem q mostar o aeroporto no mapa
                if (clicado != null) {
                    System.out.println(clicado);
                    gerCons.mostarEsseAeroporto(gerenciador, clicado);
                }

                // System.out.println("-------" + pos);// pra pegar aeroporto fazer metodo q
                // pega pos e encontra um aeroporto perto arredondando a coordenada
            }

            if (lastButton == MouseEvent.BUTTON1) {
                gerenciador.setPosicao(loc);
                gerenciador.getMapKit().repaint();
                GeoPosition pos = gerenciador.getPosicao();

                double longitude = pos.getLongitude();
                double latitude = pos.getLatitude();
                Geo posDaLinha = new Geo(latitude, longitude);
                if (gerAero.getAirportFromGPS(posDaLinha) != null) {
                    gerenciador.consulta4Marcar(posDaLinha);
                }

            }
        }
    }

    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                swingNode.setContent(gerenciador.getMapKit());
            }
        });
    }

}
