package cmc;

import graficos.Punto;

public class PuntoCandidato implements Comparable{
	
	/**
	 * setAbierto
	 * @param abierto utiliza false para dejar el Punto cerrado y true como abierto
	 */
	
	private double costoDistancia;
	private double costoPeso;
	private double costoTotal;
	private int x, y;
	private boolean abierto; //Abierto == true , Cerrado == false
	private Punto punto;
	private PuntoCandidato predecesor;
	
	public PuntoCandidato(int costoPeso, int x, int y, 
			PuntoCandidato predecesor, Punto destino) {
		this.costoDistancia = calcularDistanciaADestino(destino);
		this.costoPeso = costoPeso;
		this.costoTotal = calcularCostoTotal();
		/* PuntoCandidato */
		this.x = x;
		this.y = y;
		this.punto = new Punto(x,y);
		/* PuntoCandidato */
		this.predecesor = predecesor;
		this.abierto = true;
	}
	
	private double calcularDistanciaADestino(Punto destino) {
		return Math.abs(destino.getX() - this.x) + Math.abs(destino.getY() - this.y); 
	}

	public double getCostoTotal() {
		return costoTotal;
	}
	
	public double calcularCostoTotal() {
		return this.costoDistancia + this.costoPeso;
	}
	
	public boolean isAbierto() {
		return abierto;
	}

 
	public void setAbierto(boolean abierto) {
		this.abierto = abierto;
	}

	@Override
	public int compareTo(Object otroPunto) {
		double diferencia = this.costoTotal - ((PuntoCandidato) otroPunto).getCostoTotal();
		return (int)diferencia;
	}

}
