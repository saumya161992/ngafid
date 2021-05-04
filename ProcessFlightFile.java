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
import java.util.HashSet;
import java.util.stream.Collectors;


/**
 * this class is processing the data file
 * and stores the detected phase and ranges 
 * of start and end of each phase inside
 * arraylist after retrieving all flight 
 * files from manual annotations text file
 */

public class ProcessFlightFile {
	public static void main(String[] arguments) {
		//make sure that two command line argument is given and
		//print an error message and quit if not
		if (arguments.length != 2) {
			System.err.println("ERROR: did not specify proper command line arguments, usage:");
			System.err.println("java ProcessFlightFile <flight filename>");
			System.exit(1);
		}

		//The first command line argument will be the manual flight filename and second is type of phase
		String phasetype = arguments[1];
		String manualannotationtext = arguments[0];
		ArrayList<ArrayList<Phase>> Allautomatedphases = new ArrayList<ArrayList<Phase>>() ; // this will store all phases detected  by automated phasetype algorithm for all flight file
		ArrayList<Phase> humanPhases ; // this will store all phases for all flight files detected manually
		ArrayList<Phase> automatedphases = new ArrayList(); // this will store phases returned for each flight file by automated phasetype algorithm
		HashSet<String> flightfiles = new HashSet<String>(); //this is used so that repeated names of flightfiles are not stored from manual annotation file.



		String line1 = "";
		try {
                        
			BufferedReader bufferedReadernew = new BufferedReader(new FileReader(new File(manualannotationtext)));
			while ((line1 = bufferedReadernew.readLine()) != null) {

                                //reads each line of manual annotations file
				String[] aircraft = line1.split(",");
				flightfiles.add(aircraft[0]);
			}	
			bufferedReadernew.close();

                                
		 } catch (IOException e) {
                        System.err.println("ERROR reading flight file: '" + manualannotationtext + "'");
                        e.printStackTrace();
                        System.exit(1);
                }

	



                for ( String file : flightfiles) {

			try {

				//create a buffered reader given the filename (which requires creating a File and FileReader object beforehand)
				BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(file)));

				String line = "";

				//read the first line of the flight file
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
						//so we can iterate over each of them
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
					

					/**
					 * here we create an object of each annotated phase type 
					 * class for each flight file and then check function inside
					 * annotated phase type is called which returns all detected 
					 * phases which are stored in allautomated arraylist
					 * next all detected phases from manual annotations files
					 * are stored in humanannotations arraylist
					 * next we create an object of validate class to validate
					 * number of phases which are detected correctly
					 */
					//String[] phasetypes = {"Standing", "Taxi", "InitialClimb" };
					int numOfRows = columns.get(0).getSize();
					System.out.println(" number of rows in CSV file are " + numOfRows);
					// here we pass row count and columns arraylist to standing class
					System.out.println("Enter phase type 1 for standing phase  and type 2 to identify Transition from taxi to takeoff phase "  + " \n " + "3 for Initial Climb phase and 4 for Enroute phase ");
					

					int type = Integer.valueOf(phasetype);
					switch(type) {
						case 1:	Standing ST = new Standing(numOfRows, columns);
							
							break;
						case 2:	Takeoff TT = new Takeoff(numOfRows, columns);
							//automatedphases = TT.check(numOfRows);
							//System.out.println("the length is  "  + automatedphases.size());
							Allautomatedphases.add(TT.check(numOfRows));
							
							Humanannotations HCT  = new Humanannotations(manualannotationtext,"TakeOff");

							humanPhases = HCT.getAnnotationsFor(manualannotationtext);
							Validation vcT = new Validation(numOfRows,  Allautomatedphases, humanPhases);
							
							break;
						case 3: InitialClimb IC = new InitialClimb(numOfRows,columns);
							
							Allautomatedphases.add(IC.check(numOfRows));

							Humanannotations HC  = new Humanannotations(manualannotationtext, "InitialCimb");
							humanPhases = HC.getAnnotationsFor(manualannotationtext);
							Validation vc = new Validation(numOfRows,  Allautomatedphases, humanPhases);
							break;	
						case 4: Enroute EN = new Enroute(numOfRows,columns);
							int k = 0;
							while (k < numOfRows) {

                        					double height = columns.get(ColNames.AltAGL.getValue()).getValue(k);

                        					if (height >= 1000) {

                                					break;
                        					}

                        					k++;

							}
							Allautomatedphases.add(EN.check(numOfRows, k , columns));
							Humanannotations HCE  = new Humanannotations(manualannotationtext, "Cruise");
                                                        humanPhases = HCE.getAnnotationsFor(manualannotationtext);
                                                        Validation vcE = new Validation(numOfRows,  Allautomatedphases, humanPhases);

							//System.out.println(Allautomatedphases.size());

							break;	
						case 5 : Maneuvering MN = new Maneuvering(numOfRows,columns);
                                                         break;	
						default:break;
					}	


				} catch (IOException e) {
					System.err.println("ERROR reading flight file: '" + file + "'");
					e.printStackTrace();
					System.exit(1);
				}
			}
		
	}	


	
}
