import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;
/*
 *this class is processing the data file
 * and storee the phase and  ranges 
 *inside the hashmap of string and arraylist
 *
 */
public class ProcessFlightFile {
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

        try {

            //create a buffered reader given the filename (which requires creating a File and FileReader object beforehand)
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(flightFilename)));

            String line = "";

            //read the first line of the file
            line = bufferedReader.readLine();
            String[] columnNames = line.split(",");

            //make an array list of FlightColumn objects which will
            //hold the data for each column
            ArrayList<FlightColumn> columns = new ArrayList<FlightColumn>();

            System.out.println("File columns are: ");
            for (String columnName : columnNames) {
                //for each column we just parsed from the file
                //create a new FlightColumn object and add it
                //to the columns ArrayList
                FlightColumn newColumn = new FlightColumn(columnName);
                columns.add(newColumn);
                System.out.println("\t" + newColumn);
            }

            //read the next line of the file, which is the units
            //for each column. We can ignore this because
            //we dont need this information
            line = bufferedReader.readLine();


            //read the rest of the file line by line
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line); 

                String[] valueStrings = line.split(",");

                //we know that there should be the same number of
                //values in each line as there are flight columms
                //so we can interate over each of them
                for (int i = 0; i < valueStrings.length; i++) {
                    //System.out.println("\t" + value);

                    //Double.parseDouble(String) converts a String to a
                    //double
                    double value = Double.parseDouble(valueStrings[i]); 

                    //append each value to the appropriate FlightColumn
                    columns.get(i).addValue(value);

                    //print out the updated FlightColumn object
                    //System.out.println("\t" + columns.get(i));
                }

            }
            bufferedReader.close();
            int j=0;
	    for(FlightColumn column : columns){
		    j=j+1;
		    //System.out.println("*************************************this is j********************************\n" );
            System.out.println("\t\n" +  column );
	    }
           		    

            //after the file has been read print out all the
            //FlightColumns
	    //
	    /**
	     * creating phase array of different 
	     * types of phases
	     */
              
	     
	     
	    String[] phasetypes = {"Standing", "Taxi", "InitialClimb" };
		//new Taxi(),
		//Approach,
		// Landing,
	    
            /**
	     * using for loop for getting all rows individually
	     * and determine the phase
	     * using hashmap to store the phase identified which has 
	     * arraylist of start and end time of phase
	     *
	     */
	   
	   
	    int numOfRows = columns.get(0).getSize();
	    System.out.println(" number of rows in CSV file are " + numOfRows);
	    HashMap<String, ArrayList<int[]>> phaseRanges= new HashMap<>();
             for (String phase : phasetypes)
		     phaseRanges.put(phase, new ArrayList<int[]>());
	    //for(int i=0;i<numOfRows;i++){
                   
		    //for(Phase phase : phases) {
			    /**
			     * checks if phase is identified boolean
			     *  of phase function return true
			     *  and then start and end time of phase in flight 
			     *  duration is stored
			     *   list
			     *   if the current row meets the condition of  certain phase then we update the ranges accordingly
			     */  



	     // here we pass row count and columns arraylist to standing class
	      Standing ST=new Standing(numOfRows,columns);
	      System.out.println(ST);
                       //ST.check();	      
	    // Phase.check(columns,numOfRows);
		       //	{
				/*ArrayList<int[]> list = phaseRanges.get(phase.toString());
		        	if(list.size()== 0){
					list.add(new int[]{i,i});
		       	 		continue;	
			
				}
				int[] lastItem = list.get(list.size()-1);
				if(lastItem[1] == i-1)
					lastItem[1] = i;//updating second elemnt inside the array
				else
				  list.add(new int[]{i,i});
			}
		    }
      }
	   /* converts phase arraylist of array to 
	    * a string to be displayed on console
	    */ 


	   //phaseRanges.keySet().stream().forEach(k -> System.out.println(k + ": " + phaseRanges.get(k).stream().map(Arrays::toString).collect(Collectors.toList())));*/


            /*for (FlightColumn column : columns) {
                System.out.println("\t" + column);
            }*/

        } catch (IOException e) {
            System.err.println("ERROR reading flight file: '" + flightFilename + "'");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
