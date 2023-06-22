package GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;

public class Movie1 {

	private long id;
	private String title;
	private String genre;
	private static final File file = new File("movies.txt");
	
	public Movie1(long id, String title, String genre) {
		this.id = id;
		this.title = title;
		this.genre = genre;
	}
	
	public static ArrayList<Movie1> findAll() {
		ArrayList<Movie1> movies = new ArrayList<Movie1>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			
			while( (line = br.readLine()) != null) {
				String[] temp = line.split(",");
				Movie1 m = new Movie1(Long.parseLong(temp[0]), temp[1], temp[2]);
				movies.add(m);
			}
			br.close();		
		}catch(IOException e) {
			System.out.println("파일 입출력에서 문제가 생겼습니다.");
		}catch(Exception e) {
			System.out.printf(">> 예매에 실패하였습니다: %s\n", e.getMessage());
		}
		return movies;
	}
	
	public String toString() {
		return String.format("[%d]: %s(%s)", id, title, genre);
	}
	
	public Movie1( String title, String genre) {
		this.id = Instant.now().getEpochSecond();
		this.title = title;
		this.genre = genre;
	}
	
	public void save() throws IOException {
		FileWriter fw = new FileWriter(file, true);
		fw.write(this.toFileString() + "\n");
		fw.close();
	}
	
	private String toFileString() {
		return String.format("%d,%s,%s", id, title, genre);
	}
	
	public static void delete(String movieIdStr) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String text = "";
		String line = null;
		
		while((line=br.readLine()) != null) {
			String[] temp = line.split(",");
			if(movieIdStr.equals(temp[0])) {
				continue;
			}
			text += line+"\n";
		}
		br.close();
		
		FileWriter fw = new FileWriter(file);
		fw.write(text);
		fw.close();
	}
	
	public static Movie1 findById(String movieIdStr) throws IOException {
		Movie1 movie = null;
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		
		while((line=br.readLine()) != null) {
			String[] temp = line.split(",");
			if(movieIdStr.equals(temp[0])) {
				movie = new Movie1(Long.parseLong(temp[0]),temp[1],temp[2]);
				break;
			}
		}
		
		br.close();
		return movie;
	}
	
	public String getTitle() {
		return title;
	}

	public String getId() {
		return String.valueOf(id);
	}

}