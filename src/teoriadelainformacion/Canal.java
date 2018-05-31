package teoriadelainformacion;

import java.util.List;

public class Canal<TX extends Comparable<TX>,TY extends Comparable<TY>> {

	DistProbCondicional<TX,TY> distribucion;
	
	public Canal( DistProbCondicional<TX,TY> distribucion) {
		this.distribucion = distribucion;
	}
	
	public float getRuido() {
		float entropiaYdadoX = 0;
		float sumatoria=0;
		for (int fila=0 ; fila < distribucion.getDistX().getNEventos(); fila++ ) {
			for ( int col = 0 ; col < distribucion.getDistY().getNEventos(); col++) {
				sumatoria+=distribucion.getProbCondYdadoX(fila, col)* ( Math.log(distribucion.getProbCondYdadoX(fila, col)) / Math.log(2) );
			}
			entropiaYdadoX*=-distribucion.getDistX().getProb(fila)*sumatoria;
			sumatoria=0;
		}
			
		return entropiaYdadoX;
	}
	
	public float getPerdida() {
		float entropiaXdadoY = 0;
		float sumatoria=0;
		for (int col=0 ; col < distribucion.getDistY().getNEventos(); col++ ) {
			for ( int fila = 0 ; fila < distribucion.getDistX().getNEventos(); fila++) {
				sumatoria+=distribucion.getProbCondXdadoY(fila, col)* ( Math.log(distribucion.getProbCondXdadoY(fila, col)) / Math.log(2) );
			}
			entropiaXdadoY*=-distribucion.getDistY().getProb(col)*sumatoria;
			sumatoria=0;
		}
			
		return entropiaXdadoY;
	}
	
	public float getInformacionMutua() {	
		return distribucion.getDistX().getEntropia() - getPerdida();
	}
	
	public List<TY> transmitir(List<TX> mensaje){
		
	}
	
	//Transmite hasta que converga
	public List<TY> transmitir(GeneradorMontecarlo<TX> generador){
		
	}
	
}
