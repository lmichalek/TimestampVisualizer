/*
 * @author lmichale, shilpar, swatigupta, tgavankar
 * @version 1.0
 */

package actions;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.JFreeChart;

import panels.MasterPanel;
import visualize.DateRange;
import visualize.FileObject;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class ExportAsPDFAction implements ActionListener{

	MasterPanel panel;

	/*
	 * class constructor
	 * 
	 * @param m the MasterPanel to be exported
	 */
	public ExportAsPDFAction(MasterPanel m) 
	{
		panel = m;
	}

	/*
	 * exports the graph as a pdf
	 * 
	 * @param e the event that triggered this action
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			JCheckBox includeDate = new JCheckBox("Show Today's Date");

			JCheckBox includeHash = new JCheckBox("Include Hash Value");
			JTextField hash = new JTextField(32);

			JCheckBox includeName = new JCheckBox("Include Expert Name");
			JTextField name = new JTextField(32);

			JCheckBox includeFiles = new JCheckBox("Include File List");

			JPanel exportPanel = new JPanel();
			exportPanel.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();

			c.gridx = 0;
			c.gridy = 0;
			exportPanel.add(includeDate, c);

			c.gridx = 0;
			c.gridy++;
			exportPanel.add(includeHash, c);

			c.gridx++;
			exportPanel.add(hash, c);

			c.gridx = 0;
			c.gridy++;
			exportPanel.add(includeName, c);

			c.gridx++;
			exportPanel.add(name, c);

			c.gridx = 0;
			c.gridy++;
			exportPanel.add(includeFiles, c);

			c.gridx++;
			exportPanel.add(new JLabel("Warning: May slow down export significantly"), c);

			int result = JOptionPane.showConfirmDialog(null, exportPanel, "PDF Export options", JOptionPane.OK_CANCEL_OPTION);

			if(result == JOptionPane.OK_OPTION) {
				String savename = saveFile(new Frame(), "Save...", ".", "chart.pdf");
				if(savename != null) { // User hit cancel
					ArrayList<ExportData> data = new ArrayList<ExportData>();

					if(panel.numCharts == 1) {
						data.add(new ExportData(panel.createChart(panel.view[0], 0), "", makeDescription(includeFiles.isSelected() ? panel.allFilesSingle : null, panel.singleGraphTitles, panel.view[0], includeDate.isSelected(), includeHash.isSelected() ? hash.getText() : null, includeName.isSelected() ? name.getText() : null)));
					}
					else if(panel.numCharts == 2) {
						data.add(new ExportData(panel.createChart(panel.view[1], 1), "", makeDescription(includeFiles.isSelected() ? panel.allFilesDual1 : null, panel.dual1GraphTitles,  panel.view[1], includeDate.isSelected(), includeHash.isSelected() ? hash.getText() : null, includeName.isSelected() ? name.getText() : null)));
						data.add(new ExportData(panel.createChart(panel.view[2], 2), "", makeDescription(includeFiles.isSelected() ? panel.allFilesDual2 : null,  panel.dual2GraphTitles, panel.view[2], includeDate.isSelected(), includeHash.isSelected() ? hash.getText() : null, includeName.isSelected() ? name.getText() : null)));
					}

					try {
						create(new FileOutputStream(new File(savename)), data);
						JPanel exportCompletePanel = new JPanel();
						exportCompletePanel.add(new JLabel("PDF Export Complete"));
						JOptionPane.showConfirmDialog(null, exportCompletePanel, "Export Complete", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
					} catch (DocumentException e1) {
						e1.printStackTrace();
					}
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/*
	 * @param allFiles all files that were included in the graph
	 * @param titles the names of all the included files
	 * @param dr the DateRange that is being exported
	 * @param date whether the date should be displayed or not
	 * @param hash source drive hash
	 * @param name of the experts who created the graph
	 * 
	 * @return a description of the chart to be exported 
	 */
	private String makeDescription(ArrayList<ArrayList<FileObject>> allfiles, ArrayList<String> titles, DateRange dr, boolean date, String hash, String name) {
		StringBuilder sb = new StringBuilder();
		if(date) {
			sb.append("Generated on: ");
			DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
			sb.append(df.format(new Date()));
			sb.append("\n");
		}

		if(name != null) {
			sb.append("Expert(s): ");
			sb.append(name);
			sb.append("\n");
		}

		if(hash != null) {
			sb.append("Source Drive Hash: ");
			sb.append(hash);
			sb.append("\n");
		}

		sb.append("\nData Filter: \n");
		sb.append(dr.toString());
		sb.append("\n\n");

		if(allfiles != null) {
			sb.append("Included file lists: \n\n");
			ArrayList<ArrayList<FileObject>> allfiltered = dr.filterFiles(allfiles);
			for(int i=0; i<allfiltered.size(); i++) {
				sb.append(titles.get(i));
				sb.append(": \n");
				for(FileObject f : allfiltered.get(i)) {
					sb.append(f.getOrigLine());
					sb.append("\n");
				}
				sb.append("\n\n");
			}
		}
		return sb.toString();
	}

	private class ExportData {
		public JFreeChart chart;
		public String title;
		public String description;

		public ExportData(JFreeChart chart, String title, String description) {
			this.chart = chart;
			this.title = title;
			this.description = description;
		}
	}

	/*
	 * @param f parent frame
	 * @param title title
	 * @param defDir directory to save file
	 * @param fileType the type of the file
	 * 
	 * @return the pathname of the saved file
	 */
	private String saveFile(Frame f, String title, String defDir, String fileType) {
		FileDialog fd = new FileDialog(f, title, FileDialog.SAVE);
		fd.setFile(fileType);
		fd.setDirectory(defDir);
		fd.setLocation(300, 200);
		fd.setVisible(true);
		if(fd.getDirectory() == null || fd.getFile() == null) return null;
		return fd.getDirectory() + fd.getFile();
	}

	/*
	 * @param outputStream where to write the pdf
	 * @param data the data to be written
	 */
	public void create(OutputStream outputStream, ArrayList<ExportData> data) throws DocumentException, IOException {
		Document document = null;
		PdfWriter writer = null;

		try {
			//instantiate document and writer
			document = new Document(PageSize.LETTER);
			writer = PdfWriter.getInstance(document, outputStream);

			//open document
			document.open();

			for(int i=0; i<data.size(); i++) {
				ExportData edata = data.get(i);
				JFreeChart chart = edata.chart;
				String titleText = edata.title;
				String descText = edata.description;

				Paragraph title = new Paragraph(new Chunk(titleText));
				title.setAlignment(Element.ALIGN_CENTER);
				//add text before
				document.add(title);

				//add image
				int width = 500;
				int height = 350;

				//create PdfContentByte
				//if you work with this object, you write to
				//the top most layer, meaning anything behind
				//will be clipped
				PdfContentByte contentByte = writer.getDirectContent();
				//create PdfTemplate from PdfContentByte
				PdfTemplate template = contentByte.createTemplate(width, height);
				//create Graphics2D from PdfTemplate
				Graphics2D g2 = 
						template.createGraphics(width, height, new DefaultFontMapper());
				//setup the drawing area
				Rectangle2D r2D = new Rectangle2D.Double(0, 0, width, height);
				//pass the Graphics2D and drawing area to JFreeChart
				chart.draw(g2, r2D, null);
				g2.dispose(); //always dispose this

				//create Image from PdfTemplate
				Image image = Image.getInstance(template);
				image.setAlignment(Image.MIDDLE);
				document.add(image);

				//add text after
				Paragraph desc = new Paragraph(new Chunk(descText));
				document.add(desc);


				document.newPage();
			}
			//release resources
			document.close();
			document = null;

			writer.close();
			writer = null;
		} catch(DocumentException de) {
			throw de;
		} finally {
			//release resources
			if(null != document) {
				try { document.close(); }
				catch(Exception ex) { }
			}

			if(null != writer) {
				try { writer.close(); }
				catch(Exception ex) { }
			}
		}
	}

}
