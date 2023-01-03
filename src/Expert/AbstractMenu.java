package Expert;

import java.util.Scanner;

public abstract class AbstractMenu implements Menu{
	String menuText; //현재 화면에 출력할 메뉴를 가진 변수
	Menu prevMenu;   //이전에 화면에 출력했던 메뉴를 가진 변수
	static final Scanner sc = new Scanner(System.in);
	
	public AbstractMenu(String menuText, Menu prevMenu) {
		super();
		this.menuText = menuText;
		this.prevMenu = prevMenu;
	}
	
	public void setPrevMenu(Menu prevMenu) {
		this.prevMenu = prevMenu;
	}
	
	@Override
	public void print() {
		System.out.println(menuText);
	}

//	@Override
//	public Menu next() {
//		this.menuText = prevMenu;
//	}
	
}
