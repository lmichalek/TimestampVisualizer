/*
 * @author lmichale, shilpar, swatigupta, tgavankar
 * @version 1.0
 */
package visualize;

import org.jfree.ui.RefineryUtilities;

import panels.MasterPanel;
import actions.AddNewDataSetAction;

public class Main 
{ 
	public static void main(String[] args) 
	{
		MasterPanel display;

		display = new MasterPanel();
		display.pack();
		RefineryUtilities.centerFrameOnScreen(display);
		display.setResizable(false);
		display.setVisible(true);
		(new AddNewDataSetAction(display)).actionPerformed(null);
	}
}