/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Anagrama;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author SONY
 */
public class Logica {

    private String palabraJuego;
    private Letra letras[],
                  letraSeleccionada;
    private Font fuente;
    private Color colores[]={Color.MAGENTA,Color.RED,Color.BLUE,
                             Color.GRAY,Color.ORANGE,Color.PINK,
                             new Color(30,158,223),new Color(239,83,146),
                             new Color(223,157,205),new Color(1,158,101),
                             new Color(140,83,64),new Color(253,126,71),
                             new Color(249,97,86)};
    
    private final int ALCHO_LETRA   =95,
                      ALTO_LETRA    =95,
                      ESPACIO_LETRAS=8,
                      POSICION_Y    =560;
    private int indiceSeleccionado  =-1;
    
    
    public Logica() {
         palabraJuego="PRUEBA";
        try{
           fuente = Font.createFont(Font.TRUETYPE_FONT,new File("./Fuentes/1.ttf"));
           fuente=fuente.deriveFont(85f);
         }catch (FontFormatException e)
                    {
            
         }catch(IOException e){
             System.out.println(e);
         }
        
         desordenarColores();
         generarLetras();
         desordenarLetras();
         establecerPosicionLetras();
    }
    
     public void cambiarPalabra(String nuevaPalabra){
        if(nuevaPalabra.length()>13)
            palabraJuego="PRUEBA";
        else{
            palabraJuego=nuevaPalabra;
            desordenarColores();
            generarLetras();
            desordenarLetras();
            establecerPosicionLetras();
            
        } 
    }
     
    private void generarLetras(){
        int numeroLetras =palabraJuego.length();
        letras           =new Letra[numeroLetras];
        for(int i=0;i<numeroLetras;i++){
            letras[i]=new Letra( String.valueOf(palabraJuego.charAt(i)),
                                 colores[i]);
        }
    }
    private void establecerPosicionLetras(){
        
        int numeroLetras =palabraJuego.length();
        int posicion_X   =(1360/2)-(((numeroLetras*ALCHO_LETRA)+
                                    ((numeroLetras-1)*ESPACIO_LETRAS))/2);
        for(int i=0;i<letras.length;i++){
             letras[i].establecerPosicion(posicion_X, POSICION_Y);
             posicion_X+=ALCHO_LETRA+ESPACIO_LETRAS;
        }
        
    }
    private void desordenarLetras(){
        Random numAleatorio=new Random();
        int inicio=letras.length-1,
            numAL;
        for (int i=inicio; i>1 ;i--){
            numAL         =numAleatorio.nextInt(inicio);
            Letra tmp     =letras[i];
            letras[i]     =letras[numAL];
            letras[numAL] =tmp ;
        }        
        
    }
    private void desordenarColores(){
        Random numAleatorio =new Random();
        int inicio          =colores.length-1,
            numAL;
        for(int i=inicio; i>1 ;i--){
            numAL=numAleatorio.nextInt(inicio);   
            Color tmp2=(colores[i]);
            colores[i]=colores[numAL];
            colores[numAL]=tmp2;
        }
    }
    public void dibujar(Graphics2D g2){
        
        for(int i=0;i<letras.length;i++){
            if(letras[i]!=getLetraSeleccionada())
                letras[i].dibujarLetra(g2);
        }
        if(getLetraSeleccionada()!=null)
            getLetraSeleccionada().dibujarLetra(g2);
    }
    public boolean verificarOrdenPalabras(){
        String ctr="";
        for(Letra ltr:letras)
            ctr+=ltr.letra;
        
        return (ctr.equals(palabraJuego))? true:false ;
    }
    public void buscarLetraSeleccionada(Point pt){
        for(int i=0;i<letras.length;i++){
            if(letras[i].contienePunto(pt)){
                letraSeleccionada  =letras[i];
                indiceSeleccionado =i;
                return;
            }
        }
        letraSeleccionada=null;
    }
    public void cambiarPosicionLetraSeleccionada(int x,int y){
       if(getLetraSeleccionada()!=null){
            getLetraSeleccionada().cambiarPosicion(x, y);
       }
    }
    public boolean verificarInterseccion(Point a){
        if(getLetraSeleccionada()!=null){
            int x,y,x1,y1;
            for(int i=0;i<letras.length;i++){
                if( letras[i]!=letraSeleccionada && letras[i].bordeLetra.contains(a) ){
                    x=getLetraSeleccionada().posicionOriginalX;
                    y=getLetraSeleccionada().posicionOriginalY;
                    x1=letras[i].posicionOriginalX;
                    y1=letras[i].posicionOriginalY;
                    Letra tmp                  =letras[i];
                    letras[i]                  =getLetraSeleccionada();
                    letras[i].cambiarPosicion(x1, y1);
                    letras[i].fijarPosicion();
                    letras[indiceSeleccionado] =tmp;
                    letras[indiceSeleccionado].cambiarPosicion(x, y);
                    letras[indiceSeleccionado].fijarPosicion();
                    return true;//verifica esto 
                }
            }
        }
        return false;//verificar esto
    }
    public void volverPosicionOriginalLetra(){
        if(getLetraSeleccionada()!=null){
             getLetraSeleccionada().volverPosicionGeneral();
        }
       
    }

    /**
     * @return the letraSeleccionada
     */
    public Letra getLetraSeleccionada() {
        return letraSeleccionada;
    }
    public class Letra{
        private String letra;
        private Color colorLetra;
        RoundRectangle2D.Float bordeLetra,
                               sombra;   
        int posicionX,
            posicionY,
            posicionOriginalX,
            posicionOriginalY;
        public Letra(String letra,Color cl) {
            this.letra=letra;      
            colorLetra=cl;
        }
        public void establecerPosicion(int x,int y){
            posicionOriginalX=posicionX=
                              x;
            posicionOriginalY=posicionY=
                              y;           
            bordeLetra=new RoundRectangle2D.Float(posicionX,posicionY, ALCHO_LETRA, ALTO_LETRA,25,25);
            sombra=new RoundRectangle2D.Float(posicionX+5, posicionY+5, ALCHO_LETRA, ALTO_LETRA, 25, 25);
        }
        public void dibujarLetra(Graphics2D g2){           
            sombra.setFrame(posicionX+5, posicionY+5,ALCHO_LETRA, ALTO_LETRA);
            bordeLetra.setFrame(posicionX, posicionY,ALCHO_LETRA, ALTO_LETRA);
            g2.setColor(Color.BLACK);
            g2.fill(sombra);
            g2.setColor(colorLetra);
            g2.fill(bordeLetra);
            g2.setColor(Color.BLACK);
            g2.setFont(fuente);
            g2.drawString(letra, bordeLetra.x+10,bordeLetra.y+80);
        }
        public boolean contienePunto(Point pt){
            return bordeLetra.contains(pt);
        }
        public void cambiarPosicion(int x,int y){
            posicionX=x;
            posicionY=y;
        }
        public void volverPosicionGeneral(){
            posicionX=posicionOriginalX;
            posicionY=posicionOriginalY;
        }
        public void fijarPosicion(){
            posicionOriginalX=posicionX;
            posicionOriginalY=posicionY;
        }
        @Override
        public String toString() {
            return ""+letra;
        }
    }
}
