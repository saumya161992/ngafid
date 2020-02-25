public class Standing extends Phase{

	public boolean isInThisPhase(Row r){
		// System.out.println("----> " + r.getColValue("GndSpd") + " | " + r.getColValue("AltAGL") + " | " + r.getColValue("VSpd"));
		return ((double) r.getColValue("GndSpd") == 0) && ((double) r.getColValue("AltAGL") == 0) && ((double) r.getColValue("VSpd") == 0);

	}
}	
