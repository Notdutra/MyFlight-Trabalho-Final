package pucrs.myflight.modelo;

import java.security.KeyStore.Entry;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import pucrs.myflight.gui.GerenciadorConsultas;
import sun.security.jca.GetInstance;

public class App {

    public static void main(String[] args) {

        GerenciadorCias gerCias = GerenciadorCias.getInstance();

        gerCias.carregaDados("airlines.dat");
        ArrayList<CiaAerea> todasCias = gerCias.listarTodas();
        System.out.println("Total cias:" + todasCias.size());
        for(CiaAerea cia: todasCias)
            System.out.println(cia.getCodigo() + " - " + cia.getNome());

		/*
		gerCias.adicionar(new CiaAerea("JJ", "LATAM Linhas Aéreas"));
		gerCias.adicionar(new CiaAerea("G3", "Gol Linhas Aéreas S/A"));
		gerCias.adicionar(new CiaAerea("TP", "TAP Portugal"));
		gerCias.adicionar(new CiaAerea("AD", "Azul Linhas Aéreas"));
		*/

        GerenciadorAeronaves gerAvioes = GerenciadorAeronaves.getInstance();
        gerAvioes.carregaDados("equipment.dat");
        /*gerAvioes.adicionar(new Aeronave("733", "Boeing 737-300", 140));
        gerAvioes.adicionar(new Aeronave("73G", "Boeing 737-400", 126));
        gerAvioes.adicionar(new Aeronave("380", "Airbus Industrie A380", 644));
        gerAvioes.adicionar(new Aeronave("764", "Boeing 767-400", 304));
        gerAvioes.ordenarDescricao();*/
//        gerAvioes.ordenarCodigo();
        // Listando em ordem alfabética de descrição:
        System.out.println("\nAeronaves:");
        for(Aeronave av: gerAvioes.listarTodas())
            System.out.println(av);
        System.out.println();

        GerenciadorAeroportos gerAero = GerenciadorAeroportos.getInstance();
        gerAero.carregaDados("airports.dat");

        /*gerAero.adicionar(new Aeroporto("POA", "Salgado Filho Intl",
                new Geo(-29.9939, -51.1711)));
        gerAero.adicionar(new Aeroporto("GRU", "São Paulo Guarulhos Intl",
                new Geo(-23.4356, -46.4731)));
        gerAero.adicionar(new Aeroporto("LIS", "Lisbon",
                new Geo(38.7742, -9.1342)));
        gerAero.adicionar(new Aeroporto("MIA", "Miami Intl Airport",
                new Geo(25.7933, -80.2906)));
        gerAero.ordenarNomes();*/

        System.out.println("\nAeroportos ordenados por nome:\n");
        for(Aeroporto a: gerAero.listarTodos())
            System.out.println(a);
        System.out.println();

        // Para facilitar a criação de rotas:

        CiaAerea latam = gerCias.buscarCodigo("JJ");
        CiaAerea gol   = gerCias.buscarCodigo("G3");
        CiaAerea tap   = gerCias.buscarCodigo("TP");
        CiaAerea azul  = gerCias.buscarCodigo("AD");

        Aeronave b733 = gerAvioes.buscarCodigo("733");
        Aeronave b73g = gerAvioes.buscarCodigo("73G");
        Aeronave a380 = gerAvioes.buscarCodigo("380");

        Aeroporto poa = gerAero.buscarCodigo("POA");
        Aeroporto gru = gerAero.buscarCodigo("GRU");
        Aeroporto lis = gerAero.buscarCodigo("LIS");
        Aeroporto mia = gerAero.buscarCodigo("MIA");

        System.out.println("Distância POA->GRU: "+
                Geo.distancia(poa.getLocal(), gru.getLocal()));

        System.out.println("Distâcia GRU->POA: " +
                gru.getLocal().distancia(poa.getLocal()));

        GerenciadorRotas gerRotas = GerenciadorRotas.getInstance();
        gerRotas.carregaDados("routes.dat");
        // codigo exemplo
        // Rota poagru = new Rota(latam, poa, gru, b733);
        // Rota grupoa = new Rota(latam, gru, poa, b733);
        // Rota grumia = new Rota(tap, gru, mia, a380);
        // Rota grulis = new Rota(tap, gru, lis, a380);

        // gerRotas.adicionar(grumia);
        // gerRotas.adicionar(grulis);
        // gerRotas.adicionar(poagru);
        // gerRotas.adicionar(grupoa);
//		gerRotas.ordenarCias();
        gerRotas.ordenarNomesAeroportosCias();

        System.out.println("\nRotas ordenadas:\n");
        for(Rota r: gerRotas.listarTodas())
            System.out.println(r);
        System.out.println();

        LocalDateTime manhacedo = LocalDateTime.of(2018, 3, 29, 8, 0);
        LocalDateTime manhameio = LocalDateTime.of(2018, 4, 4, 10, 0);
        LocalDateTime tardecedo = LocalDateTime.of(2018, 4, 4, 14, 30);
        LocalDateTime tardetarde = LocalDateTime.of(2018, 4, 5, 17, 30);

        Duration curto = Duration.ofMinutes(90);
        Duration longo1 = Duration.ofHours(12);
        Duration longo2 = Duration.ofHours(14);

        GerenciadorVoos gerVoos = GerenciadorVoos.getInstance();

        // codigo exemplo
        // gerVoos.adicionar(new Voo(poagru, curto)); // agora!
        // gerVoos.adicionar(new Voo(grulis, tardecedo, longo2));
        // gerVoos.adicionar(new Voo(grulis, tardetarde, longo2));
        // gerVoos.adicionar(new Voo(poagru, manhacedo, curto));
        // gerVoos.adicionar(new Voo(grupoa, manhameio, curto));
        // gerVoos.adicionar(new Voo(grumia, manhacedo, longo1));

        // Vôo com várias escalas
        // VooEscalas vooEsc = new VooEscalas(poagru,
        //         manhacedo, longo2);
        // vooEsc.adicionarRota(grulis);

        // gerVoos.adicionar(vooEsc);

        // O toString vai usar o método implementado
        // em VooEscalas, mas reutilizando (reuso) o método
        // original de Voo
        // System.out.println(vooEsc.toString());

//        gerVoos.ordenarDataHoraDuracao();
        gerVoos.ordenarDataHoraDuracao();
        System.out.println("Todos os vôos:\n");
        for(Voo v: gerVoos.listarTodos())
        {
            if(v instanceof VooEscalas) {
                System.out.println(">>> Vôo com escalas!");
                VooEscalas vaux = (VooEscalas) v;
                System.out.println("Escalas: "+vaux.getTotalRotas());
            }
            System.out.println(v);
        }

        // Tarefa 1: listar os vôos de determinada origem

        System.out.println("\nVôos cuja origem é Guarulhos (gru)\n");
        for(Voo v: gerVoos.buscarOrigem("GRU"))
            System.out.println(v);

        // Tarefa 2: mostrar a localização dos aeroportos que operam em determinado período do dia

        LocalTime inicio = LocalTime.of(8, 0);
        LocalTime fim    = LocalTime.of(9, 0);

        System.out.println("\nVôos que ocorrem entre 7h e 9h\n");
        for(Voo v: gerVoos.buscarPeriodo(inicio, fim)) {
//            System.out.println(v);
            Aeroporto origem = v.getRota().getOrigem();
            System.out.println(origem.getNome() + ": " +origem.getLocal());
        }

        LocalTime inicio2 = LocalTime.of(9, 0);
        LocalTime fim2    = LocalTime.of(16, 0);

        System.out.println("\nVôos que ocorrem entre 9h e 16h\n");
        for(Voo v: gerVoos.buscarPeriodo(inicio2, fim2)) {
//            System.out.println(v);
            Aeroporto origem = v.getRota().getOrigem();
            System.out.println(origem.getNome() + ": " + origem.getLocal());
        }

        GerenciadorPaises gerPaises = GerenciadorPaises.getInstance();
        gerPaises.carregaDados("countries.dat");
        gerPaises.ordenarNome();
        ArrayList<Pais> todosPaises = gerPaises.listarTodas();
        System.out.println("Total de Paises:" + todosPaises.size());
        for(Pais p: todosPaises){
            System.out.println(p.getCodigo() + " - " + p.getNome());
        }

        // System.out.println("------------------------");
        // System.out.println("Todas as rotas com origem em POA");
        // ArrayList<Rota> origemPOALista = gerRotas.buscarOrigem(poa.getCodigo());
        // gerRotas.printarArrayRota(origemPOALista);
        
        
        
        // System.out.println("------------------------");
        // System.out.println("Todas as rotas com destino em POA");
        // ArrayList<Rota> destinoPOALista = gerRotas.buscarDestino(poa.getCodigo());
        // gerRotas.printarArrayRota(destinoPOALista);

        // System.out.println("------------------------");
        // System.out.println("Todas as rotas com origem em GRU");
        // ArrayList<Rota> origemGRULista = gerRotas.buscarOrigem(gru.getCodigo());
        // gerRotas.printarArrayRota(origemGRULista);

        // System.out.println("------------------------");
        // System.out.println("Todas as rotas com destino em POA");
        // ArrayList<Rota> destinoPOALista = gerRotas.buscarDestino(poa.getCodigo());
        // gerRotas.printarArrayRota(destinoPOALista);
        

        GerenciadorConsultas gerCon = GerenciadorConsultas.getInstance();

        ArrayList<String> busca = gerCon.acharRotaComDuasConexoes("POA", "MIA");
        //HashSet<String> buscaSemDups = new HashSet<>(busca);

        for (String string : busca) {
             System.out.println(string);
        }

    }
}