import java.util.*;

public class Polygone {
	private ArrayList<Point> poly = new ArrayList<Point>();
	private ArrayList<Point> polytmp = new ArrayList<Point>();
	private ArrayList<Point> savepoint = new ArrayList<Point>(); //stocker les point du polygone sans les triangles
	private ArrayList<Point> pointsupr = new ArrayList<Point>(); //liste des point du polygone deja elimin utile pour interpropre pair
	private ArrayList<Droite> segment = new ArrayList<Droite>();
	private ArrayList<Triangle> triangle = new ArrayList<Triangle>();
	ArrayList<Droite> D = new ArrayList<Droite>();
	private String couleur;
	
	public Polygone(ArrayList<Point> poly, String coul){
		this.poly = poly;
		this.couleur = coul;
		for (Point P : poly){
			polytmp.add(P.clone());
		}
		
	}
	
	public double max(double a, double b){
		if (a > b){
			return a;
		} else {
			return b;
		}
	}
	
	public double min(double a, double b){
		if (a < b){
			return a;
		} else {
			return b;
		}
	}
	
	public ArrayList<Triangle> getT(){
		return triangle;
	}
	
	//Fonction qui calcule le nombre d'intersection propre par rapport aux sommets du polygone
	public int interpropre_sommet(Droite D){ 
		/*soit le passage de la droite par un sommet A du polygone, de sorte que les deux sommets voisins de A 
		 * sur le polygone soient situs dans des demi-plans diffrents de la droite
		 */
		int nb_inter_propre = 0;
		for (int i = 0; i < polytmp.size(); i++) { //test pour tous les points du poly
			if (D.appDemi(polytmp.get(i))){ //si le point appartient à la demi droite
				if (i == 0){ //si le point est le premier de la liste voisin de droite est n-1
					if (D.appartient(polytmp.get(i+1)) == -(D.appartient(polytmp.get(polytmp.size()-1))) || (D.appartient(polytmp.get(i+1))*(D.appartient(polytmp.get(polytmp.size()-1))) == 0)){ //si le voisin de droite et gauche sont dans demis plan différents
						nb_inter_propre++;
					}
				}
				else if (i==polytmp.size()-1){ //si le point est le dernier de la liste voisin de gauche est 0
					if (D.appartient(polytmp.get(i-1)) == -(D.appartient(polytmp.get(0))) || (D.appartient(polytmp.get(i-1))*(D.appartient(polytmp.get(0))) == 0) ){ //si le voisin de droite et gauche sont dans demis plan différents
						nb_inter_propre++;
						
					}
				}
				else{
					if (D.appartient(polytmp.get(i-1)) == -(D.appartient(polytmp.get(i+1))) || (D.appartient(polytmp.get(i-1))*(D.appartient(polytmp.get(i+1))) == 0) ){ //si le voisin de droite et gauche sont dans demis plan différents
						nb_inter_propre++;
					}
				}
			}
		}
		return nb_inter_propre;
	}
	
	//Fonction qui calcule le nombre d'intersection propre par rapport a 2 sommet cons�cutif du polygone
	public int interpropre_segment(Droite D){ 
		/* si le passage de la demi droite par deux sommets consécutifs A, B du polygone 
		 * (la droite contient alors le segment ferm [AB]), de sorte que l’autre sommet voisin de A sur le polygone 
		 * (cad celui qui n’est pas B) et l’autre sommet voisin de B sur le polygone (cad celui qui n’est pas A) soient situes 
		 * dans des demi-plans diffrents de la droite.*/
		int n = polytmp.size();
		int nb_inter_propre = 0;
		for (int i = 0; i < polytmp.size(); i++) { //test pour tous les points du poly
			if (i == 0){
				if (D.appDemi(polytmp.get(i))){ // 1er de la liste appartient a la droite
					if (D.appDemi(polytmp.get(n-1))){ // voisin de gauche conscutif app à la droite
						if (D.appartient(polytmp.get(n-2)) == - D.appartient(polytmp.get(i+1)) || (D.appartient(polytmp.get(n-2))*(D.appartient(polytmp.get(i+1))) == 0)){//voisin de gauche du voisin de gauche ds demi plan diff du voisin de droite de i
							nb_inter_propre++;
						}
					}
				}
			} else if (i == 1){ // si deuxime point de la liste
				if (D.appDemi(polytmp.get(i))){ //test si app à la droite
					if (D.appDemi(polytmp.get(i-1))){ // voisin de gauche
						if (D.appartient(polytmp.get(n-1)) == - D.appartient(polytmp.get(i+1)) || (D.appartient(polytmp.get(n-1))*(D.appartient(polytmp.get(i+1))) == 0)){//voisin de gauche du voisin de gauche ds demi plan diff du voisin de droite de i
							nb_inter_propre++;
						}
					}
				}
			} else if (i == n-1){ //dernier de la liste
				if (D.appDemi(polytmp.get(i))){ //test si app a la droite
					if (D.appDemi(polytmp.get(i-1))){ // voisin de gauche
						if (D.appartient(polytmp.get(i-2)) == - D.appartient(polytmp.get(0))|| (D.appartient(polytmp.get(i-2))*(D.appartient(polytmp.get(0))) == 0) ){//voisin de gauche du voisin de gauche ds demi plan diff du voisin de droite de i
							nb_inter_propre++;
						}
					}
				}
			} else { // tous les autres cas
				if (D.appDemi(polytmp.get(i))){ //test si app a la droite
					if (D.appDemi(polytmp.get(i-1))){ // voisin de gauche
						if (D.appartient(polytmp.get(i-2)) == - D.appartient(polytmp.get(i+1)) || (D.appartient(polytmp.get(i-2))*(D.appartient(polytmp.get(i+1))) == 0)){//voisin de gauche du voisin de gauche ds demi plan diff du voisin de droite de i
							nb_inter_propre++;
						}
					}
				}
			}
		}
		return nb_inter_propre;
	}
	
	//soit l’intersection de la droite avec un cote du polygone dans un point interne au cote
	public int interpropre_cote(Droite D){
		int n = polytmp.size();
		int nb_inter_propre = 0;
		Point P,A,B;
		for (int i = 0; i <= n-1; i++){
			if (i == 0){ //cas spcial du 1er de liste mais pas besoin du dernier
				A = polytmp.get(0);
				B = polytmp.get(n-1);
				Droite D1 = new Droite(A,B);
				if (D.interDemi(D1) == true){
					P = D.point_inter(D1);
					if (A.getX() == B.getX() && P.getY() < max(A.getY(),B.getY()) && P.getY() > min(A.getY(),B.getY())){ // cas d'alignement des x
						nb_inter_propre++;
					}
					//on prends une demi-droite
					else if ((P.getX() < max(A.getX(),B.getX()) && P.getX() > min(A.getX(),B.getX()))){ //verifie que le pt d'intersection est bien dans le segment
						nb_inter_propre++;
					}
				}
			} else {
				A = polytmp.get(i);
				B = polytmp.get(i-1);
				Droite D1 = new Droite(A, B);
				if (D.interDemi(D1) == true){
					P = D.point_inter(D1);
					if (A.getX() == B.getX() && P.getY() < max(A.getY(),B.getY()) && P.getY() > min(A.getY(),B.getY())){ // cas d'alignement des x
						nb_inter_propre++;
					}
					//on prends une demi-droite
					else if ((P.getX() < max(A.getX(),B.getX()) && P.getX() > min(A.getX(),B.getX()))){ //verifie que le pt d'intersection est bien dans le segment
						nb_inter_propre++;
					}
				} 
			}
		}
		return nb_inter_propre;
	}
	
	//Fonction qui calcule le totale d'interpropre de la droite
	public int interpropre(Droite D){
		return interpropre_sommet(D) + interpropre_segment(D) + interpropre_cote(D);
	}
	
	public double abs(double x){
		if (x >= 0)
			return x;
		else
			return -x;
	}
	
	//Fonciton qui test si un triangle ne contient aucun autre points du polgyones
	public boolean triangle_vide(Point A, Point B, Point C) {
		
		double x0 = A.getX();
		double y0 = A.getY();
		double a = B.getX() - x0;
		double b = B.getY() - y0;
		double c = C.getX() - x0;
		double d = C.getY() - y0;
		double det = a*d - b*c;
		Droite D = new Droite(A,C);
		double xM = -1.0 , xm; // xM coordonnes de M dans repre (A,AB,AC), xm coordonnes de M dans repre d'origine
		double yM = -1.0 , ym; // yM coordonnes de M dans repre (A,AB,AC), ym coordonnes de M dans repre d'origine
		boolean vide = true; //pas de point dans le triangle
		for (Point M : polytmp){
			xm = M.getX();
			ym = M.getY();
			if (M != A && M != B && M != C){
				xM = (c*(y0 - ym) + d*(xm - x0))/det;
				yM = (a*(ym - y0) + b*(x0 - xm))/det;
			}
			if ( (xM + yM) <= 1 && (xM + yM) >= 0 && xM <= 1 && xM >= 0 && yM <= 1 && yM >= 0){ //le point est dans le triangle ABC
				vide = false;
			}
		}
		if (D.appartient(B) == 0.0){
			vide = false;
		}
		return vide;
	}
	
	//Fonction qui cr�e un triangle 
	public Triangle creation_triangle(int i){
		int iA = i; //indice des points qui forme le triangle
		int iB = i+1;
		int iC = i+2;
		int n=polytmp.size();
		if (i == (n-2)){
			iC = 0;
		} else if (i == n-1){
			iB = 0;
			iC = 1;
		}
		Point A = polytmp.get(iA).clone();
		Point B = polytmp.get(iB).clone();
		Point C = polytmp.get(iC).clone();
		Triangle T = new Triangle(A, B, C, this.couleur);
		if (!(pointsupr.contains(polytmp.get(iB)))){
			pointsupr.add(polytmp.get(iB));
		}
		return T;
	}
	
	//Fonction qui triangulise le polygone
	public void triangulation(){
	/*1. Si l’un des autres points du polygone est à l’intrieur du triangle ABC ou sur
	le segment [AC], alors retourner Faux.
	2. Construire un segment AD tel que C soit sur le segment AD et D soit à
	l’extrieur du polygone. donc utiliser des demi-droites !!!!
	3. Si le nombre d’intersections propres entre le segment ouvert et le poly-
	gone est impair, alors retourner Vrai ; sinon retourner Faux.*/
		int k ;
		int j = 0;
		D = new ArrayList<Droite>();
		//faire des droites avec ts les pts
		for (int i=0;i<polytmp.size()-2;i++){ //jusqu'a n-2 car aprs reviens au debut
			if (triangle_vide(polytmp.get(i),polytmp.get(i+1),polytmp.get(i+2))){
				D.add(new Droite(polytmp.get(i),polytmp.get(i+2)));
			}
		}
		if (triangle_vide(polytmp.get(polytmp.size() - 2),polytmp.get(polytmp.size()-1),polytmp.get(0))){ // avant derniere droite
			D.add(new Droite(polytmp.get(polytmp.size() - 2), polytmp.get(0)));
		}
		if (triangle_vide(polytmp.get(polytmp.size() - 1),polytmp.get(0),polytmp.get(1))){ // derniere droite
			D.add(new Droite(polytmp.get(polytmp.size() - 1), polytmp.get(1)));
		}
		for (Droite d : D){
			// on verifie que c'est impaire,et on verifie qu'elle ne coupe pas de segments dej trac
			if((interpropre(d) % 2) == 1){ 
				if (segment.size() == 0){ //si c'est le premier segment, on l'ajoute
					j = 0;
					while (!(polytmp.get(j).equals(d.getA())) && j<polytmp.size()){
						j++;
					}
					k = j;
					Triangle T = creation_triangle(k);
					if(!(triangle.contains(T))){
						triangle.add(T); //Triangle form ajouter  la liste
						segment.add(d);
						}
				} else {
					boolean inter = false;
					for (Droite s : segment){ //parcours les segments dja tracs
						if ((d.interSegments(s))){
							inter = true;
						}
					}
					if(inter == false){
						j = 0;
						while (j<polytmp.size() && !(polytmp.get(j).equals(d.getA())) && j<polytmp.size()){
							j++;
						}
						k = j;
						Triangle T = creation_triangle(k);
						if(!(triangle.contains(T))){
							triangle.add(T); //Triangle form ajouter  la liste
							segment.add(d);
							}
						}
					}
				}
			else{
				}
			}
		for (Point P : pointsupr){
			polytmp.remove(P); //on suprime tous les points B servant � la construction d'un triangle
		}
		pointsupr = new ArrayList<Point>();
	}
	
	//Fonction qui calcule la triangulation totale du polygones
	public void triangulation_totale(){
		if (polytmp.size() == 3) { //condition d'arret on forme un triangle
			Triangle T = creation_triangle(0);
			triangle.add(T);
		}
		else if (polytmp.size() <= 2){
			//rien on s'arrete car polynome de 4 cot donc on se stop
		}
		else{
			triangulation();
			triangulation_totale();
		}
	}
	public ArrayList<Point> getPoly() {
		return poly;
	}

	public void setPoly(ArrayList<Point> poly) {
		this.poly = poly;
	}
	
	public void affichePoly(){
		for(int i = 0; i < this.poly.size(); i++){
			this.poly.get(i).Affiche();
		}
		System.out.println(this.couleur);
	}
	
	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
}