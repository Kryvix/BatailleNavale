import java.util.Scanner;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * La classe principale du jeu.
 * @author Julien EMMANUEL, Charlotte RICHAD, Thomas DUTOUR, Alexis SAGET
 */
public class BatailleNavale {
	private static Fenetre fen = new Fenetre("Grille adverse (Zaya)", true);
	private static Fenetre fen2 = new Fenetre("Votre grille", false);
	public static void main(String[] args) //throws AddressException, MessagingException
	{
		// Grille du joueur :
		int[][] grilleJoueur = new int[10][10];
		// Grille du joueur visible par Zaya :
		int[][] grilleJoueurV = new int[12][12];
		for(int i=0; i<12; i++)
			grilleJoueurV[0][i] = 1;
		for(int i=0; i<12; i++)
			grilleJoueurV[11][i] = 1;
		for(int i=0; i<12; i++)
			grilleJoueurV[i][0] = 1;
		for(int i=0; i<12; i++)
			grilleJoueurV[i][11] = 1;
		// Grille de Zaya :
		int[][] grilleZaya = new int[10][10];
		// Grille de Zaya visible par le joueur :
		int[][] grilleZayaV = new int[10][10];
		fen.setGrid(grilleZayaV);
		// Création des bateaux du joueur :
		Bateau[] bateauxJ = new Bateau[5];
		bateauxJ[0] = new Bateau("Porte-avions",5);
		bateauxJ[1] = new Bateau("Croiseur",4);
		bateauxJ[2] = new Bateau("Contre-torpilleur",3);
		bateauxJ[3] = new Bateau("Sous-marin",3);
		bateauxJ[4] = new Bateau("Torpilleur",2);
		// Création des bateaux de Zaya :
		Bateau[] bateauxZaya = new Bateau[5];
		bateauxZaya[0] = new Bateau("Porte-avions",5);
		bateauxZaya[1] = new Bateau("Croiseur",4);
		bateauxZaya[2] = new Bateau("Contre-torpilleur",3);
		bateauxZaya[3] = new Bateau("Sous-marin",3);
		bateauxZaya[4] = new Bateau("Torpilleur",2);
		// Positionnement des bateaux :
		positionner(bateauxJ, grilleJoueur);
		positionnerZaya(bateauxZaya, grilleZaya);
		//afficherGrilleDev(grilleZaya);
		Joueur joueur = new Joueur();
		Joueur Zaya = new Joueur();
		Scanner sc = new Scanner(System.in);
		tourJoueur(grilleZayaV, sc, Zaya, grilleZaya, bateauxZaya);
		while(joueur.getLife() != 0 && Zaya.getLife() != 0)
		{
			int x = 0;
			int y = 0;
			do
			{
				boolean aTrouve = false;
				int count = 1;
				while((!aTrouve && count != 300) && (joueur.getLife() != 0 && Zaya.getLife() != 0))
				{
					int g = (int)(Math.random()*12);
					int h = (int)(Math.random()*12);
					if(grilleJoueurV[g][h] == 2 && joueur.getLife() != 0 && Zaya.getLife() != 0)
					{
						boolean[] etatTemp = recherche(g, h, (int)((Math.random()*4)+1), grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
						if(!etatTemp[0] && !etatTemp[1])
							grilleJoueurV[g][h] = 3;
					}
					count++;
				}
				while(grilleJoueurV[x][y] != 0 && joueur.getLife() != 0 && Zaya.getLife() != 0)
				{
					x = (int)(Math.random()*12);
					y = (int)(Math.random()*12);
				}
				boolean[] etat = bombe(x, y, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
				if (etat[0] && !etat[1] && joueur.getLife() != 0 && Zaya.getLife() != 0)
				{
					recherche(x, y, (int)((Math.random()*4)+1), grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
				}
			}
			while((grilleJoueur[x-1][y-1] == 0 || grilleJoueur[x-1][y-1] == 1 ) && joueur.getLife() != 0 && Zaya.getLife() != 0);
		}
		fen.setGrid(grilleZayaV);
		if(joueur.getLife() == 0)
		{
			fen.setPerdu();
			fen2.setPerdu();
			System.out.println("Vous avez perdu.");
			//Email mail = new Email(true);
		}
		else
		{
			fen.setGagne();
			fen2.setGagne();
			System.out.println("Vous avez gagné ! Bravo !");
			//Email mail = new Email(false);
		}
	}
	/**
	 * Demande à l'utilisateur la position de ses bateaux et les ajoute à sa grille.
	 * @param bateauxJ Le tableau des bateaux à positionner.
	 * @param grilleJoueur La grille dans laquelle les bateaux seront positionnés.
	 */
	public static void positionner(Bateau[] bateauxJ, int[][] grilleJoueur)
	{
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
				System.out.println("Ligne du "+bateauxJ[i].getName()+" :");
				String ligneString = sc.nextLine();
				while(ligneString.length()==0)
					ligneString = sc.nextLine();
				while(!verifierNombre(ligneString))
				{
					System.out.println("Veuillez entrer un nombre entre 1 et 10 :");
					ligneString = sc.nextLine();
					while(ligneString.length()==0)
						ligneString = sc.nextLine();
				}
				System.out.println("Colonne du "+bateauxJ[i].getName()+" :");
				String colonneString = sc.nextLine();
				while(colonneString.length()==0)
					colonneString = sc.nextLine();
				while(!verifierNombre(colonneString))
				{
					System.out.println("Veuillez entrer un nombre entre 1 et 10 :");
					colonneString = sc.nextLine();
					while(colonneString.length()==0)
						colonneString = sc.nextLine();
				}
				System.out.println("Le "+bateauxJ[i].getName()+" doit il être vertical ou horizontal (horizontal = 0, vertical = 1) :");
				String verticalString = sc.nextLine();
				while(verticalString.length()==0)
					verticalString = sc.nextLine();
				while(verticalString.charAt(0) != '0' && verticalString.charAt(0) != '1')
				{
					System.out.println("Veuillez entrer 0 ou 1 :");
					verticalString = sc.nextLine();
					while(verticalString.length()==0)
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
			fen2.setGrid(grilleJoueur);
		}
	}
	/**
	 * Positionne aléatoirement les bateaux de Zaya.
	 * @param bateauxZaya Le tableau des bateaux à positionner.
	 * @param grilleZaya La grille dans laquelle les bateaux seront positionnés.
	 */
	public static void positionnerZaya(Bateau[] bateauxZaya, int[][] grilleZaya)
	{
		bateauxZaya[0].position(1,3,true);
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
				bateauxZaya[i].position(positionX, positionY,vertical);
				dansLaGrille = bateauxZaya[i].verifierPosition();
				if(dansLaGrille)
				{
					for(int k = 0; k<bateauxZaya[i].getTaille(); k++)
					{
						int x = 0;
						int y = 0;
						if(bateauxZaya[i].isVertical())
						{
							x = positionX;
							y = positionY + k;
						}
						else
						{
							x = positionX + k;
							y = positionY;
						}
						if(grilleZaya[x][y] != 0)
							casePrise = true;
					}
				}
			}
			for(int j = 0; j<bateauxZaya[i].getTaille(); j++)
			{
				int x = 0;
				int y = 0;
				if(bateauxZaya[i].isVertical())
				{
					x = positionX;
					y = positionY + j;
					
				}
				else
				{
					x = positionX + j;
					y = positionY;
				}
				grilleZaya[x][y] = i+1;
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
	 * Effectue un tour du joueur
	 */
	 public static void tourJoueur(int[][] grilleZayaV, Scanner sc, Joueur Zaya, int[][] grilleZaya, Bateau[] bateauxZaya)
	 {
		fen.setGrid(grilleZayaV);
		System.out.println("Ligne de la case a viser :");
		String ligneString = sc.nextLine();
		while(ligneString.length()==0)
			ligneString = sc.nextLine();
		while(!verifierNombre(ligneString))
		{
			System.out.println("Veuillez entrer un nombre entre 1 et 10 :");
			ligneString = sc.nextLine();
			while(ligneString.length()==0)
				ligneString = sc.nextLine();
		}
		System.out.println("Colonne de la case a viser :");
		String colonneString = sc.nextLine();
		while(colonneString.length()==0)
			colonneString = sc.nextLine();
		while(!verifierNombre(colonneString))
		{
			System.out.println("Veuillez entrer un nombre entre 1 et 10 :");
			colonneString = sc.nextLine();
			while(colonneString.length()==0)
				colonneString = sc.nextLine();
		}
		int positionX = Integer.parseInt(colonneString) - 1;
		int positionY = Integer.parseInt(ligneString) - 1;
		if(grilleZaya[positionX][positionY] == 0)
		{
			System.out.println("Vous n'avez rien touche !");
			grilleZayaV[positionX][positionY] = 1;
		}
		else
		{
			boolean coule = bateauxZaya[grilleZaya[positionX][positionY]-1].destroyCell(positionX,positionY);
			if(coule)
			{
				System.out.println("Coule !");
				Zaya.enleverVie();
			}
			else
				System.out.println("Touche !");
			grilleZayaV[positionX][positionY] = 2;
		}
	}
	public static boolean[] bombe(int x, int y, int[][] grilleJoueur, int[][] grilleJoueurV, int[][] grilleZayaV, Scanner sc, Joueur Zaya, Joueur joueur, int[][] grilleZaya, Bateau[] bateauxZaya, Bateau[] bateauxJ)
	{
		boolean[] etat = {false,false};
		if(joueur.getLife() != 0 && Zaya.getLife() != 0)
		{
			if(grilleJoueur[x-1][y-1] == 0)
			{
				System.out.println("L'ordinateur n'a rien touché !");
				grilleJoueurV[x][y] = 1;
				grilleJoueur[x-1][y-1] = 42;
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
				grilleJoueur[x-1][y-1] = 6;
			}
			fen2.setGrid(grilleJoueur);
			for(int i = 1; i < grilleJoueurV.length-1; i++)
			{
				for(int j = 1; j < grilleJoueurV[0].length-1; j++)
				{
					if(grilleJoueurV[i-1][j] == 1 && grilleJoueurV[i+1][j] == 1 && grilleJoueurV[i][j-1] == 1 && grilleJoueurV[i][j+1] == 1)
						grilleJoueurV[i][j] = 1;
				}
			}
			
			
			
			if(joueur.getLife() != 0 && Zaya.getLife() != 0)
			{
				/*
				System.out.print("Votre grille :");
				afficherGrille(grilleJoueurV);
				afficherGrilleDev(grilleJoueurV);
				 */
				tourJoueur(grilleZayaV, sc, Zaya, grilleZaya, bateauxZaya);
			}
		}
		return etat;
	}
	public static boolean[] recherche(int x, int y, int direction, int[][] grilleJoueur, int[][] grilleJoueurV, int[][] grilleZayaV, Scanner sc, Joueur Zaya, Joueur joueur, int[][] grilleZaya, Bateau[] bateauxZaya, Bateau[] bateauxJ)
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
		if(caseVide && joueur.getLife() != 0 && Zaya.getLife() != 0)
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
						etat = bombe(x-i, y, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
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
							while((!aTrouve && count != 300) && (joueur.getLife() != 0 && Zaya.getLife() != 0))
							{
								int g = (int)(Math.random()*12);
								int h = (int)(Math.random()*12);
								if(grilleJoueurV[g][h] == 2 && joueur.getLife() != 0 && Zaya.getLife() != 0)
								{
									etat = recherche(g, h, (int)((Math.random()*4)+1), grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
									if(!etat[0] && !etat[1])
										grilleJoueurV[g][h] = 3;
								}
								count++;
							}
						}
					}
					if(!etat[1] && grilleJoueurV[x+1][y] == 0)
					{
						etat = recherche(x, y, 2, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
					}
					else if (!etat[1] && grilleJoueurV[x][y-1] == 0)
					{
						etat = recherche(x, y, 3, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
					}
					else if (!etat[1] && grilleJoueurV[x][y+1] == 0)
					{
						etat = recherche(x, y, 4, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
					}					
				}
				else if(direction == 2)
				{
					while(!etat[1] && etat[0] && grilleJoueurV[x+i][y] == 0)
					{
						etat = bombe(x+i, y, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
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
							while((!aTrouve && count != 300) && (joueur.getLife() != 0 && Zaya.getLife() != 0))
							{
								int g = (int)(Math.random()*12);
								int h = (int)(Math.random()*12);
								if(grilleJoueurV[g][h] == 2 && joueur.getLife() != 0 && Zaya.getLife() != 0)
								{
									etat = recherche(g, h, (int)((Math.random()*4)+1), grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
									if(!etat[0] && !etat[1])
										grilleJoueurV[g][h] = 3;
								}
								count++;
							}
						}
					}
					if(!etat[1] && grilleJoueurV[x-1][y] == 0)
					{
						etat = recherche(x, y, 1, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
					}
					else if (!etat[1] && grilleJoueurV[x][y-1] == 0)
					{
						etat = recherche(x, y, 3, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
					}
					else if (!etat[1] && grilleJoueurV[x][y+1] == 0)
					{
						etat = recherche(x, y, 4, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
					}
				}
				else if(direction == 3)
				{
					while(!etat[1] && etat[0] && grilleJoueurV[x][y-i] == 0)
					{
						etat = bombe(x, y-i, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
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
							while((!aTrouve && count != 300) && (joueur.getLife() != 0 && Zaya.getLife() != 0))
							{
								int g = (int)(Math.random()*12);
								int h = (int)(Math.random()*12);
								if(grilleJoueurV[g][h] == 2 && joueur.getLife() != 0 && Zaya.getLife() != 0)
								{
									etat = recherche(g, h, (int)((Math.random()*4)+1), grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
									if(!etat[0] && !etat[1])
										grilleJoueurV[g][h] = 3;
								}
								count++;
							}
						}
					}
					if(!etat[1] && grilleJoueurV[x][y+1] == 0)
					{
						etat = recherche(x, y, 4, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
					}
					else if (!etat[1] && grilleJoueurV[x-1][y] == 0)
					{
						etat = recherche(x, y, 1, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
					}
					else if (!etat[1] && grilleJoueurV[x+1][y] == 0)
					{
						etat = recherche(x, y, 2, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
					}
				}
				else
				{
					while(!etat[1] && etat[0] && grilleJoueurV[x][y+i] == 0)
					{
						etat = bombe(x, y+i, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
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
							while((!aTrouve && count != 300) && (joueur.getLife() != 0 && Zaya.getLife() != 0))
							{
								int g = (int)(Math.random()*12);
								int h = (int)(Math.random()*12);
								if(grilleJoueurV[g][h] == 2 && joueur.getLife() != 0 && Zaya.getLife() != 0)
								{
									etat = recherche(g, h, (int)((Math.random()*4)+1), grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
									if(!etat[0] && !etat[1])
										grilleJoueurV[g][h] = 3;
								}
								count++;
							}
						}
					}
					if(!etat[1] && grilleJoueurV[x][y-1] == 0)
					{
						etat = recherche(x, y, 3, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
					}
					else if (!etat[1] && grilleJoueurV[x-1][y] == 0)
					{
						etat = recherche(x, y, 1, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
					}
					else if (!etat[1] && grilleJoueurV[x+1][y] == 0)
					{
						etat = recherche(x, y, 2, grilleJoueur, grilleJoueurV, grilleZayaV, sc, Zaya, joueur, grilleZaya, bateauxZaya, bateauxJ);
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
