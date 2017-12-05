package algorithm.BP;

import java.util.Random;


public abstract class BP {
	public double[][] trainInput;
	public double[][] trainTarget;
	
	
	/** input vector. */
	public double[] input;
	/** hidden layer. */
	public double[] hidden;
	/** output layer. */
	public double[] output;
	/** target. */
	public double[] target;

	/**
	 * delta vector of the hidden layer .
	 */
	public double[] hidDelta;
	/**
	 * delta vector of the output layer.
	 */
	private double[] optDelta;

	/**
	 * learning rate.
	 */
	public double eta;

	/**
	 * weight matrix from input layer to hidden layer.
	 */
	public double[][] iptHidWeights;
	/**
	 * weight matrix from hidden layer to output layer.
	 */
	public double[][] hidOptWeights;
	
	/**
	 * weight matrix from input layer to hidden layer.
	 */
	public double[][] iptHidDeltaWeights;
	/**
	 * weight matrix from hidden layer to output layer.
	 */
	public double[][] hidOptDeltaWeights;
	public double[] hidSumForDelta;

	/**
	 * previous weight update.
	 */
	//private double[][] iptHidPrevUptWeights;
	/**
	 * previous weight update.
	 */
	//private double[][] hidOptPrevUptWeights;

	public double optErrSum = 0d;

	//public double hidErrSum = 0d;

	public Random random;
	
	public int trainTimes = 10;

	/**
	 * Constructor.
	 * <p>
	 * <strong>Note:</strong> The capacity of each layer will be the parameter
	 * plus 1. The additional unit is used for smoothness.
	 * </p>
	 * 
	 * @param inputSize
	 * @param hiddenSize
	 * @param outputSize
	 * @param eta
	 * @param momentum
	 * @param epoch
	 */
	public BP(int inputSize, int hiddenSize, int outputSize, double eta,
			double momentum) {

		input = new double[inputSize+1];
		hidden = new double[hiddenSize+1];
		output = new double[outputSize];
		target = new double[outputSize];

		hidDelta = new double[hiddenSize+1];
		optDelta = new double[outputSize];

		iptHidWeights = new double[inputSize+1][hiddenSize +1];
		hidOptWeights = new double[hiddenSize+1][outputSize];
		
		this.iptHidDeltaWeights = new double[inputSize+1][hiddenSize +1];
		this.hidOptDeltaWeights = new double[hiddenSize+1][outputSize];

		hidSumForDelta = new double[hiddenSize+1];
		
		random = new Random(1000);
		randomizeWeights(iptHidWeights);
		randomizeWeights(hidOptWeights);

		//iptHidPrevUptWeights = new double[inputSize+1][hiddenSize+1];
		//hidOptPrevUptWeights = new double[hiddenSize+1][outputSize ];

		this.eta = eta;
		//this.momentum = momentum;
	}

	private void randomizeWeights(double[][] matrix) {
		for (int i = 0, len = matrix.length; i < len; i++)
			for (int j = 0, len2 = matrix[i].length; j < len2; j++) {
				double real = random.nextDouble();
				matrix[i][j] = random.nextDouble() > 0.5 ? real : -real;
			}
	}
	
	public void loadInputTarget(int index) {

		for(int i = 0;i<this.input.length;i++){
			this.input[i] = this.trainInput[index][i];
			
			
		}
		for(int i = 0;i<this.target.length;i++){
			this.target[i] = this.trainTarget[index][i];
			
			
		}
		
		
		return;
		
	}

	/**
	 * Constructor with default eta = 0.25 and momentum = 0.3.
	 * 
	 * @param inputSize
	 * @param hiddenSize
	 * @param outputSize
	 * @param epoch
	 */
	public BP(int inputSize, int hiddenSize, int outputSize) {
		this(inputSize, hiddenSize, outputSize, 0.25, 0.9);
	}

	/**
	 * Entry method. The train data should be a one-dim vector.
	 * 
	 * @param trainData
	 * @param target
	 */
	public void train(String fileName) {
		loadTrainingData(fileName);
		for (int i = 0; i < this.trainTimes; i++) {
			//System.out.println("--------------train"+(i+1)+"----------------");
			for (int j = 0; j < this.trainInput.length; j++) {
				//System.out.print("data"+(j+1)+":");
				loadInputTarget(j);
				forward();
				calculateDelta();
				if(j==1){
				    System.out.println(this.optErrSum);
				}
				adjustWeight();
			}
			
		}
		
	}

	/**
	 * Test the BPNN.
	 * 
	 * @param inData
	 * @return
	 */
	public double[] test(double[] inData) {
		if (inData.length != input.length) {
			throw new IllegalArgumentException("Size Do Not Match.");
		}
		System.arraycopy(inData, 0, input, 0, inData.length);
		forward();
		return this.output;
	}

	/**
	 * Load the target data.
	 * 
	 * @param arg
	 */
	/*private void loadTarget(double[] arg) {
		if (arg.length != target.length - 1) {
			throw new IllegalArgumentException("Size Do Not Match.");
		}
		System.arraycopy(arg, 0, target, 1, arg.length);
	}*/

	/**
	 * Load the training data.
	 * 
	 * @param fileName
	 */
	public abstract void loadTrainingData(String fileName) ;

	/**
	 * Forward.
	 * 
	 * @param layer0
	 * @param layer1
	 * @param weight
	 */
	private void forward(double[] layer0, double[] layer1, double[][] weight) {
		// threshold unit.
		for (int j = 0; j < layer1.length; j++) {
			double sum = 0;
			for (int i = 0 ; i < layer0.length; i++){
				sum += weight[i][j] * layer0[i];
				//System.out.println(weight[i][j]+" ### "+layer0[i]);
			}
			layer1[j] = sum;
		}
	}

	/**
	 * Forward.
	 */
	private void forward() {
		forward(input, hidden, iptHidWeights);
		forward(hidden, output, hidOptWeights);
	}



	/**
	 * Calculate hidden errors.
	 */
	/*private void hiddenErr() {
		double errSum = 0;
		for (int j = 0; j < this.hidden.length; j++) {
			//double o = hidden[j];
			//double sum = 0;
			for (int k = 0; k < this.input.length; k++){
				this.iptHidDeltaWeights[k][j] = this.eta*(di-oi)*this.hidden[j];
			}
				//sum += hidOptWeights[j][k] * optDelta[k];
			//hidDelta[j] = o * (1d - o) * sum;
			errSum += Math.abs(hidDelta[j]);
		}
		hidErrSum = errSum;
		
		double errSum = 0;
		for (int i = 1; i < output.length; i++) {
			double oi = output[i];
			double di = target[i];
			optDelta[i] = oi - di;
			for (int j = 1; j < this.hidden.length; j++){
				this.hidOptDeltaWeights[j][i] = this.eta*(di-oi)*this.hidden[j];
			}
			errSum += Math.pow(optDelta[i], 2);
		}
		optErrSum = errSum;
	}*/

	/**
	 * Calculate errors of all layers.
	 */
	public void calculateDelta(){
		//formatOutput();
				double errSum = 0;
				for (int j = 0; j < this.hidden.length; j++){
					this.hidSumForDelta[j] = 0;
					for (int i = 0; i < output.length; i++) {
						double oi = output[i];
						double di = target[i];

						errSum += Math.pow((oi-di), 2);

						//System.out.println(oi+" "+di+" "+this.hidden[j]);
						//System.out.println(di+" "+oi+"  "+this.hidOptDeltaWeights[j][i]);
						if(oi!=di){
							this.hidOptDeltaWeights[j][i] = this.eta*(di-oi)*this.hidden[j];
							this.hidSumForDelta[j] += (di-oi)*this.hidOptWeights[j][i];
							//-this.hidOptDeltaWeights[j][i] = this.eta*(di-oi)*this.hidden[j]*oi*(1.0-oi);
							//-this.hidSumForDelta[j] += (di-oi)*this.hidOptWeights[j][i]*oi*(1.0-oi);
							//this.hidOptDeltaWeights[j][i] = this.eta*(oi-di)*output[j]*oi*(1.0-oi);
							//this.hidSumForDelta[j] += (oi-di)*this.hidOptWeights[j][i]*oi*(1.0-oi);
							//System.out.println(di+" "+oi+" here "+this.hidOptDeltaWeights[j][i]+" "+this.hidden[j]);
						}
						
					}
				}		
				optErrSum = errSum;
			
				for (int j = 0; j < this.hidden.length; j++){
					for (int k = 0; k < this.input.length; k++){
						this.iptHidDeltaWeights[k][j] = this.eta*this.hidSumForDelta[j]*this.input[k];
						
						//this.iptHidDeltaWeights[k][j] = this.eta*this.hidSumForDelta[j]*this.input[k]*output[j]*(1.0-output[j]);
						//-double hj = this.hidden[j];
						//-this.iptHidDeltaWeights[k][j] = this.eta*this.hidSumForDelta[j]*this.input[k]*hj*(1-hj);
					}
				
				}

				//System.out.println("error:"+errSum);
	}

	/**
	 * Adjust the weight matrix.
	 * 
	 * @param delta
	 * @param layer
	 * @param weight
	 * @param prevWeight
	 */
	/*private void adjustWeight(double[] delta, double[] layer,double[][] weight, double[][] prevWeight) {
		for (int i = 1; i < delta.length; i++) {
			for (int j = 0; j <layer.length; j++) {
				double newVal = momentum * prevWeight[j][i] + eta * delta[i]
						* layer[j];
				weight[j][i] += newVal;
				prevWeight[j][i] = newVal;
			}
		}
	}*/

	/**
	 * Adjust all weight matrices.
	 */
	public void adjustWeight() {
		for(int k = 0;k<input.length;k++){
			for(int j = 0;j<hidden.length;j++){
				this.iptHidWeights[k][j] = this.iptHidWeights[k][j]+this.iptHidDeltaWeights[k][j];
				//System.out.println(this.iptHidWeights[k][j]+" "+this.iptHidDeltaWeights[k][j]);
			}
		}
		for(int j = 0;j<hidden.length;j++){
			for(int i = 0;i<output.length;i++){
				this.hidOptWeights[j][i] = this.hidOptWeights[j][i]+this.hidOptDeltaWeights[j][i];
			}
		}
	}
	/**
	 * Sigmoid.
	 * 
	 * @param val
	 * @return
	 */
	public double sigmoid(double val) {
		return 1d / (1d + Math.exp(-val));
	}

}
