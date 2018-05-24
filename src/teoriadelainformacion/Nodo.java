/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

/**
 *
 * @author lucho
 * @param <T>
 */
public class Nodo<T extends Comparable<T>> implements Comparable<Nodo<T>> {

    T simbolo;
    int frecuencia;
    Nodo<T> izq;
    Nodo<T> der;

    public Nodo(T t, int frecuencia, Nodo<T> izq, Nodo<T> der) {
        this.simbolo = t;
        this.frecuencia = frecuencia;
        this.izq = izq;
        this.der = der;
    }

    public Nodo(int frecuencia, Nodo<T> izq, Nodo<T> der) {
        this.frecuencia = frecuencia;
        this.izq = izq;
        this.der = der;
    }

    public boolean esHoja() {
        return izq == null && der == null;
    }

    public T getSimbolo() {

        return simbolo;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    @Override
    public int compareTo(Nodo<T> nodo) {

        if (frecuencia > nodo.getFrecuencia()) {
            return 1;
        } else if (frecuencia < nodo.getFrecuencia()) {
            return -1;
        }

        return 0;
    }

    public Nodo<T> getDer() {
        return der;
    }

    public Nodo<T> getIzq() {
        return izq;
    }
    
    
}
