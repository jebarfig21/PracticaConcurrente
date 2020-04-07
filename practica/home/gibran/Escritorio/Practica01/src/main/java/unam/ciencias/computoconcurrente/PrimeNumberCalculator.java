package unam.ciencias.computoconcurrente;
import java.lang.Math;
import java.lang.*;

public class PrimeNumberCalculator implements Runnable{

		private int threads;
		private static int numPrimo;
		public static boolean result;
		public static int longitudSubInter; //Dividimos el intervalo [2,N-1] en this.threads cantidad de sub interbalos, uno por cada hilo


		public PrimeNumberCalculator() {
				this.threads = 1;
		}

		public PrimeNumberCalculator(int threads) {
				this.threads = threads > 1 ? threads : 1;
		}

		public PrimeNumberCalculator(int threads , int numPrimo) {
				this.threads = threads > 1 ? threads : 1;
				this.numPrimo = numPrimo;
		}


		public boolean isPrime(int n) throws InterruptedException{
				result = true;
				if(n==0 || n==1){
						return false;
				}
				for(int i = 0; i < threads; i++){
						Thread t = new Thread(new PrimeNumberCalculator(threads,n));
						t.setName(String.valueOf(i));
						t.start();
						t.join();
				}
				return result;
		}

		//Suponiendo que tenemos 4 thread fijo y no vamos a varias ese numero, esta es solo una primer aproximacion por lo que despues se peude profundiazr a varias el tamaño de los segmentso y el nuemro de threads
		@Override
		public void run(){

	  String ID = Thread.currentThread().getName();
		int idHilo = Integer.valueOf(ID);
		longitudSubInter = (int)Math.round(((numPrimo+.0)/(threads+.0)));

		if(longitudSubInter*(idHilo+1) >= numPrimo/2){
			for (int i = 2+(longitudSubInter * idHilo); i < numPrimo-1; i++ ){
				if(Math.abs(numPrimo)%i == 0){
					result = false;
					return;
				}
		}
			}else{
				for (int i = 2+(longitudSubInter*idHilo); i < (2+(longitudSubInter * (idHilo+1)))/2; i++ ){
					if(Math.abs(numPrimo)%i == 0){
			result = false;
			return;
					}
			}
			}
	}

}
