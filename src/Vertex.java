import java.util.HashSet;
import java.util.Set;

/**
*
*
*/
public class Vertex {
	private String verName;
	private Vertex nextNode = null;
	private int verId;
	private Set<Integer> TNumber = new HashSet<Integer>();
	
	public String getVerName() {
		return verName;
	}
	public void setVerName(String verName) {
		this.verName = verName;
	}
	public Vertex getNextNode() {
		return nextNode;
	}
	public void setNextNode(Vertex nextNode) {
		this.nextNode = nextNode;
	}
	public int getVerId() {
		return verId;
	}
	public void setVerId(int verId) {
		this.verId = verId;
	}
	public Set<Integer> getTNumber() {
		return TNumber;
	}
	public void setTNumber(Set<Integer> tNumber) {
		TNumber = tNumber;
	}
	
	
}
