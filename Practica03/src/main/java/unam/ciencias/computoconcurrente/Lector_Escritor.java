package unam.ciencias.computoconcurrente;
//import Monitor;


enum Rol {ESCRITOR, LECTOR};

/**
 *  Cada lector y escritor se ejecuta en un hilo.
 */

public class Lector_Escritor implements Runnable {
    public static int DEFAULT_NUMERO_ESCRITORES = 5; //Numero se escritores
    public static int DEFAULT_NUMERO_LECTORES = 5;   //Numero de lectores
    public static int numeroEscritores;              //Numero de escritores
    public static int numeroLectores;                //Numero de lectores
    public static int totalPersonas;                //Numero de lectores
    public static boolean bloqueado;                        //Es Escritor? true, es Lector? false
    public  static Monitor mon = new Monitor();      //Monitor en comun
    private boolean escritor;                        //Es Escritor? true, es Lector? false
    private Rol rol;


    /*
    * @param escritor : Bolean, True si es escritor, False si es lector
    * Constructor que recive true o false dependeinedo de ser escritor o lector
    */
    public Lector_Escritor(Rol rol){
      this.escritor = escritor;
      this.rol = rol;
    }

    /*
    * @return True : Si el objeto es escritor
    * @return False : Si el objeto es lector
    */
    public boolean esEscritor(){
         return rol == Rol.ESCRITOR ;
    }

    /*
    *
    *
    */
    public void run() {
      while(true){                             //Ejecucion Infinita
        if(esEscritor()){
          boolean entro = mon.entraEscritor(Thread.currentThread().getName());
          if(entro){
            try{
              System.out.println("El "+Thread.currentThread().getName()+" esta Escribiendo....");
              Thread.sleep(2000);                  //Supuesta sección critica
            }catch(InterruptedException exc){}

          mon.saleEscritor(Thread.currentThread().getName());
        }
        else{
            System.out.println("El"+ Thread.currentThread().getName() +"intento entrar y no pudo");
          }
        }

        else{                                   //Si es lector
          mon.entraLector(Thread.currentThread().getName());
        try{
          Thread.sleep(6000);                  //Supuesta sección critica
        }catch(InterruptedException exc){}

          mon.saleLector(Thread.currentThread().getName());
        }
      }
    }

    public static void main(String[] args){
      numeroEscritores = 5;
      numeroLectores = 5;
      totalPersonas = numeroEscritores+numeroLectores;
      Thread[] hilos = new Thread[totalPersonas];

      //For para crear ecritores
      for(int i = 0; i< numeroEscritores ;i++){
        Runnable runnable = new Lector_Escritor(Rol.ESCRITOR);
        hilos[i] = new Thread(runnable);
        hilos[i].setName("Escritor "+i);
        hilos[i].start();
      }

      //For para crear lectores
      for(int i = numeroEscritores; i<totalPersonas ;i++){
        Runnable runnable = new Lector_Escritor(Rol.LECTOR);
        hilos[i] = new Thread(runnable);
        hilos[i].setName("Lector "+numeroEscritores+i);
        hilos[i].start();
      }

      for(int i = 0; i< totalPersonas ;i++){
        try{
          hilos[i].join();
        }catch(Exception ex){}
      }

    }

}
