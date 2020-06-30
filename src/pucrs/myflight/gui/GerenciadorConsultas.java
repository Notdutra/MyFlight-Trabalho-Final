package pucrs.myflight.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jxmapviewer.viewer.GeoPosition;

import java.awt.Color;
import java.lang.reflect.Array;

import pucrs.myflight.modelo.*;

public class GerenciadorConsultas {

    private static GerenciadorConsultas instance;
    private static GerenciadorRotas gerRotas;

    public GerenciadorConsultas() {
    }

    public static GerenciadorConsultas getInstance() {
        if (instance == null) {
            instance = new GerenciadorConsultas();
        }
        return instance;
    }

    public void consultaExemplo(GerenciadorMapa gerMapa) {
        // Lista para armazenar o resultado da consulta
        List<MyWaypoint> lstPoints = new ArrayList<>();

        Aeroporto poa = new Aeroporto("POA", "Salgado Filho", new Geo(-29.9939, -51.1711));
        Aeroporto gru = new Aeroporto("GRU", "Guarulhos", new Geo(-23.4356, -46.4731));
        Aeroporto lis = new Aeroporto("LIS", "Lisbon", new Geo(38.772, -9.1342));
        Aeroporto mia = new Aeroporto("MIA", "Miami International", new Geo(25.7933, -80.2906));

        gerMapa.clear();
        Tracado tr = new Tracado();
        tr.setLabel("Teste");
        tr.setWidth(5);
        tr.setCor(new Color(0, 0, 0, 60));
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
        // GeoPosition pos = gerMapa.getPosicao();
        // System.out.println(pos.toString());
        // Informa o resultado para o gerenciador
        gerMapa.setPontos(lstPoints);

        // Quando for o caso de limpar os traçados...
        // gerenciador.clear();

        gerMapa.getMapKit().repaint();
    }

    public void consulta1(GerenciadorMapa gerMapa, GerenciadorAeroportos gerAero) {
        gerMapa.clear();

        ArrayList<Rota> rotas = GerenciadorRotas.getInstance().listarTodas();

        for (Rota r : rotas) {
            Tracado tr2 = new Tracado();
            tr2.setWidth(5);
            tr2.setCor(new Color(0, 0, 0, 60));
            tr2.addPonto(r.getOrigem().getLocal());
            tr2.addPonto(r.getDestino().getLocal());
            gerMapa.addTracado(tr2);
        }

        List<MyWaypoint> lstPoints = new ArrayList<>();
        for (Aeroporto a : gerAero.listarTodos()) {
            lstPoints.add(new MyWaypoint(Color.GREEN, a.getCodigo(), a.getLocal(), 5));
        }
        gerMapa.setPontos(lstPoints);

        Set<Aeroporto> setAeroporto = new HashSet<Aeroporto>();

        gerMapa.getMapKit().repaint();
    }

    public void consulta3(GerenciadorMapa gerMapa, Aeroporto destino, Aeroporto origem, GerenciadorRotas gerRotas) {
        ArrayList<Rota> result = new ArrayList<Rota>();
        for (Rota rota : gerRotas.listarTodas()) {
            if (rota.getOrigem() == origem && rota.getDestino() == destino) {// voo direto
                result.add(rota);
                Tracado tr2 = new Tracado();
                tr2.setWidth(1);
                tr2.setCor(Color.BLUE);
                tr2.addPonto(rota.getOrigem().getLocal());
                tr2.addPonto(rota.getDestino().getLocal());
                gerMapa.addTracado(tr2);
            }
            if (rota.getOrigem() == origem && rota.getDestino() != destino) {
                if (rota.getDestino() != rota.getOrigem()) {
                    for (Rota rota2 : gerRotas.listarTodas()) {
                        if (rota2.getDestino() == destino)
                            result.add(rota2);
                        Tracado tr2 = new Tracado();
                        tr2.setWidth(1);
                        tr2.setCor(Color.BLUE);
                        tr2.addPonto(rota2.getOrigem().getLocal());
                        tr2.addPonto(rota2.getDestino().getLocal());
                        gerMapa.addTracado(tr2);
                    }

                }
            }
        }
        System.out.println(result);

    }

    public void plotarAeroPorCia(GerenciadorMapa gerMapa, ArrayList<Rota> rotasDaCia) {
        gerMapa.clear();

        HashSet<Aeroporto> aeroportosCiaOpera = new HashSet<Aeroporto>();

        List<MyWaypoint> lstPoints = new ArrayList<>();

        for (Rota r : rotasDaCia) {
            if (!aeroportosCiaOpera.contains(r.getOrigem())) {
                aeroportosCiaOpera.add(r.getOrigem());
            }
            if (!aeroportosCiaOpera.contains(r.getDestino())) {
                aeroportosCiaOpera.add(r.getDestino());
            }
            Tracado tr2 = new Tracado();
            tr2.setWidth(1);
            tr2.setCor(Color.BLUE);
            tr2.addPonto(r.getOrigem().getLocal());
            tr2.addPonto(r.getDestino().getLocal());
            gerMapa.addTracado(tr2);
        }
        for (Aeroporto a : aeroportosCiaOpera) {
            lstPoints.add(new MyWaypoint(Color.GREEN, a.getCodigo(), a.getLocal(), 5));
        }

        gerMapa.setPontos(lstPoints);
        gerMapa.getMapKit().repaint();
    }

    public void setTraffic(GerenciadorMapa gerMapa, GerenciadorAeroportos gerAero, HashMap<String, Integer> traffic) {
        gerMapa.clear();

        List<MyWaypoint> lstPoints = new ArrayList<>();

        for (String s : traffic.keySet()) {
            Aeroporto temp = gerAero.buscarCodigo(s);
            int tamPonto = traffic.get(s) / 10;
            if (tamPonto < 50) {
                lstPoints.add(new MyWaypoint(Color.GREEN, temp.getCodigo(), temp.getLocal(), tamPonto));
            } else {
                lstPoints.add(new MyWaypoint(Color.RED, temp.getCodigo(), temp.getLocal(), tamPonto));
            }
        }

        gerMapa.setPontos(lstPoints);
        gerMapa.getMapKit().repaint();
    }
}