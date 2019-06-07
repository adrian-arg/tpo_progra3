package cmc;
import java.awt.Color;
/**
 * Obtiene la lista de los puntos marcados en la matriz (mapa)
 * Itera los mismos de la siguiente forma:
 * Obtiene los 2 primeros y expande los contiguos entre ambos.
 * Primero expande eje x, segundo expande el eje y.
 * Reitera la lista expandiendo el siguiente (siempre expandiendo de a pares)
 * El recorrido es secuencial (conforme al orden de marcado de los puntos en el mapa)
 * Invoca la m�todo dibujar en cada iteraci�n.
 * Al finalizar la iteraci�n expande los contiguos entre el �ltimo y el primero de la lista.
 * Vuelve a Invocar la m�todo dibujar para cerrar el ciclo.
 * No contempla las densidades definidas en la matriz (mapa)
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import graficos.Area;
import graficos.Punto;
import mapa.MapaInfo;

public class CmcDemoTPO {
	private MapaInfo mapa;
	private CmcImple cmc;
	
	public CmcDemoTPO(MapaInfo mapa, CmcImple cmc) {
		this.mapa = mapa;
		this.cmc = cmc;
		demoObtenerCamino();
	}
	
	private void demoObtenerCamino() {
		
		/* Se obtienen los puntos origen y destino */
		Punto origen = mapa.getPuntos().get(0), destino = mapa.getPuntos().get(1);
		
		/* Se obtiene un mapa de las densidades */
		int[][] densidades = obtenerDensidades(mapa);
		
		/* Densidad maxima es infranqueable */
		int infranqueable = mapa.MAX_DENSIDAD;
		
		
		/* ***Adrian*** 
			Hay que hacer una Clase que contenga Punto(o coordenadas), el predecesor, 
				que además tenga el costo relativo (ver calculo), y peso acumulado 
					(o solo parcial), que implemente interfaz comparable para poder 
						usar en la cola de prioridad, de esa manera se ordena sola
			
			La dificultad que hay que pensar es como voy haciendo el cambio de los 
				puntos expandidos que son de menor costo (en cola prioridad o matriz?)
					En la matriz habría que guardar los definitivos
						Tener una lista de los ya utilizados? Para no volver a 
							expandirlos.. Puntos Abiertos (cola prioridad) y 
								cerrados (diccionario?)
			
			Cola Prioridad - https://www.redeszone.net/2012/04/02/curso-de-java-colas-de-prioridad/
				PriorityQueue<E> colaPrioridad = new PriorityQueue<>();
			
			Calculo de Horizontal y Vertical - Peso es (1) Distancia es la Diferencia de 
				Valor absoluto entre ejes de Puntos origen y destino
			Calculo de Diagonal - Peso es (1,41) , siempre y cuando no sea una esquina 
				(consultar por valor de arriba y abajo o algo asi
			
			El recorrido se forma exclusivamente con la fila prioridad y se van guardando en una matriz? 
		*/
		
		PriorityQueue<PuntoCandidato> colaPrioridad = new PriorityQueue<>();
		
			
		Iterator<Punto> iter = mapa.getPuntos().iterator();
		List<Punto> listaPuntos = null;
		int minimo = Integer.MAX_VALUE;
		if (iter.hasNext()) {
			origen = iter.next();
		
			while(iter.hasNext()) {
				destino = iter.next();
				List<Punto> aux = expandirPuntosContiguos(origen, destino);
				if(aux.size() < minimo) {
					minimo = aux.size();
					listaPuntos = aux;
				}
			}
			cmc.dibujarCamino(listaPuntos,Color.red);
			mapa.enviarMensaje("Camino minimo: " + listaPuntos.size() + " puntos");
		}
	}
	

	private List<Punto> expandirPuntosContiguos(Punto a, Punto b) {
		List<Punto> listaPuntos = new ArrayList<Punto>();
		if (a.x < b.x) {
			for(int x = a.x ; x < b.x; x++) {
				listaPuntos.add(new Punto(x, a.y));
			}
		} else {
			for(int x = a.x ; x > b.x; x--) {
				listaPuntos.add(new Punto(x, a.y));
			}
		}
		if (a.y < b.y) {
			for(int y = a.y ; y < b.y; y++) {
				listaPuntos.add(new Punto(b.x, y));
			}
		} else {
			for(int y = a.y ; y > b.y; y--) {
				listaPuntos.add(new Punto(b.x, y));
			}
		}
		cmc.dibujarCamino(listaPuntos);
		return listaPuntos;
	}
		
	private int[][] obtenerDensidades(MapaInfo mapa){
		int[][] densidades = new int[mapa.ALTO][mapa.LARGO];
		for(int i = 0; i< mapa.ALTO; i++) {
			Arrays.fill(densidades[i], 1);
		}
		List<Area> areas = mapa.getAreas();
		for(Area a: areas) {
			int[] coordenadas = a.getCoordenadas();
			int x_ini = coordenadas[1];
			int y_ini = coordenadas[0];
			int x_fin = coordenadas[3];
			int y_fin = coordenadas[2];
			System.out.println("X inicio " + x_ini);
			System.out.println("Y inicio " + y_ini);
			System.out.println("X fin " + x_fin);
			System.out.println("Y fin " + y_fin);
			
			/* EL MAPA NO TOMA EL ULTIMO PUNTO MARCADO SINO (X-1,Y-1) */
			for(int x=x_ini; x < x_fin; x++) {
				for(int y=y_ini; y < y_fin; y++) {
					System.out.println("Densidad "+x+","+y+": "+densidades[x][y]);
					System.out.println("Mapa "+x+","+y+": "+mapa.getDensidad(x,y));
					if(mapa.getDensidad(x, y) > densidades[x][y]){
						densidades[x][y] = mapa.getDensidad(x,y);
						System.out.println("Nueva Densidad "+x+","+y+": "+densidades[x][y]);
					}
				}
			}
			
		}
		return densidades;
	}
	
}
