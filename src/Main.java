import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;


public class Main {
	//Stockage de toutes nos constantes
	
	//Nom du fichier à lire
	private static String nom_fichier = "TestGolfConcave.txt";
	
	//pour la taille de la fenetre graphique
	private static int haut = 700;		
	private static int larg = 700;
	
	//Pour la lecture du fichier txt
	private static int compteur;
	private static int nb_parcours;
	
	//Pour l'affichage de la triangularisation
	private static boolean triangle = false;
	
	//Pour la lecture dans la console
	private static Scanner sc;

	//Getter pour retourner la hauteur de la fenetre
	public static int getHaut() {
		return haut;
	}

	//Getter pour retourner la largeur de la fenetre
	public static int getLarg() {
		return larg;
	}

	//Getter pour retourner le booleen triangle
	public static boolean getTriangle(){
		return triangle;
	}
	
	//Getter pour retourner le nombre de parcours présent dans le fichier
	public static int getNb_parcours() {
		return nb_parcours;
	}

	//Fonction qui permet de stocker les données du fichier texte chaine de caractères par chaine de caractères, 
	//sans prendre en compte par les parenthèses ou les virgules. On stock ainsi toutes les chaines dans un arrayList.
	public static ArrayList<String> lectureFichier(){
        ArrayList<String> donnees = new ArrayList<String>();
        
		//Lecture du fichier text
	        try {
	            FileReader c = new FileReader(nom_fichier); //pour indiquer quel fichier lire
	            BufferedReader r = new BufferedReader(c);
	 
	            String line = r.readLine();
	            
	            while (line != null) { //on va lire ligne par ligne
	            	for (String val : line.split("[(,)]+")){ //on prend toutes les valeurs se trouvant entre des () ou des ,
	            		donnees.add(val); //on stocke les valeurs
	            	}
	           line = r.readLine();
	            }
	            r.close();

	        } catch (Exception e) { //Pour les erreurs
	            throw new Error(e);
	        }
	        return donnees;
	}

	//Fonction qui permet de traiter les données récupéré dans le fichier pour créer les polygones indiqués.
	//Avec lectureFichier(), on a toutes les valeurs du fichier a suivre dans un arraylist de string
	//chaque case contenant un espace indique qu'on passe la ligne suivante (donc à un autre polygone)
	//la premiere valeur indique le nb de polygone      	
	public static ArrayList<Polygone> lirePoly(){
   
		int nb = Integer.parseInt(lectureFichier().get(0)); //nombre de plolygone indiqué à la 1ere ligne du fichier
		ArrayList<Polygone> polys = new ArrayList<Polygone>(); //on va y stocker tous nos polygones
		ArrayList<Point> pt = new ArrayList<Point>();
		String couleur = " "; //Initialisation de la couleur
		int i = 2; //en 0 on a le nb de poly, en 1 on a " " donc on commence en 2
		//Lecture des polygones
		while (nb != 0){ //tant qu'il nous reste des poly a lire
			//si le string lu n'est pas une couleur, alors c'est un double et on va creer un point avec le double qui suit
			if (!lectureFichier().get(i).equals("V") && !lectureFichier().get(i).equals("C") && !lectureFichier().get(i).equals("B") && !lectureFichier().get(i).equals("S") && !lectureFichier().get(i).equals("J")){
				pt.add(new Point(Double.parseDouble(lectureFichier().get(i)),Double.parseDouble(lectureFichier().get(i+1))));
			} else { //sinon, c'est que c'est une couleur, on la donne au polygone et on l'ajoute à notre liste de poly.
				couleur = lectureFichier().get(i);
				polys.add(new Polygone(pt,couleur));
				nb -= 1; //on decremente le nb de poly à lire
				//on vide pt chaque nouveau polygone
				pt = new ArrayList<Point>();
			}
			i += 2; //on se deplace deux par deux car couples (x1, y1), (x2, y2), ...., (couleur, /n)
		}
		compteur = i; //on stocke à quel indice on est pour lire la suite du fichier
		nb_parcours = Integer.parseInt(lectureFichier().get(compteur-1));
		return polys;
	}
	
	//Fonction qui va lire les polygones des parcours donnes dans le fichier
	//int i : le numero du parcours à lire (commence a 1)
	public static ArrayList<Polygone> lirePolyParcours(int i){ 
		
		ArrayList<Polygone> li = new ArrayList<Polygone>(); //on stocke les poly parcouru pdt le parcours
		int comptmp = compteur; //on reprend l'indice de la fin de lecture des polynomes
		
		comptmp = comptmp + (i-1)*9; //pour acceder au debut de la ligne du parcours num i
		
			for (int j = 0; j < 4; j++){ //pour lire les li 
				li.add(j, lirePoly().get(Integer.parseInt((lectureFichier().get(comptmp)))-1)); //le premier poly est l'indice 0 donc -1
				comptmp++; //on passe au string suivant
			}
		return li;
	}
	
	//Fonction qui va lire le point de depart du parcours i
	//int i : le numero du parcours à lire (commence a 1)
	public static Point lireDepartParcours(int i){
		Point depart = null;
		int comptmp = compteur;
		
		comptmp = (comptmp + 4) + (i-1)*9; //pour acceder à l'indice du x du pt de depart du parcours num i
		depart = new Point(Double.parseDouble((lectureFichier().get(comptmp))), Double.parseDouble((lectureFichier().get(comptmp+1))));
		
		return depart;
	}
	
	//Fonction qui va lire le point d'arrive du parcours i
	//int i : le numero du parcours à lire (commence a 1)
	public static Point lireArriveParcours(int i){
			Point arrive = null;
			int comptmp = compteur;
			
			comptmp = (comptmp + 6) + (i-1)*9;//pour acceder à l'indice du x du pt de d'arrive du parcours num i
			arrive = new Point(Double.parseDouble((lectureFichier().get(comptmp))), Double.parseDouble((lectureFichier().get(comptmp+1))));

			return arrive;
	}
	
	//Fonction qui récupère le par du parcours i (commence a 1)
	public static int lireParParcours(int i){
		int Par;
		int comptmp = compteur;
		
		comptmp = (comptmp + 8) + (i-1)*9;
		Par = Integer.parseInt((lectureFichier().get(comptmp)));	
		
		return Par;
	}
	
	//Le main ou nous allons gerer les entrees/sorties de la console et creer la fenetre pour l'affichage graphique
	public static void main(String[] args) {
		
		// creation du panneau d'affichage
		Affichage panneau = new Affichage();

		// creation de la fenetre principale contenant le panneau
		new Fenetre(panneau);
		
		//Pour la lecture dans la console
		sc = new Scanner(System.in);
		
		//Mise en place du menu
		//menu à afficher dans la console
		System.out.println("taper 1 pour tester TriangulationTerrain");	
		System.out.println("taper 2 pour tester ConstructionQT");
		System.out.println("taper 3 pour tester CalculCoefficientsDroite");
		System.out.println("taper 4 pour tester TestIntersectionDeuxSegments");
		System.out.println("taper 5 pour tester TestRegionIntersecteTriangle");
		System.out.println("taper 6 pour tester TestTriangleContientPoint");
		System.out.println("taper 7 pour tester RecherchePointQT");
		System.out.println("taper 8 pour tester RecherchePointTriangle");
		System.out.println("taper 9 pour tester CalculePointAtterrissageBalle");
		System.out.println("taper 10 pour tester CalculePointDepartBalle");
		System.out.println("taper 11 pour jouer");
		System.out.println("Votre choix : ");
		
		//lecture du choix
		int i = sc.nextInt();
		
		//traitement du choix après la lecture
		while ( true){
			if (i == 1){
				System.out.println("vous avez choisi triangulation terrain ");
				triangle = true;
				panneau.repaint();
			}
			else if (i == 2){
				System.out.println("vous avez choisi  ConstructionQT ");
				System.out.println("nombre de triangle max par region : ");
				int n = sc.nextInt();
				ArrayList<Triangle> triangles = new ArrayList<Triangle>();
				ArrayList<Polygone> nosPoly = new ArrayList<Polygone>();
				nosPoly = lirePoly();
				for ( Polygone P : nosPoly){
					P.triangulation_totale();
					for (Triangle T : P.getT()){
						triangles.add(T);
					}
				}
				Region R = new Region (0.0 , 0.0 ,10.0 ,10.0);
				Quadtree Q = new Quadtree (R , n, triangles);
				Q.Contruction();
			}
			else if(i == 3){
				System.out.println("vous avez choisi CalculCoefficientsDroite ");
				System.out.println("coordonnee de A, x puis y ");
				double a = sc.nextDouble();
				double b = sc.nextDouble();
				System.out.println("coordonnee de B, x puis y ");
				double u = sc.nextDouble();
				double v = sc.nextDouble();
				Point A = new Point(a,b);
				Point B = new Point(u,v);
				Droite D = new Droite(A,B);
				System.out.println("Droite : " + D.getb() + "y = " + D.geta() + "x + " + D.getc());
			}
			else if(i == 4){
				System.out.println("vous avez choisi TestIntersectionDeuxSegments ");
				System.out.println("coordonnee de A, x puis y ");
				double a = sc.nextDouble();
				double b = sc.nextDouble();
				System.out.println("coordonnee de B, x puis y ");
				double u = sc.nextDouble();
				double v = sc.nextDouble();
				System.out.println("coordonnee de C, x puis y ");
				double x = sc.nextDouble();
				double y = sc.nextDouble();
				System.out.println("coordonnee de D, x puis y ");
				double s = sc.nextDouble();
				double t = sc.nextDouble();
				Point A = new Point(a,b);
				Point B = new Point(u,v);
				Point C = new Point(x,y);
				Point D = new Point(s,t);
				Droite D1 = new Droite(A,B);
				Droite D2 = new Droite(C,D);
				System.out.println("l'intersection des deux segment est : "+ D1.interSegments_point(D2));
			}
			else if(i == 5){
				System.out.println("vous avez choisi TestRegionIntersecteTriangle ");
				System.out.println("coordonnee de la region, xmin puis ymin puis xmax puis ymax ");
				double a = sc.nextDouble();
				double b = sc.nextDouble();
				double u = sc.nextDouble();
				double v = sc.nextDouble();
				System.out.println("coordonnee du triangle ");
				System.out.println("coordonnee de A, x puis y ");
				double x = sc.nextDouble();
				double y = sc.nextDouble();
				System.out.println("coordonnee de B, x puis y ");
				double s = sc.nextDouble();
				double t = sc.nextDouble();
				System.out.println("coordonnee de C, x puis y ");
				double p = sc.nextDouble();
				double r = sc.nextDouble();
				Point A = new Point(x,y);
				Point B = new Point(s,t);
				Point C = new Point(p,r);
				Triangle T = new Triangle (A,B,C);
				Region R = new Region (a, b ,u ,v);
				System.out.println("le triangle intersecte la region : "+ R.triangle_present(T));
			}
			else if(i == 6){
				System.out.println("vous avez choisi TestTriangleContientPoint ");
				System.out.println("coordonnee du Point, x puis y ");
				double a = sc.nextDouble();
				double b = sc.nextDouble();
				System.out.println("coordonnee de A, x puis y ");
				double x = sc.nextDouble();
				double y = sc.nextDouble();
				System.out.println("coordonnee de B, x puis y ");
				double s = sc.nextDouble();
				double t = sc.nextDouble();
				System.out.println("coordonnee de C, x puis y ");
				double p = sc.nextDouble();
				double r = sc.nextDouble();
				Point A = new Point(x,y);
				Point B = new Point(s,t);
				Point C = new Point(p,r);
				Point D = new Point(a,b);
				Triangle T = new Triangle (A,B,C);
				System.out.println("le triangle contient le point : "+ T.contient_Point(D));
			}
			else if(i == 7){
				System.out.println("vous avez choisi RecherchePointQT"); 
				System.out.println("coordonnee du Point, x puis y ");
				double a = sc.nextDouble();
				double b = sc.nextDouble();
				Point D = new Point(a,b);
				System.out.println("nombre de triangle max par region : ");
				int n = sc.nextInt();
				ArrayList<Triangle> triangles = new ArrayList<Triangle>();
				ArrayList<Polygone> nosPoly = new ArrayList<Polygone>();
				nosPoly = lirePoly();
				for ( Polygone P : nosPoly){
					P.triangulation_totale();
					for (Triangle T : P.getT()){
						triangles.add(T);
					}
				}
				Region R = new Region (0.0 , 0.0 ,10.0 ,10.0);
				Quadtree Q = new Quadtree (R , n, triangles);
				Q.Contruction();
				Region RR = Q.recherche(D);
				System.out.println("le point se trouve dans la region : " + RR.getXmin() + " " + RR.getXmax() + " " + RR.getYmax() + " " + RR.getYmin());
			}
			else if(i == 8){
				System.out.println("vous avez choisi RecherchePointTriangle"); 
				System.out.println("coordonnee du Point, x puis y ");
				double a = sc.nextDouble();
				double b = sc.nextDouble();
				Point D = new Point(a,b);
				System.out.println("nombre de triangle max par region : ");
				int n = sc.nextInt();
				ArrayList<Triangle> triangles = new ArrayList<Triangle>();
				ArrayList<Polygone> nosPoly = new ArrayList<Polygone>();
				nosPoly = lirePoly();
				for ( Polygone P : nosPoly){
					P.triangulation_totale();
					for (Triangle T : P.getT()){
						triangles.add(T);
					}
				}
				Region R = new Region (0.0 , 0.0 ,10.0 ,10.0);
				Quadtree Q = new Quadtree (R , n, triangles);
				Q.Contruction();
				Region RR = Q.recherche(D);
				Triangle T = RR.triangle_du_point(D);
				System.out.println("le point se trouve dans le triangle : ");
				T.afficher();
			}
			else if(i == 9){
				System.out.println("vous avez choisi CalculePointAtterrissageBalle"); 
				System.out.println("coordonnee du Point, x puis y ");
				double a = sc.nextDouble();
				double b = sc.nextDouble();
				Point D = new Point(a,b);
				System.out.println("nombre de triangle max par region : ");
				int n = sc.nextInt();
				ArrayList<Triangle> triangles = new ArrayList<Triangle>();
				ArrayList<Polygone> nosPoly = new ArrayList<Polygone>();
				nosPoly = lirePoly();
				for ( Polygone P : nosPoly){
					P.triangulation_totale();
					for (Triangle T : P.getT()){
						triangles.add(T);
					}
				}
				Region R = new Region (0.0 , 0.0 ,10.0 ,10.0);
				Quadtree Q = new Quadtree (R , n, triangles);
				Q.Contruction();
				Point trou = lireArriveParcours(1);
				int par = lireParParcours(1);
				Balle B = new Balle(D, Q ,trou , par);
				System.out.println("Ou visez vous ? x puis y ");
				double x = sc.nextDouble();
				double y = sc.nextDouble();
				Point Cible = new Point (x,y);
				System.out.println("Vous atterissez ici :");
				B.calcul_point_aterisage(Cible).Affiche();
			}
			else if(i == 10){
				System.out.println("vous avez choisi CalculePointDepart"); 
				System.out.println("coordonnee du Point, x puis y ");
				double a = sc.nextDouble();
				double b = sc.nextDouble();
				Point D = new Point(a,b);
				System.out.println("nombre de triangle max par region : ");
				int n = sc.nextInt();
				ArrayList<Triangle> triangles = new ArrayList<Triangle>();
				ArrayList<Polygone> nosPoly = new ArrayList<Polygone>();
				nosPoly = lirePoly();
				for ( Polygone P : nosPoly){
					P.triangulation_totale();
					for (Triangle T : P.getT()){
						triangles.add(T);
					}
				}
				Region R = new Region (0.0 , 0.0 ,10.0 ,10.0);
				Quadtree Q = new Quadtree (R , n, triangles);
				Q.Contruction();
				Point trou = lireArriveParcours(1);
				int par = lireParParcours(1);
				Balle B = new Balle(D, Q ,trou , par);
				System.out.println("Ou visez vous ? x puis y ");
				double x = sc.nextDouble();
				double y = sc.nextDouble();
				Point Cible = new Point (x,y);
				Point atte = B.calcul_point_aterisage(Cible);
				B.calcul_point_depart(atte);
				System.out.println("Vous partez  d'ici :");
				B.getP().Affiche();
			}
			else if(i == 11){
				triangle = true; //on applique le triangularisation
				panneau.repaint(); //on l'affiche graphiquement
				
				System.out.println("nombre de triangle max par region : ");
				int n = sc.nextInt();
				
				//Création de deux liste pour stocker les triangles et les polygones
				ArrayList<Triangle> triangles = new ArrayList<Triangle>();
				ArrayList<Polygone> nosPoly = new ArrayList<Polygone>();
				
				nosPoly = lirePoly(); //on récupère la liste de nos polygones
				
				//On fait la triangularisation
				for ( Polygone P : nosPoly){
					P.triangulation_totale();
					for (Triangle T : P.getT()){
						triangles.add(T);
					}
				}
				Region R = new Region (0.0 , 0.0 ,10.0 ,10.0);
				Quadtree Q = new Quadtree (R , n, triangles);
				Q.Contruction();
				
				//lire le numero du parcours	
				int np;
				if (nb_parcours > 1){
				System.out.println("Il y a " + nb_parcours + " parcours. Lequel voulez vous faire ? (1 : premier parcours)");
					np = sc.nextInt();
				} else {
					np = 1;
				}
				
	            //on lit les élément pour creer la balle
				Point P = lireDepartParcours(np);
				Point trou = lireArriveParcours(np);
				int par = lireParParcours(np);
				Balle B = new Balle(P, Q ,trou , par);
				
                //on verifie si la balle est dans le trou
				while (!B.balle_dans_trou()){
					System.out.println("Vous etes ici : ");
					B.getP().Affiche();
					System.out.println("Point de votre cible, x puis y :");
					double x = sc.nextDouble();
					double y = sc.nextDouble();
					Point Cible = new Point (x,y);
					Point atte = B.calcul_point_aterisage(Cible);
					B.calcul_point_depart(atte);
				}
				//on affiche le score
				System.out.println("Vous avez un score de " + B.calcul_score());
			}
			
			
			System.out.println();	
			System.out.println();	
			System.out.println();	
			System.out.println();	
			System.out.println();	
			System.out.println();	
			System.out.println("taper 1 pour tester TriangulationTerrain");	
			System.out.println("taper 2 pour tester ConstructionQT");
			System.out.println("taper 3 pour tester CalculCoefficientsDroite");
			System.out.println("taper 4 pour tester TestIntersectionDeuxSegments");
			System.out.println("taper 5 pour tester TestRegionIntersecteTriangle");
			System.out.println("taper 6 pour tester TestTriangleContientPoint");
			System.out.println("taper 7 pour tester RecherchePointQT");
			System.out.println("taper 8 pour tester RecherchePointTriangle");
			System.out.println("taper 9 pour tester CalculePointAtterrissageBalle");
			System.out.println("taper 10 pour tester CalculePointDepartBalle");
			System.out.println("taper 11 pour jouer");
			System.out.println("Votre choix : ");
			i = sc.nextInt();
			
			
		}
		
	}

}
