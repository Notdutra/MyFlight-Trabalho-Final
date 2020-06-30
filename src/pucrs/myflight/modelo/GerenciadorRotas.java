package pucrs.myflight.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GerenciadorRotas {

    GerenciadorCias gerCias = GerenciadorCias.getInstance();
    GerenciadorAeroportos gerAero = GerenciadorAeroportos.getInstance();
    GerenciadorAeronaves gerAvioes = GerenciadorAeronaves.getInstance();

    private ArrayList<Rota> rotas;

    private static GerenciadorRotas instance;

    public static GerenciadorRotas getInstance() {
        if (instance == null) {
            instance = new GerenciadorRotas();
        }
        return instance;
    }

    private GerenciadorRotas() {
        this.rotas = new ArrayList<>();
    }

    public void ordenarCias() {
        Collections.sort(rotas);
    }

    public void ordenarNomesCias() {
        rotas.sort((Rota r1, Rota r2) -> r1.getCia().getNome().compareTo(r2.getCia().getNome()));
    }

    public void ordenarNomesAeroportos() {
        rotas.sort((Rota r1, Rota r2) -> r1.getOrigem().getNome().compareTo(r2.getOrigem().getNome()));
    }

    public void ordenarNomesAeroportosCias() {
        rotas.sort((Rota r1, Rota r2) -> {
            int result = r1.getOrigem().getNome().compareTo(r2.getOrigem().getNome());
            if (result != 0)
                return result;
            return r1.getCia().getNome().compareTo(r2.getCia().getNome());
        });
    }

    public void adicionar(Rota r) {
        rotas.add(r);
    }

    public ArrayList<Rota> listarTodas() {
        return new ArrayList<>(rotas);
    }

    public void printarArrayRota(ArrayList<Rota> lista) {
        for (Rota rota : lista) {
            System.out.println(rota);
        }
    }

    public void printarTodas() {
        for (Rota rota : rotas) {
            System.out.println(rota + "\n");
        }
    }

    public void carregaDados(String nomeArq) {
        Path path1 = Paths.get(nomeArq);
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("utf8"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(";| ");

                CiaAerea novaCiaAerea = gerCias.buscarCodigo(dados[0]);
                Aeroporto novoOrigem = gerAero.buscarCodigo(dados[1]);
                Aeroporto novoDestino = gerAero.buscarCodigo(dados[2]);
                Aeronave novaAeronave = gerAvioes.buscarCodigo(dados[5]);

                if ((novaCiaAerea != null) && (novoOrigem != null) && (novoDestino != null) && (novaAeronave != null)) {
                    Rota nova = new Rota(novaCiaAerea, novoOrigem, novoDestino, novaAeronave);
                    adicionar(nova);
                }
            }
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    public ArrayList<Rota> buscarOrigem(String codigo) {
        ArrayList<Rota> result = new ArrayList<>();
        for (Rota r : rotas)
            if (r.getOrigem().getCodigo().equals(codigo))
                result.add(r);
        return result;
    }

    public ArrayList<Rota> buscarDestino(String codigo) {
        ArrayList<Rota> result = new ArrayList<>();
        for (Rota r : rotas)
            if (r.getDestino().getCodigo().equals(codigo))
                result.add(r);
        return result;
    }

    public HashMap<Aeroporto, Aeroporto> pegaDestino(String codigo) {
        HashMap<Aeroporto, Aeroporto> resultado = new HashMap<>();
        for (Rota r : rotas) {
            if (r.getDestino().getCodigo().equalsIgnoreCase(codigo)) {
                resultado.put(r.getOrigem(), r.getDestino());
            }
        }
        return resultado;
    }

    public HashMap<Aeroporto, Aeroporto> pegaOrigem(String codigo) {
        HashMap<Aeroporto, Aeroporto> resultado = new HashMap<>();
        for (Rota r : rotas) {
            if (r.getOrigem().getCodigo().equalsIgnoreCase(codigo)) {
                resultado.put(r.getDestino(), r.getOrigem());
            }
        }
        return resultado;
    }

    public ArrayList<Rota> getRotasPorCia(String codCia) {
        ArrayList<Rota> rotasDaCia = new ArrayList<Rota>();
        for (Rota r : rotas) {
            if (r.getCia().getCodigo() == codCia) {
                rotasDaCia.add(r);
            }
        }
        return rotasDaCia;
    }

    public HashMap<String, Integer> getAirTraffic() {
        HashMap<String, Integer> traffic = new HashMap<String, Integer>();

        for (Rota r : rotas) {
            if (!traffic.containsKey(r.getOrigem().getCodigo())) {
                traffic.put(r.getOrigem().getCodigo(), 0);
            } else {
                int value = traffic.get(r.getOrigem().getCodigo());
                traffic.replace(r.getOrigem().getCodigo(), value, ++value);
            }
            if (!traffic.containsKey(r.getDestino().getCodigo())) {
                traffic.put(r.getDestino().getCodigo(), 0);
            } else {
                int value = traffic.get(r.getDestino().getCodigo());
                traffic.replace(r.getDestino().getCodigo(), value, ++value);
            }
        }

        return traffic;

    }

    public ArrayList<String> acharRotaComUmaConexao(String origemInicial, String destinoFinal) {
        GerenciadorRotas gerRotas = GerenciadorRotas.getInstance();
        System.out.println("---------------------------------------------");

        HashMap<Aeroporto, Aeroporto> mapaOrigemInicial = gerRotas.pegaOrigem(origemInicial);
        HashMap<Aeroporto, Aeroporto> mapaDestinoFinal = gerRotas.pegaDestino(destinoFinal);

        ArrayList<String> listaDeConexoes = new ArrayList<>();
        mapaDestinoFinal.entrySet().forEach(destinoAtual -> {
            mapaOrigemInicial.entrySet().forEach(origemAtual -> {
                if (origemAtual.getKey().equals(destinoAtual.getKey())) {
                    // System.out.println(origemAtual.getKey().getCodigo()
                    listaDeConexoes.add(origemInicial + origemAtual.getKey().getCodigo() + destinoFinal);

                }
            });
        });

        return listaDeConexoes;
    }

    public ArrayList<String> acharRotaComDuasConexoes(String origemInicial, String destinoFinal) {
        GerenciadorRotas gerRotas = GerenciadorRotas.getInstance();
        System.out.println("---------------------------------------------");

        HashMap<Aeroporto, Aeroporto> mapaPoa = gerRotas.pegaOrigem(origemInicial);
        HashMap<Aeroporto, Aeroporto> mapaMia = gerRotas.pegaDestino(destinoFinal);
        // x = chaveDePoa y = chaveDeMia
        ArrayList<String> listaDeConexoes = new ArrayList<>();
        mapaMia.entrySet().forEach(chaveDeMia -> {
            mapaPoa.entrySet().forEach(chaveDePoa -> {
                Aeroporto xMia = chaveDePoa.getKey();
                if (xMia.equals(chaveDeMia.getKey())) {// se poa tem conexao com mia
                    // entao pulamos para xMia -> y -> mia
                    HashMap<Aeroporto, Aeroporto> mapaXMia = gerRotas.pegaOrigem(origemInicial);
                    mapaXMia.entrySet().forEach(chaveDoX -> {
                        mapaMia.entrySet().forEach(chaveDeMiaFinal -> {
                            Aeroporto yMia = chaveDeMiaFinal.getKey();
                            if (yMia.equals(chaveDoX.getKey())
                                    && !xMia.getCodigo().equalsIgnoreCase(yMia.getCodigo())) { // se x tem conexao com
                                                                                               // mia
                                // System.out.println(origemInicial + " -> " + xMia.getCodigo() + " -> " +
                                // yMia.getCodigo() + " -> " + destinoFinal);
                                listaDeConexoes.add(
                                        origemInicial + xMia.getCodigo() + " -> " + yMia.getCodigo() + destinoFinal);
                            }
                        });
                    });
                }
            });
        });
        // String temp = (origemInicial + " -> " + aeroportoX + " -> " + destinoFinal);
        return listaDeConexoes;
    }
}
