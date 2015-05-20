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
		int[][] grilleJoueurV = new int[12][12];
		for(int i=0; i<12; i++)
			grilleJoueurV[0][i] = 1;
		for(int i=0; i<12; i++)
			grilleJoueurV[11][i] = 1;
		for(int i=0; i<12; i++)
			grilleJoueurV[i][0] = 1;
		for(int i=0; i<12; i++)
			grilleJoueurV[i][11] = 1;
		// Grille de l'IA :
		int[][] grilleIA = new int[10][10];
		// Grille de l'IA visible par le joueur :
		int[][] grilleIAV = new int[10][10];
		// Création des bateaux du joueur :
		Bateau[] bateauxJ = new Bateau[5];
		bateauxJ[0] = new Bateau("Porte-avions",5);
		bateauxJ[1] = new Bateau("Croiseur",4);
		bateauxJ[2] = new Bateau("Contre-torpilleur",3);
		bateauxJ[3] = new Bateau("Sous-marin",3);
		bateauxJ[4] = new Bateau("Torpilleur",2);
		// Création des bateaux de l'IA :
		Bateau[] bateauxIA = new Bateau[5];
		bateauxIA[0] = new Bateau("Porte-avions",5);
		bateauxIA[1] = new Bateau("Croiseur",4);
		bateauxIA[2] = new Bateau("Contre-torpilleur",3);
		bateauxIA[3] = new Bateau("Sous-marin",3);
		bateauxIA[4] = new Bateau("Torpilleur",2);
		// Positionnement des bateaux :
		positionner(bateauxJ, grilleJoueur);
		positionnerIA(bateauxIA, grilleIA);
		//afficherGrilleDev(grilleIA);
		Joueur joueur = new Joueur();
		Joueur IA = new Joueur();
		Scanner sc = new Scanner(System.in);
		afficherGrilleDev(grilleJoueur);
		tourJoueur(grilleIAV, sc, IA, grilleIA, bateauxIA);
		while(joueur.getLife() != 0 && IA.getLife() != 0)
		{
			int x = 0;
			int y = 0;
			do
			{
				boolean aTrouve = false;
				int count = 1;
				while((!aTrouve && count != 300) && (joueur.getLife() != 0 && IA.getLife() != 0))
				{
					int g = (int)(Math.random()*12);
					int h = (int)(Math.random()*12);
					if(grilleJoueurV[g][h] == 2 && joueur.getLife() != 0 && IA.getLife() != 0)
					{
						boolean[] etatTemp = recherche(g, h, (int)((Math.random()*4)+1), grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
						if(!etatTemp[0] && !etatTemp[1])
							grilleJoueurV[g][h] = 3;
					}
					count++;
				}
				while(grilleJoueurV[x][y] != 0 && joueur.getLife() != 0 && IA.getLife() != 0)
				{
					x = (int)(Math.random()*12);
					y = (int)(Math.random()*12);
				}
				boolean[] etat = bombe(x, y, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
				if (etat[0] && !etat[1] && joueur.getLife() != 0 && IA.getLife() != 0)
				{
					recherche(x, y, (int)((Math.random()*4)+1), grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
				}
			}
			while((grilleJoueur[x-1][y-1] == 0 || grilleJoueur[x-1][y-1] == 1 ) && joueur.getLife() != 0 && IA.getLife() != 0);
		}
		if(joueur.getLife() == 0)
			System.out.println("Vous avez perdu.");
		else
			System.out.println("Vous avez gagné ! Bravo !");
	}
	/**
	 * Demande à l'utilisateur la position de ses bateaux et les ajoute à sa grille.
	 * @param bateauxJ Le tableau des bateaux à positionner.
	 * @param grilleJoueur La grille dans laquelle les bateaux seront positionnés.
	 */
	public static void positionner(Bateau[] bateauxJ, int[][] grilleJoueur)
	{
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
				System.out.println("Le "+bateauxJ[i].getName()+" doit il être vertical ou horizontal (horizontal = 0, vertical = 1) :");
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
	}
	/**
	 * Positionne aléatoirement les bateaux de l'IA.
	 * @param bateauxIA Le tableau des bateaux à positionner.
	 * @param grilleIA La grille dans laquelle les bateaux seront positionnés.
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
	 * Vérifie qu'une chaîne de caractère correspond bien à un nombre compris entre 1 et 10 inclus.
	 * @param chaine La chaîne à analyser
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
	 * Affiche une grille entrée en paramètre (utile seulement pendant la phase de développement pour effectuer des tests).
	 * @param grille La grille à afficher
	 */
	public static void afficherGrilleDev(int[][] grille)
	{
		System.out.println();
		for(int i = 0; i < grille.length; i++)
		{
			for(int j = 0; j < grille[0].length; j++)
			{
				System.out.print("|");
				System.out.print(grille[j][i]);
			}
			System.out.print("|");
			System.out.println();
		}
	}
	/**
	 * Affiche une grille entrée en paramètre.
	 * @param grille La grille à afficher
	 */
	public static void afficherGrille(int[][] grille)
	{
		System.out.println();
		for(int i = 0; i < grille.length; i++)
		{
			for(int j = 0; j < grille[0].length; j++)
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
	/**
	 * Effectue un tour du joueur
	 */
	 public static void tourJoueur(int[][] grilleIAV, Scanner sc, Joueur IA, int[][] grilleIA, Bateau[] bateauxIA)
	 {
		System.out.print("Grille adverse :");
		afficherGrille(grilleIAV);
		System.out.println("Ligne de la case a viser :");
		String ligneString = sc.nextLine();
		while(!verifierNombre(ligneString))
		{
			System.out.println("Veuillez entrer un nombre entre 1 et 10 :");
			ligneString = sc.nextLine();
		}
		System.out.println("Colonne de la case a viser :");
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
			System.out.println("Vous n'avez rien touche !");
			grilleIAV[positionX][positionY] = 1;
		}
		else
		{
			boolean coule = bateauxIA[grilleIA[positionX][positionY]-1].destroyCell(positionX,positionY);
			if(coule)
			{
				System.out.println("Coule !");
				IA.enleverVie();
			}
			else
				System.out.println("Touche !");
			grilleIAV[positionX][positionY] = 2;
		}
	}
	public static boolean[] bombe(int x, int y, int[][] grilleJoueur, int[][] grilleJoueurV, int[][] grilleIAV, Scanner sc, Joueur IA, Joueur joueur, int[][] grilleIA, Bateau[] bateauxIA, Bateau[] bateauxJ)
	{
		boolean[] etat = {false,false};
		if(joueur.getLife() != 0 && IA.getLife() != 0)
		{			
			if(grilleJoueur[x-1][y-1] == 0)
			{
				System.out.println("L'ordinateur n'a rien touché !");
				grilleJoueurV[x][y] = 1;
			}
			else
			{
				etat[0] = true;
				etat[1] = bateauxJ[grilleJoueur[x-1][y-1]-1].destroyCell(x-1,y-1);
				if(etat[1])
				{
					System.out.println("L'ordinateur a coulé votre "+bateauxJ[grilleJoueur[x-1][y-1]-1].getName()+" !");
					joueur.enleverVie();
				}
				else
				{
					System.out.println("L'ordinateur a touché votre "+bateauxJ[grilleJoueur[x-1][y-1]-1].getName()+" !");
				}
				grilleJoueurV[x][y] = 2;
			}
			for(int i = 1; i < grilleJoueurV.length-1; i++)
			{
				for(int j = 1; j < grilleJoueurV[0].length-1; j++)
				{
					if(grilleJoueurV[i-1][j] == 1 && grilleJoueurV[i+1][j] == 1 && grilleJoueurV[i][j-1] == 1 && grilleJoueurV[i][j+1] == 1)
						grilleJoueurV[i][j] = 1;
				}
			}
			
			
			
			if(joueur.getLife() != 0 && IA.getLife() != 0)
			{
				/*
				System.out.print("Votre grille :");
				afficherGrille(grilleJoueurV);
				afficherGrilleDev(grilleJoueurV);
				 */
				tourJoueur(grilleIAV, sc, IA, grilleIA, bateauxIA);
			}
		}
		return etat;
	}
	public static boolean[] recherche(int x, int y, int direction, int[][] grilleJoueur, int[][] grilleJoueurV, int[][] grilleIAV, Scanner sc, Joueur IA, Joueur joueur, int[][] grilleIA, Bateau[] bateauxIA, Bateau[] bateauxJ)
	{
		boolean[] etat = {true,false};
		int i = 1;
		boolean caseVide = false;
		for(int z = 0; z<grilleJoueurV.length - x; z++)
		{
			if(grilleJoueurV[z][y] == 0)
				caseVide = true;
		}
		for(int m = 0; m<grilleJoueurV[0].length - 1; m++)
		{
			if(grilleJoueurV[x][m] == 0)
				caseVide = true;
		}
		if(caseVide && joueur.getLife() != 0 && IA.getLife() != 0)
		{
			if(grilleJoueurV[x-1][y] !=0 && grilleJoueurV[x+1][y] !=0 && grilleJoueurV[x][y-1] !=0 && grilleJoueurV[x][y+1] !=0)
			{
				etat[0] = false;
				etat[1] = false;
			}
			else
			{
				if(direction == 1)
				{
					while(!etat[1] && etat[0] && grilleJoueurV[x-i][y] == 0)
					{
						etat = bombe(x-i, y, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
						i++;
						if(etat[1])
						{
							for(int w = 0; w<3; w++)
							{
								try
								{
									if(grilleJoueurV[x-(i-1)][y] == 2)
										grilleJoueurV[x-(i-1)][y] = 3;
								}
								finally
								{
									i--;
								}
							}
							boolean aTrouve = false;
							int count = 1;
							while((!aTrouve && count != 300) && (joueur.getLife() != 0 && IA.getLife() != 0))
							{
								int g = (int)(Math.random()*12);
								int h = (int)(Math.random()*12);
								if(grilleJoueurV[g][h] == 2 && joueur.getLife() != 0 && IA.getLife() != 0)
								{
									etat = recherche(g, h, (int)((Math.random()*4)+1), grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
									if(!etat[0] && !etat[1])
										grilleJoueurV[g][h] = 3;
								}
								count++;
							}
						}
					}
					if(!etat[1] && grilleJoueurV[x+1][y] == 0)
					{
						etat = recherche(x, y, 2, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
					}
					else if (!etat[1] && grilleJoueurV[x][y-1] == 0)
					{
						etat = recherche(x, y, 3, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
					}
					else if (!etat[1] && grilleJoueurV[x][y+1] == 0)
					{
						etat = recherche(x, y, 4, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
					}					
				}
				else if(direction == 2)
				{
					while(!etat[1] && etat[0] && grilleJoueurV[x+i][y] == 0)
					{
						etat = bombe(x+i, y, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
						i++;
						if(etat[1])
						{
							for(int w = 0; w<3; w++)
							{
								try
								{
									if(grilleJoueurV[x+(i-1)][y] == 2)
										grilleJoueurV[x+(i-1)][y] = 3;
								}
								finally
								{
									i--;
								}
							}
							boolean aTrouve = false;
							int count = 1;
							while((!aTrouve && count != 300) && (joueur.getLife() != 0 && IA.getLife() != 0))
							{
								int g = (int)(Math.random()*12);
								int h = (int)(Math.random()*12);
								if(grilleJoueurV[g][h] == 2 && joueur.getLife() != 0 && IA.getLife() != 0)
								{
									etat = recherche(g, h, (int)((Math.random()*4)+1), grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
									if(!etat[0] && !etat[1])
										grilleJoueurV[g][h] = 3;
								}
								count++;
							}
						}
					}
					if(!etat[1] && grilleJoueurV[x-1][y] == 0)
					{
						etat = recherche(x, y, 1, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
					}
					else if (!etat[1] && grilleJoueurV[x][y-1] == 0)
					{
						etat = recherche(x, y, 3, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
					}
					else if (!etat[1] && grilleJoueurV[x][y+1] == 0)
					{
						etat = recherche(x, y, 4, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
					}
				}
				else if(direction == 3)
				{
					while(!etat[1] && etat[0] && grilleJoueurV[x][y-i] == 0)
					{
						etat = bombe(x, y-i, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
						i++;
						if(etat[1])
						{
							for(int w = 0; w<3; w++)
							{
								try
								{
									if(grilleJoueurV[x][y-(i-1)] == 2)
										grilleJoueurV[x][y-(i-1)] = 3;
								}
								finally
								{
									i--;
								}
							}
							boolean aTrouve = false;
							int count = 1;
							while((!aTrouve && count != 300) && (joueur.getLife() != 0 && IA.getLife() != 0))
							{
								int g = (int)(Math.random()*12);
								int h = (int)(Math.random()*12);
								if(grilleJoueurV[g][h] == 2 && joueur.getLife() != 0 && IA.getLife() != 0)
								{
									etat = recherche(g, h, (int)((Math.random()*4)+1), grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
									if(!etat[0] && !etat[1])
										grilleJoueurV[g][h] = 3;
								}
								count++;
							}
						}
					}
					if(!etat[1] && grilleJoueurV[x][y+1] == 0)
					{
						etat = recherche(x, y, 4, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
					}
					else if (!etat[1] && grilleJoueurV[x-1][y] == 0)
					{
						etat = recherche(x, y, 1, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
					}
					else if (!etat[1] && grilleJoueurV[x+1][y] == 0)
					{
						etat = recherche(x, y, 2, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
					}
				}
				else
				{
					while(!etat[1] && etat[0] && grilleJoueurV[x][y+i] == 0)
					{
						etat = bombe(x, y+i, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
						i++;
						if(etat[1])
						{
							for(int w = 0; w<3; w++)
							{
								try
								{
									if(grilleJoueurV[x][y+(i-1)] == 2)
										grilleJoueurV[x][y+(i-1)] = 3;
								}
								finally
								{
									i--;
								}
							}
							boolean aTrouve = false;
							int count = 1;
							while((!aTrouve && count != 300) && (joueur.getLife() != 0 && IA.getLife() != 0))
							{
								int g = (int)(Math.random()*12);
								int h = (int)(Math.random()*12);
								if(grilleJoueurV[g][h] == 2 && joueur.getLife() != 0 && IA.getLife() != 0)
								{
									etat = recherche(g, h, (int)((Math.random()*4)+1), grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
									if(!etat[0] && !etat[1])
										grilleJoueurV[g][h] = 3;
								}
								count++;
							}
						}
					}
					if(!etat[1] && grilleJoueurV[x][y-1] == 0)
					{
						etat = recherche(x, y, 3, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
					}
					else if (!etat[1] && grilleJoueurV[x-1][y] == 0)
					{
						etat = recherche(x, y, 1, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
					}
					else if (!etat[1] && grilleJoueurV[x+1][y] == 0)
					{
						etat = recherche(x, y, 2, grilleJoueur, grilleJoueurV, grilleIAV, sc, IA, joueur, grilleIA, bateauxIA, bateauxJ);
					}
				}
			}
		}
		else
		{
			etat[0] = false;
			etat[1] = false;
		}
		return etat;
	}
}
