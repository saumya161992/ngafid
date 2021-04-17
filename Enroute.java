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
 * this class will identify where enroute
 * is starting
 */

public  class Enroute {

        private static DecimalFormat df = new DecimalFormat("0.00");
        private int count = 0;
	private int starttime = 0;
	private int endtime = 0;
	private int k = 0;
	protected double height = 0;
        private int rowcount;// this is CSV file row count
        private ArrayList<FlightColumn> columnsList; //this will be used to read Columns arraylist generated in ProcessFlightFile
        double slope = 0.0;//this will save all calculated slopes
        double[] roundoffslope ;
        private ArrayList<Double> phasetransition = new ArrayList<>();//this arraylist will store those 20 altitudes against which transition is there
	private ArrayList<Integer> cruiseslopes = new ArrayList();
	private ArrayList<Phase> phasedetected = new ArrayList();

        /**
         * in the Enroute constructor we we pass the count of rows
         * in a CSV file for execution at that time and the arraylist 
         * which has all the values of csv file and post this check function 
         * is call to identify the takeoff phase
         * @param rows is the count of rows in CSV file
         * @param columns is the arraylist to that holds all values of CVS
         */
        public Enroute(int rows, ArrayList<FlightColumn> columns) {
		System.out.println(" row count is " + rows);
                this.rowcount = rows;
                this.columnsList = columns;
		int count = ((int)this.rowcount/300)+1;
                double[] roundoffslope = new double[rows];
		while (k < rowcount) {

                        height = columnsList.get(ColNames.AltAGL.getValue()).getValue(k);

                        if (height >= 1000) {
                        	
				break;
                        }
		
                        k++;
			

		
		}

                
		//here we call check function to identify if there is a transition from initial climb to Enroute phase
                check(rowcount, k);
        }

        /**
         * here we call get regression slope function to calculate
         * the slopes and then compare the previous slope to the new 
         * slope and then the check condition will validate the transition
         * to takeoff phase
         *
         * @param rowcount is the number of rows in CSV file
         */
         public ArrayList<Phase> check(int rowcount, int k) {

                 double previous_slopeprevious = 0.0;
                 double previous_slope = 0.0;
                 double previous_rsquare = 0.0;
                 double current_rsquare = 0.0;
                 double current_slope = 0.0;
                 int i = 0;
                 int pointer = 0;//this will maintain count of the current slope

 		 String str = "";
                 String filepath = "/home/saumya/saumya_ngafid/ngafid/output.csv";
                 File file = new File(filepath);

                 while (k < (rowcount - 1 )) {
                   
                         if (k == 0) {

                                 LinearRegression temp = getRegressionSlope(rowcount, k, 300);
                                 //this will store the first clculated slope
                                 
                                 previous_slope = temp.slope;
               
	       		//	 previous_rsquare = temp.rsquare;
                         } else {

                                 //this will store the previous slope
                                 previous_slopeprevious = previous_slope;
                                 previous_slope = current_slope;
                          //       previous_rsquare = current_rsquare;
                         }

                         k = k+1;

                         LinearRegression temp = getRegressionSlope(rowcount, k , 300);
                         //here the current calculated slope is stored

                         current_slope = temp.slope;
                         //current_rsquare = temp.rsquare;
                         //double altitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(k);
                         //System.out.println("altitude is " +altitude );
                         int val = k;
			 double altitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(val);

                         pointer++;
			 
			 if (current_slope > -1.0 && current_slope < 1.0 && altitude >= 1000 ) {
			
			
				cruiseslopes.add(val); 
                         	//System.out.println( "current slope is  " + current_slope + " at time " + val + " at altitude " + altitude  );

			 } else {

                         	int size = cruiseslopes.size();
			 	if (size >= 300) {
				     
					
                                        starttime = 150 +  cruiseslopes.get(0)  ;
					endtime = 150 + cruiseslopes.get(size-1)  ;
					System.out.println("cruise found starting at  " + starttime + " ending at " + endtime);

					Phase currentphase = new Phase("Cruise", starttime, endtime);
                                        phasedetected.add(currentphase);
                               
				}       
			        
				cruiseslopes.clear();
			     
		        }	     
                 }

		 return phasedetected;
         }
         /**
          * here slope for altitude against time is
          * getting calculated which will be returned 
          * to check function for identifying if their 
          * is a transition to Takeoff phase
          * @param rowcount is the total number of rows in CSV
          * @param offset is the start index  of time and altitude
          * @param lenght is the range uptil will slope will be calculated
          * @return this is the calculted slope which will be returned to check function
          */
          public LinearRegression getRegressionSlope(int rowcount, int offset, int length) {


                 double mean_x = 0.0;// this will store mean of time
                 double mean_y = 0.0;//this will store mean of altitude
                 double totaltime = 0.0;
                 double[] altitude = new double[300];//will store all the altitude values for the defined range from start index
                 int[] time = new int[300];//this will store all the time values for the defined range from start index 
                 int p = 0;
                 double sumaltitude = 0.0;
                 Arrays.fill(altitude, 0);//this will initialize altitude array with zero
                 Arrays.fill(time, 0);//this will initilize time arry with zero

                for (int j = offset; j < offset + length; j++)
                {
                        if (j < rowcount) {
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
                mean_y = (sumaltitude/300);
                //this will store average of time
                mean_x = (totaltime/300);

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
                
                /*for (int i = 0; i < length && offset+i < rowcount; i++) {
                y_pred = intercept + slope * time[i];
                actual = actual + (altitude[i] - mean_y) * (altitude[i] - mean_y);
                estimated = estimated + (y_pred - mean_y) * (y_pred - mean_y);
               }

               r2 = (estimated/actual);*/
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
		