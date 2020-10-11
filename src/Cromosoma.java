
public class Cromosoma {

    public final int AVANZAR = 1;
    public final int GIRAR_DERECHA = 2;
    public final int GIRAR_IZQUIERDA = 3;
    /*
     * El arreglo de respuestas (cromosoma) se compone asi:
     *
     * posicion 0 - 255: estados posibles dentro de la cuadricula posicion 256 -
     * 263: estados posibles estando en una esquina superior derecha posicion
     * 264 - 271: estados posibles estando en una esquina inferior derecha
     * posicion 272 - 279: estados posibles estando en una esquina inferior
     * izquierda posicion 280 - 287: estados posibles estando en una esquina
     * superior derecha posicion 288 - 319: estados posibles estando en un borde
     * inferior posicion 320 - 351: estados posibles estando en un borde derecha
     * posicion 352 - 383: estados posibles estando en un borde izquierdo
     * posicion 384 - 415: estados posibles estando en un borde superior
     *
     */
    public int[] respuestas;

    // Este es el constructor por defecto
    // inicia el arreglo de respuestas de manera
    // aleatoria
    public Cromosoma() {
        respuestas = new int[416];

        for (int i = 0; i < 416; i++) {
            // Se genera un numero aleatorio entre 1-3
            respuestas[i] = (int) Math.round((Math.random() * 2)) + 1;
        }
    }

    // Crea una copia de otro cromosoma
    public Cromosoma(Cromosoma aCopiar) {
        respuestas = new int[416];

        for (int i = 0; i < 416; i++) {
            respuestas[i] = aCopiar.respuestas[i];
        }
    }

    public int getRespuesta(String estado, int direccion) {
        int posicion;
        String estadoAux;

        if (estado.length() == 8) {
            posicion = convierteBinarioDecimal(estado);

            return respuestas[posicion];

        } else {
            // Si el string tiene longitud mayor a 8 trae -1
            // la hormiga esta en un borde del tablero

            if (estado.length() == 13) {
                // La hormiga se encuentra en una esquina

                if (estado.substring(0, 8).equals("-1-1-1-1")) {
                    // Esquina superior derecha 
                    // -1-1-1-1***-1
                    estadoAux = estado.substring(8, 11);
                    posicion = convierteBinarioDecimal(estadoAux) + 256;

                    return respuestas[posicion];

                } else if (estado.substring(1, 11).equals("-1-1-1-1-1")) {
                    // Esquina inferior derecha
                    //*-1-1-1-1-1**

                    estadoAux = estado.substring(0, 1);
                    estadoAux += estado.substring(11, 13);
                    posicion = convierteBinarioDecimal(estadoAux) + 264;

                    return respuestas[posicion];

                } else if (estado.substring(3, 13).equals("-1-1-1-1-1")) {
                    // Esquina inferior izquierda
                    // ***-1-1-1-1-1

                    estadoAux = estado.substring(0, 3);
                    posicion = convierteBinarioDecimal(estadoAux) + 272;

                    return respuestas[posicion];
                } else {
                    // Esquina superior izquierda
                    // -1-1***-1-1-1

                    estadoAux = estado.substring(4, 7);
                    posicion = convierteBinarioDecimal(estadoAux) + 280;

                    return respuestas[posicion];

                }

            } else if (estado.length() == 11) {
                if (estado.substring(3, 9).equals("-1-1-1")) {
                    // Borde inferior
                    // ***-1-1-1***

                    estadoAux = estado.substring(0, 3);
                    estadoAux += estado.substring(9, 11);
                    posicion = convierteBinarioDecimal(estadoAux) + 288;

                    return respuestas[posicion];

                } else if (estado.substring(1, 7).equals("-1-1-1")) {
                    // Borde derecho
                    // *-1-1-1****

                    estadoAux = estado.substring(0, 1);
                    estadoAux += estado.substring(7, 11);
                    posicion = convierteBinarioDecimal(estadoAux) + 320;

                    return respuestas[posicion];

                } else if (estado.substring(5, 11).equals("-1-1-1")) {
                    // Borde izquierdo
                    // *****-1-1-1

                    estadoAux = estado.substring(0, 5);
                    posicion = convierteBinarioDecimal(estadoAux) + 352;

                    return respuestas[posicion];

                } else {
                    //Borde superior
                    // -1-1*****-1

                    estadoAux = estado.substring(4, 9);
                    posicion = convierteBinarioDecimal(estadoAux) + 384;

                    return respuestas[posicion];

                }
            }

        }

        return -1;
    }

    public void setRespuesta(int i, int dato) {
        this.respuestas[i] = dato;
       // System.out.println("cambia "+respuestas[i]+" por "+dato);
    }

    public int getRespuesta(int i) {
        return this.respuestas[i];
    }

    public int convierteBinarioDecimal(String binario) {
        int numero;

        // Pasamos el estado de cadena binaria a decimal
        binario = Integer.toString(Integer.parseInt(binario, 2), 10);
        numero = Integer.parseInt(binario);

        return numero;
    }

    public String toString() {
        String res="Cromosoma ";
        for(int i =0;i<respuestas.length;i++){
        res+=respuestas[i];
        }
        return res;
    }
}
