/*
 * @author lmichale, shilpar, swatigupta, tgavankar
 * @version 1.0
 */

package visualize;

public class FileObject
{
  private String origLine;
  private int month;
  private int day;
  private int year;
  private int hour;
  private int minute;
  private int second;
  private int dayOfWeek;
  private String name;
  private int size;
  private boolean isFile;
  
  private boolean userRead;
  private boolean userWrite;
  private boolean userExecute;
  
  private boolean groupRead;
  private boolean groupWrite;
  private boolean groupExecute;
  
  private boolean allRead;
  private boolean allWrite;
  private boolean allExecute;
  
  /*
   * class constructor
   * 
   * @param orig the original line
   * @param month the month this file was accessed
   * @param day the day this file was accessed
   * @param year the year this file was accessed
   * @param hour the hour this file was accessed
   * @param minute the minute this file was accessed
   * @param dayOfWeek the day of the week this file was accessed
   * @param name the name of the file
   * @param size the size of the file
   * @param isFile true if the file is a file, false if it is a directory
   * @param userRead true if the read flag is set
   * @param userWrite true if the write flag is set
   * @param userExecute true if the execute flag is set
   * @param groupRead true if the read flag is set for the group
   * @param groupWrite true if the write flag is set for the group
   * @param groupExecute true if the execute flag is set for the group
   * @param allRead true if the read flag is set for the world
   * @param allWrite true if the write flag is set for the world
   * @param allExecute true if the execute flag is set for the world
   */
  public FileObject(String orig, int month, int day, int year, 
		  int hour, int minute, int second,
		  int dayOfWeek, String name, int size,
		  boolean isFile,
		  boolean userRead, boolean userWrite, boolean userExecute,
		  boolean groupRead, boolean groupWrite, boolean groupExecute,
		  boolean allRead, boolean allWrite, boolean allExecute)
  {
	  this.origLine = orig;
	  this.month = month;
	  this.day = day;
	  this.year = year;
	  this.hour = hour;
	  this.minute = minute;
	  this.second = second;
	  this.dayOfWeek = dayOfWeek;
	  this.name = name;
	  this.size = size;
	  this.isFile = isFile;
	  this.userRead = userRead;
	  this.userWrite = userWrite;
	  this.userExecute = userExecute;
	  this.groupRead = groupRead;
	  this.groupWrite = groupWrite;
	  this.groupExecute = groupExecute;
	  this.allRead = allRead;
	  this.allWrite = allWrite;
	  this.allExecute = allExecute;
  }


  /*
   * @return true if the read flag is set
   */
  public boolean userCanRead()
  {
	  return userRead;
  }

  /*
   * @return true if the write flag is set
   */
  public boolean userCanWrite()
  {
	  return userWrite;
  }

  /*
   * @return true if the execute flag is set
   */
  public boolean userCanExecute()
  {
	  return userExecute;
  }

  /*
   * @return true if the read flag is set for the group
   */
  public boolean groupCanRead()
  {
	  return groupRead;
  }

  /*
   * @return true if the write flag is set for the group
   */
  public boolean groupCanWrite()
  {
	  return groupWrite;
  }

  /*
   * @return true if the execute flag is set for the group
   */
  public boolean groupCanExecute()
  {
	  return groupExecute;
  }

  /*
   * @return true if the read flag is set for the world
   */
  public boolean allCanRead()
  {
	  return allRead;
  }

  /*
   * @return true if the write flag is set for the world
   */
  public boolean allCanWrite()
  {
	  return allWrite;
  }

  /*
   * @return true if the execute flag is set for the world
   */
  public boolean allCanExecute()
  {
	  return allExecute;
  }

  /*
   * @return the original line
   */
  public String getOrigLine() 
  {
	  return origLine;
  }

  /*
   * @return true if this FileObject represents a file
   */
  public boolean isFile()
  {
	  return isFile;
  }

  /*
   * @return the month this file was accessed
   */
  public int getMonth()
  {
	  return month;
  }

  /*
   * @return the day this file was accessed
   */
  public int getDay()
  {
	  return day;
  }

  /*
   * @return the year this file was accessed
   */
  public int getYear()
  {
	  return year;
  }

  /*
   * @return the hour this file was accessed
   */
  public int getHour()
  {
	  return hour;
  }

  /*
   * @return the minute this file was accessed
   */
  public int getMinute()
  {
	  return minute;
  }

  /*
   * @return the second this file was accessed
   */
  public int getSecond()
  {
	  return second;
  }

  /*
   * @return the day of the week this file was accessed
   */
  public int getDayOfWeek()
  {
	  return dayOfWeek;
  }

  /*
   * @return the name of this file 
   */
  public String getName()
  {
	  return name;
  }

  /*
   * @return the size of the file
   */
  public int getSize()
  {
	  return size;
  }

  /*
   * @return the name of the day of the week this file was accessed
   */
  public String getDayByName()
  {
	  String days = "";
	  switch(dayOfWeek)
	  {
	  case 1 : days = "Monday"; break;
	  case 2 : days = "Tuesday"; break;
	  case 3 : days = "Wednesday"; break;
	  case 4 : days = "Thursday"; break;
	  case 5 : days = "Friday"; break;
	  case 6 : days = "Saturday"; break;
	  case 7 : days = "Sunday"; break;
	  default : break;
	  }
	  return days;
  }

  /*
   * @return the name of the month this file was accessed
   */
  public String getMonthByName()
  {
	  String mo = "";
	  switch(month)
	  {
	  case 1 : mo = "January"; break;
	  case 2 : mo = "February"; break;
	  case 3 : mo = "March"; break;
	  case 4 : mo = "April"; break;
	  case 5 : mo = "May"; break;
	  case 6 : mo = "June"; break;
	  case 7 : mo = "July"; break;
	  case 8 : mo = "August"; break;
	  case 9 : mo = "September"; break;
	  case 10 : mo = "October"; break;
	  case 11 : mo = "November"; break;
	  case 12 : mo = "December"; break;
	  default : break;
	  }
	  return mo;
  }

  /*
   * @see java.lang.Object#toString()
   */
  public String toString()
  {
	  String s = "Name: "+getName()+"\n";
	  s+="Date modified: "+getMonthByName()+" "+getDay()+", "+getYear()+"\n";
	  s+="Time modified: ";
	  if(getHour() < 10)
		  s+="0"+getHour()+":";   
	  else s+= getHour()+":";

	  if(getMinute() < 10)
		  s+="0"+getMinute()+":";
	  else s+=getMinute()+":";

	  if(getSecond() < 10)
		  s+="0"+getSecond();
	  else s+= getSecond();


	  s+="\nFile size: "+size+" bytes";

	  if(isFile())
		  s+="\nThis is a file";
	  else s+= "\nThis is a directory";

	  s+= "\nUser priviledges: ";
	  s+= userCanRead() ? "read " : "";
	  s+= userCanWrite() ? "write " : "";
	  s+= userCanExecute() ? "execute " : "";

	  s+= "\nGroup priviledges: ";
	  s+= groupCanRead() ? "read " : "";
	  s+= groupCanWrite() ? "write " : "";
	  s+= groupCanExecute() ? "execute " : "";

	  s+= "\nGeneral priviledges: ";
	  s+= allCanRead() ? "read " : "";
	  s+= allCanWrite() ? "write " : "";
	  s+= allCanExecute() ? "execute " : "";

	  s+="\n-----------------\n";
	  return s;
  }












}