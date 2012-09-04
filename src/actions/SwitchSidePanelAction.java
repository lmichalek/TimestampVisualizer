/*
 * @author lmichale, shilpar, swatigupta, tgavankar
 * @version 1.0
 */

package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import panels.MasterPanel;

public class SwitchSidePanelAction implements ActionListener{

	MasterPanel master;

	/*
	 * class constructor
	 * 
	 * @param m the MasterPanel to update
	 */
	public SwitchSidePanelAction(MasterPanel m)
	{
		master = m;

	}

	/*
	 * switches the ViewPanel in the MasterPanel 
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{

		JButton source = (JButton) e.getSource();
		if(source.getText().equals("Top Graph")) master.sidePanel = 1;
		if(source.getText().equals("Bottom Graph")) master.sidePanel =2;

		master.redraw();
	}
}
