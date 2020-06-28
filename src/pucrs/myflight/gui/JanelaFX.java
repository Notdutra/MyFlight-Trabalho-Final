package pucrs.myflight.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

	final SwingNode mapkit = new SwingNode();

	private GerenciadorConsultas gerCons;
	private GerenciadorCias gerCias;
	private GerenciadorAeronaves gerAvioes;
	private GerenciadorAeroportos gerAero;
	private GerenciadorRotas gerRotas;

	private GerenciadorMapa gerenciador;

	private EventosMouse mouse;

	private ObservableList<CiaAerea> comboCiasData;
	private ComboBox<CiaAerea> comboCia;

	@Override
	public void start(Stage primaryStage) throws Exception {

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

		Button btnConsultaExemplo = new Button("Consulta Exemplo");
		Button btnConsulta1 = new Button("Consulta 1");
		Button btnConsulta2 = new Button("Consulta 2");
		Button btnConsulta3 = new Button("Consulta 3");
		Button btnConsulta4 = new Button("Consulta 4");


		leftPane.add(btnConsultaExemplo, 0, 0);
		leftPane.add(btnConsulta1, 1, 0);
		leftPane.add(btnConsulta2, 2, 0);
		leftPane.add(btnConsulta3, 3, 0);
		leftPane.add(btnConsulta4, 4, 0);

		btnConsultaExemplo.setOnAction(e -> {
			consultaExeplo();
		});
		
		btnConsulta1.setOnAction(e -> {
			consulta1();
		});	

		pane.setCenter(mapkit);
		pane.setTop(leftPane);

		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Mapas com JavaFX");
		primaryStage.show();

	}

	// Inicializando os dados aqui...
	private void setup() {

		gerCons = GerenciadorConsultas.getInstance();
		//Load the airlines to memory
		gerCias = GerenciadorCias.getInstance();
		gerCias.carregaDados("airlines.dat");
		//Load the aircrafts to memory
		gerAvioes = GerenciadorAeronaves.getInstance();
		gerAvioes.carregaDados("equipment.dat");
		//Load the airports to memory
		gerAero = GerenciadorAeroportos.getInstance();
		gerAero.carregaDados("airports.dat");
		//Load the routes to memory
		gerRotas = GerenciadorRotas.getInstance();
		gerRotas.carregaDados("routes.dat");



	}

	private void consultaExeplo() {
		gerCons.consultaExemplo(gerenciador);
	}

	private void consulta1() {
		gerCons.consulta1(gerenciador, gerAero);
	}

	private class EventosMouse extends MouseAdapter {
		private int lastButton = -1;

		@Override
		public void mousePressed(MouseEvent e) {
			JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
			GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
			// System.out.println(loc.getLatitude()+", "+loc.getLongitude());
			lastButton = e.getButton();
			// Botão 3: seleciona localização
			if (lastButton == MouseEvent.BUTTON3) {
				gerenciador.setPosicao(loc);
				gerenciador.getMapKit().repaint();
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

	public static void main(String[] args) {
		launch(args);
	}
}
