import java.util.ArrayList;



public class FlightColumn {
    private String name;
    private ArrayList<Double> values;

    /**
     * This constructrs a new FlightColumn object with a given name and
     * initializes it's values ArrayList to an empty ArrayList.
     *
     * @param name is the name of the column from the data file that
     * this object will hold the values for
     */
    public FlightColumn(String name) {
         this.name = name;
        values = new ArrayList<Double>();
    }

    /**
     * This appends a new value from the flight data file to
     * the array list for this flight column
     *
     * @param value is the value to append to the array list
     */
    public void addValue(double value) {
        values.add(value);
    }

    /**
     * This gets a particular value from the array list
     * at a given index.
     *
     * @param index is the index of the value
     */
    public double getValue(int index) {
        return values.get(index);
    }
    /**
     * Returns name of the column
     *
     * @return name of column
     */
    
    public String getName(){
	   return this.name;
    }
   
    /**
     * Getter for getting row number
     *
     * @return the size of the column ArrayList
     */
    public int getSize(){
	  return this.values.size();
    } 

    /**
     * Writes this FlightColumn to a String so we can
     * easily print it out.
     */
    public String toString() {
        //use a StringBuilder to construct a string
        //to print this out because it will be
        //faster than just using Strings and the + 
        //operator
        StringBuilder sb = new StringBuilder();
        sb.append(name + " {");

        boolean first = true;
        for (double value : values) {
            //print a comma if we we're 
            if (!first) sb.append(", ");
            sb.append(value);
            first = false;
        }

        sb.append("}");

        //return the StringBuilder contents converted
        //to a String
        return sb.toString();
    }
}
