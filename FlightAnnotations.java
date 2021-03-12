import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;

public class FlightAnnotations {
    public static void main(String[] arguments) {
        //make sure that one command line argument is given and
        //print an error message and quit if not
        if (arguments.length != 1) {
            System.err.println("ERROR: did not specify proper command line arguments, usage:");
            System.err.println("java ProcessFlightFile <flight filename>");
            System.exit(1);
        }

        //The first command line argument will be the flight filename
        String flightFilename = arguments[0];
        //String phasetype = arguments[1];
	int truepositives = 0;

       try {

            //create a buffered reader given the filename (which requires creating a File and FileReader object beforehand)
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(flightFilename)));

            String line = "";
            int count = 0;
            //read the first line of the file
	    while((line = bufferedReader.readLine()) !=null){
		  
		 if(line.length() == 0 || line.charAt(0) == '#'){
		       continue;
	         }	       
            	//line = bufferedReader.readLine();
	    	System.out.println(line);
            	String[] columnNames = line.split(",");
		/*for(int i = 0 ; i < columnNames.length ; i++){
                	System.out.println(columnNames[i] + "\t");
          
		}*/
		double actual = Double.parseDouble(columnNames[3]);
		double found = Double.parseDouble(columnNames[1]);
		//System.out.println("---->>>> "  + val1);
	        if((found == actual)||(Math.abs(found-actual)<=10)){	
				//||((found - actual) < 5) || ((actual - found) <5)) {
			truepositives++;
		}	
		
	    }
	    System.out.println("true positives are " + truepositives);
       } catch (IOException e) {
            System.err.println("ERROR reading flight file: '" + flightFilename + "'");
            e.printStackTrace();
            System.exit(1);
       }
    }
	    
}

