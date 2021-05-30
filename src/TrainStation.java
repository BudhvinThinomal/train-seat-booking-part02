import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.stage.Stage;
import org.bson.Document;
import java.util.*;

public class TrainStation extends Application {
    private static final int SEATING_CAPACITY = 42;      //create static variable for seat capacity

    @Override
    public void start(Stage primaryStage) throws Exception {
        HashMap<String, HashMap> tripOne = new HashMap<String, HashMap>();//create Hashmap to store trip one data
        Passenger passengerObj = new Passenger();//create object for passenger class
        PassengerQueue passengerQueueObj = new PassengerQueue();//create object for passengerQueue class

        try {
            MongoClient mongoClient = MongoClients.create();                     //connect with the mongodb
            MongoDatabase mongoDatabase = mongoClient.getDatabase("seatBooking");
            MongoCollection<Document> collection = mongoDatabase.getCollection("newTripOne");

            HashMap<Integer, String> passengerMap = new HashMap<Integer, String>();//create inner Hashmap to store trip one data
            String enteredDate = null;
            String previousDate;
            List<Document> names = (List<Document>) collection.find().into(new ArrayList<Document>()); //gathered data from database
            for (Document a : names) {
                List<Document> date = (List<Document>) a.get("DataBaseDate");       //get data to documents
                List<Document> details = (List<Document>) a.get("DataBaseString");
                List<Document> seat = (List<Document>) a.get("DataBaseInteger");

                for (int i = 0; i < date.size(); i++) {     //put list item one by one to the variable.
                    for (int x = 0; x < date.size(); x++) {
                        enteredDate = String.valueOf(date.get(i));
                        String eneteredDetails = String.valueOf(details.get(i));
                        String enteredInteger = String.valueOf(seat.get(i));
                        Integer finalInteger = Integer.parseInt(enteredInteger);
                        previousDate = String.valueOf(date.get(x));
                        if (tripOne.get(enteredDate) != null) { //check the key was  create before
                            passengerMap = tripOne.get(enteredDate); //assign the key to inner hash map
                            passengerMap.put(finalInteger, eneteredDetails);    //assign the values
                            //this condition used to compare the entered date with all other keys
                        } else if (tripOne.get(enteredDate) != tripOne.get(previousDate)) { //check the key was create in previously
                            tripOne.put(enteredDate, new HashMap() {{//put key to inner hash map
                                put(finalInteger, eneteredDetails);      //assign the values
                            }});
                        }
                    }
                }
                final HashMap loadData = passengerMap;
                tripOne.put(enteredDate, loadData);//put entered date and inner hash map to the main hash map
            }
            System.out.println("Data load successfully!!");
        } catch (Exception e) {
            System.out.println("Operation is unsuccessful...Error - " + e);
        }

        Scanner scan = new Scanner(System.in);              //Train station menu options
        while (true) {
            System.out.println("______________________________Colombo Train Station!!______________________________\n");
            System.out.println("Please enter a date: (ex: 2020-01-01) OR Enter \"Q\" to quit ");
            String selectedDate = scan.next().toUpperCase();
            if (selectedDate.equals("Q")) {
                System.out.println("Thank you!!");
                System.exit(0);
            }
            HashMap passengerMap;
            if (tripOne.get(selectedDate) != null) {  //check the key is in the hash map
                passengerMap = tripOne.get(selectedDate);
            } else {
                System.out.println("There are no reservations for entered date!!");
                continue;
            }
            final HashMap select = passengerMap;//assign it to final hash map
            passengerQueueObj.setDate(selectedDate);       //pass the selected date using setter
            ArrayList<Integer> seat = new ArrayList<Integer>();    //define new array list

                for (Object a : select.keySet()) {           //add keys to array list
                    seat.add((Integer) a);
                }//sorting according to seat number
            for (int x = 0; x < seat.size() - 1; x++){                                 // bubble sort outer loop
                for (int j = 0; j < seat.size() - x - 1; j++) {                     //bubble sort inner loop
                    if (seat.get(j).compareTo(seat.get(j + 1)) > 0) {
                        int temporary = seat.get(j);
                        seat.set(j, seat.get(j + 1));
                        seat.set(j + 1, temporary);
                    }
                }
            }
            String[][] selectedPassengers = new String[42][2];  //define new array
            for (Integer seatNumber : seat) {
                for (int i = 1; i <= SEATING_CAPACITY; i++) {
                    if (select.containsKey(seatNumber)) {      //get the value
                        int oddEven = 0;
                        String value = (String) select.get(seatNumber);//assign the value to string
                        String[] space = value.split(" ");      //split the value
                        for (String b : space) {
                            if (oddEven % 2 == 0) {         //adding values to array
                                selectedPassengers[(seatNumber - 1)][0] = b;
                            } else {
                                selectedPassengers[(seatNumber - 1)][1] = b;
                            }
                            oddEven++;
                        }
                        break;
                    }
                }
            }
            for (int x = 0; x < SEATING_CAPACITY; x++) {    //passing the array using setter
                passengerObj.setName(selectedPassengers[x][0], selectedPassengers[x][1]);
            }
            while (true) {
                System.out.println("___________________Denuwara Menike Train boarding gate program!!___________________\n");
                System.out.println("____________________________Colombo to Badulla journey!!___________________________\n");
                System.out.println("Note: If you load data please remind to store data before quit the program!!\n      Otherwise data'll be lost!!\n");
                System.out.println("Enter \"A\" to add a passenger to the train queue");
                System.out.println("Enter \"V\" to view the train queue");
                System.out.println("Enter \"D\" to delete a passenger from the train queue");
                System.out.println("Enter \"S\" to store train queue Data");
                System.out.println("Enter \"L\" to load train queue Data");
                System.out.println("Enter \"R\" to produce report");
                System.out.println("Enter \"Q\" to quit ");
                System.out.print("Please enter the given command : ");
                String option = scan.next();
                switch (option) {                        //Switch case for options
                    case "A":
                    case "a":
                        passengerQueueObj.addWaitingRoom(); //calling to the method
                        passengerQueueObj.addPassenger();
                        break;
                    case "V":
                    case "v":
                        passengerQueueObj.display();//calling to the method
                        break;
                    case "D":
                    case "d":
                        passengerQueueObj.deleteQueue();//calling to the method
                        break;
                    case "S":
                    case "s":
                        passengerQueueObj.saveQueue();//calling to the method
                        break;
                    case "L":
                    case "l":
                        passengerQueueObj.loadQueue();//calling to the method
                        break;
                    case "R":
                    case "r":
                        passengerQueueObj.runTheSimulationAndGenerateReport();//calling to the method
                        break;
                    case "Q":
                    case "q":
                        System.out.println("Thank you!!");
                        System.exit(0);
                    default:
                        System.out.println("Please enter the given command!!");
                }
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

}