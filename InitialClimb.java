import java.util.ArrayList;
import java.util.Arrays;


public class InitialClimb{

	private ArrayList<FlightColumn> columnsList;
	private int rowcount;
        private int starttime = 0;
	private int endtime = 0;
	private int j = 0;
	private double altitude = 0.0;

	public InitialClimb(int rows, ArrayList<FlightColumn> columns) {
		this.rowcount = rows;
		this.columnsList = columns;

		check(rowcount);
	}	
	



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




