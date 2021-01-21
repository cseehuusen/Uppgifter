int frame = 0;
float multiplier = 0.002;
int numberOfPoints = 100;


void setup()
{
  size(1000, 1000);
  strokeWeight(2);
}

void draw()
{
  background(0);
  sinCurve();
  cosCurve();
  circleCurve();
  spiralCurve();
  frame++;
} 

void sinCurve()
{
  stroke(255, 250, 100);

    for (int i = 0; i < numberOfPoints; ++i)
    {
      point(i * 10, height / 2 + sin(frame * 0.025  + i * 0.044) * 251);
    }
}

void cosCurve()
{
  stroke(18, 248, 0);
  scale(1, 1);

  translate(width, height);
  rotate(PI/1);
    for (int i = 0; i < numberOfPoints; ++i)
    {
      point(i * 10, height/2  + cos(frame * 0.026  + i * 0.044) * 251);
    }   
}

void circleCurve()
{
  stroke(252, 2, 255);
  
  float centerX = width / -1;
  float centerY = height / 2;
  float radius = 142;
  float angle = 5;
  float slice = PI * 39 / numberOfPoints;
  
  translate(width / 2, height / 2);
  
  for (int i = 0; i < numberOfPoints; ++i)
  {
  angle = i * slice + frame / 10f;
  centerX = cos(angle) * radius * ((float) i / numberOfPoints);
  centerY = sin(angle) * radius * ((float) i/ numberOfPoints);
  point(centerX, centerY);
  }
}

void spiralCurve()
{
  stroke(233, 234, 232);
  scale(0.2, 0.1);
 
for (int i = 0; i < numberOfPoints; ++i) {
  rotate(frame / 1000f);
  scale(1.01);
  line(6, 12, -43, 44);
}
}
