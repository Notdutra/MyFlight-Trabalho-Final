package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

public class GerenciadorPaises {

    private ArrayList<Pais> paises;

    private static GerenciadorPaises instance;

    public static GerenciadorPaises getInstance() {
        if (instance == null){
            instance = new GerenciadorPaises();
        }
        return instance;
    }

    private GerenciadorPaises() {
        this.paises = new ArrayList<>();
    }

    public void adicionar(Pais pais) {
        paises.add(pais);
    }
    
    public ArrayList<Pais> listarTodas() {
        return new ArrayList<>(paises);
    }

    public Pais buscarCodigo(String codigo) {
        for (Pais p : paises){
            if (p.getCodigo().equals(codigo))
                return p;
        }
        return null;
    }

    public void ordenarNome() {
        paises.sort(Comparator.comparing(Pais::getNome));
    }

    public void carregaDados(String nomeArq) {
        Path path1 = Paths.get(nomeArq);
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("utf8"))) {
            String line = null;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(";");
                Pais novo = new Pais(dados[0], dados[1]);
                adicionar(novo);
            }
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }
}