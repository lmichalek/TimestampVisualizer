/*
 * @author lmichale, shilpar, swatigupta, tgavankar
 * @version 1.0
 */

package visualize;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader
{
	private BufferedReader in;
	private String line;

	/*
	 * class constructor 
	 * 
	 * @param fileName the name of the file to be loaded 
	 */
	public FileLoader(String fileName)
	{
		try
		{
			in = new BufferedReader(new FileReader(fileName));
			line = in.readLine();
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/*
	 * returns whether the file has more lines
	 * 
	 * @return true if there are more lines, false otherwise
	 */
	public boolean hasMoreLines()
	{
		return line != null;
	}

	/*
	 * reads a line of the inputted file
	 * 
	 * @return the line that was just read
	 */
	public String readLine()
	{
		try
		{
			String toReturn = line;
			line = in.readLine();
			return toReturn;
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/*
	 * closes the file's buffer reader
	 */
	public void close()
	{
		try
		{
			in.close();
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);    }
	}
}