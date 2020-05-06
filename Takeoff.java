import java.util.ArrayvList;
import java.util.Arrays;
import java.math.RoundingMode;
import java.text.DecimalFormat;
public  class Takeoff {

	private static DecimalFormat df=new DecimalFormat("0.00");
        private int count=0;
        private int rowcount;// this is CSV file row count
	private ArrayList<FlightColumn> columnsList; //this will be used to read Columns arraylist generated in ProcessFlightFile
        double slope=0.0;//this will save all calculated slopes
	double[] roundoffslope=new double[602];
	private ArrayList<Double> phasetransition=new ArrayList<>();//this arraylist will store those 20 altitudes against which transition is there
	 
	/**
	 * in the Takeoff constructor we we pass the count of rows
	 * in a CSV file for execution at that time and the arraylist 
	 * which has all the values of csv file and post this check function 
	 * is call to identify the takeoff phase
	 * @param rows is the count of rows in CSV file
	 * @param columns is the arraylist to that holds all values of CVS
	 */
	public Takeoff(int rows,ArrayList<FlightColumn> columns){
        	this.rowcount=rows;
              	this.columnsList=columns;
              
		//hete we call check function to identify if there is a transition from Taxi to Takeoff phase
	        check(rowcount);
        }
      
	/**
	 * here we call get regression slope function to calculate
	 * the slopes and then compare the previous slope to the new 
	 * slope and then the check condition will validate the transition
	 * to takeoff phase
	 *
	 * @param rowcount is the number of rows in CSV file
	 */
         void check(int rowcount) {
		double previous_slope = 0.0;
		double current_slope=0.0;
		int i=0;
		int pointer=0;//this will maintain count of the current slope
	 	
                while(i<rowcount){  
			if(i==0){
			        //this will store the first clculated slope
				previous_slope = getRegressionSlope(rowcount, i, 60);
			}else{
				//this will store the previous slope
				previous_slope=current_slope;
		       }	
		        i=i+60;
			//here the current calculated slope is stored
			current_slope = getRegressionSlope(rowcount, i, 60);
			pointer++;
			System.out.println("Current slope calculated as " + current_slope + " and previous slope is " + previous_slope);
			//the below condition will compare previous slope with current slope and 
			//to identify the transition to Takeoff phase 
			if (previous_slope < 1.0 && current_slope >= 1.0) {
				System.out.println("found phase");
				// this will store the exact time at which transition from taxi to takeoff is happening 
			        int startindex= (pointer*60);
	                        //int endindex=(((i+1)*10)+59);
	                        System.out.println("Taxi ended at time  " + startindex + "  second"); 
				break;
			}
			
		}
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

	 double getRegressionSlope(int rowcount, int offset,int length){
              
         	double mean_x=0.0;// this will store mean of time
	      	double mean_y=0.0;//this will store mean of altitude
	      	double totaltime=0.0;
	      	double[] altitude= new double[60];//will store all the altitude values for the defined range from start index
	      	int[] time= new int[60];//this will store all the time values for the defined range from start index 
              	int p=0;
	      	double sumaltitude=0.0;
	      	Arrays.fill(altitude,0);//this will initialize altitude array with zero
	      	Arrays.fill(time,0);//this will initilize time arry with zero

	       for(int j=offset;j<offset+length;j++)
               {  
			if(j<rowcount){
				//this is used to fetch altitude in CSV at a given index
               			altitude[p]=columnsList.get(ColNames.AltAGL.getValue()).getValue(j);
		             	//this will store all the time values against the altitude
				time[p]=j;
			     	sumaltitude=sumaltitude + altitude[p];
                             	totaltime= totaltime +  time[p];
		             	p++;
	                }
		}
                       
	       //this will store average of altitude
		mean_y=(sumaltitude/60);
		//this will store average of time
		mean_x=(totaltime/60);
					
                double  numerator = 0;
		double  denominator = 0;
                for(int k=0; k<length && offset+k < rowcount; k++){

	    		numerator += (altitude[k] - mean_y) * (time[k] - mean_x);
		 	denominator += (time[k]-mean_x)*(time[k] -mean_x);
	        }   
		//here final slope calculation for range is happening
	        slope=numerator/denominator;

	        System.out.println("slope " + count + " is " + slope);
                count++;
	     	
	        //here slope is returned to the getregression slope function	
                return slope;  
       	    
	 }
}		 
     

   

      	







    
         

     
	 




	  
	  
	 

              
          
              
	       

	      
	     
	      
		
		

		
		

	
		
	        	
          
		      
                  
               		   
		            
            		  
                           


		
                       
		       

