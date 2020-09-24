PVector ballPosition = new PVector(320,240); // pos på mus
PVector between = new PVector(); // räknar avståndet mellan strecken och bollen
PVector speed = new PVector(0, 0); // hastighet på bollen
float ballWidth = 50;
float ballHeight = 50;
// Setup körs bara 1 gång - när programmet drar igång
void setup() 
{
	size(640, 480); // Storlek i fönstret
	
}


void draw()
{ // en gång varje frame
	background(200, 200, 100); // Färg på bakgrunden
	stroke(100, 100, 150); // Färg på penseldragen
	strokeWeight(2.5); // Tjocklek på penseln
	ellipse(ballPosition.x, ballPosition.y, ballWidth, ballHeight); // X & Y mittpunkten av cirkeln. 50,50 width och height av cirkeln 
	if (mousePressed) { // Om vänster aktiveras händer = 

		line(ballPosition.x, ballPosition.y, pmouseX, pmouseY);// Linje som visar nästa drag för bollen
		
	}
	move(); // kallar på Move 
	collision(); // kallar på Collision
}

void collision()
{
	if (ballPosition.x < ballWidth / 2) { // Bollens x-koordinat är nära vänster vägg till hälften av dess storlek

		speed.x = speed.x * -1;
	}

	if (ballPosition.y < ballHeight / 2) { // Bollens y-koordinat är nära taket till hälften av dess storlek

		speed.y = speed.y * -1;
		
	}
	if (ballPosition.x >= width - ballWidth / 2) { // Bollen x-koordinat / Radie

		speed.x = speed.x * -1;
		
	}
	if (ballPosition.y >= height - ballHeight / 2) { // Bollen y-koordinat / Radie

		speed.y = speed.y * -1;
		
	}
}

void mouseReleased()
{
	between.set(mouseX - ballPosition.x, mouseY - ballPosition.y); // Set skriver över värdena på Vectorn. Räknar ut värdet på min Vector. 
	println("between: "+between); // Ren data typ / string och kan ej kombinera skriver ut texten
	speed.set(between);// Tredje Vector sätter den till between
	speed.mult(0.08); // Modifierar mina värden - annars för hög hastighet .
}

void move()
{
	ballPosition.x = ballPosition.x + speed.x; // Räknar ut bollens hastighet i X-axel
	ballPosition.y = ballPosition.y + speed.y; // Räknar ut bollens hastighet i Y-axel
}
