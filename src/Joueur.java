/**
 * Cette classe définit l'objet "joueur".
 * @author Julien EMMANUEL, Charlotte RICHAD, Thomas DUTOUR, Alexis SAGET
 */
public class Joueur {
	private int bateauxRestants;
	/**
	 * Ce constructeur instancie un joueur.
	 */
	public Joueur() {
		this.bateauxRestants = 1;
	}
	public int getLife()
	{
		return bateauxRestants;
	}
	public void enleverVie()
	{
		this.bateauxRestants--;
	}
}
