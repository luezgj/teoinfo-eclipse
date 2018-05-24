package teoriadelainformacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodificacionHuffman {
	
	public static <T extends Comparable<T>> HashMap<T,String[]> obtenerCodigo(DistProbSimple<T> distribucion){
		
		Nodo<T> raiz = generarArbol(distribucion);
		
		HashMap<T,String[]> codificacion = getHashMap(raiz);
		
		return codificacion;
	}
	
        private static <T extends Comparable<T>> HashMap<T,String[]> getHashMap(Nodo<T> raiz){
		
		HashMap<T,String[]> codificacion = new HashMap<>();
		
		completarHashMap(raiz, "", codificacion);
		
		return codificacion;
	}
	
	private static <T extends Comparable<T>> void completarHashMap(Nodo<T> nodo, String cod,HashMap<T,String[]> codificacion){
		
		if (!nodo.esHoja()) {
			completarHashMap(nodo.der, cod + '1',codificacion);
			completarHashMap(nodo.izq, cod + '0',codificacion);
		} else {
			String[] codArr = new String[cod.length()];
			for (int i = 0; i < cod.length (); i++) 
				codArr[i]=Character.toString(cod.charAt(i));
			codificacion.put(nodo.getSimbolo(),codArr);
		}
	}
	
	public static <T extends Comparable<T>> Nodo<T> generarArbol(DistProbSimple<T> distribucion) {
		List<Nodo<T>> Nodos = new ArrayList<>();
		
		for (Map.Entry<T, Integer> pair : distribucion.getFrecuencia().entrySet()) {
			if (pair.getValue() != 0)
				Nodos.add(new Nodo<T>(pair.getKey(),pair.getValue(),null,null));
		}
		
		Collections.sort(Nodos);
		if ( Nodos.size() == 1)
			Nodos.add(new Nodo<T>(1,null,null));
		
		while (Nodos.size() > 1) {
			Nodo<T> nodoDer = Nodos.get(0);
			Nodo<T> nodoIzq = Nodos.get(1);
			Nodo<T> padre = new Nodo(nodoIzq.frecuencia + nodoDer.frecuencia,nodoIzq,nodoDer);
			Nodos.remove(nodoIzq);
			Nodos.remove(nodoDer);
			Nodos.add(padre);
			Collections.sort(Nodos);
		}
		
		return Nodos.get(Nodos.size()-1);
	}
}
