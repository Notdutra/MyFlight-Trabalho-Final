package pucrs.myflight.modelo;

public class Pais {
    private String code;
    private String name;

    public Pais(String codigo, String nome){
        this.code = codigo;
        this.name = nome;
    }

    //Getters
    public String getCodigo(){
        return this.code;
    }
    public String getNome() {
        return this.name;
    }

}