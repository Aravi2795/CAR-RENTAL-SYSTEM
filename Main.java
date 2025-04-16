import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Car {
    String id;
    String model;
    boolean isAvailable;

    Car(String id, String model) {
        this.id = id;
        this.model = model;
        this.isAvailable = true;
    }

    public String toString() {
        return id + " - " + model + (isAvailable ? " (Available)" : " (Rented)");
    }
}

class Customer {
    String name;
    String licenseNumber;
    Document document;

    Customer(String name, String licenseNumber) {
        this.name = name;
        this.licenseNumber = licenseNumber;
    }
}

class Document {
    String docName;
    boolean isVerified;

    Document(String docName) {
        this.docName = docName;
        this.isVerified = false;
    }

    void verify() {
        // simple logic: if document name ends with .pdf or .jpg, it's valid
        if (docName.endsWith(".pdf") || docName.endsWith(".jpg")) {
            isVerified = true;
        } else {
            isVerified = false;
        }
    }
