package com.example.firebaseexample;

public class Information {

    private String Name;
    private String Email;

    // It is specified in the document of DataSnapshot
    // That the class (in which we are getting the snapshot values) should have
    // a no argument constructor
    // See this link: https://firebase.google.com/docs/reference/android/com/google/firebase/database/DataSnapshot#public-t-getvalue-classt-valuetype
    private Information() {}

    public Information(String Name, String Email) {
        this.Name = Name;
        this.Email = Email;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }
}
