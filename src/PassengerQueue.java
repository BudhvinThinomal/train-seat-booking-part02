import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class PassengerQueue {
    Passenger data = new Passenger();
    private static int maxLimit = 42;
    private String[] waitingRoom = data.getName();
    private static String[] passenger = new String[maxLimit];
    private static Integer[] trainQueue = new Integer[maxLimit];
    private String addDate;
    private boolean isFull;
    private boolean isEmpty;
    private int first=-1;
    private int last=-1;
    private int maxStayInQueue = 0;
    private int minStayInQueue = 18;
    private double totalTimeInQueue = 0;
    private int avgTimeInQueue=0;

    public void addWaitingRoom() {
        int z = 0;
        try {
            int num = (int) (Math.random() * 6 + 1);     //create random number
            for (int x = 0; x < num; x++) {     //assign values according to random number
                while ((waitingRoom)[z] == null) {
                    z++;
                }
                passenger[z] = waitingRoom[z];
                waitingRoom[z] = null;
                z++;
            }
            System.out.println("\n_________________\nWaiting Room List\n_________________\n");
            for (int y = 0; y < waitingRoom.length; y++) {
                if ((waitingRoom)[y] != null) {
                    System.out.println(waitingRoom[y] + " ||->seat No. : " + (y + 1));
                }
            }
        } catch (Exception a) {
            System.out.println("\n_______________________\nWaiting room is empty!!\n_______________________");
        }
    }

    public void addPassenger(){
        first=last=-1;          //define circular array variables
        for (int i = 0; i < maxLimit; i++) {     //assign values to queue array
            if ((passenger)[i] != null) {
                if(first==-1 && last==-1){
                    first=last=0;
                    trainQueue[last]=i;
                } else{
                    last=(last+1)%maxLimit;
                    trainQueue[last]=i;
                }
            }
        }

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Train Station Train Queue");
        FlowPane flowPane = fPane();                //calling method to get flow pane

        for (int i = first; i <= last; i++){    //create buttons using for loop
            Button btn = new Button("Seat " + (i+1));
            btn.setId(Integer.toString(i));
            btn.setPrefWidth(300);
            btn.setStyle("-fx-background-color:#404040; -fx-text-fill: white;-fx-font-weight:bolder;");
            btn.setAlignment(Pos.BASELINE_LEFT);
            btn.setText(passenger[trainQueue[i]]+" ||Seat No "+(trainQueue[i]+1));
            btn.setDisable(false);
            flowPane.getChildren().add(btn);
        }

        isFull=(((last+1)%maxLimit)==first);
        if (isFull){            //isFull=true
            Stage conBox = new Stage();                                 //confirmation box
            conBox.setTitle("Train Queue");
            Button confirm = new Button("Ok");
            VBox vbox = new VBox(new Text("         ERROR!!\nTrain Queue is full!!"),confirm);
            vbox.setStyle("-fx-background-color:#d1d1e0;-fx-font-size:18;-fx-font-weight:bolder;");
            vbox.setSpacing(20);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(20));

            confirm.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    conBox.close();
                    primaryStage.close();
                }
            });
            conBox.setScene(new Scene(vbox, 200, 150));
            conBox.showAndWait();
        }
        isEmpty=((first==-1)&&(last==-1));
        if (isEmpty) {          //isEmpty=true
            System.out.println("______________________\nTrain Queue is empty!!\n______________________\n");
        } else {
            Scene scene = new Scene(flowPane, 940, 550);
            primaryStage.setScene(scene);
            primaryStage.showAndWait();
        }
    }

    public void display(){
        Stage primaryStage =new Stage();
        primaryStage.setTitle("Train Station Train Queue");
        FlowPane flowPane = fPane();            //calling method to get flow pane

        for (int i = 0; i < maxLimit; i++){     //create buttons using for loop
            Button btn = new Button("Slot " + (i+1));
            btn.setId(Integer.toString(i));
            btn.setPrefWidth(300);
            btn.setStyle("-fx-background-color:#C0C0C0; -fx-text-fill: black;-fx-font-weight:bolder;");
            btn.setDisable(false);
            flowPane.getChildren().add(btn);

            if ((passenger)[i]!=null) {     //change button text according to not null values
                btn.setAlignment(Pos.BASELINE_LEFT);
                btn.setStyle("-fx-background-color:#404040; -fx-text-fill: white;-fx-font-weight:bolder;");
                btn.setText(passenger[i]+" ||Seat "+(i+1));
            }
        }
        Scene scene = new Scene(flowPane, 940, 550);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }

    public void deleteQueue(){
        isEmpty=((first==-1)&&(last==-1));
        if (isEmpty){           //isEmpty=true
            System.out.println("\n______________________\nTrain Queue is empty!!\n______________________\n");
        }else {
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter your first name : ");
            String firstName = scan.next().toUpperCase();         //turn the string value to uppercase
            System.out.println("Enter your surname : ");
            String surName = scan.next().toUpperCase();
            String passengerName = firstName + " " + surName;        //turn the string value to uppercase
            for (int i = first; i <= last; i++){
                if (passenger[trainQueue[i]].equals(passengerName)){    //condition equal to passenger name
                    System.out.println(passengerName + " (Seat No." + (trainQueue[i] + 1) + ")removed from Train Queue");
                    passenger[trainQueue[i]] = null;
                    first=last=-1;
                    for (int x = 0; x < maxLimit; x++) {
                        if ((passenger)[x] != null) {
                            if (first == -1 && last == -1) {
                                first = last = 0;
                                trainQueue[last] = x;
                            } else {
                                last = (last + 1) % maxLimit;
                                trainQueue[last] = x;
                            }
                        }
                    }
                    break;
                } if ((i == last) && !(passenger[trainQueue[i]].equals(passengerName))) {   //condition not equal to passenger name
                    System.out.println(passengerName + " not in the Train Queue");
                }
            }
        }
    }

    public void setDate(String date) {
        addDate=date;
    }       //get selected date using getter

    public void saveQueue(){
        try {
            String finalDate = addDate + ".txt";
            FileWriter fw = new FileWriter(finalDate);      //define file
            for (int i = 0; i < passenger.length; i++) {        //write passenger array elements to file
                fw.write(passenger[i] + "\n");
            }
            for (int x = 0; x < waitingRoom.length; x++) {      //write waiting room array elements to file
                fw.write(waitingRoom[x] + "\n");
            }
            fw.close();
            System.out.println("Data stored successfully To:-" + finalDate + " file!!");
        } catch (Exception e) {
            System.out.println("Operation is unsuccessful...Error - " + e);
        }
    }

    private String[][] temp=new String[42][2];
    public void loadQueue(){
        try {
            String finalDate=addDate+".txt";
            Scanner scanner = new Scanner(new File(finalDate));    //find the file
            for (int i = 0; i < maxLimit; i++) {        //add string values to passenger array
                String names = scanner.nextLine();
                if(names.equals("null")){
                    names=null;
                }
                passenger[i]=names;
            }
            first=last=-1;
            for (int x = 0; x < maxLimit; x++) {    //assign values to queue array
                if ((passenger)[x] != null) {
                    if (first == -1 && last == -1) {
                        first = last = 0;
                        trainQueue[last] = x;
                    } else {
                        last = (last + 1) % maxLimit;
                        trainQueue[last] = x;
                    }
                }
            }

            ArrayList<String>tempFullName=new ArrayList<String>();
            for(int x=0;x<maxLimit;x++){        //add string values to array list
                String names = scanner.nextLine();
                if(names.equals("null")){
                    names="null null";
                }
                tempFullName.add(names);
            }

            int oddEven = 0;
            for(int z=0;z<maxLimit;z++){        //get the value
                String str = tempFullName.get(z);//assign the value to string
                String[] space = str.split(" ");//split the value
                for (String b : space) {    //adding values to array
                    if (oddEven % 2 == 0) {
                        temp[z][0] = b;
                    } else {
                        temp[z][1] = b;
                    }
                    oddEven++;
                }
            }
            for (int x = 0; x < maxLimit; x++) {
                data.setName(temp[x][0], temp[x][1]);       //passing the array using setter
            }
            System.out.println("Data load successfully from:-"+finalDate+" file!!");
        }catch(Exception e){
            System.out.println("Operation is unsuccessful... File not founded"+e);
        }
    }

    private ArrayList<Integer> delay = new ArrayList<Integer>();
    private ArrayList<String> details = new ArrayList<String>();

    public void runTheSimulationAndGenerateReport() {
        isEmpty=((first==-1)&&(last==-1));
        if (isEmpty) {      //isEmpty=true
            System.out.println("\n______________________\nTrain Queue is empty!!\n______________________\n");
        } else {
            for (int i = first; i <= last; i++){
                    int random = (int) (Math.random() * 16 + 3);
                    details.add("Name: " + passenger[trainQueue[i]] + " seat no.: " + (trainQueue[i] + 1));//assign queue to list
                    delay.add(random);      //assign random numbers to list
            }

            for (int x = 0; x < delay.size(); x++) {//passing the array list using setter
                data.setSecondsInQueue(delay.get(x));
            }
            for (Integer delayTime : delay) {    //check for maximum number
                if (maxStayInQueue < delayTime) {
                    maxStayInQueue = delayTime;
                }
                if (minStayInQueue >= delayTime) {   //check for minimum number
                    minStayInQueue = delayTime;
                }
            }
            totalTimeInQueue = data.getSecondsInQueue(); //get the total using getter
            int maxLength = delay.size();
            avgTimeInQueue= (int) Math.round(totalTimeInQueue / maxLength);

            theReport(maxStayInQueue, minStayInQueue, avgTimeInQueue,details,delay, maxLength);
            Stage primaryStage =new Stage();//create GUI part for report
            primaryStage.setTitle("Train Queue Report");
            AnchorPane anchorPane = new AnchorPane();

            Label title=new Label("Denuwara Manike Train Queue Report");
            title.setStyle("-fx-background-color:#404040; -fx-text-fill: white;-fx-font-weight: bolder;-fx-font-size: 30;");
            title.setAlignment(Pos.CENTER);
            AnchorPane.setLeftAnchor(title, 0.0);
            AnchorPane.setRightAnchor(title, 0.0);
            anchorPane.getChildren().add(title);

            Label subTitle1=new Label("Summary of the Train Queue");
            subTitle1.setStyle("-fx-background-color:#404040; -fx-text-fill: white;-fx-font-weight: bolder;-fx-font-size: 20;");
            AnchorPane.setTopAnchor(subTitle1,75.0);
            anchorPane.getChildren().add(subTitle1);

            Label item1=new Label("01. Maximum length of the queue - " + maxLength);
            AnchorPane.setTopAnchor(item1,125.0);
            anchorPane.getChildren().add(item1);

            Label item2=new Label("02. Maximum time spent in queue - " + maxStayInQueue);
            AnchorPane.setTopAnchor(item2,175.0);
            anchorPane.getChildren().add(item2);

            Label item3=new Label("03. Minimum time spent in queue - " + minStayInQueue);
            AnchorPane.setTopAnchor(item3,225.0);
            anchorPane.getChildren().add(item3);

            Label item4=new Label("04. Average time spent in queue - " + avgTimeInQueue);
            AnchorPane.setTopAnchor(item4,275.0);
            anchorPane.getChildren().add(item4);

            Label subTitle2=new Label("Details of the passengers");
            subTitle2.setStyle("-fx-background-color:#404040; -fx-text-fill: white;-fx-font-weight: bolder;-fx-font-size: 20;");
            AnchorPane.setTopAnchor(subTitle2,325.0);
            anchorPane.getChildren().add(subTitle2);

            double firstCol=350.0;
            double secondCol=350.0;
            for (int i = 0; i < details.size(); i++){
                Label item= new Label((i+1)+"." + details.get(i)+" || Spent time in queue: "+ delay.get(i)+"\n");
                if(i<21){
                    firstCol+=25.0;
                    AnchorPane.setTopAnchor(item,firstCol);
                    AnchorPane.setLeftAnchor(item, 10.0);
                }else{
                    secondCol+=25.0;
                    AnchorPane.setTopAnchor(item,secondCol);
                    AnchorPane.setLeftAnchor(item, 760.0);
                }
                anchorPane.getChildren().add(item);
            }
            anchorPane.setStyle("-fx-text-fill: black;-fx-font-weight: bolder;-fx-font-size: 18;-fx-background-color:#d1d1e0");
            Scene scene = new Scene(anchorPane, 1500, 900);
            primaryStage.setScene(scene);
            primaryStage.showAndWait();
            remove();
        }

    }
    private void remove(){
        for (int i = 0; i < maxLimit; i++) {        //remove item from passenge array
            if ((passenger)[i] != null)
                passenger[i]=null;
        }
        try {       //save data
            String finalDate = addDate + ".txt";
            FileWriter fw = new FileWriter(finalDate);
            for (int i = 0; i < passenger.length; i++) {
                fw.write(passenger[i] + "\n");
            }
            for (int x = 0; x < waitingRoom.length; x++) {
                fw.write(waitingRoom[x] + "\n");
            }
            fw.close();
            System.out.println("Data stored successfully To:-" + finalDate + " file!!");
        } catch (Exception e) {
            System.out.println("Operation is unsuccessful...Error - " + e);
        }
        first=last=-1;
        delay.clear();
        details.clear();
        maxStayInQueue = 0;
        minStayInQueue = 18;
        totalTimeInQueue = 0;
        avgTimeInQueue =0;
        data.setResetSecondsinQueue();      //reset seconds in queue value using setter
    }
    private void theReport(int maxStayInQueue, int minStayInQueue, int avgTimeInQueue, ArrayList<String> details, ArrayList<Integer> delay, int maxLength) {
        try {
            FileWriter fw = new FileWriter("Report.txt");       //create new file
            fw.write("Summary of the Train Queue\n\n");
            fw.write("01. Maximum length of the queue - " + maxLength + "\n");
            fw.write("02. Maximum time spent in queue - " + maxStayInQueue + "\n");
            fw.write("03. Minimum time spent in queue - " + minStayInQueue + "\n");
            fw.write("04. Average time spent in queue - " + avgTimeInQueue + "\n\n");
            fw.write("Details of the passengers\n\n");
            for (int i=0;i<details.size();i++){
                fw.write((i+1)+"." + details.get(i)+" || Spent time in queue: "+ delay.get(i)+"\n");
            }
            fw.close();
            System.out.println("Data stored successfully To:- Report.txt");
        } catch (Exception e) {
            System.out.println("Operation is unsuccessful..." + e);
        }
    }
    private static FlowPane fPane() {       //defined method to flow pane
        FlowPane flowPane=new FlowPane();
        flowPane.setHgap(15);
        flowPane.setVgap(7.5);
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setStyle("-fx-background-color:#d1d1e0");
        return flowPane;
    }
}
