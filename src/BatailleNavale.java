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
		// Cr�ation des bateaux du joueur :
		Bateau[] bateauxJ = new Bateau[5];
		bateauxJ[0] = new Bateau("Porte-avions",5);
		bateauxJ[1] = new Bateau("Croiseur",4);
		bateauxJ[2] = new Bateau("Contre-torpilleur",3);
		bateauxJ[3] = new Bateau("Sous-marin",3);
		bateauxJ[4] = new Bateau("Torpilleur",2);
		// Cr�ation des bateaux de l'IA :
		Bateau[] bateauxIA = new Bateau[5];
		bateauxIA[0] = new Bateau("Porte-avions",5);
		bateauxIA[1] = new Bateau("Croiseur",4);
		bateauxIA[2] = new Bateau("Contre-torpilleur",3);
		bateauxIA[3] = new Bateau("Sous-marin",3);
		bateauxIA[4] = new Bateau("Torpilleur",2);
		// Positionnement des bateaux :
		positionner(bateauxJ, grilleJoueur);
		positionnerIA(bateauxIA, grilleIA);
		afficherGrilleDev(grilleIA);
		Joueur joueur = new Joueur();
		Joueur IA = new Joueur();
		Scanner sc = new Scanner(System.in);
		afficherGrilleDev(grilleJoueur);
		while(joueur.getLife() != 0 && IA.getLife() != 0)
		{
			System.out.print("Grille adverse :");
			afficherGrille(grilleIAV);
			System.out.println("Ligne de la case � viser :");
			String ligneString = sc.nextLine();
			while(!verifierNombre(ligneString))
			{
				System.out.println("Veuillez entrer un nombre entre 1 et 10 :");
				ligneString = sc.nextLine();
			}
			System.out.println("Colonne de la case � viser :");
			String colonneString = sc.nextLine();
			while(!verifierNombre(colonneString))
			{
				System.out.println("Veuillez entrer un nombre entre 1 et 10 :");
				colonneString = sc.nextLine();
			}
			int positionX = Integer.parseInt(colonneString) - 1;
			int positionY = Integer.parseInt(ligneString) - 1;
			if(grilleIA[positionX][positionY] == 0)
			{
				System.out.println("Vous n'avez rien touch� !");
				grilleIAV[positionX][positionY] = 1;
			}
			else
			{
				boolean coule = bateauxIA[grilleIA[positionX][positionY]-1].destroyCell(positionX,positionY);
				if(coule)
				{
					System.out.println("Coul� !");
					IA.enleverVie();
				}
				else
					System.out.println("Touch� !");
				grilleIAV[positionX][positionY] = 2;
			}
		}
	}
	/**
	 * Demande � l'utilisateur la position de ses bateaux et les ajoute � sa grille.
	 * @param bateauxJ Le tableau des bateaux � positionner.
	 * @param grilleJoueur La grille dans laquelle les bateaux seront positionn�s.
	 */
	public static void positionner(Bateau[] bateauxJ, int[][] grilleJoueur)
	{
		bateauxJ[0].position(1,3,true);
		Scanner sc = new Scanner(System.in);
		afficherGrilleDev(grilleJoueur);
		for(int i = 0; i<5; i++)
		{
			boolean dansLaGrille = false;
			boolean casePrise = false;
			int positionX = 0;
			int positionY = 0;
			while(!dansLaGrille || casePrise)
			{
				casePrise = false;
				System.out.println("Ligne du "+bateauxJ[i].getName()+" :");
				String ligneString = sc.nextLine();
				while(!verifierNombre(ligneString))
				{
					System.out.println("Veuillez entrer un nombre entre 1 et 10 :");
					ligneString = sc.nextLine();
				}
				System.out.println("Colonne du "+bateauxJ[i].getName()+" :");
				String colonneString = sc.nextLine();
				while(!verifierNombre(colonneString))
				{
					System.out.println("Veuillez entrer un nombre entre 1 et 10 :");
					colonneString = sc.nextLine();
				}
				System.out.println("Le "+bateauxJ[i].getName()+" doit il �tre vertical ou horizontal (horizontal = 0, vertical = 1) :");
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
				bateauxJ[i].position(positionX, positionY,vertical);
				dansLaGrille = bateauxJ[i].verifierPosition();
				if(!dansLaGrille)
					System.out.println("Le bateau sort de la grille !");
				else
				{
					for(int k = 0; k<bateauxJ[i].getTaille(); k++)
					{
						int x = 0;
						int y = 0;
						if(bateauxJ[i].isVertical())
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
			for(int j = 0; j<bateauxJ[i].getTaille(); j++)
			{
				int x = 0;
				int y = 0;
				if(bateauxJ[i].isVertical())
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
			afficherGrilleDev(grilleJoueur);
		}
		// sc.close();
	}
	/**
	 * Positionne al�atoirement les bateaux de l'IA.
	 * @param bateauxIA Le tableau des bateaux � positionner.
	 * @param grilleIA La grille dans laquelle les bateaux seront positionn�s.
	 */
	public static void positionnerIA(Bateau[] bateauxIA, int[][] grilleIA)
	{
		bateauxIA[0].position(1,3,true);
		for(int i = 0; i<5; i++)
		{
			boolean dansLaGrille = false;
			boolean casePrise = false;
			boolean vertical;
			int positionX = 0;
			int positionY = 0;
			while(!dansLaGrille || casePrise)
			{
				casePrise = false;
				positionX = (int)(Math.random()*10);
				positionY = (int)(Math.random()*10);
				if((int)(Math.random()*2) == 0)
					vertical = false;
				else
					vertical = true;
				bateauxIA[i].position(positionX, positionY,vertical);
				dansLaGrille = bateauxIA[i].verifierPosition();
				if(dansLaGrille)
				{
					for(int k = 0; k<bateauxIA[i].getTaille(); k++)
					{
						int x = 0;
						int y = 0;
						if(bateauxIA[i].isVertical())
						{
							x = positionX;
							y = positionY + k;
						}
						else
						{
							x = positionX + k;
							y = positionY;
						}
						if(grilleIA[x][y] != 0)
							casePrise = true;
					}
				}
			}
			for(int j = 0; j<bateauxIA[i].getTaille(); j++)
			{
				int x = 0;
				int y = 0;
				if(bateauxIA[i].isVertical())
				{
					x = positionX;
					y = positionY + j;
					
				}
				else
				{
					x = positionX + j;
					y = positionY;
				}
				grilleIA[x][y] = i+1;
			}
		}
	}
	/**
	 * V�rifie qu'une cha�ne de caract�re correspond bien � un nombre compris entre 1 et 10 inclus.
	 * @param chaine La cha�ne � analyser
	 * @return TRUE si le nombre est valide, sinon FALSE.
	 */
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
	/**
	 * Affiche une grille entr�e en param�tre (utile seulement pendant la phase de d�veloppement pour effectuer des tests).
	 * @param grille La grille � afficher
	 */
	public static void afficherGrilleDev(int[][] grille)
	{
		System.out.println();
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
	/**
	 * Affiche une grille entr�e en param�tre.
	 * @param grille La grille � afficher
	 */
	public static void afficherGrille(int[][] grille)
	{
		System.out.println();
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				System.out.print("|");
				if(grille[j][i] == 0)
					System.out.print(" ");
				else if(grille[j][i] == 1)
					System.out.print("~");
				else
					System.out.print("*");
			}
			System.out.print("|");
			System.out.println();
		}
	}
}