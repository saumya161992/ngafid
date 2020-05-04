import java.util.ArrayList;
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
	 
	 /*
	  * here we calculate the average of time for 20 values
	  * and average of altitude for 20 values
	  * and then calculate the slope
	  */
         public Takeoff(int rows,ArrayList<FlightColumn> columns){
              this.rowcount=rows;
              this.columnsList=columns;
              
	         check(rowcount);

	      
	     /* for(int i=0;i<rowcount;i+=60)
	      {
		int p=0;
		sumaltitude=0.0;
		totaltime=0.0;
		mean_x=0.0;
		mean_y=0.0;
	        Arrays.fill(altitude,0);
		Arrays.fill(time,0);
		// Arrays.fill(prod,0);
	        	
                /*for(int j=i;j<i+60;j++)
		{       
                        if(j<rowcount){
               		    altitude[p]=columnsList.get(ColNames.AltAGL.getValue()).getValue(j);
		            time[p]=j;
            		    sumaltitude=sumaltitude + altitude[p];
                            totaltime= totaltime +  time[p];
		            p++;
	                }
		}*/
                       
  }

	  void check(int rowcount) {
		double previous_slope = 0.0;
		double current_slope=0.0;
		int i=0;
		int pointer=0;
	 	//for (int i = 0; i < rowcount; i += 60) {
		  //System.out.println("inside check");
                   while(i<rowcount){  
			if(i==0){
			  previous_slope = getRegressionSlope(rowcount, i, 60);
			  
			}else{
			  previous_slope=current_slope;
		       }	
		        i=i+60;
			current_slope = getRegressionSlope(rowcount, i, 60);
			pointer++;
			System.out.println("Current slope calculated as " + current_slope + " and previous slope is " + previous_slope);
			if (previous_slope < 1.0 && current_slope >= 1.0) {
				System.out.println("hhhhhhhhhhhhh");
				System.out.println("found phase");
			        int startindex= (pointer*60);
	                        //int endindex=(((i+1)*10)+59);
	                        System.out.println("Taxi ended at time  " + startindex + "  second"); 
				break;
			}
			i=i+60;
			
		}
	 }

	 double getRegressionSlope(int rowcount, int offset,int length){
              
              double mean_x=0.0;
	      double mean_y=0.0;
	      double totaltime=0.0;
	      double[] altitude= new double[60];
	      int[] time= new int[60];
              int p=0;
	      double sumaltitude=0.0;
	      Arrays.fill(altitude,0);
	      Arrays.fill(time,0);

	       for(int j=offset;j<offset+length;j++)

		{      
		      	                      
                             if(j<rowcount){
               		     altitude[p]=columnsList.get(ColNames.AltAGL.getValue()).getValue(j);
		             time[p]=j;
			     sumaltitude=sumaltitude + altitude[p];
                             totaltime= totaltime +  time[p];
		             p++;
	                }
		}
                       
		       

		mean_y=(sumaltitude/60);
		mean_x=(totaltime/60);
					
                double  numerator = 0;
		double  denominator = 0;
                for(int k=0; k<length && offset+k < rowcount; k++){
	    	   numerator += (altitude[k] - mean_y) * (time[k] - mean_x);
		   denominator += (time[k]-mean_x)*(time[k] -mean_x);
	        }   
		

	     
              slope=numerator/denominator;
	      System.out.println("slope " + count + " is " + slope);
              count++;
	     	     
             return slope;  
       	    
	 }






        }		 
     

   

      	







    
         

     
	 




	  
	  
	 

              
          
              
	       

	      
	     
	      
		
		

		
		

	
		
	        	
          
		      
                  
               		   
		            
            		  
                           


		
                       
		       

