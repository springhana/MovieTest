package Expert;

import java.io.IOException;
import java.util.ArrayList;

public class MainMenu extends AbstractMenu { //일반 사용자 메뉴

	private static final MainMenu instance = new MainMenu(null);
	private static final String MAIN_MENU_TEXT = 
					"1. 영화 예매하기\n" + 
					"2. 예매 확인하기\n" + 
					"3. 예매 취소하기\n" + 
					"4. 관리자 모드로 이동\n" + 
					"q. 종료\n\n" +
					"메뉴를 선택하세요:";

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
				    	System.out.println(">> 비밀번호가 틀렸습니다.");
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
				System.out.print("예매할 영화를 선택하세요: ");
				String movieIdStr = sc.nextLine();
				if( movieIdStr == null || movieIdStr.isEmpty()) {System.out.println(movieIdStr);return; }
				Movie m = Movie.findById(movieIdStr);
				
				if( m != null) {
					ArrayList<Reservation> reservations = Reservation.findByMovieId(movieIdStr);
					Seats seats = new Seats(reservations);
					seats.show();
					System.out.print("좌석을 선택하세요(예: E-9): ");
					String seatName = sc.nextLine();
					seats.mark(seatName);
					Reservation r = new Reservation(Long.parseLong(movieIdStr), m.getTitle(), seatName);
					r.save();
					System.out.println(">>예매가되었습니다.");
					System.out.printf(">>발급번호: %d\n", r.getId());
					return;
				}
				else 
					System.out.println("????? ???ID?? ?????.??? ????????.");
			}
			while(true);
		}catch(IOException e) {
			System.out.println("파일 입출력에 문제가 생겼습니다.");
		}catch(Exception e) {
			System.out.printf(">> 예매에 실패했습니단: %s\n", e.getMessage());
		}
	}
	
	private void checkReservation() {
		System.out.println("발급 번호를 입력하세요: ");
		
		try {
			Reservation r = Reservation.findById(sc.nextLine());
			System.out.println(r);
			if( r != null) {
				System.out.printf(">> [확인 완료] %s \n", r.toString());
			}else {
				System.out.println(">>발급 번호가 틀렸습니다.");
			}
		}catch(IOException e) {
			System.out.println("파일 입출력이 잘못되었습니다.");
		}
	}
	
	private void cancelReservation() {
		System.out.println("예약번호: ");
		try {
			Reservation canceled = Reservation.cancel(sc.nextLine());
			if( canceled != null) {
				System.out.printf(">> [예약삭제] %s삭제. \n", canceled.toString());
			}else {
				System.out.println(">>예약리스트가 없습니다.");
			}
		}catch(IOException e) {
			System.out.println("파일 오류.");
		}

	}
	
	private boolean checkAdminPassword() {
		System.out.println("관리자 비밀번호를 입력하세요: ");
		return "abcd".equals(sc.nextLine());
	}
	
}
