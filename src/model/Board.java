package model;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Board {
	
	public static Point[] _startingSpots = {new Point(1, 6), new Point(17, 1), new Point(24, 8), 
			new Point(10, 25), new Point(15, 25), new Point(1, 19)};
	
	private BoardSpace[][] _board;
	private ArrayList<Room> _rooms;
	
	public Board(ArrayList<Room> rooms) {
		_rooms = rooms;
		_board = populateBoard();
	}
	
	public BoardSpace[][] populateBoard() {
		BoardSpace[][] newBoard = new BoardSpace[26][27];
		int y = 0;
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("default_board.board"));		
			String line = br.readLine();
			
			while (line != null) {
				for (int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					
					switch(c) {
						case 'w': 
							newBoard[i][y] = new WallSpace();
							break;
							
						case 'h': 
							newBoard[i][y] = new HallSpace();
							break;
							
						case 'g':
							newBoard[i][y] = new BorderSpace();
							break;
							
						case 'S': // Study
							newBoard[i][y] = _rooms.get(0);
							break;
							
						case 'H': 
							newBoard[i][y] = _rooms.get(1);
							break;
							
						case 'L': 
							newBoard[i][y] = _rooms.get(2);
							break;
							
						case 'D': 
							newBoard[i][y] = _rooms.get(3);
							break;
							
						case 'K': 
							newBoard[i][y] = _rooms.get(4);
							break;
							
						case 'B': 
							newBoard[i][y] = _rooms.get(5);
							break;
							
						case 'C': 
							newBoard[i][y] = _rooms.get(6);
							break;
							
						case 'I':
							newBoard[i][y] = _rooms.get(7);
							break;
							
						case 'R':
							newBoard[i][y] = _rooms.get(8);
							break;
							
						case 'M':
							newBoard[i][y] = new BlankSpace();
							break;
							
						case '!':
							newBoard[i][y] = new DoorSpace();
							break;
							
						case '?':
							newBoard[i][y] = new SecretPassage();
							break;
							
						default:
							 System.out.println(c);
					}
				}
				
				line = br.readLine();
				y++;
			}
			
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return newBoard;
	}
	
	public BoardSpace[][] getBoard() {
		return _board;
	}
}
