package pucrs.myflight.modelo;

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

        // GerenciadorPaises gerPaises = GerenciadorPaises.getInstance();
        // gerPaises.carregaDados("countries.dat");
        System.out.println("foi");
        gerRotas.printarTodas();
        System.out.println("Terminou");
        
    }

}