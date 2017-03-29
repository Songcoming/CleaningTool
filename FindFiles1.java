package cleaningtools;

import java.io.*;
import java.util.*;

public class FindFiles1{

	File address;
	String key;
	List<String> resultname = new ArrayList<String>();
	List<File> resultfile = new ArrayList<File>();

	public FindFiles1(String folder, String ext){

		key = ext;
		address = new File(folder);

		findAccordingExt(address, key);
	}

	public void findAccordingExt(File directory, String str2){

		if(directory.exists()){
			if(directory.isDirectory()){
				String[] children = directory.list();

				for(String c : children){
					File childobject = new File(directory.getPath(), c);
					findAccordingExt(childobject, str2);
				}
			}else{
				String filename = directory.getName();

				String[] factors = filename.split("\\.");
				if(str2.equals(factors[factors.length - 1])){
					resultname.add(directory.getName());
					resultfile.add(directory);

					//System.out.println(resultname);

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