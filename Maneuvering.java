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
                int count = ((int)this.rowcount/10)+1;
               
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

                 double previous_slopeprevious = 0.0;
                 double previous_slope = 0.0;
                 double current_slope = 0.0;
                 int i = 0;
                 int pointer = 0;//this will maintain count of the current slope

                 int index = 0;                
                 
               

                 while (k < rowcount - 2) {
                          
		       if (k == 0) {

                                 LinearRegression temp = getRegressionSlope(rowcount, k , 10);
                                 //this will store the first clculated slope

                                 previous_slope = temp.slope;


                        } else {

                                //this will store the previous slope
                                previous_slopeprevious = previous_slope;
                                previous_slope = current_slope;
                          //    previous_rsquare = current_rsquare;
                         }

                         k = k+1;

                         LinearRegression temp = getRegressionSlope(rowcount, k, 10);
                         //here the current calculated slope is stored

                         current_slope = temp.slope;
                         //current_rsquare = temp.rsquare;
                         //double altitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(k);
                         //System.out.println("altitude is " +altitude );
                         
                         double altitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(k);

                         

			
                         

                       
			// System.out.println("current slope is " +  current_slope  + " previous slope is " + previous_slope + " time is " + k   );

			

                   

                        
			 if(current_slope < -8 ) {
                               
				current_slope = maneuveringcheck(altitude, rowcount, previous_slope, current_slope);
 
                                if (current_slope > -1.0 && current_slope > 1.0 ) {
                                    
			           	System.out.println("--------detected turn --------");		
				   	int turntime = k;

					current_slope = maneuveringcheck(altitude, rowcount ,previous_slope, current_slope);  
				   
				  	      

					/* while ((current_slope > previous_slope) && index < 8) {
                                                        
						 k++;
						 index++;
                                                 current_slope = maneuveringcheck(altitude, rowcount, previous_slope, current_slope);
						 if (current_slope  > -1.0 && current_slope > 1.0) {
							 //System.out.println("maneuvering ends" + k);
							 break;
					         
						 }
				                 //System.out.println("maneuvering ends" + k);
		 

                                         }*/
			        while (current_slope > -1.0 && current_slope > 1.0) {
                                	current_slope = maneuveringcheck(altitude, rowcount, previous_slope, current_slope);
                                }
                                         
				if (k - turntime > 25) {
					System.out.println("maneuvering ends" + k);
                                } 


				       	 
						

                         }

                                

                                
                               
				
				
	 	}	
				
					
                                           
                                //System.out.println("current altitude is " + curraltitude + " at timestamp " + timestamp + " prevaltitude is " + prevaltitude);
                                /*if (index > 200) {
					//Pitch(19),
                                        // Roll(20),
                                       // System.out.println("inside condition");
			      	 	double currentpitch = columns.get(pitchindex).getValue(timestamp) ;
				 	double currentroll = columns.get(Rollindex).getValue(timestamp) ;

					//double roll = columnsList.get(ColNames.Roll.getValue()).getValue(timestamp) ;
                                        System.out.println("Pitch is " + currentpitch + " at time " + timestamp);
					System.out.println("Roll is " + currentroll + " at time " + timestamp);

                                 	//System.out.println( " pitch is  " + pitch + " roll is " + roll + "timestamp is " + timestamp );
                                        
                                }index =0;*/

                        
                        //k++;
                }



        }

        
       public double maneuveringcheck(double altitude,int  rowcount,  double previous_slope, double  current_slope) { 

      	 	while (altitude > 200 && k < rowcount -1 && current_slope > previous_slope )  {


                       previous_slope = current_slope;
                       k++;

                       LinearRegression temp2 = getRegressionSlope(rowcount, k, 10);
                        //here the current calculated slope is stored

                       current_slope = temp2.slope;

                       altitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(k);

                       System.out.println("current slope is " +  current_slope  + " previous slope is " + previous_slope + " time is " + k  );
              
		}
                return current_slope;
      }		




        public LinearRegression getRegressionSlope(int rowcount, int offset, int length) {


                double mean_x = 0.0;// this will store mean of time
                double mean_y = 0.0;//this will store mean of altitude
                double totaltime = 0.0;
                double[] altitude = new double[10];//will store all the altitude values for the defined range from start index
                int[] time = new int[10];//this will store all the time values for the defined range from start index 
                int p = 0;
                double sumaltitude = 0.0;
                Arrays.fill(altitude, 0);//this will initialize altitude array with zero
                Arrays.fill(time, 0);//this will initilize time arry with zero

                for (int j = offset; j < offset + length; j++) {

                        if (j < rowcount ) {
                                //this is used to fetch altitude in CSV at a given index
                                altitude[p] = columnsList.get(ColNames.AltAGL.getValue()).getValue(j);
                                //this will store all the time values against the altitude
                                time[p] = j;
                                sumaltitude = sumaltitude + altitude[p];
                                totaltime = totaltime +  time[p];
                                p++;
                        }
                }

               //this will store average of altitude
                mean_y = (sumaltitude/10);
                //this will store average 1of time
                mean_x = (totaltime/10);

                double  intercept = 0.0;
                double  numerator = 0;
                double  denominator = 0;

                for (int k = 0; k < length && offset + k < rowcount; k++) {

                        numerator += (altitude[k] - mean_y) * (time[k] - mean_x);
                        denominator += (time[k] - mean_x) * (time[k] - mean_x);

                }
                //here final slope calculation for range is happening
                slope = numerator/denominator;

                intercept  = (mean_y - (slope *  mean_x));
                //System.out.println("slope " + count + " is " + slope);
                count++;
                double actual = 0.0;
                double estimated = 0.0;
                double y_pred = 0.0;
                double r2 = 0.0;
                

		LinearRegression LR = new LinearRegression();
                LR.slope = slope;
                //LR.rsquare = r2;

                //System.out.println(" r value is " + r2);
                //here slope and rsquare values are returned to the getregression slope function
                return LR;

		 


         }


         class LinearRegression {

                public double slope = 0.0;
                //public double rsquare = 0.0;

         }



                     
}
