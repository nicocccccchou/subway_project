import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
*
*
*/
public class LineTest {
	private static Map<Integer,String> map = new HashMap<Integer, String>();
	private static Graph graph = new Graph();
	private static int first = 0;
	public static int isWriter = 0;
	
	public static void main(String[] args) throws IOException {
		
		//BufferedReader是可以按行读取文件
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("vertex.txt")));
		String line = null;
		int count = 0;
		while((line = reader.readLine()) != null) {
			String[] chars = line.split(" ");
//			System.out.println(chars[0]);
			Vertex v = new Vertex();
			v.setVerName(chars[0]);
			v.setVerId(Integer.parseInt(chars[1]));
			for(int i=2; i<chars.length; i++) {
				v.getTNumber().add(Integer.parseInt(chars[i]));
			}
			Graph.getVertexArray()[count] = v;
			count++;
		}
		reader.close();
		reader = new BufferedReader(new InputStreamReader(new FileInputStream("SNumber.txt")));
		while((line = reader.readLine()) != null) {
			String[] chars = line.split(" ");
			map.put(Integer.parseInt(chars[1]), chars[0]);
		}
		reader.close();
		if(args[2].equals("-map")) {
			isWriter = 1;
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("subway.txt")));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("")));
			while((line = reader.readLine()) != null) {
				read(line,writer);
				System.out.println("\n");
			}
			writer.close();
			reader.close();
			
		}
		else if(args[2].equals("-a")) {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[7])));
			int flag = 0;
			isWriter = 0;
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("subway.txt")));
			while((line = reader.readLine()) != null) {
				if(map.get(Integer.parseInt(line.split(" ")[0].split(":")[0])).equals(args[3])) {
					read(line,writer);
					flag = 1;
				}
			}
			reader.close();
			writer.close();
			if(flag == 0) {
				System.out.println("请输入正确的线路名称");
			}
		}
		else if(args[2].equals("-b")) {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[8])));
			if(graph.getVertexByName(args[3], count) == null) {
				System.out.println("请输入正确的始发站名称");
				return;
			}
			if(graph.getVertexByName(args[4], count) == null) {
				System.out.println("请输入正确的终点站名称");
				return;
			}
			if(args[3].equals(args[4])) {
				System.out.println("输入的始发站名称和终点站名称相同");
				return;
			}
			int start = graph.getVertexByName(args[3], count).getVerId();
			int end = graph.getVertexByName(args[4], count).getVerId();
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("dist1.txt")));
			while((line = reader.readLine()) != null) {
				String[] chars = line.split(" ");
				if(Integer.parseInt(chars[0]) == start && Integer.parseInt(chars[chars.length-1]) == end) {
					readline(chars,0,count,writer);
					System.out.println("\n");
				}
			}
			reader.close();
			writer.close();
		}
		else {
			System.out.println("请输入正确参数");
		}
	}
	
	public static void read(String line, BufferedWriter writer) throws IOException {
		int TNumber = 0;
		int flag = 0;
		String[] chars = line.split(" ");  
		for (int i = 0; i < chars.length; i++) { 
			if(chars[i].contains("#")) {
				String[] charss = chars[i].split("#");
				System.out.print(" "+charss[0]+"(可转");
				if(isWriter == 0)writer.write(" "+charss[0]+"(可转");
				for(int j=1; j<charss.length; j++) {
					if(Integer.parseInt(charss[j]) != TNumber) {
						System.out.print(map.get(Integer.parseInt(charss[j])));
						if(isWriter == 0)writer.write(map.get(Integer.parseInt(charss[j])));
						if(j!= charss.length - 1) {
							System.out.print(",");
							if(isWriter == 0)writer.write(",");
						}
						else {
							System.out.print(")");
							if(isWriter == 0)writer.write(")");
						}
					}
						
				}
				
			}
			else if(chars[i].contains(":")) {
				if(flag != 0) {
					System.out.println("\n");
					if(isWriter == 0)writer.write("\n");
				}
				else {
					flag = 1;
				}
				String[] charss = chars[i].split(":");
				TNumber = Integer.parseInt(charss[0]);
				System.out.print(map.get(TNumber)+":");
				if(isWriter == 0)writer.write(map.get(TNumber)+":");
			}
			else {
				System.out.print(" "+chars[i]);
				if(isWriter == 0)writer.write(" "+chars[i]);
			}
				
		}  
	}
	
	public static void readline(String[] chars,int start,int count, BufferedWriter writer) throws IOException {
		int putsubNumber = 0;
		for(int i=start; i<chars.length; i++) {
			Vertex v = graph.getVertexById(Integer.parseInt(chars[i]), count);
			if(first == 0) {
				System.out.print(v.getVerName());
				writer.write(v.getVerName());
				first = 1;
			}
			else {
				System.out.print("-->"+v.getVerName());
				writer.write("-->"+v.getVerName());
			}	
			
			if(v.getTNumber().size() > 1) {
				Set<Integer> s1 = new HashSet<Integer>();
				s1.addAll(v.getTNumber());
				if(i+1 != chars.length) {
					s1.retainAll(graph.getVertexById(Integer.parseInt(chars[i+1]), count).getTNumber());
					if(putsubNumber != s1.iterator().next()) {
						System.out.print("-->乘坐"+map.get(s1.iterator().next()));
						writer.write("-->乘坐"+map.get(s1.iterator().next()));
						putsubNumber = s1.iterator().next();
					}
				}
					
					
				
			}
			else {
				if(putsubNumber == 0) {
					System.out.print("-->乘坐"+map.get(v.getTNumber().iterator().next()));
					writer.write("-->乘坐"+map.get(v.getTNumber().iterator().next()));
					putsubNumber = v.getTNumber().iterator().next();
				}
					
			}

		}
	}
}
