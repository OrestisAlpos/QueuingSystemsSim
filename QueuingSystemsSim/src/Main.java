import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class Main {

	public static void main(String[] args) {
		
		int K, m_a=4, m_b=1, N=10;
		for (K=1; K<=10; K++) {
		
			int losses=0, totalArrivals=0, totalClientsServedByA = 0, totalClientsServedByB = 0, reps = 0, iter = 0;
			double averageClients = 0, averageClients_aux = 0;
			State[][] states;
			String outString = new String();
			int l= Integer.parseInt(args[0]);	
			
			// STATES INITIALIZATION. WE USE A STATE-ARRAY TO INDEX EACH POSSIBLE STATE
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
			
			
			while(true) {
				iter++;
				while (reps < 1000 * iter) { //Statistics calculated every 1000 events.
				
					// NEW RANDOM EVENT
					
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
					
					
					//SWITCH TO NEW SITUATION BASED ON THE RANDOM EVENT
					
					//First case: The event was a new arrival
					if (event == 0) {	
						totalArrivals++;
						states[currentState_i][currentState_j].arrivalCount++;
						if (currentState_i == 10){
							losses++;
						} else {
							currentState_i++;
							if (currentState_i == K+1){
								currentState_j = 0;
							}
						}
						
					//Second case: The event was a termination in Server A
					}else if (event==1) {
						totalClientsServedByA++;
						states[currentState_i][currentState_j].clientsServedByA++;
						currentState_i--;
						if (currentState_i == K){
							currentState_j = 1;
						}
						
					// Third case: The event was a termination in Server B	
					}else {		
						totalClientsServedByB++;
						states[currentState_i][currentState_j].clientsServedByB++;
						currentState_i--;
						currentState_j = 0;
					}
					
					reps++;
				}//while(reps<1000)
				
				//UPDATE THE ERGODIC PROBABILITY OF EACH STATE (Pi, for i=0..10)
				states[0][0].prob = states[0][0].arrivalCount / (double)totalArrivals;
				for (i=1; i<=K; i++){
					states[i][0].prob = states[i][0].arrivalCount / (double)totalArrivals;
					states[i][1].prob = states[i][1].arrivalCount / (double)totalArrivals;
				}
				for (i=K+1; i<=N; i++){
					states[i][0].prob = states[i][0].arrivalCount / (double)totalArrivals;
				}
				
				//CALCULATE THE AVERAGE NUMBER OF CLIENTS IN SYSTEM, BASED ON THE ERGODIC PROBABILITIES. 	Ó(n*Pn)
				averageClients_aux = averageClients;
				averageClients = 0;
				for (i=1; i<=K; i++){
					averageClients += i * states[i][0].prob;
					averageClients += i * states[i][1].prob;		}
				for (i=K+1; i<=N; i++){
					averageClients += i * states[i][0].prob;
				}
				outString = outString.concat("\n" + averageClients);
				
				if ((averageClients < 1.001 * averageClients_aux) && (averageClients > 0.999 * averageClients_aux)) {
					break;
				}
				
			}//while(true)
			
			//OUTPUT RESULTS TO A TXT FILE
			try { PrintWriter writer = new PrintWriter("result_L" + l + "_K" + K + ".txt", "UTF-8");
				  writer.println(outString);
				  writer.close();
				} catch (UnsupportedEncodingException e) {
					System.err.println("Unable to write to file: " + e.getMessage());
				} catch (FileNotFoundException e) {
					System.err.println("Unable to write to file: " + e.getMessage());
			}
			
	
		}//for	
	}
}
	