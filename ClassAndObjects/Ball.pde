class Ball
{
    //Our class variables
    PVector position; //Ball position
    PVector velocity; //Ball direction
    float ballHeight;
    float ballWidth;
    color ballColor;

    //Ball Constructor, called when we type new Ball(x, y);
    Ball(float x, float y, float ballHeight, float ballWidth, color ballColor)
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
    void update()
    {
        position.x += velocity.x;
        position.y += velocity.y;
        ballColision();
    }

    void draw()
    {
        fill(ballColor);
        ellipse(position.x, position.y, ballHeight, ballWidth);
    }

    void ballColision()
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