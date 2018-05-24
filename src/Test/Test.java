package Test;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jfree.chart.JFreeChart;

import teoriadelainformacion.CodificacionHuffman;
import teoriadelainformacion.Codificador;
import teoriadelainformacion.Decodificador;
import teoriadelainformacion.DistProbSimple;
import teoriadelainformacion.Drawer;
import teoriadelainformacion.GestorImagenes;
import teoriadelainformacion.Imagen;
import teoriadelainformacion.InformacionArchivo;
import teoriadelainformacion.ManejadorArchivos;

public class Test {
	
	static final String dir = "ArchivosCreados/";
	
	public static void main (String arg[]) {
		
		Imagen imgOrg = new Imagen("Imagenes/Will(Original).bmp");

        Imagen img1 = new Imagen("Imagenes/Will_1.bmp"),
        img2 = new Imagen("Imagenes/Will_2.bmp"),
        img3 = new Imagen("Imagenes/Will_3.bmp"),
        img4 = new Imagen("Imagenes/Will_4.bmp"),
        img5 = new Imagen("Imagenes/Will_5.bmp"),
        img6 = new Imagen("Imagenes/Will_6.bmp"),
        img7 = new Imagen("Imagenes/Will_7.bmp");
        GestorImagenes gi = new GestorImagenes(imgOrg);
        gi.addImagen(img1);
        gi.addImagen(img2);
        gi.addImagen(img3);
        gi.addImagen(img4);
        gi.addImagen(img5);
        gi.addImagen(img6);
        gi.addImagen(img7);
        
        //Ejercicio 1
        gi.ordenarImagenes();
        //CREA ARCHIVO 
        ManejadorArchivos.guardarEnArchivo(dir+"ImagenesOrdenadas.txt", gi.getInfoOrdenado());
        
        //Ejercicio 2
        Imagen imgMasParecida = gi.getimagenMasParecida(imgOrg);
        Imagen imgMenosParecida = gi.getImagenParecida(gi.size()-1);
        
        
        DistProbSimple DistImgOriginal = imgOrg.getDistribucion();
        DistProbSimple DistImgMasParecida = imgMasParecida.getDistribucion();
        float mediaImgOriginal = DistImgOriginal.getMedia();
        float mediaImgMasParecida = DistImgMasParecida.getMedia();
        float DesvioImgOriginal = DistImgOriginal.getDesvio();
        float DesvioImgMasParecida = DistImgMasParecida.getDesvio();
        
        JFreeChart histogramaParecida= Drawer.generarHistograma("Distribucion mas similar (1)", "Tono de gris", "Frecuencia", imgMasParecida);
        JFreeChart histogramaDistina= Drawer.generarHistograma("Distribucion menos similar (6)", "Tono de gris", "Frecuencia", imgMenosParecida);
        JFreeChart histogramaOriginal= Drawer.generarHistograma("Distribucion original", "Tono de gris", "Frecuencia", imgOrg);
        Drawer.guardarJPEG(histogramaParecida, dir+"Hist_similar.jpg");
        Drawer.guardarJPEG(histogramaDistina, dir+"Hist_distinta.jpg");
        Drawer.guardarJPEG(histogramaOriginal, dir+"Hist_original.jpg");
        
        //Ejercicio 3
        List<Integer> mensaje = new ArrayList<>();
        int[] ArrPixeles = imgMasParecida.getPixeles();
        for (int i=0 ; i < ArrPixeles.length ; i++)
        	mensaje.add(ArrPixeles[i]);
        
        long time_start, time_end;
        time_start = System.currentTimeMillis();
        HashMap<Integer,String[]> codificacion = CodificacionHuffman.obtenerCodigo(DistImgMasParecida);
        String resultado = Codificador.codificar(mensaje, codificacion);
        time_end = System.currentTimeMillis();
        System.out.println("codification has taken "+ ( time_end - time_start ) +" seconds");
        
        //PARTE DE GUARDAR Y LEER EL ARCHIVO
        InformacionArchivo archivo = new InformacionArchivo<Integer>(imgOrg.getSizeX(),imgOrg.getSizeY(),DistImgMasParecida,resultado);
        ManejadorArchivos.guardarEnArchivo(dir+"imagenCodificada.bin", archivo);
        
        
        InformacionArchivo<Integer> infoArch = ManejadorArchivos.leerArchivo(dir+"imagenCodificada.bin");
        int alto=infoArch.getHeight(),ancho = infoArch.getWidth();
        DistProbSimple<Integer> distribucion = infoArch.getDistribucion();
        String mensajeCodificado = infoArch.getMensaje();
        int cantsimbolos = alto*ancho;
        
        //PARTE DE DECODIFICACION        
        time_start = System.currentTimeMillis();    
        List<Integer> pixeles = Decodificador.decodificar(mensajeCodificado, cantsimbolos, distribucion);
        time_end = System.currentTimeMillis();
        System.out.println("decodification has taken "+ ( time_end - time_start ) +" seconds");
        
       //PARTE DE RECONSTRUCCION IMAGEN   
        
        int [] arr = pixeles.stream().mapToInt(Integer::intValue).toArray();
        int[] RGB = new int[cantsimbolos];
        int pixel;
        
        for (int i=0 ; i < arr.length; i++) {
        	pixel = arr[i];
        	Color c = new Color(pixel,pixel,pixel);
        	RGB[i]= c.getRGB();
        }
        
        Imagen imagenReConstruida = new Imagen(ancho,alto,RGB);
        
        imagenReConstruida.Guardar(dir, "imagenReconstruida");
        
        try {
			Desktop.getDesktop().open(new File(dir));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
