/**
*
*这里考虑单例模式
*/
public class Graph {
	private static Vertex[] vertexArray = new Vertex[330];
	private int verNum = 0;
	
	public Graph() {
		
	}
	
	public Graph( int verNum) {
		this.verNum = verNum;
	}
	
	public static Vertex[] getVertexArray() {
		return vertexArray;
	}
	public int getVerNum() {
		return verNum;
	}
	public void setVerNum(int verNum) {
		this.verNum = verNum;
	}
	public Vertex getVertexByName(String verName,int count) {
		for(int i= 0 ; i < count; i++) {
			if(vertexArray[i].getVerName().equals(verName)) {
				return vertexArray[i];
			}
		}
		return null;
	}
	public Vertex getVertexById(int verId,int count) {
		for(int i= 0 ; i < count; i++) {
			Vertex v = vertexArray[i];
			if(vertexArray[i].getVerId() == verId) {
				return vertexArray[i];
			}
		}
		return null;
	}
}
