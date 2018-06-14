/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author lucho
 * @param <T>
 */
public class DistProbSimple<T extends Comparable<T>> extends DistProb{
    private int nEventos;
    private int[] ocurrencias;
    
    private HashMap<T, Integer> eventTag; //Traduce T a enteros
    
    private int totalOcurrencias;
    private boolean desvioChanged;
    private float desvio=-1;
    

    public DistProbSimple(int nEventos, HashMap<T, Integer> etiquetas) {
        this.nEventos = nEventos;
        ocurrencias= new int[nEventos];
        eventTag= etiquetas;
    }
    
    public boolean addOcurrencia(int evento, int cantOcurrencias){
        if ( evento>=0 && evento<nEventos) {
            ocurrencias[evento]+=cantOcurrencias;
            totalOcurrencias+=cantOcurrencias;
            mediaChanged=true;
            desvioChanged=true;
        }
        return false;
    }
    
    public float getProb(int evento){
        if (evento>=0 && evento<nEventos){
            return (float)ocurrencias[evento]/(float) totalOcurrencias;
        }
        return -1;
    }
    
    public Map<T,Integer> getFrecuencia() {
    	LinkedHashMap<T,Integer> outMap= new LinkedHashMap<T, Integer>();
    	Set<T> eventos= eventTag.keySet();
        for (Iterator<T> iterator = eventos.iterator(); iterator.hasNext();) {
			T tagEvento = (T) iterator.next();
			Integer indice= eventTag.get(tagEvento);
			outMap.put(tagEvento, ocurrencias[indice]);
		}
    	return outMap;
    }
    
    public int getNEventos(){
        return nEventos;
    }
    
    public int getOcurrencias(){
    	return totalOcurrencias;
    }
    
    @Override
    protected float getMediaSpecific(){
        int suma = 0;
        for (int i = 0; i < ocurrencias.length; i++) {
            suma += i * ocurrencias[i];
        }
        return (float) suma / (float) totalOcurrencias;
    }
    
    public float getDesvio() {
        if (desvio == -1 || desvioChanged) {
            float desvioOut = 0;
            for (int i = 0; i < ocurrencias.length; i++) {
                desvioOut += Math.pow(i - getMedia(), 2) * ((float) ocurrencias[i] / (float) totalOcurrencias);
            }
            desvio = (float) Math.pow(desvioOut, 0.5);
            desvioChanged = false;
        }
        return desvio;
    }
    
    public Integer getIndice(T evento){
    	return eventTag.get(evento);
    }
    
    public T getSimbolo(int indice) {
    	Set<T> simbolos=eventTag.keySet();
    	int aux = 0;
    	for (T simbolo : simbolos) {
    		if (aux == indice)
    			return simbolo;
    		aux++;
    	}
    	return null;
    }
    
    public float getEntropia(){
    	float entropia=0;
    	for (int i = 0; i < ocurrencias.length; i++) {
    		if (ocurrencias[i] != 0) {
    			float probi=(float)ocurrencias[i]/(float)totalOcurrencias;
    			entropia+= -(probi * ( Math.log(probi) / Math.log(2) ) );
    		}
		}
    	return entropia;
    }
}