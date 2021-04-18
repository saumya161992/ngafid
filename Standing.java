import java.util.ArrayList;
import java.util.Arrays;

/**
 * in this class tansition from Standing to 
 * Taxi phase is identified
 */

public  class Standing {

	
	private ArrayList<FlightColumn> columnsList; //this will be used to read Columns arraylist generated in ProcessFlightFile
        private int rowcount;       
	/**
	 * in this constructor we extract all values in the CSV and 
	 * rowcount and then check function is called to identify transition 
	 * from Standing phase to Taxi phase
	 * @param columns is an arraylist for all values in CSV
	 * @param rows is the row count of number of rows in CSV file 
	 */
	public Standing(int rows, ArrayList<FlightColumn> columns) {
		this.rowcount = rows;
              	this.columnsList = columns;
              
	        check(rowcount);
	 }


	/**
	 * in this function groundspeed at each index will be checked
	 * for a threshold to find if there is a transition to taxi
	 * @param rowcount is the count of number of rows in CSV 
	 */
	void check(int rowcount) {
	
	//double starttime = columnsList.get(ColNames.Time.getValue()).getValue(0);
	double Groundspeed = 0.0;     
	int j = 0;
		

		while (j < rowcount) {      
			   
                	Groundspeed = columnsList.get(ColNames.GndSpd.getValue()).getValue(j);
			
			if (Groundspeed > 4.50) {
				System.out.println("Standing phase ends/taxi starts at second " + j);	   
				break;     
	        	}
                	j++;       
		}

	

	}		 
     

}    

       

