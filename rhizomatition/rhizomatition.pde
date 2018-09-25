/*
* RHIZOMATITION
* This Programm is algorithmically exploring the visual quality of a root.
* It starts from the center and organically grows from a tree structure into
* a non hirachical rhizomatic structure.
* The underlying idea is based on a particle system. Each and every root is
* represented by a particle in the code which is behaving to the principle of
* Craig Raynolds steering behaviours for autonomous characters.
* Starting with twenty roots the system can easely go up to over 3000 roots till
* they loose their origin and become one again.
* 
* 22nd May 2018 - FW
*/

Tree tree;

void setup(){
  //size(8000,4000,P2D);
  fullScreen(P2D);
  smooth(3);
  background(255);
  
  tree = new Tree();
  
  for (int i = 0; i < 10; i++) {
   tree.addBranch(new Branch());
  }
  

}

void draw(){
  tree.run();
  println(frameCount);
}

void mouseClicked()
{
  tree.fork();
}

void keyPressed(){
    if(key=='s'||key=='S')
            saveFrame("output/circle_5_####.png"); 
}
