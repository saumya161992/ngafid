import java.util.ArrayList;

public abstract class Phase implements StateDecider{

	abstract public  void  check(ArrayList<FlightColumn> columns, int row);
}
