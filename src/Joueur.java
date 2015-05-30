/**
 * Cette classe d�finit l'objet "joueur".
 * @author Julien EMMANUEL, Charlotte RICHAD, Thomas DUTOUR, Alexis SAGET
 */
public class Joueur {
	private int bateauxRestants;
	/**
	 * Ce constructeur instancie un joueur. Cet objet est inutile car il ne contient qu'une seule variable, mais la flemme de r��crire tout le code donc �a restera comme �a.
	 */
	public Joueur() {
		this.bateauxRestants = 5;
	}
	/**
	 * D�finit le nombre de bateaux restants du joueur.
	 * @param life Le nombre de bateaux restants.
	 */
	public void setLife(int life)
	{
		this.bateauxRestants = life;
	}
	/**
	 * Renvoie le nombre de bateaux restants du joueur.
	 * @return Nombre de bateaux restants.
	 */
	public int getLife()
	{
		return bateauxRestants;
	}
	/**
	 * Enl�ve une vie au joueur.
	 */
	public void enleverVie()
	{
		this.bateauxRestants--;
	}
}
