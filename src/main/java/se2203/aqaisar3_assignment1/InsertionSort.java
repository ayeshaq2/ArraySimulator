package se2203.aqaisar3_assignment1;


import javafx.application.Platform;

public class InsertionSort implements SortingStrategy {
//
      private int[] list;
      private SortingHubController controller;


      //creating a constructor with arguments
    public InsertionSort(int[] list, SortingHubController controller){
        this.controller= controller;
        this.list=list;
    }


    //the run method which tells the thread how to execute
    @Override
    public void run() {
        new Thread(()->{
            sort(list);
        }).start();
    }



    public void sort(int[] list){
        //insertion sort selects the first element in the array (a) and compares it to the next element, in the scenario that the second element is smaller
        //than our first selected one, it will create a new space, and remove the second element from the array into the new space, move the first element into its
        //original place in the array and then bring the second (smaller) element to the first element's old spot. This continues for the rest of the array, where our element
        //a is compared with the next element, but at the same time on each iteration, each element that is removed from the array is compared with each previous element and put back in the
        //right spot, orderly


        //traversing through the elements in the array, choosing a key which will be compared
        //with the adjacent elements
        for(int i=1; i< list.length ; i++){

            int key = list[i];

            //comparing the elements of the array to the key
            for( int j =i-1; (j>=0 && list[j]>key); j--){

                //if our key is smaller than the element, then that element is swapped accordingly
                //the adjacent element is brought to the previous spot
                list[j+1] = list[j];
                //and our current element becomes the key, upon which the next iteration will be compared
                list[j] =key;

                    //using runlater to allow running the updateGraph method within the thread, as the main thread is delayed
                    Platform.runLater(()-> controller.updateGraph(list));
                    try{
                        //calling thread sleep to delay or pause the execution of the method above, updateGraph by 40 ms,in which it would
                        //pass the control over to the run method to be executed.
                        //this allows the visualization to occur, by allowing the array to sort, and then updating the scene with the changes in the array
                        Thread.sleep(20);
                    }catch(InterruptedException e){
                        throw new RuntimeException(e);
                    } }
                }
        }
    }


