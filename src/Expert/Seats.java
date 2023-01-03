package Expert;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Seats {
	
	public static final int MAX_ROW = 5;
	public static final int MAX_COL = 9;
	private String[][] map = new String[MAX_ROW][MAX_COL];
	
	public Seats(ArrayList<Reservation> reservations)  throws Exception {
		for(int i =0; i < MAX_ROW; i++) {
			for(int j = 0; j < MAX_COL; j++) {
				map[i][j] = "o";
			}
		}
		
		for(int i=0; i < reservations.size(); i++)	{
			Reservation r = reservations.get(i);
			String seatName = r.getSeatName();
			mark(seatName);
		}
	}
	
	public void show() {
		System.out.println("----------------------");
		System.out.println("     S C R E E N   ");
		System.out.println("----------------------");
		for(int i =0; i < MAX_ROW; i++) {
			System.out.printf("%c ", 'A'+i);
			for(int j = 0; j < MAX_COL; j++) {
				System.out.printf(" %s", map[i][j]);
			}
			System.out.println();
		}
		System.out.println("   1 2 3 4 5 6 7 8 9");
	}
	
	public void mark(String seatName)  throws Exception {
		String pt = "^[A-E]-[1-9]$";
		System.out.println(seatName);
		if( !Pattern.matches(pt, seatName)) throw new Exception("패턴 오류.");
		char[] temp = seatName.toCharArray();
		int row = temp[0] -'A';
		int col = temp[2] - '1';
		if( "x".equals(map[row][col])) {
			throw new Exception("이미 예약이 되었습니다.");
		}
		map[row][col] = "x";
	}
}
