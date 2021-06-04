import javax.swing.JFrame;

/**
 * @brief Classe engendrant une fetre principale d'application associe  un panneau
 */
public class Fenetre extends JFrame {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @brief Constructeur de la fentre
	 * @param pan le panel a afficher
	 */
	public Fenetre(Affichage pan) {
		// titrage de la fentre
		super("Golf (beta)");
		
		// rglage des paramtres
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);

		// ajout de la grille
		getContentPane().add(pan);
		pack();
		
		// affichage centr
		setVisible(true);
		setLocationRelativeTo(null);
	}
}