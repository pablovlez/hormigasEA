
public class Main {
	
	public static void main(String[] args) {
		String mundoF = "File.in";
		int[][] mundo = {
				{1, 1, 0, 0, 0, 0},
				{0, 1, 1, 1, 0, 0},
				{0, 0, 1, 0, 1, 0},
				{0, 0, 1, 0, 1, 0},
				{0, 1, 1, 1, 0, 0},
				{0, 0, 1, 0, 0, 0},				
			};
		
		AlgoritmoGenetico ag = new AlgoritmoGenetico(100, 100, mundoF);
		
		int generacion=1;
		int desempAnte= 0;
		String res="";
		//System.out.println((ag.meta * 0.8));
		//ag.evolucion();
		
		//evolucionamos hasta que el desempe�o de la poblacion sea mayor a 10 o al que deseemos
		while(desempAnte < (ag.getMeta() * 0.8)){
		//while(ag.getMejorDesemp() <= 100000){
			
			ag.evolucion();
			System.out.println("Generacion "+ generacion + ", best desemp: " + ag.getMejorDesemp());
			
			generacion++;
			if(ag.getMejorDesemp() > desempAnte){
				res+="Cambio de desempeno en it "+generacion+ " Desempeno "+ag.getMejorDesemp()+"\n";
			}
			desempAnte=ag.getMejorDesemp();
			
		}
		
		System.out.println("Generaciones "+ generacion);
		System.out.println("Mundo Inicial");
		ag.getEspacio().muestraMundo();		               
		System.out.println(res);
	}

}
