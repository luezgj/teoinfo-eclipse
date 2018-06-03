package teoriadelainformacion;

import java.util.List;

public class Canal<TX extends Comparable<TX>,TY extends Comparable<TY>> {

	static final double CONSTANTE_CONVERGENCIA = 0.01;
	
	DistProbCondicional<TX,TY> distribucion;
	
	public Canal( DistProbCondicional<TX,TY> distribucion) {
		this.distribucion = distribucion;
	}
	
	public double getRuido() {
		double entropiaYdadoX = 0;
		double sumatoria=0;
		double probCond;
		double LogBase2;
		for (int fila=0 ; fila < distribucion.getDistX().getNEventos(); fila++ ) {
			for ( int col = 0 ; col < distribucion.getDistY().getNEventos(); col++) {
				probCond=distribucion.getProbCondYdadoX(fila, col);
				if (probCond != 0) {
					LogBase2=Math.log(probCond) / Math.log(2) ;
					sumatoria+=probCond * LogBase2;
				}
			}
			entropiaYdadoX+=-distribucion.getDistX().getProb(fila)*sumatoria;
			sumatoria=0;
		}
			
		return entropiaYdadoX;
	}
	
	public double getPerdida() {
		double entropiaXdadoY = 0;
		double sumatoria=0;
		double probCond;
		double LogBase2;
		
		for (int col=0 ; col < distribucion.getDistY().getNEventos(); col++ ) {
			for ( int fila = 0 ; fila < distribucion.getDistX().getNEventos(); fila++) {
				probCond=distribucion.getProbCondXdadoY(fila, col);
				if (probCond != 0) {
					LogBase2=Math.log(probCond) / Math.log(2) ;
					sumatoria+=probCond * LogBase2;
				}
			}
			entropiaXdadoY*=-distribucion.getDistY().getProb(col)*sumatoria;
			sumatoria=0;
		}
			
		return entropiaXdadoY;
	}
	
	public double getInformacionMutua() {	
		return distribucion.getDistX().getEntropia() - getPerdida();
	}
	
	public List<TY> transmitir(List<TX> mensaje){
		
		return null;
	}
	
	private boolean converge(Canal otroCanal) {
		
		
		return false;
	}
	
	private TY transmitir(TX simbolo) {
		
		return null;
	}
	
	//Transmite hasta que converga
	public List<TY> transmitir(GeneradorMontecarlo<TX> generador){
		
		return null;
	}
	
}
