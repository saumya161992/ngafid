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
   	public int startRow;
   	public int endRow; 
	
	public humanAnnotations( String anotationsFilename, String name) { 

      		this.filename = anotationsFilename;
    		this.phasename = name;      
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
//				System.out.println(columnNames[0]);
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
	 
			//	return annotations; 
     		} catch (IOException e) {
            		System.err.println("ERROR reading flight file: '" + annotationFilename + "'");
            		e.printStackTrace();
            		System.exit(1);
	       }

 	       return annotations;
                     

	}	

}	
