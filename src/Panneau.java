import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
 
public class Panneau extends JPanel { 
	
	private int[][] grille = new int[10][10];
	private boolean gagne = false;
	private boolean perdu = false;
	
	public void paintComponent(Graphics g){
		try 
		{
			Image[] img = new Image[3];
			for(int y = 0; y<3; y++)
				img[y] = ImageIO.read(new File("Images/case"+y+".png"));
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
					g.drawImage(img[grille[i][j]], 32*i+32, 32*j+32, this);
				}
			}
			
			if(gagne)
			{
				g.drawImage(IMGgagne, 0, 0, this);
			}
			
			if(perdu)
			{
				g.drawImage(IMGperdu, 0, 0, this);
			}
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}   
	}    
	
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
	public void setGagne()
	{
		this.gagne = true;
	}
	public void setPerdu()
	{
		this.perdu = true;
	}
	public void reinitAff()
	{
		this.gagne = false;
		this.perdu = false;
	}
}
