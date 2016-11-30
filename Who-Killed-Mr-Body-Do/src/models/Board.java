package models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Board {
	
	private BoardSpace[][] _board;
	
	public Board() {
		_board = populateBoard();
	}
	
	public BoardSpace[][] populateBoard() {
		BoardSpace[][] newBoard = new BoardSpace[24][25];
		int y = 0;
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("default_board.board"));		
			String line = br.readLine();
			
			while (line != null) {
				BoardSpace[] row = new BoardSpace[24];
				for (int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
				}
				
				line = br.readLine();
				y++;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return newBoard;
	}
}
