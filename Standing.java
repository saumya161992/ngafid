import java.util.ArrayList;

public class Standing extends Phase{

	/**
	 * checking if the current
	 * row is in standing phase
	 * @param columns arraylist of flight column
	 * @param row row index
	 * @return true if the current row is in standing phase otherwise false
	 */

	public boolean check(ArrayList<FlightColumn> columns, int row){
		double altAgl = columns.get(ColNames.AltAGL.getValue()).getValue(row);
		double gndSpd = columns.get(ColNames.GndSpd.getValue()).getValue(row);

		//system.out.println(row + ": " + altAgl + " | " + gndSpd);

		return altAgl == 0 && gndSpd == 0;

	}
	


	@Override
	public String toString() {
		return "Standing";
	}



}	
