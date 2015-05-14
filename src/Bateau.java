/**
 * La classe principale du jeu.
 * @author Julien EMMANUEL, Charlotte RICHAD, Thomas DUTOUR, Alexis SAGET
 */
public class Bateau {
	private String nom;
	private int taille;
	private boolean[] etat;
	private int vie;
	private boolean vertical;
	private int positionX;
	private int positionY;
	public Bateau(String nom, int taille)
	{
		this.nom = nom;
		this.taille = taille;
		this.vie = this.taille;
		this.vertical = false;
		this.positionX = 0;
		this.positionY = 0;
		this.etat = new boolean[taille];
		for(int i = 0; i<etat.length; i++)
		{
			etat[i] = true;
		}
	}
	public String toString()
	{
		String chaine = nom + " - taille = " + taille + " - position = {" + positionX + ";" + positionY + "}" + vertical;
		return chaine;
	}
	public String getName()
	{
		return nom;
	}
	public void position(int positionX, int positionY, boolean vertical)
	{
		this.positionX = positionX;
		this.positionY = positionY;
		this.vertical = vertical;
	}
	public boolean verifierPosition()
	{
		boolean positionValide = true;
		for(int i = 0; i<taille; i++)
		{
			if(vertical)
			{
				int x = positionX;
				int y = positionY + i;
				if(x < 0 || x > 9 || y < 0 || y > 9)
					positionValide = false;
			}
			else
			{
				int x = positionX + i;
				int y = positionY;
				if(x < 0 || x > 9 || y < 0 || y > 9)
					positionValide = false;
			}
		}
		return positionValide;
	}
}