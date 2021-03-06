import java.util.Scanner;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * La classe principale du jeu.
 * @author Julien EMMANUEL, Charlotte RICHAD, Thomas DUTOUR, Alexis SAGET
 */
public class BatailleNavale {
	static Fenetre fen = new Fenetre("Grille adverse (Zaya)", true);
	static Fenetre fen2 = new Fenetre("Votre grille", false);
	static Bateau[] bateauxJ = new Bateau[5];
	static Bateau[] bateauxZaya = new Bateau[5];
	static Scanner sc = new Scanner(System.in);
	static int[][] grilleJoueur = new int[10][10];
	static int[][] grilleJoueurV = new int[12][12];
	static int[][] grilleZaya = new int[10][10];
	static int[][] grilleZayaV = new int[10][10];
	static Joueur joueur = new Joueur();
	static Joueur Zaya = new Joueur();
	
	/**
	 * Méthode principale du jeu, qui initialise la partie puis crée des tours de jeu tant qu'il reste des bateaux.
	 * @param args Paramètre non utilisé.
	 * @throws AddressException Erreur dans l'envoi d'email.
	 * @throws MessagingException Erreur dans l'envoi d'email.
	 */
	public static void main(String[] args) throws AddressException, MessagingException
	{
		System.out.println("Notice: \n\nBateaux: \n        - Un porte-avions (5 cases)\n        - Un croiseur (4 cases)\n        - Un contre-torpilleur (3 cases)\n        - Un sous-marin (3 cases)\n        - Un torpilleur (2 cases)\n\nPosition des bateaux: \n        - Si vous indiquez 'vertical' le bateau est positionné vers le bas à partir de la case indiquée précédemment \n        - À droite si vous indiquez 'horizontal'  \n\n");
		String reponse;	
		do{
		fen.reinitAff();
		fen2.reinitAff();
		// Grille du joueur :
		reinitialiser(grilleJoueur);
		fen2.setGrid(grilleJoueur);
		// Grille du joueur visible par Zaya :
		reinitialiser(grilleJoueurV);
		for(int i=0; i<12; i++)
			grilleJoueurV[0][i] = 1;
		for(int i=0; i<12; i++)
			grilleJoueurV[11][i] = 1;
		for(int i=0; i<12; i++)
			grilleJoueurV[i][0] = 1;
		for(int i=0; i<12; i++)
			grilleJoueurV[i][11] = 1;
		// Grille de Zaya :
		reinitialiser(grilleZaya);
		// Grille de Zaya visible par le joueur :
		reinitialiser(grilleZayaV);
		fen.setGrid(grilleZayaV);
		// Création des bateaux du joueur :
		bateauxJ[0] = new Bateau("Porte-avions",5);
		bateauxJ[1] = new Bateau("Croiseur",4);
		bateauxJ[2] = new Bateau("Contre-torpilleur",3);
		bateauxJ[3] = new Bateau("Sous-marin",3);
		bateauxJ[4] = new Bateau("Torpilleur",2);
		// Création des bateaux de Zaya :
		bateauxZaya[0] = new Bateau("Porte-avions",5);
		bateauxZaya[1] = new Bateau("Croiseur",4);
		bateauxZaya[2] = new Bateau("Contre-torpilleur",3);
		bateauxZaya[3] = new Bateau("Sous-marin",3);
		bateauxZaya[4] = new Bateau("Torpilleur",2);
		// Positionnement des bateaux :
		System.out.println("Voulez-vous placer les bateaux au hasard? oui/non");
		String choix = sc.nextLine();
		if (choix.equals("oui") || choix.equals("Oui") || choix.equals("OUI"))
		{
			positionnerJoueur();
			fen2.setGrid(grilleJoueur);
			System.out.println("Cela vous convient-il? oui/non");
			choix = sc.nextLine();
			while (choix.equals("non") || choix.equals("Non") || choix.equals("NON"))
			{
			reinitialiser(grilleJoueur);
			positionnerJoueur();
			fen2.setGrid(grilleJoueur);
			System.out.println("Cela vous convient-il? oui/non");
			choix = sc.nextLine();
			}
		}	
		else 
			positionner();
		positionnerZaya();
		joueur.setLife(5);
		Zaya.setLife(5);
		tourJoueur();
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
						boolean[] etatTemp = recherche(g, h, (int)((Math.random()*4)+1));
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
				boolean[] etat = bombe(x, y);
				if (etat[0] && !etat[1] && joueur.getLife() != 0 && Zaya.getLife() != 0)
				{
					recherche(x, y, (int)((Math.random()*4)+1));
				}
			}
			while((grilleJoueur[x-1][y-1] == 0 || grilleJoueur[x-1][y-1] == 1 ) && joueur.getLife() != 0 && Zaya.getLife() != 0);
		}
		fen.setGrid(grilleZayaV);
		boolean ZayaGagne;
		int bateauxRestants;
		if(joueur.getLife() == 0)
		{
			fen.setPerdu();
			fen2.setPerdu();
			System.out.println("Vous avez perdu.");
			ZayaGagne = true;
			bateauxRestants = Zaya.getLife();
		}
		else
		{
			fen.setGagne();
			fen2.setGagne();
			System.out.println("Vous avez gagné ! Bravo !");	
			ZayaGagne = false;
			bateauxRestants = joueur.getLife();
		}
		System.out.println("Vous pouvez laisser un commentaire sur la partie ou sur le jeu, ou simplement laisser libre cours à votre imagination :");
		String commentaire = sc.nextLine();
		Email mail = new Email(ZayaGagne, bateauxRestants, commentaire);
		System.out.println("Voulez-vous commencer une nouvelle partie? oui/non");
		reponse = sc.nextLine();
		
		}while(reponse.equals("oui") || reponse.equals("Oui") || reponse.equals("OUI"));
		
	fen.dispose();
	fen2.dispose();
	System.out.println("Merci d'avoir joué et à bientôt ;) !");
	sc.close();
	}
	/**
	 * Demande à l'utilisateur la position de ses bateaux et les ajoute à sa grille.
	 */
	public static void positionner()
	{
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
	 */
	public static void positionnerZaya()
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
	 * Positionne aléatoirement les bateaux du joueur.
	 */
	public static void positionnerJoueur()
	{
		bateauxJ[0].position(1,3,true);
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
				bateauxJ[i].position(positionX, positionY,vertical);
				dansLaGrille = bateauxJ[i].verifierPosition();
				if(dansLaGrille)
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
	 public static void tourJoueur()
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
		System.out.println("----------");
		if(grilleZaya[positionX][positionY] == 0)
		{
			System.out.println("Vous n'avez rien touche !");
			grilleZayaV[positionX][positionY] = 1;
		}
		else if(grilleZaya[positionX][positionY] == 58)
		{
			System.out.println("Vous avez déjà touché cette case !f");
		}
		else
		{
			boolean coule = bateauxZaya[grilleZaya[positionX][positionY]-1].destroyCell(positionX,positionY);
			grilleZaya[positionX][positionY] = 58;
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
	 /**
	  * Méthode utilisée par Zaya pour tirer sur une case dans la grille du joueur.
	  * @param x Position horizontale de la case visée.
	  * @param y Position verticale de la case visée.
	  * @return Le premier booleen vaut TRUE si la bombe touche un bateau, le deuxième vaut TRUE si la bombe coule un bateau, sinon les booleens valent FALSE.
	  */
	public static boolean[] bombe(int x, int y)
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
			System.out.println("----------");
			
			
			if(joueur.getLife() != 0 && Zaya.getLife() != 0)
			{
				tourJoueur();
			}
		}
		return etat;
	}
	/**
	 * Définit le comportement de Zaya à partir du moment où elle touche un bateau.
	 * @param x Position horizontale de départ de la recherche (de la case touchée donc).
	 * @param y Position horizontale de départ de la recherche (de la case touchée donc).
	 * @param direction La direction dans laquelle Zaya commence sa recherche.
	 * @return Le premier booleen vaut TRUE si Zaya touche un bateau, le deuxième vaut TRUE si Zaya coule un bateau, sinon les booleens valent FALSE.
	 */
	public static boolean[] recherche(int x, int y, int direction)
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
						etat = bombe(x-i, y);
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
									etat = recherche(g, h, (int)((Math.random()*4)+1));
									if(!etat[0] && !etat[1])
										grilleJoueurV[g][h] = 3;
								}
								count++;
							}
						}
					}
					if(!etat[1] && grilleJoueurV[x+1][y] == 0)
					{
						etat = recherche(x, y, 2);
					}
					else if (!etat[1] && grilleJoueurV[x][y-1] == 0)
					{
						etat = recherche(x, y, 3);
					}
					else if (!etat[1] && grilleJoueurV[x][y+1] == 0)
					{
						etat = recherche(x, y, 4);
					}					
				}
				else if(direction == 2)
				{
					while(!etat[1] && etat[0] && grilleJoueurV[x+i][y] == 0)
					{
						etat = bombe(x+i, y);
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
									etat = recherche(g, h, (int)((Math.random()*4)+1));
									if(!etat[0] && !etat[1])
										grilleJoueurV[g][h] = 3;
								}
								count++;
							}
						}
					}
					if(!etat[1] && grilleJoueurV[x-1][y] == 0)
					{
						etat = recherche(x, y, 1);
					}
					else if (!etat[1] && grilleJoueurV[x][y-1] == 0)
					{
						etat = recherche(x, y, 3);
					}
					else if (!etat[1] && grilleJoueurV[x][y+1] == 0)
					{
						etat = recherche(x, y, 4);
					}
				}
				else if(direction == 3)
				{
					while(!etat[1] && etat[0] && grilleJoueurV[x][y-i] == 0)
					{
						etat = bombe(x, y-i);
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
									etat = recherche(g, h, (int)((Math.random()*4)+1));
									if(!etat[0] && !etat[1])
										grilleJoueurV[g][h] = 3;
								}
								count++;
							}
						}
					}
					if(!etat[1] && grilleJoueurV[x][y+1] == 0)
					{
						etat = recherche(x, y, 4);
					}
					else if (!etat[1] && grilleJoueurV[x-1][y] == 0)
					{
						etat = recherche(x, y, 1);
					}
					else if (!etat[1] && grilleJoueurV[x+1][y] == 0)
					{
						etat = recherche(x, y, 2);
					}
				}
				else
				{
					while(!etat[1] && etat[0] && grilleJoueurV[x][y+i] == 0)
					{
						etat = bombe(x, y+i);
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
									etat = recherche(g, h, (int)((Math.random()*4)+1));
									if(!etat[0] && !etat[1])
										grilleJoueurV[g][h] = 3;
								}
								count++;
							}
						}
					}
					if(!etat[1] && grilleJoueurV[x][y-1] == 0)
					{
						etat = recherche(x, y, 3);
					}
					else if (!etat[1] && grilleJoueurV[x-1][y] == 0)
					{
						etat = recherche(x, y, 1);
					}
					else if (!etat[1] && grilleJoueurV[x+1][y] == 0)
					{
						etat = recherche(x, y, 2);
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
	/**
	 * Remplit un tableau de 0.
	 * @param tab Le tableau à réinitialiser.
	 */
	public static void reinitialiser (int[][] tab)
	{
		for (int i=0; i<tab.length; i++)
		{
			for (int j=0; j<tab[i].length; j++)
			{
				tab[i][j] = 0;
			}
		}
	}
}

