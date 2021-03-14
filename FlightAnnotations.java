/**
 * this class will validate how well the
 * automated algorithm for taxi identification
 * is performing for takeoff phase based on the
 * annotation flight files created
 */ 

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;

public class FlightAnnotations {
        
       /**
	* this will make sure that one command line 
	* argument is given and print an error message
	* and quit if not
	*/
	public static void main(String[] arguments) {
        
        if (arguments.length != 1) {
            System.err.println("ERROR: did not specify proper command line arguments, usage:");
            System.err.println("java ProcessFlightFile <flight filename>");
            System.exit(1);
        }

        //The first command line argument will be the flight filename
        String flightFilename = arguments[0];
        
	//output matches with manually entered phase is true positives"
	int truepositives = 0;
	//algorithm detects a phase incorrectly
	int falsepositives = 0;
	//algorith didn't detect but is done by user manually
	int falsenegatives = 0;

        try {
		       
		//create a buffered reader given the filename (which requires creating a File and FileReader object beforehand)
            	BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(flightFilename)));

                String line = "";
                int count = 0;
                //read the first line of the file
	        while ((line = bufferedReader.readLine()) != null) {
		  
			if (line.length() == 0 || line.charAt(0) == '#') {
		        	continue;
	         	}	       
            	 	//line = bufferedReader.readLine();
	    	 	//System.out.println(line);
            	 	String[] columnNames = line.split(",");
		 
		
		 	double actualstart = Double.parseDouble(columnNames[1]);
		 	double foundstart = Double.parseDouble(columnNames[2]);
		 	double actualend = Double.parseDouble(columnNames[3]);
		 	double foundend = Double.parseDouble(columnNames[4]);
		 	//System.out.println("actualstart is --->> " + actualstart  + " foundstart is ---->>>> "  + foundstart + " actual end is--->>  " + actualend + " found end is--->> " + foundend );
	         	if ((foundstart == actualstart) || (Math.abs(foundstart - actualstart) <= 20)) {	
			
				truepositives++;
		 	}
		 	else if(foundstart == 0.0) {
			 
				falsenegatives++;

	        	} 
		 	else if(actualstart == 0.0) {
	                 
	                	falsepositives++;
                 	}					 
		 	if ((foundend == actualend) || (Math.abs(foundend - actualend) <= 10)) {

				truepositives++;

		 	} 
		 	else if (foundend == 0.0)  {
			 
				falsenegatives++;

	         	} 
		 	else if (actualend == 0.0 ) {
	                
	                	falsepositives++;
	         	}  
		    	
		
		}
	    	System.out.println("True positives are " + truepositives);
		System.out.println("False negatives are " + falsenegatives);
		System.out.println("False positives are " + falsepositives);
        } catch (IOException e) {
          	System.err.println("ERROR reading flight file: '" + flightFilename + "'");
            	e.printStackTrace();
            	System.exit(1);
       }
    }
	    
}

