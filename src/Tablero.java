import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Tablero{
	
	private int tamano;
	private int[][] mundo;
	private int alimento;
	
	public Tablero(int size) {
		tamano = size;
		mundo = new int[tamano][tamano];
	}
	
	public Tablero(int size, int[][] _mundo){
		tamano = size;
		mundo = new int[size][size];
		for(int i=0; i < size; i++){
			for(int j=0; j < size; j++){
				mundo[i][j] = _mundo[i][j];
			}
		}
	}
	
	public Tablero(String _mundo){
		tamano = tamanoMundo(_mundo);
		mundo = new int[tamano][tamano];
				
		lecturaArchivo("File.in");
		
	}
	
	public void lecturaArchivo(String file){
		String text=""; 
		char[] aux;
		
		File Abrir = new File(file);
        if (Abrir != null) {
            FileReader Fichero = null;
            try {
                Fichero = new FileReader(Abrir);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedReader leer = new BufferedReader(Fichero);

            try {

            	for (int i = 0; ((text = leer.readLine()) != null); i++) {
                	text = text.replace(" ", "");
                	aux = text.toCharArray();
                	
                    for (int j = 0; j < getTamano(); j++) {
                    	getMundo()[i][j] = (int) aux[j] - 48;
                    	if(getMundo()[i][j] == 1){	setAlimento(getAlimento() + 1);	}
                    }                    
                }
            	leer.close();
            	
            } catch (IOException ex) {
                Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return ;
	}
	
	
	public int tamanoMundo(String file){
		char[] aux;
		String text="";
		File Abrir = new File(file);
        if (Abrir != null) {
            FileReader Fichero = null;
            try {
                Fichero = new FileReader(Abrir);
            
                BufferedReader leer = new BufferedReader(Fichero);

            	if((text = leer.readLine()) != null){
                	text = text.replace(" ", "");
                	aux = text.toCharArray();
                	leer.close();
                	
                	return aux.length;
                }else{
                	leer.close();
                	return -1;
                }
            }
            catch (Exception ex) {
                Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
		return -1;
	}
	
	/*
	 * 	SetMundo se usa para cargar el mundo 
	 *  pasando como parametros una matriz con
	 *  los valores a almacenar
	 *  
	 * */
	
	public void setMundo(int[][] _mundo){
		setMundo(_mundo);
	}
	
	/*
	 *  getArea recibe como parámetros una coordenada x, una coordenada y
	 *  y devuelve una cuadricula de 3x3 con los valores del mundo centrada
	 *  en la posición (x,y). En caso de estar en un borde los valores no 
	 *  existentes se devuelven como -1
	 * 
	*/
	
	public int[][] getArea(int x, int y){
		int[][] area = new int[3][3];
		
		if(x== 0){
					
		}
		
		for(int i = -1; i< 2; i++){
			for(int j=-1; j< 2; j++){
				// Si la hormiga se encuentra en un borde de la cuadricula
				// guardamos un -1 en el area no existente
				
				if( (x==0 && i== -1) || (x== getTamano() -1 && i== 1) || 
					(y==0 && j== -1) || (y== getTamano() -1 && j== 1)   ){
					
					// Debemos revisar que pasa en los bordes
					area[i+1][j+1]= -1;
					//area[i+1][j+1]= 0;
				}else{
										
					area[i+1][j+1]= getMundo()[x+i][y+j];
				}
			}			
		}
		
		return area;
	}
	
	public int getEstadoCasilla(int x, int y){
		return getMundo()[y][x];
	}
	
	public void setEstadoCasilla(int x, int y, int estado){
		getMundo()[y][x] = estado;
	}
	
	public int getTamanoMundo(){
		return getTamano();
	}
	
	public void muestraMundo(){
		for(int i= 0; i < getTamano(); i++){
			for(int j=0; j<getTamano(); j++){
				System.out.print(getMundo()[i][j] + " ");
			}
			System.out.println();
		}		
	}

    /**
     * @return the tamano
     */
    public int getTamano() {
        return tamano;
    }

    /**
     * @param tamano the tamano to set
     */
    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    /**
     * @return the mundo
     */
    public int[][] getMundo() {
        return mundo;
    }

    
    /**
     * @return the alimento
     */
    public int getAlimento() {
        return alimento;
    }

    /**
     * @param alimento the alimento to set
     */
    public void setAlimento(int alimento) {
        this.alimento = alimento;
    }
}