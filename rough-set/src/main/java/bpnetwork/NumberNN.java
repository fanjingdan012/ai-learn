package bpnetwork;

public class NumberNN implements Network{
	
	private int in_size;
	private int hid_size;
	private int out_size;
	private double [][]inhid_weight;
	private double [][]hidout_weight;
	private double [] input;
	private double [] hid;
	private double [] output;
	private double learnRate=0.75;
	
	public double getLearnRate() {
		return learnRate;
	}

	public void setLearnRate(double learnRate) {
		this.learnRate = learnRate;
	}

	public NumberNN(int in,int hide,int out)
	{
		this.in_size=in;
		this.hid_size=hide;
		this.out_size=out;
		this.input=new double[in_size+1];
		this.hid=new double[hid_size+1];
		this.output=new double[out_size];
		this.inhid_weight=new double[in_size+1][hid_size];
		this.hidout_weight=new double[hid_size+1][out_size];
		
	}

	public double[][] getInhid_weight() {
		return inhid_weight;
	}

	public void setInhid_weight(double[][] inhidWeight) {
		inhid_weight = inhidWeight;
	}

	public double[][] getHidout_weight() {
		return hidout_weight;
	}

	public void setHidout_weight(double[][] hidoutWeight) {
		hidout_weight = hidoutWeight;
	}

	public int getIn_size() {
		return in_size;
	}

	public int getHid_size() {
		return hid_size;
	}

	public int getOut_size() {
		return out_size;
	}

	public double[] getInput() {
		return input;
	}

	public double[] getHid() {
		return hid;
	}

	public double[] getOutput() {
		double [] result=new double [out_size];
		int index=0;double max=0.0;
		for(int i=0;i<out_size;i++)
		{
			if(output[i]>=max)
			{
				max=output[i];
				index=i;
			}
			
		}
		for(int i=0;i<out_size;i++)
		{
			if(i!=index)
				result[i]=0;
			else result[i]=1;
		}
		return result;
	//	return output;
	}

	

	public void adjust(double [] realout) {
		double[] dhid=new double[hid_size+1];
		double[] dout=new double[out_size];
		for(int i=0;i<out_size;i++)
		{
			dout[i]=output[i]*(1-output[i])*(realout[i]-output[i]);
			
		}
		for(int i=0;i<hid_size+1;i++)
		{
			double diff=0;
			for(int j=0;j<out_size;j++)
			{
				diff=hidout_weight[i][j]*dout[j];
			}
			dhid[i]=hid[i]*(1-hid[i])*diff;
		}
		for(int i=0;i<out_size;i++)
		{
			
			for(int j=0;j<hid_size+1;j++)
			{
				hidout_weight[j][i]+=learnRate*dout[i]*hid[j];
			}
		}
		
	}
	public double activate(double in) {
		double temp=1/(1+Math.pow(Math.E, -in));
		return temp;
	}
	public void init() {
		this.input[in_size]=1;
		this.hid[hid_size]=1;
		for(int i=0;i<in_size+1;i++)
		{
			for(int j=0;j<hid_size;j++)
			{
				inhid_weight[i][j]=Math.random()-0.5;
			}
		}
		for(int i=0;i<hid_size+1;i++)
		{
			for(int j=0;j<out_size;j++)
			{
				hidout_weight[i][j]=Math.random()-0.5;
			}
		}
		
	}


	public void output(double[] inp) {
		for(int i=0;i<in_size;i++)
		{
			this.input[i]=inp[i];
		}
	//	this.input[in_size]=1;
		for(int i=0;i<hid_size;i++)
		{
			double sum=0.0;
			for(int j=0;j<in_size+1;j++)
			{
		//		System.out.println("input in output is "+input[j]);
				sum+=(inhid_weight[j][i]*input[j]);
			}
			hid[i]=activate(sum);
		}
		for(int i=0;i<out_size;i++)
		{
			double sum=0.0;
			for(int j=0;j<hid_size+1;j++)
			{
				sum+=(hidout_weight[j][i]*hid[j]);
			}
			output[i]=activate(sum);
		}
		
	}

}
