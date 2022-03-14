import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class FormatChecker {
	
	/**
	 * 
	 * Checks grid files to see if they are formatted correctly,
	 * prints results to the command line
	 * 
	 * @param args names of files to be checked for validity
	 * 
	 */
	public static void main(String[] args) {
		
		
		for (int i=0; i < args.length; i++) {
			int rows;
			int columns;
			boolean valid = true;
			
			
			System.out.println(args[i]);
			
			try {
				Scanner file = new Scanner (new File(args[i]));
				Scanner line = new Scanner (file.nextLine());
				
				rows = Integer.parseInt(line.next());
				columns = Integer.parseInt(line.next());
				
				//Checks for more than 2 numbers in the grid dimensions
				if (line.hasNext()) {
					System.out.println("Only 2 ints should be in dimensions");
					valid = false;
				}
				
				
				//Loop to test if grid numbers and dimensions are valid
				for (int j=0; j < rows; j++) {
					line = new Scanner(file.nextLine());
					
					for (int k=0; k < columns; k++) {
						
						Double.parseDouble(line.next());
					}
					if (line.hasNext()) {
						System.out.println("Too many rows or columns specified");
						valid = false;
					}
					line.close();
				}
				
				
				if (file.hasNext()) {
					System.out.println("Too many rows or columns specified");
					valid = false;
				}
				
				if (file != null) file.close();
				
				
			}
			catch (FileNotFoundException e) {
				System.out.println(e);
				valid = false;
			}
			catch (NumberFormatException e) {
				System.out.println(e);
				valid = false;
			}
			catch (NoSuchElementException e) {
				System.out.println("Not enough rows or columns specified");
				valid = false;
			}
			finally {
			}
			
			
			if (valid) {
				System.out.println("VALID");
			}
			else {
				System.out.println("INVALID");
			}
			
			System.out.println();
			
		}
			
	}
	
}