
public class Hexa {
	 public static String stringHexadecimalToBinary(String a){
		 String result ="";
		 for(int k=0;k<a.length();k++){
			 if(a.charAt(k)=='0')
				 result = result + "0000";
			 else if (a.charAt(k)=='1')
				 result = result + "0001";
			 else if (a.charAt(k)=='2')
				 result = result + "0010";
			 else if (a.charAt(k)=='3')
				 result = result + "0011";
			 else if (a.charAt(k)=='4')
				 result = result + "0100";
			 else if (a.charAt(k)=='5')
				 result = result + "0101";
			 else if (a.charAt(k)=='6')
				 result = result + "0110";
			 else if (a.charAt(k)=='7')
				 result = result + "0111";
			 else if (a.charAt(k)=='8')
				 result = result + "1000";
			 else if (a.charAt(k)=='9')
				 result = result + "1001";
			 else if (a.charAt(k)=='A')
				 result = result + "1010";
			 else if (a.charAt(k)=='B')
				 result = result + "1011";
			 else if (a.charAt(k)=='C')
				 result = result + "1100";
			 else if (a.charAt(k)=='D')
				 result = result + "1101";
			 else if (a.charAt(k)=='E')
				 result = result + "1110";
			 else if (a.charAt(k)=='F')
				 result = result + "1111";
		 }
			 return result;		 
	}
	 public static void main(String args[]){
		 System.out.println(stringHexadecimalToBinary("E587F2DD"));
	 }
 }

