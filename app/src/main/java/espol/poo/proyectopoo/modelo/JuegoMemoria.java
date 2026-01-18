package espol.poo.proyectopoo.modelo;

import java.util.ArrayList;
import java.util.Collections;

public class JuegoMemoria {
    /**
     * @param tablero: tablero del juego, 4 filas y 4 columnas de cartas
     * @param baraja: ArrayList con las cartas de la baraja
     *
     */

    private Carta[][] tablero = new Carta[4][4];
    private ArrayList<Carta> baraja = new ArrayList<>();

    public JuegoMemoria(String[] palabras){
        generarBaraja(palabras);
        mezclarYUbicar();
    }

    /**
     * Metodo para generar la baraja de cartas
     * @param palabras: Lista de palabras para generar las cartas
     */
    private void generarBaraja(String[] palabras){
        for(String p : palabras){
            baraja.add(new Carta(p));
            baraja.add(new Carta(p));
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

    public String verValor(int numero){
        return getCarta(numero).getValor();
    }

    public boolean estaEmparejada(int numero){
        return getCarta(numero).estaEmparejada();
    }

    public void marcarEmparejadas(int n1, int n2){
        getCarta(n1).marcarEmparejada();
        getCarta(n2).marcarEmparejada();
    }

    /**
     * Metodo para que se muestren las cartas que ya han sido emparejadas
     * @return tablero con las cartas que han sido emparejadas
     */
    public String[][] getTableroVisible(){
        String[][] visible = new String[4][4];

        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(tablero[i][j].estaEmparejada()){
                    visible[i][j] = tablero[i][j].getValor();
                } else {
                    visible[i][j] = "?";
                }
            }
        }
        return visible;
    }

    /**
     * Metodo para generar las pistas de la ubicacion de las palabras
     * para facilitar revisión :)
     * @param palabras: Se obtienen las palabras que han sido colocadas para
     *                encontrar sus pares
     * @return pares: Se devuelve la lista de pares encontrados
     */
    public String[][] generarPistas(String[] palabras){
        String[][] pares = new String[8][5];
        int k = 0;

        for(String p : palabras){
            int f1=-1, c1=-1, f2=-1, c2=-1;

            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++){
                    if(tablero[i][j].getValor().equals(p)){
                        if(f1 == -1){ f1=i; c1=j; }
                        else       { f2=i; c2=j; }
                    }
                }
            }

            pares[k][0] = p;
            pares[k][1] = String.valueOf(f1);
            pares[k][2] = String.valueOf(c1);
            pares[k][3] = String.valueOf(f2);
            pares[k][4] = String.valueOf(c2);
            k++;
        }

        return pares;
    }
}

