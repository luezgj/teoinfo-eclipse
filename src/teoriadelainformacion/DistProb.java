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
public abstract class DistProb {
    protected float media=-1;
    protected boolean mediaChanged;
    
    public float getMedia(){
        if(media==-1 || mediaChanged){
            media=getMediaSpecific();
            mediaChanged=false;
        }
        return media;
    }
    
    protected abstract float getMediaSpecific();
    
}
