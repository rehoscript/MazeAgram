/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeagram;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Solosimpi
 */
public class Pantallalateral extends Canvas implements Runnable{
    Toolkit tk;
    ArrayList chestabiertos;
    int aux=0;
    BufferedImage panelx;
    BufferedImage logo;
    String cadena;
    String cadena2;
    String cadena3;
    String cadena4;
    String cadena5="";
    String cadena6="";
    Rectangle bounds1;
    int cont=0;
    long tiempo=System.currentTimeMillis();
    int tamaño;
    Font fuente;
    TexturePaint texturafondo;
    private boolean banderaSalida=true;
    
    Pantallalateral(ArrayList chestabiertos,int tamaño) {
        this.chestabiertos=chestabiertos;
        this.tamaño=tamaño;
        tk = Toolkit.getDefaultToolkit();
        Cursor c;
        ImageIcon image = new ImageIcon("Imagenes/puntero.png");
        c = tk.createCustomCursor(image.getImage(), new Point(0,0), "cursorName");
        setCursor(c);
        Dimension dimwin = getSize(), dv = tk.getScreenSize();
        setSize(300,dv.height);
        try{
        BufferedImage aux=(ImageIO.read(new File("Textures/ltal.jpg")));
        Rectangle2D.Float auxrect=new Rectangle2D.Float(0,0,40,40);
        texturafondo=new TexturePaint(aux,auxrect);
        panelx=(ImageIO.read(new File("Textures/panel.png")));
        logo=(ImageIO.read(new File("Textures/logo.png")));
        fuente = Font.createFont(Font.TRUETYPE_FONT,new File("Fuentes/1.ttf"));
        fuente=fuente.deriveFont(28f);
        bounds1=new Rectangle(30,logo.getHeight()+200,10,10);
        cadena3=" ";
        cadena="Bienvenido a";
        cadena2=" Mazeagram";
        cadena4="  Muevete";
        cadena5="   usando";
        cadena6="  W A S D";

        }catch(Exception e){
        }
   }
    
    public void paint(Graphics g){
        Graphics2D g2D= (Graphics2D)g;
        g2D.setPaint(texturafondo);
        g2D.fillRect(0,0,tk.getScreenSize().width,tk.getScreenSize().height);
        g2D.setStroke(new BasicStroke(5));
        g2D.setColor(Color.BLACK);
        g2D.drawRect(0,0,tk.getScreenSize().width,tk.getScreenSize().height);
        g2D.drawImage(logo,10,0, this);
        g2D.drawImage(panelx,0,logo.getHeight()+30, this);
        g2D.setFont(fuente);
        g2D.drawString(cadena,bounds1.x,bounds1.y);
        g2D.drawString(cadena2,bounds1.x,bounds1.y+30);
        g2D.drawString(cadena3,bounds1.x,bounds1.y+60);
        g2D.drawString(cadena4,bounds1.x,bounds1.y+90);
        g2D.drawString(cadena5,bounds1.x,bounds1.y+120);
        g2D.drawString(cadena6,bounds1.x,bounds1.y+150);
    }
    
    public void update(Graphics g) {
	Graphics offgc;
	Image offscreen = null;
	Dimension d = getSize();
        Graphics2D g2D = (Graphics2D) g;
	// create the offscreen buffer and associated Graphics
	offscreen = createImage(d.width, d.height);
	offgc = offscreen.getGraphics();
	// clear the exposed area
	offgc.setColor(getBackground());
	offgc.fillRect(0, 0, d.width, d.height);
	offgc.setColor(getForeground());
	// do normal redraw
	paint(offgc);
	// transfer offscreen to window
	g.drawImage(offscreen, 0, 0, this);
   }
    
    public void terminarHilo(){
        banderaSalida=false;
    }
    @Override
    public void run() {
        while(banderaSalida){
            if(aux!=chestabiertos.size()){ 
                cadena2=" Has encontrado ";
                cadena3="   la letra "+((Chest)chestabiertos.get(chestabiertos.size()-1)).letra;
                cadena=cadena4=" ";
                fuente=fuente.deriveFont(20f);
                aux=chestabiertos.size();
                                cadena5="";
                cadena6="";
                for (int i = 0; i < chestabiertos.size(); i++) {
                    if(i<=8) cadena5=cadena5+" "+((Chest)chestabiertos.get(i)).letra;
                    else cadena6=cadena6+" "+((Chest)chestabiertos.get(i)).letra;
                }
                update(getGraphics());
                tiempo=System.currentTimeMillis();
            }
            if (System.currentTimeMillis()-tiempo>5000) { // actualizamos cada 25 milisegundos
                if(cont==0){ cadena="Ayuda a nuestro";
                             cadena2=" amigo a juntar";
                             cadena3="   todos los ";
                             cadena4="    tesoros"; cont++; fuente=fuente.deriveFont(20f);}
                else{ cadena="    Tienes "+chestabiertos.size();
                      cadena2="     letras,";
                      cadena3="  Te faltan "+(tamaño-chestabiertos.size());
                      cadena4=" ";
                       fuente=fuente.deriveFont(20f);
                }
                if(chestabiertos.size()==tamaño){ cadena=" Bien hecho! ";
                        cadena2=" ahora sal";
                        cadena3="del laberinto";
                        cadena4=" ";
                        fuente=fuente.deriveFont(20f);
                }
                cadena5="";
                cadena6="";
                for (int i = 0; i < chestabiertos.size(); i++) {
                    if(i<7) cadena5=cadena5+" "+((Chest)chestabiertos.get(i)).letra;
                    else cadena6=cadena6+" "+((Chest)chestabiertos.get(i)).letra;
                }
                tiempo=System.currentTimeMillis();
                update(getGraphics());
            } 
        }
    }
}
