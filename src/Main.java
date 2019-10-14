import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
 
public class Main 
{
	private static ArrayList<Integer>[][] arr = (ArrayList<Integer>[][]) new ArrayList[300][300];
	private static int count = 0;
	private static Graph graph = new Graph();
	public static void main(String[] args) throws IOException
	{
		
		//BufferedReader是可以按行读取文件
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("subway.txt")));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("lineinfo.txt")));
		String strLine=null;
		Vertex lastVertex = null;
		int flag = 0;
		int TNumber = 0;
	    while ((strLine = reader.readLine()) != null){
			String[] chars = strLine.split(" ");  
			for (int i = 0; i < chars.length; i++) { 
				if(chars[i].contains("#")) {
					String[] charss = chars[i].split("#");
					Vertex vertex = graph.getVertexByName(charss[0], count);
					if(vertex == null) {
						vertex = new Vertex();
						vertex.setVerName(charss[0]);
						vertex.setVerId(count);
						Graph.getVertexArray()[count] = vertex;
//						System.out.println(count+":"+charss[0]);
						count++;
					}
					writer.write(" "+charss[0]);
					vertex.getTNumber().add(TNumber);
				}
				else if(chars[i].contains(":")) {
					String[] charss = chars[i].split(":");
//					array = s.get(Integer.parseInt(charss[0]));
					if(flag == 0) {
						flag = 1;
						writer.write(charss[0]+":");
					}
					else {
						writer.write("\n"+charss[0]+":");
					}
					TNumber = Integer.parseInt(charss[0]);
				}
				else {
//					array.add(chars[i]);
					Vertex vertex = graph.getVertexByName(chars[i], count);
					if(vertex == null) {
						vertex = new Vertex();
						vertex.setVerName(chars[i]);
						vertex.setVerId(count);
						Graph.getVertexArray()[count] = vertex;
//						System.out.println(count+":"+chars[i]);
						count++;
					}
					writer.write(" "+chars[i]);
					vertex.getTNumber().add(TNumber);
				}
					
			}  
	    }
	    graph.setVerNum(count);
	    writer.close();
	    reader.close();
	    reader = new BufferedReader(new InputStreamReader(new FileInputStream("lineinfo.txt")));
	    while ((strLine = reader.readLine()) != null){
			String[] chars = strLine.split(" ");  
			for (int i = 0; i < chars.length; i++) { 
				if(chars[i].contains(":")) {
//					String[] charss = chars[i].split(":");
//					array = s.get(Integer.parseInt(charss[0]));
					lastVertex = null;
				}
				else {
					if(lastVertex != null ) {
						Vertex vertex1 = graph.getVertexByName(lastVertex.getVerName(), count);
						Vertex vertex2 = new Vertex();
						vertex2.setVerName(chars[i]);
						vertex2.setNextNode(vertex1.getNextNode());
						vertex1.setNextNode(vertex2);
						Vertex reV2 = graph.getVertexByName(chars[i] , count);
						Vertex reV1 = new Vertex();
						reV1.setVerName(lastVertex.getVerName());
						reV1.setNextNode(reV2.getNextNode());
						reV2.setNextNode(reV1);
						lastVertex = vertex2;
					}
					else {
						Vertex vertex = new Vertex();
						vertex.setVerName(chars[i]);
						lastVertex = vertex;
					}
				}
			}  
	    }
	    int INF = 400;
	    reader.close();
	    System.out.println(count);
	    int[][] dist = new int[count][count];
	    for(int i=0 ; i < count; i++) {
	    	for(int j=0; j < count; j++) {
	    		dist[i][j] = INF;
	    	}
	    }
	    for(int i=0; i<count; i++) {
	    	Vertex vertex = Graph.getVertexArray()[i];
	    	int start = vertex.getVerId();
	    	while(vertex != null) {
	    		int end = graph.getVertexByName(vertex.getVerName(), count).getVerId();
	    		if(start != end) {
	    			dist[start][end] = 1;
	    			dist[end][start] = 1;
	    		}
//	    		System.out.print(vertex.getVerName()+" ");
	    		vertex = vertex.getNextNode();
	    	}
//	    	System.out.println();
	    }
	    
	    for(int i=0; i<count; i++) {
	    	for(int j=0; j<count; j++) {
	    		ArrayList<Integer> arrayList = new ArrayList<Integer>();
	    		arr[i][j] = arrayList;
	    	}
	    }
	    for(int k=0; k<count; k++) {
	    	for(int i=0; i<count; i++) {
	    		for(int j=0; j<count; j++) {
	    			if(dist[i][j] > (dist[i][k]+dist[k][j])) {
	    				dist[i][j] = dist[i][k]+dist[k][j];
	    				arr[i][j].clear();
	    				arr[i][j].add(k);
	    			}
//	    			else if(dist[i][j] == (dist[i][k]+dist[k][j])) {
//	    				arr[i][j].add(k);
//	    			}
	    		}
	    	}
	    }
	    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("dist1.txt")));
	    for(int i=0; i<count; i++) {

	    	for(int j=0; j<count; j++) {
	    		
	    		writer.write(String.valueOf(i));
	    		Output(i,j,writer);
	    		writer.write(" "+j+"\n");

	    	}
	    }
//	    System.out.println(dist[49][37]);
//	    System.out.println(arr[49][37].get(0));
//	    writer.write(String.valueOf(49));
//		Output(49,80,writer);
//		writer.write(" "+80+"\n");
	    writer.close();
	    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("vertex.txt")));
	    for(int i= 0; i<count; i++) {
	    	writer.write(Graph.getVertexArray()[i].getVerName()+" "+Graph.getVertexArray()[i].getVerId());
	    	for(Integer TN : Graph.getVertexArray()[i].getTNumber()) {
//	    		System.out.println(TN);
	    		writer.write(" "+TN);
	    	}
	    	writer.write("\n");
//	    	if(Graph.getVertexArray()[i].getTNumber().size() == 1) {
//	    		writer.write(" "+Graph.getVertexArray()[i].getTNumber().iterator().next()+"\n");
//	    	}
//	    	else
//	    		writer.write("\n");
	    }
	    writer.close();
	    System.out.println();
	}
	
	private static void Output(int i, int j,BufferedWriter writer) {
		if(arr[i][j].size() == 0) {
			return;
		}
		else {
			for(int k=0; k<arr[i][j].size(); k++) {
				Output(i,arr[i][j].get(k),writer);
				try {
					writer.write(" "+arr[i][j].get(k));
				} catch (IOException e) {
					e.printStackTrace();
				}
				Output(arr[i][j].get(k),j,writer);
			}
		}
	}
	
}