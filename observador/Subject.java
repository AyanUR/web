import java.util.ArrayList;
public class Subject{
	private ArrayList<Observer> observers;
	private String action;
	private String place;
	public Subject(){
		observers=new ArrayList<Observer>();
	}
	public void recorderObserver(Observer observer){
		observers.add(observer);
	}
	public void removeObserver(Observer observer){
		observers.remove(observer);
	}
	public void notificar(){
		for(Observer observer:observers){
			observer.update(this.action,this.place);
		}
	}
	public void executeAction(String action,String place){
		this.action=action;
		this.place=place;
		notificar();
	}	
}
