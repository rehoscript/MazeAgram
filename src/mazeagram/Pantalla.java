/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeagram;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Solosimpi
 */
public class Pantalla extends Canvas implements Runnable{
    MazeGenerator maze;
    private Personaje pnj;
    Toolkit tk;
    BufferedImage laboscuro;
    KeyEventDemo aux1;
    int velocidad=10;
    int iluminacionradio=300;
    long tiempo=System.currentTimeMillis();
    long tiempo2=System.currentTimeMillis();
    Sfx opens;
    int ancho,alto;
    ArrayList chestabiertos;
    Boolean finalizado=false;
    private Interface ventanaJuego;
    
    Pantalla(Interface ventanaJuego,int x, int velocidad){
        this.ventanaJuego=ventanaJuego;
        this.velocidad=velocidad;
        tk = Toolkit.getDefaultToolkit();
        Cursor c;
        ImageIcon image = new ImageIcon("Imagenes/puntero.png");
        c = tk.createCustomCursor(image.getImage(), new Point(0,0), "cursorName");
        setCursor(c);
        chestabiertos=new ArrayList();
        maze=new MazeGenerator(x,x);
        pnj= new Personaje(0,0,100,100,maze.getRandom(2));
        pnj.bounds.x=(tk.getScreenSize().width/2)-50-150;
        pnj.bounds.y=(tk.getScreenSize().height/2)-50;
        maze.inicioy=(tk.getScreenSize().height/2)-maze.ancho-(pnj.bounds.height+maze.ancho)/2;
        maze.iniciox=(tk.getScreenSize().width/2)-maze.ancho-(pnj.bounds.width+maze.ancho)/2-150;
        maze.salida.x=maze.salida.x+maze.iniciox;
        maze.salida.y=maze.salida.y+maze.inicioy;
        for (int i = 0; i < maze.chests.length; i++) {
            maze.chests[i].bounds.x=maze.chests[i].bounds.x+maze.iniciox;
            maze.chests[i].bounds.y=maze.chests[i].bounds.y+maze.inicioy;
        }
        creaLaboscuro();
        

        opens=new Sfx("sfx/opens.mp3",3);
        
        aux1= new KeyEventDemo();
        addKeyListener(aux1);
        Dimension dimwin = getSize(), dv = tk.getScreenSize();
        setSize(dv.width-300,dv.height);
    }
    
    public void creaLaboscuro(){
        float alpha = .4f;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha); 
        laboscuro=new BufferedImage(maze.labimg.getWidth(),maze.labimg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gbi = laboscuro.createGraphics();
        gbi.setColor(Color.black);
        gbi.drawRect(0,0,maze.labimg.getWidth(), maze.labimg.getHeight());
        gbi.setComposite(ac);
        gbi.drawImage(maze.labimg,0,0,this);
    }
    
    public void clipping(BufferedImage img,int x,int y){
        Graphics2D g2D= (Graphics2D)img.getGraphics();
        g2D.clip(new Ellipse2D.Double(x-150,y,iluminacionradio,iluminacionradio));
        g2D.drawImage(img,0,0,this);
    }
    
    public Graphics2D getGrap(){
        return (Graphics2D)getGraphics();
    }
    
    public void paint(Graphics g){
        Graphics2D g2D= (Graphics2D)g;
        g2D.setColor(Color.black);
        g2D.fillRect(0,0,tk.getScreenSize().width,tk.getScreenSize().height);
        creaLaboscuro();
        clipping(laboscuro,(tk.getScreenSize().width/2)-maze.iniciox-iluminacionradio/2,(tk.getScreenSize().height/2)-maze.inicioy-iluminacionradio/2);
        g2D.drawImage(laboscuro,maze.iniciox,maze.inicioy,this);
        for (int i = 0; i < maze.chests.length; i++) {
            if(maze.chests[i].open){
                g2D.drawImage(maze.chestopen,maze.chests[i].bounds.x,maze.chests[i].bounds.y, this);
                
            }
        }
        pintarpnj(g2D);
    }
    
    public void pintarpnj(Graphics2D g2D){
        g2D.drawImage(pnj.pnjimg,pnj.bounds.x,pnj.bounds.y,this);
        if(pnj.abajo||pnj.arriba||pnj.izquierda||pnj.derecha){
            g2D.setColor(Color.BLACK);
            g2D.fillOval(pnj.bounds.x+15,pnj.bounds.y+30,35,45);
            g2D.fillOval(pnj.bounds.x+45,pnj.bounds.y+30,35,40);
        }
        if(pnj.izquierda){
        if(pnj.tipo==1)g2D.setColor(Color.WHITE);
        else g2D.setColor(Color.yellow);
        g2D.fillOval(pnj.bounds.x+17,pnj.bounds.y+45,10,10);
        g2D.fillOval(pnj.bounds.x+47,pnj.bounds.y+45,10,10);}
        if(pnj.derecha){          
            if(pnj.tipo==1)g2D.setColor(Color.WHITE);
            else g2D.setColor(Color.yellow);
            g2D.fillOval(pnj.bounds.x+35,pnj.bounds.y+45,10,10);
            g2D.fillOval(pnj.bounds.x+65,pnj.bounds.y+45,10,10);
        }
        if(pnj.arriba){
            if(pnj.tipo==1)g2D.setColor(Color.WHITE);
            else g2D.setColor(Color.yellow);
            g2D.fillOval(pnj.bounds.x+29,pnj.bounds.y+30,10,10);
            g2D.fillOval(pnj.bounds.x+56,pnj.bounds.y+30,10,10);
        }
        if(pnj.abajo){
            if(pnj.tipo==1)g2D.setColor(Color.WHITE);
            else g2D.setColor(Color.yellow);
            g2D.fillOval(pnj.bounds.x+29,pnj.bounds.y+55,10,10);
            g2D.fillOval(pnj.bounds.x+56,pnj.bounds.y+55,10,10);
        }
        //g2D.setColor(Color.BLACK);
        
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


    public void run() {
        Boolean bandera=true;
        int bandera1=0;
        //requestFocus();
        while(true){
            velocidad=10;
        Rectangle aux= new Rectangle(maze.iniciox,maze.inicioy,maze.labimg.getWidth(),maze.labimg.getHeight());
        if(pnj.arriba){
            this.mueveCofresAb();
            maze.inicioy+=velocidad; maze.salida.y+=velocidad;
            aux= new Rectangle(maze.iniciox,maze.inicioy,maze.labimg.getWidth(),maze.labimg.getHeight());
            while(isPixelCollide(pnj.pnjimg1,maze.loglab,pnj.bounds,aux)){
                maze.inicioy-=velocidad; maze.salida.y-=velocidad;
                pnj.arriba=false;
                aux= new Rectangle(maze.iniciox,maze.inicioy,maze.labimg.getWidth(),maze.labimg.getHeight());
                removeKeyListener(aux1);
                this.mueveCofresA();
            } addKeyListener(aux1);// pnj.pnjimg=pnj.generarImagen();
        }
        if(pnj.abajo){
            maze.inicioy-=velocidad; maze.salida.y-=velocidad;
            aux= new Rectangle(maze.iniciox,maze.inicioy,maze.labimg.getWidth(),maze.labimg.getHeight());
            this.mueveCofresA();
            while(isPixelCollide(pnj.pnjimg1,maze.loglab,pnj.bounds,aux)){
                    removeKeyListener(aux1);
                    maze.inicioy+=velocidad; maze.salida.y+=velocidad;
                    pnj.abajo=false;
                    aux= new Rectangle(maze.iniciox,maze.inicioy,maze.labimg.getWidth(),maze.labimg.getHeight());
                    this.mueveCofresAb();
                } addKeyListener(aux1);
            }
            if(pnj.izquierda){ 
                maze.iniciox+=velocidad; maze.salida.x+=velocidad;
                aux= new Rectangle(maze.iniciox,maze.inicioy,maze.labimg.getWidth(),maze.labimg.getHeight());
                this.mueveCofresI();
                while(isPixelCollide(pnj.pnjimg1,maze.loglab,pnj.bounds,aux)){
                    removeKeyListener(aux1);
                    maze.iniciox-=velocidad; maze.salida.x-=velocidad;
                    pnj.izquierda=false;
                    aux= new Rectangle(maze.iniciox,maze.inicioy,maze.labimg.getWidth(),maze.labimg.getHeight());
                    this.mueveCofresD();
                } addKeyListener(aux1);
            }
            if(pnj.derecha){ 
                maze.iniciox-=velocidad; maze.salida.x-=velocidad;
                aux= new Rectangle(maze.iniciox,maze.inicioy,maze.labimg.getWidth(),maze.labimg.getHeight());
                this.mueveCofresD();
                while(isPixelCollide(pnj.pnjimg1,maze.loglab,pnj.bounds,aux)){
                    removeKeyListener(aux1);
                    pnj.derecha=false; maze.salida.x+=velocidad;
                    maze.iniciox+=velocidad;
                    aux= new Rectangle(maze.iniciox,maze.inicioy,maze.labimg.getWidth(),maze.labimg.getHeight());
                    this.mueveCofresI();
                } addKeyListener(aux1);
            }
            if (System.currentTimeMillis()-tiempo>70) { // actualizamos cada 25 milisegundos
                if(bandera){
                iluminacionradio+=10; 
                bandera=false;
                } else{iluminacionradio-=10; bandera=true;}
                tiempo=System.currentTimeMillis();
            } if(pnj.bounds.intersects(maze.salida)&&maze.chests.length==chestabiertos.size()){ 
                System.out.println("LLegaste a salida");
                finalizado=true;
                ventanaJuego.terminarHiloPL();//
                ventanaJuego.establecesJuego2();//
                break;
            }
            if (System.currentTimeMillis()-tiempo2>100) { // actualizamos cada 25 milisegundos
                if(bandera1==0){
                    pnj.pnjimg=pnj.pnjimg2; 
                    bandera1++;
                } else if(bandera1==1){pnj.pnjimg=pnj.pnjimg3; bandera1++;}
                else if(bandera1==2){ pnj.pnjimg=pnj.pnjimg4; bandera1++;
                } else{pnj.pnjimg=pnj.pnjimg1; bandera1=0;}
                tiempo2=System.currentTimeMillis();
            }
            colicofres();
            update(getGrap());
        }
    }
    
    public void colicofres(){
        for (int i = 0; i < maze.chests.length; i++) {
            if(maze.chests[i].bounds.intersects(pnj.bounds)&&!maze.chests[i].open){ maze.chests[i].open=true;
                new Thread(opens).start();
                chestabiertos.add(maze.chests[i]);
            }
        }
    }
    
    public void mueveCofresI(){
        for (int i = 0; i < maze.chests.length; i++) {
            maze.chests[i].bounds.x+=velocidad;
        }
    }
    
    public void mueveCofresD(){
        for (int i = 0; i < maze.chests.length; i++) {
            maze.chests[i].bounds.x-=velocidad;
        }
    }
    
    public void mueveCofresA(){
        for (int i = 0; i < maze.chests.length; i++) {
            maze.chests[i].bounds.y-=velocidad;
        }
    }
    public void mueveCofresAb(){
        for (int i = 0; i < maze.chests.length; i++) {
            maze.chests[i].bounds.y+=velocidad;
        }
    }
    
    public boolean isPixelCollide(BufferedImage img1,BufferedImage img2,Rectangle bounds1, Rectangle bounds2) {
      // initialization
        if(bounds1.intersects(bounds2)){
        BufferedImage image1=img1;
        BufferedImage image2=img2;
        double x1=bounds1.x,y1=bounds1.y,x2=bounds2.x,y2=bounds2.y;
        double width1 = x1 + image1.getWidth() -1,
        height1 = y1 + image1.getHeight() -1,
        width2 = x2 + image2.getWidth() -1,
        height2 = y2 + image2.getHeight() -1;
      
        int xstart = (int) Math.max(x1, x2),
        ystart = (int) Math.max(y1, y2),
        xend   = (int) Math.min(width1, width2),
        yend   = (int) Math.min(height1, height2);

        // intersection rect
        int toty = Math.abs(yend - ystart);
        int totx = Math.abs(xend - xstart);

        for (int y=1;y < toty-1;y++){
            int ny = Math.abs(ystart - (int) y1) + y;
            int ny1 = Math.abs(ystart - (int) y2) + y;

            for (int x=1;x < totx-1;x++) {
                int nx = Math.abs(xstart - (int) x1) + x;
                int nx1 = Math.abs(xstart - (int) x2) + x;
                try {
                    if (((image1.getRGB(nx,ny) & 0xFF000000) != 0x00) &&
                    ((image2.getRGB(nx1,ny1) & 0xFF000000) != 0x00)) {
                    return true;
            }
        } catch (Exception e) {}
    }}
  }
  return false;
  }

  
    public class KeyEventDemo  implements KeyListener {

        public void keyTyped(KeyEvent ke) {
        }

        public Graphics2D getGrap(){
            return (Graphics2D)getGraphics();
        }
        
        
        public void keyPressed(KeyEvent ke) {
             if(ke.getKeyText(ke.getKeyCode()).contentEquals("W")){
                 pnj.arriba=true;
             }
             if(ke.getKeyText(ke.getKeyCode()).contentEquals("A")){
                 pnj.izquierda=true;
             }
             if(ke.getKeyText(ke.getKeyCode()).contentEquals("S")){
                pnj.abajo=true; 
             }
             if(ke.getKeyText(ke.getKeyCode()).contentEquals("D")){
                 pnj.derecha=true; 
             }
             if(ke.getKeyCode()==KeyEvent.VK_ESCAPE){
             System.exit(0);
        }
        }
        

        public void keyReleased(KeyEvent ke) {
            pnj.abajo=pnj.arriba=pnj.derecha=pnj.izquierda=false;
        }
   }


}
