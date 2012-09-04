/*
 * @author lmichale, shilpar, swatigupta, tgavankar
 * @version 1.0
 */

package panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;

import visualize.DateRange;
import visualize.FileObject;
import actions.AddNewDataSetAction;
import actions.ClickChartAction;
import actions.ExportAsPDFAction;
import actions.ExportAsPNGAction;
import actions.MyPopupAction;
import actions.NumPanelsAction;
import actions.RemoveDataSetAction;
import actions.UndoZoomAction;

public class MasterPanel extends ApplicationFrame
{

	private static final long serialVersionUID = 1L;
	JPanel panel;
	public int numCharts = 1; //used to tell if we are viewing 1 or 2 charts

	//dimensions for various parts of the panel
	Dimension singleChartD = new Dimension(800, 600);
	Dimension dualChartD = new Dimension(800, 300);
	public Dimension sideDimension = new Dimension(400,600);

	//represents which side panel to show
	public int sidePanel = 0; 

	//files for various graphs
	public  ArrayList<ArrayList<FileObject>> allFilesSingle;
	public ArrayList<ArrayList<FileObject>> allFilesDual1;
	public ArrayList<ArrayList<FileObject>> allFilesDual2;

	//titles for various graphs
	public ArrayList<String> singleGraphTitles;
	public ArrayList<String> dual1GraphTitles;
	public ArrayList<String> dual2GraphTitles;

	public DateRange[] view =  new DateRange[3];

	//these colors represent the color of the side panel when you are editing
	//the single chart, the dual chart 1, and the dual chart 2
	Color[] colors = {Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY};

	//side panels for each of the 3 graphs
	public ViewPanel prefPanel;
	public ViewPanel prefPanel1;	
	public ViewPanel prefPanel2;	


	/*
	 * class constructor
	 */
	public MasterPanel()
	{
		super("Timestamp Visualizer");
		allFilesSingle = new ArrayList<ArrayList<FileObject>>();
		allFilesDual1 = new ArrayList<ArrayList<FileObject>>();
		allFilesDual2 = new ArrayList<ArrayList<FileObject>>();


		singleGraphTitles = new ArrayList<String>();
		dual1GraphTitles = new ArrayList<String>();
		dual2GraphTitles = new ArrayList<String>();

		for(int i =0; i < 3; i++)
		{
			view[i] = new DateRange(i);
		}

		view[0].getDataSet(allFilesSingle, singleGraphTitles);
		view[1].getDataSet(allFilesDual1, dual1GraphTitles);
		view[2].getDataSet(allFilesDual2, dual2GraphTitles);

		initPanels(null,null,null);

		panel = new JPanel(new GridBagLayout());
		redraw();
	}

	/*
	 * initializes the passed in view panels,  
	 * sets each panel's DateRange object, size, title, and associated color
	 * 
	 * @param prev1 ViewPanel for single graph 
	 * @param prev2 ViewPanel for the top dual graph
	 * @param prev3 View Panel for the bottom dual graph
	 */
	public void initPanels(ViewPanel prev1, ViewPanel prev2, ViewPanel prev3)
	{
		prefPanel = new ViewPanel(
				this, 
				view[0],
				sideDimension, 
				colors[0], 
				"Single Chart: View Selection",
				prev1);

		prefPanel1 = new ViewPanel(
				this, 
				view[1], 
				sideDimension,
				colors[1], 
				"Dual Chart 1: View Selection",
				prev2);

		prefPanel2 = new ViewPanel(
				this, 
				view[2], 
				sideDimension, 
				colors[2], 
				"Dual Chart 2: View Selection",
				prev3);
	}

	/*
	 * redraws the panel to reflect the users requested changes
	 */
	public void redraw()
	{
		switch(numCharts)
		{
		case 1:
			redraw1();
			break;
		case 2:
			redraw2();
			break;
		}
	}

	/*
	 * draws panel with 1 chart
	 */
	public void redraw1()
	{
		//clear panel
		panel.removeAll();
		GridBagConstraints c = new GridBagConstraints();

		//put menu bar in place
		JMenuBar menubar = getBar();
		c.gridx = 0; c.gridy = 0;
		c.anchor=GridBagConstraints.WEST;
		c.weighty=1;
		panel.add(menubar,c);

		//get updated chart and put it in place
		JFreeChart chart = createChart(view[0], 0);
		ChartPanel p = new ChartPanel(chart);
		chart.setBorderVisible(false);
		p.setPreferredSize(singleChartD);
		p.addChartMouseListener(new ClickChartAction(view[0], this,0));
		c.gridy=1;		
		panel.add(p, c);

		//get appropriate ViewPanel and put in place
		c.gridx=2;
		panel.add(getSidePanel(), c);

		setContentPane(panel);

	}

	/*
	 * draws panel with 2 charts 
	 */
	public void redraw2()
	{
		//clear panel
		panel.removeAll();
		GridBagConstraints c = new GridBagConstraints();

		//put menu bar and put in place
		JMenuBar menubar = getBar();
		c.gridx = 0; c.gridy = 0;
		c.anchor=GridBagConstraints.WEST;
		c.weighty=1;
		panel.add(menubar,c);

		JPanel chartPanel = new JPanel(new GridBagLayout());
		GridBagConstraints chartC = new GridBagConstraints();

		//get updated top chart and put in place
		JFreeChart chart1 = createChart(view[1], 1);
		chart1.setBorderVisible(sidePanel == 01);
		ChartPanel p1 = new ChartPanel(chart1);
		p1.setPreferredSize(dualChartD);
		p1.addChartMouseListener(new ClickChartAction(view[1], this, 1));
		chartC.gridx=0;
		chartC.gridy=0;		
		chartPanel.add(p1, chartC);

		//get updated bottom chart and put in place
		JFreeChart chart2 = createChart(view[2], 2);
		chart2.setBorderVisible(sidePanel==2);
		ChartPanel p2 = new ChartPanel(chart2);
		p2.setPreferredSize(dualChartD);
		p2.addChartMouseListener(new ClickChartAction(view[2], this, 2));
		chartC.gridx=0;
		chartC.gridy=1;			
		chartPanel.add(p2, chartC);

		//add charts to panel
		c.gridy=1;
		panel.add(chartPanel, c);

		//get appropriate ViewPanel and put in place
		c.gridx=2;
		panel.add(getSidePanel(), c);

		setContentPane(panel);
	}

	/*
	 * @return the correct ViewPanel's panel 
	 *         based on which graph the user is currently editing
	 */
	public Component getSidePanel()
	{
		switch(sidePanel)
		{
		case 0:
			return prefPanel.panel;
		case 1:
			return prefPanel1.panel;
		case 2:
			return prefPanel2.panel;
		}
		return null;
	}

	private void adjustYears(ArrayList<FileObject> files, boolean[] graphs)
	{
		for(FileObject file : files)
		{
			for(int i =0; i < graphs.length; i++)
			{
				if(!graphs[i]) continue;


				if(file.getYear() > view[i].maxYear)
				{
					if(view[i].minYear==Integer.MAX_VALUE)
					{
						view[i].maxYear = file.getYear();
					}
					else
					{
						boolean[] newYears = new boolean[file.getYear()-view[i].minYear+1];
						for(int j = 0; j< newYears.length; j++)
							newYears[j]=true;
						if(view[i].years != null)
						{
							for(int j = 0; j<view[i].years.length; j++)
							{
								newYears[j] = view[i].years[j];
							}
						}
						view[i].maxYear = file.getYear();
						view[i].years = newYears;
					}

				}


				if(file.getYear() < view[i].minYear)
				{
					if(view[i].maxYear==Integer.MIN_VALUE)
					{
						view[i].minYear = file.getYear();
					}
					else
					{
						int diff = view[i].minYear-file.getYear();
						boolean[] newYears = new boolean[view[i].maxYear - file.getYear() + 1];
						for(int j = 0; j< newYears.length; j++)
							newYears[j]=true;
						if(view[i].years != null)
						{
							for(int j = 0; j<view[i].years.length; j++)
							{
								newYears[j+diff] = view[i].years[j];
							}
						}
						view[i].minYear = file.getYear();
						view[i].years = newYears;
					}
				}
			}
		}
	}

	/*
	 * adds new ls dump files to appropriate graphs (based on user's selection)
	 * 
	 * @param files represents the additional files from the new ls dump
	 * @param title is the ls dump's filename
	 * @param g1 true/false if it should be in the single graph
	 * @param g2 true/false if it should be in the top dual graph
	 * @param g3 true/false if it should be in the bottom dual graph
	 */
	public void addSet(ArrayList<FileObject> files, String title, boolean g1, boolean g2, boolean g3)
	{
		if(g1) 
		{
			allFilesSingle.add(files);
			singleGraphTitles.add(title);
		}


		if(g2) 
		{
			allFilesDual1.add(files);
			dual1GraphTitles.add(title);
		}


		if(g3)
		{
			allFilesDual2.add(files);
			dual2GraphTitles.add(title);
		}

		boolean[] arr = new boolean[3];
		arr[0]=g1; arr[1] = g2; arr[2] = g3;

		adjustYears(files,arr);
		initPanels(prefPanel, prefPanel1, prefPanel2);
	}

	/*
	 * creates an updated chart
	 * 
	 * @param d the data to be used for the chart
	 * @param chartNum represents single, top dual, or bottom dual graph
	 * @return the updated chart
	 */
	public JFreeChart createChart(DateRange d, int chartNum) 
	{
		CategoryDataset dataset = null;

		if(chartNum==0) dataset = d.getDataSet(d.filterFiles(allFilesSingle),singleGraphTitles);
		if(chartNum==1) dataset = d.getDataSet(d.filterFiles(allFilesDual1), dual1GraphTitles);
		if(chartNum==2) dataset = d.getDataSet(d.filterFiles(allFilesDual2), dual2GraphTitles);

		//get chart's label
		String xLabel = "";
		switch (d.g)
		{
		case YEARS:
			xLabel = "Year"; break;
		case MONTHS:
			xLabel = "Month"; break;
		case DAYS_OF_WEEK:
			xLabel = "Day of the Week"; break;
		case DAYS:
			xLabel = "Day of the Month"; break;
		case HOURS:
			xLabel = "Hour of the Day"; break;
		case MINUTES:
			xLabel = "Minute"; break;
		case SECONDS:
			xLabel = "Second"; break;
		}
		JFreeChart chart = ChartFactory.createBarChart(
				null, 
				xLabel, 
				"# files accessed", 
				dataset, 
				PlotOrientation.VERTICAL, 
				true, 
				true, 
				false);

		//set visuals of chart
		chart.setBackgroundPaint(Color.LIGHT_GRAY);
		chart.getPlot().setBackgroundPaint(Color.GRAY);
		chart.getLegend().setBackgroundPaint(Color.LIGHT_GRAY);
		chart.setBorderPaint(Color.BLACK);
		chart.setBorderStroke(new BasicStroke(3));
		chart.setBorderVisible(true);
		chart.getPlot().setInsets(new RectangleInsets(0,0,10,0));

		CategoryAxis xAxis = chart.getCategoryPlot().getDomainAxis();
		xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

		ValueAxis yAxis = chart.getCategoryPlot().getRangeAxis();
		yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		BarRenderer br = new BarRenderer();
		br.setItemMargin(0.0);
		chart.getCategoryPlot().setRenderer(br);
		return chart;
	}

	/*
	 * creates the menubar
	 * 
	 * @return the menubar for the display
	 */
	public JMenuBar getBar()
	{
		JMenuBar mainMenuBar;

		JMenu format, help, undo, imp, export;
		JMenuItem plainTextMenuItem;

		JRadioButtonMenuItem rbMenuItem;

		mainMenuBar = new JMenuBar();

		format = new JMenu("Select Format");
		format.setMnemonic(KeyEvent.VK_S);
		mainMenuBar.add(format);

		rbMenuItem = new JRadioButtonMenuItem( "Single Graph");
		rbMenuItem.setSelected((1==numCharts));
		rbMenuItem.addActionListener(new NumPanelsAction(this, 1, 0));
		rbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
		format.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem( "Double Graph");
		rbMenuItem.setSelected((2==numCharts));
		rbMenuItem.addActionListener(new NumPanelsAction(this, 2,1));
		rbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
		format.add(rbMenuItem);
		mainMenuBar.add(format);




		undo= new JMenu("Undo Zoom");
		undo.setMnemonic(KeyEvent.VK_U);
		plainTextMenuItem = new JMenuItem("Single Chart");
		plainTextMenuItem.setMnemonic(KeyEvent.VK_S);
		plainTextMenuItem.addActionListener(new UndoZoomAction(this, 0));
		undo.add(plainTextMenuItem);
		undo.addSeparator();
		plainTextMenuItem = new JMenuItem("Dual Chart 1");
		plainTextMenuItem.setMnemonic(KeyEvent.VK_1);
		plainTextMenuItem.addActionListener(new UndoZoomAction(this, 1));
		undo.add(plainTextMenuItem);
		plainTextMenuItem = new JMenuItem("Dual Chart 2");
		plainTextMenuItem.setMnemonic(KeyEvent.VK_2);
		plainTextMenuItem.addActionListener(new UndoZoomAction(this, 2));
		undo.add(plainTextMenuItem);
		mainMenuBar.add(undo);

		imp = new JMenu("Import");
		imp.setMnemonic(KeyEvent.VK_I);
		plainTextMenuItem = new JMenuItem("Import a File");
		plainTextMenuItem.addActionListener(new AddNewDataSetAction(this));
		imp.add(plainTextMenuItem);

		plainTextMenuItem = new JMenuItem("Remove a File");
		plainTextMenuItem.addActionListener(new RemoveDataSetAction(this));
		imp.add(plainTextMenuItem);
		mainMenuBar.add(imp);


		export = new JMenu("Export");
		export.setMnemonic(KeyEvent.VK_E);
		plainTextMenuItem = new JMenuItem("Export to PNG");
		plainTextMenuItem.addActionListener(new ExportAsPNGAction(this));
		export.add(plainTextMenuItem);
		plainTextMenuItem = new JMenuItem("Export to PDF");
		plainTextMenuItem.addActionListener(new ExportAsPDFAction(this));
		export.add(plainTextMenuItem);
		mainMenuBar.add(export);

		help = getHelpMenu();
		help.setMnemonic(KeyEvent.VK_H);
		mainMenuBar.add(help);

		return mainMenuBar;
	}

	/*
	 * @return help menu
	 */
	public JMenu getHelpMenu()
	{
		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);

		JMenuItem item;
		String title, text;


		title = "General";
		item = new JMenuItem(title);	
		text = "Use the Import dropdown to add/remove files containing ls dumps " +
				"to be viewed.  Files are loaded and displayed based on access time. \n\n" +
				"Select how to view files by using the radio buttons on the " +
				"right panel. \n"+
				"Filter which files to include in the displayed graph(s) by " +
				"selecting the desided filters from the right panel. \n\n" +
				"Click on a column to zoom in on that component.\n\n" + 
				"";
		item.addActionListener(new MyPopupAction( (title + " Help"), text));
		help.add(item);

		title = "Adding/Removing Files";
		item = new JMenuItem(title);
		text = "Use the Import dropdown to add/remove files. \n\n" +
				"When adding files choose which graphs (single, top dual, bottom dual)\n" +
				"will include this dataset. \n\n" +
				"When removing files, they will be removed from all graphs.";
		item.addActionListener(new MyPopupAction( (title + " Help"), text));
		help.add(item);

		title = "Regex";
		item = new JMenuItem("Regex Info");
		text = "Enter a regular expression representing which files to include. \n\n" +
				"Any regular expression supported by Java can be used here. \n\n" +
				"RegEx Help Examples: \n" +
				". \t matches any character except a newline\n" +
				"* \t match 0 or more times\n" +
				"+ \t match 1 or more times\n" +
				"";
		item.addActionListener(new MyPopupAction( (title + " Help"), text));
		help.add(item);

		return help;
	}



}