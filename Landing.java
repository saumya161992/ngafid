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

public class Landing {


        private double prevaltitude = 0.0;
        private double curraltitude = 0.0;
        private int starttime = 0;//for storing start time of phase
        private int endtime = 0;//for storing end time of phase
        private int timestamp = 0;
        private int index = 0;
        private int k = 0;
        protected double height = 0; // this store current altitude
        private int rowcount;// this is CSV file row count
        private ArrayList<FlightColumn> columnsList;
        private int flag = 0;
        private int count = 0;
	private int speedindex = 0;
	
        private ArrayList<Phase> phasedetected = new ArrayList(); // this stores all detected phases


        /**
         * in the landing constructor we we pass the count of rows
         * in a CSV file for execution at that time and the arraylist
         * which has all the values of csv file and post this check function
         * is call to identify initial approach phase and final approach  phase
         *
         * @param rows is the count of rows in CSV file
         * @param columns is the arraylist to that holds all values of CVS
         */

        public Landing(int rows, ArrayList<FlightColumn> columns) {

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

                //here we call check function to detect go around  phase
                checkgoaround(rowcount, k, columns);
		for (int i = 0 ;i < columns.size() ; i++) {
                	if (columns.get(i).getName().equals("IAS")){
				 speedindex = i;
                        }
                }

		//here we call check function to detect if aircraft is in landing phase


		checklanding(rowcount, k, columns, speedindex);

        }



        /**
         * in the check function we compare current altitude with previous
         * where it goes below 165 AGL for go around to start
         * and then to above 500 AGL for go around  to end in few seconds
         * @param rowcount is the number of rows in CSV file
         * @param k is the time when flight completes initial climb phase to look further for go around phase
         * @param columns is the arraylist that holds all values of the CSV
         */


        public ArrayList<Phase> checkgoaround(int rowcount, int k, ArrayList<FlightColumn> columns) {



                while (k < rowcount - 5) {

                        prevaltitude = curraltitude;
			k++;
                        curraltitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(k) ;
                        //System.out.println("current altitude is " + curraltitude + " at timestamp " + k + " prevaltitude is " + prevaltitude);

                       //here we look for altitude to be 165 for go around to start
                        if(curraltitude < 165) {
                        
				starttime = k;


				while ((curraltitude < 510) && (k < rowcount -10 )) {
                               
		                	prevaltitude = curraltitude;
                                	k++;
					curraltitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(k) ;
					index++;

					//here we check the time value to compare if it goes below 135 then we skip time value as it can't be go around

					if(curraltitude < 135)  { 
						
						k = k +300;
						break;

					}	

					if((curraltitude > 510) ) {
						endtime = k; // here go around is detected
						if(index <=200) {
							System.out.println(" Goaround phase found " + " starttime is " + starttime + " endtime is " + endtime);

							Phase currentphase = new Phase("Go around", starttime, endtime);
                                        
                                        		phasedetected.add(currentphase);
						}
					        break;
					}	
				}	
					
		 	
			}
		        
		        //System.out.println("starttime is " + starttime + " endtime is " + endtime);
			//return phasedetected;

		        
		}

		return phasedetected;


		
     


	}	



        public ArrayList<Phase> checklanding(int rowcount, int k, ArrayList<FlightColumn> columns, int speedindex) {
                        
	
        	while (k < rowcount - 5) {

                        prevaltitude = curraltitude;
			k++;
                        curraltitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(k) ;
                        //System.out.println("current altitude is " + curraltitude + " at timestamp " + k + " prevaltitude is " + prevaltitude);

                       	//here we look for altitude to be close to 60 for landing to start after final approach
                       	
			while ((curraltitude < 75)  && (k < rowcount - 5) ) {
                       
				if((count == 0) && (curraltitude < prevaltitude)) {

                                        starttime = k;   
                                }


				prevaltitude = curraltitude;
                        	k++;
                        	curraltitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(k) ;

				// this is a check condition to check altitude increases after going below final approch altitude because if this happens it is not initial approach yet
                                if (curraltitude >  prevaltitude) {
                                        index++;
					starttime = 0;

                                        count = 0;
                                } else {
                                        count++;
                                        index = 0;
                                }

                                //System.out.println("current altitude is " + curraltitude + " at timestamp " + k + " prevaltitude is " + prevaltitude + " at index " + index);

                                /**
                                 * this condition checks if altitude is constantly 
                                 * increasing for minimum 5 seconds if that happens
                                 * previous detected initial approach start and end time
                                 * values are removed from arraylist and we break out of
                                 * loop then we look for new start and end time values 
                                 * whenever altitude will be close to 60 AGL
                                 */
                                if ((index >= 5) ) {

                                        //System.out.println( " flight not yet approaching " );
                                        //System.out.println("clearing slope at " + starttime);
                                        phasedetected.clear();
                                        count =0;
                                        flag = 0;


                                        index = 0;
                                        break;
                                }



		        	double currentspeed = columns.get(speedindex).getValue(k) ;

				
                               //here we see if indicated airspeed is close to 40 as it indiacates aircraft is not flying now
	
		                if((currentspeed <= 40) && (flag == 0)) {
					System.out.println("starttime is  " + k + " curraltitude " + curraltitude);
					starttime = k;
					flag = 1;
					prevaltitude = curraltitude;
                        		k++;
                        		curraltitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(k) ;
					//System.out.println("Here index" + currentspeed + " time " + k);


                                        if(curraltitude < 10 ) {
						endtime = k;
						System.out.println("endtime is " + k + " curraltitude " + curraltitude);
					        phasedetected.clear();
						Phase currentphase = new Phase("landing", starttime, endtime);

                                                phasedetected.add(currentphase);

						//System.out.println("landing starttime " + starttime + " endtime " + endtime); 
							break;
							
					}
			        }	
		        }

			
	        }	       

		System.out.println(phasedetected.get(0).startRow + " phase " + phasedetected.get(0).endRow);
		return phasedetected;


        }				
       		       	
        


}

