package grids;

/**
 * The cardinal directions North, South, East, West, and related utility methods.
 */
public enum Cardinal {
    NORTH, SOUTH, EAST, WEST;

   public Cardinal opposite(){
       return opposite(this);
   }

   public Cardinal clockwise(){
       return clockwise(this);
   }

   public Cardinal counterClockwise(){
       return counterClockwise(this);
   }

   public static Cardinal opposite(Cardinal c){
       switch(c){
           case NORTH: return SOUTH;
           case SOUTH: return NORTH;
           case EAST: return WEST;
           case WEST: return EAST;
           default: return null;
       }
   }

   public static Cardinal clockwise(Cardinal c){
      switch(c){
          case NORTH: return EAST;
          case EAST: return SOUTH;
          case SOUTH: return WEST;
          case WEST: return NORTH;
          default: return null;
      }
   }

   public static Cardinal counterClockwise(Cardinal c){
       switch(c){
           case NORTH: return WEST;
           case WEST: return SOUTH;
           case SOUTH: return EAST;
           case EAST: return NORTH;
           default: return null;
       }
   }
}
