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
import teoriadelainformacion.ManejadorArchivos;

public class TestParteFinal {

	static final String dir = "ArchivosCreados/";
	final static int NRO_DE_COLORES=256;
	
	//Obtiene la codificación Huffman apartir de una distribucion y calcula la longitud media del código
	private static double getLongitudMedia(DistProbSimple distribucion) {
		double longitudMedia = 0.0;
		
        HashMap<Integer,String[]> codificacion = CodificacionHuffman.obtenerCodigo(distribucion);
        for (Map.Entry<Integer, String[]> entry : codificacion.entrySet()) {
            longitudMedia += entry.getValue().length * distribucion.getProb(entry.getKey());
        }
        
        return longitudMedia;
	}
	
	public static String getInfoCanal(List<Integer> mensajeGenerado,List<Integer> mensajeTransmitido) {
		String informacion="";
		
		informacion +=" Mensaje Generado: "+mensajeGenerado.toString()+"\n";
		informacion +=" Mensaje Transmitido: "+mensajeTransmitido.toString()+"\n";
		
		Integer[] eventosX = new Integer[mensajeGenerado.size()];
		Integer[] eventosY = new Integer[mensajeTransmitido.size()];
		eventosX=mensajeGenerado.toArray(eventosX);
		eventosY=mensajeTransmitido.toArray(eventosY);
		
		DistProbCondicional<Integer,Integer>  probCond = new DistProbCondicional<>(eventosX,eventosY,NRO_DE_COLORES);
		Canal<Integer,Integer> canalGenerado = new Canal<>(probCond);
		informacion+="Ruido: "+canalGenerado.getRuido()+"Perdida: "+canalGenerado.getPerdida()+"Informacion Mutua: "+canalGenerado.getInformacionMutua()+"\n"+"\n";
		
		return informacion;
	}
	
	public static void main(String[] args) {
		
		Imagen imgCanal2 = new Imagen("Imagenes/Will_Canal2.bmp"),
		imgCanal8 = new Imagen("Imagenes/Will_Canal8.bmp"),
		imgCanal10 = new Imagen("Imagenes/Will_Canal10.bmp"),
		imgWill6 = new Imagen("Imagenes/Will_6.bmp"),
		imgOrg = new Imagen("Imagenes/Will(Original).bmp");
		
		String contenidoArchivo;
		
		DistProbCondicional<Integer,Integer> probCond;
		Canal<Integer,Integer> canal1;
		Canal<Integer,Integer> canal2;
		Canal<Integer,Integer> canal3; 
		Canal<Integer,Integer> canalGenerado;
		int[] eventosImgOrg = imgOrg.getPixeles();
		DistProbSimple distImgOriginal = imgOrg.getDistribucion();
		
		
        //Ejercicio 1
		
		DistProbSimple distImgCanal2 = imgCanal2.getDistribucion();
		DistProbSimple distImgCanal8 = imgCanal8.getDistribucion();
        DistProbSimple distImgCanal10 = imgCanal10.getDistribucion();
        
        JFreeChart histogramaCanal2= Drawer.generarHistograma("Distribucion de la imagen salida del canal 2", "Tono de gris", "Frecuencia", imgCanal2);
        JFreeChart histogramaCanal8= Drawer.generarHistograma("Distribucion de la imagen salida del canal 8", "Tono de gris", "Frecuencia", imgCanal8);
        JFreeChart histogramaCanal10= Drawer.generarHistograma("Distribucion de la imagen salida del canal 10", "Tono de gris", "Frecuencia", imgCanal10);
        Drawer.guardarJPEG(histogramaCanal2, dir+"Hist_imgCanal 2.jpg");
        Drawer.guardarJPEG(histogramaCanal8, dir+"Hist_imgCanal 8.jpg");
        Drawer.guardarJPEG(histogramaCanal10, dir+"Hist_imgCanal_10.jpg");
        
        contenidoArchivo = "Imagen canal 2: "+"\n"+"Media: "+distImgCanal2.getMedia()+" Desvio: "+distImgCanal2.getDesvio()+"\n";
        contenidoArchivo += "Imagen canal 8: "+"\n"+"Media: "+distImgCanal8.getMedia()+" Desvio: "+distImgCanal8.getDesvio()+"\n";
        contenidoArchivo += "Imagen canal 10: "+"\n"+"Media: "+distImgCanal10.getMedia()+" Desvio: "+distImgCanal10.getDesvio()+"\n";
        
        ManejadorArchivos.guardarEnArchivo(dir+"ParteFinalEjercicio1.txt", contenidoArchivo);
        
        //Ejercicio 2
        
        contenidoArchivo = "Longitud media canal 2: "+getLongitudMedia(distImgCanal2)+"\n";
        contenidoArchivo += "Longitud media canal 8: "+getLongitudMedia(distImgCanal8)+"\n";
        contenidoArchivo += "Longitud media canal 10: "+getLongitudMedia(distImgCanal10)+"\n";
       
        ManejadorArchivos.guardarEnArchivo(dir+"ParteFinalEjercicio2.txt", contenidoArchivo);
        
        //Ejercicio 3
        List<Integer> mensajeTransmitido = new ArrayList<>();
        List<Integer> mensajeImgWill6 = new ArrayList<>();
        int[] ArrPixeles=imgWill6.getPixeles();
        for (int i=0 ; i < ArrPixeles.length ; i++) {
        	mensajeImgWill6.add(ArrPixeles[i]);
        }
        
        //Genero el canal entre la imagen original y la imagen salida del canal 2
      	int[] eventosImgCanal2 = imgCanal2.getPixeles();		
      	probCond = new DistProbCondicional<>(eventosImgOrg,eventosImgCanal2,distImgOriginal,distImgCanal2);
      	canal1 = new Canal<>(probCond);
      	//Genero el canal entre la imagen original y la imagen salida del canal 8
      	int[] eventosImgCanal8 = imgCanal8.getPixeles();		
      	probCond = new DistProbCondicional<>(eventosImgOrg,eventosImgCanal8,distImgOriginal,distImgCanal8);
      	canal2 = new Canal<>(probCond);
      	//Genero el canal entre la imagen original y la imagen salida del canal 10
      	int[] eventosImgCanal10 = imgCanal8.getPixeles();		
      	probCond = new DistProbCondicional<>(eventosImgOrg,eventosImgCanal10,distImgOriginal,distImgCanal10);
      	canal3 = new Canal<>(probCond);
        
      	//Trasmito la imagen will_6
      	mensajeTransmitido = canal1.transmitir(mensajeImgWill6);
      	contenidoArchivo = getInfoCanal(mensajeImgWill6, mensajeTransmitido)+"\n";
      	mensajeTransmitido = canal1.transmitir(mensajeImgWill6);
      	contenidoArchivo += getInfoCanal(mensajeImgWill6, mensajeTransmitido)+"\n";
      	mensajeTransmitido = canal1.transmitir(mensajeImgWill6);
      	contenidoArchivo = getInfoCanal(mensajeImgWill6, mensajeTransmitido)+"\n";
        
      	ManejadorArchivos.guardarEnArchivo(dir+"ParteFinalEjercicio3.txt", contenidoArchivo);
      	
      	//Ejercicio 4
      	//Analizado en el informe
	}
	
}
