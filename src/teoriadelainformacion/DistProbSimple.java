/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 *
 * @author lucho
 * @param <T>
 */
public class DistProbSimple<T extends Comparable<T>> extends DistProb{
    private int nEventos;
    private int[] ocurrencias;
    private T[] eventTag;
    private int totalOcurrencias;
    private boolean desvioChanged;
    private float desvio=-1;
    

    public DistProbSimple(int nEventos, T[] etiquetas) {
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
    	LinkedHashMap<T,Integer> outMap= new LinkedHashMap();
        for (int i = 0; i < ocurrencias.length; i++) {
            outMap.put(eventTag[i], ocurrencias[i]);
        }
    	return outMap;
    }
    
    public int getNEventos(){
        return nEventos;
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
    
}