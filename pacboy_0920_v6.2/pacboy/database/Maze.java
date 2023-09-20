package pacboy.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Maze {

	/* 미로 파일 경로 */
	static final String DATABASE_PATH = Paths.get(File.separator + "pacco", "resource", "database").toString();
	private static final String INDEX_FORMAT = "Index%d";

	/* 2차원 동적배열 미로 */
	private List<Integer[]> maze = new ArrayList<>();

	/* 미로 레벨 */
	private String level;
	private int value;
	/* 미로 가로 사이즈 */
	private int sizeOfMaze;
	/* 유령 초기 위치 */
	private int[] ghostInitLocation = new int[2];
	/* 팩보이 초기 위치 */
	private int[] pacboyInitLocation = new int[2];
	/* 벽 갯수 */
	private int numOfBricks = 0;
	/* 코인 갯수 */
	private int numOfCoins = 0;
	/* 팩보이 갯수 : 이후에 2P 모두가 만들어 진다면 (심화) */
	private int numOfPacBoy = 1;
	/* 유령 갯수 : 미로에서 0의 갯수중 유저의 갯수를 1개 빼야됨. */
	private int numOfGhosts = -1;

	/* 벽 위치 좌표 */
	private static ArrayList<int[]> wallMap = new ArrayList<int[]>();

	/* 코인 위치 좌표 */
	public static ArrayList<int[]> coinMap = new ArrayList<int[]>();

	/* 미로 가져오기 */
	public List<Integer[]> getMaze() {
		int random = (int) (Math.random() * 3);
		String randomLevel = Integer.toString(random);

//		System.out.println("미로 DB 경로 확인 : " + DATABASE_PATH);
		try (BufferedReader br = new BufferedReader(
				new FileReader(DATABASE_PATH + File.separator + "MazeLevel" + randomLevel + ".ini"));) { // 미로랜덤

			Properties mazeProperties = new Properties();
			mazeProperties.load(br);
			// System.out.println("database 읽는중 ...");
			String getMazeSize = mazeProperties.getProperty("MazeSize");
			level = mazeProperties.getProperty("Level");
			sizeOfMaze = getMazeSize != null ? Integer.parseInt(getMazeSize) : 0;
			setSizeOfMaze(sizeOfMaze);
			// System.out.println("미로 사이즈 : " + sizeOfMaze);

			/* 유령,팩보이 초기위치 */
			String[] ghostInitLocationToString = mazeProperties.getProperty("Ghost").split(" ");
			String[] pacboyInitLocationToString = mazeProperties.getProperty("Pacboy").split(" ");
			ghostInitLocation[0] = Integer.parseInt(ghostInitLocationToString[0]);
			ghostInitLocation[1] = Integer.parseInt(ghostInitLocationToString[1]);
			pacboyInitLocation[0] = Integer.parseInt(pacboyInitLocationToString[0]);
			pacboyInitLocation[1] = Integer.parseInt(pacboyInitLocationToString[1]);

			/* DB에서 한줄씩 가져오기 */
			// System.out.println("===============================");
			for (int row = 0; row < sizeOfMaze; row++) {
				String rowDataOfMaze = mazeProperties.getProperty(String.format(INDEX_FORMAT, row));

				/* 미로 문자열->정수 변환 후 저장 */
				// System.out.println(rowDataOfMaze);
				String[] rowArrayOfMaze = rowDataOfMaze.split("\t");
				Integer[] rowArrayOfMazeToInt = new Integer[sizeOfMaze];
				for (int column = 0; column < sizeOfMaze; column++) {
					rowArrayOfMazeToInt[column] = Integer.parseInt(rowArrayOfMaze[column]);
					numOfBricks += (rowArrayOfMazeToInt[column] == -1) ? 1 : 0;
					numOfCoins += (rowArrayOfMazeToInt[column] == 1) ? 1 : 0;
					numOfGhosts += (rowArrayOfMazeToInt[column] == 0) ? 1 : 0;
					if (rowArrayOfMazeToInt[column] == 1) {
						int[] coinXY = new int[2];
						coinXY[0] = column;
						coinXY[1] = row;
						coinMap.add(coinXY);
					}
					if (rowArrayOfMazeToInt[column] == -1) {
						int[] wallXY = new int[2];
						wallXY[0] = column;
						wallXY[1] = row;
						wallMap.add(wallXY);
					}
					// System.out.println(numOfBricks + numOfCoins + numOfGhosts);
				}
				// System.out.print(rowArrayOfMazeToInt[i]);

				maze.add(rowArrayOfMazeToInt);
			}
			// System.out.println("===============================");

		} catch (

		FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maze;
	}
	public int getMazeValue(int row, int column) {
		Integer[] rowArrayOfMaze = maze.get(row);
		value = rowArrayOfMaze[column];
		return value;
	}

	public static ArrayList<int[]> getWallMap() {
		return wallMap;
	}

	public static void setWallMap(ArrayList<int[]> wallMap) {
		Maze.wallMap = wallMap;
	}

	public static ArrayList<int[]> getCoinMap() {
		System.out.println("coinMap size : " + coinMap.size());
		return coinMap;
	}

	public static void setCoinMap(ArrayList<int[]> coinMap) {
		Maze.coinMap = coinMap;
	}

	public static String getIndexFormat() {
		return INDEX_FORMAT;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getSizeOfMaze() {

		return sizeOfMaze;
	}

	public int getNumOfBricks() {
		return numOfBricks;
	}

	public int getNumOfCoins() {
		return numOfCoins;
	}

	public int getNumOfGhosts() {
		return numOfGhosts;
	}

	public int getNumOfPacBoy() {
		return numOfPacBoy;
	}

	public void setSizeOfMaze(int sizeOfMaze) {
		this.sizeOfMaze = sizeOfMaze;
	}
	
	public int[] getGhostInitLocation() {
		System.out.printf("ghost 미로 인덱스[x][y] : [%d][%d]\n", ghostInitLocation[0], ghostInitLocation[1]);
		return ghostInitLocation;
	}

	public int[] getPacboyInitLocation() {
		System.out.printf("pacboy 미로 인덱스[x][y] : [%d][%d]\n", pacboyInitLocation[0], pacboyInitLocation[1]);
		return pacboyInitLocation;
	}

	public static void main(String[] args) {
		Maze mazeLv0 = new Maze();
		mazeLv0.getMaze();

		System.out.println("미로레벨 : " + mazeLv0.getLevel());
		System.out.println("미로사이즈 : " + mazeLv0.getSizeOfMaze());
		System.out.println("벽돌갯수 : " + mazeLv0.getNumOfBricks());
		System.out.println("코인갯수 : " + mazeLv0.getNumOfCoins());
		System.out.println("유령갯수 : " + mazeLv0.getNumOfGhosts());
		System.out.println("미로[8][8]인덱스값 : " + mazeLv0.getMazeValue(8, 8));
		mazeLv0.getGhostInitLocation();
		mazeLv0.getPacboyInitLocation();
		System.out.println("coinMap 사이즈 : " + coinMap.size());
		for (int[] xy : coinMap) {
			System.out.println("x,y : " + xy[0] + ", " + xy[1]);
		}
	}

}
