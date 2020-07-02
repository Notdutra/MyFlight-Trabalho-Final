package pucrs.myflight.modelo;

public class Rota implements Comparable<Rota> {
	private CiaAerea cia;
	private Aeroporto origem;
	private Aeroporto destino;
	private Aeronave aeronave;
	
	public Rota(CiaAerea cia, Aeroporto origem, Aeroporto destino, Aeronave aeronave) {
		this.cia = cia;
		this.origem = origem;
		this.destino = destino;
		this.aeronave = aeronave;		
	}	
	
	public CiaAerea getCia() {
		return cia;
	}
	
	public Aeroporto getDestino() {
		return destino;
	}
	
	public Aeroporto getOrigem() {
		return origem;
	}
	
	public Aeronave getAeronave() {
		return aeronave;
	}

    @Override
    public String toString() {
        return cia.getCodigo() + " - " + origem.getCodigo() + " -> " + destino.getCodigo()
                + " ("  + aeronave.getCodigo() + ")";
    }

	@Override
	public int compareTo(Rota rota) {
		return this.cia.getNome().compareTo(
				rota.cia.getNome());
	}
}
