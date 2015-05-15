/**
 * Cette classe d�finit l'objet "bateau".
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
	/**
	 * Le constructeur de l'objet bateau.
	 * @param nom Le nom du bateau.
	 * @param taille La taille du bateau.
	 */
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
	/**
	 * Renvoie les caract�ristiques principales du bateau sous forme d'une cha�ne de caract�res.
	 */
	public String toString()
	{
		String chaine = nom + " - taille = " + taille + " - position = {" + positionX + ";" + positionY + "}" + vertical;
		return chaine;
	}
	/**
	 * Renvoie le nom d'un bateau.
	 * @return Nom du bateau.
	 */
	public String getName()
	{
		return nom;
	}
	/**
	 * Renvoie la taille d'un bateau.
	 * @return Taille du bateau.
	 */
	public int getTaille()
	{
		return taille;
	}
	/**
	 * Indique si le bateau est horizontal ou vertical.
	 * @return TRUE si le bateau est vertical, sinon FALSE;
	 */
	public boolean isVertical()
	{
		return vertical;
	}
	/**
	 * D�finit pr�cis�ment la position du bateau
	 * @param positionX La colonne d'origine du bateau.
	 * @param positionY La ligne d'origine du bateau.
	 * @param vertical L'orientation du bateau.
	 */
	public void position(int positionX, int positionY, boolean vertical)
	{
		this.positionX = positionX;
		this.positionY = positionY;
		this.vertical = vertical;
	}
	/**
	 * V�rifie si le bateau n'est pas en dehors de la grille (dont la taille est 10x10)
	 * @return TRUE si le bateau est bien enti�rement contenu dans la grille, sinon FALSE.
	 */
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