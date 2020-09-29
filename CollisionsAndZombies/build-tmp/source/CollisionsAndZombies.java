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

public class CollisionsAndZombies extends PApplet {

CharacterManager characterManager;
boolean gameover = false;

public void setup()
{
   // Setup
  init();
}

public void draw() {
  background(100, 100, 240); // Bakgrund
  UpdateDeltaTime();

  characterManager.update();
  characterManager.draw();

  
}


public void init()
{
  characterManager = new CharacterManager(99, 1);
  startTime = millis();
}

class Character
{
    //Our class variables
    PVector position; //character position
    PVector velocity; //character direction
    float characterHeight;
    float characterWidth;
    float characterRadius;
    int characterColor;

    // Human Constructor
    Character(float characterHeight, float characterWidth, int characterColor)
    {
        position = new PVector(random(0, width), random(0, height));
        velocity = new PVector();
        velocity.x = random(100, 200);
        velocity.y = random(100, 200);
        this.characterHeight = characterHeight;
        this.characterWidth = characterWidth;
        this.characterColor = characterColor;
        this.characterRadius = characterWidth / 2;
    }

    //Zombie Constructor
    Character(float x, float y, float characterHeight, float characterWidth, int characterColor)
    {
        //Set our position when we create the code.
        position = new PVector(x, y);

        //Create the velocity vector and give it a random direction.
        velocity = new PVector();
        velocity.x = random(50, 60);
        velocity.y = random(50 ,60);
        this.characterHeight = characterHeight;
        this.characterWidth = characterWidth;
        this.characterColor = characterColor;
        this.characterRadius = characterWidth / 2;

    }

    //Update our character
    public void update()
    {
        PVector move = velocity.copy();
        move.mult(deltaTime);
        position.add(move);
        ScreenWrap();
    }

    public void draw()
    {
        fill(characterColor);
        ellipse(position.x, position.y, characterHeight, characterWidth);
    }

    public void ScreenWrap ()
    {
        if (position.x < -characterRadius)
            position.x += width + characterWidth;
        else if (position.x > width + characterRadius)
            position.x -= width + characterWidth;

        if (position.y < -characterRadius)
            position.y += height + characterHeight;
        else if (position.y > height + characterRadius)
            position.y -= height + characterHeight;
    }

    public void characterColision()
    {
        if (position.x > width || position.x < 0) 
        {
            velocity.x *= -1; // As soon the character reaches the screen edge - it bounces on the right / left
        }
        if (position.y > height || position.y < 0) 
        {
            velocity.y *= -1;
        }
        
    }

    public boolean foundHuman(Character other)
{
  float maxDistance = characterRadius + other.characterRadius;

  if(abs(position.x - other.position.x) > maxDistance || abs(position.y - other.position.y) > maxDistance)
  {
    return false;
  }
  
  else if(PVector.dist(position, other.position) > maxDistance)
  {
    return false;
  }
  
  else
  {
   return true;
  }
}
}
public class CharacterManager

{
    Character[] characters;
    int humansAlive;
    CharacterManager(int numberofHumans, int numberofZombies)
    {
        characters = new Character[numberofHumans + numberofZombies];
        for (int i = 0; i < numberofHumans; i++) 
        {
            characters[i] = new Human(40, 40, color(random(120, 255),0, 0)); 
        }

        for (int i = numberofHumans; i < characters.length; i++) 
        {
            characters[i] = new Zombie(width / 2, height / 2, 40, 40, color(0, random(120, 255),0)); 
        }

        humansAlive = numberofHumans;

    }

    public void draw()
        {

        for (int i = 0; i < characters.length; i++) 
        {
            characters[i].draw();
        }
        if (humansAlive <= 0) 
        {
            textSize(32);
            textAlign(CENTER); 
            fill(255);
            text ("GAME OVER\nTOTAL TIME: " + endTime, width / 2, height / 2 );

        }
    }

    public void update()
    {
        for (int i = 0; i < characters.length; i++) 
        {
            characters[i].update();
        }
    }

    public void humanGotKilled()
        {
            humansAlive -= 1;
            if (humansAlive <= 0) 
            {
                endTime = (millis() - startTime) * 0.001f;
            }
        }

}
public class Human extends Character 
{
	
	public Human (float characterHeight, float characterWidth, int characterColor) 
	{
		super(characterHeight, characterWidth, characterColor);
	}

}
boolean resetButtonDown = false;

public void keyPressed()
{
  if (resetButtonDown == false && key == 'r')
  	{
    resetButtonDown = true;
	init();

	}
}

public void keyReleased()
{
	if (key == 'r') 
	resetButtonDown = false;
}
float deltaTime;
long time;
float startTime;
float endTime;



public void UpdateDeltaTime()

	{
		long currentTime = millis(); 
		deltaTime = (currentTime - time) * 0.001f;
		time = currentTime;
	}
public class Zombie extends Character 
{

	public Zombie (float x, float y, float characterHeight, float characterWidth, int characterColor) 
	{
		super(x, y, characterHeight, characterWidth, characterColor);
	}

	public void update()
	{
		super.update();
		lookingForHuman();
		
	}

	public void lookingForHuman()
	{
		Character[] characters = characterManager.characters;
		for (int i = 0; i < characters.length; i++) 
		{
			if (characters[i] instanceof Human) 
			{
				if (foundHuman(characters[i]))
				{
					Zombie newZombie = new Zombie(characters[i].position.x, characters[i].position.y, characters[i].characterHeight, characters[i].characterWidth, color(0, random(120, 255),0));
					characters[i] = newZombie;
					characterManager.humanGotKilled();
				}
			}
		}
	}


}
  public void settings() {  size(1000, 1000); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "CollisionsAndZombies" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
