package Expert;

import java.io.IOException;
import java.util.ArrayList;

public class AdminMenu extends AbstractMenu { //������ �޴�
	
	private static final AdminMenu instance = new AdminMenu(null);
	private static final String ADMIN_MENU_TEXT = 
				"1. ��ȭ ����ϱ�\n" +
				"2. ��ȭ ��Ϻ���\n" +
				"3. ��ȭ �����ϱ�\n" +
				"4. ���� ��Ϻ���\n" +
				"5. ���� �����ϱ�\n" +
				"b. ���� �޴��� �̵�\n\n" +
				"�޴��� �����ϼ���:";
	
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
		System.out.print("��ȭ ����:");
		String title = sc.nextLine();
		System.out.print("��ȭ �帣:");
		String genre = sc.nextLine();
		Movie movie = new Movie(title, genre);
		try {
			movie.save();
			System.out.println(">>����Ǿ����ϴ�.");
		} catch(IOException e) {
			System.out.println(">>������ �ȉ���ϴ�.");
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
			System.out.println("������ ���ٿ� �����Ͽ����ϴ�.");
		}
	}
	
	private void deleteMovie() {
		String movieIdStr;
		printAllMovies();
		System.out.print("��ȭ ID�� �Է��ϼ���: ");
		movieIdStr = sc.nextLine();
		try {
			Movie.delete(movieIdStr);
			System.out.println(">>�����߽��ϴ�.");
			System.out.print(">>���� ��ϱ��� �����ϰڽ��ϱ�?(yes/no): ");
			if(sc.nextLine().equalsIgnoreCase("yes"))
				Reservation.relatedReservationCancel(movieIdStr);
		} catch(IOException e) {
			System.out.println(">> ���� ����.");
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
			System.out.println("���� ��� ���ӿ� �����߽��ϴ�.");
		}
	}
	
	private void deleteRervation(){
		System.out.println("�����ȣ: ");
		try {
			Reservation canceled = Reservation.cancel(sc.nextLine());
			if( canceled != null) {
				System.out.printf(">> [�������] %s����. \n", canceled.toString());
			}else {
				System.out.println(">>��������.");
			}
		}catch(IOException e) {
			System.out.println("���� ����.");
		}

	}

}
