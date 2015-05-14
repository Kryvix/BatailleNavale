import java.util.Scanner;

/**
 * La classe principale du jeu.
 * @author Julien EMMANUEL, Charlotte RICHAD, Thomas DUTOUR, Alexis SAGET
 */
public class BatailleNavale {
	
	public static void main(String[] args) 
	{
		// Grille du joueur :
		int[][] grilleJoueur = new int[10][10];
		// Grille du joueur visible par l'IA :
		int[][] grilleJoueurV = new int[10][10];
		// Grille de l'IA :
		int[][] grilleIA = new int[10][10];
		// Grille de l'IA visible par le joueur :
		int[][] grilleIAV = new int[10][10];
		genererBateaux(grilleJoueur);
		afficherGrille(grilleJoueur);
	}
	
	public static void genererBateaux (int[][] grilleJoueur)
	{
		Bateau[] bateaux = new Bateau[5];
		bateaux[0] = new Bateau("Porte-avions",5);
		bateaux[1] = new Bateau("Croiseur",4);
		bateaux[2] = new Bateau("Contre-torpilleur",3);
		bateaux[3] = new Bateau("Sous-marin",3);
		bateaux[4] = new Bateau("Torpilleur",2);
		positionner(bateaux, grilleJoueur);
		for (int i = 0; i<5; i++)
		{
			System.out.println(bateaux[i]);
		}
	}
	public static void positionner(Bateau[] bateaux, int[][] grilleJoueur)
	{
		bateaux[0].position(1,3,true);
		Scanner sc = new Scanner(System.in);
		for(int i = 0; i<5; i++)
		{
			boolean dansLaGrille = false;
			boolean casePrise = false;
			int positionX = 0;
			int positionY = 0;
			while(!dansLaGrille || casePrise)
			{
				casePrise = false;
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
				System.out.println("Le "+bateaux[i].getName()+" doit il être vertical ou horizontal (horizontal = 0, vertical = 1) :");
				String verticalString = sc.nextLine();
				while(verticalString.charAt(0) != '0' && verticalString.charAt(0) != '1')
				{
					System.out.println("Veuillez entrer 0 ou 1 :");
					verticalString = sc.nextLine();
				}
				boolean vertical = true;
				if(verticalString.charAt(0) == '0')
					vertical = false;
				positionX = Integer.parseInt(colonneString) - 1;
				positionY = Integer.parseInt(ligneString) - 1;
				bateaux[i].position(positionX, positionY,vertical);
				dansLaGrille = bateaux[i].verifierPosition();
				if(!dansLaGrille)
					System.out.println("Le bateau sort de la grille !");
				else
				{
					for(int k = 0; k<bateaux[i].getTaille(); k++)
					{
						int x = 0;
						int y = 0;
						if(bateaux[i].isVertical())
						{
							x = positionX;
							y = positionY + k;
						}
						else
						{
							x = positionX + k;
							y = positionY;
						}
						if(grilleJoueur[x][y] != 0)
							casePrise = true;
					}
					if(casePrise)
						System.out.println("Votre bateau occupe une case commune avec un autre bateau !");
				}
			}
			for(int j = 0; j<bateaux[i].getTaille(); j++)
			{
				int x = 0;
				int y = 0;
				if(bateaux[i].isVertical())
				{
					x = positionX;
					y = positionY + j;
					
				}
				else
				{
					x = positionX + j;
					y = positionY;
				}
				grilleJoueur[x][y] = i+1;
			}
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
	public static void afficherGrille(int[][] grille)
	{
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				System.out.print("|");
				System.out.print(grille[j][i]);
			}
			System.out.print("|");
			System.out.println();
		}
	}
}