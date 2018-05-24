/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

/**
 *
 * @author lucho
 */
public class Drawer {
    
    public static JFreeChart generarHistograma(String nombre,String etiquetaX, String etiquetaY,Imagen img){
        HistogramDataset datos=new HistogramDataset();
        datos.setType(HistogramType.FREQUENCY);
        double [] arre = img.getDPixeles();
        datos.addSeries("Imagen", arre, Imagen.NRO_DE_COLORES);
        JFreeChart chart= ChartFactory.createHistogram(nombre, etiquetaX, etiquetaY, datos, PlotOrientation.VERTICAL, true, true, false);
        return chart;
    }
    
    public static void dibujarDistribucion(JFreeChart dibujo,JPanel panel){
        panel.removeAll();
        panel.repaint();
        panel.setLayout(new java.awt.BorderLayout());
        panel.add(new ChartPanel(dibujo));
        panel.validate();  
    }
    
    public static void guardarJPEG(JFreeChart dibujo,String dir){
        try {
            OutputStream out= new FileOutputStream(new File(dir));
            ChartUtilities.writeChartAsJPEG(out, dibujo, 400, 300);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Drawer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Drawer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
