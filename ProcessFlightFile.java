import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
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
		if (arguments.length != 2) {
			System.err.println("ERROR: did not specify proper command line arguments, usage:");
			System.err.println("java ProcessFlightFile <flight filename>");
			System.exit(1);
		}

		//The first command line argument will be the flight filename
		//String flightFilename = arguments[0];
		String phasetype = arguments[1];
		String manualannotationtext = arguments[0];
		ArrayList<ArrayList<Phase>> Allautomatedphases = new ArrayList<ArrayList<Phase>>() ;
		ArrayList<Phase> humanPhases ;
		ArrayList<Phase> automatedphases = new ArrayList();
                


		String line1 = "";
		try {

			BufferedReader bufferedReadernew = new BufferedReader(new FileReader(new File(manualannotationtext)));
			while ((line1 = bufferedReadernew.readLine()) != null) {

				//String line1 = "";
				//read the first line of the file
				//line1 = bufferedReader.readLine();
				String[] columnNames1 = line1.split(",");
                                





				try {

					//create a buffered reader given the filename (which requires creating a File and FileReader object beforehand)
					BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(columnNames1[0])));

					String line = "";

					//read the first line of the file
					line = bufferedReader.readLine();
					String[] columnNames = line.split(",");

					//make an array list of FlightColumn objects which will
					//hold the data for each column
					ArrayList<FlightColumn> columns = new ArrayList<FlightColumn>();

					//System.out.println("File columns are: ");
					for (String columnName : columnNames) {
						//for each column we just parsed from the file
						//create a new FlightColumn object and add it
						//to the columns ArrayList
						FlightColumn newColumn = new FlightColumn(columnName);
						columns.add(newColumn);
						// System.out.println("\t" + newColumn);
					}

					//read the next line of the file, which is the units
					//for each column. We can ignore this because
					//we dont need this information
					line = bufferedReader.readLine();


					//read the rest of the file line by line
					while ((line = bufferedReader.readLine()) != null) {
						//System.out.println(line); 

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
					/*for(FlightColumn column : columns){
					  j=j+1;
					  System.out.println("\t\n" +  column );
					  }*/

					//after the file has been read print out all the
					//FlightColumns

					/**
					 * creating phase array of different 
					 * types of phases
					 *
					 **/
					//String[] phasetypes = {"Standing", "Taxi", "InitialClimb" };
					int numOfRows = columns.get(0).getSize();
					System.out.println(" number of rows in CSV file are " + numOfRows);
					// here we pass row count and columns arraylist to standing class
					System.out.println("Enter phase type 1 for identifying Transition from standing phase to taxi phase and type 2 to identify Transition from taxi to takeoff phase");
					//Scanner sc=new Scanner(System.in);

					int type = Integer.valueOf(phasetype);
					switch(type) {
						case 1:	Standing ST = new Standing(numOfRows, columns);
							System.out.println(ST);
							break;
						case 2:	Takeoff TT = new Takeoff(numOfRows, columns);
							//automatedphases = TT.check(numOfRows);
							//System.out.println("the length is  "  + automatedphases.size());
							Allautomatedphases.add(TT.check(numOfRows));
							humanAnnotations HCT  = new humanAnnotations(manualannotationtext,"TakeOff");

							humanPhases = HCT.getAnnotationsFor(manualannotationtext);
							Validation vcT = new Validation(numOfRows,  Allautomatedphases, humanPhases);
							break;
						case 3: InitialClimb IC = new InitialClimb(numOfRows,columns);
							//System.out.println(IC);
							Allautomatedphases.add(IC.check(numOfRows));

							humanAnnotations HC  = new humanAnnotations(manualannotationtext, "InitialCimb");
							humanPhases = HC.getAnnotationsFor(manualannotationtext);
							Validation vc = new Validation(numOfRows,  Allautomatedphases, humanPhases);
							break;	
						case 4: Enroute EN = new Enroute(numOfRows,columns);
							break;	
						default:break;
					}	


				} catch (IOException e) {
					System.err.println("ERROR reading flight file: '" + columnNames1[0] + "'");
					e.printStackTrace();
					System.exit(1);
				}
			}
		} catch (IOException e) {
			//System.err.println("ERROR reading flight file: '" + columnNames1[0] + "'");
			e.printStackTrace();
			System.exit(1);
	        }


	}
}
