package pucrs.myflight.modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class VooEscalas extends Voo {

    private ArrayList<Rota> rotas;

    public VooEscalas(Rota rota,
         LocalDateTime datahora, Duration duracao) {
       super(rota, datahora, duracao);
       rotas = new ArrayList<>();
//       this.rota = rota;
//       this.datahora = datahora;
//       this.duracao = duracao;
//       this.rotaFinal = rotaFinal;
    }

    public void adicionarRota(Rota nova) {
        rotas.add(nova);
    }

    public int getTotalRotas() {
        return rotas.size();
    }

    public ArrayList<Rota> getRotas() {
        return new ArrayList<>(rotas);
    }

//    public Rota getRotaFinal() {
//        return rotaFinal;
//    }

    @Override
    public String toString() {
        String aux = super.toString();
        for(Rota r: rotas)
            aux += "\n   " + r;
        return aux;
    }



}
