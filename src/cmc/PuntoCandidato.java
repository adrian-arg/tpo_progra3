package cmc;

import graficos.Punto;

public class PuntoCandidato {
	
	private int costoDistancia;
	private int costoPeso;
	private int costoTotal;
	private int x, y;
	private Punto punto;
	private Punto predecesor;
	private boolean abierto; //Abierto == true , Cerrado == false
	
	public PuntoCandidato(int costoDistancia, int costoPeso, int x, int y, 
			Punto predecesor) {
		this.costoDistancia = costoDistancia;
		this.costoPeso = costoPeso;
		this.costoTotal = costoTotal;
		this.x = x;
		this.y = y;
		this.punto = punto;
		this.predecesor = predecesor;
		this.abierto = abierto;
	}
	
	
	
	
	
	
	
}
