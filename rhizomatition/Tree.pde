class Tree{
  ArrayList<Branch> branches;
  float splitAngle = 0.3;

  Tree(){
    branches = new ArrayList<Branch>();
  }
  
  public void run(){
    for(Branch b : branches){
      b.run();
    }
  }
  
  void addBranch(Branch b){
    branches.add(b);
  }
  
  void fork(){
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
