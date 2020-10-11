
public class AlgoritmoGenetico {

    private Hormiga[] poblacion;
    private Hormiga[] mattingPool;
    private Hormiga[] hijos;
    private Tablero espacio;
    private double[] aptitud;
    private int mejorDesemp;
    private int mejorIndividuo;
    private int meta;
    private int movimientos;

    public AlgoritmoGenetico(int cant_poblacion, int[][] mundo) {

        mattingPool = new Hormiga[25];
        hijos = new Hormiga[24];
        espacio = new Tablero(6, mundo);

        poblacion = new Hormiga[cant_poblacion];
        aptitud = new double[cant_poblacion];
        creaPoblacion(cant_poblacion);
    }

    public AlgoritmoGenetico(int cant_poblacion, int _movimientos, String mundo) {

        mattingPool = new Hormiga[25];
        hijos = new Hormiga[25];
        espacio = new Tablero(mundo);
        meta = espacio.getAlimento();
        movimientos = _movimientos;
        poblacion = new Hormiga[cant_poblacion];
        aptitud = new double[cant_poblacion];
        creaPoblacion(cant_poblacion);
        mejorIndividuo = 0;
    }

    public void creaPoblacion(int n) {
        for (int i = 0; i < n; i++) {
            getPoblacion()[i] = new Hormiga(getMovimientos(), getEspacio());
        }
    }

    public void evolucion() {
        /*
         * for(int i=0; i< poblacion.length; i++){ for(int j=0; j <
         * poblacion[i].acciones.respuestas.length; j++){
         * System.out.print(poblacion[i].acciones.respuestas[j] + " "); }
         * System.out.println();
		}
         */


        evaluacion();
        seleccion();

        reproduccion();
        reemplazo();
        reestableceValoresPoblacion();

    }

    public void evaluacion() {
        int mejor = 0, aux = 0;

        for (int j = 0; j < getPoblacion().length; j++) {
            //System.out.println("Individuo " + j);
            aux = evalua(getPoblacion()[j]);

            if (aux > mejor) {
                mejor = aux;
                setMejorIndividuo(j);
            }

        }

        setMejorDesemp(mejor);
        calculaAptitud();
    }

    public void calculaAptitud() {
        double max = 0;
        // Calculamos el maximo esperado;

        for (int i = 0; i < getPoblacion().length; i++) {
            max += getPoblacion()[i].getDesempeno();
        }

        // Normalizamos los valores obtenidos
        for (int i = 0; i < getPoblacion().length; i++) {
            if (i == 0) {
                getAptitud()[i] = getPoblacion()[i].getDesempeno() / max;
            } else {
                getAptitud()[i] = (getPoblacion()[i].getDesempeno() / max) + getAptitud()[i - 1];
            }


        }

    }

    public void seleccion() {
        double valor;
        for (int i = 0; i < getMattingPool().length; i++) {
            valor = Math.random();

            for (int j = 0; j < getAptitud().length; j++) {

                if (j == 0) {
                    if (valor < getAptitud()[j]) {
                        getMattingPool()[i] = getPoblacion()[j];
                        break;
                    }
                } else {
                    if (valor >= getAptitud()[j - 1] && valor < getAptitud()[j]) {
                        getMattingPool()[i] = getPoblacion()[j];
                        break;
                    }
                }
            }
        }
    }

    // Vamos a realizar seleccion por torneo
    public void seleccionPorTorneo() {
        Hormiga[] participantes = new Hormiga[3];
        int rand;
        int mayor = 0;
        int mejor = 0;

        for (int j = 0; j < 25; j++) {

            // Seleccionamos 3 hormigas aleatoriamente
            for (int i = 0; i < 3; i++) {
                rand = (int) Math.round(Math.random() * (getPoblacion().length - 1));
                participantes[i] = getPoblacion()[rand];

                if (participantes[i].getDesempeno() > mayor) {
                    mayor = participantes[i].getDesempeno();
                    mejor = i;
                }
            }

            getMattingPool()[j] = participantes[mejor];
        }
    }

    public void reproduccion() {
        int ind1 = (int) Math.round(Math.random() * 24);
        int ind2 = (int) Math.round(Math.random() * 24);
        Hormiga nuevos[] = new Hormiga[2];
        // Con una posibilidad de cruce del 60% se cruzan dos
        // individuos aleatorios

        for (int i = 0; i < 23; i = i + 2) {
            // Se eligen 2 individuos aleatorios			

            if (Math.random() < 0.6) {
                nuevos = cruce(getIndiMattingPool(ind1), getIndiMattingPool(ind2));

                if (Math.random() < 0.05) {
                    nuevos[0] = mutacion(nuevos[0]);
                }

                if (Math.random() < 0.05) {
                    nuevos[1] = mutacion(nuevos[1]);
                }

                //agregamos las nuevas hormigas al arreglo de hijos generados, los cuales 
                //reemplazaran a algunas hormigas de la poblacion inicial.
                setHijo(i, nuevos[0]);
                setHijo(i, nuevos[1]);
                
                //System.out.println(nuevos[0].getAcciones().toString());

            } else {
                setHijo(i, getIndiMattingPool(ind1));
                setHijo(i+1,getIndiMattingPool(ind2));
            }

            ind1 = (int) Math.round(Math.random() * 24);
            ind2 = (int) Math.round(Math.random() * 24);
        }
    }
    
    public Hormiga getIndiMattingPool(int i){
        return this.mattingPool[i];
    }
    
    public void setHijo(int i ,Hormiga ant){
        this.hijos[i]=ant;
    }
    

    public Hormiga[] cruce(Hormiga ind1, Hormiga ind2) {
        //creamos un arreglo de Hormigas que contiene las 
        //dos nuevas hormigas generadas por el cruce de genes de los padres
        Hormiga h[] = new Hormiga[2];
        Cromosoma aux1 = new Cromosoma(ind1.getAcciones());
        Cromosoma aux2 = new Cromosoma(ind2.getAcciones());

        // punto de cruce, 207, la mitad del cromosoma
        //para mas variedad se podria generar un punto de cruce aleatorio
        int point = (int) Math.round(Math.random() * 415);
        int mid= 415-point;
        for (int i = 0; i < mid; i++) {

            ind1.getAcciones().setRespuesta(i, aux2.getRespuesta(i));
            ind2.getAcciones().setRespuesta(i, aux1.getRespuesta(i));

        }
        for (int i = mid; i <= 415; i++) {

            ind1.getAcciones().setRespuesta(i, aux1.getRespuesta(i));
            ind2.getAcciones().setRespuesta(i, aux2.getRespuesta(i));

        }
        h[0] = new Hormiga(getMovimientos(), getEspacio(), ind1.getAcciones());
        h[1] = new Hormiga(getMovimientos(), getEspacio(), ind2.getAcciones());
        return h;
    }

    public Hormiga mutacion(Hormiga mutante) {
        int gen;

        // Generamos un número aleatorio entre 0 y 415 para
        // seleccionar el gen a mutar

        gen = (int) Math.round(Math.random() * 415);

        // Asignamos un valor aleatorio en esa posicion
        int valor=(int) Math.round(Math.random() * 2) + 1;
        mutante.getAcciones().setRespuesta(gen,valor);
        //System.out.println("muta "+ mutante.getAcciones().getRespuesta(gen)+" por " +valor);
        
        //devolvemos la hormiga mutada
        return mutante;

    }

    public void reemplazo() {
        int salado, hijo = 0;
        // Por ahora se elige aleatoriamente al que va a ser
        // reemplazado

        if (getMejorDesemp() > 2) {
            for (int i = 0; i < getHijos().length; i++) {
                salado = (int) Math.round(Math.random() * (getPoblacion().length - 1));
                if (getPoblacion()[salado].getDesempeno() < (getMejorDesemp() * 0.8)) {
                    if (getHijos()[hijo] != null) {
                        getPoblacion()[salado] = new Hormiga( getMovimientos(), getHijos()[hijo].getMundo(), getHijos()[hijo].getAcciones());
                    }

                    hijo++;

                    if (hijo > 24) {
                        break;
                    }
                }
            }
        } else {

            for (int i = 0; i < getHijos().length; i++) {
                salado = (int) Math.round(Math.random() * (getPoblacion().length - 1));
                if (getHijos()[i] != null && getAptitud()[salado] < 0.5) {
                    getPoblacion()[salado] = new Hormiga( getMovimientos(), getHijos()[i].getMundo(), getHijos()[i].getAcciones());
                };
            }
        }

    }

    public int evalua(Hormiga ant) {
        ant.vivir2();
        return ant.getDesempeno();
    }

    public void reestableceValoresPoblacion() {

        for (int i = 0; i < getPoblacion().length; i++) {
            getPoblacion()[i].reestableceDesempeno();
            getPoblacion()[i].reestableceMundo(getEspacio());
            getPoblacion()[i].reestableceMovimientos(getMovimientos());
            getPoblacion()[i].reestablecePosicion();
        }
    }

    /**
     * @return the poblacion
     */
    public Hormiga[] getPoblacion() {
        return poblacion;
    }

    /**
     * @param poblacion the poblacion to set
     */
    public void setPoblacion(Hormiga[] poblacion) {
        this.poblacion = poblacion;
    }

    /**
     * @return the mattingPool
     */
    public Hormiga[] getMattingPool() {
        return mattingPool;
    }

    /**
     * @param mattingPool the mattingPool to set
     */
    public void setMattingPool(Hormiga[] mattingPool) {
        this.mattingPool = mattingPool;
    }

    /**
     * @return the hijos
     */
    public Hormiga[] getHijos() {
        return hijos;
    }

    /**
     * @param hijos the hijos to set
     */
    public void setHijos(Hormiga[] hijos) {
        this.hijos = hijos;
    }

    /**
     * @return the espacio
     */
    public Tablero getEspacio() {
        return espacio;
    }

    /**
     * @param espacio the espacio to set
     */
    public void setEspacio(Tablero espacio) {
        this.espacio = espacio;
    }

    /**
     * @return the aptitud
     */
    public double[] getAptitud() {
        return aptitud;
    }

    /**
     * @param aptitud the aptitud to set
     */
    public void setAptitud(double[] aptitud) {
        this.aptitud = aptitud;
    }

    /**
     * @return the mejorDesemp
     */
    public int getMejorDesemp() {
        return mejorDesemp;
    }

    /**
     * @param mejorDesemp the mejorDesemp to set
     */
    public void setMejorDesemp(int mejorDesemp) {
        this.mejorDesemp = mejorDesemp;
    }

    /**
     * @return the mejorIndividuo
     */
    public int getMejorIndividuo() {
        return mejorIndividuo;
    }

    /**
     * @param mejorIndividuo the mejorIndividuo to set
     */
    public void setMejorIndividuo(int mejorIndividuo) {
        this.mejorIndividuo = mejorIndividuo;
    }

    /**
     * @return the meta
     */
    public int getMeta() {
        return meta;
    }

    /**
     * @param meta the meta to set
     */
    public void setMeta(int meta) {
        this.meta = meta;
    }

    /**
     * @return the movimientos
     */
    public int getMovimientos() {
        return movimientos;
    }

    /**
     * @param movimientos the movimientos to set
     */
    public void setMovimientos(int movimientos) {
        this.movimientos = movimientos;
    }
}
