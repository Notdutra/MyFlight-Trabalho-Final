package pucrs.myflight.modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;

public class GerenciadorVoos {

    private ArrayList<Voo> voos;

    private static GerenciadorVoos instance;

    public static GerenciadorVoos getInstance() {
        if (instance == null) {
            instance = new GerenciadorVoos();
        }
        return instance;
    }

    private GerenciadorVoos() {
        this.voos = new ArrayList<>();
    }

    public void ordenarDataHora() {
        //voos.sort(Comparator.comparing(v -> v.getDatahora()));
        voos.sort(Comparator.comparing(Voo::getDatahora));
    }

    public void ordenarDataHoraDuracao() {
        voos.sort(Comparator.comparing(Voo::getDatahora).
                thenComparing(Voo::getDuracao));
    }

    public void adicionar(Voo r) {
        voos.add(r);
    }

    public ArrayList<Voo> listarTodos() {
        return new ArrayList<>(voos);
    }

    public ArrayList<Voo> buscarData(LocalDate data) {
       ArrayList<Voo> result = new ArrayList<>();
       for(Voo v: voos)
           if(v.getDatahora().toLocalDate().equals(data))
               result.add(v);
       return result;
    }

    // Tarefa 1: listar os dados de vôos cuja origem é informada
    public ArrayList<Voo> buscarOrigem(String cod) {
        ArrayList<Voo> result = new ArrayList<>();
        for(Voo v: voos)
            if(v.getRota().getOrigem().getCodigo().equals(cod))
                result.add(v);
        return result;
    }

    // Tarefa 1: listar os dados de vôos que operam em determinado período do dia
    public ArrayList<Voo> buscarPeriodo(LocalTime inicio, LocalTime fim) {
        ArrayList<Voo> result = new ArrayList<>();
        for(Voo v: voos) {
            if(v.getDatahora().toLocalTime().compareTo(inicio) >= 0 &&
                    v.getDatahora().toLocalTime().compareTo(fim) <= 0)
                result.add(v);
        }
        return result;
    }
}
