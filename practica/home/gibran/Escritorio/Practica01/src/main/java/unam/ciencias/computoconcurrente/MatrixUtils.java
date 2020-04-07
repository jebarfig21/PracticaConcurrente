package unam.ciencias.computoconcurrente;
import java.util.*;

public class MatrixUtils implements Runnable{
    private int threads;
    //private static int[] posiblesMinimos; // Arreglo para que cada hilo guarde su minimo encontrado
    //private static int[] matrixGlobal; 
    //private static int secciones;

    public double [] result;
    public int longitudSubInter;
    public List<Integer> list = new ArrayList<Integer>();

    public MatrixUtils() {
        this.threads = 1;
    }

    public MatrixUtils(int threads) {
        this.threads = threads;
    }

    //Thread representa el numero de threads activos
    //list es la matriz pasada convertida en vector que sera partida en cachos
    //result es el arreglo final en donde meteremos los promedios de nuestras submatrices
    public MatrixUtils(int threads,List<Integer> list,double[] result) {
        this.threads = threads;
        this.list = list;
        this.result = result;
    }

    @Override
    public void run() {

        double prom = 0;
        double elementos = 0;
        String ID = Thread.currentThread().getName();
        int idHilo = Integer.valueOf(ID);
        longitudSubInter = list.size()/threads;
        //Recorremos nuestras matrices
        if(longitudSubInter*(idHilo+1) >= list.size()){
          for (int i = longitudSubInter * idHilo; i < list.size(); i++ ){
            prom+=list.get(i);
            elementos++;
          }
          //Guardamos el resultado del promedio de nuestra submatriz en un arreglo
          result[idHilo]=prom/elementos;
          return;

        }else{
            for (int i = (longitudSubInter*idHilo); i < (longitudSubInter * (idHilo+1)); i++ ){
              prom+=list.get(i);
              elementos++;
            }
            //Guardamos el resultado del promedio de nuestra submatriz en un arreglo
            result[idHilo]=prom/elementos;
            return;
          }
        
    }

    public double findAverage(int[][] matrix) throws InterruptedException{
        
        //Convertimos nuestro arreglo en un vector que sera pasado como parametro
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) { 
            list.add(matrix[i][j]); 
            }
        }
        
        result = new double[threads];

            for(int i = 0; i < threads; i++){
                Thread t = new Thread(new MatrixUtils(threads,list,result));
                t.setName(String.valueOf(i));
                t.start();
                t.join();
                //System.out.println(result[0]);
                //System.out.println(result[1]);
            }

            double promedioFinal = 0;
            //Sacamos el promedio de nuestro arreglo final
             //System.out.println(result.length);
            for(int i = 0; i < result.length; i++){
                promedioFinal += result[i];
            }

            //System.out.println(result.length);

            return promedioFinal/result.length;
       
    }

    /**
     * Metodo que recorre una matriz de dos dimensiones 
     * @param matrix - matriz de dos dimensiones 
     * @return promedio - promedio de la matriz
     */
    public double Average(int[][] matrix){
        
        return 1.0;
    }

}
