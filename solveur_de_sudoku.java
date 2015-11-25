package solveur_de_sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author MAIGUIZO Maman Sani, MOUSTAPHA Omar Farah, AMEGBLETO Atsu Elom Yao, YOUMBI EKEN Bernadette Josiane  
 *
 */
public class Solveur_de_sudoku {

public static final int N=9;
    private int[][] grille;

    public Solveur_de_sudoku() {
       this.grille = new int [N][N];
    }
// Methode pour lire la grille a partir d'un fichier
public void readGrille(String Fichier) {
    int[][] input=new int [N][N];
	try {
        StringBuffer sbuf = new StringBuffer();
        String str;
        BufferedReader in = new BufferedReader(new FileReader(Fichier));
    	
        str = in.readLine();
        
    	int i=0;
        while (str != null) {
        	for (int j=0;j<str.trim().split(" ").length;j++){
        		input[i][j]=Integer.parseInt(str.trim().split(" ")[j]);
        	}
       	
        	str = in.readLine();
        	i++;
        }
        grille=input;
    } catch (IOException e) {
        System.err.println(e);
        System.out.println("Fichier " +Fichier+" inexistant...");
    }
 
}

	/**
	* Vérifie si la grille donnée est valide pour le jeu du sudoku, i.e ne content pas de doublons sur les lignes.
	* @param grille
	**/
    public boolean checkligne(int n, int[][] grille, int i) { // verifie si il n'y pas de repétition sur la ligne i pour le chiffre n
        for (int j = 0; j < 9; j++) {
            if (grille[i][j] == n) {
                return false;
            }
        }
        return true;
    }

	/**
	* Vérifie si la grille donnée est valide pour le jeu du sudoku, i.e ne content pas de doublons sur les colonnes.
	* @param grille
	**/
    public boolean checkcol(int n, int[][] grille, int j) { // verifie si il n'a pas de doublon sur la colonne j pour le chiffre n  
        for (int i = 0; i < 9; i++) {

            if (grille[i][j] == n) {
                return false;
            }
        }
        return true;
    }

		/**
	* verifie s'il n'existe pas de doublon dans la zone délimitée par i et j pour le chiffre n.
	* @param grille
	* @param n
	* @param i
	* @param j
	**/
    public boolean checkzone(int n, int[][] grille, int i, int j) { 
        int w = i - (i % 3), z = j - (j % 3);

        for (i = w; i < w + 3; i++) {
            for (j = z; j < z + 3; j++) {
                if (grille[i][j] == n) {
                    return false;
                }
            }
        }
        return true;
    }
    
	
    //prend en paramètre la grille de sudoku et le résous en faisant appelle au autre methode de récurcivement

    public boolean verificateurValide(int[][] grille, int position) {

        if (position == 9 * 9) {        // si on est à la position 81 il renvoie vraie (on fini de remplir le sudoku)
            return true;
        }

        int i = position / 9, j = position % 9;    // i la ligne = partie entière de la division & j la colonne = le reste de la division ( position [0 .....81]

        if (grille[i][j] != 0) {             // si il existe déjà un nombre à cette position on passe à la position suivante
            return verificateurValide(grille, position + 1);
        }

        for (int k = 1; k <= 9; k++) {

	// le nombre choisi (k) n'exite pas sur la colonne ni sur la ligne ni dans la zone ont peut l'inserer à la position i j
            if (checkligne(k, grille, i) && checkcol(k, grille, j) && checkzone(k, grille, i, j)) {

                grille[i][j] = k;

                if (verificateurValide(grille, position + 1)) {
                    return true;
                }
            }

        }
	// Tous les chiffres ont été testés, aucun n'est bon, on réinitialise la case et on retourne false
        grille[i][j] = 0;
        return false;
    }

    
    public void affiche( int [][] grille) {
        int k = 0;

        for (int i = 0; i < 9; i++) {
            int n = 0;
            if (k % 3 == 0) {
                System.err.print("\n");
            }
            k++;
            for (int j = 0; j < 9; j++) {
                n++;
                System.err.print(" " + grille[i][j]);
                if (n % 3 == 0) {
                    System.err.print(" ");
                }
                if (n == 9) {
                    System.err.println("");
                }
            }
        }
        System.err.print("--------------------");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Solveur_de_sudoku solveur_de_sudoku = new Solveur_de_sudoku();
        solveur_de_sudoku.readGrille("~Users/Public/workspace/solveur_de_sudoku/bin/solveur_de_sudoku/Fichier.txt");
        solveur_de_sudoku.affiche(solveur_de_sudoku.grille);
        solveur_de_sudoku.verificateurValide(solveur_de_sudoku.grille, 0);
        solveur_de_sudoku.affiche(solveur_de_sudoku.grille);
    }

}
