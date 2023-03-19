import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class andrewGPT {

	HashMap<String, ArrayList<String>> andrew = new  HashMap<String, ArrayList<String>>(); // this will be the main graph. There are keys that represent a string of letters, and then a following ArrayList of the chars which ever come after it.
	
	public static void run(String inputFile, String inputString, String outputFile, int numWords) throws IOException
	{
		String[] bohnson = inputString.split(" ");
		int order = bohnson.length;
		andrewGPT john = new andrewGPT(inputFile, order);
		john.generateText(outputFile, numWords, inputString);
	}

	public andrewGPT(String fileName, int chainOrder) throws IOException{
		//.read .ready .close
		try {
            File input = new File(fileName);
            Scanner buffy = new Scanner(new FileReader(input));
			
			ArrayList<String> current = new ArrayList<String>();

			for (int x = 0; x < chainOrder; x++)
			{
				String test = buffy.next();
				current.add(test);
			}

            while (buffy.hasNext()) {
				String nextWord =  buffy.next();
				String key = "";
				for (String x : current)
				{
					key += x;
				}
                if (!andrew.containsKey(key))
				{
					System.out.println(key);
                	andrew.put(key, new ArrayList<String>());
				}
            	andrew.get(key).add(nextWord);

				current.remove(0);
				ArrayList<String> temp = current;
				current = new ArrayList<String>();
				for (String x : temp)
				{
					current.add(x);
				}
				current.add(nextWord);
			
            }
        buffy.close();
        } catch (FileNotFoundException e) {
			System.out.print("oops no that did not work");
        }

	}

	
	public void generateText(String outputFileName, int numChars, String inputString) throws FileNotFoundException {
		// for (Map.Entry<String, ArrayList<String>> entry : andrew.entrySet()) {
		// 	System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
		// }
		String key = "";
		String currentString = inputString;
		
		String[] balls = currentString.split(" ");
		ArrayList<Integer> lengths = new ArrayList<Integer>();
		for (String ball : balls)
		{
			key += ball;
			lengths.add(ball.length());
		}
		PrintWriter pw = new PrintWriter(new File(outputFileName));
		pw.print(inputString + " ");

		for (int i = 0; i < numChars;i++)
        {
			
            int index = (int)(Math.random() * andrew.get(key).size());
			String nextChar = andrew.get(key).get(index);

            key = key.substring(lengths.get(0));
			lengths.remove(0);
			lengths.add(nextChar.length());
			key += nextChar;
            pw.print(nextChar + " ");
            
        }

		pw.close();
	}
}
