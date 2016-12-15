import java.io.BufferedReader;
import java.io.FileReader;
//import java.math.BigInteger;

public class check {
	public static String received="";
	public static String base="";
    private static String dummy = "";
    public static void setDummy(){
    	for(int i=0;i<1000;i++)
    		dummy = dummy+"0";
    	System.out.println("Dummy Size: "+dummy.length());
    }
	public static void lerArquivo(String file){
		try {
			BufferedReader br = new BufferedReader(new FileReader(file+".txt"));
		    String line;
		    System.out.println(file);
		    while ((line = br.readLine()) != null) {
		       received = received + line;
		    }
		    br.close();
		    //received = new BigInteger(received,16).toString(2);
		    
		}
		catch (Exception e){
			System.out.println("Deu ruim na leitura do arquivo!");
		}
	}
	public static void lerBase(String file){
		try {
			BufferedReader br = new BufferedReader(new FileReader(file+".txt"));
		    String line;
		    while ((line = br.readLine()) != null) {
		       base = base + line;
		    }
		    br.close();
		    //base = new BigInteger(base,16).toString(2);
		    
		}
		catch (Exception e){
			System.out.println("Deu ruim na leitura do arquivo base!");
		}
	}
	public static void compare(){
		int erro =0;
		int pos;
		for(pos=0;pos<base.length()-2 & pos<received.length();pos++){
			if(base.charAt(pos)!=received.charAt(pos)){
				erro++;
				//System.out.println("Erro na posição: "+pos);
			}
		}
		System.out.println("Size: "+ pos);
		System.out.println("Erros: "+erro);
	}
	private static void checkHeaders() {
		int herr=0;
		for(int header = 0; header<received.length()/1000 && header<=2140 && received.length()<2140000;header++){
			//System.out.println("Header:" + header);
			int erros=0;
			for(int i=0;i<1000 && header*1000+i<base.length();i++){
				if(base.charAt(header*1000+i)!=received.charAt(header*1000+i)){
					erros++;
				}				
			}
			if(erros>400){//Header nao reconhecido
				herr++;
				received=received.substring(0,header*1000)+dummy+received.substring(header*1000);
				//System.out.println("Erro no header: "+header+"["+received.length()+"]");
			}
		}
		System.out.println(herr+" headers não reconhecidos");
		
	}
	public static void main(String args[]) {
		setDummy();
		lerBase("OriginalTheWarInTheAir");
		//lerBase("Augusto_Murilo_est");
		lerArquivo("Augusto_Murilo_100_est");
		checkHeaders();
		compare();
		System.out.println("Base size: "+base.length());
		System.out.println("Received size: "+received.length());
		//System.out.println(base.substring(2139850));
		//System.out.println(received.substring(2139850));
	}

}
