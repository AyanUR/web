public class Auditor implements Observer{
	public void update(String action,String place){
		System.out.print("\nguardando en la dataBase\naccion: "+action+"\tlugar: "+place);
	}
}
