import java.util.ArrayList;


public class Validation {



   public int rows;
   public String humanAnnotationFilename;  
     ArrayList<ArrayList<Phase>>  automatedPhases ;
     ArrayList<Phase> humanPhases ;

  

   //output matches with manually entered phase is true positives"
   int truepositives = 0;
   //algorithm detects a phase incorrectly
   int falsepositives = 0;
   //algorith didn't detect but is done by user manually
   int falsenegatives = 0;

   public Validation (int rows, ArrayList<ArrayList<Phase>> automatedPhases, ArrayList<Phase> humanPhases) {

      this.rows = rows;
      this.humanPhases = humanPhases;

      this.automatedPhases = automatedPhases;

      Validation(humanPhases, automatedPhases); 

   }	   
	
   public void Validation(ArrayList<Phase> humanPhases, ArrayList<ArrayList<Phase>> automatedPhases) {
	   
	    
        //ArrayList<Phase> humanPhases = humanAnnotations.getAnnotationsFor(humanannotationFilename);


	/*for (int a = 0; a < humanPhases.size() ; a++) {
		 
		//System.out.println("value is  " + humanPhases.get(i).phaseName + "------" + humanPhases.get(i).startRow + "------" +  humanPhases.get(i).endRow );
		//int actualstart = humanPhases.get(a).startRow;       
                //int actualend = humanPhases.get(a).endRow;
		 int actualstart = humanPhases.get(a).startRow;
                 int actualend = humanPhases.get(a).endRow;*/

	int a = 0;
        //System.out.println("size is " + automatedPhases.size());
	for (int i = 0; i < automatedPhases.size(); i++) {
		for( int j = 0; j < automatedPhases.get(i).size() && a < humanPhases.size() ; j++, a++) {
			
			System.out.println("value is  " + automatedPhases.get(i).get(j).phaseName + "------" + automatedPhases.get(i).get(j).startRow + "------" +  automatedPhases.get(i).get(j).endRow );

			int actualstart = humanPhases.get(a).startRow;
                        int actualend = humanPhases.get(a).endRow;


                        int foundstart = automatedPhases.get(i).get(j).startRow;
                        int foundend = automatedPhases.get(i).get(j).endRow;
                        System.out.println("a is ---- " + a + "j is --- " + j + "i is --- "+ i +"actualstart is --->> " + actualstart  + " foundstart is ---->>>> "  + foundstart + " actual end is--->>  " + actualend + " found end is--->> " + foundend );
			 if( ((foundstart == actualstart) || (Math.abs(foundstart - actualstart) < 10))) {

                                truepositives++;
                        }
                        else if (Math.abs(foundstart - actualstart ) > 10) {

                                falsenegatives++;

                        }
		        if( ((foundend == actualend) || (Math.abs(foundend - actualend) < 10))) {

                                truepositives++;
                        }
                        else if (Math.abs(foundend - actualend ) > 10) {

                                falsenegatives++;

                        }
	
                    
               }
	}   

        System.out.println("True positives are " + truepositives );
        System.out.println("False negatives are " + falsenegatives);
        System.out.println("False positives are " + falsepositives);
	
   }
}   

       	       


