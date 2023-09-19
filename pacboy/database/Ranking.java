package pacboy.database;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections; //정렬 기능이 있는 유틸
import java.util.Comparator;
import java.util.List;

import pacboy.database.Ranking.User;

public class Ranking {

	// 랭킹 데이터 파일 경로
	static final String DATABASE_PATH = Paths.get(File.separator + "eclipseSTS", "workspace", "database").toString();

	// 파일에서 랭킹 정보를 읽어오는 메서드
	public static List<User> readRanking() {
		List<User> ranking = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(
				new FileReader(DATABASE_PATH + File.separator + "RankRecord.ini"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length == 2) {
					String name = parts[0];
					int score = Integer.parseInt(parts[1]);
					ranking.add(new User(name, score));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ranking;
	}

	// 사용자 정보를 삽입하는 클래스
	public static void addUserAndUpdateList(String name, int score) {
		
		//1. 이전의 데이터를 읽어온다.
		List<User> ranking = readRanking();
		//2. 내 점수를 같고있는 User를 만든다
		User myuser = new User(name, score);
		//3. 랭킹에 내점수를 추가한다.
		ranking.add(myuser);
		//4. 정렬한다.
		Collections.sort(ranking, new Descending());
		//5. Top 5를 계산한다.
		updateRanking(ranking);
	}

	// 랭킹을 읽어와서 콘솔창에 출력하는 메서드
	private static void printRanking() {
		List<User> ranking = readRanking();
		System.out.println("----- 랭킹 -----");
		for (int i = 0; i < ranking.size(); i++) {
			User user = ranking.get(i);
			System.out.println("Rank " + (i + 1) + ": " + user.name + " - " + user.score); // 랭크 1부터 나타내야 하므로 i+1,
		}
	}

	// 랭킹 업데이트 메서드
	private static void updateRanking(List<User> userList) {
		try (BufferedWriter writer = new BufferedWriter(
				new FileWriter(DATABASE_PATH + File.separator + "RankRecord.ini"))) {
			// 최대 5명의 랭킹만 파일에 저장
			for (int i = 0; i < Math.min(userList.size(), 5); i++) {
				User user = userList.get(i);
				// 사용자 이름과 점수를 파일에 쓰고 줄 바꿈
				writer.write(user.name + "," + user.score);
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 내림차순 비교를 위한 Comparator 클래스
	static class Descending implements Comparator<User> {
		@Override
		public int compare(User o1, User o2) {
			// 점수를 내림차순으로 비교
			return Integer.compare(o2.score, o1.score);
		}
	}

	// 사용자 정보를 나타내는 클래스
	public static class User implements Comparable<User> {
		public String name; // 사용자 이름
		public int score; // 사용자 점수

		public User(String _name, int _score) {
			name = _name;
			score = _score;
		}

		@Override
		public String toString() {
			// 사용자 정보를 문자열로 반환
			return "[" + name + "," + score + "]";
		}

		@Override
		public int compareTo(User o) {
			// 점수를 기준으로 사용자를 비교
			return Integer.compare(this.score, o.score);
		}
	}

	public static void main(String[] args) {
		// 테스트용 사용자 데이터 생성
		User user1 = new User("Jeong Lim", 50); // 사용자1 생성
		User user2 = new User("Seong kyun", 42); // 사용자2 생성
		User user3 = new User("Sa Min", 1); // 사용자3 생성
//		User user4 = new User("Hyo Beom", 22); // 사용자4 생성
//		User user5 = new User("Eun Yeong", 35); // 사용자5 생성
//		User user6 = new User("Stranger", 38); // 사용자6 생성
		// 현재 ArrayList가 무한정 생기는 현상 발생 수정 예정

		// 사용자 데이터를 리스트에 추가
		List<User> userList = new ArrayList<>(); // ArrayList 사용
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);
//		userList.add(user4);
//		userList.add(user5); // 사용자1~5 리스트에 추가
//		userList.add(user6);

		// 사용자 리스트를 점수를 기준으로 내림차순 정렬
		Collections.sort(userList, new Descending()); // sort 정렬, Descending은 내림차순

		// 정렬된 사용자 리스트 출력
		for (User user : userList) { // 배열을 반복할때 사용
			System.out.println(user.toString()); // 위에 있는 userList 5번까지 전부 훑고 출력
		}

		// 랭킹 업데이트 메서드 호출
		updateRanking(userList); // 밑에 updataRanking 호출

		// 게임 종료 후 랭킹 표시 메서드 호출
		printRanking();
	}
//   
//        for (User user : userList) { 
//            System.out.println(user.toString()); 
//    }} //현재 ArrayList가 무한정 생기는 현상 발생 수정 예정
}