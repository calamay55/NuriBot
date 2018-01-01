package grids;

public class Coordinate {
    public final int x;
    public final int y;

    public Coordinate(int x, int y){
        this.x=x;
        this.y=y;
    }

    public Coordinate plus(int x, int y){
        return new Coordinate(this.x+x, this.y+y);
    }

    public Coordinate next(Cardinal c) {
       switch(c){
           case NORTH: return plus(0,1);
           case SOUTH: return plus(0,-1);
           case EAST: return plus(1,0);
           case WEST: return plus(-1, 0);
           default: return null;
       }
    }

    public String toString(){
        return "("+x+","+y+")";
    }


    public Integer orthogonalDistanceTo(Coordinate c) {
        return Math.abs(this.x - c.x) + Math.abs(this.y-c.y);
    }
}


