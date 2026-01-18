package espol.poo.proyectopoo.modelo;

public class Carta {
    /**
     * @param valor: valor de la carta
     * @param emparejada: indica si se ha encontrado el valor
     *                  al que le corresponde en el juego.
     */
    private String valor;
    private boolean emparejada;

    public Carta(String valor){
        this.valor = valor;
        this.emparejada = false;
    }
    //getters y setters
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

