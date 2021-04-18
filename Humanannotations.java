import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;



public class Humanannotations {

        public String filename;
        public String phasename;
        public ArrayList<Phase> annotations;
        public int startRow;
        public int endRow;

	/** here manualannotations text file is passes
	 *  and the name of the phase for which
	 *  manual annotations is created is passed
	 *  and then text file name and phase name is
	 *  initialized
	 *  @param annotationsFilename name of text file
	 *  @param name is the phase for which annotations are done
	 */  

        public Humanannotations( String anotationsFilename, String name) {

                this.filename = anotationsFilename;
                this.phasename = name;
                
	}

	/** here file name is passed and each line of text file
	 *  is read to store detected phase
	 *  first phase object will be created to store detected phase
	 *  and then the phase detected will be stored in annotations 
	 *  arrylist
	 *  @param annotationsFilename is the name of the textfile storing 
	 *  phase name and start and end time of each phase detected
	 *  @return returns arraylist of human annotated phases
	 */ 

        public ArrayList<Phase> getAnnotationsFor(String annotationFilename) {


                try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(annotationFilename)));


                        annotations = new ArrayList<Phase>();

                        String line = "";
                        int count = 0;
                        //read the first line of the file
                        while ((line = bufferedReader.readLine()) != null) {

                                if (line.length() == 0 || line.charAt(0) == '#') {
                                        continue;
                                }
                                //line = bufferedReader.readLine();
                                //System.out.println(line);
//                              System.out.println(columnNames[0]);
                                String[] columnNames = line.split(",");
                                /*for(int i = 0 ; i < columnNames.length ; i++){
                                        System.out.println(columnNames[i] + "\t");
          
                                }*/
                                //System.out.println(columnNames[0]);


                                startRow = Integer.parseInt(columnNames[1]);
                                endRow = Integer.parseInt(columnNames[2]);
                                Phase phasedetected = new Phase(this.phasename, startRow, endRow);
                                annotations.add(phasedetected);
                        }

                } catch (IOException e) {
                        System.err.println("ERROR reading flight file: '" + annotationFilename + "'");
                        e.printStackTrace();
                        System.exit(1);
               }

               return annotations;


        }

}

