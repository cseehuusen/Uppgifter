float deltaTime;
long time;
Player player;
Ball[] enemies;
int enemiesCount = 10;
boolean gameover = false;


void setup()
{
  size(640, 480); // Setup
  player = new Player(); // Variabel som är player, skapar nytt objekt för player.
  enemies = new Ball[enemiesCount];
  for (int i = 0; i < enemiesCount; ++i)
  {
  	enemies[i] = new Ball(50, 50, 20, 20, color(random(0, 255), random(0, 255), random (0, 255)));

  }
  
}

void draw() {
  background(100, 100, 240); // Bakgrund
  println("speedX: " + player.speedX + "speedY: " + player.speedY); // printa speed x och speedy på skräm
  long currentTime = millis(); // CurrentTime är  Får in tiden i form av millis, sekunder istället för frames.
  deltaTime = (currentTime - time) * 0.001f; // CurrentTime är 0 - gånger med 0 - 0.001 för millesekunder
  player.move(); // Kallar på dem från Player
  player.display(); // Kallar på dem från Player
  
  for (int i = 0; i < enemiesCount; ++i) 
  {
  	Ball enemy = enemies[i];
  	enemy.update();
  	enemy.draw();
  	if (roundCollision(enemy.position.x, enemy.position.y, enemy.ballHeight / 2, player.positionx, player.positiony, player.playerWidth / 2)) 
  	{
    		gameover = true;
  	}

  }
if (gameover) 
{
	fill(255, 255, 255);
	textSize(32);
	text("Game Over", 240, 50);	
}

  time = currentTime;
}

 boolean roundCollision(float x1, float y1, float size1, float x2, float y2, float size2)
{
  float maxDistance = size1 + size2;

  //first a quick check to see if we are too far away in x or y direction
  //if we are far away we dont collide so just return false and be done.
  if(abs(x1 - x2) > maxDistance || abs(y1 - y2) > maxDistance)
  {
    return false;
  }
  //we then run the slower distance calculation
  //dist uses Pythagoras to get exact distance, if we still are to far away we are not colliding.
  else if(dist(x1, y1, x2, y2) > maxDistance)
  {
    return false;
  }
  //We now know the points are closer then the distance so we are colliding!
  else
  {
   return true;
  }
}
//A better way to do this is to have a circle object and pass the objects in to the function,
//then we just have to pass 2 objects instead of 6 different values.