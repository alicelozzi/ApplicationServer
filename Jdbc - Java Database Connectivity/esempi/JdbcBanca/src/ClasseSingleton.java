import javax.security.sasl.AuthorizeCallback;

/**
 * @author Angelo Pasquarelli
 * 
 * La presente classe rappresenta l'implementazione del pattern singleton.
 * 
 * Questo pattern (modello) determina un modo di implementare la classe 
 * tale che la classe si autoistanzia ed è istanziabile una sola volta 
 * (eseguire il debugging del metodo main() per comprenderne il significato).  
 */

public class ClasseSingleton {

	private static ClasseSingleton classeSingletonInstance;
	
	
	private ClasseSingleton() {
	}
	
	public
	static
	ClasseSingleton readClasseSingletonInstance()	{
		
		if (classeSingletonInstance == null) {
			classeSingletonInstance = new ClasseSingleton();
		}
		
		return classeSingletonInstance;
	}
	
	
	public static void main(String[] args) {
		ClasseSingleton classeSingleton1 = ClasseSingleton.readClasseSingletonInstance();
		ClasseSingleton classeSingleton2 = ClasseSingleton.readClasseSingletonInstance();
		ClasseSingleton classeSingleton3 = ClasseSingleton.readClasseSingletonInstance();
		ClasseSingleton classeSingleton4 = ClasseSingleton.readClasseSingletonInstance();
		ClasseSingleton classeSingleton5 = ClasseSingleton.readClasseSingletonInstance();
		ClasseSingleton classeSingleton6 = ClasseSingleton.readClasseSingletonInstance();

        System.out.println(classeSingleton1);
        System.out.println(classeSingleton2);
        System.out.println(classeSingleton3);
        System.out.println(classeSingleton4);
        System.out.println(classeSingleton5);
        System.out.println(classeSingleton6);

	}
}
