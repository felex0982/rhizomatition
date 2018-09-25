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
  color c = color(0,0,0,100);
  
  Branch(){
    size = 20;
    mass = 55;
    maxspeed = 1;
    maxforce = 0.2;
    theta = random(0, PI*2); // angle
    position = new PVector(0,0);
    velocity = new PVector(1*cos(theta),1*sin(theta));
    velocity.limit(maxspeed);
    acceleration = new PVector(0,0);
    direction = velocity.copy();
  }
  
  Branch(PVector pos,PVector vel, PVector dir, color co){
    size = 20;
    mass = 55;
    maxspeed = 1;
    maxforce = 0.2;
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
  
  void move(){
    mappings();
    PVector ste = steering();
    PVector wob = wobble();
    
    ste.mult(swt);
    wob.mult(wwt);
    
    applyForce(ste);
    applyForce(wob);
  }
  
  void applyForce(PVector force){
    PVector f = PVector.div(force,mass);
    acceleration.add(f);
  }
  
  void mappings(){
    float distance = position.dist(new PVector(0,0));
    if(frameCount<600){
      size = map(frameCount, 0, 800, 4, 1);
    }
    else if(frameCount<1000){
      size = 1;
    } else{ size = 0;}
    
    wwt = map(distance, 0, height/3, 3, 20);
  }
  
  void update(){
    velocity.add(acceleration);
    position.add(velocity);
    velocity.limit(maxspeed);
    acceleration.mult(0);
  }
  
    void display(){
    pushMatrix();
    translate(position.x,position.y);
    fill(c);
    noStroke();
    ellipse(0,0,size,size);
    popMatrix();
  }
  
  PVector steering(){
    direction.setMag(maxspeed);
    PVector steer = PVector.sub(direction,velocity);
    steer.limit(maxforce);
    return steer;
  }
  
  PVector wobble(){
    PVector wobble = PVector.random2D();
    PVector steer = PVector.sub(wobble,velocity);
    steer.limit(maxforce);
    return steer;
  }

}
