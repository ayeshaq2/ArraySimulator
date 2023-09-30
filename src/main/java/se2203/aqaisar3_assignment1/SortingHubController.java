package se2203.aqaisar3_assignment1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;


public class SortingHubController implements Initializable {


    //adding all the fields we need to use for our GUI application
    @FXML
    private Label algorithmText;
    //creating a decimal class instance to present the slider values as whole numbers
    private static final DecimalFormat decfor = new DecimalFormat("0");
    private int[] firstArray;
    @FXML
    private ComboBox<String> algorithmChoice = new ComboBox<>();
    @FXML
    private Slider sliderArray;
    @FXML
    private Label arraySizeText;
    @FXML
    private Label arraySizeNum;
    @FXML
    private Button sortBtn;
    @FXML
    private Button resetBtn;
    @FXML
    private Pane displayPane;
    private Rectangle rectangle;

    private SortingStrategy sortAlgorithm;

    //setting up an initialize method to set inital values for all the GUI fields
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //adding options to our combo box
        algorithmChoice.getItems().addAll("Merge Sort", "Insertion Sort");
        algorithmChoice.getSelectionModel().selectFirst();

        //intialzing the created array with a size 64
        firstArray = new int[64];
        int leSize = firstArray.length;

        //setting the slider value to 64 which is the length of our array
        sliderArray.setValue(64);

        //calling methods to populate, convert to an ArrayList and shuffle the created array
        populateArray(firstArray);
        ArrayList demolist = convertToArraylist(firstArray);
        ShuffleReset(firstArray);

        //setting up a for loop to display the integers in my array as rectangles on the scene, that are red in color
        //reducing width by 2 units in order to create a white border around the rectangles
        for (int i = 0; i < leSize; i++) {
                //rectangle = new Rectangle(((i*displayPane.getPrefWidth())/64),(int)((displayPane.getPrefHeight()+((((demoArray[i])/leSize)*100))))-1,(displayPane.getPrefWidth()/64)-1 ,((demoArray[i])));
                // rectangle = new Rectangle(((i*displayPane.getPrefWidth())/64),(int)(displayPane.getPrefHeight()*64),(displayPane.getPrefWidth()/64)-1 ,((firstArray[i])));
            rectangle = new Rectangle(((i * displayPane.getPrefWidth()) / 64), (int) ((displayPane.getPrefHeight() - (firstArray[i]*(displayPane.getPrefHeight()/64)))), (displayPane.getPrefWidth()/64)-2, ((firstArray[i]*(displayPane.getPrefHeight()/64))));

            //rectangle = new Rectangle(10,10,10,10);
            displayPane.getChildren().add(rectangle);
            rectangle.setFill(Color.RED);

        }


    }

    //creating a method that cna take an array and return it as an ArrayList
    public ArrayList<Integer> convertToArraylist(int[] array){
        ArrayList<Integer> theArrayList = new ArrayList<Integer>();
        for(int i=0; i<array.length; i++){
            theArrayList.add((array[i]));
        }
        return theArrayList;
    }

    //creating a method to populate an array
    public void populateArray(int[] array){
        //using a for loop for population so as to avoid duplicated values
        for(int i=0; i< array.length; i++){
            array[i] = i+1;
        }
    }


    //event listener 1
    //creating an event listener that acts on "sort button". this method will check the option selected in the combo box and then sort based on the algorithm choice
    @FXML
    public void setSortStrategy() {

        String option = algorithmChoice.getValue();

        if ((option.equals("Merge Sort"))) {

            //creating an instance to be able to send the array to the merge sort class and have it sort under merge sort algorithm
            sortAlgorithm = new MergeSort(firstArray,this);
            //creating a thread here that will be executed in the sorting class
            new Thread(sortAlgorithm).start();

        } else if (option.equals("Insertion Sort")) {

            //creating reference object to send our command to and then control passes over through the Thread
            sortAlgorithm = new InsertionSort(firstArray,this);
            new Thread(sortAlgorithm).start();

        }
    }

    //creating a method to update the scene or graph of rectangles as they are sorted
    public void updateGraph(int[] data){
        displayPane.getChildren().clear();
        int thenewSize = (int)sliderArray.getValue();

        for (int i = 0; i < data.length; i++) {
//               Rectangle updaterectangle = new Rectangle(((i * displayPane.getPrefWidth()) / (sliderArray.getValue())), (displayPane.getPrefHeight() - (firstArray[i]*(displayPane.getPrefHeight()/sliderArray.getValue()))), (displayPane.getPrefWidth()) / (sliderArray.getValue())- 1, (firstArray[i]*(displayPane.getPrefHeight()/sliderArray.getValue())));
            rectangle = new Rectangle(((i * displayPane.getPrefWidth()) / thenewSize), (displayPane.getPrefHeight() - (firstArray[i]*(displayPane.getPrefHeight()/thenewSize))), (displayPane.getPrefWidth()) / thenewSize- 2, (firstArray[i]*(displayPane.getPrefHeight()/thenewSize)));

            displayPane.getChildren().add(rectangle);
            rectangle.setFill(Color.RED);

        }
    }

    public void clearGraph(){
        displayPane.getChildren().clear();
    }

    //event listener 2:
    //creating an event listener that responds to the changes in the slider
    public void setTheSize(){
        displayPane.getChildren().clear();

        //sets the new value from the slider in the text as a whole number
        arraySizeNum.setText((String.valueOf(decfor.format(sliderArray.getValue()))));

        int theSize = (int) (sliderArray.getValue());

        //setting the array with length as the slider value, populating and then shuffling it
        firstArray = new int[theSize];
        //System.out.println(theSize);
        populateArray(firstArray);
        ShuffleReset(firstArray);

        ArrayList<Integer> first = convertToArraylist(firstArray);

        //displaying the rectangles of the shuffled array as the size is chosen
        for(int i=0; i<theSize; i++){
            Rectangle newrectangle = new Rectangle(((i*displayPane.getPrefWidth())/(theSize)),(displayPane.getPrefHeight()-(firstArray[i]*(displayPane.getPrefHeight()/theSize)))-2,(displayPane.getPrefWidth())/(theSize)-2,(firstArray[i]*(displayPane.getPrefHeight()/theSize)));
           // rectangle = new Rectangle(((i*displayPane.getPrefWidth())/64),(i*displayPane.getPrefHeight()-(firstArray[i])-1),(i*displayPane.getPrefWidth()/64) -2 ,firstArray[i]);

            displayPane.getChildren().add(newrectangle);
            newrectangle.setFill(Color.RED);

        }


    }

    @FXML
    //creating a method that takes in an array and shuffles it using Random
    void ShuffleReset(int[] array){
        //code for shuffling
            Random random = new Random();
            for(int i= array.length -1; i>0; i--){
                int index = random.nextInt(i+1);
                int a = array[index];
                array[index] = array[i];
                array[i] = a;
            }

    }

    //event listener 3
    //creating an event listener that responds to the click of the reset button
    @FXML
    void ShuffleBtn(){
        //using Random object to shuffle the array
        displayPane.getChildren().clear();
        firstArray = new int[64];
        int leSize = firstArray.length;

        sliderArray.setValue(64);
        arraySizeNum.setText((String.valueOf(decfor.format(sliderArray.getValue()))));


//        Random random = new Random();
//        for(int i= firstArray.length-1; i>0; i--){
//            int index = random.nextInt(i+1);
//            int a = firstArray[index];
//            firstArray[index] = firstArray[i];
//            firstArray[i] = a;
//        }

        populateArray(firstArray);
        ShuffleReset(firstArray);
        algorithmChoice.getSelectionModel().selectFirst();


        //displaying the shuffled integers using a for loop
        for(int i=0; i<firstArray.length; i++){
            Rectangle btnrectangle = new Rectangle(((i*(displayPane.getPrefWidth()/sliderArray.getValue()))),((displayPane.getPrefHeight()-(firstArray[i]*(displayPane.getPrefHeight()/sliderArray.getValue())))-2),(displayPane.getPrefWidth())/(sliderArray.getValue())-2,(firstArray[i]*(displayPane.getPrefHeight()/sliderArray.getValue())));
            //Rectangle btnrectangle = new Rectangle(((i*displayPane.getPrefWidth())/(sliderArray.getValue()))-2,(displayPane.getPrefHeight()-(firstArray[i]))-2,(displayPane.getPrefWidth())/(sliderArray.getValue())-1,(firstArray[i]));

            displayPane.getChildren().add(btnrectangle);
            btnrectangle.setFill(Color.RED);
        }



    }

}
