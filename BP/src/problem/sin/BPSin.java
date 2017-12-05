package problem.sin;

import algorithm.BP.BP;
import util.IO;

public class BPSin extends BP{
	public BPSin(int inputSize, int hiddenSize, int outputSize) {
		super(inputSize, hiddenSize, outputSize);
		
		// TODO Auto-generated constructor stub
	}
	



	@Override
	public void loadTrainingData(String fileName) {
		String content = IO.readData(fileName);
		String[] lines = content.split("\r\n");
		this.trainInput = new double[lines.length][this.input.length];
		this.trainTarget = new double [lines.length][this.target.length];
		for(int i = 0;i<lines.length;i++){
			String[] splits = lines[i].split(" :");
			this.trainInput[i][0] = Double.parseDouble(splits[1]);
			this.trainInput[i][1] = 1.0;
			this.trainTarget[i][0] = Double.parseDouble(splits[0]);
			
		}
		return;
		
	}
	
	

}
