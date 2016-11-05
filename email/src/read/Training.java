package read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Training {
	private String[] train_ham;
	private String[] train_spam;
	private File train_ham_dir;
	private File train_spam_dir;
	private String hampath="d:\\data\\train\\ham";
	private String spampath="d:\\data\\train\\spam";
	public HashMap<String,Integer> map_ham=new HashMap<String,Integer>();
	public HashMap<String,Integer> map_spam=new HashMap<String,Integer>();
	public HashMap<String,Double> map_p=new HashMap<String,Double>();
	public Training() throws IOException {
		train_ham_dir=new File(hampath);
		train_spam_dir=new File(spampath);
		if(!train_ham_dir.isDirectory()||!train_spam_dir.isDirectory()) {
			System.out.println("no such direc");
		}
		else {
			this.train_ham=train_ham_dir.list();
			this.train_spam=train_spam_dir.list();
			get_table();
			//calp();
		}
	}
	public static String getText(String filepath) throws IOException {
		//System.out.printf("%s\n",filepath);
		InputStreamReader isreader=new InputStreamReader(new FileInputStream(filepath));
		BufferedReader reader=new BufferedReader(isreader);
		String oneline;
		StringBuilder sb=new StringBuilder();
		while((oneline=reader.readLine())!=null) {
			sb.append(oneline+" ");
		}
		isreader.close();
		reader.close();
		return sb.toString();
	}
	public void get_table() throws IOException {
		//String one_ham;
		int count=0;
		for(String onetxt:this.train_ham) {
			String text=getText(hampath+"\\"+onetxt);
			String[] wordlist=text.split(" ");
			for(String word:wordlist) {
				if(map_ham.containsKey(word)!=false) {
					count=map_ham.get(word)+1;
					map_ham.put(word, count);
				}
				else {
					map_ham.put(word, new Integer(1));
					map_p.put(word,new Double(1));
				}
			}
		}
		count=0;
		for(String onetxt:this.train_spam) {
			String text=getText(spampath+"\\"+onetxt);
			String[] wordlist=text.split(" ");
			for(String word:wordlist) {
				if(map_spam.containsKey(word)!=false) {
					count=map_spam.get(word)+1;
					map_spam.put(word, count);
				}
				else {
					map_spam.put(word, new Integer(1));
					map_p.put(word,new Double(1));
				}
			}
		}
	}
	public void calp() {
		//Iterator<String,Integer> iter=map_p.entrySet().iterator();
		for(HashMap.Entry<String,Double> entry:map_p.entrySet()) {
			String word=entry.getKey().toString();
			double c1=0,c2=0,p=0;
			if(map_ham.containsKey(word)!=false) {
				c1=map_ham.get(word);
			}
			if(map_spam.containsKey(word)!=false) {
				c2=map_spam.get(word);
			}
			p=c1/(c1+c2);
			if(Double.isNaN(p))
				System.out.println(p);
			map_p.put(word, p);
		}
	}
}
