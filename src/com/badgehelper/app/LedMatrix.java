package com.badgehelper.app;

import java.util.ArrayList;

public class LedMatrix {
	private boolean mLedMatrix[][] = new boolean[8][8];
	
	public LedMatrix(){
		//initialize
		clear();
	}
	
	public void set(int row, int column, boolean value ){
		mLedMatrix[column][row] = value;
	}
	
	public ArrayList<String> createCommands(){
		int rowByte = 0x00;
		int colByte = 0x00;
		ArrayList<String> commands = new ArrayList<String>();
		for (int x = 0; x < 8; x++) {
			rowByte = 0x00;
			colByte = 0x00;
			for (int y = 0; y < 8; y++) {
				if (mLedMatrix [y][x]) 
				{
					//This is ON
					colByte |= 1 << (7-y);
					rowByte |= 1 << (7-x);
					commands.add(
							String.format("ledMatrix.write( 0x%02x , 0x%02x );", (~rowByte) & 0xFF , colByte)
							);
					
				}
			}
		}
		return commands;
	}

	private void clear() {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				mLedMatrix[y][x] = false;
			}
		}
		
	}
}
