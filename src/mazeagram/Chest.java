/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeagram;

import java.awt.Rectangle;

/**
 *
 * @author Solosimpi
 */
public class Chest {
    boolean open;
    String letra;
    Rectangle bounds;
    
    Chest(int x,int y,int w, int h, String letra){
        open=false;
        bounds=new Rectangle(x,y,w,h);
        this.letra=letra;
    }
}
