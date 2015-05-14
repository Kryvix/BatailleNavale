import java.util.Scanner;

/**
 * Cette classe définit l'objet "bateau".
 * @author Julien EMMANUEL, Charlotte RICHAD, Thomas DUTOUR, Alexis SAGET
 */
public class BatailleNavale {
	
	public static void main(String[] args) 
	{
		genererBateaux();
	}
	
	public static void genererBateaux ()
	{
		Bateau[] bateaux = new Bateau[5];
		bateaux[0] = new Bateau("Porte-avions",5);
		bateaux[1] = new Bateau("Croiseur",4);
		bateaux[2] = new Bateau("Contre-torpilleur",3);
		bateaux[3] = new Bateau("Sous-marin",3);
		bateaux[4] = new Bateau("Torpilleur",2);
		positionner(bateaux);
		for (int i = 0; i<5; i++)
		{
			System.out.println(bateaux[i]);
		}
	}
	public static void positionner(Bateau[] bateaux)
	{
		bateaux[0].position(1,3,true);
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir un mot :");
		String str = sc.nextLine();
	}
}