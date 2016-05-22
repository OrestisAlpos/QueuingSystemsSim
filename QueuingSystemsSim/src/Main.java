
public class Main {

	private int K, l;
	private static int N=10;

	static Situation[][] situations;
	
	//Synarthsh pou apofainetai gia ton typo tou gegonotos.
	//Dexetai ws orisma tous 3 rythmous l, m_a, m_b (borei na einai kai 0, an vriskomaste se katastash opou kapoio gegonos einai adynato)
	//Returns 0 for new arrival, 1 for serving-termination in server A, 2 for serving termination in server B.
	private int getRandomEvent(int l,int m_a, int m_b){
		//if (l+m_a+m_b == 0) throw error;
		double random = Math.random(); 
		if (random < l / (l + m_a + m_b)) {
			return 0;
		} else if (random < l + m_a / (l + m_a + m_b)){
			return 1;
		} else {
			return 2;
		}
	}
	
	public static void main(String[] args) {
		
		situations = new Situation[N][2];
		
		

	}

}
