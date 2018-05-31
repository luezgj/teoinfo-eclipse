package teoriadelainformacion;

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
    
    private void inicializarMatriz() {
    	for (int i=0; i <  MatrizConjunta.length ; i++)
    		for ( int j=0 ; j < MatrizConjunta[i].length ; j++)
    			MatrizConjunta[i][j] = 0;
    }
    
    public float getProbConjunta(int x,int y) {
    	return (float) MatrizConjunta[x][y] / (float) totalOcurrencias ;
    }
    
    public float getProbCondXdadoY(int x,int y) {
    	return getProbConjunta(x,y) / distY.getProb(y);
    }
    
	public float getProbCondYdadoX(int x,int y) {
    	
		return getProbConjunta(x,y) / distX.getProb(x);
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