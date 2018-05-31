/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author lucho
 */
//Esta clase solo puede decodificar un mensaje por vez
public class Decodificador {

    private static char caracter;
    private static int cantSimbolosLeidos;
    private static String secuenciaBit;
    
    public static <T extends Comparable<T>> List<T> decodificar(String mensajeCodificado, int cantSimbolos, DistProbSimple<T> probabilidades) {
        List<T> decodificado = new LinkedList<T>();

        Nodo<T> arbolCodigo = CodificacionHuffman.generarArbol(probabilidades);
        Nodo<T> auxNodo = arbolCodigo;
        
        StringBuilder mensaje=new StringBuilder();
        
        for ( int i=0 ; i < mensajeCodificado.length(); i++) {
        	caracter= mensajeCodificado.charAt(i);
        	secuenciaBit=Codificador.generarBits(caracter);
        	mensaje.append(secuenciaBit);
        }
        
        char bit;
        
        cantSimbolosLeidos=0;
        for (int i=0 ; i < mensaje.length(); i++) {
        	bit = mensaje.charAt(i);
        	if (bit == '1')
        		auxNodo = auxNodo.getDer();
        	else
        		auxNodo = auxNodo.getIzq();
        	if (auxNodo.esHoja()) {
            	decodificado.add(auxNodo.getSimbolo());
                auxNodo = arbolCodigo;
                if (++cantSimbolosLeidos == cantSimbolos){
                	return decodificado;
                }}
        }
        return decodificado;
    }

}
