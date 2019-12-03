package TypeRacerPackage;
import java.io.*;

public class TextListener {
	File filepath;
	BufferedReader read;
	boolean newWords;
	String line="";
	String word;
	
	public TextListener(File filelocation)
	{
		filepath = filelocation; 
		System.out.println(filepath);
		try
		{
			read = new BufferedReader(new FileReader(filepath));
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("ERROR");
		}
		newWords=true;
	}
	public String firstOpt()
	{

		if (line.length()<=1)
		{
			newWords=true;
			
		}
		if (newWords)
		{
			line=secondOpt();
			newWords=false;
		}
		if (line == null )
		{
			return null;
		}
		if (line.contains(" "))
		{
			word = line.substring(0,line.indexOf(" "));
			word=word.trim();
			line=line.substring(line.indexOf(" "),line.length());
			line=line.trim();
		}
		else 
		{
			word = line;
			line="";
		}
			System.out.println(line+","+word);	
		return	word;
	}
	
	public String secondOpt()
	{
		String line="";
		try
		{
			try
			{
				line = read.readLine().trim();
			}
			catch ( IOException iox1 )
			{
			System.out.println("ERROR"); 
			line = null; // whenever a null is returned to the main program the game is over 
			}
		}
		catch(NullPointerException npe)
		{
			line=null;
		}
		//System.out.println("test");
		return line;
	}

	public String thirdOpt()
	{
		String b ="";
		try
		{
	     String a = read.readLine();
	     b=a;
	     while ( a != null )  // while not end of file
	     {
	       a = read.readLine();
	       if (a!=null)
	       {
	       		b=b+a;	
	       }
	     }
		}
		catch(IOException i)
		{
			System.out.println("ERROR"); 
		}
	     return b;
	}
}