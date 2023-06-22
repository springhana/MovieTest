package Expert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;

public class Reservation {
	private long id;
	private long movieId;
	private String movieTitle;
	private String seatName;
	private static final File file = new File("reservations.txt");
	
	public Reservation(long id, long movieId, String movieTitle, String seatName) {
		this.id = id;
		this.movieId = movieId;
		this.movieTitle = movieTitle;
		this.seatName = seatName;
	}
	
	public Reservation(long movieId, String movieTitle, String seatName) {
		this.id = Instant.now().toEpochMilli();
		this.movieId = movieId;
		this.movieTitle = movieTitle;
		this.seatName = seatName;
	}
	
	public static ArrayList<Reservation> findAll() throws IOException {
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		
		while( (line = br.readLine()) != null) {
			String[] temp = line.split(",");
			Reservation r = new Reservation(Long.parseLong(temp[0]), Long.parseLong(temp[1]), temp[2], temp[3]);
			reservations.add(r);
		}
		br.close();
		return reservations;
	}
	
	public static Reservation findById(String reservationId) throws IOException {
		Reservation r = null;
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		
		while( (line = br.readLine()) != null) {
			String[] temp = line.split(",");
			if(reservationId.equals(temp[0])) {
				r = new Reservation(Long.parseLong(temp[0]), Long.parseLong(temp[1]), temp[2], temp[3]);
				break;
			}
		}
		br.close();
		return r;
	}
	
	public static Reservation cancel(String reservationId) throws IOException {
		Reservation canceled = null;
		BufferedReader br = new BufferedReader(new FileReader(file));
		String text = "";
		String line = null;
		
		while((line=br.readLine()) != null) {
			String[] temp = line.split(",");
			if(reservationId.equals(temp[0])) {
				canceled = new Reservation(Long.parseLong(temp[0]), Long.parseLong(temp[1]), temp[2], temp[3]);
				continue;
			}
			text += line + "\r\n";
		}
		br.close();
		
		FileWriter fw = new FileWriter(file);
		fw.write(text);
		fw.close();
		return canceled;
	}
	
	public static void relatedReservationCancel(String movieIdStr) throws IOException {
		Reservation canceled = null;
		BufferedReader br = new BufferedReader(new FileReader(file));
		String text = "";
		String line = null;
		
		while((line=br.readLine()) != null) {
			String[] temp = line.split(",");
			if(movieIdStr.equals(temp[1])) {
				canceled = new Reservation(Long.parseLong(temp[0]), Long.parseLong(temp[1]), temp[2], temp[3]);
				System.out.printf(">> [삭제 완료] %s예약삭제. \n", canceled.toString());
				continue;
			}
			text += line + "\r\n";
		}
		br.close();
		
		FileWriter fw = new FileWriter(file);
		fw.write(text);
		fw.close();
	}
	
	public static ArrayList<Reservation> findByMovieId(String movieIdStr) throws IOException {
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		
		while((line=br.readLine()) != null) {
			String[] temp = line.split(",");
			if(movieIdStr.equals(temp[1])) {
				long id = Long.parseLong(temp[0]);
				long movieId = Long.parseLong(temp[1]);
				String movieTitle = temp[2];
				String seatName = temp[3];
				Reservation r =  new Reservation(id, movieId, movieTitle, seatName);
				reservations.add(r);
				
			}
		}
		br.close();
		return reservations;
	}
	
	public void save() throws IOException {
		FileWriter fw = new FileWriter(file, true);
		fw.write(this.toFileString()+"\r\n");
		fw.close();
	}
	
	public String getSeatName() {
		return seatName;
	}
	
	public long getId() {
		return id;
	}
	
	public String toString() {
		return String.format("예약번호: %s, 영화제목: %s, 좌석: %s", id, movieTitle, seatName);
	}
	
	public String toFileString() {
		return String.format("%d,%d,%s,%s", id, movieId, movieTitle, seatName);
	}
}
