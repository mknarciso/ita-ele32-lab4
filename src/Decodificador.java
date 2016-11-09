import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.math.BigInteger;

public class Decodificador {
	public static String received="";
	public static int posicao=0;
    //private static int[] header2 = {0,0,0,1, 1,0,1,0, 1,1,0,0, 1,1,1,1, 1,1,1,1, 1,1,0,0, 0,0,0,1, 1,1,0,1};
    private static int[] header  = {1,1,1,0, 0,1,0,1, 1,0,0,0, 0,1,1,1, 1,1,1,1, 0,0,1,0, 1,1,0,1, 1,1,0,1};
    private int[] bitsIn = new int[2032];
    private int[] bits2 = new int[2032];
    private int[] bits3 = new int[2000];
    private int[] ss = {1,1,1,1, 1,1,1,1};
    private int[] cs = {0,0,0,0, 0,0};
    // {Estado,proxEstado,input,output}

	public static void lerArquivo(String file){
		try {
			BufferedReader br = new BufferedReader(new FileReader(file+".txt"));
		    String line;
		    while ((line = br.readLine()) != null) {
		       received = received + line;
		    }
		    br.close();
		    received = new BigInteger(received,16).toString(2);
		    
		}
		catch (Exception e){
			System.out.println("Deu ruim na leitura do arquivo!");
		}
	}


	 /*public void criaTxtHexadecimal (String filename){
		 try(  PrintWriter out = new PrintWriter( filename+"_out.txt" )  ){
			    out.print( Decodificador.compactado );
		}
		 catch (Exception e){
				System.out.println("Deu ruim!");
		}
	 }*/
    public void readBlock(){
    	for(int i=0;i<2032;i++){
    		if(received.charAt(i+posicao)=='0'){
    			bitsIn[i]=0;
    		} else if(received.charAt(i+posicao)=='1'){
    			bitsIn[i]=1;
    		}
    	}    	
    	posicao=posicao+2031;
    }
    public void run(String file){
        lerArquivo(file);
        int k=0;
        while (posicao != received.length()){
            if(findBlock())
            	readBlock();
            
            descramble();
            removeHeader();
            //System.out.println(received);
            if(k==0){
            	for(int i=0;i<1000;i++)
                	System.out.print(bits2[i]);
            }
            posicao++;
            k++;
        }
        //criaTxtHexadecimal(file);
        System.out.println();
       // for(int i=0;i<1000;i++)
        //	System.out.print(bits2[i]);
    }

    private void removeHeader() {
		for(int i=0;i<2000;i++){
			bits3[i] = bits2[i+32];
		}
		
	}


	public void descramble(){
    	int a;
		for(int j = 0; j < 8; j++){
	       ss[j] = 1;
	       
	    }
        for(int i=0;i<2032;i++){
            a = ss[0];
        	ss[0] = (ss[0] + ss[2] + ss[4] + ss[7])%2;
        	bits2[i]=(bitsIn[i]+ss[7])%2;
            ss[7]=ss[6];
            ss[6]=ss[5];
            ss[5]=ss[4];
            ss[4]=ss[3];
            ss[3]=ss[2];
            ss[2]=ss[1];
            ss[1]= a;
          
        }
    }

	private boolean findBlock() {
		int errors=0;
		if(posicao+32>=received.length()){ return false; }
		for(int k=0;k<32;k++){
			if(!(""+received.charAt(posicao+k)).equals(""+header[k]))
				errors++;
			if(errors>4){ return false; }
		}
		return true;
	}

	public static void main(String args[]){
        Decodificador cod = new Decodificador();
        cod.run("code1_out");

    }
}
