/*
 * @author lmichale, shilpar, swatigupta, tgavankar
 * @version 1.0
 */

package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import panels.MasterPanel;

public class NumPanelsAction implements ActionListener{

	MasterPanel panel;
	int val;
	int panelNum;

	/*
	 * class constructor
	 * 
	 * @param m the MasterPanel to update
	 * @param n the number of charts being viewed
	 * @param panelNum which ViewPanel to use
	 */
	public NumPanelsAction(MasterPanel m , int n, int panelNum) 
	{
		panel = m;
		val = n;
		this.panelNum = panelNum;
	}

	/*
	 * updates the MasterPanel to show the chart(s) and ViewPanel
	 * the user is interested in
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) 
	{
		panel.numCharts = val;
		panel.sidePanel = panelNum;
		panel.redraw();
	}
}
