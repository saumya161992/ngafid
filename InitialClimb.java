import java.util.ArrayList;
import java.util.Arrays;

/**
 * this class will identify start and end of
 *initial climb phase for all flight 
 * files
 */

public class InitialClimb{

	private ArrayList<FlightColumn> columnsList;//this will be used to generate columns arraylist in process flight file class
	private int rowcount;//this is the count of rows in the CSV file
        private int starttime = 0;
	private int endtime = 0;
	private int j = 0;
	private double altitude = 0.0;

	/**
        * in the InitialClimb constructor we we pass the count of rows
        * in a CSV file for execution at that time and the arraylist
        * which has all the values of csv file and post this check function
        * is call to identify the initial climb  phase
        * @param rows is the count of rows in CSV file
        * @param columns is the arraylist to that holds all values of CVS
        */

        public InitialClimb(int rows, ArrayList<FlightColumn> columns) {
		this.rowcount = rows;
		this.columnsList = columns;

		check(rowcount);
	}	
	


        /**
         * here we pass the rowcount to  calculate
         * the compare the altitude and then the check 
	 * condition will validate the transition
         * to initial climb phaseis taking place
	 * and record the start time and end time
         * of intial climb phase
	 *
         * @param rowcount is the number of rows in CSV file
         */

	void check(int rowcount) {

		//double altitude = 0.0;
                //int  j = 0;

		while (j < rowcount) {

			altitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(j);

			if (altitude > 35) {
				starttime = j;
				break;
			}	
                        j++;			
                }

	        while (j < rowcount) {

			altitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(j);
                        
			if (altitude >= 1000) {
				endtime = j;
				System.out.println("Initial climb started at time " + starttime + " and ended at time " + endtime);
				break;
			}
		        j++;	
              }

        }

}




