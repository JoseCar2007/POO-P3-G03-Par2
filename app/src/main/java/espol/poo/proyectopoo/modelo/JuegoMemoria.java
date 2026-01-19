package espol.poo.proyectopoo.modelo;

import java.util.ArrayList;
import java.util.Collections;

public class JuegoMemoria {
    /**
     * @param tablero: tablero del juego, 4 filas y 4 columnas de cartas
     * @param baraja: ArrayList con las cartas de la baraja
     */
    private final Carta[][] tablero = new Carta[4][4];
    private final ArrayList<Carta> baraja = new ArrayList<>();

    // CAMBIO: Recibimos int[] (IDs de recursos) en lugar de String[]
    public JuegoMemoria(int[] imagenes){
        generarBaraja(imagenes);
        mezclarYUbicar();
    }

    /**
     * Metodo para generar la baraja de cartas
     * @param imagenes: Lista de IDs de imágenes para generar las cartas
     */
    private void generarBaraja(int[] imagenes){
        for(int img : imagenes){
            baraja.add(new Carta(img));
            baraja.add(new Carta(img));
        }
    }

    /**
     * Metodo para mezclar y ubicar las cartas en el tablero
     */
    private void mezclarYUbicar(){
        Collections.shuffle(baraja);
        int idx = 0;

        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                tablero[i][j] = baraja.get(idx++);
            }
        }
    }

    // Obtener carta por número del 1 al 16
    public Carta getCarta(int numero){
        int pos = numero - 1;
        int fila = pos / 4;
        int col = pos % 4;
        return tablero[fila][col];
    }

    // CAMBIO: Devolvemos int (ID imagen) en vez de String
    public int verImagen(int numero){
        return getCarta(numero).getIdImagen();
    }

    public boolean estaEmparejada(int numero){
        return getCarta(numero).estaEmparejada();
    }

    public void marcarEmparejadas(int n1, int n2){
        getCarta(n1).marcarEmparejada();
        getCarta(n2).marcarEmparejada();
    }
}