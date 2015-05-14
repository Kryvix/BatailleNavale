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
		for(int i = 0; i<5; i++)
		{
			System.out.println("Ligne du "+bateaux[i].getName()+" :");
			String ligneString = sc.nextLine();
			while(!verifierNombre(ligneString))
			{
				System.out.println("Veuillez entrer un nombre entre 1 et 10 :");
				ligneString = sc.nextLine();
			}
			System.out.println("Colonne du "+bateaux[i].getName()+" :");
			String colonneString = sc.nextLine();
			while(!verifierNombre(colonneString))
			{
				System.out.println("Veuillez entrer un nombre entre 1 et 10 :");
				colonneString = sc.nextLine();
			}
			bateaux[i].position(Integer.parseInt(ligneString), Integer.parseInt(colonneString),true);
		}
	}
	public static boolean verifierNombre(String chaine)
	{
		boolean nombreValide = true;
		int i = 0;
		while(nombreValide && i <chaine.length())
		{
			if(Character.isDigit(chaine.charAt(i)))
			{
				nombreValide = true;
			}
			else
			{
				nombreValide = false;
			}
			i++;
		}
		if(nombreValide)
		{
			if(Integer.parseInt(chaine) > 10 || Integer.parseInt(chaine) < 1)
			{
				nombreValide = false;
			}
		}
		return nombreValide;
	}
	
}