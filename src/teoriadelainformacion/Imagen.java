package teoriadelainformacion;



import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Imagen {
    final static int NRO_DE_COLORES=256;
    private BufferedImage img;
    private String nombre;
    
    public Imagen(String dir){
        nombre=dir;
        try {
            img = ImageIO.read(new File(dir));
        } 
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public ColorModel getColorModel(){
        return img.getColorModel();
    }
    
    //
    public Imagen(int width,int height,int[] RGB) {
    	img= new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
    	for (int i=0; i < height; i++)
    		for (int j=0 ; j < width; j++)
    			img.setRGB(j, i, RGB[(i*width)+j] );
    }
    
    public Imagen(int width, int height){
        img= new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
    }
    
    public int getSizeX(){
        return img.getWidth();
    }
    
    public int getSizeY(){
        return img.getHeight();
    }
    
    public BufferedImage getImg() {
		return img;
	}
    
    public int[] getPixeles(){
        int[] pixelsArray= img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
        for(int i=0;i<pixelsArray.length;i++){
            pixelsArray[i]=(pixelsArray[i] & 0xFF);
        }
        return pixelsArray;
    }
    
    public double[] getDPixeles(){
        double[] outArray=new double[img.getWidth()*img.getHeight()];
        int[] pixelsArray=getPixeles();
        for (int i = 0; i < pixelsArray.length; i++) {
            outArray[i] = pixelsArray[i];
        }
        return outArray;
    }
    
    public DistProbSimple<Integer> getDistribucion(){
        int [] pixeles=getPixeles();
        HashMap<Integer,Integer> etiquetas = new HashMap<>();
        for (int i = 0; i < NRO_DE_COLORES; i++) {
            etiquetas.put(i, i);
        }
        DistProbSimple<Integer> salida = new DistProbSimple<Integer>(NRO_DE_COLORES,etiquetas);
        for (int i=0; i < pixeles.length; i++){
            salida.addOcurrencia(pixeles[i], 1);
        }
        return salida;
    }

    @Override
    public String toString() {
        return "Imagen{"+ nombre + '}';
    }
    public void Guardar(String dir,String nombreArchivo){
    	try{
    		String formato = "bmp";
    		
    		File fichero = new File(dir+nombreArchivo+".bmp");
			ImageIO.write(img, formato, fichero);
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    }
    
}
