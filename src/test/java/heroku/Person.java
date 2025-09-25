package heroku;

public class Person {
    //tao object
    String firstname,lastname,due;

    public Person(String firstname, String lastname, String due) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.due = due;
    }
    public String getFullName(){
        return String.format("%s %s",this.firstname,this.lastname);
    }

    public double getDue() {
        return Double.parseDouble(this.due.replace("$",""));
    }
}
