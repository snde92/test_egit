package read;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class testing {
	private String[] testlist;
	private File test_dir;
	private String testpath="d:\\data\\test";
	public double prob;
	public HashMap<String,Integer> map_test=new HashMap<String,Integer>();
	public ArrayList<Double> pl=new ArrayList<Double>();
	public ArrayList<Double> p=new ArrayList<Double>();
	public ArrayList<Double> result=new ArrayList<Double>();
	//public ArrayList<Integer> result=new ArrayList<Integer>();
	public ArrayList<String> testfile=new ArrayList<String>();
	//public double[] p;
	//public int[] result;
	public testing() throws IOException {
		test_dir=new File(testpath);
		if(!test_dir.isDirectory()) {
			System.out.println("no such direc");
		}
		else {
			this.testlist=test_dir.list();
			Training tr=new Training();
			double dcount=0;
			int icount=0,cnt=0;
			for(String onetxt:this.testlist) {
				testfile.add(onetxt);
				String text=getText(testpath+"\\"+onetxt);
				String[] wordlist=text.split(" ");
				int i=0;
				for(String word:wordlist) {
					if(tr.map_p.containsKey(word)!=false) {
						dcount=tr.map_p.get(word);
						//System.out.println(i);
						pl.add(dcount);
					}
					else {
						pl.add(new Double(1));
					}
					//if(i%100==0)
						//System.out.println(pl.get(i));
					i++;
					if(map_test.containsKey(word)!=false) {
						icount=map_test.get(word)+1;
						map_test.put(word, icount);
					}
					else {
						map_test.put(word, new Integer(1));
					}
				}
				double up=1,down=1;
				for(int j=0;j<i;j++) {
					//if(j<10)
						//System.out.println(up+"\t"+down);
					up=up*pl.get(j);
					down=down*(1-pl.get(j));
					//if(Double.isNaN(up)||Double.isNaN(down))
						//System.out.println(up+"\t"+down);
				}
				
				p.add(up/((up+down)));
				//result.add((p.get(cnt)>0.5)?1:-1);
				result.add((p.get(cnt)));
				cnt++;
			}
		}
	}
	public static String getText(String filepath) throws IOException {
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
	public static void main(String[] args) throws IOException {
		testing t=new testing();
		String outfile="d://out1.txt";
		File file=new File(outfile);
		file.createNewFile();
		FileWriter fw=new FileWriter(outfile);
		BufferedWriter bw=new BufferedWriter(fw);
		for(int i=0;i<t.testfile.size();i++) {
			bw.write(t.testfile.get(i)+" "+t.result.get(i));
			bw.newLine();
			bw.flush();
		}
		bw.close();
		fw.close();
	}
}
