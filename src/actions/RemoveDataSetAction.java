/*
 * @author lmichale, shilpar, swatigupta, tgavankar
 * @version 1.0
 */

package actions;

import java.awt.Color;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import panels.MasterPanel;
import visualize.DateRange;
import visualize.FileObject;
import panels.ViewPanel;

public class RemoveDataSetAction implements ActionListener {

	MasterPanel master;

	/*
	 * class constructor
	 * 
	 * @param m the MasterPanel to update
	 */
	public RemoveDataSetAction(MasterPanel m)
	{
		master = m;
	}

	/*
	 * removes a given dataset from user specified graphs
	 * the user is prompted for which files they want to remove from all datasets
	 * once a file is removed it isn't seen on any graph
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//check if there are datasets to remove
		if(!existDataSets())
		{
			JOptionPane pop = new JOptionPane();
			String message = "There are no imported files to remove.";
			JOptionPane.showMessageDialog(pop, message, "Error", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		//get all files being displayed
		ArrayList<String> allImported = new ArrayList<String>();
		for(String file : master.singleGraphTitles)
		{
			if(!allImported.contains(file)) allImported.add(file);
		}
		for(String file : master.dual1GraphTitles)
		{
			if(!allImported.contains(file)) allImported.add(file);
		}
		for(String file : master.dual2GraphTitles)
		{
			if(!allImported.contains(file)) allImported.add(file);
		}

		//create checkboxes for each file
		ArrayList<JCheckBox> options = new ArrayList<JCheckBox>();
		for(String file : allImported)
			options.add(new JCheckBox(file));


		removeDialog(options);

		//removes user selected files from single graph
		if(master.singleGraphTitles.size() != 0)
			recalculateYears(master.allFilesSingle, master.view[0]);
		else
		{
			master.view[0].minYear = Integer.MAX_VALUE;
			master.view[0].maxYear = Integer.MIN_VALUE;
			master.view[0].years = null;
			master.prefPanel = new ViewPanel(master, 
					master.view[0],
					master.sideDimension,
					Color.LIGHT_GRAY,
					"Single Chart: View Selection",
					null);
		}

		//removes user selected files from top dual graph
		if(master.dual1GraphTitles.size() != 0)
			recalculateYears(master.allFilesDual1, master.view[1]);
		else
		{
			master.view[1].minYear = Integer.MAX_VALUE;
			master.view[1].maxYear = Integer.MIN_VALUE;
			master.view[1].years = null;
			master.prefPanel1 = new ViewPanel(master, 
					master.view[1],
					master.sideDimension,
					Color.LIGHT_GRAY,
					"Dual Chart 1: View Selection",
					null);
		}

		//removes user selected files from bottom dual graph
		if(master.dual2GraphTitles.size() != 0)
			recalculateYears(master.allFilesDual2, master.view[2]);
		else
		{
			master.view[2].minYear = Integer.MAX_VALUE;
			master.view[2].maxYear = Integer.MIN_VALUE;
			master.view[2].years = null;
			master.prefPanel2 = new ViewPanel(master, 
					master.view[2],
					master.sideDimension,
					Color.LIGHT_GRAY,
					"Dual Chart 2: View Selection",
					null);
		}



		master.redraw();
	}


	/*
	 * @return true if there are datasets that can be removed
	 */
	private boolean existDataSets()
	{
		return (master.singleGraphTitles.size() != 0 
				|| master.dual1GraphTitles.size() != 0
				|| master.dual2GraphTitles.size() !=0);
	}

	/*
	 * updates the year range for the given DateRange
	 * 
	 * @param files files to be included
	 * @param d DateRange to update year range
	 */
	private void recalculateYears(ArrayList<ArrayList<FileObject>> files, DateRange d)
	{
		int newMin = Integer.MAX_VALUE;
		int newMax = Integer.MIN_VALUE;

		for(ArrayList<FileObject> arr : files)
		{
			for(FileObject file : arr)
			{
				if(file.getYear() < newMin) newMin = file.getYear();
				if(file.getYear() > newMax) newMax = file.getYear();
			}
		}

		boolean[] newYears = new boolean[newMax-newMin+1];
		for(int i = 0; i < newYears.length; i++) newYears[i] = true;

		int startOffset = newMin-d.minYear;
		for(int i = 0; i < newYears.length; i++)
		{
			newYears[i] = d.years[startOffset+i];
		}

		d.minYear = newMin;
		d.maxYear = newMax;
		d.years = newYears;
	}


	/*
	 * prompts user with a dialog box of checkboxes for which files they wish to remove
	 * 
	 * @param options checkboxes repreresnting files which can be removed
	 */
	private void removeDialog(ArrayList<JCheckBox> options)
	{
		JPanel frame = new JPanel();
		frame.setLayout(new GridBagLayout());
		String titleBarText = "";
		int dialogOpts =0;

		GridBagConstraints g = new GridBagConstraints();

		frame.add(new JLabel("Which imported files would you like to remove?"), g);
		titleBarText = "Select files to remove";
		dialogOpts = JOptionPane.OK_CANCEL_OPTION;

		int offset = 1;
		g.anchor = GridBagConstraints.WEST;
		for(JCheckBox opt : options)
		{
			if(offset==1)g.insets = new Insets(10,0,0,0);
			else g.insets = new Insets(2,0,0,0);
			g.gridy = offset++;
			frame.add(opt, g);
		}

		int ret =JOptionPane.showConfirmDialog(null,
				frame,
				titleBarText,
				dialogOpts,
				JOptionPane.PLAIN_MESSAGE);


		ArrayList<String> currTitles = null;
		ArrayList<ArrayList<FileObject>> currObjs = null;
		if(ret == JOptionPane.OK_OPTION)
		{
			for(JCheckBox opt : options)
			{
				if(opt.isSelected())
				{
					String file = opt.getText();
					for(int i = 0; i < 3; i++)
					{
						if(i==0)
						{
							currTitles = master.singleGraphTitles;
							currObjs = master.allFilesSingle;
						}
						else if(i==1)
						{
							currTitles = master.dual1GraphTitles;
							currObjs = master.allFilesDual1;
						}
						else if(i==2)
						{
							currTitles = master.dual2GraphTitles;
							currObjs = master.allFilesDual2;
						}

						for(int j = 0; j < currTitles.size(); j++)
						{
							if(currTitles.get(j).equals(file))
							{
								currTitles.remove(j);
								currObjs.remove(j);
							}
						}
					}
				}
			}
		}
	}

}
