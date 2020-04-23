import java.util.ArrayList;
import java.util.Arrays;
import java.math.RoundingMode;
import java.text.DecimalFormat;
public  class Standing {

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
         public Standing(int rows,ArrayList<FlightColumn> columns){
              this.rowcount=rows;
              this.columnsList=columns;
              //double sumaltitude=0.0;
	      //int count=0;
             // double mean_x=0.0;
	      //double mean_y=0.0;
	      //double totaltime=0.0;
	      //double[] prod=new double[60];
	      //double[] mean_x_diff=new double[60];
	      //double[] mean_y_diff=new double[60];
              //double[] altitude= new double[60];
	      //int[] time= new int[60];


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
		}
                       
		       

		         mean_y=(sumaltitude/60);
		        
	        	 mean_x=(totaltime/60);
			
                 
	         
	          
                    
		    double  numerator = 0;
		    double  denominator = 0;
                   for(int k=0; k<60 && i+k < rowcount; k++){
	    	        numerator += (altitude[k] - mean_y) * (time[k] - mean_x);
			denominator += (time[k]-mean_x)*(time[k] -mean_x);
	           }   
	     
             slope[count]=numerator/denominator;

	    
	   
	     count++;*/
	
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
			//System.out.println(" now i is " + i);
		}
	 }

	 double getRegressionSlope(int rowcount, int offset,int length){
              
              double mean_x=0.0;
	      double mean_y=0.0;
	      double totaltime=0.0;
	      double[] altitude= new double[60];
	      int[] time= new int[60];
              // Standing s = new Standing(...);
	      int p=0;
	      double sumaltitude=0.0;
	      Arrays.fill(altitude,0);
	      Arrays.fill(time,0);

	       for(int j=offset;j<offset+length;j++)

		{      
		       //System.out.println("offset is " + j + " submission is " + (offset+length));
	                      
                        if(j<rowcount){
               		    altitude[p]=columnsList.get(ColNames.AltAGL.getValue()).getValue(j);
		            //System.out.println("inside if and altitude is  " +  altitude[p]);
			    time[p]=j;
			    //System.out.println(" time is " + time[p]);
            		    sumaltitude=sumaltitude + altitude[p];
                            totaltime= totaltime +  time[p];
		            p++;
	                }
		}
                       
		       

		mean_y=(sumaltitude/60);
		mean_x=(totaltime/60);
		//System.out.println("mean_x is  " + mean_x + " mean y is "+ mean_y);
			
                double  numerator = 0;
		double  denominator = 0;
                for(int k=0; k<length && offset+k < rowcount; k++){
	    	   numerator += (altitude[k] - mean_y) * (time[k] - mean_x);
		   denominator += (time[k]-mean_x)*(time[k] -mean_x);
	        }   
		//System.out.println(" numerator is " + numerator + " denominator is " + denominator);

	     
             slope=numerator/denominator;
	     System.out.println("slope " + count + " is " + slope);
             count++;
	     	     
             return slope;  
       	    
	 }






        }		 
     

     /* in this loop all calculated slopes 
      * will be compared
      * till an increasing slope is 
      * identified to identify Take OFF  phase from TAXI phase
      * one an increasing slope is identified
      * start and endindex for the slope is captured.
      * Then all ALTAGL values from the start and end index which i captured are added to phase transition array list
      */ 

      	

/*      for(int num=0;num<slope.length-1;num++){
	
	      
       if(slope[num]>=1.0){
	    int startindex= ((num+1)*60);
	    
	    int endindex=(((num+1)*10)+59);
	    System.out.println("Taxi ended at time  " + startindex + "  second"); 
	    System.out.println("start index is" + startindex + "end index is" + endindex); 
	    //for(int index=startindex;index<=endindex;index++){
               //phasetransition.add(columnsList.get(ColNames.AltAGL.getValue()).getValue(index));
	       //System.out.println("altitude at" + index + " is " + altitude[index]);
            //}		    
           break;	    
       }
       
    }
    System.out.println("not found comparitive slope");
 }  
      @Override// this could be used to print all slope values
      public String toString(){
      String outputString= new String();
      for(int i=0;i<slope.length;i++){
         //System.out.println("hhhhhh");
	 df.setRoundingMode(RoundingMode.UP); 
       outputString += "\t" + df.format(slope[i]) + "\n";
 }

     outputString += "\n"; 
     return outputString;
      }

   
}*/     

    	     
 	     
         /**
	 * checking if the current
	 * row is in standing phase
	 * @param columns arraylist of flight column
	 * @param row row index
	 * @return true if the current row is in standing phase otherwise false
	 */

	/*public void check(FlightColumn List, int rowcount){

		double[] altitude= new double[20];
		for(int i=0;i<20;i++){

		altitude[i] = columnsList.get(ColNames.AltAGL.getValue()).getValue(i);
                 System.out.println(altitude[i]);
		}
	
		//double gndSpd = columns.get(ColNames.GndSpd.getValue()).getValue(row);

		//system.out.println(row + ": " + altAgl + " | " + gndSpd);

		//return altAgl == 0 && gndSpd == 0;

	}
	


	/*@Override
	public String toString() {
		return "Standing";
	}*/

       

	


	
