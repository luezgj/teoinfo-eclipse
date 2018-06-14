package teoriadelainformacion;

import java.util.HashMap;

public class DistProbCondicional<TX extends Comparable<TX>, TY extends Comparable<TY>> extends DistProb {    
    private DistProbSimple<TX> distX;
    private DistProbSimple<TY> distY;
	
	private int [][] MatrizConjunta;
    private int totalOcurrencias;

    
    public DistProbCondicional(int[] eventosX, int[] eventosY, DistProbSimple<TX> distribucionX, DistProbSimple<TY> distribucionY) {
        this.distX=distribucionX;
        this.distY=distribucionY;
        
    	MatrizConjunta = new int[distX.getNEventos()][distY.getNEventos()];
        
        inicializarMatriz();
        
    	for (int i=0; i < eventosX.length ; i++){
    		MatrizConjunta[eventosX[i]][eventosY[i]]++;
    	}
    	
    	totalOcurrencias = eventosX.length;
    }
    
    public DistProbCondicional(TX[] eventosX, TY[] eventosY, int nEventos){
    	HashMap<TX,Integer> etiquetasX = new HashMap<>();
        for (int i = 0; i < nEventos; i++) {
            etiquetasX.put(eventosX[i], i);
        }
        distX = new DistProbSimple<TX>(nEventos,etiquetasX);
        
        HashMap<TY,Integer> etiquetasY = new HashMap<>();
        for (int i = 0; i < nEventos; i++) {
            etiquetasY.put(eventosY[i], i);
        }
        distY = new DistProbSimple<TY>(nEventos,etiquetasY);
        for (int i=0; i < eventosX.length; i++){
        	distX.addOcurrencia(distX.getIndice(eventosX[i]), 1);
        	distY.addOcurrencia(distY.getIndice(eventosY[i]), 1);
        }
        
        MatrizConjunta = new int[distX.getNEventos()][distY.getNEventos()];
        
        inicializarMatriz();
        
    	for (int i=0; i < eventosX.length ; i++){
    		MatrizConjunta[distX.getIndice(eventosX[i])][distY.getIndice(eventosY[i])]++;
    	}
    	
    	totalOcurrencias = eventosX.length;
    }
    
    private void inicializarMatriz() {
    	for (int i=0; i <  MatrizConjunta.length ; i++)
    		for ( int j=0 ; j < MatrizConjunta[i].length ; j++)
    			MatrizConjunta[i][j] = 0;
    }
    
    public float getProbConjunta(int x,int y) {
    	return (float) MatrizConjunta[x][y] / (float) totalOcurrencias ;
    }
    
    public float getProbCondXdadoY(int x,int y) {
    	if (distY.getProb(y) != 0)
    		return getProbConjunta(x,y) / distY.getProb(y);
    	return 0;
    }
    
	public float getProbCondYdadoX(int x,int y) {
    	if (distX.getProb(x) != 0)
    		return getProbConjunta(x,y) / distX.getProb(x);
    	return 0;
    }
    
	@Override
	protected float getMediaSpecific() {
		float mediaOut=0;
        for(int i=0;i<MatrizConjunta.length;i++){
        	for(int j=0;i<MatrizConjunta[0].length;i++){
        		mediaOut+=i*j*MatrizConjunta[i][j];
        	}
        }
        return mediaOut/totalOcurrencias;
	}
	
	public DistProbSimple<TX> getDistX(){
		return distX;
	}

	public DistProbSimple<TY> getDistY(){
		return distY;
	}
}