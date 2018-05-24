/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lucho
 */
public class ManejadorArchivos {
	
    public static void guardarEnArchivo(String dir, String info){
        try {
            Writer out= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dir)));
            out.write(info);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ManejadorArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static <T extends Comparable <T>> void guardarEnArchivo(String dir, InformacionArchivo<T> info) {
    	try {
	    	FileOutputStream fileSalida = new FileOutputStream(dir);
	    	DataOutputStream salida = new DataOutputStream(fileSalida);
	    	salida.writeInt(info.getWidth());
	    	salida.writeInt(info.getHeight());
	    	Map<T,Integer> frecuencias=info.getDistribucion().getFrecuencia();
	    	int cantElemAGuardar=0;
	        for (Map.Entry<T, Integer> entry : frecuencias.entrySet()) {
	            if(entry.getValue()!=0){
	            	cantElemAGuardar++;
	            }
	        }
	        salida.writeInt(cantElemAGuardar);
	        frecuencias = info.getDistribucion().getFrecuencia();
	        for (Map.Entry<T, Integer> entry : frecuencias.entrySet()) {
	            if(entry.getValue()!=0){
	            	salida.writeInt((Integer)entry.getKey());
	            	salida.writeInt(entry.getValue());
	            }
	        }
	        salida.writeChars(info.getMensaje());
	    	salida.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public static InformacionArchivo<Integer> leerArchivo(String dir) {
    	try {
	    	DataInputStream archEntrada = new DataInputStream(new FileInputStream(dir));
	            
	        int width=0, height=0;
	            
	        Integer[] etiquetas= new Integer[Imagen.NRO_DE_COLORES];
	        for (int i = 0; i < etiquetas.length; i++) {
	        	etiquetas[i]= i;
	        }
	            
	        DistProbSimple<Integer> distribucion= new DistProbSimple<Integer>(Imagen.NRO_DE_COLORES,etiquetas);
	            
	        width = archEntrada.readInt();
	        height = archEntrada.readInt();
	            
	        int cant = archEntrada.readInt();
	        for (int i = 0; i < cant; i++) {
	        	int evento=archEntrada.readInt();
	            int frecuencia=archEntrada.readInt();
	            distribucion.addOcurrencia(evento, frecuencia);
	        }
	         
	    	StringBuilder mensaje= new StringBuilder();
	        while (archEntrada.available() > 0)
	        	mensaje.append(archEntrada.readChar());
	            
	        InformacionArchivo<Integer> infoOut= new InformacionArchivo<Integer>(width,height,distribucion,mensaje.toString());
	        
	        archEntrada.close();
	        return infoOut;
    	}catch (IOException e) {
    		e.printStackTrace();
    	}
    	return null;
    	
    }
    
}
