package Test;

import java.util.ArrayList;
import java.util.List;

import teoriadelainformacion.Canal;
import teoriadelainformacion.DistProbCondicional;
import teoriadelainformacion.DistProbSimple;
import teoriadelainformacion.GeneradorMontecarlo;
import teoriadelainformacion.Imagen;
import teoriadelainformacion.ManejadorArchivos;

public class TestSegundaParte {

	static final String dir = "ArchivosCreados/";
	final static int NRO_DE_COLORES=256;;

	public static String getInfoCanal(List<Integer> mensajeGenerado,List<Integer> mensajeTransmitido) {
		String informacion="";
		
		informacion +=" Mensaje Generado: "+mensajeGenerado.toString()+"\n";
		informacion +=" Mensaje Transmitido: "+mensajeTransmitido.toString()+"\n";
		
		Integer[] eventosX = new Integer[NRO_DE_COLORES];
		Integer[] eventosY = new Integer[NRO_DE_COLORES];
		eventosX=mensajeGenerado.toArray(eventosX);
		eventosY=mensajeTransmitido.toArray(eventosY);
		
		DistProbCondicional<Integer,Integer>  probCond = new DistProbCondicional<>(eventosX,eventosY,NRO_DE_COLORES);
		Canal<Integer,Integer> canalGenerado = new Canal<>(probCond);
		informacion+="Ruido: "+canalGenerado.getRuido()+"Perdida: "+canalGenerado.getPerdida()+"Informacion Mutua: "+canalGenerado.getInformacionMutua()+"\n"+"\n";
		
		return informacion;
	}
	
	public static void main(String[] args) {
		
		Imagen imgOrg = new Imagen("Imagenes/Will(Original).bmp"),
		imgCanal2 = new Imagen("Imagenes/Will_Canal2.bmp"),
		imgCanal8 = new Imagen("Imagenes/Will_Canal8.bmp"),
		imgCanal10 = new Imagen("Imagenes/Will_Canal10.bmp");
		
		DistProbSimple distImgOriginal = imgOrg.getDistribucion();
		DistProbSimple distImgCanal2 = imgCanal2.getDistribucion();
		DistProbSimple distImgCanal8 = imgCanal8.getDistribucion();
        DistProbSimple distImgCanal10 = imgCanal10.getDistribucion();
        
        String contenidoArchivoEjercicio1;
        String contenidoArchivoEjercicio2;
        String contenidoArchivoEjercicio3;
		
        GeneradorMontecarlo generador = new GeneradorMontecarlo();
        int longitud=10;
        List<Integer> mensajeGenerado = new ArrayList<>();
        List<Integer> mensajeTransmitido = new ArrayList<>();
        Integer[] eventosX;
        Integer[] eventosY;
        Canal<Integer,Integer> canalGenerado;
        
		//Ejercicios
		
		int[] eventosImgOrg = imgOrg.getPixeles();
		//Genero el canal entre la imagen original y canal 2
		int[] eventosImgCanal2 = imgCanal2.getPixeles();
		
		DistProbCondicional<Integer,Integer> probCond = new DistProbCondicional<>(eventosImgOrg,eventosImgCanal2,distImgOriginal,distImgCanal2);
		Canal<Integer,Integer> canal = new Canal<>(probCond);
		
		contenidoArchivoEjercicio1 = "Matriz de transicion del canal generado por la imagen original y salida del canal 2"+"\n"+"\n"+canal.getInfoMatrizTransicion()+"\n";
		contenidoArchivoEjercicio2 = "Canal generado por la imagen original y salida del canal 2 "+"Ruido: "+canal.getRuido()+"Perdida: "+canal.getPerdida()+"Informacion Mutua: "+canal.getInformacionMutua()+"\n";
		contenidoArchivoEjercicio3 = "Canal generado por la imagen original y salida del canal 2"+"\n"+"\n";
		while ( longitud < 10000) {
			mensajeGenerado=generador.generarMensaje(distImgOriginal, longitud);
			mensajeTransmitido=canal.transmitir(mensajeGenerado);
			contenidoArchivoEjercicio3 += "Cantidad de datos: "+longitud+"\n";
			
			contenidoArchivoEjercicio3 += getInfoCanal(mensajeGenerado, mensajeTransmitido);
			
			longitud*=10;
		}
		contenidoArchivoEjercicio3+="Con método de convergencia: "+"\n";
		mensajeTransmitido=canal.transmitir(generador, mensajeGenerado);
		contenidoArchivoEjercicio3 += getInfoCanal(mensajeGenerado, mensajeTransmitido);
				
		//Genero el canal entre la imagen original y canal 8
		int[] eventosImgCanal8 = imgCanal8.getPixeles();
		
		probCond = new DistProbCondicional<>(eventosImgOrg,eventosImgCanal8,distImgOriginal,distImgCanal8);
		canal = new Canal<>(probCond);
		contenidoArchivoEjercicio1 += "Matriz de transicion del canal generado por la imagen original y salida del canal 8"+"\n"+"\n"+canal.getInfoMatrizTransicion()+"\n";
		contenidoArchivoEjercicio2 += "Canal generado por la imagen original y salida del canal 8 "+"Ruido: "+canal.getRuido()+"Perdida: "+canal.getPerdida()+"Informacion Mutua: "+canal.getInformacionMutua()+"\n";
		
		//Genero el canal entre la imagen original y canal 10
		int[] eventosImgCanal10 = imgCanal10.getPixeles();
		
		probCond = new DistProbCondicional<>(eventosImgOrg,eventosImgCanal10,distImgOriginal,distImgCanal10);
		canal = new Canal<>(probCond);
		contenidoArchivoEjercicio1 += "Matriz de transicion del canal generado por la imagen original y salida del canal 10"+"\n"+"\n"+canal.getInfoMatrizTransicion()+"\n";
		contenidoArchivoEjercicio2 += "Canal generado por la imagen original y salida del canal 10 "+"Ruido: "+canal.getRuido()+"Perdida: "+canal.getPerdida()+"Informacion Mutua: "+canal.getInformacionMutua()+"\n";
		
		//Creo el archivo
		ManejadorArchivos.guardarEnArchivo(dir+"SegundaParteEjercicio1.txt", contenidoArchivoEjercicio1);
		ManejadorArchivos.guardarEnArchivo(dir+"SegundaParteEjercicio2.txt", contenidoArchivoEjercicio2);
		ManejadorArchivos.guardarEnArchivo(dir+"SegundaParteEjercicio3.txt", contenidoArchivoEjercicio3);
		
		//Ejercicio 2
		
	}
}
