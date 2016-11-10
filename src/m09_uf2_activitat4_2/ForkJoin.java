package m09_uf2_activitat4_2;

import java.util.concurrent.ForkJoinPool;
import static java.util.concurrent.ForkJoinTask.invokeAll;
import java.util.concurrent.RecursiveTask;

/**
 * Aquesta clase ForkJoin extendeix de RecursiveTask<Integer> per tal
 * de poder fer recursivitat. Declarem l'array, un inici i un final d'aquest
 * per a poder separar l'array en arrays mes petits i el tamany d'aquest.
 * @author ALUMNEDAM
 */
public class ForkJoin extends RecursiveTask<Double>{
    
    private final double[] array;
    private final int inici, finale;
        
        /**
         * Declarem el constructor que rep l'array i l'inici i el fi com a int.
         * Que utilitzarem per a crear la resta de fills.
         * @param arr
         * @param ini
         * @param fin 
         */
        public ForkJoin(double[] arr, int ini, int fin) {
            this.array = arr;
            this.inici = ini;
            this.finale = fin;
        }
    
    /**
     * @Override ens indica que sobreescriurem aquest metode.
     * Es divideix l'array en sectors fins que finale-inici sigui mes petit o 
     * igual a 3 per a despres entrar al if i calcular la temperatura mitja.
     * A continuacio es calculara la temperatura minima d'aquestes mitjes.
     * @return 
     */    
    @Override
    protected Double compute() {
        if (finale - inici <= 3) {
            double temperaturaMedia=0;
            for (int i = inici; i <= finale; i++) {
                temperaturaMedia +=array[i]; 
            }
            temperaturaMedia /= 4;

            return temperaturaMedia;
        }else {
            int mitat = inici + (finale - inici) / 2;
            ForkJoin forkJoin1 = new ForkJoin(array, inici, mitat);
            ForkJoin forkJoin2 = new ForkJoin(array, mitat + 1, finale);
            invokeAll(forkJoin1, forkJoin2);
                
            return Math.min(forkJoin1.join(), forkJoin2.join());
        }
    }
    
    /**
     * Declarem el nostre array de tempertures.
     * Declarem el ForkJoin i l'inicalitzem.
     * Mostrem el resultat.
     * @param args 
     */
    public static void main(String[] args) {
        double[] temperatures = {
            13.0, 13.2, 13.3, 13.4, //00:00 h.
            13.5, 13.7, 13.8, 13.9, //01:00 h.
            14.1, 14.2, 14.3, 14.4, //02:00 h.
            14.6, 14.7, 14.8, 14.9, //03:00 h.
            15.0, 15.2, 15.3, 15.4, //04:00 h.
            15.5, 15.7, 15.8, 15.9, //05:00 h.
            16.1, 16.2, 16.3, 16.4, //06:00 h.
            16.6, 16.7, 16.8, 16.9, //07:00 h.
            17.0, 17.2, 17.3, 17.4, //08:00 h.
            17.5, 17.7, 17.8, 17.9, //09:00 h.
            18.1, 18.2, 18.3, 18.4, //10:00 h.
            18.6, 18.7, 18.8, 18.9, //11:00 h.
            18.0, 18.2, 18.3, 18.4, //12:00 h.
            18.5, 18.7, 18.8, 18.9, //13:00 h.
            17.1, 17.2, 17.3, 17.4, //14:00 h.
            17.6, 17.7, 17.8, 17.9, //15:00 h.
        };
        int NumberOfProcessors =Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(NumberOfProcessors);
        ForkJoin tasca = new ForkJoin(temperatures, 0, temperatures.length - 1);
        Double result = pool.invoke(tasca);
        
        System.out.println("Resultat és: " + result);
        
        
    }

    
    

    

}
