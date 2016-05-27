
public class RandomEvent {
	
		//Input parameters are the three rates, l, m_a and m_b. Can also be zero, but not all concurrently.
		//Returns 0 for new arrival, 1 for serving-termination in server A, 2 for serving termination in server B.
		public static int getRandomEvent(int l, int m_a, int m_b){
			//if (l+m_a+m_b == 0) error;
			double random = Math.random(); 
			if (random < (double)l / (l + m_a + m_b)) {
				return 0;
			} else if (random < (double)(l + m_a) / (l + m_a + m_b)){
				return 1;
			} else {
				return 2;
			}
		}
}

