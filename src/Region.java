import java.util.ArrayList;

public class Region {
	private double Xmin;
	private double Xmax;
	private double Ymin;
	private double Ymax;
	private Point A;
	private Point B;
	private Point C;
	private Point D;
	private ArrayList<Droite> droite = new ArrayList<Droite>();
	private int nb_triangle;
	private ArrayList<Triangle> triangles = new ArrayList<Triangle>(); //liste des triangle coupant la region
	public Region(double Xmin, double Ymin, double Xmax, double Ymax){
		this.Xmin = Xmin;
		this.Ymin = Ymin;
		this.Xmax = Xmax;
		this.Ymax = Ymax;
		A = new Point(Xmin, Ymax);
		B = new Point(Xmin, Ymin);
		C = new Point(Xmax, Ymin);
		D = new Point(Xmax, Ymax);
		droite.add(new Droite(A,B));
		droite.add(new Droite(B,C));
		droite.add(new Droite(C,D));
		droite.add(new Droite(D,A));
	}
	
	public double getXmin(){
		return Xmin;
	}
	
	public double getYmin(){
		return Ymin;
	}
	
	public double getXmax(){
		return Xmax;
	}
	
	public double getYmax(){
		return Ymax;
	}
	
	
	//Fonction qui cherche les triangles qui intersecte la region
	public void calcul_triangle( ArrayList<Triangle> triangles){
		for (Triangle T : triangles){
			if (triangle_present(T)){
				this.triangles.add(T);
				nb_triangle++;
			}
		}
	}
	
	//Fonction qui teste si un triangle en entièrement présent dans la region
	public boolean triangle_present_entier(Triangle T){
		double x0 = Xmin;
		double y0 = Ymin;
		double a = Xmax - x0;
		double b = 0;
		double c = 0;
		double d = Ymax - y0;
		double det = a*d - b*c;
		double xM = -1.0 , xm; // xM coordonnes de M dans repre (A,AB,AC), xm coordonnes de M dans repre d'origine
		double yM = -1.0 , ym; // yM coordonnes de M dans repre (A,AB,AC), ym coordonnes de M dans repre d'origine
		boolean contient = true; //triangle entierement dans la regions
		ArrayList<Point> points = new ArrayList<Point>(); //les point du triangle
		Point A = T.getA();
		Point B = T.getB();
		Point C = T.getC();
		points.add(A);
		points.add(B);
		points.add(C);
		
		for (Point M : points){
			xm = M.getX();
			ym = M.getY();
			xM = (c*(y0 - ym) + d*(xm - x0))/det;
			yM = (a*(ym - y0) + b*(x0 - xm))/det;
			if ( !((xM + yM) <= 2 && (xM + yM) >= 0 && xM <= 1 && xM >= 0 && yM <= 1 && yM >= 0)){ //le point est dans la regions
				contient = false;
				}
			}
		return contient;
	}
	
	//Fonciton qui test si un triangle intersecte la region
	public boolean triangle_present(Triangle T){
		boolean present = false;
		Point A = T.getA();
		Point B = T.getB();
		Point C = T.getC();
		ArrayList<Droite> droite_triangle = new ArrayList<Droite>(); //creation des droites du triangles
		droite_triangle.add(new Droite(A,B));
		droite_triangle.add(new Droite(B,C));
		droite_triangle.add(new Droite(C,A));
		for (Droite D : droite){ //test si les droites de la regions intersectes celle du trianles
			for (Droite DD : droite_triangle){
				if(D.interSegments_point(DD)){
					present = true;
				}
			}
		}
		if (!present){
			present = triangle_present_entier(T);
		}
		return present;
	}
	
	
	public int getNB(){
		return nb_triangle;
	}
	
	public boolean contient_tirangle(Triangle T){
		return triangles.contains(T);
	}
	
	//Fonction qui cherche le triangle de la region qui contient le point P
	public Triangle triangle_du_point(Point P){
		boolean trouve = false;
		int i = 0;
		Triangle T = triangles.get(0);
		while (!trouve){ //pour tous triangles test si celui ci contient le point
			T = triangles.get(i);
			trouve = T.contient_Point(P);
			i++;
		}
		return T;
	}
	
	public void affiche(){
		System.out.println(" Region " + " Xmin : " + Xmin + " Xmax : " + Xmax + " Ymin : " + Ymin + " Ymax : " + Ymax);
	}
	
}
