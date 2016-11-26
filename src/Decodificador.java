import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    //private int[] cs = {0,0,0,0, 0,0};
    private String estimaIn="";
    //private String estimaOut="";
    

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


	 public void criaTxtEstimado (String filename){
		 try(  PrintWriter out = new PrintWriter( filename+"_est.txt" )  ){
			    out.print( estimaIn );
		}
		 catch (Exception e){
				System.out.println("Deu ruim na impressao da saida!");
		}
	 }
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
    public void run(String file) throws IOException{
    	System.out.println("Lendo o arquivo: "+file);
        lerArquivo(file);
        int k=0;
        while (posicao != received.length()){
            if(findBlock())
            	readBlock();
            
            descramble();
            removeHeader();
            //System.out.println(received);

            Viterbi vit = new Viterbi();
        	vit.run(2000, bits3);
        	estimaIn=estimaIn+vit.getInput();
        	//estimaOut=estimaOut+vit.getOutput();
            /*if(k==0){
            	for(int i=0;i<1000;i++)
                	System.out.print(bits3[i]);
            }*/
        	 
        	Runtime.getRuntime().exec("clear"); 
        	System.out.println(k*100/(received.length()/2032)+"%");
            posicao++;
            k++;
        }

    	//System.out.println("\nIn? "+estimaIn);
    	//System.out.println("Out? "+estimaOut);
        criaTxtEstimado(file);
        System.out.println("Operação finalizada, gerado o arquivo: "+file+"_est.txt");
        //System.out.println();
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

	public static void main(String args[]) throws IOException{
        Decodificador cod = new Decodificador();
        cod.run("code1_outE");

    }
}
