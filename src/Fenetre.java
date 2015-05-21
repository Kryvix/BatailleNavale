import javax.swing.JFrame;
 
public class Fenetre extends JFrame {
	
	private Panneau pan = new Panneau();
	private Panneau2 pan2 = new Panneau2();
	private boolean IA;
	
	public Fenetre(String nom, boolean IA)
	{
		this.IA = IA;
		this.setTitle(nom);
		this.setSize(256,278);
		if(IA)
			this.setLocation(0, 0);
		else
			this.setLocation(300, 0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         
		this.setAlwaysOnTop(true);
		this.setResizable(false); 
		if(IA)
			this.setContentPane(pan);
		else
			this.setContentPane(pan2);
		this.setVisible(true);
	}
	public void setGrid(int[][] grille)
	{
		if(IA)
		{
			pan.setGrid(grille);
			pan.repaint(); 
		}
		else
		{
			pan2.setGrid(grille);
			pan2.repaint(); 
		}
	}
}