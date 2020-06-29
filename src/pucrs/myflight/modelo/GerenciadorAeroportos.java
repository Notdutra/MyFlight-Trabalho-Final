package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import org.jxmapviewer.viewer.GeoPosition;

public class GerenciadorAeroportos {

    private ArrayList<Aeroporto> aeroportos;

    private static GerenciadorAeroportos instance;

    public static GerenciadorAeroportos getInstance() {
        if (instance == null) {
            instance = new GerenciadorAeroportos();
        }
        return instance;
    }

    private GerenciadorAeroportos() {
        this.aeroportos = new ArrayList<>();
    }

    public void ordenarNomes() {
        Collections.sort(aeroportos);
    }

    public void adicionar(Aeroporto aero) {
        aeroportos.add(aero);
    }

    public ArrayList<Aeroporto> listarTodos() {
        return new ArrayList<>(aeroportos);
    }

    public Aeroporto buscarCodigo(String codigo) {
        for(Aeroporto a: aeroportos)
            if(a.getCodigo().equals(codigo))
                return a;
        return null;
    }
    public void carregaDados(String nomeArq){
        Path path1 = Paths.get(nomeArq);
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("utf8"))) {
            String line = null;
            reader.readLine();//pula a primeira linha
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(";");
                Geo geoAux = new Geo(Double.parseDouble(dados[1]),Double.parseDouble(dados[2]));
                Aeroporto novo = new Aeroporto(dados[0], dados[4],geoAux);
                adicionar(novo);
            }
        }
        catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    public Aeroporto getAirportFromGPS(Geo posEmGeo) {
        for (Aeroporto aeroporto : aeroportos) {
            if (aeroporto.getLocal().distancia(posEmGeo) <= 5) { // 5 é a distancia... 5 km ta dando mais ou menos 5km entao azar
                return aeroporto;
            }
        }
        return null;
    }
}
