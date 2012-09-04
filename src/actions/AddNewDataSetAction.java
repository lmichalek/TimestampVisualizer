/*
 * @author lmichale, shilpar, swatigupta, tgavankar
 * @version 1.0
 */

package actions;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import panels.MasterPanel;
import visualize.FileObject;
import visualize.LSParser;

public class AddNewDataSetAction implements ActionListener {

	MasterPanel master;
	boolean single;
	boolean dual1;
	boolean dual2;

	/*
	 * class constructor
	 * 
	 * @param m the MasterPanel that contains the single and dual graphs
	 *          that will have these files added to
	 */
	public AddNewDataSetAction(MasterPanel m)
	{
		this.master = m;
	}

	/*
	 * adds all the files from the imported file to the user selected graphs
	 * @param arg0 the event that occurred 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		String file = loadDialog();
		if(file==null) return;

		ArrayList<FileObject> allFiles = null;
		try
		{
			allFiles = LSParser.parseFile(file);
			if(allFiles.size()==0) throw new Exception();
		}
		catch(Exception e)
		{
			JOptionPane pop = new JOptionPane();
			String message = "The specified file has an invalid format.\n\n" 
					+"The desired format is obtained by executing:\n      ls -ls --time-style=+%m/%d/%Y\\ %T\n" 
					+ "                             or \n" +
					"      ls -Rls --time-style=+%m/%d/%Y\\ %T\n";
			JOptionPane.showMessageDialog(pop, message, "Error", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		addDialog();

		if(single && master.singleGraphTitles.contains((new File(file)).getName()))
			return;
		if(dual1 && master.dual1GraphTitles.contains((new File(file)).getName()))
			return;
		if(dual2 && master.dual2GraphTitles.contains((new File(file)).getName()))
			return;

		master.addSet(allFiles,(new File(file)).getName(),single,dual1,dual2);
		master.redraw();

	}

	/*
	 * @return the filename of the new ls dump to add
	 */
	private String loadDialog()
	{
		FileDialog fd = new FileDialog(new Frame(), "Select a file", FileDialog.LOAD);
		fd.setLocation(300,200);
		fd.setVisible(true);
		if(fd.getDirectory()==null || fd.getFile()==null) return null;
		return fd.getDirectory()+fd.getFile();
	}

	/*
	 * prompts user to select which graphs' data set to include new file 
	 */
	private void addDialog()
	{

		JPanel frame = new JPanel();
		frame.setLayout(new GridBagLayout());

		JCheckBox single = new JCheckBox("Single graph",true);
		JCheckBox dual1 = new JCheckBox("Dual graph 1", true);
		JCheckBox dual2 = new JCheckBox("Dual graph 2", true);

		GridBagConstraints g = new GridBagConstraints();
		g.gridx = 1;
		g.gridy = 0;
		frame.add(new JLabel("Which graphs would you like to add your dataset to?"), g);

		g.anchor = GridBagConstraints.WEST;
		g.insets = new Insets(10,0,0,0);
		g.gridy = 1;
		frame.add(single, g);
		g.insets = new Insets(2,0,0,0);
		g.gridy = 2;
		frame.add(dual1, g);
		g.gridy = 3;
		frame.add(dual2, g);

		int ret =JOptionPane.showConfirmDialog(null,
				frame,
				"Select destination graphs",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if(ret == JOptionPane.OK_OPTION)
		{
			this.single = single.isSelected();
			this.dual1 = dual1.isSelected();
			this.dual2 = dual2.isSelected();
		}
	}
}