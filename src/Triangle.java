public class Triangle {
	
	private Point A;
	private Point B;
	private Point C;
	private String couleur;
	
	public Triangle (Point A, Point B, Point C){
		this.A = A;
		this.B = B;
		this.C = C;
	}
	public Triangle (Point A, Point B, Point C, String coul){
		this.A = A;
		this.B = B;
		this.C = C;
		this.couleur = coul;
	}
	
	public void afficher(){
		System.out.print("Point A : " );
		A.Affiche();
		System.out.print("Point B : " );
		B.Affiche();
		System.out.print("Point C : " );
		C.Affiche();
	}
	
	public Point getA(){
		return A;
	}
	
	public Point getB(){
		return B;
	}
	
	public Point getC(){
		return C;
	}
	
	public String getCoul(){
		return couleur;
	}
	
	public boolean equals(Object obj){
		boolean result = false;
	    if (obj instanceof Triangle) {
	        Triangle that = (Triangle) obj;
	        result = (this.getA().equals(that.getA()) && this.getB().equals(that.getB()) && this.getC().equals(that.getC()));
	    }
	    return result;
	}
	
	//Fonction qui teste si le triangle contient le point
	public boolean contient_Point(Point P){
		double x0 = A.getX();
		double y0 = A.getY();
		double a = B.getX() - x0;
		double b = B.getY() - y0;
		double c = C.getX() - x0;
		double d = C.getY() - y0;
		double det = a*d - b*c;
		double xP = -1.0 , xp; // xM coordonnes de M dans repre (A,AB,AC), xm coordonnes de M dans repe d'origine
		double yP = -1.0 , yp; // yM coordonnes de M dans repre (A,AB,AC), ym coordonnes de M dans repre d'origine
		boolean contient = false; //triangle pas dans la region
		xp = P.getX();
		yp = P.getY();
		xP = (c*(y0 - yp) + d*(xp - x0))/det;
		yP = (a*(yp - y0) + b*(x0 - xp))/det;
		if ( (xP + yP) <= 1 && (xP + yP) >= 0 && xP <= 1 && xP >= 0 && yP <= 1 && yP >= 0){ //le point est dans la regions
			contient = true;
			}
		return contient;
	}
}
