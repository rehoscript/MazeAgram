/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Anagrama;

import Anagrama.Logica.Letra;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author SONY
 */
public class Listener implements MouseListener,
                                 MouseMotionListener{

    private Logica anagrama;
    private CanvasAnagrama lienzo;
    private int x=0,
                y=0;
    private Point calculoArrastrar;
    
    public Listener(Logica anagrama,CanvasAnagrama lienzo) {
         this.anagrama =anagrama;
         this.lienzo   =lienzo;
         calculoArrastrar=new Point();
        
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        anagrama.buscarLetraSeleccionada(e.getPoint());
        calculoArrastrar=e.getPoint();
    }
    @Override
    public void mouseDragged(MouseEvent e) {   
        Letra ltr=anagrama.getLetraSeleccionada();
        if(ltr!=null){
            x= (e.getX()-calculoArrastrar.x)+anagrama.getLetraSeleccionada().posicionX;
            y= (e.getY()-calculoArrastrar.y)+anagrama.getLetraSeleccionada().posicionY;  
            anagrama.cambiarPosicionLetraSeleccionada(x,y);
            calculoArrastrar.setLocation(e.getPoint());
            lienzo.repintar();
            lienzo.cambiarPunteroArrastrar();
        }
        
        
    }
    @Override
    public void mouseReleased(MouseEvent e){
        if(anagrama.verificarInterseccion(e.getPoint())){
            lienzo.repintar();
                       
            lienzo.cambiarPunteroLibre();  
            if(anagrama.verificarOrdenPalabras()){
                lienzo.ultimaAnimacion();
            } 
        }
        else{
            anagrama.volverPosicionOriginalLetra();
            lienzo.repintar();
            lienzo.cambiarPunteroLibre();  
        }
                    

    }
    @Override
    public void mouseMoved(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e){}
    @Override
    public void mouseClicked(MouseEvent e){}

  
}
