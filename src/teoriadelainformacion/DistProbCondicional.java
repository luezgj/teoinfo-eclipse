package teoriadelainformacion;

public class DistProbCondicional<T extends Comparable<T>> extends DistProb {

	Imagen img1;
    Imagen img2;
    
    private int [][] MatrizConjunta;
    private int [] ArregloX;
    private int [] ArregloY;
    private int totalOcurrencias;
    private int nEventos;

    public DistProbCondicional(T[] eventosX, T[] eventosY) {
    	
    }
    
    public DistProbCondicional(/*Imagen imagen1, Imagen imagen2*/) {
        //this.img1 = imagen1;
        //this.img2 = imagen2;
        
        nEventos = img1.getDistribucion().getNEventos();
        
        MatrizConjunta = new int[nEventos][nEventos];
        
        ArregloX = new int[img1.getDistribucion().getNEventos()];
        ArregloY = new int[img1.getDistribucion().getNEventos()];
        
        inicializarMatriz();
        inicializarArreglos(ArregloX,ArregloY);
        
        armarMatriz(ArregloX,ArregloY);
    }
    
    private void inicializarArreglos(int [] ArregloX,int [] ArregloY) {
    	for (int i=0; i <  ArregloX.length ; i++) {
    		ArregloX[i]= 0;
    		ArregloY[i]= 0;
    	}
    }
    
    private void inicializarMatriz() {
    	for (int i=0; i <  MatrizConjunta.length ; i++)
    		for ( int j=0 ; j < MatrizConjunta[i].length ; j++)
    			MatrizConjunta[i][j] = 0;
    }
	
    private void armarMatriz(int [] ArregloX,int [] ArregloY) {
    	
    	int [] pA = img1.getPixeles();
    	int [] pB = img2.getPixeles();
    	
    	for (int i=0; i < pA.length ; i++){
    			MatrizConjunta[pA[i]][pB[i]]++;
    			ArregloX[pA[i]]++;
    			ArregloY[pB[i]]++;
    		}
    	totalOcurrencias = pA.length;
    }
    
    public float getProbX(int i) {
    	return (float) ArregloX[i] / (float) totalOcurrencias ;
    }
    
    public float getProbY(int i) {
    	return (float) ArregloY[i] / (float) totalOcurrencias ;
    }
    
    public float getProbConjunta(int x,int y) {
    	return (float) MatrizConjunta[x][y] / (float) totalOcurrencias ;
    }
    
    public float getProbCondXdadoY(int x,int y) {
    	
    	return getProbConjunta(x,y) / getProbY(y);
    }
    
	public float getProbCondYdadoX(int x,int y) {
    	
		return getProbConjunta(x,y) / getProbX(x);
    }
	
	public int getNEventos(){
        return nEventos;
    }
    
	@Override
	protected float getMediaSpecific() {
		float mediaOut=0;
        int[] pixels1=img1.getPixeles();
        int[] pixels2=img2.getPixeles();
        for(int i=0;i<pixels1.length;i++){
            mediaOut+=pixels1[i]*pixels2[i];
        }
        return mediaOut/pixels1.length;
	}
	
	public DistProbSimple<T> getDistX(){
		return img1.getDistribucion();
	}

}