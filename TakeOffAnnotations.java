/**
 * this class is used to validate how accurate the 
 * automated algorithm is identifying takeoff phase
 * based on the annotation files created for 
 * takeoff phase
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;


public class TakeOffAnnotations {

   /**
    * this will make sure that one coomand line argument is
    * given and print an error message and
    * quit if not
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
                        for (int i = 1 ; i < columnNames.length-1 ; i = i+2) {

				double actual = Double.parseDouble(columnNames[i]);
                                double found = Double.parseDouble(columnNames[i+1]);
                                if ((actual == found) || Math.abs(actual - found ) <= 10) {

					truepositives++;
				        

				}
		                else if (actual == 0.0) {
                                        
					falsepositives++;
					
				}	
			       	else if (found  == 0.0) {

					falsenegatives++;
					
				} 
					
                        }

                               
		  }
		  System.out.println("true positives are " + truepositives);
		  System.out.println("false negatives are " + falsenegatives);
		  System.out.println("falsepositives are "  + falsepositives);

		
	} catch (IOException e) {
                System.err.println("ERROR reading flight file: '" + flightFilename + "'");
                e.printStackTrace();
                System.exit(1);
       }
    }

}


