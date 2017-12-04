package bpnetwork;

public interface Network {
	/**
	 * initialize random weights
	 */
	public void init();
	
	public void output(double [] input);
	
	public void adjust(double [] realoutput);
	
	public double activate(double in);

	public double [] getOutput();
}
