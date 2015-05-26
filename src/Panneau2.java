import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
 
public class Panneau2 extends JPanel { 
	
	private int[][] grille = new int[10][10];
	private boolean gagne = false;
	private boolean perdu = false;
	
	public void paintComponent(Graphics g){
		try 
		{
			Image[] img = new Image[3];
			img[0] = ImageIO.read(new File("case0.png"));
			img[1] = ImageIO.read(new File("bateau.png"));
			img[2] = ImageIO.read(new File("case2.png"));
			Image IMGgagne = ImageIO.read(new File("gagne.png"));
			Image IMGperdu = ImageIO.read(new File("perdu.png"));
	      
			for(int i = 0; i < grille.length; i++)
			{
				for(int j = 0; j < grille[0].length; j++)
				{
					if(grille[i][j] == 0)
						g.drawImage(img[0], 32*i, 32*j, this);
					else if(grille[i][j] < 6)
						g.drawImage(img[1], 32*i, 32*j, this);
					else
						g.drawImage(img[2], 32*i, 32*j, this);
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
}