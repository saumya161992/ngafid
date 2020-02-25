import java.util.*;
/**
 * this class represents eac row from a csv file
 */ 

public class Row<K,V> {
	private int _id;
	private HashMap<K, V> _cols = new HashMap<K, V>();
	private Phase _phase;

	public Row(int index) {
		this._id = index;
	}

	public int getId() {
		return this._id;
	}

	public Phase getPhase() {
		return this._phase;
	}

/**
 * this is adding a column to the current row
 * @param colName name of the column
 * @param value value of the columnname in the current row
 */
	public void addColVal(K colName, V value) {
	   this._cols.put(colName,value);
	}
/**
 * this method set the phase of the current row
 * @param phase of the row
 */	

	public void setPhase(Phase phase) {
          this._phase=phase;
	}
       public V  getColValue(K colName){
	       return this._cols.get(colName);
       }

}

