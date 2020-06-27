package pucrs.myflight.modelo;

import java.util.ArrayList;
import java.util.Collections;

public class GerenciadorRotas {

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
        rotas.sort( (Rota r1, Rota r2) ->
          r1.getCia().getNome().compareTo(
          r2.getCia().getNome()));
    }

    public void ordenarNomesAeroportos() {
        rotas.sort( (Rota r1, Rota r2) ->
                r1.getOrigem().getNome().compareTo(
                r2.getOrigem().getNome()));
    }

    public void ordenarNomesAeroportosCias() {
        rotas.sort( (Rota r1, Rota r2) -> {
           int result = r1.getOrigem().getNome().compareTo(
                   r2.getOrigem().getNome());
           if(result != 0)
               return result;
           return r1.getCia().getNome().compareTo(
                   r2.getCia().getNome());
        });
    }
    public void adicionar(Rota r) {
        rotas.add(r);
    }

    public ArrayList<Rota> listarTodas() {
        return new ArrayList<>(rotas);
    }

    public ArrayList<Rota> buscarOrigem(String codigo) {
        ArrayList<Rota> result = new ArrayList<>();
        for(Rota r: rotas)
            if(r.getOrigem().getCodigo().equals(codigo))
                result.add(r);
        return result;
    }
}
