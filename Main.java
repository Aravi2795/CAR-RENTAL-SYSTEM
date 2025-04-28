import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
        
        if (docName.endsWith(".pdf") || docName.endsWith(".jpg")) {
            isVerified = true;
        } else {
            isVerified = false;
        }
    }

    public String toString() {
        return docName + " - " + (isVerified ? "Verified" : "Not Verified");
    }
}

class Rental {
    Car car;
    Customer customer;
    LocalDate rentDate;
    LocalDate dueDate;

    Rental(Car car, Customer customer, LocalDate rentDate, int days) {
        this.car = car;
        this.customer = customer;
        this.rentDate = rentDate;
        this.dueDate = rentDate.plusDays(days);
    }

    public String toString() {
        return car.model + " rented by " + customer.name + " until " + dueDate;
    }
}

public class Carrent {
    static Scanner scanner = new Scanner(System.in);
    static List<Car> cars = new ArrayList<>();
    static List<Rental> rentals = new ArrayList<>();

    public static void main(String[] args) {
        seedData();
        while (true) {
            System.out.println("\n--- Car Rental System ---");
            System.out.println("1. View Available Cars");
            System.out.println("2. Rent a Car");
            System.out.println("3. Return a Car");
            System.out.println("4. Upload & Verify Document");
            System.out.println("5. View Rental Reminders");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showAvailableCars();
                case 2 -> rentCar();
                case 3 -> returnCar();
                case 4 -> uploadDocument();
                case 5 -> viewReminders();
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    static void seedData() {
        cars.add(new Car("C101", "Toyota Corolla"));
        cars.add(new Car("C102", "Honda Civic"));
        cars.add(new Car("C103", "Ford Mustang"));
		cars.add(new Car("C104", "Mahendra Thar"));
		cars.add(new Car("C105", "Mahendra Scopio"));
		cars.add(new Car("C106", "Swift vdi"));
		cars.add(new Car("C107", "KIA"));
		cars.add(new Car("C108", "BMW"));
		cars.add(new Car("C109", "Jaguar"));
		cars.add(new Car("C110", "Audi"));
		cars.add(new Car("C111", "Nissan GTR"));
		cars.add(new Car("C112", "TATA Punch"));
    }

    static void showAvailableCars() {
        System.out.println("\n--- Available Cars ---");
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    static void rentCar() {
        System.out.println("\n--- Rent a Car ---");
        showAvailableCars();
        System.out.print("Enter Car ID to rent: ");
        String carId = scanner.nextLine();
        Car carToRent = null;

        for (Car car : cars) {
            if (car.id.equals(carId) && car.isAvailable) {
                carToRent = car;
                break;
            }
        }

        if (carToRent == null) {
            System.out.println("Car not available.");
            return;
        }

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your license number: ");
        String license = scanner.nextLine();

        Customer customer = new Customer(name, license);
        System.out.print("Enter number of rental days: ");
        int days = scanner.nextInt();
        scanner.nextLine();

        carToRent.isAvailable = false;
        Rental rental = new Rental(carToRent, customer, LocalDate.now(), days);
        rentals.add(rental);

        System.out.println("Car rented successfully! Due date: " + rental.dueDate);
    }

    static void returnCar() {
        System.out.println("\n--- Return a Car ---");
        System.out.print("Enter Car ID to return: ");
        String carId = scanner.nextLine();

        Rental toRemove = null;
        for (Rental rental : rentals) {
            if (rental.car.id.equals(carId)) {
                rental.car.isAvailable = true;
                toRemove = rental;
                break;
            }
        }

        if (toRemove != null) {
            rentals.remove(toRemove);
            System.out.println("Car returned successfully.");
        } else {
            System.out.println("Rental record not found.");
        }
    }

    static void uploadDocument() {
        System.out.println("\n--- Upload & Verify Document ---");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter document filename (e.g. license.pdf): ");
        String filename = scanner.nextLine();

        Document doc = new Document(filename);
        doc.verify();

        System.out.println("Document upload status: " + doc);
    }
	

    static void viewReminders() {
    System.out.println("\n--- Rental Reminders ---");
    LocalDate today = LocalDate.now();
    StringBuilder reminderDetails = new StringBuilder();

    for (Rental rental : rentals) {
        long daysLeft = ChronoUnit.DAYS.between(today, rental.dueDate);
        String reminder = rental + " - " + daysLeft + " day(s) left";
        System.out.println(reminder);
        reminderDetails.append(reminder).append("\n");
    }

    
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("new1.txt", true))) {
        writer.write("Reminders as of " + today + ":\n");
        writer.write(reminderDetails.toString());
        writer.write("\n"); 
        System.out.println("Reminders have been saved to 'new1.txt'.");
    } catch (IOException e) {
        System.out.println("Error writing to file: " + e.getMessage());
    }
}
}
