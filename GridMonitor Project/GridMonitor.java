import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;
public class GridMonitor implements GridMonitorInterface {
	private double[][] grid;
	private int rows;
	private int columns;
	
	/**
	 * 
	 * Constructs a 2d array of ints or doubles from an input file
	 * 
	 * @param filename name of the file that will be scanned, dimensions of the grid must be at the top
	 * @throws FileNotFoundException
	 */
	public GridMonitor(String filename) throws FileNotFoundException {
		
		File inputFile = new File(filename);
		Scanner file = new Scanner(inputFile);
		
		rows = file.nextInt();
		columns = file.nextInt();
		grid = new double[rows][columns];
		
		for (int i=0; i < rows; i++) {
			for (int j=0; j < columns; j++)
			{
				grid[i][j] = file.nextDouble();
			}
		}
		file.close();
	}
	
	public double[][] getBaseGrid() {
		double[][] gridBase = new double[rows][columns];
		for (int i=0; i < rows; i++) {
			for (int j=0; j < columns; j++) {
				gridBase[i][j] = grid[i][j];
			}
		}
		return gridBase;
	}
	
	public double[][] getSurroundingSumGrid() {
	
		double[][] gridSum = new double[rows][columns];
		
		if (rows == 1 && columns == 1) {
			gridSum[0][0] = grid[0][0]*4;
		}
		
		else {	
		
			for (int i=0; i < rows; i++) {
				for (int j=0; j < columns; j++) {
					
					//Checks for cells on the top or bottom
					gridSum[i][j] = 0;
					if (i == 0) {
						gridSum[i][j] += (grid[i+1][j] + grid[i][j]);
					}
					else if (i == rows-1) {
						gridSum[i][j] += (grid[i-1][j] + grid[i][j]);
					}
					else {
						gridSum[i][j] += (grid[i-1][j] + grid[i+1][j]);
					}
					
					//Checks for cells on the left or right
					if (j == 0) {
						gridSum[i][j] += (grid[i][j+1] + grid[i][j]);
					}
					else if (j == columns-1) {
						gridSum[i][j] += (grid[i][j-1] + grid[i][j]);
					}
					else {
						gridSum[i][j] += (grid[i][j+1] + grid[i][j-1]);
					}					
				}
			}
		}
		return gridSum;			
	}
	
	public double[][] getSurroundingAvgGrid() {
		double[][] gridSum = getSurroundingSumGrid();
		double[][] gridAvg = new double[rows][columns];
		
		for (int i=0; i < rows; i++) {
			for (int j=0; j<columns; j++) {
				gridAvg[i][j] = gridSum[i][j]/4;
				
			}
		}
		return gridAvg;
	}
	
	public double[][] getDeltaGrid() {
		double[][] gridDelta = new double[rows][columns];
		double[][] gridAvg = getSurroundingAvgGrid();
		
		for (int i=0; i < rows; i++) {
			for (int j=0; j<columns; j++) {
				gridDelta[i][j] = gridAvg[i][j]/2;
				
				//Gets the absolute value
				if (gridAvg[i][j] < 0) {
					gridDelta[i][j] *= -1;
				}
				
			}
		}
		return gridDelta;
	}
	
	public boolean[][] getDangerGrid() {
		boolean[][] gridDanger = new boolean[rows][columns];
		double[][] gridAvg = getSurroundingAvgGrid();
		double[][] gridDelta = getDeltaGrid();
		
		for (int i=0; i < rows; i++) {
			for (int j=0; j < columns; j++) {
				if (grid[i][j] > (gridAvg[i][j] + gridDelta[i][j]) || grid[i][j] < (gridAvg[i][j] - gridDelta[i][j])) {
					gridDanger[i][j] = true;
				}
				
				else {
					gridDanger[i][j] = false;
				}
			}
		}
		return gridDanger;
	}
	
	/**
	 * Returns a string including the input grid, safe ranges for the values, a grid showing which
	 * cells are in danger, and the coordinates of the cells in danger
	 * 
	 * @return String showing relevant information for the grid
	 */
	
	public String toString() {
		String str = "";
		double[][] gridAvg = getSurroundingAvgGrid();
		double[][] gridDelta = getDeltaGrid();
		boolean[][] gridDanger = getDangerGrid();
		
		
		//Prints important grids
		str += "Base Grid: \n";
		for (int i=0; i < rows; i++) {
			for (int j=0; j < columns; j++) {
				str += (" " + grid[i][j] + "\t");
			}
			str += "\n";
		}
		
		//Uses the average + or - the delta to display safe ranges
		str += "Safe Ranges:\n";
		
		for (int i=0; i < rows; i++) {
			for (int j=0; j < columns; j++) {
				str += (" " + (gridAvg[i][j]-gridDelta[i][j]) + "-" + (gridAvg[i][j]+gridDelta[i][j]) + "\t");
			}
			str += "\n";
		}
		
		str += "Danger Grid:\n";
		for (int i=0; i < rows; i++) {
			for (int j=0; j < columns; j++) {
				str += (" " + gridDanger[i][j] + "\t");
			}
			str += "\n";
		}
		
		
		//Print coordinates of cells in danger by finding true values
		str += "Cells in Danger:\n";
		int x = 0;
		for (int i=0; i < rows; i++) {
			for (int j=0; j < columns; j++) {
				if (gridDanger[i][j] == true) {
					x++;
					str += ("(" + i + "," + j + ") ");
				}
			}
		}
		if ( x == 0) {
			str += "No Cells Are In Danger";
		}
		
		return str;	
		
	}
	
}
