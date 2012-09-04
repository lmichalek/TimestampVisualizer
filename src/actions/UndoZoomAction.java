/*
 * @author lmichale, shilpar, swatigupta, tgavankar
 * @version 1.0
 */

package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import panels.MasterPanel;
import visualize.DateRange;
import visualize.DateRange.Grouping;

public class UndoZoomAction implements ActionListener{

	MasterPanel panel;
	DateRange range;
	int panelNum;

	/*
	 * class constructor
	 * 
	 * @param m the MasterPanel to update
	 * @param panelNum which ViewPanel to update
	 */
	public UndoZoomAction(MasterPanel m , int panelNum) 
	{
		panel = m;
		range = panel.view[panelNum];
		this.panelNum = panelNum;
	}

	/*
	 * undoes a zoom-in which was caused by clikcing on the chart
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if(range.zooms <= 0) return;


		if(panelNum != 0)
		{
			panel.sidePanel = panelNum;
		}

		switch(range.g)
		{
		case YEARS:
			break;
		case MONTHS:
			range.zooms--;
			monthsToYears();
			break;
		case DAYS_OF_WEEK:
			range.zooms--;
			dOwToMonths();
			break;
		case HOURS:
			range.zooms--;
			hoursToDoW();
			break;
		case MINUTES:
			range.zooms--;
			minutesToHours();
			break;
		case SECONDS:
			range.zooms--;
			secondsToMinutes();
			break;

		}

		panel.redraw();
	}

	/*
	 * switch view from minutes to hours
	 */
	private void minutesToHours()
	{
		if(panelNum == 0)
			panel.prefPanel.hoursButton.setSelected(true);
		else if(panelNum == 1)
			panel.prefPanel1.hoursButton.setSelected(true);
		else if(panelNum==2)
			panel.prefPanel2.hoursButton.setSelected(true);

		for(int i = 0; i < range.hours.length; i++)
		{
			range.hours[i] = true;
		}

		range.g = Grouping.HOURS;
	}

	/*
	 * switches view from seconds to minutes
	 */
	private void secondsToMinutes()
	{
		if(panelNum ==0)
			panel.prefPanel.minutesButton.setSelected(true);
		else if(panelNum== 1)
			panel.prefPanel1.minutesButton.setSelected(true);
		else if(panelNum==2)
			panel.prefPanel2.minutesButton.setSelected(true);

		for(int i = 0; i < range.minutes.length; i++)
		{
			range.minutes[i] = true;
		}

		range.g = Grouping.MINUTES;
	}

	/*
	 * switches view from days of the week to months
	 */
	private void dOwToMonths()
	{
		if(panelNum==0)
			panel.prefPanel.monthsButton.setSelected(true);
		else if(panelNum == 1)
			panel.prefPanel1.monthsButton.setSelected(true);
		else if(panelNum==2)
			panel.prefPanel2.monthsButton.setSelected(true);

		for(int i = 0; i < range.months.length; i++)
		{
			range.months[i] = true;
		}

		range.g = Grouping.MONTHS;
	}

	/*
	 * switches view from hours to days of the week
	 */
	private void hoursToDoW()
	{
		if(panelNum==0)
			panel.prefPanel.daysOfWeekButton.setSelected(true);
		else if(panelNum == 1)
			panel.prefPanel1.daysOfWeekButton.setSelected(true);
		else if(panelNum==2)
			panel.prefPanel2.daysOfWeekButton.setSelected(true);

		for(int i = 0; i < range.daysOfWeek.length; i++)
		{
			range.daysOfWeek[i] = true;		
		}

		range.g = Grouping.DAYS_OF_WEEK;
	}

	/*
	 * switches view from months to years
	 */
	private void monthsToYears()
	{
		if(panelNum==0)
			panel.prefPanel.yearsButton.setSelected(true);
		else if(panelNum == 1)
			panel.prefPanel1.yearsButton.setSelected(true);
		else if(panelNum==2)
			panel.prefPanel2.yearsButton.setSelected(true);

		for(int i = 0; i < range.years.length; i++)
		{
			range.years[i] = true;		
		}

		range.g = Grouping.YEARS;
	}
}
