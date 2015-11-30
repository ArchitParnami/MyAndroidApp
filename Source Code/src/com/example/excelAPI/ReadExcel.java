package com.example.excelAPI;

import java.io.InputStream;

import jxl.BooleanCell;
import jxl.Cell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

public class ReadExcel {

	InputStream is;
	Workbook workbook;
		
	public ReadExcel(InputStream is) {
		this.is = is;
		
		try {
			workbook = Workbook.getWorkbook(is);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public int getAboutColumnCount(){
		Sheet s = workbook.getSheet("about");
		return s.getColumns();
	}
	
	public int getNoOfAwards(){
		Sheet s = workbook.getSheet("awards");
		return s.getRows()-1;
	}
	
	public int getNoOfNews(){
		Sheet s = workbook.getSheet("news");
		return s.getRows()-1;
	}
	
	public int getNoOfVideos(){
		Sheet s = workbook.getSheet("videos");
		return s.getRows()-1;
	}

	public void readAboutSheetInto(String[] columns, String[] data) {
		
			Sheet  s = workbook.getSheet("about");
			
			Cell[] columnNames = s.getRow(0);
			Cell[] columnData = s.getRow(1);
			
			for(int i=0; i< columns.length; i++) {
				columns[i] = columnNames[i].getContents();
				data[i] = columnData[i].getContents().replace("'", "");
			}
			
	}
	
	public void readAwardsSheetInto(String data[][]) {
		
		Sheet s = workbook.getSheet("awards");
		
		int rowCount = s.getRows();
		
		for(int i=1; i < rowCount; i++) {
			for(int j=0; j<3; j++) {
				data[i-1][j] = s.getCell(j, i).getContents().replace("'", "");
			}
		}
		
	}

	
	public void readNewsSheetInto(String data[][]) {
		
		Sheet s = workbook.getSheet("news");
		
		int rowCount = s.getRows();

		for(int i=1; i < rowCount; i++) {
			for(int j=0; j<8; j++) {
				data[i-1][j] = s.getCell(j, i).getContents().replace("'", "");
			}
		}
		
	}
	
	public void readVideosSheetInto(String data[]) {
		
		Sheet s = workbook.getSheet("videos");
		int rowCount = s.getRows();
		
		for(int i = 1; i < rowCount; i++) {
			data[i-1] = s.getCell(0, i).getContents().replace("'", "");
		}
	}
	
	
	
	public int  getCurrentVersion(){
		
		Sheet s = workbook.getSheet("metadata");
		Cell database_subversion_cell = s.getCell(0, 1);
		int subversion = (int)((NumberCell)database_subversion_cell).getValue();
		return subversion;
		
	}
	
	public void readUpdatedSheets(boolean sheets[]) {
		Sheet s = workbook.getSheet("metadata");
		for(int i=0; i<4;i++){
			sheets[i] = ((BooleanCell)s.getCell(i+1, 1)).getValue();
		}
		
	}
	
	
	
	
	@Override
	protected void finalize() throws Throwable {
		
		if(workbook != null) {
			workbook.close();
		}
		super.finalize();
	}
	
}
