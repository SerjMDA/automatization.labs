package n3;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;
import java.util.ArrayList;


@XStreamAlias("Person")
public class Person implements Serializable {

  
	private static final long serialVersionUID = 1L;
	private Initials Initials;
   @XStreamAsAttribute
    private int Age;
    private String Language;
    private contactInfo ContactInfo;
    private ArrayList<String> Phrases;

    Person(Initials initials, int age, String nationality, contactInfo contactInfo, ArrayList<String> phrases) {
        this.Initials = initials;
        this.Age = age;
        this.Language = nationality;
        this.ContactInfo = contactInfo;
        this.Phrases = phrases;
    }

    Person(){}


    public Initials getInitials() {
        return Initials;
    }

    public void setInitials(Initials initials) {
        this.Initials = initials;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        this.Age = age;
    }

    public String getNationality() {
        return Language;
    }

    public void setNationality(String language) {
        this.Language =language;
    }

    public n3.contactInfo getContactInfo() {
        return ContactInfo;
    }

    public void setContactInfo(n3.contactInfo contactInfo) {
        this.ContactInfo = contactInfo;
    }

    public ArrayList<String> getPhrases() {
        return Phrases;
    }

    public void setPhrases(ArrayList<String> phrases) {
        this.Phrases = phrases;
    }

    @Override
    public String toString() {
        return "\nPerson : \n" +
                "Age : " + Age +
                 Initials + "\n" +
                "Nationality : \"" + Language + "\"\n" +
                "ContactInfo : " + ContactInfo + "\n" +
                "Phrases : " + Phrases +"\n";
    }
}




 class Initials implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String FirstName;
    private String MiddleName;
    private String LastName;

   Initials(String firstName,String middleName,String lastName){
        this.FirstName = firstName;
        this.LastName = lastName;
        this.MiddleName = middleName;
    }

    Initials(){}


    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        this.MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

     @Override
     public String toString() {
         return "\n" +
                 "FirstName : \"" + FirstName + '\"' + "\n" +
                 "MiddleName :\"" + MiddleName + '\"' + "\n"+
                 "LastName : \"" + LastName + '\"';
     }
 }

class contactInfo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Email;
    private String PhoneNumber;


     contactInfo(String email,String phoneNumber){

        this.Email = email;
        this.PhoneNumber = phoneNumber;
    }

    contactInfo(){}


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.PhoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return
                "Email :\"" + Email + "\", " +
                "PhoneNumber=\"" + PhoneNumber + "\"";
    }
}
