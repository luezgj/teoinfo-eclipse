package Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.JFreeChart;

import teoriadelainformacion.Canal;
import teoriadelainformacion.CodificacionHuffman;
import teoriadelainformacion.DistProbCondicional;
import teoriadelainformacion.DistProbSimple;
import teoriadelainformacion.Drawer;
import teoriadelainformacion.Imagen;



public class TestGenerador {
	
	static final String dir = "ArchivosCreados/";

	public static void main(String[] args) {
		/*
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
		
		GeneradorMontecarlo<String> generador = new GeneradorMontecarlo();

		List<String> mensajeSalida= canal.transmitir(generador);
		*/
		Imagen imgOrg = new Imagen("Imagenes/Will(Original).bmp"),
		imgWill6 = new Imagen("Imagenes/Will_6.bmp"),
		imgCanal2 = new Imagen("Imagenes/Will_Canal2.bmp"),
		imgCanal8 = new Imagen("Imagenes/Will_Canal8.bmp"),
		imgCanal10 = new Imagen("Imagenes/Will_Canal10.bmp");
		
		//Ejercicio 1
		
		DistProbSimple distImgOriginal = imgOrg.getDistribucion();
		DistProbSimple distImgCanal2 = imgCanal2.getDistribucion();
		DistProbSimple distImgCanal8 = imgCanal8.getDistribucion();
        DistProbSimple distImgCanal10 = imgCanal10.getDistribucion();
        
        JFreeChart histogramaCanal2= Drawer.generarHistograma("Distribucion de la imagen salida del canal 2", "Tono de gris", "Frecuencia", imgCanal2);
        JFreeChart histogramaCanal8= Drawer.generarHistograma("Distribucion de la imagen salida del canal 8", "Tono de gris", "Frecuencia", imgCanal8);
        JFreeChart histogramaCanal10= Drawer.generarHistograma("Distribucion de la imagen salida del canal 10", "Tono de gris", "Frecuencia", imgCanal10);
        Drawer.guardarJPEG(histogramaCanal2, dir+"Hist_imgCanal 2.jpg");
        Drawer.guardarJPEG(histogramaCanal8, dir+"Hist_imgCanal 8.jpg");
        Drawer.guardarJPEG(histogramaCanal10, dir+"Hist_imgCanal_10.jpg");
        
        float mediaImgcanal2 = distImgCanal2.getMedia();
        float mediaImgcanal8 = distImgCanal8.getMedia();
        float mediaImgcanal10 = distImgCanal10.getMedia();
        
        float DesvioImgCanal2 = distImgCanal2.getDesvio();
        float DesvioImgCanal8 = distImgCanal8.getDesvio();
        float DesvioImgCanal10 = distImgCanal10.getDesvio();
        
        //Ejercicio 2
        
        double longitudMedia=0.0;
        HashMap<Integer,String[]> codificacion = CodificacionHuffman.obtenerCodigo(distImgCanal2);
        for (Map.Entry<Integer, String[]> entry : codificacion.entrySet()) {
            longitudMedia += entry.getValue().length * distImgCanal2.getProb(entry.getKey());
        }
        System.out.println("Longitud media canal 2: "+longitudMedia);        
        
        longitudMedia=0.0;
        codificacion = CodificacionHuffman.obtenerCodigo(distImgCanal8);
        for (Map.Entry<Integer, String[]> entry : codificacion.entrySet()) {
            longitudMedia += entry.getValue().length * distImgCanal8.getProb(entry.getKey());
        }
        System.out.println("Longitud media canal 8: "+longitudMedia);
        
        longitudMedia=0.0;
        codificacion = CodificacionHuffman.obtenerCodigo(distImgCanal10);
        for (Map.Entry<Integer, String[]> entry : codificacion.entrySet()) {
            longitudMedia += entry.getValue().length * distImgCanal10.getProb(entry.getKey());
        }
        System.out.println("Longitud media canal 10: "+longitudMedia);
        System.out.println();
        //Ejercicio 3
        
        List<Integer> mensajeImgWill6 = new ArrayList<>();
        int[] ArrPixeles=imgWill6.getPixeles();
        for (int i=0 ; i < ArrPixeles.length ; i++) {
        	mensajeImgWill6.add(ArrPixeles[i]);
        }
        List<Integer> mensajeTrasmitido = new ArrayList<>();
        int[] eventosImgOrg = imgOrg.getPixeles();
        
		//Genero el canal entre la imagen original y canal 2
		int[] eventosImgCanal2 = imgCanal2.getPixeles();
		
		DistProbCondicional<Integer,Integer> probCond = new DistProbCondicional<>(eventosImgOrg,eventosImgCanal2,distImgOriginal,distImgCanal2);
		Canal<Integer,Integer> canal = new Canal<>(probCond);
		
		System.out.println("Canal 2");
		System.out.println("Ruido: "+canal.getRuido());
		System.out.println("Perdida: "+canal.getPerdida());
		System.out.println("Informacion Mutua: "+canal.getInformacionMutua());
		
		//trasmito imagen will_6
		mensajeTrasmitido=canal.transmitir(mensajeImgWill6);
		
		//Genero el canal entre la imagen original y canal 8
		int[] eventosImgCanal8 = imgCanal8.getPixeles();
		
		probCond = new DistProbCondicional<>(eventosImgOrg,eventosImgCanal8,distImgOriginal,distImgCanal8);
		canal = new Canal<>(probCond);
		
		System.out.println("Canal 8");
		System.out.println("Ruido: "+canal.getRuido());
		System.out.println("Perdida: "+canal.getPerdida());
		System.out.println("Informacion Mutua: "+canal.getInformacionMutua());
		
		//trasmito imagen will_6
		mensajeTrasmitido=canal.transmitir(mensajeImgWill6);
		
		//Genero el canal entre la imagen original y canal 10
		int[] eventosImgCanal10 = imgCanal10.getPixeles();
		
		probCond = new DistProbCondicional<>(eventosImgOrg,eventosImgCanal10,distImgOriginal,distImgCanal10);
		canal = new Canal<>(probCond);
		
		System.out.println("Canal 10");
		System.out.println("Ruido: "+canal.getRuido());
		System.out.println("Perdida: "+canal.getPerdida());
		System.out.println("Informacion Mutua: "+canal.getInformacionMutua());
		
		//trasmito imagen will_6
		mensajeTrasmitido=canal.transmitir(mensajeImgWill6);
	}

}
