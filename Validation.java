import java.util.ArrayList;


public class Validation {



	private int rows;
	private String humanAnnotationFilename;  
	private ArrayList<ArrayList<Phase>>  automatedPhases ;
	private ArrayList<Phase> humanPhases ;
	private int count = 0;

  

	//output matches with manually entered phase is true positives"
   	int truepositives = 0;
   	//algorithm detects a phase incorrectly
   	int falsepositives = 0;
   	//algorith didn't detect but is done by user manually
   	int falsenegatives = 0;

	/**
	 * here row count of each csv file is initialized and then
	 * automatedPhases arraylist and human annotated arraylist
	 * is initialized to arraylist returned from humanannotations
	 * and automated phase(for each phase type) which is used for
	 * for validating correctly detected phases
	 * @param rows number of rows in csv file
	 * @param automatedPhases stored phases detected through automated algorithm
	 * @param humanPhases stored phases detected manually
	 */
   	public Validation (int rows, ArrayList<ArrayList<Phase>> automatedPhases, ArrayList<Phase> humanPhases) {

   		this.rows = rows;
   		this.humanPhases = humanPhases;
		this.automatedPhases = automatedPhases;
		/*for (int i = 0; i< humanPhases.size() ; i++) {
			System.out.println(humanPhases.get(i).phaseName + "<--------->" + humanPhases.get(i).startRow + "<-------->" + humanPhases.get(i).endRow);
		}*/	

		Validation(humanPhases, automatedPhases); 

   	}	   

	/**
	 * here humanannotations and automatedphases arraylist is
	 * passed for validating correctness of each phase and then
	 * truepositives, falsepositives and falsenegatives are caculated
	 */ 
	
   	public void Validation(ArrayList<Phase> humanPhases, ArrayList<ArrayList<Phase>> automatedPhases) {

   	
        	for(int a = 0; a < humanPhases.size() ; a++) {
                	int actualstart = humanPhases.get(a).startRow;
                	int actualend = humanPhases.get(a).endRow;

        
        		for (int i = 0; i < automatedPhases.size() ; i++) {
                		for( int j = 0; j < automatedPhases.get(i).size() ; j++) {

                        	//System.out.println("value is  " + automatedPhases.get(i).get(j).phaseName + "------" + automatedPhases.get(i).get(j).startRow + "------" +  automatedPhases.get(i).get(j).endRow );


                        		int foundstart = automatedPhases.get(i).get(j).startRow;
                        		int foundend = automatedPhases.get(i).get(j).endRow;
//                       		System.out.println("a is ---- " + a + "j is --- " + j + "i is --- "+ i +"actualstart is --->> " + actualstart  + " foundstart is ---->>>> "  + foundstart + " actual end is--->>  " + actualend + " found end is--->> " + foundend );
                        		//System.out.println(automatedPhases.get(i).get(j).phaseName +"<-------->" + actualstart  + " foundsstart---->>>> "  + foundstart + " actual end--->>  " + actualend + " found end is---- " + foundend );

                        		if( (((foundstart == actualstart) || (Math.abs(foundstart - actualstart) < 15))) && (((foundend == actualend) || (Math.abs(foundend - actualend) < 15))) ) {

                                		truepositives++;
						count++;
						
                        		}
                       			else if ((Math.abs(foundstart - actualstart ) > 10) && (Math.abs(foundend - actualend) < 10)) {
                            
                                		falsepositives++;

                       			} else if((truepositives + falsepositives) < humanPhases.size()) {

						falsenegatives = (humanPhases.size() - (truepositives + falsepositives) );
		       			}	
                     
				}
         		}
        	}	


        	System.out.println("True positives are " + truepositives );
        	System.out.println("False negatives are " + falsenegatives);
       	 	System.out.println("False positives are " + falsepositives);
	
   	}
}   

       	       


