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

public class InputAndMotion extends PApplet {

float x = 0;
float y = 0;
float speedX = 0;
float speedY = 0;
float currentSpeed = 0;
float maxSpeed = 4;
float acceleration = 10;
float deAcceleration = 3;
float deltaTime;
long time;
boolean moveLeft;
boolean moveRight;
boolean moveUp;
boolean moveDown;
int velocity = 5;


public void setup()
{
   // Setup 
  x = width/2;
  y = height/2;
}

public void draw() {
	background(50, 166, 240); // Bakgrund
	println("speedX: " + speedX + " speedY: " + speedY); // printa speed x och speedy på skräm
	long currentTime = millis(); // CurrentTime är  Får in tiden i form av millis, sekunder istället för frames.
	deltaTime = (currentTime - time) * 0.001f; // CurrentTime är 0 - gånger med 0 - 0.001 för millesekunder

  if (moveLeft) {
  	speedX = speedX - deltaTime * acceleration; // Första sekunden 0.001 - allts står still - räknar ut accelartionen på X-axeln.
    
  }
  else if (moveRight) {
  	speedX = speedX + deltaTime *  acceleration; // Samma fast positivt - bygger för höger
    
  }
  else if (speedX != 0) {
  	speedX = moveTowards(speedX, 0, deltaTime *  deAcceleration); // 
  }
  if (moveUp) {
  	speedY = speedY - deltaTime *  acceleration;
  	
  }
  else if (moveDown) {
  	speedY = speedY + deltaTime *  acceleration;
  	
  }
  else if (speedY != 0) {
  	speedY = moveTowards(speedY, 0, deltaTime *  deAcceleration);

  }
  	if (speedX > maxSpeed) { // Right speed
  		speedX = maxSpeed;
  	}

  	else if (speedX < -maxSpeed) { // Left speed
  		speedX = -maxSpeed;
  	}

  	if (speedY > maxSpeed) { // Up speed
  		speedY = maxSpeed;
  	}

  	else if (speedY < -maxSpeed) { // Down speed
  		speedY = -maxSpeed;
  	}

  	x += speedX;
  	y += speedY;
	ellipse(x, y, 40, 40);
	time = currentTime;

}
//Key pressed, set our variables to true
public void keyPressed()
{ println("bög: ");
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

public float calculateDeaccelerationDirection (float v) 
{ // Kallas på alla axlar - plockas på moveTowards, tas både på X och Y. 
	if (v < 0) {
		return -1; // Byta plats på 109 & 113 kommer bli en konstant accelration, aldrig en deacceleration.
	}
	else
	{
		return 1;
	}

}

public float moveTowards(float currSpeed, float targetSpeed, float maxStep) { // 
    if (abs(targetSpeed - currSpeed) <= maxStep)
        return targetSpeed;

    return currSpeed + maxStep * calculateDeaccelerationDirection(targetSpeed - currSpeed); // Ränkar ut ett värde från vart jag skall deaccelerera
}
  public void settings() {  size(640,480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "InputAndMotion" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
