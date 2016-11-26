public class Xfer {
    // {Estado,proxEstado,input,output}
	public String estado;
	public String proxEstado0;
	public String output0;
	public String proxEstado1;
	public String output1;
	public Xfer(	String estado,String proxEstado0,String output0,String proxEstado1,
			String output1){
		this.estado = estado;
		this.proxEstado0 = proxEstado0;
		this.output0 = output0;
		this.proxEstado1 = proxEstado1;
		this.output1 = output1;		
	}
	public void set(	String estado,String proxEstado0,String output0,String proxEstado1,
			String output1){
		this.estado = estado;
		this.proxEstado0 = proxEstado0;
		this.output0 = output0;
		this.proxEstado1 = proxEstado1;
		this.output1 = output1;		
	}
	public int next(int in){
		if(in==0){
			return Integer.parseInt(proxEstado0, 2);
		} else {
			return Integer.parseInt(proxEstado1, 2);
		}
	}
}
