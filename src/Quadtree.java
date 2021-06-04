import java.util.ArrayList;

public class Quadtree {
	private Region R;
	private Quadtree Q1;
	private Quadtree Q2;
	private Quadtree Q3;
	private Quadtree Q4;
	int n; //nombre de tringle max par region
	private ArrayList<Triangle> triangles; //liste de tous les triangles de la triangulation
	
	public Quadtree (Region R, int n, ArrayList<Triangle> triangles){
		this.R = R;
		this.n = n;
		this.triangles = triangles;
	}
	
	public void setQ1(Quadtree Q){
		Q1 = Q;
	}
	
	public void setQ2(Quadtree Q){
		Q2 = Q;
	}
	
	public void setQ3(Quadtree Q){
		Q3 = Q;
	}
	
	public void setQ4(Quadtree Q){
		Q4 = Q;
	}
	
	public Region getR(){
		return R;
	}
	
	public Quadtree getQ1(){
		return Q1;
	}
	
	public Quadtree getQ2(){
		return Q2;
	}
	
	public Quadtree getQ3(){
		return Q3;
	}
	
	public Quadtree getQ4(){
		return Q4;
	}
	
	
	//Fonction qui teste si un region contient moins de nb triangles afin de contruire le quadtree
	public Quadtree arbre(double Xmin, double Ymin, double Xmax, double Ymax){
		
		Region R = new Region(Xmin, Ymin, Xmax, Ymax);
		R.calcul_triangle(triangles);
		if(R.getNB() <= n){ //si moins de n triangles alors on crée un quadtree avec la region en laissant ses fils null et on le renvoit
			R.affiche();
			System.out.println("___________");
			Quadtree Q = new Quadtree(R , n, triangles);
			return Q;
		}
		else{
			Quadtree Q = new Quadtree(R, n, triangles); //si plus de n triangles on redecoupe la region qu'on place dans ses quadtree fils
			Q.setQ1(arbre(Xmin, (Ymin +Ymax)/2, (Xmax + Xmin)/2, Ymax));
			Q.setQ2(arbre((Xmax + Xmin)/2, (Ymin +Ymax)/2, Xmax, Ymax));
			Q.setQ3(arbre((Xmax + Xmin)/2, Ymin, Xmax, (Ymin +Ymax)/2));
			Q.setQ4(arbre(Xmin, Ymin, (Xmax + Xmin)/2, (Ymin +Ymax)/2));
			return Q;
		}
	}
	
	
	//Fonction de depart de la construction du quadtree
	public void Contruction(){
		R.calcul_triangle(triangles);
		System.out.println(R.getNB());
		if(R.getNB() > n){
			double Xmin = R.getXmin();
			double Ymin = R.getYmin();
			double Xmax = R.getXmax();
			
			double Ymax = R.getYmax();
			setQ1(arbre(Xmin, Ymax/2, Xmax/2, Ymax));
			setQ2(arbre(Xmax/2, Ymax/2, Xmax, Ymax));
			setQ3(arbre(Xmax/2, Ymin, Xmax, Ymax/2));
			setQ4(arbre(Xmin, Ymin, Xmax/2, Ymax/2));
		}
	}
	
	
	//Fonction qui recherche la region qui contient le point
	public Region recherche_ds_arbre(Point P, Quadtree Q){
		if (Q.getQ1() == null && Q.getQ2() == null && Q.getQ3() == null && Q.getQ4() == null){ //si tous les fils sont null on est arrive a une feuille on renvoie alors la region
			return Q.getR();
		}
		else { //sinon on se deplace dans le quadtree en fonction des coordonnées du point
			Region R = Q.getR();
			double x = P.getX();
			double y = P.getY();
			double Xmin = R.getXmin();
			double Ymin = R.getYmin();
			double Xmax = R.getXmax();
			double Ymax = R.getYmax();
			if (x <= (Xmax + Xmin)/2 && y <= (Ymin +Ymax)/2){
				return recherche_ds_arbre(P, Q.getQ4());
			}
			else if((x <= (Xmax + Xmin)/2 && y > (Ymin +Ymax)/2)){
				return recherche_ds_arbre(P, Q.getQ1());
			}
			else if((x > (Xmax + Xmin)/2 && y <= (Ymin +Ymax)/2)){
				return recherche_ds_arbre(P, Q.getQ3());
			}
			else{
				return recherche_ds_arbre(P, Q.getQ2());
			}
		}
	}
	
	//fonction qui recherche la region qui contient le point
	public Region recherche(Point P){
		if (Q1 == null && Q2 == null && Q3 == null && Q4 == null){ //tous les fils sont null on est sur une feuillle on renvoit la region
			return R;
		}
		else { //sinon on se deplace dans l'arbre en fontion des coordonnees du point
			double x = P.getX();
			double y = P.getY();
			double Xmax = R.getXmax();
			double Ymax = R.getYmax();
			if (x <= Xmax/2 && y <= Ymax/2){
				return recherche_ds_arbre(P, Q4);
			}
			else if((x <= Xmax/2 && y > Ymax/2)){
				return recherche_ds_arbre(P, Q1);
			}
			else if((x > Xmax/2 && y <= Ymax/2)){
				return recherche_ds_arbre(P, Q3);
			}
			else{
				return recherche_ds_arbre(P, Q2);
			}
		}
	}
}


