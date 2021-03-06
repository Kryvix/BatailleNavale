import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
 
/**
 * Panneau correspondant à la grille du joueur.
 * @author Julien EMMANUEL, Charlotte RICHAD, Thomas DUTOUR, Alexis SAGET
 */
public class Panneau2 extends JPanel { 
	
	private int[][] grille = new int[10][10];
	private boolean gagne = false;
	private boolean perdu = false;
	/**
	 * Rafraichit l'affichage de la grille.
	 */
	public void paintComponent(Graphics g){
		try 
		{
			Image[] img = new Image[4];
			img[0] = ImageIO.read(new File("Images/case0.png"));
			img[1] = ImageIO.read(new File("Images/bateau.png"));
			img[2] = ImageIO.read(new File("Images/case2.png"));
			img[3] = ImageIO.read(new File("Images/case1.png"));
			Image IMGgagne = ImageIO.read(new File("Images/gagne.png"));
			Image IMGperdu = ImageIO.read(new File("Images/perdu.png"));
			
			Image[] numerotation = new Image[11];
			for(int n = 0; n<11; n++)
				numerotation[n] = ImageIO.read(new File("Images/"+n+".png"));
			for(int z = 0; z <= grille.length; z++)
				g.drawImage(numerotation[z], 32*z, 0, this);
			for(int p = 1; p <= grille[0].length; p++)
				g.drawImage(numerotation[p], 0, 32*p, this);
			
			for(int i = 0; i < grille.length; i++)
			{
				for(int j = 0; j < grille[0].length; j++)
				{
					if(grille[i][j] == 0)
						g.drawImage(img[0], 32*i+32, 32*j+32, this);
					else if(grille[i][j] < 6)
						g.drawImage(img[1], 32*i+32, 32*j+32, this);
					else if(grille[i][j] == 42)
						g.drawImage(img[3], 32*i+32, 32*j+32, this);
					else
						g.drawImage(img[2], 32*i+32, 32*j+32, this);
				}
			}
			if(gagne)
			{
				g.drawImage(IMGgagne, 32, 32, this);
			}
			
			if(perdu)
			{
				g.drawImage(IMGperdu, 32, 32, this);
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}   
	}    
	/**
	 * Définit (ou actualise) la grille à afficher.
	 * @param grille La grille à utiliser.
	 */
	public void setGrid(int[][] grille)
	{
		for(int i = 0; i < grille.length; i++)
		{
			for(int j = 0; j < grille[0].length; j++)
			{
				this.grille[i][j] = grille[i][j];
			}
		}
	}
	/**
	 * Affiche l'image de fin si le joueur a gagné.
	 */
	public void setGagne()
	{
		this.gagne = true;
	}
	/**
	 * Affiche l'image de fin si le joueur a perdu.
	 */
	public void setPerdu()
	{
		this.perdu = true;
	}
	/**
	 * Efface les images de fin de partie.
	 */
	public void reinitAff()
	{
		this.gagne = false;
		this.perdu = false;
	}
}
