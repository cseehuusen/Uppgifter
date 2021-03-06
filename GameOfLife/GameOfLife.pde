GameObject[][] cells;    //Our game grid matrix
GameObject[][] buffer;
float cellSize = 10;    //Size of our cells
int numberOfColums;
int numberOfRows;
int fillPercentage = 15;  //Number of starting dots

void setup() 
{
	size(1000, 1000);    //Set game Size
	ellipseMode(LEFT);  //set correct mode for our dots (so they stay in frame)
	frameRate(4);    //Low framerate makes it easier to test.

	//Calculate our grid depending on size and cellSize
	numberOfColums = (int)Math.floor(width/cellSize);
	numberOfRows = (int)Math.floor(height/cellSize);

	//Initiate our matrix
	cells = new GameObject[numberOfColums][numberOfRows];
	buffer = new GameObject[numberOfColums][numberOfRows];

	//For each row
	for (int y = 0; y < numberOfRows; ++y) 
	{
		//for each column in each row
		for (int x = 0; x < numberOfColums; ++x) {
			//Create our game objects, multiply by cellSize for correct placement
			cells[x][y] = new GameObject(x * cellSize, y * cellSize, cellSize);

			//Random if it should be alive
			if (random(0, 101) <= fillPercentage) {
				cells[x][y].alive = true;
			}
		}
	}
}

void draw() 
{
	update();
	background(0);

	//For each row
	for (int y = 0; y < numberOfRows; ++y) {
		//for each column in each row
		for (int x = 0; x < numberOfColums; ++x) {
			//Draw current cell
			cells[x][y].draw();
		}
	}
}

void update()
{
	arrayCopy(cells, buffer); // Buffer är en kopia av cells utan att länka dem på något sätt


	for (int y = 0; y < numberOfRows; ++y) 
	{
		for (int x = 0; x < numberOfColums; ++x) 
		{
			buffer[x][y].alive = checkNeighbours(x, y);
		}
	}

	arrayCopy(buffer, cells); // Kopiera tillbaka fast andra hållet
}

boolean checkNeighbours(int x, int y)
{
	int neighbourCount = 0;

	for (int i = -1; i < 2; i++) // I börjar på - 1 och ökar med 1. -1, 0, 1. Stoppar vid 2 
	{
		for (int j = -1; j < 2; j++) 
		{
			if (i == 0 && j == 0) // 0,0 är våran position, vi behöver inte kolla oss själva
			{
				continue;
			}
			else if (y+i < 0 || x+j < 0 || y+i >= numberOfRows || x+j >= numberOfColums) // Om I eller J är större/likamed eller mindre än 0, hoppa över.
			{
				continue;
			}

			if (cells[x + j][y + i].alive == true) 
			{
				neighbourCount++;
				if (neighbourCount > 3) // Är neighbour mer än 3 - kommer den ej överleva. Det blir overpopulation för spelet och går mot grundreglerna 
				{
					return false;	
				}
			}
		}
	}

	// NeighbourCount kan bara vara mellan 0 och 3.

	if (neighbourCount < 2)
	{
		return false;	
	}

	// NeighbourCount kan bara vara mellan 2 och 3.

	else if (neighbourCount == 3) 
	{
		return true; 	
	}

	// NeighbourCount är 2.

	return cells[x][y].alive; // Denna variabel är antingen true eller false. Retunera samma värde som jag har
	// En död cell skall inte retunera, den är död..


}
