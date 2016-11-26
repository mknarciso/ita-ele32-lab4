import java.util.ArrayList;
import java.util.List;

public class Trelica {
	
	private int[] cs = {0,0,0,0,0,0};
	private List<Xfer> tabela = new ArrayList<Xfer>();
    /*public void checkConvolute(){
    	for(int k=0;k<64;k++){
    		String binary = Integer.toBinaryString(k);
    		setCs(k);
        }
    }*/
	public Trelica(){
		makeTrelica();
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
		for(int k=0;k<64;k++){
			setCs(k);
			Xfer nova = new Xfer(
					""+cs[0]+cs[1]+cs[2]+cs[3]+cs[4]+cs[5],
					"0"+cs[0]+cs[1]+cs[2]+cs[3]+cs[4],
					runConv(0),
					"1"+cs[0]+cs[1]+cs[2]+cs[3]+cs[4],
					runConv(1));
			tabela.add(nova);
		}
	}
	public static void main(String args[]){
		Trelica t = new Trelica();
		/*int in = 3;
		t.setCs(in);
		System.out.println(in+":"+t.cs[0]+t.cs[1]+t.cs[2]+t.cs[3]+t.cs[4]+t.cs[5]);
		System.out.println(t.runConv(0));*/
		
		Xfer toPrint;
		for(int k=0;k<64;k++){
			System.out.println("Trelica ["+k+"]");
			toPrint = t.tabela.get(k);
			System.out.println(toPrint.estado+":");
			System.out.println("=>"+toPrint.proxEstado0+"/"+toPrint.next(0)+":"+toPrint.output0);
			System.out.println("=>"+toPrint.proxEstado1+"/"+toPrint.next(1)+":"+toPrint.output1);
		}
	}
	public Xfer get(int i){
		return tabela.get(i);
	}
}
