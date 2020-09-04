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

public class Mittnamn extends PApplet {

public void setup()
{
    
}
public void draw()
{
  background(34, 171, 211);
  strokeWeight(2.5f);
  
  //  "C"
  line(175, 196, 175, 134);
  line(177, 196, 212, 196);
  line(175, 133, 212, 133);
  
  // "H"
  line(230, 197, 229, 134);
  line(268, 197, 267, 134);
  line(266, 167, 231, 167);
  
  // "R"
  line(289, 198, 288, 134);
  line(327, 134, 288, 134);
  line(327, 159, 327, 135);
  line(327, 161, 290, 161);
  line(289, 162, 326, 198);
  
  // "I"
  line(350, 198, 349, 135);
  
  // "S"
  line(377, 134, 410, 134);
  line(376, 135, 376, 161);
  line(410, 163, 377, 163);
  line(411, 196, 410, 164);
  line(410, 196, 379, 196);
  

}
  public void settings() {  size(768, 432); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Mittnamn" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
