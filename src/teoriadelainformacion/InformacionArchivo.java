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
public class InformacionArchivo<T extends Comparable<T>> {
    private int width;
    private int height;
    private DistProbSimple<T> distribucion;
    private String mensaje;

    public InformacionArchivo(int width, int height, DistProbSimple<T> distribucion, String mensaje) {
        this.width = width;
        this.height = height;
        this.distribucion = distribucion;
        this.mensaje = mensaje;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public DistProbSimple<T> getDistribucion() {
        return distribucion;
    }

    public void setDistribucion(DistProbSimple<T> distribucion) {
        this.distribucion = distribucion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "InformacionArchivo{" + "width=" + width + ", height=" + height + ", mensaje=" + mensaje + '}';
    }
    
    
}
