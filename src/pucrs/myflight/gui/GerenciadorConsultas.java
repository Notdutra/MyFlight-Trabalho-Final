package pucrs.myflight.gui;

import java.util.ArrayList;
import java.util.List;

import java.awt.Color;
import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.Geo;
import pucrs.myflight.modelo.GerenciadorAeroportos;
import pucrs.myflight.modelo.GerenciadorRotas;
import pucrs.myflight.modelo.Rota;

public class GerenciadorConsultas {
    
    private static GerenciadorConsultas instance;

    public GerenciadorConsultas() {}

    public static GerenciadorConsultas getInstance() {
        if (instance == null) {
            instance = new GerenciadorConsultas();
        }
        return instance;
    }

    public void consultaExemplo (GerenciadorMapa gerMapa){
        // Lista para armazenar o resultado da consulta
		List<MyWaypoint> lstPoints = new ArrayList<>();

		Aeroporto poa = new Aeroporto("POA", "Salgado Filho", new Geo(-29.9939, -51.1711));
		Aeroporto gru = new Aeroporto("GRU", "Guarulhos", new Geo(-23.4356, -46.4731));
		Aeroporto lis = new Aeroporto("LIS", "Lisbon", new Geo(38.772,-9.1342));
		Aeroporto mia = new Aeroporto("MIA", "Miami International", new Geo(25.7933, -80.2906));
		
		gerMapa.clear();
		Tracado tr = new Tracado();
		tr.setLabel("Teste");
		tr.setWidth(5);
		tr.setCor(new Color(0,0,0,60));
		tr.addPonto(poa.getLocal());
		tr.addPonto(mia.getLocal());

		gerMapa.addTracado(tr);
		
		Tracado tr2 = new Tracado();
		tr2.setWidth(5);
		tr2.setCor(Color.BLUE);
		tr2.addPonto(gru.getLocal());
		tr2.addPonto(lis.getLocal());
		gerMapa.addTracado(tr2);
		
		// Adiciona os locais de cada aeroporto (sem repetir) na lista de
		// waypoints
		
		lstPoints.add(new MyWaypoint(Color.RED, poa.getCodigo(), poa.getLocal(), 5));
		lstPoints.add(new MyWaypoint(Color.RED, gru.getCodigo(), gru.getLocal(), 5));
		lstPoints.add(new MyWaypoint(Color.RED, lis.getCodigo(), lis.getLocal(), 5));
		lstPoints.add(new MyWaypoint(Color.RED, mia.getCodigo(), mia.getLocal(), 5));

		// Para obter um ponto clicado no mapa, usar como segue:
		// GeoPosition pos = gerenciador.getPosicao();

		// Informa o resultado para o gerenciador
		gerMapa.setPontos(lstPoints);

		// Quando for o caso de limpar os traçados...
		// gerenciador.clear();

		gerMapa.getMapKit().repaint();
	}
	
	public void consulta1(GerenciadorMapa gerMapa, GerenciadorAeroportos gerAero,GerenciadorRotas gerRotas, String nomeCiaAerea){
		gerMapa.clear();
		List<MyWaypoint> lstPoints = new ArrayList<>();

        ArrayList<Rota> listaRota = gerRotas.listarTodas();
        for (int i = 0; i < listaRota.size(); i++) {
            Rota atual = listaRota.get(i);
            if (atual.getCia().getNome().equalsIgnoreCase(nomeCiaAerea)) {
                Aeroporto aero = atual.getOrigem();
                lstPoints.add(new MyWaypoint(Color.CYAN, aero.getCodigo(), aero.getLocal(), 5));
                System.out.println(i);
            }
        }

		gerMapa.setPontos(lstPoints);
		gerMapa.getMapKit().repaint();
	}
}