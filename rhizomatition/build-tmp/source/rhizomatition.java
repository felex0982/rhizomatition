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

public class rhizomatition extends PApplet {

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

public void setup(){
  //size(8000,4000,P2D);
  
  
  background(255);
  
  tree = new Tree();
  
  for (int i = 0; i < 10; i++) {
   tree.addBranch(new Branch());
  }
  

}

public void draw(){
  tree.run();
  println(frameCount);
}

public void mouseClicked()
{
  tree.fork();
}

public void keyPressed(){
    if(key=='s'||key=='S')
            saveFrame("output/circle_5_####.png"); 
}
float swt = 3;
float wwt = 5;

class Branch {
  PVector position;
  PVector velocity;
  PVector acceleration;
  float size;
  float mass;
  float maxspeed;
  float maxforce;
  float theta;
  PVector direction;
  int c = color(0,0,0,100);
  
  Branch(){
    size = 20;
    mass = 55;
    maxspeed = 1;
    maxforce = 0.2f;
    theta = random(0, PI*2); // angle
    position = new PVector(0,0);
    velocity = new PVector(1*cos(theta),1*sin(theta));
    velocity.limit(maxspeed);
    acceleration = new PVector(0,0);
    direction = velocity.copy();
  }
  
  Branch(PVector pos,PVector vel, PVector dir, int co){
    size = 20;
    mass = 55;
    maxspeed = 1;
    maxforce = 0.2f;
    position = pos;
    velocity = vel;
    velocity.limit(maxspeed);
    acceleration = new PVector(0,0); //<>//
    direction = dir;
    c = co;
  }
  
  public void run(){
    move();
    update();
    pushMatrix();
    translate(width/2,height/2);
    display();
    popMatrix();
  }
  
  public void move(){
    mappings();
    PVector ste = steering();
    PVector wob = wobble();
    
    ste.mult(swt);
    wob.mult(wwt);
    
    applyForce(ste);
    applyForce(wob);
  }
  
  public void applyForce(PVector force){
    PVector f = PVector.div(force,mass);
    acceleration.add(f);
  }
  
  public void mappings(){
    float distance = position.dist(new PVector(0,0));
    if(frameCount<600){
      size = map(frameCount, 0, 800, 4, 1);
    }
    else if(frameCount<1000){
      size = 1;
    } else{ size = 0;}
    
    wwt = map(distance, 0, height/3, 3, 20);
  }
  
  public void update(){
    velocity.add(acceleration);
    position.add(velocity);
    velocity.limit(maxspeed);
    acceleration.mult(0);
  }
  
    public void display(){
    pushMatrix();
    translate(position.x,position.y);
    fill(c);
    noStroke();
    ellipse(0,0,size,size);
    popMatrix();
  }
  
  public PVector steering(){
    direction.setMag(maxspeed);
    PVector steer = PVector.sub(direction,velocity);
    steer.limit(maxforce);
    return steer;
  }
  
  public PVector wobble(){
    PVector wobble = PVector.random2D();
    PVector steer = PVector.sub(wobble,velocity);
    steer.limit(maxforce);
    return steer;
  }

}
class Tree{
  ArrayList<Branch> branches;
  float splitAngle = 0.3f;

  Tree(){
    branches = new ArrayList<Branch>();
  }
  
  public void run(){
    for(Branch b : branches){
      b.run();
    }
  }
  
  public void addBranch(Branch b){
    branches.add(b);
  }
  
  public void fork(){
    ArrayList<Branch> newBranches = new ArrayList<Branch>();
    for(int i = branches.size() - 1; i >= 0; i--){
      Branch oldBranch = branches.get(i);
      PVector pos1 = new PVector();
      pos1 = oldBranch.position.copy();
      PVector vel1 = new PVector();
      vel1 = oldBranch.velocity.copy();
      PVector rot1 = new PVector();
      rot1 = vel1.copy();
      newBranches.add(new Branch(pos1,vel1,rot1.rotate(splitAngle), color(0,0,0,150))); //<>//
      PVector pos2 = new PVector();
      pos2 = oldBranch.position.copy();
      PVector vel2 = new PVector();
      vel2 = oldBranch.velocity.copy();
      PVector rot2 = new PVector();
      rot2 = vel2.copy();
      newBranches.add(new Branch(pos2,vel2,rot2.rotate(-splitAngle), color(0,0,0,150)));
      branches.remove(i);
    }
    for(Branch b : newBranches){
      branches.add(b);
    }
  }
}
  public void settings() {  fullScreen(P2D);  smooth(3); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "rhizomatition" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
