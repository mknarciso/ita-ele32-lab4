import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class Codificador {
	public static String texto="";
	public static int posicao = 0;
    private static int[] header = {0,0,0,1, 1,0,1,0, 1,1,0,0, 1,1,1,1, 1,1,1,1, 1,1,0,0, 0,0,0,1, 1,1,0,1};
    private int[] bitsIn = new int[1000];
    private int[] bits2 = new int[2000];
    private int[] bits3 = new int[2032];
    private int[] bits4 = new int[2032];
    private int[] cs = {0,0,0,0,0,0};
    private int[] ss = {1,1,1,1, 1,1,1,1};
 
    public static String compactado = "";
    
	public static void lerTexto(String file){
		try {
			BufferedReader br = new BufferedReader(new FileReader(file+".txt"));
		    String line;
		    while ((line = br.readLine()) != null) {
		       texto = texto + line;
		    }
		    br.close();
		}
		catch (Exception e){
			System.out.println("Deu ruim!");
		}
	}
	
	public void gerarBitsIn (){
		//if(texto.charAt(0))
		/*for(int k=0;k<10;k++){
			System.out.print(texto.charAt(posicao+k));
			
		}
		System.out.println();*/
		char aux;
		int a = texto.length();
		if(a < posicao + 1000){
			for(int i = 0; i < posicao + 1000 - a; i ++){
				texto = texto + "0";
			}
		}
		for (int i = 0; i < 1000; i ++){
			aux = texto.charAt(posicao + i);
			if(aux == '1')
				bitsIn[i] = 1;
			else if(aux == '0') 
				bitsIn[i] = 0;
		}
		/*System.out.println(texto.substring(posicao,posicao+1000));
		for (int i = 0; i < 1000; i ++){
			System.out.print(texto.charAt(posicao + i));
		}*/
		posicao = posicao + 1000;
	}
	
    public void convolute(){
        for(int j = 0; j < 6; j++){
	       cs[j] = 0;
        }
        for(int i =0; i<1000; i++){
        	//if(i<50)
        	//	System.out.print(bitsIn[i]+" "+cs[0]+cs[1]+cs[2]+cs[3]+cs[4]+cs[5]+"  ");
            bits2[2*i]   =(bitsIn[i]+cs[0]+cs[1]+cs[2]+cs[5])%2;
            bits2[2*i+1] =(bitsIn[i]+cs[1]+cs[2]+cs[4]+cs[5])%2;
            /*if(i<50){
	            System.out.print(bits2[2*i]+" "+bits2[2*i+1]+"  ");
	            for(int j=0;j<2*i;j++){
	            	System.out.print(bits2[j]);
	            }
	            System.out.print("\n");
	        }*/
            cs[5] = cs[4];
            cs[4] = cs[3];
            cs[3] = cs[2];
            cs[2] = cs[1];
            cs[1] = cs[0]; // Tava faltando essa linha
            cs[0] = bitsIn[i];
        }
    }
    
    public void addHeader(){
        for(int i=0;i<32;i++){
            bits3[i]=header[i];
        }
        for(int i=0;i<2000;i++){
            bits3[32+i]=bits2[i];
        }

    }
    
    public void scramble(){
    	int a;
		for(int j = 0; j < 8; j++){
	       ss[j] = 1;
	       
	    }
        for(int i=0;i<2032;i++){
            a = ss[0];
        	ss[0] = (ss[0] + ss[2] + ss[4] + ss[7])%2;
        	bits4[i]=(bits3[i]+ss[7])%2;
            ss[7]=ss[6];
            ss[6]=ss[5];
            ss[5]=ss[4];
            ss[4]=ss[3];
            ss[3]=ss[2];
            ss[2]=ss[1];
            ss[1]= a;
          
        }
    }

	 public void armazenaEmHexadecimal (){
		 String a;
		 String aux = "";
		 for (int i = 0; i <508; i ++){
			 a = Integer.toString( 1000*bits4[4*i] + 100* bits4[4*i+1] + 10*bits4[4*i+2] + bits4[4*i+3]);
			 //System.out.println(a + " " + stringHexadecimal(a));
			 aux = aux + stringHexadecimal(a);
			
		 }
		 compactado = compactado + aux;
	 }
	 
	 public String stringHexadecimal(String a){
		 if(a.equals("0"))
			 return "0";
		 else if (a.equals("1"))
			 return "1";
		 else if (a.equals("10"))
			 return "2";
		 else if (a.equals("11"))
			 return "3";
		 else if (a.equals("100"))
			 return "4";
		 else if (a.equals("101"))
			 return "5";
		 else if (a.equals("110"))
			 return "6";
		 else if (a.equals("111"))
			 return "7";
		 else if (a.equals("1000"))
			 return "8";
		 else if (a.equals("1001"))
			 return "9";
		 else if (a.equals("1010"))
			 return "A";
		 else if (a.equals("1011"))
			 return "B";
		 else if (a.equals("1100"))
			 return "C";
		 else if (a.equals("1101"))
			 return "D";
		 else if (a.equals("1110"))
			 return "E";
		 else if (a.equals("1111"))
			 return "F";
		 return null;
	 }
	 
	 public void criaTxtHexadecimal (String filename){
		 try(  PrintWriter out = new PrintWriter( filename+"_out.txt" )  ){
			    out.print( Codificador.compactado );
		}
		 catch (Exception e){
				System.out.println("Deu ruim!");
		}
	 }
    
    public void run(String file){
        lerTexto(file);
        int k=0;
        while (posicao != texto.length()){
        	//System.out.println(k+"k < Tamanho < "+(k+1)+"k: texto.lenght="+texto.length()+", posicao="+posicao);
        	
            gerarBitsIn();
            convolute();
            addHeader();       
            scramble();
            if(k==0){
        		for (int i = 0; i < 1000; i ++){
        			System.out.print(bitsIn[i]);
        		}
        		System.out.print("\n");
        		for (int i = 0; i < 2000; i ++){
        			System.out.print(bits2[i]);
        		}
        		System.out.print("\n");
        		for (int i = 0; i < 2032; i ++){
        			System.out.print(bits3[i]);
        		}
        		System.out.print("\n");
        		for (int i = 0; i < 2032; i ++){
        			System.out.print(bits4[i]);
        		}
        		System.out.print("\n");
            }
            armazenaEmHexadecimal();
            k++;
        }
        criaTxtHexadecimal(file);
    }

	/*public static void main(String args[]){
        Codificador cod = new Codificador();
        cod.run("theWarInTheAir");
    }*/
}
