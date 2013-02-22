/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Anagrama;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import mazeagram.Interface;

/**
 *
 * @author SONY
 * 
 * modificado por git
 */
public class CanvasAnagrama extends Canvas
                     implements Runnable,
                     KeyListener{
    
    private Logica anagrama;
    private ImageIcon imagenCursor,
                      imagenCursor2;
    private Cursor cursorAnagrama,
                   cursorAnagrama2;
    private Listener listenerCanvas;
    
    private BufferedImage bufferSuelo,
                          bufferPasto,
                          bufferMarco,
                          bufferNube,
                          bufferRH,
                          bufferDialogo,
                          bufferM1,
                          bufferM2,
                          bufferM3,
                          bufferImagenPalabra;
//                          bufferPlanta;
    Rectangle2D.Double resSuelo,
                       resPasto,
                       recPasto,
                       recRecuadro;
    TexturePaint patronSuelo,
                 patronPasto,
                 patronPasto2,
                 patronRecuadro;
    private Font fuente;
    
    
    private final int ALTO_SUEL0=371;
    private int inicioNubes=-400,
                tiempoMensajeInicio=1,
                tipoMensaje=1;
    
    private double anguloRotacion=0d;
//    private boolean bandera=false;;
    private boolean banderaMensajes=false,
                    banderaOjos=false;
      double cX=0,
             cY=0;
      
    private Interface frame;
    private boolean banderaHilo=true;
    
    public CanvasAnagrama(Interface frame , String palabra,int nivel){
        this.frame=frame;
        this.setFocusable(true);
        anagrama       =new Logica();
        anagrama.cambiarPalabra(palabra);
        listenerCanvas =new Listener(anagrama,this);
        setBackground(new Color(104,156,248));
        imagenCursor=new ImageIcon("./Imagenes/Puntero2.png");
        
        imagenCursor2=new ImageIcon("./Imagenes/PunteroArrastrar.png");
        cursorAnagrama=Toolkit.getDefaultToolkit().createCustomCursor(imagenCursor.getImage(),
                                                                      new Point(0,0) , 
                                                                      "CursorAnagrama");
        cursorAnagrama2=Toolkit.getDefaultToolkit().createCustomCursor(imagenCursor2.getImage(),
                                                                      new Point(0,0) , 
                                                                      "CursorAnagrama2");
        
        setCursor(cursorAnagrama);
        addKeyListener(this);
         try {
            bufferSuelo = ImageIO.read(new File("./Imagenes/patronSuelo3.BMP"));
            bufferPasto = ImageIO.read(new File("./Imagenes/patronPasto3.BMP"));
            bufferRH    = ImageIO.read(new File("./Imagenes/rehilete.PNG"));
//            bufferPlanta= ImageIO.read(new File("./Imagenes/planta2.PNG"));
            bufferNube = ImageIO.read(new File("./Imagenes/nube.PNG"));
            bufferMarco= ImageIO.read(new File("./Imagenes/marco.png"));
            bufferDialogo=ImageIO.read(new File("./Imagenes/dialogo2.PNG"));
            bufferM1=ImageIO.read(new File("./Imagenes/m1.BMP"));
            bufferM2=ImageIO.read(new File("./Imagenes/m2.BMP"));
            bufferM3=ImageIO.read(new File("./Imagenes/m3.BMP"));
            
            switch(nivel){
                case 5:
                    bufferImagenPalabra=ImageIO.read(new File("./niveles/facil/"+palabra+".PNG"));
                    break;
                case 8:
                    bufferImagenPalabra=ImageIO.read(new File("./niveles/medio/"+palabra+".PNG"));
                    break;
                case 10:
                    bufferImagenPalabra=ImageIO.read(new File("./niveles/dificil/"+palabra+".PNG"));
                    break;
            }
            
            resSuelo=new Rectangle2D.Double(0,0,bufferSuelo.getWidth(),bufferSuelo.getHeight());          //RectanguloPatron
            resPasto =new Rectangle2D.Double(0,0,bufferPasto.getWidth(),bufferPasto.getHeight());         
            patronSuelo=new TexturePaint(bufferSuelo, resSuelo);
            patronPasto=new TexturePaint(bufferPasto, resPasto);
            cX=(int)(1100+(bufferRH.getWidth()/2));
            cY=(int)(300+(bufferRH.getHeight()/2));
            fuente = Font.createFont(Font.TRUETYPE_FONT,new File("./Fuentes/15.ttf"));
            fuente=fuente.deriveFont(37.5f);
            new Thread (this).start();
            Timer timer = new Timer();
            timer.schedule(new Temporizador(), 
                           tiempoMensajeInicio*1000);
        } catch (Exception ex) {
             System.out.println("Error cargar imagen");
        }
        
    }
    @Override
    public void paint(Graphics g) {
            Graphics2D gON  = ((Graphics2D)g);        
            Image mImagen   =createImage(getWidth(),getHeight());
                try{
                    Graphics2D offG =(Graphics2D) mImagen.getGraphics();
                    pintarNubes(offG);
                    pintarMontañas(offG);
                    pintarSuelo(offG);
                    pintarImagenJuego(offG);
                    pintarPersonajeH(offG);
                    pintarRH(offG);
    //                pintarPlanta(offG);
                    anagrama.dibujar(offG);
                    gON.drawImage(mImagen, 0,0,this);
                }catch(Exception e){
                
                }
    }
   
    private void pintarSuelo(Graphics2D g2d){
        Paint respaldo=g2d.getPaint();  
        g2d.setPaint(patronSuelo);
        Rectangle2D.Double limiteSuelo=new Rectangle2D.Double(0, getHeight()-ALTO_SUEL0,getWidth(),ALTO_SUEL0);
        g2d.fill(limiteSuelo);
        g2d.setPaint(patronPasto);
        Rectangle2D.Double limitePasto=new Rectangle2D.Double(0,getHeight()-ALTO_SUEL0,getWidth(),22);
        g2d.fill(limitePasto);
        g2d.setPaint(patronSuelo);
        
        g2d.setPaint(respaldo);
    }
    private void pintarNubes(Graphics2D g2d){
        int inicio=inicioNubes;
        for(int i=0;i<5;i++){
             g2d.drawImage(bufferNube,inicio+50,0,this);
             g2d.drawImage(bufferNube,inicio,0,this);
             g2d.drawImage(bufferNube,inicio+170,50,this);
             g2d.drawImage(bufferNube,inicio+120,50,this);
             inicio+=400;
        }
    }
    
    private void pintarRH(Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        g2d.fillRect((int)cX-2,(int)cY, 5, 55);
        AffineTransform au=g2d.getTransform();
        g2d.rotate(anguloRotacion,cX,cY);
        g2d.drawImage(bufferRH,1100,300,this);
        g2d.setTransform(au);   
    }
//    private void pintarPlanta(Graphics2D g2d){
//        AffineTransform respaldo=g2d.getTransform();
//        if (bandera){
//            AffineTransform plantaT = new AffineTransform();
//            plantaT.translate(-25, -40);
//            plantaT.scale(1.1, 1.1);
//            g2d.setTransform(plantaT);
//        }
//        g2d.drawImage(bufferPlanta,260,362,this);
//        g2d.setTransform(respaldo);
//    }

    private void pintarMontañas(Graphics2D g2d){
        AffineTransform au=g2d.getTransform();
        g2d.translate(100,50);
        GeneralPath montañaS=new GeneralPath();
        montañaS.moveTo(0,400);
        montañaS.lineTo(100,200);
        montañaS.curveTo(100,200,100,193,110,190); //x1-inicio x2 controlCurva  x3-PFinal
        montañaS.lineTo(150, 190);
        montañaS.curveTo(150,190,160,193,160,200);
        montañaS.lineTo(260, 400);
        montañaS.closePath();
        g2d.setColor(new Color(120,200,135));
        g2d.fill(montañaS);
        GeneralPath montaña2S=new GeneralPath();
        montaña2S.moveTo(10,400);
        montaña2S.lineTo(110,200);
        montaña2S.lineTo(140,200);
        montaña2S.lineTo(220,400);
        montaña2S.closePath();
        g2d.setColor(new Color(128,208,144));
        g2d.fill(montaña2S);
        g2d.setTransform(au);
        
        GeneralPath montaña=new GeneralPath();
        montaña.moveTo(0,400);
        montaña.lineTo(100,200);
        montaña.curveTo(100,200,100,193,110,190); //x1-inicio x2 controlCurva  x3-PFinal
        montaña.lineTo(150, 190);
        montaña.curveTo(150,190,160,193,160,200);
        montaña.lineTo(260, 400);
        montaña.closePath();
        g2d.setColor(new Color(80,168,64));
        g2d.fill(montaña);
        GeneralPath montaña2=new GeneralPath();
        montaña2.moveTo(10,400);
        montaña2.lineTo(110,200);
        montaña2.lineTo(140,200);
        montaña2.lineTo(220,400);
        montaña2.closePath();
        g2d.setColor(new Color(96,184,80));
        g2d.fill(montaña2);
        g2d.setTransform(au);
        
        for(int i=1;i<4;i++){
            g2d.translate(i*420, 50);
            g2d.setColor(new Color(120,200,135));
            g2d.fill(montañaS);
            g2d.setColor(new Color(128,208,144));
            g2d.fill(montaña2S);
            g2d.setTransform(au);
            //----------
            g2d.translate(i*320, 0);
            g2d.setColor(new Color(80,168,64));
            g2d.fill(montaña);
            g2d.setColor(new Color(96,184,80));
            g2d.fill(montaña2);
            g2d.setTransform(au);
        }
        g2d.setTransform(au);
    }
    private void pintarPersonajeH(Graphics2D g2d){
        g2d.setPaint(Color.BLACK);
        g2d.fillRect(90,250, 100, 80);//cuerpo
        g2d.fillRect(70, 270,20,5);//orejaI
        g2d.fillOval(73, 265,10,15);//orejaI
        g2d.fillRect(190,270,20,5);//orejaD
        g2d.fillOval(196, 265,10,15);//orejaD
        g2d.fillRect(123,230,5,20);//antena_1
        g2d.fillOval(118,220,15,15);//antena_11
        g2d.fillRect(153,230,5,20);//antena_2
        g2d.fillOval(148,220,15,15);//antena_11
        g2d.setPaint(Color.WHITE);
        
        if(!banderaOjos){
            g2d.fillOval(115, 270, 20, 32);//ojoI
            g2d.fillOval(145, 270, 20, 32);//ojoD
        }else{
            g2d.fillOval(105, 275, 32, 20);//ojoI
            g2d.fillOval(148, 275, 32, 20);//ojoD
        }
        
        
        
        g2d.fillRoundRect(30, 320,225, 90, 25, 25);//recuadro
        g2d.setPaint(Color.BLACK);
        g2d.fillOval(55,310, 15, 15);//mano1_1
        g2d.fillOval(68,310, 15, 15);//mano1_2
        g2d.fillOval(195,310, 15, 15);//mano2_1
        g2d.fillOval(208,310, 15, 15);//mano2_2
        g2d.setFont(fuente);
        g2d.drawString("MazeAgram",31,390);//tituloJuego
        
        if(banderaMensajes){//cambiar vanedea
            g2d.drawImage(bufferDialogo,200,50, this);
            switch(tipoMensaje){
                case 1://mensaje Bienvenida 
                    g2d.drawImage(bufferM2, 250, 78,this);//mensaje Final
                    break;
                case 2:
                    g2d.drawImage(bufferM3,240,85,this);
                    break;
                case 3:
                    g2d.drawImage(bufferM1, 255, 75,this);//mensaje
                    break;
            }
        }
    }
    private void pintarImagenJuego(Graphics2D g2d){
       g2d.drawImage(bufferMarco, 430,120, this);
       int posicionX=(getWidth()/2)-
                     (bufferImagenPalabra.getWidth()/2);
       int posicionY=(530-bufferImagenPalabra.getHeight());
       g2d.drawImage(bufferImagenPalabra, posicionX, posicionY, this);       
    }
    
    public void repintar(){
            paint(getGraphics());
    }
    public void establecerListeners(){
        addMouseListener(listenerCanvas);
        addMouseMotionListener(listenerCanvas);
    }
    public void quitarListeners(){
        removeMouseListener(listenerCanvas);
        removeMouseMotionListener(listenerCanvas);
    }
    public void cambiarPunteroLibre(){
        setCursor(cursorAnagrama);
        banderaOjos=false;
    }
    
    public void cambiarPunteroArrastrar(){
        setCursor(cursorAnagrama2);
        banderaOjos=true;
    }
    public void ultimaAnimacion(){
        try {
            quitarListeners();
            Thread.sleep(1000l);
            banderaMensajes=true;
            Thread.sleep(5000l);//tiempo m3
            banderaMensajes=false;
            tipoMensaje=0;
            banderaHilo=false;
            frame.establecerSalida();
        } catch (InterruptedException ex) {
            System.out.println("Error");
        }
    }
    
    
    @Override
    public void run() {
        int n=10;
            //m=1;
        while(banderaHilo){
            try {
                anguloRotacion=Math.toRadians(n);
                inicioNubes+=2;                             //velocidadNubes
                Thread.sleep(100l);
                n+=10;
                if(n>=370)
                   n=0;
//                if(m==5)
//                    bandera=true;
//                if(m==10){
//                    m=0;
//                    bandera=false;
//                }
                if(inicioNubes==0)
                        inicioNubes=-400;
                 repintar();
//                m++;
            } catch (InterruptedException ex) {
                System.out.println("GATOMAN estuvo aqui");
            }
            
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
       
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if(arg0.getKeyCode()==KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
     
    }
    
    class Temporizador extends TimerTask{

        @Override
        public void run() {
            banderaMensajes=true;
            try {
                Thread.sleep(5000l);//tiempo m1
                banderaMensajes=false;
                Thread.sleep(1000l);
                banderaMensajes=true;
                tipoMensaje++;
                Thread.sleep(7000l);//tiempo m2
                banderaMensajes=false;
                tipoMensaje=3;
                establecerListeners();
            } catch (InterruptedException ex) {
                System.out.println("Error");
            }
        }
    
    }
}
