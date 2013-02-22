/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeagram;

/**
 *
 * @author Solosimpi
 */
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
import javax.imageio.ImageIO;
/**Clase que genera el laberinto, y regresa una imagen**/


public class MazeGenerator extends Canvas{
	public final int x;
	public final int y;
	private final int[][] maze;
	private static final Random rand = new Random();
        BasicStroke Stroke;
        TexturePaint texturamuralla;
        TexturePaint texturafondo;
        TexturePaint escaleras;
        Rectangle salida;
        Chest []chests;
        String palabra;
        int dx=160;
        int dy=160;
        int iniciox=10;
        int inicioy=10;        
        int ancho=40;
        
        RoundRectangle2D.Float lineh= new RoundRectangle2D.Float(iniciox,inicioy,dx+ancho,ancho,5,5);         
        RoundRectangle2D.Float linev= new RoundRectangle2D.Float(iniciox,inicioy,ancho,dy+ancho,5,5);
        BufferedImage labimg;
        BufferedImage loglab;
        BufferedImage chestclosed;
        BufferedImage chestopen;
        Boolean bandera=false;
        ArrayList puntos;
        
	public MazeGenerator(int x, int y) {
                Stroke=new BasicStroke(3);
		this.x = x;
		this.y = y;
                
                //Elegir Palabra
                palabra=elegirpalabra();
                puntos = new ArrayList();
                generaChest();
		maze = new int[this.x][this.y];
		generateMaze(0, 0);
                setSize(((dx+ancho)*x)+iniciox,(dy+ancho)*y);
                try{
                BufferedImage aux=(ImageIO.read(new File("Textures/brick"+getRandom(4)+".jpg")));
                Rectangle2D.Float auxrect=new Rectangle2D.Float(0,0,40,40);
                texturamuralla=new TexturePaint(aux,auxrect);
                aux=(ImageIO.read(new File("Textures/fondotextura"+getRandom(4)+".jpg")));
                texturafondo=new TexturePaint(aux,auxrect);
                aux=(ImageIO.read(new File("Textures/escaleras.jpg")));
                Rectangle2D.Float auxrect2=new Rectangle2D.Float(0,0,20,80);
                escaleras=new TexturePaint(aux,auxrect2);
                chestopen=(ImageIO.read(new File("Textures/chestopen.jpg")));
                chestclosed=(ImageIO.read(new File("Textures/chestclosed.jpg")));
                }catch(Exception e){
                }
                loglab=generarImagen();
                bandera=true;
                labimg=generarImagen();
                
	}
        
        public String elegirpalabra(){
            File  dir;
            String [] ficheros;
            Random rand = new Random();
            String palabra;
            if(x==5) dir = new File ("niveles/facil");
            else if(x==8) dir = new File ("niveles/medio");
            else dir = new File ("niveles/dificil");
            ficheros=dir.list();
            palabra=ficheros[rand.nextInt(ficheros.length)];
            palabra=palabra.substring(0,palabra.length()-4);
            System.out.println(palabra);
            return palabra;
        }
        
        public void generaChest(){
            chests=new Chest[palabra.length()];
            for (int i = 0; i < palabra.length(); i++) {
                int x1=getRandom(x);
                int y1=getRandom(x);
                Chest aux=new Chest((dx*(x1))+ancho+iniciox+30,(dy*y1)+ancho+inicioy+30,50,50,String.valueOf(palabra.charAt(i)));
                while(colisione(aux,i)||x1==0&&y1==0||x1==x&&y1==y){
                    x1=getRandom(x);
                    y1=getRandom(x);
                    System.out.println(x1+","+y1);
                    aux=new Chest((dx*(x1))+ancho+iniciox+30,(dy*y1)+ancho+inicioy+30,50,50,String.valueOf(palabra.charAt(i)));
                }
                chests[i]= aux;
            }
        }
        
        public boolean colisione(Chest aux,int x){
            for (int i = 0; i < x; i++) {
                if(aux.bounds.intersects(chests[i].bounds)){
                    return true;
                }
            } return false;
        }
        
        public int getRandom(int x){
            return rand.nextInt(x);
        }

	private void generateMaze(int cx, int cy) { //Metodo recursivo para generar el laberinto
		DIR[] dirs = DIR.values(); //Obtenemos el laberinto actual
		Collections.shuffle(Arrays.asList(dirs)); //mezclamos los elementos de una fila del laberinto
		for (DIR dir : dirs) { //Para cada elemento de la fila
			int nx = cx + dir.dx; 
			int ny = cy + dir.dy;
			if (between(nx, x) && between(ny, y)
					&& (maze[nx][ny] == 0)) {
				maze[cx][cy] |= dir.bit;
				maze[nx][ny] |= dir.opposite.bit;
				generateMaze(nx, ny);
			}
		}
	}
 
	private static boolean between(int v, int upper) {
		return (v >= 0) && (v < upper);
	}
 
	private enum DIR {
		N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
		private final int bit;
		private final int dx;
		private final int dy;
		private DIR opposite;
 
		// use the static initializer to resolve forward references
		static {
			N.opposite = S;
			S.opposite = N;
			E.opposite = W;
			W.opposite = E;
		}
 
		private DIR(int bit, int dx, int dy) {
			this.bit = bit;
			this.dx = dx;
			this.dy = dy;
		}
	};
     
    public void dibujaLaberinto(Graphics g){
        Graphics2D g2D= (Graphics2D)g;
        g2D.setStroke(Stroke); //ponemos tipo de linea
        for (int i = 0; i < y; i++) { //dibujamos el laberinto
            for (int j = 0; j < x; j++) { 
                if((maze[j][i]&1)==0){ //|___ graficamos una linea horizontal
                    g2D.fill(lineh); 
                    lineh.x+=dx; //movemos la linea
                }
                else{ //|
                    lineh.x+=dx; //no se grafica nada, aumentamos una linea
                }
            }
            lineh.x=iniciox; //reiniciiamos la linea en x
            lineh.y+=dy; //reiniciamos la linea en y
            //|
	    // dibujamos la linea linea izquierda
            for (int j = 0; j < x; j++) {
                if((maze[j][i]&8)==0) {; //| dibujamos una linea vertical
                    g2D.fill(linev);
                    linev.x+=dx;
                }
                else{ //| no dibujamos liena vertical pero aumentamos la linea vertical en x
                    linev.x+=dx;
                }
            }
            g2D.fill(linev); 
            linev.x=iniciox;
            linev.y+=dy;
            //| 
            }
            // linea de hasta abjo
            for (int j = 0; j < x; j++) {
                    g2D.fill(lineh);
                    lineh.x+=dx;
            }
            System.out.println("+");//|
            lineh.x=iniciox;
            lineh.y=inicioy;
            linev.x=iniciox;
            linev.y=inicioy;
    }
    
    public void paint(Graphics g){
        Graphics2D g2D= (Graphics2D)g;
        if(bandera){
            g2D.setPaint(texturafondo);
            g2D.fillRect(iniciox,inicioy,((dx*x)),((dy)*y));
        }
        g2D.setPaint(texturamuralla);
        dibujaLaberinto(g);
        if(bandera){
            g2D.setPaint(escaleras);
            g2D.fillRect(ancho+iniciox,0-(ancho+inicioy),dx-ancho,dy);
            salida=new Rectangle((dx*x)-dx+ancho+iniciox,(dy*y)-dy+ancho+iniciox+ancho,dx-ancho,dy-ancho);
            g2D.fill(salida);
            g2D.setColor(Color.BLACK);
            g2D.drawRect(ancho+iniciox,0-(ancho+inicioy),dx-ancho,dy);
            g2D.draw(salida);
            for (int i = 0; i < palabra.length(); i++) {
                g2D.drawImage(chestclosed,chests[i].bounds.x,chests[i].bounds.y, this);
            }
        }
    }
    
    
    private BufferedImage generarImagen() {
        int w = getWidth();
        int h = getHeight();
        int type = BufferedImage.TYPE_INT_ARGB_PRE;;
        BufferedImage image = new BufferedImage(w,h,type);
        Graphics2D g2 = image.createGraphics();
        paint(g2);
        g2.dispose();
        return image;
    }
}
