package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GerenciadorAeronaves {
    
    private ArrayList<Aeronave> avioes;

    private static GerenciadorAeronaves instance;

    public static GerenciadorAeronaves getInstance() {
        if (instance == null) {
            instance = new GerenciadorAeronaves();
        }
        return instance;
    }

    private GerenciadorAeronaves() {
        this.avioes = new ArrayList<>();
    }

    public void adicionar(Aeronave aviao) {
        avioes.add(aviao);
    }

    public ArrayList<Aeronave> listarTodas() {
        return new ArrayList<>(avioes);
    }

    public Aeronave buscarCodigo(String codigo) {
        for (Aeronave a : avioes)
            if (a.getCodigo().equals(codigo))
                return a;
        return null;
    }

    public void ordenarDescricao() {
        // Usando Comparable<Aeronave> em Aeronave
        //Collections.sort(avioes);

        // Usando expressão lambda
        //avioes.sort( (Aeronave a1, Aeronave a2) ->
        //    a1.getDescricao().compareTo(a2.getDescricao()));

        // Mesma coisa, usando método static da interface Comparator:
        //avioes.sort(Comparator.comparing(a -> a.getDescricao()));

        // Invertendo o critério de comparação com reversed():
        avioes.sort(Comparator.comparing(Aeronave::getDescricao).reversed());
    }

    public void ordenarCodigoDescricao() {
        // Ordenando pelo código e desempatando pela descrição
        avioes.sort(Comparator.comparing(Aeronave::getCodigo).
                thenComparing(Aeronave::getDescricao));
    }

    public void ordenarCodigo() {
        avioes.sort((Aeronave a1, Aeronave a2) ->
                a1.getCodigo().compareTo(a2.getCodigo()));
    }

    public void carregaDados(String nomeArq) {
        Path path1 = Paths.get(nomeArq);
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("utf8"))) {
            String line = null;
            int capacity = 0;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(";");
                capacity = Integer.parseInt(dados[2]);
                Aeronave nova = new Aeronave(dados[0], dados[1], capacity);
                adicionar(nova);
            }
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }
}
