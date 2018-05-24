/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

/**
 *
 * @author lucho
 */
public class DistProbConjunta extends DistProb{
    Imagen img1;
    Imagen img2;

    public DistProbConjunta(Imagen imagen1, Imagen imagen2) {
        this.img1 = imagen1;
        this.img2 = imagen2;
    }
    
    @Override
    protected float getMediaSpecific(){
        float mediaOut=0;
        int[] pixels1=img1.getPixeles();
        int[] pixels2=img2.getPixeles();
        for(int i=0;i<pixels1.length;i++){
            mediaOut+=pixels1[i]*pixels2[i];
        }
        return mediaOut/pixels1.length;
    }
    
    public float getCovarianza(){
        return getMedia()-(img1.getDistribucion().getMedia()*img2.getDistribucion().getMedia());
    }
    
    public float getFactorCorrelacion(){
        return getCovarianza()/(img1.getDistribucion().getDesvio()*img2.getDistribucion().getDesvio());
    }
    
    public float getProb(int eventoA,int eventoB){
        return img1.getDistribucion().getProb(eventoA)*img2.getDistribucion().getProb(eventoB);
    }
}
