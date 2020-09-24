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
  void move()
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

  float calculateDeaccelerationDirection (float v) 
  { // Kallas på alla axlar - plockas på moveTowards, tas både på X och Y. 
    if (v < 0) {
      return -1; // Byta plats på 109 & 113 kommer bli en konstant accelration, aldrig en deacceleration.
    } else
    {
      return 1;
    }
  }

  float moveTowards(float currSpeed, float targetSpeed, float maxStep) { // 
    if (abs(targetSpeed - currSpeed) <= maxStep)
      return targetSpeed;

    return currSpeed + maxStep * calculateDeaccelerationDirection(targetSpeed - currSpeed); // Ränkar ut ett värde från vart jag skall deaccelerera
  }

  void display()
  {
    fill(200, 200, 200);
    ellipse(positionx, positiony, playerWidth, playerHeight);
  }

  void playerCollision()
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
