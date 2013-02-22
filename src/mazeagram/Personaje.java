/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeagram;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Solosimpi
 */
public class Personaje extends Canvas{

    Rectangle bounds;
    BufferedImage pnjimg;
    BufferedImage pnjimg1;
    BufferedImage pnjimg2;
    BufferedImage pnjimg3;
    BufferedImage pnjimg4;
    boolean arriba,abajo,izquierda,derecha;
    int trans=0;
    int dx;
    int tipo;
    
    Personaje(int x,int y,int w, int h,int tipo){
        this.tipo=tipo;
        arriba=abajo=izquierda=derecha=false;
        bounds= new Rectangle(x,y,w,h);
        setSize(bounds.getSize());
        pnjimg1=generarImagen();
        pnjimg2=generarImagen();
        pnjimg3=generarImagen();
        pnjimg4=generarImagen();
        pnjimg=pnjimg1;
    }
    
    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D)g;
        if(tipo==1)g2D.setColor(Color.WHITE);
        else g2D.setColor(Color.yellow);
        if(trans==0){
            g2D.fillRect(bounds.x,bounds.y+30,bounds.width,bounds.height-40+dx);
            if(tipo==1)g2D.fillOval(bounds.x,bounds.y,bounds.width,((bounds.height+10)/2)+dx);
            else g2D.fillRoundRect(bounds.x,bounds.y,bounds.width,((bounds.height+10)/2)+dx,5,5);
            g2D.fillOval(bounds.x-6,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+4,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+24,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+44,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+64,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+84,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+104,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.setColor(Color.BLACK);
            g2D.fillOval(bounds.x+15,bounds.y+30,35,45);
            g2D.fillOval(bounds.x+45,bounds.y+30,35,40);
            if(tipo==1)g2D.setColor(Color.WHITE);
            else g2D.setColor(Color.yellow);
            g2D.fillOval(bounds.x+17,bounds.y+45,10,10);
            g2D.fillOval(bounds.x+47,bounds.y+45,10,10);
            trans++;
        } else if(trans==1){
            
            g2D.fillRect(bounds.x,bounds.y+33,bounds.width,bounds.height-40+dx-3);
            if (tipo==1)g2D.fillOval(bounds.x,bounds.y+3,bounds.width,((bounds.height+10)/2)+dx);
            else g2D.fillRoundRect(bounds.x,bounds.y+3,bounds.width,((bounds.height+10)/2)+dx,5,5);
            g2D.fillOval(bounds.x-2,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+18,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+38,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+58,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+78,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+98,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.setColor(Color.BLACK);
            g2D.fillOval(bounds.x+15,bounds.y+30,35,45);
            g2D.fillOval(bounds.x+45,bounds.y+30,35,40);
            if(tipo==1)g2D.setColor(Color.WHITE);
            else g2D.setColor(Color.yellow);
            g2D.fillOval(bounds.x+17,bounds.y+45,10,10);
            g2D.fillOval(bounds.x+47,bounds.y+45,10,10);
            trans++;
        } else if(trans==2){
            
            g2D.fillRect(bounds.x,bounds.y+35,bounds.width,bounds.height-40+dx-5);
            if(tipo==1)g2D.fillOval(bounds.x,bounds.y+5,bounds.width,((bounds.height+10)/2)+dx);
            else g2D.fillRoundRect(bounds.x,bounds.y+5,bounds.width,((bounds.height+10)/2)+dx,5,5);
            g2D.fillOval(bounds.x-9,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+11,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+31,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+51,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+71,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+91,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.setColor(Color.BLACK);
            g2D.fillOval(bounds.x+15,bounds.y+30,35,45);
            g2D.fillOval(bounds.x+45,bounds.y+30,35,40);
            if(tipo==1)g2D.setColor(Color.WHITE);
            else g2D.setColor(Color.yellow);
            g2D.fillOval(bounds.x+35,bounds.y+45,10,10);
            g2D.fillOval(bounds.x+65,bounds.y+45,10,10);
            trans++;
        } else{
            g2D.fillRect(bounds.x,bounds.y+36,bounds.width,bounds.height-40+dx-6);
            if(tipo==1)g2D.fillOval(bounds.x,bounds.y+6,bounds.width,((bounds.height+10)/2)+dx);
            else g2D.fillRoundRect(bounds.x,bounds.y+6,bounds.width,((bounds.height+10)/2)+dx,5,5);
            g2D.fillOval(bounds.x-11,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+9,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+29,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+49,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+69,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.fillOval(bounds.x+89,(bounds.y+30)+(bounds.height-40+dx)-10,24,20);
            g2D.setColor(Color.BLACK);
            g2D.fillOval(bounds.x+15,bounds.y+30,35,45);
            g2D.fillOval(bounds.x+45,bounds.y+30,35,40);
            if(tipo==1)g2D.setColor(Color.WHITE);
            else g2D.setColor(Color.yellow);
            g2D.fillOval(bounds.x+35,bounds.y+45,10,10);
            g2D.fillOval(bounds.x+65,bounds.y+45,10,10);
            trans++;
        }
        //g2D.setColor(Color.BLACK);
        //g2D.fillOval(bounds.x,bounds.y,bounds.width,bounds.height);
    }
    
    public BufferedImage generarImagen() {
        int w = getWidth();
        int h = getHeight();
        int type = BufferedImage.TYPE_INT_ARGB;;
        BufferedImage image = new BufferedImage(w,h,type);
        Graphics2D g2 = image.createGraphics();
        paint(g2);
        g2.dispose();
        return image;
    }
}
