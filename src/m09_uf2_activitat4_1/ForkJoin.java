package m09_uf2_activitat4_1;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import static java.util.concurrent.ForkJoinTask.invokeAll;
import java.util.concurrent.RecursiveTask;


    /**
     * Aquesta clase ForkJoin extendeix de RecursiveTask<Integer> per tal
     * de poder fer recursivitat. Declarem l'array, un inici i un final d'aquest
     * per a poder separar l'array en arrays mes petits i el tamany d'aquest.
     * @author ALUMNEDAM
     */
    public class ForkJoin extends RecursiveTask<Integer> {

        private final ArrayList<Integer> array;
        private final int inici, finale;
        private final static int tamanyoArraySueldos=20000;

        /**
         * Declarem el constructor que rep l'array i l'inici i el fi com a int.
         * Que utilitzarem per a crear la resta de fills.
         * @param arr
         * @param ini
         * @param fin 
         */
        public ForkJoin(ArrayList arr, int ini, int fin) {
            this.array = arr;
            this.inici = ini;
            this.finale = fin;
        }

        /**
         * @Override ens indica que sobreescriurem aquest metode.
         * En aquest metode pasem l'array i creem fills fins a que finale-inici
         * sigui mes 1 o mes petit que aquest. Per a despres entrar en el if 
         * i fer la operacio requerida, en aquest cas calcular el sou maxim.
         * Per a despres aconseguir el sou maxim de l'array senser.
         * @return 
         */
        @Override
        protected Integer compute() {
            if (finale - inici <= 1) {

                int sueldoMaximo = Math.max(array.get(inici), array.get(finale));

                return sueldoMaximo;
            }else {
                int mitat = inici + (finale - inici) / 2;
                ForkJoin forkJoin1 = new ForkJoin(array, inici, mitat);
                ForkJoin forkJoin2 = new ForkJoin(array, mitat + 1, finale);
                invokeAll(forkJoin1, forkJoin2);
                return Math.max(forkJoin1.join(), forkJoin2.join());
            }
        }

        

    /**
     * Inicialitzem declarem un random i el nostre array de sous. Omplim l'array
     * amb un for i despres cridem a protected per a realitzar la tasca recursiva.
     * @param args 
     */
    public static void main(String[] args) {
        Random rnd = new Random();
        ArrayList<Integer> sueldosArrayList = new ArrayList<>();

        for (int i = 0; i < tamanyoArraySueldos; i++) {
            int randomSueldo = rnd.nextInt(50001);
            sueldosArrayList.add(randomSueldo);
        }
        
        
        int NumberOfProcessors =Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(NumberOfProcessors);
        ForkJoin tasca = new ForkJoin(sueldosArrayList, 0, sueldosArrayList.size() - 1);
        Integer result = pool.invoke(tasca);
        
        System.out.println("Resultat és: " + result);
        }
}
