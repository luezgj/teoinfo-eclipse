package teoriadelainformacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Canal<TX extends Comparable<TX>,TY extends Comparable<TY>> {

	static final double CONSTANTE_CONVERGENCIA = 0.00001;
	static final int LONGITUD_MINIMA = 10;
	
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
		List<TY> mensajeSalida = new ArrayList<>();
	
		for ( int i=0 ; i < mensaje.size(); i++) {
			mensajeSalida.add(transmitir(mensaje.get(i)));
		}
		
		return mensajeSalida;
	}
	
	private TY transmitir(TX simbolo) {
		DistProbSimple<TX> distX = distribucion.getDistX();
		DistProbSimple<TY> distY = distribucion.getDistY();
		
		double random = Math.random();
		int indiceX = distX.getIndice(simbolo);
		int indiceY = 0;
		double acum = distribucion.getProbCondYdadoX(indiceX,indiceY);
		while (random > acum) {
			indiceY++;
			acum += distribucion.getProbCondXdadoY(indiceX,indiceY);
		}
		return (TY) distY.getSimbolo(indiceY);
	}
	
	private boolean converge(Integer [][] M_ant,Integer [][] M_act,int longitudDelMensaje) {
		if (longitudDelMensaje > LONGITUD_MINIMA) {
			for (int i=0; i <  M_ant.length ; i++)
	    		for ( int j=0 ; j < M_ant[i].length ; j++) {
	    			if (Math.abs((double)M_ant[i][j]/(double)(longitudDelMensaje-1) - (double)M_act[i][j]/(double)longitudDelMensaje) > CONSTANTE_CONVERGENCIA)
	    				return false;
    		}
			return true;
		}
		else 
			return false;
	}
	
    private void inicializarMatriz(Integer [][] matriz) {
    	for (int i=0; i <  matriz.length ; i++)
    		for ( int j=0 ; j < matriz[i].length ; j++)
    			matriz[i][j] = 0;
    }
    
	//Transmite hasta que converga
	public List<TY> transmitir(GeneradorMontecarlo<TX> generador){
		List<TY> mensajeSalida = new ArrayList<>();
		
		int cantSimbolosX = distribucion.getDistX().getNEventos();
		int cantSimbolosY = distribucion.getDistY().getNEventos();
		Integer [][] M_ant = new Integer[cantSimbolosX][cantSimbolosY];
		Integer [][] M_act = new Integer[cantSimbolosX][cantSimbolosY];
		inicializarMatriz(M_ant);
		inicializarMatriz(M_act);
		int longitudDelMensaje=0;
		int indiceSimboloX,indiceSimboloY;
		while (!converge(M_ant,M_act,longitudDelMensaje)) {
			TX simboloAEmitir = generador.generarSimbolo(distribucion.getDistX());
			TY simboloTransmitido = transmitir(simboloAEmitir);
			indiceSimboloX = distribucion.getDistX().getIndice(simboloAEmitir);
			indiceSimboloY = distribucion.getDistY().getIndice(simboloTransmitido);
			
			for(int i = 0 ; i < M_act.length ; i++)
				for( int j=0 ; j < M_act[i].length; j++) {
					M_ant[i][j]=M_act[i][j];
			}
			
			M_act[indiceSimboloX][indiceSimboloY]++;
			longitudDelMensaje++;
			
			mensajeSalida.add(simboloTransmitido);
		}
		return mensajeSalida;
	}
	
	public Float[][] getMatrizTransicion() {
		int cantSimbolosX = distribucion.getDistX().getNEventos();
		int cantSimbolosY = distribucion.getDistY().getNEventos();
		
		Float[][] matrizTransicion = new Float[cantSimbolosX][cantSimbolosY];
		
		for(int i = 0 ; i < matrizTransicion.length ; i++)
			for( int j=0 ; j < matrizTransicion.length; j++)
				matrizTransicion[i][j]=distribucion.getProbCondYdadoX(i, j);
		
		return matrizTransicion;
	}
}