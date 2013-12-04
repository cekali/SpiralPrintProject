package spiralPrint;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpiralPrint
{
	private Integer[][] array;
	private static String RANGE = "between 0 and 2147483647: ";
	
	public SpiralPrint(int max)
	{
		fillByCorners(max);
	}

	public static void main(String... args)
	{
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter an integer " + RANGE);
		
		Integer inputValue = null;
		while(inputValue == null)
		{
			if(!scanner.hasNextInt())
			{
				scanner.nextLine();
				System.out.print("Please enter an integer " + RANGE);
				continue;
			}
			
			int temp = scanner.nextInt();
			if(temp < 0)
			{
				scanner.nextLine();
				System.out.print("Please enter a POSITIVE integer " + RANGE);
				continue;
			}
			
			inputValue = temp;
		}
		scanner.close();
		
		SpiralPrint printer = new SpiralPrint(inputValue);
		
		Integer[][] array = printer.getArray();
		
		print(array, inputValue);
	}
	
	private List<Integer> getCornerValues(int max)
	{
		List<Integer> corners = new ArrayList<Integer>();
		int currentCorner = 0;
		int sidelength = 0;
		while (currentCorner < max)
		{
			sidelength += 2;
			for (int i = 0; i < 4 && currentCorner < max; i++)
			{
				currentCorner += sidelength;
				if (currentCorner > max)
				{
					corners.add(max);
					break;
				}
				else
				{
					corners.add(currentCorner);
				}
			}
		}
		
		return corners;
	}

	private void fillByCorners(int inputValue)
	{
		List<Integer> corners = getCornerValues(inputValue);

		int squareSize = getMaxSideLength(inputValue);
		int maxValueForSquare = getMaxValueForSquare(squareSize);
		int rows = getNumberOfRows(inputValue, maxValueForSquare, squareSize);
		int columns = getNumberOfColumns(inputValue, maxValueForSquare, squareSize);

		Integer[][] array = new Integer[rows][columns];
		
		int row = getInitialRow(rows);
		int col = getInitialCol(columns);
		
		array[row][col-1] = 0;
		
		int currentValue = 1;
		for (int i = 0; i < corners.size();)
		{
			// down
			while (hasNextCorner(corners, i, currentValue))
			{
				array[row++][col] = currentValue++;
			}
			row--;// iterated one too far
			col--;// start in previous column
			i++;
			// left
			while (hasNextCorner(corners, i, currentValue))
			{
				array[row][col--] = currentValue++;
			}
			col++;// iterated one too far
			row--;// start in previous row
			i++;
			// up
			while (hasNextCorner(corners, i, currentValue))
			{
				array[row--][col] = currentValue++;
			}
			row++;// iterated one too far
			col++;// start in next column
			i++;
			// right
			while (hasNextCorner(corners, i, currentValue))
			{
				array[row][col++] = currentValue++;
			}
			// no need to update row and col, square is complete
			i++;
		}
		
		this.array = array;
	}

	private boolean hasNextCorner(List<Integer> corners, int i, int value)
	{
		return corners.size() - 1 >= i && value <= corners.get(i);
	}

	private static void print(Integer[][] array, int max)
	{
		int digits = String.valueOf(max).length() + 1;
		for (Integer[] row : array)
		{
			for(Integer element : row)
			{
				if(element == null)
				{
					System.out.printf("%" + digits + "s", "");
				}
				else
				{
					System.out.printf("%" + digits + "d", element);
				}
			}
			System.out.println();
		}
	}
	
	private int getMaxSideLength(int max)
	{
		return (int) Math.ceil(Math.sqrt(max + 1));
	}
	
	private int getMaxValueForSquare(int squareSize)
	{
		return squareSize * squareSize - 1;
	}
	
	private int getNumberOfRows(int inputValue, int maxValueForSquare, int squareSize)
	{
		int rows = squareSize;
		
		if (inputValue <= maxValueForSquare - squareSize)
		{
			rows = squareSize - 1;
		}
		
		return rows;
	}
	
	private int getNumberOfColumns(int inputValue, int maxValueForSquare, int squareSize)
	{
		int columns = squareSize;
		
		if (inputValue <= maxValueForSquare - squareSize - squareSize + 1)
		{
			columns = squareSize - 1;
		}
		
		return columns;
	}
	
	private int getInitialRow(int rows)
	{
		return rows % 2 == 0 ? rows / 2 - 1 : rows / 2;
	}
	
	private int getInitialCol(int columns)
	{
		return columns % 2 == 0 ? columns / 2 : columns / 2 + 1;
	}

	public Integer[][] getArray()
	{
		return array;
	}
}
