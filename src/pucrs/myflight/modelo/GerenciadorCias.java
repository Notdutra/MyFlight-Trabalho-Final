
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GerenciadorCias {

    private Map<String, CiaAerea> empresas;

    private static GerenciadorCias instance;

    public static GerenciadorCias getInstance() {
        if (instance == null) {
            instance = new GerenciadorCias();
        }
        return instance;
    }

    private GerenciadorCias() {
        this.empresas = new HashMap<>();
    }

    public ArrayList<CiaAerea> listarTodas() {
        return new ArrayList<>(empresas.values());
    }

    public void carregaDados(String nomeArq){
        Path path1 = Paths.get(nomeArq);
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("utf8"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(";");
                CiaAerea nova = new CiaAerea(dados[0], dados[1]);
                adicionar(nova);
            }
        }
        catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    public void adicionar(CiaAerea cia1) {
        empresas.put(cia1.getCodigo(),
                cia1);
    }

    public CiaAerea buscarCodigo(String cod) {
        return empresas.get(cod);
//        for (CiaAerea cia : empresas)
//            if (cia.getCodigo().equals(cod))
//                return cia;
//        return null;
    }

    public CiaAerea buscarNome(String nome) {
        for(CiaAerea cia: empresas.values())
           if(cia.getNome().equals(nome))
               return cia;
        return null;
    }
}
