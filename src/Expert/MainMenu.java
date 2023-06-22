package Expert;

import java.io.IOException;
import java.util.ArrayList;

public class MainMenu extends AbstractMenu { //�Ϲ� ����� �޴�

	private static final MainMenu instance = new MainMenu(null);
	private static final String MAIN_MENU_TEXT = 
					"1. ��ȭ �����ϱ�\n" + 
					"2. ���� Ȯ���ϱ�\n" + 
					"3. ���� ����ϱ�\n" + 
					"4. ������ ���� �̵�\n" + 
					"q. ����\n\n" +
					"�޴��� �����ϼ���:";

	private MainMenu(Menu prevMenu) {
		super(MAIN_MENU_TEXT, prevMenu);
	}

	public static MainMenu getInstance() {
		return instance;
	}

	public MainMenu(String menuText, Menu prevMenu) {
		super(menuText, prevMenu);
	}

	@Override
	public Menu next() {
		switch(sc.nextLine()) {
		case "1": reserve();
				  return this;
		case "2": checkReservation();
		  		  return this;
		case "3": cancelReservation();
				  return this;
		case "4": if( !checkAdminPassword()) {
				    	System.out.println(">> ��й�ȣ�� Ʋ�Ƚ��ϴ�.");
				    	return this;
				  }
				  AdminMenu adminMenu = AdminMenu.getInstance();
				  adminMenu.setPrevMenu(this);
				  return adminMenu;
		case "q": return prevMenu;
		default: return this;
		}
	}

	private void reserve() {
		try {
			ArrayList<Movie> movies = Movie.findAll();
			do {
				for(int i=0; i < movies.size(); i++) {
					System.out.printf("%s\n", movies.get(i).toString());
				}
				System.out.print("������ ��ȭ�� �����ϼ���: ");
				String movieIdStr = sc.nextLine();
				if( movieIdStr == null || movieIdStr.isEmpty()) {System.out.println(movieIdStr);return; }
				Movie m = Movie.findById(movieIdStr);
				
				if( m != null) {
					ArrayList<Reservation> reservations = Reservation.findByMovieId(movieIdStr);
					Seats seats = new Seats(reservations);
					seats.show();
					System.out.print("�¼��� �����ϼ���(��: E-9): ");
					String seatName = sc.nextLine();
					seats.mark(seatName);
					Reservation r = new Reservation(Long.parseLong(movieIdStr), m.getTitle(), seatName);
					r.save();
					System.out.println(">>���Ű��Ǿ����ϴ�.");
					System.out.printf(">>�߱޹�ȣ: %d\n", r.getId());
					return;
				}
				else 
					System.out.println("????? ???ID?? ?????.??? ????????.");
			}
			while(true);
		}catch(IOException e) {
			System.out.println("���� ����¿� ������ ������ϴ�.");
		}catch(Exception e) {
			System.out.printf(">> ���ſ� �����߽��ϴ�: %s\n", e.getMessage());
		}
	}
	
	private void checkReservation() {
		System.out.println("�߱� ��ȣ�� �Է��ϼ���: ");
		
		try {
			Reservation r = Reservation.findById(sc.nextLine());
			System.out.println(r);
			if( r != null) {
				System.out.printf(">> [Ȯ�� �Ϸ�] %s \n", r.toString());
			}else {
				System.out.println(">>�߱� ��ȣ�� Ʋ�Ƚ��ϴ�.");
			}
		}catch(IOException e) {
			System.out.println("���� ������� �߸��Ǿ����ϴ�.");
		}
	}
	
	private void cancelReservation() {
		System.out.println("�����ȣ: ");
		try {
			Reservation canceled = Reservation.cancel(sc.nextLine());
			if( canceled != null) {
				System.out.printf(">> [�������] %s����. \n", canceled.toString());
			}else {
				System.out.println(">>���ฮ��Ʈ�� �����ϴ�.");
			}
		}catch(IOException e) {
			System.out.println("���� ����.");
		}

	}
	
	private boolean checkAdminPassword() {
		System.out.println("������ ��й�ȣ�� �Է��ϼ���: ");
		return "abcd".equals(sc.nextLine());
	}
	
}
