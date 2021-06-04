import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

//Classe permettant de realiser l'interface graphique du terrain de golf

public final class Affichage extends JPanel {
	private static final long serialVersionUID = 1L;

	// Largeur et hauteur du panel graphique, 
	private int larg, haut;
	
	//Coefficient d'agrandissement du terrain
	private int coef = 70;
	
	//constructeur initiant le panneau d'affichage
	public Affichage() {
		// calcul des dimensions du panneau
		larg = Main.getLarg();
		haut = Main.getHaut();
		setPreferredSize(new Dimension(larg,haut));
	}

	//Methode pour afficher un point en fonction de ses coordonnées
	public void affichePoint(Graphics g, Point pt, Color c){
		
		int x = (int)(pt.getX()*coef); 
		int y = (int)(haut-pt.getY()*coef); //on fait bien attention a remettre les points dans le bons sens car axe inversé

		g.setColor(c);
		g.fillOval(x, y, 10, 10);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, 10, 10);
	}
	
	//Methode pour afficher un polygone en fonction de ses points
	public void affichePolygone(Graphics g, Polygone poly) {
		
		//On stocke les coordonnées des x et des y dans 2 tableaux différents
		//Initialisation des tableaux
		int[] x = new int[poly.getPoly().size()];
		int[] y = new int[poly.getPoly().size()];
		
		//on récupère les coordonnées x et y de chaque point
		for (int i = 0; i < poly.getPoly().size(); i++){
			x[i] = (int)(poly.getPoly().get(i).getX()*coef);
			y[i] = (int)(haut-poly.getPoly().get(i).getY()*coef); //On fait bien attention a remetre le terrain dans le bon sens car axe inversé
		}
		
		//on trace le polygone avec les 2 tableaux précedents.
		g.setColor(couleur(poly));
		g.fillPolygon(x, y, x.length);
		g.setColor(Color.BLACK);
		g.drawPolyline(x, y, x.length);
	}
	
	//Methode pour afficher un triangle en fonction de ses points
	public void afficheTriangle(Graphics g, Triangle tri) {
		
		//On stocke les coordonnées des x et des y dans 2 tableaux différents
		//Initialisation des tableaux
		int[] x = new int[3];
		int[] y = new int[3];
		
		//on récupère les coordonnées x et y de chaque point
		x[0] = (int)(tri.getA().getX()*70);
		y[0] = (int)(haut-tri.getA().getY()*70);
		x[1] = (int)(tri.getB().getX()*70);
		y[1] = (int)(haut-tri.getB().getY()*70);
		x[2] = (int)(tri.getC().getX()*70);
		y[2] = (int)(haut-tri.getC().getY()*70);
			
		//on trace le contour des triangles en noir
		g.setColor(Color.BLACK);
		g.drawPolygon(x, y, x.length);
	}

	
	//Fonction qui va retourner la couleur graphique du polynome en fonction de sa couleur "string"
	public Color couleur(Polygone p){
		
		//Creation des couleurs
		Color vert = new Color(0,176,80);
		Color v_clair = new Color(160,240,64);
		Color bleu = new Color(91,155,213);
		Color v_sapin = new Color(0,128,0);
		Color beige = new Color(201,187,103);
		
		//Attribution des couleurs
		switch (p.getCouleur()){
			case "V" :  return vert;
			case "C" : 	return v_clair;
			case "B" : 	return bleu;
			case "S" : 	return v_sapin;
			case "J" : 	return beige;
			default : return Color.WHITE;
		}
	}
	
	

	//Réaffiche le panneau à la demande
	//g Objet graphique permettant de dessiner dans le panneau
	public void paintComponent(Graphics g) {
		
		// affichage par défaut
		super.paintComponent(g);
		
		//cration des Arraylist
		ArrayList<Triangle> triangle = new ArrayList<Triangle>();
		ArrayList<Polygone> nosPoly = new ArrayList<Polygone>();
		
		//on recupère les polynome du fichier
		nosPoly = Main.lirePoly();
		
		//On parcours les polynome et on les affiche
		for(int i = 0; i < nosPoly.size() ; i++) { // pour chaque polygone
			affichePolygone(g, nosPoly.get(i)); //on affiche le poly
		}
		
		//On affiche les arrivees et depart de chaque parcours
		for(int i = 1; i <= Main.getNb_parcours(); i++){
			affichePoint(g, Main.lireArriveParcours(i), Color.red);
			affichePoint(g, Main.lireDepartParcours(i), Color.yellow);
		}
		
		//on fait la triangularisation pour chaque polynome
		for(int i = 0; i < nosPoly.size() ; i++) {
			nosPoly.get(i).triangulation_totale();
			for (int j = 0; j < nosPoly.get(i).getT().size(); j++){
				triangle.add(nosPoly.get(i).getT().get(j));
			}
		}
		
		//on affiche la triangularisation
		if (Main.getTriangle()){
			for(int i = 0; i < nosPoly.size() ; i++) {
				nosPoly.get(i).triangulation_totale();
				for (int j = 0; j < nosPoly.get(i).getT().size(); j++){
					triangle.add(nosPoly.get(i).getT().get(j));
					afficheTriangle(g,nosPoly.get(i).getT().get(j));
				}
			}
			
		}

	}
	
}