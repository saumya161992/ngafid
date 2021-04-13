

public class Phase {
	String phaseName;
	int startRow;
	int endRow;   ///make a constructor
   
   
	public Phase(String phaseName, int startRow, int endRow) {
	   
		this.phaseName = phaseName;
		this.startRow = startRow;
		this.endRow = endRow;
     
      
   	}  
  
   
   	//have a comparison method
   	public boolean matches(Phase other) {
      	//return true if they match, false otherwise
      		if ( other.phaseName == phaseName ) return true;
        	return false;

   	}
}

