package Expert;

public class MainAppOnText {

	public static void main(String[] args) {
//		System.out.println("Program Start on Text-Base ���.");

		Menu menu = MainMenu.getInstance();

		while (menu != null) {
			menu.print();
			menu = menu.next();
		}
//		System.out.println("Program End");
	}

}