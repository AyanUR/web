public class Mail implements Observer{
	public void update(String action,String place){
		System.out.print("\nenviando correo\naccion: "+action+"\tlugar: "+place);
	}
}
