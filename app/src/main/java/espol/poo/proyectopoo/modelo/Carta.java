package espol.poo.proyectopoo.modelo;

public class Carta {

    private String valor;
    private boolean emparejada;

    public Carta(String valor){
        this.valor = valor;
        this.emparejada = false;
    }

    public String getValor(){
        return valor;
    }

    public boolean estaEmparejada(){
        return emparejada;
    }

    public void marcarEmparejada(){
        this.emparejada = true;
    }
}

