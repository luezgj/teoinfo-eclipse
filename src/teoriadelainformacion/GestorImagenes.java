package teoriadelainformacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GestorImagenes {

    private List<Imagen> Imagenes;
    private boolean ordenado;
    private Imagen original;
    
    public GestorImagenes(Imagen imgOriginal){
        Imagenes=new ArrayList<>();
        ordenado = false;
        original= imgOriginal;
    }
    
    public void addImagen(Imagen img){
        Imagenes.add(img);
        ordenado = false;
    }

    public Imagen getOriginal() {
        return original;
    }

    public void setOriginal(Imagen original) {
        this.original = original;
        ordenado=false;
    }
    
    public int size(){
        return Imagenes.size();
    }
    
    public void ordenarImagenes(){
    	
    	Collections.sort(Imagenes, new Comparator<Imagen>() {

			@Override
			public int compare(Imagen img1, Imagen img2) {
				
				DistProbConjunta DistConjunta1 = new DistProbConjunta(original,img1);
				DistProbConjunta DistConjunta2 = new DistProbConjunta(original,img2);
				
				float factorCorrelacion1 = DistConjunta1.getFactorCorrelacion(); 
				float factorCorrelacion2 = DistConjunta2.getFactorCorrelacion(); 
				
				if (factorCorrelacion1 > factorCorrelacion2) {
					return -1;
				} else if (factorCorrelacion1 < factorCorrelacion2) {
					return 1;
				}
				
				return 0;
			}
    		
    	});
    	ordenado = true;
    }
    
    public Imagen getimagenMasParecida(Imagen imgOriginal) {
    	if (!ordenado ) {
    		ordenarImagenes();
    	}
    	return Imagenes.get(0);
    }
    //
    
    public Imagen getImagenParecida(int index){
        if(index>=Imagenes.size()){
            return null;
        }
        if(!ordenado){
            ordenarImagenes();
        }
        return  Imagenes.get(index);
    }
    
    public String getInfoOrdenado(){
        if(!ordenado){
            ordenarImagenes();
        }
        StringBuilder outBuilder = new StringBuilder();
        
        outBuilder.append("Imagen Original: ");
        outBuilder.append(original.toString());
        outBuilder.append("- Media: ");
        outBuilder.append(original.getDistribucion().getMedia());
        outBuilder.append("- Desvio: ");
        outBuilder.append(original.getDistribucion().getDesvio());
        
        for(Imagen img: Imagenes){
            outBuilder.append(System.getProperty("line.separator"));
            outBuilder.append(img.toString());
            outBuilder.append("- Media: ");
            outBuilder.append(img.getDistribucion().getMedia());
            outBuilder.append("- Desvio: ");
            outBuilder.append(img.getDistribucion().getDesvio());
            outBuilder.append("-Correlacion: ");
            DistProbConjunta dconj = new DistProbConjunta(original, img);
            outBuilder.append(dconj.getFactorCorrelacion());
        }
        
        return outBuilder.toString();
    }
}
