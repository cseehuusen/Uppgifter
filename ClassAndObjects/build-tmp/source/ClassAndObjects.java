import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ClassAndObjects extends PApplet {

float deltaTime;
long time;
Player player;
Ball[] enemies;
int enemiesCount = 10;
boolean gameover = false;


public void setup()
{
   // Setup
  player = new Player(); // Variabel som är player, skapar nytt objekt för player.
  enemies = new Ball[enemiesCount];
  for (int i = 0; i < enemiesCount; ++i)
  {
  	enemies[i] = new Ball(50, 50, 20, 20, color(random(0, 255), random(0, 255), random (0, 255)));

  }
  
}

public void draw() {
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

 public boolean roundCollision(float x1, float y1, float size1, float x2, float y2, float size2)
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
class Ball
{
    //Our class variables
    PVector position; //Ball position
    PVector velocity; //Ball direction
    float ballHeight;
    float ballWidth;
    int ballColor;

    //Ball Constructor, called when we type new Ball(x, y);
    Ball(float x, float y, float ballHeight, float ballWidth, int ballColor)
    {
        //Set our position when we create the code.
        position = new PVector(x, y);

        //Create the velocity vector and give it a random direction.
        velocity = new PVector();
        velocity.x = random(11) - 5;
        velocity.y = random(11) - 5;
        this.ballHeight = ballHeight;
        this.ballWidth = ballWidth;
        this.ballColor = ballColor;

    }

    //Update our ball
    public void update()
    {
        position.x += velocity.x;
        position.y += velocity.y;
        ballColision();
    }

    public void draw()
    {
        fill(ballColor);
        ellipse(position.x, position.y, ballHeight, ballWidth);
    }

    public void ballColision()
    {
        if (position.x > width || position.x < 0) 
        {
            velocity.x *= -1; // As soon the ball reaches the screen edge - it bounces on the right / left
        }
        if (position.y > height || position.y < 0) 
        {
            velocity.y *= -1;
        }
        
    }
}
boolean moveLeft;
boolean moveRight;
boolean moveUp;
boolean moveDown;

public void keyPressed()
{
  if (keyCode == LEFT || key == 'a')
    moveLeft = true;
  else if (keyCode == RIGHT || key == 'd')
    moveRight = true;
  if (keyCode == UP || key == 'w') 
    moveUp = true;
  else if (keyCode == DOWN || key == 's')
    moveDown = true;
}


//When a key is released, we will set our variable to false
public void keyReleased()
{
  if (keyCode == LEFT || key == 'a')
    moveLeft = false;
  else if (keyCode == RIGHT || key == 'd')
    moveRight = false;
  if (keyCode == UP || key == 'w')
    moveUp = false;
  else if (keyCode == DOWN || key == 's')
    moveDown = false;
}

class Player
{
  float positionx = 0;
  float positiony = 0;
  float speedX = 0;
  float speedY = 0;
  float currentSpeed = 0;
  float maxSpeed = 4;
  float acceleration = 10;
  float deAcceleration = 3;
  float playerWidth = 40;
  float playerHeight = 40;


  // Constructor - varje gång en spelare skapas körs den här 1 gång.
  Player() 
  {
    positionx = width/2;
    positiony = height/2;
  }
  public void move()
  {
    playerCollision();

    if (moveLeft) {
      speedX = speedX - deltaTime * acceleration; // Första sekunden 0.001 - allts står still - räknar ut accelartionen på X-axeln.
    } else if (moveRight) {
      speedX = speedX + deltaTime *  acceleration; // Samma fast positivt - bygger för höger
    } else if (speedX != 0) {
      speedX = moveTowards(speedX, 0, deltaTime *  deAcceleration); //
    }
    if (moveUp) {
      speedY = speedY - deltaTime *  acceleration;
    } else if (moveDown) {
      speedY = speedY + deltaTime *  acceleration;
    } else if (speedY != 0) {
      speedY = moveTowards(speedY, 0, deltaTime *  deAcceleration);
    }
    if (speedX > maxSpeed) { // Right speed
      speedX = maxSpeed;
    } else if (speedX < -maxSpeed) { // Left speed
      speedX = -maxSpeed;
    }

    if (speedY > maxSpeed) { // Up speed
      speedY = maxSpeed;
    } else if (speedY < -maxSpeed) { // Down speed
      speedY = -maxSpeed;
    }

    positionx += speedX;
    positiony += speedY;
  }

  public float calculateDeaccelerationDirection (float v) 
  { // Kallas på alla axlar - plockas på moveTowards, tas både på X och Y. 
    if (v < 0) {
      return -1; // Byta plats på 109 & 113 kommer bli en konstant accelration, aldrig en deacceleration.
    } else
    {
      return 1;
    }
  }

  public float moveTowards(float currSpeed, float targetSpeed, float maxStep) { // 
    if (abs(targetSpeed - currSpeed) <= maxStep)
      return targetSpeed;

    return currSpeed + maxStep * calculateDeaccelerationDirection(targetSpeed - currSpeed); // Ränkar ut ett värde från vart jag skall deaccelerera
  }

  public void display()
  {
    fill(200, 200, 200);
    ellipse(positionx, positiony, playerWidth, playerHeight);
  }

  public void playerCollision()
  {
    if (positionx + playerWidth / 2 > width) 
    {
      positionx = width - playerWidth / 2; // As soon the ball reaches the screen edge - it bounces on the right / left
    } 
    else if (positionx - playerWidth / 2 < 0)
    {
      positionx = playerWidth / 2;
    }
    if (positiony + playerHeight / 2 > height) 
    {
      positiony = height - playerHeight / 2 ;
    } 
    else if (positiony - playerHeight / 2 < 0) 
    {
      positiony = playerHeight / 2;
    }
  }

  
}
  public void settings() {  size(640, 480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ClassAndObjects" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
