package se2203.aqaisar3_assignment1;

import javafx.application.Platform;

import java.util.Arrays;

public class MergeSort implements SortingStrategy {

    private int[] list;
    private static SortingHubController controller;

    //constructor that takes the array to sort and the controller-class instance
    public MergeSort(int[] list, SortingHubController controller){
        this.list= list;
        this.controller= controller;
    }

    //the run method which will be executed once the Thread is called
    public void run(){
        new Thread (()->{
            sort(list);
        }).start();;
    }

    public void sort(int[]list){
        //sort method with only one argument, calls the separately defined methods with the required arguments to merge sort
        mergesort(list, 0, list.length-1);

    }

    private static void mergesort(int[] list, int left, int right){
        //in the scenario that out left pointer is less than our right pointer
        if(left<right){
            int mid = (left+right)/2;

            //calling the defined mergesort method to split the first half of the array, and keep doing so till you have one
            mergesort(list, left,mid);
            //splits second half
            mergesort(list, mid+1, right);
            //calling merge to actually sort these halves
            merge(list, left, mid, right);


        }

    }

    private static void merge(int[] list, int left, int mid, int right) {
        //merge sort divides the array into halves until further division is not possible, and then compares the most recent division pairs,
        //after comparison it merges them back into pairs, and then compares 2 pairs by initially comparing each pair's first element and second element to form an ordered group
        //this is repeated until the array is entirely merged together, and is in order


        //creating a temporary array with a length of the difference between out pointer indices

        int[] temp = new int [right - left+1];
        //assigning values
        int i= left, j=mid+1, k=0;

        //setting up a few cases and how to swap elements for each

        //comparing the left and current indices
        while(i<=mid&&j<= right){

            //if left pointer is less than middle value, it will be held in a temporary position to be swapped
            if(list[i] <= list[j]){
                temp[k++] = list[i++];
                Platform.runLater(()-> controller.updateGraph(list));
                try{
                    //calling thread sleep to delay or pause the execution of the method above, updateGraph by 40 ms,in which it would
                    //pass the control over to the run method to be executed.
                    //this allows the visualization to occur, by allowing the array to sort, and then updating the scene with the changes in the array
                    Thread.sleep(10);
                }catch(InterruptedException e){
                    throw new RuntimeException(e);
                }


            }else{
                //otherwise the mid-point is stored
                temp[k++] = list[j++];
                Platform.runLater(()-> controller.updateGraph(list));
                try{
                    //calling thread sleep to delay or pause the execution of the method above, updateGraph by 40 ms,in which it would
                    //pass the control over to the run method to be executed.
                    //this allows the visualization to occur, by allowing the array to sort, and then updating the scene with the changes in the array
                    Thread.sleep(10);
                }catch(InterruptedException e){
                    throw new RuntimeException(e);
                }
            }
        }
        //setting a while loop to test for the left pointer being less than the midpoint
        while(i<=mid){
            //if so, we store that value in a temporary positon
            temp[k++] = list[i++];
            Platform.runLater(()-> controller.updateGraph(list));
            try{
                //calling thread sleep to delay or pause the execution of the method above, updateGraph by 40 ms,in which it would
                //pass the control over to the run method to be executed.
                //this allows the visualization to occur, by allowing the array to sort, and then updating the scene with the changes in the array
                Thread.sleep(10);
            }catch(InterruptedException e){
                throw new RuntimeException(e);
            }
        }

        //while loop to test the mid point being less than the right pointer
        while(j<= right){
            //if so, that point is stored in a temp posiiton
            temp[k++] = list[j++];
            Platform.runLater(()-> controller.updateGraph(list));
            try{
                //calling thread sleep to delay or pause the execution of the method above, updateGraph by 40 ms,in which it would
                //pass the control over to the run method to be executed.
                //this allows the visualization to occur, by allowing the array to sort, and then updating the scene with the changes in the array
                Thread.sleep(10);
            }catch(InterruptedException e){
                throw new RuntimeException(e);
            }
        }

        //using a for loop, the values are arranged in order starting with the temp values
        for(i=left,k=0; i<=right; i++,k++){
            list[i] = temp[k];
            Platform.runLater(()-> controller.updateGraph(list));
            try{
                //calling thread sleep to delay or pause the execution of the method above, updateGraph by 40 ms,in which it would
                //pass the control over to the run method to be executed.
                //this allows the visualization to occur, by allowing the array to sort, and then updating the scene with the changes in the array
                Thread.sleep(10);
            }catch(InterruptedException e){
                throw new RuntimeException(e);
            }
        }




        }
    }

