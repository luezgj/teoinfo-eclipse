package teoriadelainformacion;

public class Canal {

	DistProbCondicional distribucion;
	
	public Canal( DistProbCondicional distribucion) {
		this.distribucion = distribucion;
	}
	
	public Canal(Imagen imagen1, Imagen imagen2) {
		
	}
	
	public float getRuido() {
		float entropiaYdadoX = 0;
		float sumatoria=0;
		for (int fila=0 ; fila < distribucion.getNEventos(); fila++ ) {
			for ( int col = 0 ; col < distribucion.getNEventos(); col++) {
				sumatoria+=distribucion.getProbCondYdadoX(fila, col)* ( Math.log(distribucion.getProbCondYdadoX(fila, col)) / Math.log(2) );
			}
			entropiaYdadoX*=-distribucion.getProbX(fila)*sumatoria;
			sumatoria=0;
		}
			
		return entropiaYdadoX;
	}
	
	public float getPerdida() {
		float entropiaXdadoY = 0;
		float sumatoria=0;
		for (int col=0 ; col < distribucion.getNEventos(); col++ ) {
			for ( int fila = 0 ; fila < distribucion.getNEventos(); fila++) {
				sumatoria+=distribucion.getProbCondXdadoY(fila, col)* ( Math.log(distribucion.getProbCondXdadoY(fila, col)) / Math.log(2) );
			}
			entropiaXdadoY*=-distribucion.getProbY(col)*sumatoria;
			sumatoria=0;
		}
			
		return entropiaXdadoY;
	}
	
	public float getInformacionMutua() {
		float entropiaXdadoY = getPerdida();
		float entropiaX = 0;
		for ( int i=0 ; i < distribucion.getNEventos() ; i++)
			entropiaX+= -distribucion.getProbX(i)* ( Math.log(distribucion.getProbX(i)) / Math.log(2) );
			
		return entropiaX + entropiaXdadoY;
	}
	
	
	
}
