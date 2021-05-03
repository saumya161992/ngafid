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

public  class Enroute {

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
        public Enroute(int rows, ArrayList<FlightColumn> columns) {
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

		while (k < rowcount) {

                	height = columnsList.get(ColNames.AltAGL.getValue()).getValue(k);

			for ( int i = 0 ;i < columns.size() ; i++) {
                        	if (columns.get(i).getName().equals("Pitch")) {
                                	pitchindex = i;
                                	//System.out.println("1 --> " + columns.get(i).getName() + " pitch index is  "  + pitchindex );
                        	//}         System.out.println("1 --> " + columns.get(i).getName() + " pitch index is  "  + pitchindex );
                        	} else if (columns.get(i).getName().equals("Roll")) {
                                	Rollindex = i;
                                } else if (columns.get(i).getName().equals("VSpd")) {
                                        speedindex = i;
				}	

			}	

                        if (height >= 1000) {
                        	
				break;
                        }
		
                        k++;
			

		
		}

                
		//here we call check function to identify if there is a transition from initial climb to Enroute phase
                check(rowcount, k, columns);
        }

        /**
         * here we call get regression slope function to calculate
         * the slopes and then compare the previous slope to the new 
         * slope and then the check condition will validate the transition
         * to cruise and descend  phase
         *
         * @param rowcount is the number of rows in CSV file
         */
         public ArrayList<Phase> check(int rowcount, int k, ArrayList<FlightColumn> columns) {

                 double previous_slopeprevious = 0.0;
                 double previous_slope = 0.0;
		 double current_slope = 0.0;
                 double previous_slopepitch = 0.0;
                 double current_rsquare = 0.0;
                 double current_slopepitch = 0.0;
                 int i = 0;
	         int flag = -1;	
		 int timenow = 0;
		 double indexpitch = 0;
                 int pointer = 0;//this will maintain count of the current slope

 		 String str = "";
                 String filepath = "/home/saumya/saumya_ngafid/ngafid/output.csv";
                 File file = new File(filepath);

                 while (k < (rowcount - 1 )) {
                   
                 	if (k == 0) {

                                 LinearRegression temp = getRegressionSlope(rowcount, k, 300);
                                 //this will store the first clculated slope
                                 
                                 previous_slope = temp.slope;
				 
               
	       	
                        } else {

                        	//this will store the previous slope
                                //previous_slopeprevious = previous_slope;
                                previous_slope = current_slope;
		
                          
                         }

                         k = k+1;

                         LinearRegression temp = getRegressionSlope(rowcount, k , 300);
                         //here the current calculated slope is stored

                         current_slope = temp.slope;
			 
                         
                         
                         
                         int val = k;
			 double altitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(val);
			 double previouspitch = currentpitch;
			 currentpitch = columns.get(pitchindex).getValue(val) ;
                         pointer++;
			 

			 //this condition will calculate slope values within(-1 to 1) and then store them in cruiseslopes arraylist temporarily
			 if (current_slope > -3.0 && current_slope < 3.0 && altitude >= 500 ) {
			        
		                 		
				if (previouspitch < 5.0  &&  previouspitch > -5.0) {
                         		indexpitch = 0;
					//cruiseslopes.add(val);

				}	

				

                                    
				if (currentpitch > 5.0 || currentpitch < -5.0) {
					
					indexpitch++;

					if (indexpitch == 1) {
					      timenow = k;
					}     
					if (indexpitch > 10 && cruiseslopes.size() < 300) {
                                        	cruiseslopes.clear();
                                        	indexpitch = 0;
                                        	//System.out.println("cruise looking again");
						k = timenow + 1;
							
                                        }
					/*if (indexpitch >= 10 ) {
						if (cruiseslopes.size() > 300 ) {

                                        		starttime =  cruiseslopes.get(0)  ; //start time is mean of total time
                                                	endtime = cruiseslopes.get((cruiseslopes.size())-1)  ; // endtime is mean of total time
                                               		// System.out.println("cruise found starting at  " + starttime + " ending at " + endtime);

                                                	Phase currentphase = new Phase("Cruise", starttime, endtime);
                                                	phasedetected.add(currentphase); //this will keep on adding all detected cruise phases for a flight file
                                                }
                                                cruiseslopes.clear();
					}*/	
				}	





			        

				

                                //System.out.println("count list is ------>" + countlist);
				cruiseslopes.add(val);
				if (flag == 1) {countlist = 0;}
			        if (cruiseslopes.size()  > countlist) {	
					//System.out.println( "inside here " );
					double altitudeone = columnsList.get(ColNames.AltAGL.getValue()).getValue(cruiseslopes.get(countlist));

					if (cruiseslopes.size() > (countlist + 30)) {
						double altitudetwo = columnsList.get(ColNames.AltAGL.getValue()).getValue(cruiseslopes.get(countlist + 30));
						System.out.println(" difference is " + Math.abs(altitudeone - altitudetwo)  + " at val " + val + " altitudeone is " + altitudeone + " altitude 2 is " + altitudetwo + " countlist is "+ countlist);
                                        	if (Math.abs(altitudeone - altitudetwo) > 200) {
							System.out.println("altitude difference is greater than 100 so,  clearing the slopes arralylist to relinitialize at " +  countlist);
							if (cruiseslopes.size() >= 300) {
								starttime = cruiseslopes.get(0);
								endtime = cruiseslopes.get(countlist);
								System.out.println("phase start time " + starttime + " endtime is " + endtime);
								cruiseslopes.clear();
								countlist = 0;
								flag = 1;
							} else {

								k = cruiseslopes.get(countlist + 30);

								val = k;
								//countlist++;
								cruiseslopes.clear();
								 flag = 1;
								countlist = 0;
							}	
						}
						countlist++;

					}		
			       }
                               System.out.println( "current slope is  " + current_slope + " at time " + val + " at altitude " + altitude    + " current pitch is  " +  currentpitch + " count is " + indexpitch + " size of arraylist is  " + cruiseslopes.size() );

			 } else  {

				int size = cruiseslopes.size();
                                System.out.println( "current slope is  " + current_slope + " at time " + val + " at altitude " + altitude   + " till time " + (val + 300) +" current pitch is  " +  currentpitch + " count  is  " + indexpitch  );

                                if (size >= 300) {

                                
				 /*int indexpitchendtime = 0;
				 int count = 0;
				 currentroll = columns.get(Rollindex).getValue(val) ;

				 /*while (count < 300 && (val + count < (rowcount - 5 )) ) {
                                        count++;

					previouspitch  = currentpitch;
			         	currentpitch = columns.get(pitchindex).getValue(val + count) ;
               			        currentroll  = columns.get(Rollindex).getValue(val + count) ;
                                        	

					//System.out.println("end time is " + (val+count) + " and pitch is " + currentpitch + " at index " + count + " and roll is " + currentroll);
					cruiseslopes.add(val+count);
                                   

					if (previouspitch < 5.0  &&  previouspitch > -5.0) {
                                        	indexpitchendtime = 0;
                                        	

                                	}


                                        if (currentpitch > 5.0 || currentpitch < -5.0) {
                                        	System.out.println("index pitch is " + indexpitchendtime);
                                        	indexpitchendtime++;
                                                if (indexpitchendtime  > 5 || (currentroll < - 10 && currentroll > 10)) {
							break;
						}
					}	
				 }*/
		         
			 //} else {

				//below condition detects if cruise phase takes place
				//it will take place when slope values are in range (-1 to 1) for minimum 5 minutes 

                         	 size = cruiseslopes.size();
				//System.out.println( "current slope is  " + current_slope + " at time " + val + " at altitude " + altitude   + " till time " + (val + 300) +" current pitch is  " +  currentpitch + " count  is  " + indexpitch  );

			 	//if (size >= 300) {
				     
					
                                        starttime =  cruiseslopes.get(0)  ; //start time is mean of total time
					endtime = cruiseslopes.get(size-1)  ; // endtime is mean of total time
					///System.out.println("cruise found starting at  " + starttime + " ending at " + endtime);

					Phase currentphase = new Phase("Cruise", starttime, endtime);
                                        phasedetected.add(currentphase); //this will keep on adding all detected cruise phases for a flight file
                                        
					timestamp = endtime; // here we initialize timestamp to endtime of detected cruise phase 
					index = 0;
                                        System.out.println("end time is  " + timestamp);
                                        curraltitude = altitude; // we initialize curraltitude to the altitude of endtime which is where the current cruise ends

                                        /**
					 * here we look for descend phase to take place
					 * so we compare previous altitude at a timestamp
					 * to altitude at timestamp + 1 and then if altitude
					 * is not found to be decreasing for maximum 200 seconds
					 * then descend place has not taken place so we break the
					 * loop to look for next cruise phase at timestamp after 
					 * currently dectected endtime of cruise phase else if
					 * descend is taking place, starttime of detected descend is saved and 
					 * the loop iterates to look for timestamp at which altitude of 1000 is 
					 * reached which will be the end time of descend
					 */

					while (curraltitude > 1000) { 
					        
						prevaltitude = curraltitude;
						timestamp = timestamp + 1;
						curraltitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(timestamp) ;
	                                        if (curraltitude >  prevaltitude) {
						       index++;
					        
					        }
						//System.out.println("current altitude is " + curraltitude + " at timestamp " + timestamp + " prevaltitude is " + prevaltitude);
						if (index > 200) {
							System.out.println( " flight not yet descending " );
							countlist = 0;
							break;
					        }		

				       }
				       if (curraltitude <= 1000) {

		                                System.out.println("timestamp for descend to end is " + timestamp); 		       
				       }  		
                               
				}       
			        

				cruiseslopes.clear(); // arraylist is cleared to store time values for next cruise phase
			     
		        }	     
                 }

		 return phasedetected; //returns an arraylist for validation of all detected phases to process flight file class
         }

         /**
          * here slope for altitude against time is
          * getting calculated which will be returned 
          * to check function for identifying if their 
          * is a transition to Takeoff phase
	  *
          * @param rowcount is the total number of rows in CSV
          * @param offset is the start index  of time and altitude
          * @param length is the range uptil will slope will be calculated
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

                for (int j = offset; j < offset + length; j++) {

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
                
 	       LinearRegression LR = new LinearRegression();
               LR.slope = slope;
               
               //here slope and rsquare values are returned to the getregression slope function 
               return LR;


         }


         class LinearRegression {

                public double slope = 0.0;
		
                

         }
}
		
