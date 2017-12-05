package problem.number;

import util.IO;
import algorithm.BP.BP;

public class BPNumber extends BP{

	public BPNumber(int inputSize, int hiddenSize, int outputSize) {
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
			String[] inputs = splits[1].split(" ");
			String[] outputs = splits[0].split(" ");
			for(int j = 0;j<inputs.length;j++){
				this.trainInput[i][j] = Integer.parseInt(inputs[j]);
				//System.out.print(this.trainInput[i][j]+" ");
	
			}
			//System.out.println(":"+inputs.length);
			this.trainInput[i][this.input.length-1] = 1.0;
			for(int j = 0;j<outputs.length;j++){
				//System.out.println(inputs.length+" "+j+" "+i);
				this.trainTarget[i][j] = Integer.parseInt(outputs[j]);
				//System.out.print(this.trainTarget[i][j]+" ");
				
			}
			//System.out.println(outputs.length);
		}
		//System.exit(0);
		return;
		
	}	
	
	public void formatOutput() {
		for(int j = 0;j<this.output.length;j++){
			if(sigmoid(this.output[j])>0.5){
				this.output[j] = 1.0;
			}
			else{
				this.output[j] = 0.0;
			}
			 
			
		}
	}

}
