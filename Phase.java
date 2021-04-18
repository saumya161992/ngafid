
public class Phase {
	String phaseName;
	int startRow;
	int endRow;   ///make a constructor
        
	/** This constructs a new Phase  object with a given name and
         *  initializes it's start time and end time with startrow
	 *  and end row of the CSV file
         * @param phaseName is the name of the phase which is detected 
	 * @param startRow is the time when phase identified starts
         * @param endRow is the time when phase identified ends
         */


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

