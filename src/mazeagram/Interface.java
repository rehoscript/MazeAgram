/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeagram;

import Anagrama.CanvasAnagrama;
import aux2.MenuInicio;
import aux2.PanelSalida;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import javax.swing.JFrame;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 *
 * @author Solosimpi
 */
public class Interface extends JFrame {

    Pantalla juego1;//hilos
    Pantallalateral juego11;//hilos
    MusicFondo musica;//hilos
    ArrayList chestabiertos;
    int nivel;
    
    private String palabraGeneral;
    private MenuInicio menu;//
    private PanelSalida salida;
    public static int NIVEL_FACIL=5,
                      NIVEL_NORMAL=8,
                      NIVEL_DIFICIL=10;
    private CanvasAnagrama juego2;
    Thread hilo3;
    Interface() {
        palabraGeneral="";
        setLayout(new BorderLayout());
        Toolkit tk = Toolkit.getDefaultToolkit();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        Dimension dv = tk.getScreenSize();
        setSize(dv);
        
        
        menu=new MenuInicio(this);
       
        add("Center",menu);
        musica=new MusicFondo();
        hilo3=new Thread(musica);
        hilo3.start();
//        juego1 = new Pantalla(5,5);
//        chestabiertos=juego1.chestabiertos;//para comunicacion entre paneles
//        juego11=new Pantallalateral(chestabiertos,juego1.maze.palabra.length());
//        musica=new MusicFondo();
//        add("Center", juego1);
//        add("East",juego11);      
//        while(juego1.finalizado){
//            System.out.println("mau");
//        }
        setVisible(true);
    }
    
    public void establecerMenu(){
        menu=new MenuInicio(this);
        add("Center",menu);
        remove(salida);
        //menu.paintComponents(menu.getGraphics());
        paintComponents(getGraphics());
        
    }
    
    public void establecerSalida(){
        
        remove(juego2);
        salida=new PanelSalida(this);
        add(salida);
        juego2=null;
        paintComponents(getGraphics());
    }
    
    /*
     * Nivel 5 facil
     *       8 normal
     *       10 dificil
     */
    public void establecerJuego1(int nivel){
        remove(menu);
        hilo3.stop();
        musica.dir= new File ("Music");
        musica.ficheros= musica.dir.list();
        try{
        AdvancedPlayer aux=new AdvancedPlayer(new FileInputStream("sfx/21.mp3"));
        aux.play(0,150);
        } catch(Exception e){}
        hilo3=new Thread(musica);
        hilo3.start();
        
        this.nivel=nivel;
        juego1= new Pantalla(this,nivel,nivel);
        chestabiertos=juego1.chestabiertos;//para comunicacion entre paneles
        juego11=new Pantallalateral(chestabiertos,juego1.maze.palabra.length());
        palabraGeneral=juego1.maze.palabra;
        add("Center", juego1);
        add("East",juego11);
        paintComponents(getGraphics());        
        
        Thread hilo1=new Thread(juego1);//.start();
        hilo1.start();
        juego1.requestFocus();
        Thread hilo2=new Thread(juego11);//.start();
        hilo2.start();
    }
    public void establecesJuego2(){
                hilo3.stop();
        musica.dir= new File ("Musicx");
        musica.ficheros= musica.dir.list();
        try{
        AdvancedPlayer aux=new AdvancedPlayer(new FileInputStream("sfx/walk.mp3"));
        aux.play();
        aux=new AdvancedPlayer(new FileInputStream("sfx/final.mp3"));
        aux.play();
        } catch(Exception e){}
        remove(juego1);
        remove(juego11);
        
        hilo3=new Thread(musica);
        hilo3.start();
       
        juego2=new CanvasAnagrama(this, palabraGeneral,nivel);//este es el que va
        add(juego2);
        paintComponents(getGraphics());
        juego1=null;
        juego11=null;
        
    }
    public void terminarHiloPL(){
        juego11.terminarHilo();
    }
    
    

    public static void main(String[] args) {
        new Interface();
    }
}
