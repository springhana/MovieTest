package Expert;

import java.util.Scanner;

public abstract class AbstractMenu implements Menu{
	String menuText; //���� ȭ�鿡 ����� �޴��� ���� ����
	Menu prevMenu;   //������ ȭ�鿡 ����ߴ� �޴��� ���� ����
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
