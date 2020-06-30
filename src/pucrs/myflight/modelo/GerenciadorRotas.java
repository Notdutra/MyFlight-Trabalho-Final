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
}
