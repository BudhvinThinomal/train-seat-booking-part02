public class Passenger {
    private String firstName;
    private String surname;
    private  int secondsInQueue;
    private String fullName;
    private int x = 0;
    private static int maxLength = 42;
    private static String[] names = new String[maxLength];

    public String[] getName() {     //define getter
        return names;
    }

    public void setName(String name, String surname) {     //define setter
        firstName = name;       //get values using constructor
        this.surname = surname; //get values using constructor
        this.fullName = firstName + " " + this.surname;
        if (this.fullName.equals("null null")) {    //assign the values
            names[x] = null;
        }else {
            names[x] = this.fullName;
        }
        x++;

    }
    public void setResetSecondsinQueue(){    //define setter
        this.secondsInQueue=0;
    }

    public int getSecondsInQueue() {     //define getter
        return secondsInQueue;
    }

    public void setSecondsInQueue(int secondsInQueue) {    //define setter
        this.secondsInQueue=this.secondsInQueue+secondsInQueue;
    }
}
