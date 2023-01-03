package Expert;

import java.io.IOException;
import java.util.ArrayList;

public class AdminMenu extends AbstractMenu { //관리자 메뉴
	
	private static final AdminMenu instance = new AdminMenu(null);
	private static final String ADMIN_MENU_TEXT = 
				"1. 영화 등록하기\n" +
				"2. 영화 목록보기\n" +
				"3. 영화 삭제하기\n" +
				"4. 예약 목록보기\n" +
				"5. 예약 삭제하기\n" +
				"b. 메인 메뉴로 이동\n\n" +
				"메뉴를 선택하세요:";
	
	private AdminMenu(Menu prevMenu) {
		super(ADMIN_MENU_TEXT, prevMenu);
	}

	public static AdminMenu getInstance() {
		return instance;
	}
	
	@Override
	public Menu next() {
		switch(sc.nextLine()) {
		case "1": createMovie();
				  return this;
		case "2": printAllMovies();
		  		  return this;
		case "3": deleteMovie();
				  return this;
		case "4": printAllReservations();
		  		  return this;
		case "5": deleteRervation();
		  		  return this;
		case "b": return prevMenu;
		default: return this;
		}

	}
	
	private void createMovie() {
		System.out.print("영화 제목:");
		String title = sc.nextLine();
		System.out.print("영화 장르:");
		String genre = sc.nextLine();
		Movie movie = new Movie(title, genre);
		try {
			movie.save();
			System.out.println(">>저장되었습니다.");
		} catch(IOException e) {
			System.out.println(">>저장이 안됬습니다.");
		}	
	}
	
	private void printAllMovies() {
		try {
			ArrayList<Movie> movies = Movie.findAll();
			System.out.println();
			for(int i=0; i < movies.size(); i++) {
				System.out.printf("%s\n", movies.get(i).toString());
			}
		}catch(IOException e) {
			System.out.println("데이터 접근에 실패하였습니다.");
		}
	}
	
	private void deleteMovie() {
		String movieIdStr;
		printAllMovies();
		System.out.print("영화 ID를 입력하세요: ");
		movieIdStr = sc.nextLine();
		try {
			Movie.delete(movieIdStr);
			System.out.println(">>삭제했습니다.");
			System.out.print(">>예약 목록까지 삭제하겠습니까?(yes/no): ");
			if(sc.nextLine().equalsIgnoreCase("yes"))
				Reservation.relatedReservationCancel(movieIdStr);
		} catch(IOException e) {
			System.out.println(">> 삭제 실패.");
		}	
	}
	
	private void printAllReservations() {
		try {
			ArrayList<Reservation> reservations = Reservation.findAll();
			System.out.println();
			for(int i=0; i < reservations.size(); i++) {
				System.out.printf("%s\n", reservations.get(i).toString());
			}
		}catch(IOException e) {
			System.out.println("예약 목록 접속에 실패했습니다.");
		}
	}
	
	private void deleteRervation(){
		System.out.println("예약번호: ");
		try {
			Reservation canceled = Reservation.cancel(sc.nextLine());
			if( canceled != null) {
				System.out.printf(">> [예약삭제] %s삭제. \n", canceled.toString());
			}else {
				System.out.println(">>삭제실패.");
			}
		}catch(IOException e) {
			System.out.println("파일 오류.");
		}

	}

}
