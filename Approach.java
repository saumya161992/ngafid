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

public  class Approach {

	
        private double prevaltitude = 0.0;
	private double curraltitude = 0.0;
	private int starttime = 0;
	private int endtime = 0;
	private int finalstarttime = 0;
	private int finalendtime = 0;
        private int timestamp = 0;
        private int index = 0;
        private int k = 0;
        protected double height = 0;
        private int rowcount;// this is CSV file row count
        private ArrayList<FlightColumn> columnsList; 
        private int flag = 0;	
	private int count = 0;
	
	private ArrayList<Phase> phasedetected = new ArrayList(); // this stores all detected phases

	
	
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

                System.out.println( "k is  " + k + " height is " + height);
                //here we call check function to identify if there is a transition from initial climb to Enroute phase
                check(rowcount, k, columns);
        }


        

	public ArrayList<Phase> check(int rowcount, int k, ArrayList<FlightColumn> columns) {

                
 
       		while (k < rowcount - 5) {
                       

                        prevaltitude = curraltitude;

			curraltitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(k) ;
                        
			

			//System.out.println("current altitude is " + curraltitude + " at timestamp " + k + " prevaltitude is " + prevaltitude);

			

		 	while ((curraltitude < 500)  && (k < rowcount - 5) ) {
                    
				                 	
			        if((count == 0) && (curraltitude < prevaltitude)) {

					starttime = k;
				}

				//System.out.println("starttime is ----->>>>>  " + starttime);
			        //count++;	
				prevaltitude = curraltitude;
                        	
				k++;
                        	curraltitude = columnsList.get(ColNames.AltAGL.getValue()).getValue(k) ;

                        	if (curraltitude >  prevaltitude) {
				
                        		index++;
					count =0;
					

                        	} else { 
                                        count++;
                                	index = 0;
				}	
				
                        	//System.out.println("current altitude is " + curraltitude + " at timestamp " + k + " prevaltitude is " + prevaltitude + " at index " + index);
				if ((index >= 100) ) {

                        		System.out.println( " flight not yet approaching " );
                                	
					System.out.println("clearing slope at " + starttime);
					
					phasedetected.clear();
					count =0;
					flag = 0;
			         	System.out.println(" breaking at time " + k + " at index " + index);
                                         
					index = 0;
					//System.out.println(" breaking at time " + k);
					//k = timestamp;
                                	break;
                        	}

				if((curraltitude <= 210) && (curraltitude >= 185)  ) {
				        	
					endtime = k;
					//System.out.println("added now");
                                         
					//System.out.println("starttime " + starttime + " endtime " + endtime);
					//flag = 1;
					Phase currentphase = new Phase("Initial Approach", starttime, endtime);
					phasedetected.clear();
                                        phasedetected.add(currentphase); //this will keep on adding all detected cruise phases for a flight file
                                        //System.out.println(phasedetected.get(0).startRow + " phase " + phasedetected.get(0).endRow);
					break;
				}	

				




                 	
                 
			}
                        
		        k++;	


	        }		

	        System.out.println(phasedetected.get(0).startRow + " phase " + phasedetected.get(0).endRow);	
                System.out.println("what is k " + k + " what is rowcount " + rowcount);

		
		finalstarttime = phasedetected.get(0).endRow;
		System.out.println("what is final start time " + finalstarttime);
		timestamp = finalstarttime;

		while(finalstarttime < rowcount - 5) {

			prevaltitude = curraltitude;
			//timestamp = finalstarttime;
                	
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
		return phasedetected;

        }


}	
