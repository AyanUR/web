import javax.swing.JOptionPane;
public class Test{
	Subject subject;
	Auditor auditor;
	Mail mail;
	Informer informer;
	public void addObserver(String option){
		boolean flat=true;
		switch(option){
			case "send":
				subject.recorderObserver(mail);
			break;
			case "register":
				subject.recorderObserver(auditor);	
			break;
			case "informer":
				subject.recorderObserver(informer);
			break;
			default: System.out.print("\nesa no es una opcion menso!!! :@"); flat=false;
		}
		if(flat)
			System.out.print("\nobservador agregado satisfactoriamente");
	}
	public void removeObserver(String option){
		boolean flat=true;
		switch(option){
			case "send":
				subject.removeObserver(mail);
			break;
			case "register":
				subject.removeObserver(auditor);
			break;
			case "informer":
				subject.removeObserver(informer);
			break;
			default: System.out.print("\nesa no es una opcion menso!!! :@"); flat=false;
		}
		if(flat)
			System.out.print("\nobservador eliminado satisfactoriamente");
	}
	public void executeTest(){
		subject=new Subject();
		auditor=new Auditor();
		mail=new Mail();
		informer=new Informer();
		String action="",place="",option;
		do{
			System.out.print("\n\taction ->para realizar accion\n\tadd ->para añadir observador\n\tremove ->para eliminar un observador\n\tsalir ->para terminar el programa\n");
			option=JOptionPane.showInputDialog(null,"ingrese opcion");
			switch(option){
				case "action":
					action=JOptionPane.showInputDialog(null,"ingrese la accion");
					place=JOptionPane.showInputDialog(null,"ingrese el lugar");
					subject.executeAction(action,place);
					System.out.print("\n\n");
				break;
				case "add":
					System.out.print("\n\tsend ->enviar correo\n\tregister ->registrar auditoria\n\tinformer ->informar al jefe\n");
					addObserver(JOptionPane.showInputDialog(null,"ingrese una opcion"));	
				break;
				case "remove":
					System.out.print("\n\tsend ->enviar correo\n\tregister ->registrar auditoria\n\tinformer ->informar al jefe\n");
					removeObserver(JOptionPane.showInputDialog(null,"ingrese una opcion"));	
				break;
				case "salir":
					System.out.print("\n\n\tasta pronto D: ...");
					System.exit(1);
				break;
				default: System.out.print("esa no es una opcion menso!!! :@");
			}
		}while(!option.equals("salir"));
	}
	public static void main(String []args){
		Test test=new Test();
		test.executeTest();
	}
}
