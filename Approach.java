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
 * this class will identify initial approach phase and final approach phase
 * and store phase name and start and end time of the 
 * phase
 */

public  class Approach {

	
	private double prevaltitude = 0.0;
	private double curraltitude = 0.0;
	private int starttime = 0;//for storing start time of initial approach
	private int endtime = 0;//for storing end time of initial approach
	private int finalstarttime = 0;//for storing start time of final approach
	private int finalendtime = 0;//for storing end time of final approach
        private int timestamp = 0;
        private int index = 0;
        private int k = 0;
        protected double height = 0; // this store current altitude
        private int rowcount;// this is CSV file row count
        private ArrayList<FlightColumn> columnsList; 
        private int flag = 0;	
	private int count = 0;
	private ArrayList<Phase> phasedetected = new ArrayList(); // this stores all detected phases

	
	/**
         * in the Approach constructor we we pass the count of rows
         * in a CSV file for execution at that time and the arraylist
         * which has all the values of csv file and post this check function
         * is call to identify initial approach phase and final approach  phase
         *
         * @param rows is the count of rows in CSV file
         * @param columns is the arraylist to that holds all values of CVS
         */
	
	public Approach(int rows, ArrayList<FlightColumn> columns) {
                
		this.rowcount = rows;
                this.columnsList = columns;
		 
		/**
                 * while loop will look for time at which initial climb ends 
                 * and then we pass the time value to the check function so 
                 * that slope is calculated from that time onwards
                 */

                while (k < rowcount) {

                        height = columnsList.get(ColNames.AltAGL.getValue()).getValue(k);

                  	if (height >= 1000) {

                        	break;
                        }

                        k++;
		}

                //here we call check function to identify if there is a transition to initial and final approach  phase
                check(rowcount, k, columns);
        }


     
       	/**
         * in the check function we compare current altitude with previous 
	 * where it goes below 500 AGL for initial approach
	 * and then to 200 AGL for final approach to start and 
	 * end at 50 AGL for final approach to finish then
         * the starttime and endtime is stored in phasedetected
         * arraylist and passed for validation to validation class
         * @param rowcount is the number of rows in CSV file
	 * @param k is the time when flight completes initial climb phase to look further for approach phase
	 * @param columns is the arraylist that holds all values of the CSV
         */
	

	public ArrayList<Phase> check(int rowcount, int k, ArrayList<FlightColumn> columns) {

                
 
       		while (k < rowcount - 5) {
                       
			prevaltitude = curraltitude;
			curraltitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(k) ;
                        //System.out.println("current altitude is " + curraltitude + " at timestamp " + k + " prevaltitude is " + prevaltitude);

                       //here we look for altitude to be 500 to look for initial approach
			while ((curraltitude < 500)  && (k < rowcount - 5) ) {
                    
	                	if((count == 0) && (curraltitude < prevaltitude)) {

					starttime = k;    // time is stored in starttime to be added to detectedphase arraylist if condition for initial approach is satisfied after this
				}

				//System.out.println("starttime is ----->>>>>  " + starttime);
			        	
				prevaltitude = curraltitude;
                        	k++;
                        	curraltitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(k) ;

				//here we check if altitude after we once reach altitude 500 increases, if that happens then we look for a new value of starttime 

                        	if (curraltitude >  prevaltitude) { 
					index++;
					count =0;
				} else { 
                                	count++;
                                	index = 0;
				}	
				
                        	//System.out.println("current altitude is " + curraltitude + " at timestamp " + k + " prevaltitude is " + prevaltitude + " at index " + index);
				
				/**
				 * this condition checks if altitude is constantly 
				 * increasing for minimum 100 seconds if that happens
				 * previous detected initial approach start and end time
				 * values are removed from arraylist and we break out of
				 * loop then we look for new start and end time values 
				 * whenever altitude will be 500 AGL
				 */
				if ((index >= 100) ) {

                        		//System.out.println( " flight not yet approaching " );
                                	//System.out.println("clearing slope at " + starttime);
					phasedetected.clear();
					count =0;
					flag = 0;
			         	
                                         
					index = 0;
					break;
                        	}

				/**
				 * after iitial approach starts here we check
				 * if initial approach is getting over and
				 * final approach starts when altitude is clise to 
				 * 200 AGL and once we get the end time we add the 
				 * start time and end time to phasedetected arraylist
				 * break out of while loop and check function
				 */

				if((curraltitude <= 210) && (curraltitude >= 185)  ) {
				        	
					endtime = k;
					//System.out.println("added now");
                                         
					//System.out.println("starttime " + starttime + " endtime " + endtime);
					
					Phase currentphase = new Phase("Initial Approach", starttime, endtime);
					phasedetected.clear();
                                        phasedetected.add(currentphase); //this will keep on adding all detected cruise phases for a flight file
                                        
					break;
				}	

		}
                        
		k++;	


	        }		

	        //System.out.println(phasedetected.get(0).startRow + " phase " + phasedetected.get(0).endRow);	
                /**
		 * here we check if final approach starts
		 * when altitude is close to 50 AGL
		 * then it is stored in phasedetec arraylist
		 */
		finalstarttime = phasedetected.get(0).endRow;
		timestamp = finalstarttime;

		while(finalstarttime < rowcount - 5) {

			prevaltitude = curraltitude;
			                	
                        timestamp++;
			curraltitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(timestamp) ;

                                               
                        if((curraltitude > 40) && (curraltitude < 60)) {
                                                        
                        	finalendtime = timestamp;
				Phase currentphase = new Phase("Final Approach", finalstarttime,  finalendtime);
                                        
                                phasedetected.add(currentphase);
                                System.out.println("final Approach " + finalendtime);
                                break;
                       }
		}       

		System.out.println(phasedetected.get(1).startRow + " phase " + phasedetected.get(1).endRow);
		return phasedetected;   // here we return the phasedetected arraylist to process flight file for validation

        }


}	
