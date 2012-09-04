package actions;
/*
 * @author lmichale, shilpar, swatigupta, tgavankar
 * @version 1.0
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class MyPopupAction implements ActionListener{


	String title;
	String text;
	
	/*
	 * class constructor
	 * 
	 * @param title the title of the popup
	 * @param text the text for the body of the popup
	 */
	public MyPopupAction(String title, String text) 
	{
		this.title = title;
		this.text = text;
	}

	/*
	 * creates the popup
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		JOptionPane pop = new JOptionPane();		
		JOptionPane.showMessageDialog(pop, text, title, JOptionPane.PLAIN_MESSAGE);
	}
	
	

}
