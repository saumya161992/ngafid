/**
 * enumeration of all column names
 * associated with their index value
 */
public enum ColNames{

	AltAGL(0),
	//Time(1),
	E1_CHT1(1),
	E1_CHT2(2),
	E1_CHT3(3),
	E1_CHT4(4),
	E1_EGT1(5),
	E1_EGT2(6),
	E1_EGT3(7),
	E1_EGT4(8),
	E1_OilP(9),
	E1_OilT(10),
	E1_RPM(11),
	FQtyL(12),
	FQtyR(13),
	GndSpd(14),
	IAS(15),
	LatAc(16),
	NormAc(17),
	OAT(18),
	Pitch(19),
	Roll(20),
	TAS(21),
	volt1(22),
	volt2(23),
	VSpd(24),
	VSpdG(25);
	




	private final int _idx;

	/**
 	* this will store the index of enum 
 	* column names
 	* @param  _idx will store index of columnname for future reference
 	*/

	private ColNames (int idx) {
		_idx=idx;
	}

	/**
 	* this will return the index
 	* @return _idx to be returned
 	*/

 	public int getValue() {

		return _idx; 

	}
}	



