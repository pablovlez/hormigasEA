
import java.util.ArrayList;

public class Hormiga{

	final int NORTE = 1;
	final int ESTE = 2;
	final int SUR = 3;
	final int OESTE = 4;
	
	final int DERECHA = 1;
	final int IZQUIERDA = -1;
	private Tablero mundoFijo;
	private Tablero mundo;
	private Cromosoma acciones;
	private int x;
        private int y;
        private int direccion;
        private int desempeno;
        private int movimientos;        
	
	public Hormiga(int _movimientos, Tablero tablero){
		desempeno = 0;
		movimientos = _movimientos;
		x= 0;
		y= 0;
		direccion = NORTE;
		mundoFijo = new Tablero(tablero.getTamanoMundo(), tablero.getMundo());
		mundo = new Tablero(tablero.getTamanoMundo(), tablero.getMundo());
		acciones = new Cromosoma();
	}
	
	public Hormiga(int _movimientos, Tablero tablero, Cromosoma crm){
		desempeno = 0;
		movimientos = _movimientos;
		x= 0;
		y= 0;
		direccion = NORTE;
		mundoFijo = new Tablero(tablero.getTamanoMundo(), tablero.getMundo());
		mundo = new Tablero(tablero.getTamanoMundo(), tablero.getMundo());
		acciones = new Cromosoma(crm);
	}
	
	
	public void vivir2(){
		//muestraEstado();
		while(getMovimientos() > 0){
			mover();	
			//muestraEstado();
		}		
	}
	
	public void mover(){
		String estado;
		int respuesta;
		int[][] area = getMundo().getArea(getX(), getY());
		
		if(getDireccion() != NORTE){
			if(getDireccion() == ESTE){
				area = girarDerecha(area);
			}else if(getDireccion() == OESTE){
				area = girarIzquierda(area);
			}else if(getDireccion() == SUR){
				area = girarDerecha(area);
				area = girarDerecha(area);
			}			
		}
		
		estado = estadoArea(area);
		respuesta= getAcciones().getRespuesta(estado, getDireccion());
		
		if(respuesta == getAcciones().AVANZAR){                    
                    avanza();
                        
			//setDesempeno(getDesempeno() + 1);
		}else if(respuesta == getAcciones().GIRAR_DERECHA){
			cambiaDireccion(DERECHA);
		}else if(respuesta == getAcciones().GIRAR_IZQUIERDA){
			cambiaDireccion(IZQUIERDA);
		}
		
		if(getMundo().getEstadoCasilla(getX(), getY()) == 1){
			comer();                        
			setDesempeno(getDesempeno() + 2);
		}
		setMovimientos(getMovimientos() - 1);
		//muestraEstado();
	}
	

	/*
	 *  comer()
	 *	Esta funcion pone en cero la casilla donde 
	 *	se ubique la hormiga en el momento de invocarla
	 * 
	 */
	
	public void comer(){
		getMundo().setEstadoCasilla(getX(), getY(), 0);
	}
	
	public void muestraEstado(){
		String estado = "x: " + getX() + "y: " + getY() + ", dir: ";
		if(getDireccion() == NORTE) estado += "Norte";
		else if(getDireccion() == ESTE) estado += "Este";
		else if(getDireccion() == SUR) estado += "Sur";
		else if(getDireccion() == OESTE) estado += "Oeste";
		
		estado += ", movs: " + getMovimientos();		
		System.out.println(estado);
	}
        
       
	
	/*
	 * 	Esta función recibe una matriz de 3x3 y devuelve una cadena
	 *  de 8 caracteres 0 o 1 que indica el estado de cada
	 *  una de las casillas exceptuando la del centro 
	 * 
	 */
	
	static public String estadoArea(int[][] area){
		String estado = "";
		
		//casilla arriba
		estado += Integer.toString(area[0][1]);
		
		//casilla arriba derecha
		estado += Integer.toString(area[0][2]);
		
		//casilla derecha
		estado += Integer.toString(area[1][2]);
		
		//casilla derecha abajo
		estado += Integer.toString(area[2][2]);
		
		//casilla abajo
		estado += Integer.toString(area[2][1]);
		
		//casilla abajo izquierda
		estado += Integer.toString(area[2][0]);
		
		//casilla izquierda
		estado += Integer.toString(area[1][0]);
		
		//casilla arriba izquierda
		estado += Integer.toString(area[0][0]);
		
		return estado;
	}
	
	public void cambiaDireccion(int sentidoGiro){
		
		if(sentidoGiro == IZQUIERDA && getDireccion() == NORTE){
			setDireccion(OESTE);
			
		}else if(sentidoGiro == DERECHA && getDireccion() == OESTE){
			setDireccion(NORTE);
			
		}else{
			setDireccion(getDireccion() + sentidoGiro);			
		}
		
	}
	
	public void avanza(){
		// Temporalmente vamos a tomar acciones si debe avanzar
		// y se sale de la casilla, debemos preguntar que se debe 
		// hacer
		
		if(getDireccion() == NORTE){
			if(getY() > 0) setY(getY() - 1);
			else cambiaDireccion(DERECHA);
		}else if(getDireccion() == ESTE){
			if(getX() < getMundo().getTamanoMundo() -1) setX(getX() + 1);
			else cambiaDireccion(IZQUIERDA);
		}else if(getDireccion() == OESTE){
			if(getX() > 0)setX(getX() - 1);
			else cambiaDireccion(DERECHA);
		}else if(getDireccion() == SUR){
			if(getY() < getMundo().getTamanoMundo() -1)setY(getY() + 1);
			else cambiaDireccion(IZQUIERDA);
		}
		
	}
	
	// Esta funcion gira la matriz 90 grados a la derecha
	public static int[][] girarDerecha(int[][] area){
		int n=0;
		int size = area.length;
		int[][] areaAux = new int[size][size];
		
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				areaAux[j][size - i -1]= area[i][j];
				
			}
			n++;
		}		
		
		return areaAux;
	}
	
	// Esta funcion gira la matriz 90 grados a la izquierda
	public static int[][] girarIzquierda(int[][] area){
		int n=0;
		int size = area.length;
		int[][] areaAux = new int[size][size];
		
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				areaAux[i][j]= area[j][size - i -1];
				
			}
			n++;
		}		
		
		return areaAux;
	}
	
	public int getDesempeno(){
		return desempeno;
	}
	
	public void muestraMundo(){
		getMundo().muestraMundo();
	}
	
	public void muestraDesempeno(){
		System.out.println("Desempeno: " + getDesempeno());
	}
	
	public void reestableceDesempeno(){
		setDesempeno(0);
	}
		
	public void reestableceMundo(Tablero espacio){
		setMundo(new Tablero(espacio.getTamanoMundo(), espacio.getMundo()));
	}
	
	public void reestableceMovimientos(int i){
		setMovimientos(i);
	}
	
	public void reestablecePosicion(){
		setX(0);
		setY(0);
		setDireccion(NORTE);
	}
		

    /**
     * @return the mundoFijo
     */
    public Tablero getMundoFijo() {
        return mundoFijo;
    }

    /**
     * @param mundoFijo the mundoFijo to set
     */
    public void setMundoFijo(Tablero mundoFijo) {
        this.mundoFijo = mundoFijo;
    }

    /**
     * @return the mundo
     */
    public Tablero getMundo() {
        return mundo;
    }

    /**
     * @param mundo the mundo to set
     */
    public void setMundo(Tablero mundo) {
        this.mundo = mundo;
    }

    /**
     * @return the acciones
     */
    public Cromosoma getAcciones() {
        return acciones;
    }

    /**
     * @param acciones the acciones to set
     */
    public void setAcciones(Cromosoma acciones) {
        this.acciones = acciones;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the direccion
     */
    public int getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    /**
     * @param desempeno the desempeno to set
     */
    public void setDesempeno(int desempeno) {
        this.desempeno = desempeno;
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
