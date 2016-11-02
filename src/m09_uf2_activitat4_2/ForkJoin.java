package m09_uf2_activitat4_2;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import static java.util.concurrent.ForkJoinTask.invokeAll;
import java.util.concurrent.RecursiveTask;



    public class ForkJoin extends RecursiveTask<Integer> {

        private final ArrayList<Integer> array;
        private final int inici, finale;

        public ForkJoin(ArrayList arr, int ini, int fin) {
            this.array = arr;
            this.inici = ini;
            this.finale = fin;
        }

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

        

    
    public static void main(String[] args) {
        Random rnd = new Random();
        ArrayList<Integer> sueldosArrayList = new ArrayList<>();

        for (int i = 0; i < 20000; i++) {
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

