/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeagram;

import java.io.File;
import java.io.FileInputStream;
import java.util.Random;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 *
 * @author Solosimpi
 */
public class MusicFondo implements Runnable{

    File  dir = new File ("Musicx");
    AdvancedPlayer aux;
    String [] ficheros;
    private static final Random rand = new Random();
    
    MusicFondo(){
         ficheros= dir.list();
    }
    
    @Override
    public void run() {
        while(true){           
            try {
                aux=new AdvancedPlayer(new FileInputStream(dir.getPath()+"/"+ficheros[rand.nextInt(ficheros.length)]));
                aux.play();
            } catch (Exception ex) {
            } 
        }
    }
    
    
}
