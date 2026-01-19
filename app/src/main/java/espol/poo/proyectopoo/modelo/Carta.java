package espol.poo.proyectopoo.modelo;
    public class Carta {
        /**
         * CAMBIO: Ahora guardamos el ID de la imagen (int) en vez de texto (String)
         * @param idImagen: ID de la imagen que representa la carta
         * @param emparejada: indica si se ha encontrado el valor
         *                  al que le corresponde en el juego.
         */
        private int idImagen;
        private boolean emparejada;

        public Carta(int idImagen){
            this.idImagen = idImagen;
            this.emparejada = false;
        }

        public int getIdImagen(){
            return idImagen;
        }

        public boolean estaEmparejada(){
            return emparejada;
        }
        public void marcarEmparejada(){
            this.emparejada = true;
        }
    }

