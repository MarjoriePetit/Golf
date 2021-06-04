import java.util.ArrayList;
import java.util.Random;

public class Balle {
	private Point P;
	private Quadtree Q;
	private Point trou;
	private int score, par;
	private ArrayList<Double> b = new ArrayList<Double>();
	private ArrayList<Double> a = new ArrayList<Double>();
	private Random rand = new Random();
	
	public Balle (Point P, Quadtree Q, Point trou, int par){
		this.P = P;
		this.par = par;
		score = 0;
		this.Q = Q;
		b.add(1.0);
		b.add(0.99);
		b.add(0.98);
		b.add(0.97);
		b.add(0.96);
		b.add(0.95);
		b.add(0.94);
		b.add(0.93);
		b.add(0.92);
		b.add(0.91);
		b.add(0.90);
		b.add(0.6);
		b.add(1.01);
		b.add(1.02);
		b.add(1.03);
		b.add(1.04);
		b.add(1.05);
		b.add(1.06);
		b.add(1.07);
		b.add(1.08);
		b.add(1.09);
		b.add(1.1);
		b.add(1.4);
		for (double i = 1; i <= 10 ; i++){
			a.add(i);
			a.add(-i);
		}
		a.add(0.0);
		a.add(40.0);
		a.add(-40.0);
		
	}
	
	public double abs(double x){
		if (x<0)
			return -x;
		else
			return x;
	}

	//Fonction qui calcul le point d'atterrissage de la balle
	public Point calcul_point_aterisage(Point Cible){
		Point atte;
		boolean sable =false , green = false;
		double d, alpha, x, y;
		int i;
		Region R = Q.recherche(Cible); //recherche de la region ou se trouve notre point
		Triangle T = R.triangle_du_point(Cible); //recherche du triangle qui contient le point dans la region
		if(T.getCoul() == "J"){ //on est dans du sable
			sable = true;
		}
		else if (T.getCoul() == "C"){ //on est dans le green
			green = true;
		}
		d = abs((Cible.getX() - P.getX())) * abs((Cible.getX() - P.getX()));
		d += abs((Cible.getY() - P.getY())) * abs((Cible.getY() - P.getY())); //calcul de la distance à parcourir
		d = Math.sqrt(d);
		alpha = Math.atan((Cible.getY() - P.getY()) / (Cible.getX() - P.getX())); //calcul de l'angle a convertir temporairement en degre
		alpha = (180 * alpha) / Math.PI;
		if ((Cible.getX() - P.getX()) < 0 || ((Cible.getX() - P.getX()) <0 && (Cible.getY() - P.getY()) <0)){
			alpha = alpha +180;
		}
		else if ((Cible.getX() - P.getX()) >= 0 && (Cible.getY() - P.getY()) <0){
			alpha += 360;
		}
		if(green && d <= 0.1){ //si on est a 1 metre du trou on atterrit dedans
			atte = trou.clone();
		}
		i = rand.nextInt(22); //gestion de l'aleatoire
		d = d*b.get(i); 
		if (sable){ //si sable distance divise par deux
			d = d/2;
		}
		i = rand.nextInt(22);
		if ( a.get(i) > 10 || a.get(i) < -10){ 
			System.out.println("Vous avez beaucoup devie");
		}
		else{
			System.out.println("Vous avez peu devie");
		}
		alpha = alpha + a.get(i); //en fontion de l'angle on calcul les nouvelles coordonnées differement 
		if (alpha >= 0 && alpha < 90){
			x = Math.cos(alpha) * d + P.getX();
			y = Math.sin(alpha) * d + P.getY();
		}
		else if (alpha >= 90 && alpha < 180){
			x = -Math.cos(alpha) * d + P.getX();
			y = Math.sin(alpha) * d + P.getY();
		}
		else if (alpha >= 180 && alpha < 270){
			x = -Math.cos(alpha) * d + P.getX();
			y = -Math.sin(alpha) * d + P.getY();
		}
		else{
			x = Math.cos(alpha) * d + P.getX();
			y = -Math.sin(alpha) * d + P.getY();
		}
		if(x > 10){ //si on sort du plateau
			x = 10;
		}
		else if (x <0){
			x = 0;
		}
		if(y > 10){
			y = 10;
		}
		else if (y <0){
			y = 0;
		}
		atte = new Point(x,y);
		return atte;
	}
	
	
	//fonction qui calcul d'ou on repart
	public void calcul_point_depart(Point atte){
		atte.Affiche();
		Region RR = Q.recherche(atte); //recherche de la region ou se trouve notre point
		Triangle T = RR.triangle_du_point(atte); //recherche du triangle qui contient le point
		System.out.println("Vous atterissez ici : ");
		atte.Affiche();
		if (T.getCoul().equals("S")){ //si on atterrit dans les sapins
				System.out.println("Vous atterissez dans la foret, coup non valide, +1 de penalite");
				score +=2;
		}
		else if (T.getCoul().equals("B")){ //si on atterrit dans l'eau
			System.out.println("Vous atterissez dans l'eau, coup non valide, +1 de penalite");
			score +=2;
		}
		else if (T.getCoul().equals("J")){ //si on atterrit dans le sable
			System.out.println("Vous atterissez dans le sable, complique d'en sortir, distance reduite au prochain coup");
			score +=1;
			this.P = new Point(atte.getX(),atte.getY());
		}
		else{ //sinon lancée normal
			System.out.println("lancez correct");
			score +=1;
			this.P = new Point(atte.getX(),atte.getY());
		}
	}
	
	public int calcul_score(){
		return score - par;
	}
	
	
	public Point getP(){
		return P;
	}
	
	public boolean balle_dans_trou(){
		return (P.equals(trou));
	}
	
}
