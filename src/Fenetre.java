import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.Dimension;

import javax.swing.JFrame;
/**
 * Cette classe instancie une fenêtre de jeu.
 * @author Julien EMMANUEL, Charlotte RICHAD, Thomas DUTOUR, Alexis SAGET
 */
public class Fenetre extends JFrame {
	
	private Panneau pan = new Panneau();
	private Panneau2 pan2 = new Panneau2();
	private boolean Zaya;
	private int posX = 0;
	private int posY = 0;
	/**
	 * Constructeur d'une fenêtre
	 * @param nom Le nom qui s'affiche en haut de la fenêtre.
	 * @param Zaya TRUE si la fenêtre affiche la grille de Zaya, FALSE sinon (pour le joueur donc)
	 */
	public Fenetre(String nom, boolean Zaya)
	{
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int width  = (int)dimension.getWidth();
		this.Zaya = Zaya;
		this.setTitle(nom);
		this.setSize(352,352);
		this.setUndecorated(true);
		if(Zaya)
			this.setLocation(width-714, 0);
		else
			this.setLocation(width-352, 0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         
		this.setAlwaysOnTop(true);
		this.setResizable(false); 
		if(Zaya)
			this.setContentPane(pan);
		else
			this.setContentPane(pan2);
		this.setVisible(true);
		addMouseListener( new MouseAdapter(){
			 
			public void mousePressed(MouseEvent e) {
					posX = e.getX();    //Position X de la souris au clic
					posY = e.getY();
					
 
			   }
		});
		addMouseMotionListener( new MouseMotionAdapter(){
			 
			 public void mouseDragged(MouseEvent e) {
	                int depX = e.getX() - posX;
	                int depY = e.getY() - posY;
	                setLocation(getX()+depX, getY()+depY);
	            }
		});
	}
	/**
	 * Rafraichit l'affichage
	 * @param grille La grille à afficher dans la fenêtre.
	 */
	public void setGrid(int[][] grille)
	{
		if(Zaya)
		{
			pan.setGrid(grille);
			pan.repaint(); 
		}
		else
		{
			pan2.setGrid(grille);
			pan2.repaint(); 
		}
	}
	/**
	 * Affiche l'image de fin si le joueur a gagné.
	 */
	public void setGagne()
	{
		pan.setGagne();
		pan2.setGagne();
		pan.repaint(); 
		pan2.repaint(); 
	}
	/**
	 * Affiche l'image de fin si le joueur a perdu.
	 */
	public void setPerdu()
	{
		pan.setPerdu();
		pan2.setPerdu();
		pan.repaint(); 
		pan2.repaint(); 
	}
	/**
	 * Efface les images de fin de partie.
	 */
	public void reinitAff()
	{
		pan.reinitAff();
		pan2.reinitAff();
	}
}
