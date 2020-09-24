float spaceBetweenLines = 23f; // Pixlar i varje koordinat. 0.0 är 0 och 1 är 23x23 XY

void setup() // main
{
// Setup för hela programmet - storlek på skärm - pensel- tjocklek
  size(640, 460); // skärmens storlek
  stroke(128, 128, 128, 255); // Färg på penseldrag
  strokeWeight(2f); // Float - tjocklek på pennan

}
// Ritar ut grafiken - kör den här koden - ritar bakgrund varje frame & kurvor varje frame
void draw () 
{
  background(255, 255, 255); // Bakgrund RGB
  DrawCurve(); // Kör denna i draw varje frame

}

void DrawCurve ()
{
  int maxLines = 23;
  for (int i = 0; i < maxLines; i++) {
    float x1 = 0;
    float y1 = i; // stegrade för varje strek - ökar för varje strek - en punkt ner för varje punkt
    float x2 = i + 1;
    float y2 = maxLines;
    line(x1 * spaceBetweenLines, y1 * spaceBetweenLines, x2 * spaceBetweenLines, y2 * spaceBetweenLines); // större mellanrum 23x23
    
  }


}