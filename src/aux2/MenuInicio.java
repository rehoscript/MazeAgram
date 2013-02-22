/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aux2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import mazeagram.Interface;

/**
 *
 * @author SONY
 */
public class MenuInicio extends Canvas
                        implements KeyListener,
                        MouseListener{
    
    private BufferedImage bufferTextura,
                          nube,
                          ladrillos;
    private TexturePaint patronGeneral,
                         patronGeneral2;
    private  Font fuente,
                  fuente2,
                  fuente3;
    private int indiceNivel=1;
    private String niveles[]={"FACIL","NORMAL","DIFICIL"};
    private GeneralPath tr1,
                        tr2;
    private Rectangle2D boton;
    
    private Interface ventanaJuego;
    
    public MenuInicio(Interface ventanaJuego)  {
        try {
            this.ventanaJuego=ventanaJuego;
            bufferTextura= ImageIO.read(new File("./src/aux2/recursos/texturaFinal.BMP"));
            nube=ImageIO.read(new File("./src/aux2/recursos/NUBE.PNG"));
            ladrillos=ImageIO.read(new File("./src/aux2/recursos/ladrillos.PNG"));    
            Rectangle2D reC=new Rectangle2D.Double(0,0,bufferTextura.getWidth(),bufferTextura.getHeight());
            patronGeneral=new TexturePaint(bufferTextura, reC);
            Rectangle2D reC2=new Rectangle2D.Double(0,0,ladrillos.getWidth(),ladrillos.getHeight());
            patronGeneral2=new TexturePaint(ladrillos, reC2);
            fuente = Font.createFont(Font.TRUETYPE_FONT,new File("./src/aux2/recursos/2.ttf"));
            fuente2=fuente.deriveFont(43.5f);
            fuente3=fuente.deriveFont(110.0f);
            fuente = fuente.deriveFont(60.5f);
            
            tr1=new GeneralPath();
            
           
            
            tr2=new GeneralPath();
            
            
             Cursor c;
             ImageIcon image = new ImageIcon("Imagenes/puntero.png");
             c = Toolkit.getDefaultToolkit().createCustomCursor(image.getImage(), new Point(0,0), "cursorName");
            setCursor(c);
            setFocusable(true);
            addKeyListener(this);
            addMouseListener(this);
        } catch (FontFormatException ex) {
            Logger.getLogger(MenuInicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
            
            
    }
    
  
    
    
    @Override
    public void paint(Graphics g) {
        
        Graphics2D g2d=(Graphics2D)g;
        Image mImagen   =createImage(getWidth(),getHeight());
        
        Graphics2D offG =(Graphics2D) mImagen.getGraphics();
       
        
        
        offG.setPaint(patronGeneral);
        offG.fillRect(0,0,getWidth(),getHeight()-100);
        offG.setColor(Color.BLACK);
        offG.fillRect(0,getHeight()-100,getWidth(),100);
        
//        offG.drawImage(nube, 1000, 100, this); nubes
        
        offG.setPaint(patronGeneral2);
        offG.fillRect(0, getHeight()-100,getWidth(), 100);
        
        
        //Texto
        offG.setFont(fuente3);
        
        offG.setColor(Color.gray);
        offG.drawString("MAZEAGRAM", 45,205);
        offG.setColor(Color.WHITE);
       
        offG.drawString("MAZEAGRAM", 50,200);
        
        offG.setColor(Color.WHITE);
         offG.setFont(fuente);
        offG.drawString("NIVEL", 40, getHeight()-30);
        
        
        
        offG.setFont(fuente2);
        
        int aumento=0;
        switch(indiceNivel){
            case 0:
                aumento=25;
                break;
            case 2:
                aumento=10;
                break;
               
        }
        offG.setColor(new Color(174,217,231));
        offG.drawString(niveles[indiceNivel], 250+aumento, getHeight()-35);
        offG.setColor(new Color(255,216,0));
        //Pasa Nivel
        tr1.moveTo(240, getHeight()-40);//A
        tr1.lineTo(240, getHeight()-60);//B
        tr1.lineTo(190, getHeight()-50);
        tr1.closePath();
        offG.fill(tr1);
        
        tr2.moveTo(400, getHeight()-40);//A
        tr2.lineTo(400, getHeight()-60);//B
        tr2.lineTo(450, getHeight()-50);
        tr2.closePath();
        offG.fill(tr2);
        
        offG.setColor(Color.red);
        boton=new Rectangle2D.Float(590,getHeight()-75,150, 50);
        offG.fill(boton);
        
        offG.setColor(Color.WHITE);
        offG.setFont(fuente);
        offG.drawString("PLAY",600,getHeight()-30);
        offG.setFont(fuente2);
        offG.drawString("ESC (Salir)",getWidth()-200,getHeight()-30);
        
        
        
         
         
         
        g2d.drawImage(mImagen, 0,0,this);//doble buffer
    }
    
    
    public void repintar(){
        paint(getGraphics());
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
         Point pt=e.getPoint();
        if(tr1.contains(pt)){
           if(indiceNivel!=0){
                indiceNivel--;
                repintar();
           }
        }
        else if(tr2.contains(pt)){
            if(indiceNivel!=2){
                indiceNivel++;
                repintar();
            }
        }
        else if(boton.contains(pt)){
            
            switch(indiceNivel){
                case 0:
                    ventanaJuego.establecerJuego1(Interface.NIVEL_FACIL);
                    break;
                case 1:
                     ventanaJuego.establecerJuego1(Interface.NIVEL_NORMAL);
                    break;
                case 2:
                     ventanaJuego.establecerJuego1(Interface.NIVEL_DIFICIL);
                    break;
            }
            
            
        }
    }
    @Override
    public void mouseClicked(MouseEvent e){}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void keyReleased(KeyEvent e){}
       
}
