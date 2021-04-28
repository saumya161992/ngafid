import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * this class will identify cruise and descend phase
 * and store phase name and start and end time of the 
 * phase
 */

public  class Maneuvering {

        private static DecimalFormat df = new DecimalFormat("0.00");
        private int count = 0;
        private int starttime = 0;
        private int endtime = 0;
        private double curraltitude = 0.0;
        private double prevaltitude = 0.0;
        private int timestamp = 0;
        private int index = 0;
        private int k = 0;
        protected double height = 0;
        private int rowcount;// this is CSV file row count
        private ArrayList<FlightColumn> columnsList; //this will be used to read Columns arraylist generated in ProcessFlightFile
        double slope = 0.0;//this will save all calculated slopes
        double[] roundoffslope ;
        private ArrayList<Double> phasetransition = new ArrayList<>();
        private ArrayList<Integer> cruiseslopes = new ArrayList(); // this will temporarily store the time  values if slope for 300 values at a time is found in range (-1 to 1) 
        private ArrayList<Phase> phasedetected = new ArrayList(); // this stores all detected phases
	public int pitchindex;
	public int Rollindex;

        /**
         * in the Enroute constructor we we pass the count of rows
         * in a CSV file for execution at that time and the arraylist 
         * which has all the values of csv file and post this check function 
         * is call to identify cruise and descend  phase
         *
         * @param rows is the count of rows in CSV file
         * @param columns is the arraylist to that holds all values of CVS
         */
        public Maneuvering(int rows, ArrayList<FlightColumn> columns) {
                System.out.println(" row count is " + rows);
                this.rowcount = rows;
                this.columnsList = columns;
                int count = ((int)this.rowcount/300)+1;
               
		for ( int i = 0 ;i < columns.size() ; i++) {
                        if(columns.get(i).getName().equals("Pitch")) {
			        pitchindex = i;
				System.out.println("1 --> " + columns.get(i).getName() + " pitch index is  "  + pitchindex );
			} else if(columns.get(i).getName().equals("Roll")) {
			        Rollindex = i;
			        System.out.println("1 --> " + columns.get(i).getName() + "roll index " + Rollindex );
                         }	

                } 
                System.out.println("inside maneuvering constructor" );
                //here we call check function to identify if there is a transition from initial climb to Enroute phase
                check(rowcount, columns);
        }

        /**
         * here we call get regression slope function to calculate
         * the slopes and then compare the previous slope to the new 
         * slope and then the check condition will validate the transition
         * to cruise and descend  phase
         *
         * @param rowcount is the number of rows in CSV file
         */
         public void check(int rowcount, ArrayList<FlightColumn> columns) {

                
                 int j = 0;
               // System.out.println("inside check of maneu");

                while (j < rowcount) {

                         height = columnsList.get(ColNames.AltAGL.getValue()).getValue(j);

                         curraltitude = height;
                          while (curraltitude >  prevaltitude) {

                          	prevaltitude = curraltitude;
                                timestamp = timestamp + 1;
				index++;
                                curraltitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(timestamp) ;
                                System.out.println("altitude is  " + curraltitude + " index is " + timestamp);

                                if (curraltitude <= 200) {
                                	break;

                                }
			 }	
                                           
                                //System.out.println("current altitude is " + curraltitude + " at timestamp " + timestamp + " prevaltitude is " + prevaltitude);
                                if (index > 200) {
					//Pitch(19),
                                        // Roll(20),
                                       // System.out.println("inside condition");
			      	 	double currentpitch = columns.get(pitchindex).getValue(timestamp) ;
				 	double currentroll = columns.get(Rollindex).getValue(timestamp) ;

					//double roll = columnsList.get(ColNames.Roll.getValue()).getValue(timestamp) ;
                                        System.out.println("Pitch is " + currentpitch + " at time " + timestamp);
					System.out.println("Roll is " + currentroll + " at time " + timestamp);

                                 	//System.out.println( " pitch is  " + pitch + " roll is " + roll + "timestamp is " + timestamp );
                                        
                                }index =0;

                        
                        j++;
                }



        }


                     
}
