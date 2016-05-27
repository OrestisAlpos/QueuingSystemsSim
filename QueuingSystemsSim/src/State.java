
public class State {
	
	public int arrivalCount;
	public int clientsServedByA, clientsServedByB;
	public String name;
	public double prob;
	
	public State(String name) {
		this.arrivalCount = 0;
		this.clientsServedByA = 0;
		this.clientsServedByB = 0;
		this.name = name;
	}	
	
}

