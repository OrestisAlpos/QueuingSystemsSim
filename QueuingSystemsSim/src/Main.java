
public class Main {

	private static int K, l, m_a=4, m_b=1;
	private static int N=10, losses=0, totalClients=0, totalClientsServedByA = 0, totalClientsServedByB = 0,totalClientsInSystem = 0, reps = 0;
	static State[][] states;
	
	
	public static void main(String[] args) {
		
		// State-matrix initialization. We use the state-matrix states to track events in each state.
		int i;
		states = new State[N+1][2];
		states[0][0] = new State("" + 0);
		states[0][1] = new State("ERROR. State should not be reached");
		for (i=1; i<=K; i++){
			states[i][0] = new State(i + "a");
			states[i][1] = new State(i + "b");
		}
		for (i=K+1; i<=N; i++){
			states[i][0] = new State("" + i);
			states[i][1] = new State("ERROR. State should not be reached");
		}

		
		int event;
		int currentState_i = 0;
		int currentState_j = 0;
		
		
		while (reps<100) {		
			// NEW EVENT
			if (currentState_i == 0) {	
				//No clients currently in the System. Only possible event the arrival of a new client.
				event = RandomEvent.getRandomEvent(l, 0, 0);
			} else if ((currentState_i <= K) && (currentState_j == 0)) { 
				//Less or exactly K clients currently in the System, with Server B turned off.
				event = RandomEvent.getRandomEvent(l, m_a, 0);
			} else if ((currentState_i == 1) && (currentState_j == 1)){
				//1 client in the System on Server B. The only case in which Server B is on and Server A is off.
				event = RandomEvent.getRandomEvent(l, 0, m_b);
			}else {	
				// More than K clients currently in the System OR less than K clients with Servers A and B turned on. All events are possible.
				event = RandomEvent.getRandomEvent(l, m_a, m_b);
			}
			
			
			//SWITCH TO NEW SITUATION BASED ON THE EVENT
			//First case: The event was a new arrival
			if (event == 0) {	
				states[currentState_i][currentState_j].arrivalCount++;
				totalClients++;
				if (currentState_i == 10){
					losses++;
				} else {
					currentState_i++;
				}
				//Second case: The event was a termination in Server A
			}else if (event==1) {
				states[currentState_i][currentState_j].clientsServedByA++;
				totalClientsServedByA++;
				currentState_i--;
				if (currentState_i == K){
					currentState_j = 1;
				}
				// Third case: The event was a termination in Server B	
			}else {		
				states[currentState_i][currentState_j].clientsServedByB++;
				totalClientsServedByB++;
				currentState_i--;
				currentState_j = 0;
			}
			
			totalClientsInSystem += currentState_i;
			reps++;
			System.out.println(states[currentState_i][currentState_j].name);
		}
			
 	}
}
	