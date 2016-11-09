import java.util.ArrayList;
import java.util.List;

public class TrelicaTable {
	
	private int[] cs = {0,0,0,0,0,0};
	private List<Trelica> tabela = new ArrayList<Trelica>();
    public void checkConvolute(){
    	for(int k=0;k<64;k++){
    		String binary = Integer.toBinaryString(k);
    		setCs(k);
        }
    }
    private String runConv(int input){
		String output="";
        output = output+ (input+cs[0]+cs[1]+cs[2]+cs[5])%2;
        output = output+ (input+cs[1]+cs[2]+cs[4]+cs[5])%2;
        return output;
    }
    private void setCs(int b){
    	cs[0] = (b/32)%2;
    	cs[1] = (b/16)%2;
    	cs[2] = (b/8)%2;
    	cs[3] = (b/4)%2;
    	cs[4] = (b/2)%2;
    	cs[5] = b%2;
    	
    }
	public void makeTrelica(){
		
	}
}
