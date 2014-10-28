public class Informer implements Observer{
	public void update(String action,String place){
		System.out.print("\ninformado al jefe\naccion: "+action+"\tlugar: "+place);
	}
}
