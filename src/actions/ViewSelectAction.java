/*
 * @author lmichale, shilpar, swatigupta, tgavankar
 * @version 1.0
 */

package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import visualize.DateRange;
import visualize.DateRange.Grouping;

/*
 * Called when user switches graph # in the dual graph mode.
 * Switches the currently displayed side panel
 */
public class ViewSelectAction implements ActionListener{

	JRadioButton b;
	DateRange range;
	Grouping button;

	public ViewSelectAction(JRadioButton b, DateRange range, Grouping button)
	{
		this.b = b;
		this.range = range;
		this.button = button;
	}

	public void actionPerformed(ActionEvent e)
	{
		if(b.isSelected())
		{
			range.g = button;

			switch(button)
			{
			case YEARS: range.zooms=0; break;
			case MONTHS: range.zooms=1; break;
			case DAYS_OF_WEEK: range.zooms=2; break;
			case HOURS: range.zooms=3; break;
			case MINUTES: range.zooms=4; break;
			case SECONDS: range.zooms=5; break;
			}
		}
	}
}
