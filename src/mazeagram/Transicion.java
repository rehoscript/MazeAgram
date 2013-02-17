/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeagram;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Solosimpi
 */
public class Transicion extends Canvas implements Runnable{
    BufferedImage laboscuro;
    BufferedImage fondo;
    int iluminacionradio;
    Toolkit tk;
    long tiempo=System.currentTimeMillis();
    
    Transicion(){
        tk = Toolkit.getDefaultToolkit();
        try{
            fondo=(ImageIO.read(new File("Imagenes/Fondo.png")));
        } catch(Exception e){
            System.out.println("no se lee");
        }
        iluminacionradio=0;
        setSize(tk.getScreenSize());
                creaLaboscuro();
    }
    
    public void creaLaboscuro(){
        float alpha = .01f;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha); 
        laboscuro=new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gbi = laboscuro.createGraphics();
        gbi.setColor(Color.black);
        gbi.drawRect(0,0,getWidth(),getHeight());
        gbi.setComposite(ac);
        gbi.drawImage(fondo,0,0,this);
    }
    
    public void clipping(BufferedImage img,int x,int y){
        Graphics2D g2D= (Graphics2D)img.getGraphics();
        g2D.clip(new Ellipse2D.Double(x,y,iluminacionradio,iluminacionradio));
        g2D.drawImage(img,0,0,this);
    }
        
    public void paint(Graphics g){
        Graphics2D g2D= (Graphics2D)g;
        g2D.setColor(Color.black);
        g2D.fillRect(0,0,(tk.getScreenSize().width),(tk.getScreenSize().height));
        //creaLaboscuro();
        clipping(laboscuro,(tk.getScreenSize().width/2)-(iluminacionradio/2),(tk.getScreenSize().height/2)-(iluminacionradio/2));
        g2D.drawImage(laboscuro,0,0,this);
    }
    
    @Override
    public void run() {
        while(true){
        if (System.currentTimeMillis()-tiempo>400) {
            iluminacionradio+=20;
            update(getGraphics());
        }
        if(iluminacionradio>tk.getScreenSize().width){ System.out.println("Se acabo animacion");
            break;
        
        }
        }
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

}
