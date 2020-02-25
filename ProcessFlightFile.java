import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

            //after the file has been read print out all the
            //FlightColumns
	    //
	    ArrayList<Row> rows = new ArrayList<>();
	    StateDecider sd = new StateDecider();
	    int numOfRows = columns.get(0).size();

	    /*for (int i = 0; i < numOfRows; i++) {
		Row r = new Row(i);
	    	for (int j = 0; j < columns.size(); j++) {
		    double cellVal = columns.get(j).get(i);
		    r.addCellVal(cellVal);
		}
		r.setState(sd.determine(r));
		rows.add(r);
	    }

	    Phase p = r.getState();*/
	    Phase[] phases = {
	    	Standing,
		Approach,
		Landing,
	    };

	    int numOfRows = columns.get(0).getSize();
	    for(int i=0;i<numOfRows;i++){
		    Row r = new Row(i);
		    for(int j=0;j<columns.size();j++){
		    	double columnval=column.get(j).get(i);
		    	// ArrayList<FlightColumn> columns
		    	r.addColumnlVal(columns.get(j).getName(), columnVal);
		    }

		    for (Phase phase : phases) {
		    	if (phase.isInThisPhase(r)) {
				r.setPhase(phase);
			}	
		    }
           }		    

		    

            for (FlightColumn column : columns) {
                System.out.println("\t" + column);
            }

        } catch (IOException e) {
            System.err.println("ERROR reading flight file: '" + flightFilename + "'");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
