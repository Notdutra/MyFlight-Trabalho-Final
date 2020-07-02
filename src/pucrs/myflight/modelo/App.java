package pucrs.myflight.modelo;

import java.security.KeyStore.Entry;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import pucrs.myflight.gui.BotarTempo;
import pucrs.myflight.gui.GerenciadorConsultas;
import sun.security.jca.GetInstance;

public class App {

    public static void main(String[] args) {
        System.out.println("\n\n\n App comecou");

        GerenciadorCias gerCias = GerenciadorCias.getInstance();
        gerCias.carregaDados("airlines.dat");

        GerenciadorAeronaves gerAvioes = GerenciadorAeronaves.getInstance();
        gerAvioes.carregaDados("equipment.dat");
    
        GerenciadorAeroportos gerAero = GerenciadorAeroportos.getInstance();
        gerAero.carregaDados("airports.dat");

        GerenciadorRotas gerRotas = GerenciadorRotas.getInstance();
        gerRotas.carregaDados("routes.dat");

        GerenciadorPaises gerPaises = GerenciadorPaises.getInstance();
        gerPaises.carregaDados("countries.dat");


        gerAero.adicionar(new Aeroporto("POA", "Salgado Filho Intl", new Geo(-29.9939, -51.1711)));

        GerenciadorConsultas gerCon = GerenciadorConsultas.getInstance();
   
        System.out.println("\n\nApp terminou");

        
    }
}