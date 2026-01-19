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

    /**
     * * ESTE MÉTHOD QUEDÓ OBSOLETO DEBIDO AL CAMBIO EN EL MODELO PORQUE PASAMOS DE STRINGS A IMÁGENES PARA LAS CARTAS
     * Metodo para que se muestren las cartas que ya han sido emparejadas
     * NOTA: Adaptado para devolver IDs de imágenes. 0 significa oculto/signo de interrogación.
     * @return tablero con los IDs de las cartas emparejadas
     */
    public int[][] getTableroVisible(){
        int[][] visible = new int[4][4];

        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(tablero[i][j].estaEmparejada()){
                    visible[i][j] = tablero[i][j].getIdImagen();
                } else {
                    visible[i][j] = 0; // 0 representa que no se muestra nada o un placeholder
                }
            }
        }
        return visible;
    }

    /**
     * ESTE MÉTHOD QUEDÓ OBSOLETO DEBIDO AL CAMBIO EN EL MODELO PORQUE PASAMOS DE STRINGS A IMÁGENES PARA LAS CARTAS
     * Metodo para generar las pistas de la ubicacion de las imágenes
     * para facilitar revisión :)
     * @param imagenes: Se obtienen los IDs de imágenes colocados
     * @return pares: Se devuelve la lista de pares encontrados (Nombre/ID y coordenadas)
     */
    public String[][] generarPistas(int[] imagenes){
        String[][] pares = new String[8][5];
        int k = 0;

        for(int img : imagenes){
            int f1=-1, c1=-1, f2=-1, c2=-1;

            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++){
                    // Comparamos por ID de imagen (int)
                    if(tablero[i][j].getIdImagen() == img){
                        if(f1 == -1){ f1=i; c1=j; }
                        else       { f2=i; c2=j; }
                    }
                }
            }

            // Guardamos el ID como String solo para el reporte de pistas
            pares[k][0] = String.valueOf(img);
            pares[k][1] = String.valueOf(f1);
            pares[k][2] = String.valueOf(c1);
            pares[k][3] = String.valueOf(f2);
            pares[k][4] = String.valueOf(c2);
            k++;
        }

        return pares;
    }
}