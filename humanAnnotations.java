import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;



public class humanAnnotations {


   //public String filename;
   //public String phaseName;
   public ArrayList<Phase> annotations;
   public Phase 
   public Phase 
   public Phase endRow ; 

   /*public humanAnnotations( String anotationsFilename,String phaseName) { 

      this.annotationsFilename = filename;
      this.phaseName = phaseName;      
      annotations = new ArrayList<Phase>();


   }*/	   

   public ArrayList<Phase> getAnnotationsFor(String annotationFilename) {

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
                        startRow = Double.parseDouble(columnNames[1]);
                        endRow = Double.parseDouble(columnNames[2]);
                    
			annotations.add(Phase);
                        
	      }	
	 
	return annotations; 

                         

  }	

}	
