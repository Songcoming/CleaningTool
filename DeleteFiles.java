package cleaningtools;

import java.io.*;
import java.util.*;

public class DeleteFiles{

	String[] selecteditems;
	List<File> searchresults;
	int temp = 1;

	public DeleteFiles(String[] selected, List<File> directory){
		selecteditems = selected;
		searchresults = directory;
	}

	public boolean deleteSelectedFiles(){
		for(String s : selecteditems){
			for(File f : searchresults){
				if(s.equals(f.getName())){
					if(f.delete() == false)
						temp = 0;
					break;
				}
			}
		}

		if(temp == 1)
			return true;
		else
			return false;
	}
}