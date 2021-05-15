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

public  class Maneuveringnew {

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
        double slopepitch = 0.0;
        double[] roundoffslope ;
        private ArrayList<Double> phasetransition = new ArrayList<>();
        private ArrayList<Integer> cruiseslopes = new ArrayList(); // this will temporarily store the time  values if slope for 300 values at a time is found in range (-1 to 1) 
        private ArrayList<Phase> phasedetected = new ArrayList(); // this stores all detected phases
        public int pitchindex;
        public int Rollindex;
        public int speedindex;
        private double currentpitch = 0.0;
        private double currentroll = 0.0;
        private int indexspeed = 0;
        public int countlist = 0;



        /**
         * in the Enroute constructor we we pass the count of rows
         * in a CSV file for execution at that time and the arraylist 
         * which has all the values of csv file and post this check function 
         * is call to identify cruise and descend  phase
         *
         * @param rows is the count of rows in CSV file
         * @param columns is the arraylist to that holds all values of CVS
         */
        public Maneuveringnew(int rows, ArrayList<FlightColumn> columns) {
                System.out.println(" row count is " + rows);
                this.rowcount = rows;
                this.columnsList = columns;
                int count = ((int)this.rowcount/300)+1;
                double[] roundoffslope = new double[rows];

                /**
                 * while loop will look for time at which initial climb ends 
                 * and then we pass the time value to the check function so 
                 * that slope is calculated from that time onwards
                 */

                for ( int i = 0 ;i < columns.size() ; i++) {
                	if (columns.get(i).getName().equals("Pitch")) {
                        	pitchindex = i;
                                //System.out.println("1 --> " + columns.get(i).getName() + " pitch index is  "  + pitchindex );
                                //}System.out.println("1 --> " + columns.get(i).getName() + " pitch index is  "  + pitchindex );
                        } else if (columns.get(i).getName().equals("Roll")) {
                        	Rollindex = i;
                        } else if (columns.get(i).getName().equals("VSpd")) {
                        	speedindex = i;
                        }

                }

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
        
                 int starttime = 0;
		 int endtime = 0;
		 int flag = 0;
		 int currtime = 0;
		 while (k < (rowcount - 1 )) {
                 
			//double altitudeone = columnsList.get(ColNames.AltAGL.getValue()).getValue(k);
                        double currentroll = columns.get(Rollindex).getValue(k) ;
                        //System.out.println("currentroll is " + currentroll);

			if (( currentroll < -25 || currentroll > 25) &&  Math.abs(currtime - k) > 50) {
				currtime = k;
				
			        if (flag == 0) {
				         starttime = k;
					flag = 1;
			        } else {
				        endtime  = k ; 
				        flag = 0;
					System.out.println (" Maneuvering time start " +  starttime + " maneuvering time end " + endtime);
                                }
		        }         
			k++;

			/*currentroll = columns.get(Rollindex).getValue(k) ;

			if (( currentroll < -25 || currentroll > 25) && (Math.abs(currtime - k) > 50)) {
				System.out.println("endtime is " + k);

                                currtime = k;
                        }

                        /*if ((starttime > 0 ) && (endtime > 0)) {
				System.out.println("starttime for maneuvering is " + starttime + " endtime is " + endtime);
				starttime = 0;
				//endtime = 0;
                        }*/
			//Phase currentphase = new Phase("Maneuvering", starttime, endtime);
                        //phasedetected.add(currentphase);
                        
			//k++;
			        
               }

	       //return phasedetected;

	 }     
}
