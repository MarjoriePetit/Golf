
public class Droite {
	private double a, b, c;
	private Point A,B;
	
	public Droite(Point P1, Point P2){
		b=P2.getX()-P1.getX();
		a=P2.getY()-P1.getY();
		c=b*P1.getY()-a*P1.getX();
		if (b == 0.0){
			a = 1.0;
			c = -P1.getX();
		}
		if (a == 0.0){
			b = 1.0;
			c = P1.getY();
		}
		A=P1;
		B=P2;
	}
	
	public Droite(double a,double b, double c){
		this.a=a;
		this.b=b;
		this.c=c;
	}
	
	public double abs(double x){
		if (x<0)
			return -x;
		else
			return x;
	}
	
	//tester l'appartenance à une demi droite
	public boolean appDemi(Point P){
		double epsi = 0.001; //marge d'eereur a cause des arrondis
		if (abs(this.b*P.getY() - (this.a*P.getX() + this.c)) <= epsi ){ //condition appartenance droite
			if (A.getX() == B.getX()){ //PrÃ©-condition : ]A,B) et cas droite en X
				if (A.getY() > B.getY() && A.getY() > P.getY()){
					return true;
				}
				else if (A.getY() < B.getY() && A.getY() < P.getY()){
					return true;
				}
			}
			else {
				if (A.getX() > B.getX() && A.getX() > P.getX()){
					return true;
				}
				else if (A.getX() < B.getX() && A.getX() < P.getX()){
					return true;
				}
			}
		}
		return false;
	}
	
	//Utilisé si on a déjà vérifié l'intersection
	public boolean appDemi2(Point P){
		//double X = this.a*x + this.c;
		//double Y = this.b*y;
		//Point PP = new Point(X,Y); 
		//if (this.a*P.getX() + this.c == this.b*P.getY()){ //condition appartenance droite
			if (A.getX() == B.getX()){ //PrÃ©-condition : ]A,B) et cas droite en X
				if (A.getY() > B.getY() && A.getY() > P.getY()){
					return true;
				}
				else if (A.getY() < B.getY() && A.getY() < P.getY()){
					return true;
				}
			}
			else {
				if (A.getX() > B.getX() && A.getX() > P.getX()){
					return true;
				}
				else if (A.getX() < B.getX() && A.getX() < P.getX()){
					return true;
				}
			}
		//}
		return false;
	}
	
	//Fonction qui test si un point appartient à une droite et sinon dans quel demi plan elle se situe
	public double appartient(Point P){
		double epsi = 0.0001;
		if (abs(this.b*P.getY() - (this.a*P.getX() + this.c)) <= epsi ){
			return 0;
		} else if ((this.b*P.getY() - (this.a*P.getX()+this.c)) < 0){
			return -1;
		} else {
			return +1;
		}
	}
	
	
	//Fonction qui tets l'intersection de deux droites
	public boolean intersection(Droite D){
		double x = 0.0;
		double y = 0.0;
		
		if (!(D.getb()/this.b == D.geta()/this.a)){
			return true;
		}
		else {
			return false;
		}
	}
	
	//Fonction qui test l'intersection d'une demi droite avec une droite
	public boolean interDemi(Droite D){
		double x = 0.0, y = 0.0;
		
		if (!(D.getb()/this.b == D.geta()/this.a)){
			if (this.b == 0.0){
				x = A.getX();
			}
			if (this.a == 0.0){
				y = A.getY();
			}
			if (D.getb() == 0.0){
				x = D.getA().getX();
			}
			if (D.geta() == 0.0){
				y = D.getA().getY();
			}
			if (this.b != 0.0 && D.getb() != 0.0){
				x = (D.getc()-((D.getb()*this.c)/this.b))/(((D.getb()*this.a)/this.b)-D.geta());
			}
			if (this.a != 0.0 && D.geta() != 0.0){
				if (D.getb() == 0){
					y = (this.a*x + this.c)/this.b;
				}
				else{
					y = (D.geta()*x+D.getc())/D.getb();
				}
			}
			Point P = new Point(x,y);
			return appDemi(P); //pour verifié que le point d'inter se situe bien sur al demi droite
		}
		else {
			//System.out.println("Les droites sont parallÃ¨les");
			return false;
		}
	}
	
	//Fonction qui renvoie le point d'intersection de deux droites
	public Point point_inter(Droite D){
		double x = 0.0, y = 0.0;
		if (!(D.getb()/this.b == D.geta()/this.a)){ //vecteur non colineraire alors intersection
			if (this.b == 0.0){
				x = A.getX();
			}
			if (this.a == 0.0){
				y = A.getY();
			}
			if (D.getb() == 0.0){ 
				x = D.getA().getX();
			}
			if (D.geta() == 0.0){
				y = D.getA().getY();
			}
			if (this.b != 0.0 && D.getb() != 0.0){ //éviter les divisions par 0
				x = (D.getc()-((D.getb()*this.c)/this.b))/(((D.getb()*this.a)/this.b)-D.geta());
			}
			if (this.a != 0.0 && D.geta() != 0.0){
				if (D.getb() == 0){
					y = (this.a*x + this.c)/this.b;
				}
				else{
					y = (D.geta()*x+D.getc())/D.getb();
				}
			}
		}
		Point P=new Point(x,y);
		return P;
	}
	
	public double max(double a, double b){
		if (a>b){
			return a;
		}else{
			return b;
		}
	}
	
	public double min(double a, double b){
		if (a<b){
			return a;
		}else{
			return b;
		}
	}
	
	//Fonction qui test l'intersecition de deux segments
	public boolean interSegments(Droite R){
		double  xA = this.A.getX();
		double  xB = this.B.getX();
		double  xC = R.getA().getX();
		double  xD = R.getB().getX();
		double  yA = this.A.getY();
		double  yB = this.B.getY();
		double  yC = R.getA().getY();
		double  yD = R.getB().getY();
		if (intersection(R)){
			Point P = point_inter(R); //on va ensuite verifier que les coordonnées du point sont bien comprises entres les points qui forment les segments
			if(xA == xB && xC == xD && P.getY() < max(yA,yB) && P.getY() > min(yA,yB) && (P.getY() < max(yC,yD) && P.getY() > min(yC,yD))){ 
				return true;
			}
			else if (xA == xB && P.getY() < max(yA,yB) && P.getY() > min(yA,yB) && (P.getX() < max(xC,xD) && P.getX() > min(xC,xD))){
				return true;
			}
			else if (xC == xD && P.getY() < max(yC,yD) && P.getY() > min(yC,yD) && (P.getX() < max(xA,xB) && P.getX() > min(xA,xB))){
				return true;
			}
			else if ((P.getX() < max(xA,xB) && P.getX() > min(xA,xB)) && (P.getX() < max(xC,xD) && P.getX() > min(xC,xD))){
				return true;
			}else{
				return false;
			}
			
		} else {
		return false;
		}
	}
	
	//Fonction identique à la précédente mais qui verifie que le point d'intersection n'est pas un des 4 points formant les segments
	public boolean interSegments_point(Droite R){
		double  xA = this.A.getX();
		double  xB = this.B.getX();
		double  xC = R.getA().getX();
		double  xD = R.getB().getX();
		double  yA = this.A.getY();
		double  yB = this.B.getY();
		double  yC = R.getA().getY();
		double  yD = R.getB().getY();
		boolean inter = false;
		if (intersection(R)){
			Point P = point_inter(R);
			if(xA == xB && xC == xD && P.getY() < max(yA,yB) && P.getY() > min(yA,yB) && (P.getY() < max(yC,yD) && P.getY() > min(yC,yD))){
				inter = true;
			}
			else if (xA == xB && P.getY() < max(yA,yB) && P.getY() > min(yA,yB) && (P.getX() < max(xC,xD) && P.getX() > min(xC,xD))){
				inter = true;
			}
			else if (xC == xD && P.getY() < max(yC,yD) && P.getY() > min(yC,yD) && (P.getX() < max(xA,xB) && P.getX() > min(xA,xB))){
				inter = true;
			}
			else if ((P.getX() < max(xA,xB) && P.getX() > min(xA,xB)) && (P.getX() < max(xC,xD) && P.getX() > min(xC,xD))){
				inter = true;
			}
			if (inter){
				if (P == this.A || P == this.B || P == R.getA() || P == R.getB() ){
					inter = false;
					if (P == this.A){
						System.out.println("L'intersection est le Point A");
					}
					else if (P == this.B){
						System.out.println("L'intersection est le Point B");
					}
					else if (P == R.getA()){
						System.out.println("L'intersection est le Point C");
					}
					else{
						System.out.println("L'intersection est le Point D");
					}
				}
			}
		}
		return inter;
	}
	
	public Point getA(){
		return A;
	}
	public Point getB(){
		return B;
	}
	
	public double geta(){
		return a;
	}
	public double getb(){
		return b;
	}
	public double getc(){
		return c;
	}
	
}
