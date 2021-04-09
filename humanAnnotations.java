import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;



public class humanAnnotations {


   public String filename;
   public String phasename;
   public ArrayList<Phase> annotations;
   //public Phase 
   public int startRow;
   public int endRow; 

   public humanAnnotations( String anotationsFilename) { 

      this.filename = anotationsFilename;
    //  this.phaseName = phaseName;      
      //annotations = new ArrayList<Phase>();


   }	   

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
                        String[] columnNames = line.split(",");
                        /*for(int i = 0 ; i < columnNames.length ; i++){
                                System.out.println(columnNames[i] + "\t");
          
                        }*/
			phasename = "InitialClimb";
                        startRow = Integer.parseInt(columnNames[1]);
                        endRow = Integer.parseInt(columnNames[3]);
                        Phase phasedetected = new Phase(phasename, startRow, endRow);
			annotations.add(phasedetected);
                        
	      }	
	 
//	return annotations; 
     } catch (IOException e) {
            System.err.println("ERROR reading flight file: '" + annotationFilename + "'");
            e.printStackTrace();
            System.exit(1);
    }

     return annotations;
                     

  }	

}	
