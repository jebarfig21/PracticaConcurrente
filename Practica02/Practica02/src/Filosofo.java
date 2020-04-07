import java.util.concurrent.ThreadLocalRandom;

   public class Filosofo implements Runnable{
     public static int DEFAULT_TABLE_SIZE = 5;
     private Object palilloIzquierda;
     private Object palilloDerecha;
     private Boolean comio ;

    public Filosofo(Object palilloIzquierda,Object palilloDerecha){
        this.palilloIzquierda= palilloIzquierda;
        this.palilloDerecha= palilloDerecha;
        this.comio= false;
    }

    public void tomarPalillo(String lado) throws InterruptedException{
        System.out.println(Thread.currentThread().getName()+" ah tomando el palillo "+lado);
        Thread.sleep((int)(Math.random()*100));
    }

    public void pensar() throws InterruptedException{
      System.out.println(Thread.currentThread().getName()+" Esta pensando");
      Thread.sleep((int)(Math.random()*100));
    }

    public void comer()throws InterruptedException{
      System.out.println(Thread.currentThread().getName()+" Esta Comiendo");
          Thread.sleep((int)(Math.random()*100));
          this.comio = true;
        }


    @Override
    public void run() {
        try{
            while(!comio){
                pensar();
                synchronized (palilloIzquierda){
                    tomarPalillo("Izquierdo");
                    synchronized (palilloDerecha){
                      tomarPalillo("Derecho");
                      comer();
                    }
                }
            }
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
            return;
        }
    }

  public static void main(String argv[]) throws InterruptedException{
        Filosofo filosofos[] = new Filosofo[DEFAULT_TABLE_SIZE];
        Object palillos[] = new Object[DEFAULT_TABLE_SIZE];
        for(int i=0;i<filosofos.length;i++){
            palillos[i] = new Object();
        }
        for(int i=0;i<filosofos.length;i++){
            Object palilloIzquierda = palillos[i];
            Object palilloDerecha = palillos[(i+1)%DEFAULT_TABLE_SIZE];
            if(i == filosofos.length-1){
                filosofos[i] = new Filosofo(palilloIzquierda,palilloDerecha);
            }else{
                filosofos[i] = new Filosofo(palilloDerecha,palilloIzquierda);
            }
            Thread t = new Thread(filosofos[i] ," Filosofo "+(i+1));
            t.start();
        }


        //System.out.println("Todos han comido");
  }
 }
