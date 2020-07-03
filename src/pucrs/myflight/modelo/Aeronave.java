package pucrs.myflight.modelo;

public class Aeronave implements Imprimivel, Comparable<Aeronave> {
	private String codigo;
	private String descricao;
	private int capacidade;
	
	public Aeronave(String codigo, String descricao, int cap) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.capacidade = cap;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public int getCapacidade() {
		return capacidade;
	}

    @Override
    public String toString() {
        return codigo + " - " + descricao + " (" + capacidade + ")";
    }

    // Implementação da interface Imprimivel
	// Neste caso, basta chamar toString
	@Override
	public void imprimir() {
		System.out.println(toString());
	}

	// Define o critério de comparação entre duas
	// aeronaves (usado em Collections.sort(), por exemplo
	@Override
	public int compareTo(Aeronave outra) {
		return descricao.compareTo(outra.descricao);
	}
}
