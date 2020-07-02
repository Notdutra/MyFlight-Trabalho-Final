package pucrs.myflight.modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Voo {
	
	public enum Status { CONFIRMADO, ATRASADO, CANCELADO }
	
	private LocalDateTime datahora;
	private Duration duracao;
	private Rota rota;
	private Status status;
	private DateTimeFormatter formatador;

	public Voo(Rota rota, LocalDateTime datahora, Duration duracao) {
		this.rota = rota;
		this.datahora = datahora;
		this.duracao = duracao;
		this.status = Status.CONFIRMADO; // default é confirmado
        // Para formatar LocalDateTime de uma forma inteligível
        this.formatador = DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm");
	}

	public Voo(Rota rota, Duration duracao) {

	    this(rota,
          LocalDateTime.of(2018,4,3,22,00,0),
          duracao);
//	    this(rota, LocalDateTime.now(), duracao);
    }
	
	public Rota getRota() {
		return rota;
	}
	
	public LocalDateTime getDatahora() {
		return datahora;
	}
	
	public Duration getDuracao() {
		return duracao;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status novo) {
		this.status = novo;
	}

    @Override
    public String toString() {
        return rota + " : " + formatador.format(datahora) + " [" +  duracao + "] - "
                + status;
    }
}

