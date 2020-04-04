import java.util.ArrayList;

public  class Standing {

         private int rowcount;// this is CSV file row count
	 private ArrayList<FlightColumn> columnsList; //this will be used to read Columns arraylist generated in ProcessFlightFile
         double[] slope=new double[245];// this will save all calculated slopes
	 private ArrayList<Double> phasetransition=new ArrayList<>();//this arraylist will store those 20 altitudes against which transition is there
	 
	 /*
	  * here we calculate the average of time for 20 values
	  * and average of altitude for 20 values
	  * and then calculate the slope
	  */
         public Standing(int rows,ArrayList<FlightColumn> columns){
              this.rowcount=rows;
              this.columnsList=columns;
              //double[] slope=new double[rowcount/20];
	      double sumaltitude=0;
	      int count=0;
	      double totaltime=0;
	      double prodsum=0;
	      //double[] slope=new double[100];
	      
	      double[] prod=new double[20];
	      double[] mean_x_diff=new double[20];
	      double[] mean_y_diff=new double[20];
              double[] altitude= new double[20];
	      int[] time= new int[20];
	      double mean_x_square=0;
	      for(int i=0;i<rowcount;i+=20)
	      {
		int p=0;      
                for(int j=i;j<i+20;j++)
		{       
                        //
			//System.out.println("value of " + j );
			//System.out.println("the size of list " + columnsList.get(ColNames.AltAGL.getValue()));
			if(j<rowcount){
               		altitude[p]=columnsList.get(ColNames.AltAGL.getValue()).getValue(j);
			
	        	time[p]=j;
            		sumaltitude=sumaltitude + altitude[p];
               	    		totaltime= totaltime + time[p];
		        p++;		
                 }
		}
                      // System.out.println("totoal time is" + totaltime);

	        	double mean_x=(sumaltitude/20);
	        	double mean_y=(totaltime/20);
                        //System.out.println("mean of x is :"+ mean_x);
	               // System.out.println("mean of y is :" +mean_y);
	          

                   for(int k=0;k<20;k++){		
                        mean_x_diff[k]=(altitude[k]-mean_x);
			//System.out.println("mean x diff" + k + "is" + mean_x_diff[k]); 
	    		mean_x_square=mean_x_square + (mean_x_diff[k] * mean_x_diff[k]);
	    		mean_y_diff[k]=(time[k]-mean_y);
	    		prod[k]=(mean_x_diff[k])*(mean_y_diff[k]);
	    		prodsum=prodsum + prod[k];
	     }   
	     

	    slope[count]= prodsum/mean_x_square;
	    count++;
	 }
      

     /* in this loop all calculated slopes 
      * will be compared
      * till an increasing slope is 
      * identified to identify INITIAL CLIMB  phase from TAXI phase
      * one an increasing slope is identified
      * start and endindex for the slope is captured.
      * Then all ALTAGL values from the start and end index which i captured are added to phase transition array list
      */ 

      for(int num=0;num<slope.length-1;num++){
	      
       if((slope[num+1]-slope[num])>0.1){
	    int startindex= ((num+1)*20);
	    
	    int endindex=(((num+1)*20)+19);
	    System.out.println("start index is" + startindex + "end index is" + endindex); 
	    for(int index=startindex;index<=endindex;index++){
               phasetransition.add(columnsList.get(ColNames.AltAGL.getValue()).getValue(index));
	       //System.out.println("altitude at" + index + " is " + altitude[index]);
            }		    
	    
       }
       break;       
    }
 }  
     /* @Override// this could be used to print all slope values
      public String toString(){
      String outputString= new String();
      for(int i=0;i<slope.length;i++){
         //System.out.println("hhhhhh"); 
       outputString += "\t" + slope[i] + "\n";

      }

     outputString += "\n"; 
     return outputString;
      }*/

   /* this toString function is used to print all altitude
    * values in  phaseTransition  arraylist so that
    */

     @Override
     public String toString(){
	     System.out.println("Transition from Taxi to initial climb is happening at below altitude");
     String outputString=new String();
     for(int i=0;i<phasetransition.size();i++){
     outputString +="\t" + phasetransition.get(i) + "\n";
     }
     return outputString;

    	     
 }	     
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

	



}	
