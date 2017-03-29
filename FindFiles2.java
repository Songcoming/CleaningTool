package cleaningtools;

import java.io.*;
import java.text.*;
import java.util.*;

public class FindFiles2{

	File address;
	String key;
	List<String> resultname = new ArrayList<String>();
	List<File> resultfile = new ArrayList<File>();

	public FindFiles2(String folder, String length){

		key = length;
		address = new File(folder);
		findAccordingLength(address, key);
	}

	public void findAccordingLength(File directory, String str2){
		if(directory.exists()){
			if(directory.isDirectory()){
				String[] children = directory.list();

				for (String c : children){
					File childobject = new File(directory.getPath(), c);

					findAccordingLength(childobject, str2);
				}
			}else{
				long filesize = directory.length();

				DecimalFormat df = new DecimalFormat("#.00");
				String filesizeString = df.format((double)filesize);

				if (filesizeString.compareTo(str2) > 0){
					resultname.add(directory.getName());
					resultfile.add(directory);
					//System.out.println(directory.getPath());
				}
			}
		}
	}

	/*public void deleteSelectedFiles(String[] str3){
		for(String s : str3){
			for(File f : resultfile){
				if(str3.equals(f.getName())){
					f.delete();
					break;
				}
			}
		}
	}*/
}