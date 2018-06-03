package teoriadelainformacion;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GeneradorMontecarlo<T extends Comparable<T>> {
	
	public GeneradorMontecarlo() {
	}
	
	public List<T> generarMensaje(DistProbSimple<T> distribucion, int longitud){
		List<T> mensaje= new LinkedList<>();
		Map<T,Double> freqRelAcumuladas=getFrecAcumulada(distribucion.getFrecuencia(), distribucion.getOcurrencias());
		
		while(longitud--!=0){
			mensaje.add(generarSimbolo(freqRelAcumuladas));
		}
		
		return mensaje;
	}
	
	public T generarSimbolo(DistProbSimple<T> distribucion){
		return generarSimbolo(getFrecAcumulada(distribucion.getFrecuencia(),distribucion.getOcurrencias()));
	}
	
	private T generarSimbolo(Map<T, Double> freqRelAcumuladas){
		double random= Math.random();
		for(Map.Entry<T, Double> posibilidad: freqRelAcumuladas.entrySet()){
			if(random<posibilidad.getValue()){
				return posibilidad.getKey();
			}
		}
		return null;
	}
	
	private Map<T, Double> getFrecAcumulada(Map<T,Integer> frecAbsolutas, int totalOcurrencias){
		Map<T, Double> freqAcum= new LinkedHashMap<T, Double>();
		double freqAcumulada=0;
		for(Map.Entry<T, Integer> evento: frecAbsolutas.entrySet()){
			freqAcumulada+=(double)evento.getValue();
			Double freqRelAcum= new Double(freqAcumulada/totalOcurrencias);
			freqAcum.put(evento.getKey(), freqRelAcum);
		}
		return freqAcum;
	}
}