/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeagram;

import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 *
 * @author Solosimpi
 */
public class Sfx implements Runnable{
    private String filename;
    private int t;
    AdvancedPlayer aux;
    
    Sfx(String s,int t) {
        this.filename=s;
        this.t=t;
    }
    
    @Override
    public void run() {
        try {
            playSound();
        } catch (Exception ex) { System.out.println("no");
        }
    }
    
    public void playSound() throws Exception{
        aux= new AdvancedPlayer(new FileInputStream(filename));
        aux.play();
    }
    
    
}
