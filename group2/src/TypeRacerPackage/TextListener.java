package TypeRacerPackage;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class TextListener {
	File filepath;
	BufferedReader read;
	boolean newWords;
	String line = "";
	String text;
	Queue<String> words;
	Scanner in;

	public TextListener(File filelocation) {
		try {
			read = new BufferedReader(new FileReader(filelocation));
			in = new Scanner(new FileReader(filelocation));
		} catch (FileNotFoundException fnfe) {
			System.out.println("ERROR");
		}
		newWords = true;
		text = "";
		words = new LinkedList<String>();
		read();
	}
	
	private void read() {
		while(in.hasNext()) {
			String temp = in.next().trim();
			words.add(temp);
			text += temp + " ";
		}
	}

	public String firstOpt() {
		System.out.println(words);
		return words.remove();
	}

	public String secondOpt() {
		String line = "";
		try {
			try {
				line = read.readLine().trim();
			} catch (IOException iox1) {
				System.out.println("ERROR");
				line = null; // whenever a null is returned to the main program the game is over
			}
		} catch (NullPointerException npe) {
			line = null;
		}
		// System.out.println("test");
		return line;
	}

	public String thirdOpt() {
		return text;
	}
}