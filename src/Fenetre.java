import javax.swing.JFrame;
 
public class Fenetre extends JFrame {
	
	private Panneau pan = new Panneau();
	
	public Fenetre()
	{
		this.setTitle("Bataille Navale");
		this.setSize(256,278);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         
		this.setAlwaysOnTop(true);
		this.setResizable(false); 
		this.setContentPane(pan);   
		this.setVisible(true);
	}
	public void setGrid(int[][] grille)
	{
		pan.setGrid(grille);
		pan.repaint(); 
	}
}