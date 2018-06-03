package Test;

import java.util.HashMap;
import java.util.List;

import teoriadelainformacion.Canal;
import teoriadelainformacion.CodificacionHuffman;
import teoriadelainformacion.Codificador;
import teoriadelainformacion.Decodificador;
import teoriadelainformacion.DistProbCondicional;
import teoriadelainformacion.DistProbSimple;
import teoriadelainformacion.Drawer;
import teoriadelainformacion.GeneradorMontecarlo;
import teoriadelainformacion.GestorImagenes;
import teoriadelainformacion.Imagen;
import teoriadelainformacion.InformacionArchivo;
import teoriadelainformacion.ManejadorArchivos;



public class TestGenerador {

	public static void main(String[] args) {
		HashMap<String, Integer> etiquetasX = new HashMap<String, Integer>();
		etiquetasX.put("X1",0);
		etiquetasX.put("X2",1);
		
		HashMap<String, Integer> etiquetasY = new HashMap<String, Integer>();
		etiquetasY.put("Y1",0);
		etiquetasY.put("Y2",1);
		etiquetasY.put("Y3",2);
		
		DistProbSimple<String> dpsX = new DistProbSimple(2,etiquetasX);
		dpsX.addOcurrencia(0, 2);
		dpsX.addOcurrencia(1, 2);
		
		DistProbSimple<String> dpsY = new DistProbSimple(3,etiquetasY);
		dpsY.addOcurrencia(0, 1);
		dpsY.addOcurrencia(1, 2);
		dpsY.addOcurrencia(2, 1);
		
		int[] eventosX= {1,0,0,1};
		
		int[] eventosY= {1,0,2,1};
		
		DistProbCondicional<String,String> probCond = new DistProbCondicional<>(eventosX,eventosY,dpsX,dpsY);
		
		Canal<String,String> canal = new Canal<>(probCond);
		
		System.out.println("Ruido: "+canal.getRuido());
		System.out.println("Perdida: "+canal.getPerdida());
		System.out.println("Informacion Mutua: "+canal.getInformacionMutua());
	}

}
