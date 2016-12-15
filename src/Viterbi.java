
public class Viterbi {
	Trelica t = new Trelica();
	private String[] prevOut = new String[64];
	private String[] prevIn = new String[64];
	private int[] prevWeight = new int[64];
	private String[] nextOut = new String[64];
	private String[] nextIn = new String[64];
	private int[] nextWeight = new int[64];
	
	
	private int dist(String from, String to){
		int result = 0;
		if(from.charAt(0)!=to.charAt(0))
			result++;		
		if(from.charAt(1)!=to.charAt(1))
			result++;
		return result;
	}
	
	private void resetAll(){
		for(int i=0;i<64;i++){
			prevOut[i]="";
			prevIn[i]="";
			prevWeight[i]=Integer.MAX_VALUE;
			nextOut[i]="";
			nextIn[i]="";
			nextWeight[i]=Integer.MAX_VALUE;
		}
		prevWeight[0]=0;
	}
	private void clearNext(){
		for(int j=0;j<64;j++){
			prevOut[j]=nextOut[j];
			nextOut[j]="";
			prevIn[j]=nextIn[j];
			nextIn[j]="";
			prevWeight[j]=nextWeight[j];
			nextWeight[j]=Integer.MAX_VALUE;
		}
	}
	private void step(int a, int b){
		for(int k=0;k<64;k++){
			Xfer x = t.get(k);
			if(prevWeight[k]<Integer.MAX_VALUE){
				int nW;
				//Evaluate input 0
				nW = x.next(0);
				if(nextWeight[nW]>(prevWeight[k]+dist(""+a+b,x.output0))){
					nextOut[nW]=prevOut[k]+x.output0;
					nextIn[nW]=prevIn[k]+"0";
					nextWeight[nW]=prevWeight[k]+dist(""+a+b,x.output0);
				}
				//Evaluate input 1
				nW = x.next(1);
				if(nextWeight[nW]>(prevWeight[k]+dist(""+a+b,x.output1))){
					nextOut[nW]=prevOut[k]+x.output1;
					nextIn[nW]=prevIn[k]+"1";
					nextWeight[nW]=prevWeight[k]+dist(""+a+b,x.output1);
				}
				
			}
		}
		clearNext();
		//System.out.println(getInput());
		//System.out.println(getOutput());
	}
	
	public int getMin(){
		int minW = Integer.MAX_VALUE;
		int pos = 0;
		for(int k=0;k<64;k++){
			if(prevWeight[k]<minW){
				minW=prevWeight[k];
				pos=k;
			}
		}
		return pos;
	}
	public int getMinValue(){
		return prevWeight[getMin()];
	}
	public String getInput(){
		return prevIn[getMin()];
	}
	public String getOutput(){
		return prevOut[getMin()];
	}
	public void run(int size, int[] bits){
		resetAll();
		for(int i=0;i<size;i=i+2){
			step(bits[i],bits[i+1]);
		}
	}
}
