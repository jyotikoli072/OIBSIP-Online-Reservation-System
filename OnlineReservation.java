import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OnlineReservation {
    private Map<String, String> users; // For storing username and passwords
    private Map<String, Reservation> reservations; // For storing reservation data with PNR

    public OnlineReservation() {
        users = new HashMap<>();
        reservations = new HashMap<>();
    }

    public void execute() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    register(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }

            System.out.println();
        }
    }

    private void login(Scanner scanner) {
        System.out.println();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            System.out.println();
            System.out.println("Logged in successfully...");
            System.out.println();
            reservationMenu(scanner, username);
        } else {
            System.out.println();
            System.out.println("Invalid username or password...");
        }
    }

    private void register(Scanner scanner) {
        System.out.println();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println();
            System.out.println("Username already exists. Try again.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        users.put(username, password);
        System.out.println();
        System.out.println("Registration successful... \nYou can now log in to the system...");
    }

    private void reservationMenu(Scanner scanner, String username) {
        while (true) {
            System.out.println("1. Make a reservation");
            System.out.println("2. Cancel a reservation");
            System.out.println("3. Update a reservation");
            System.out.println("4. Show reservation");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    makeReservation(scanner, username);
                    break;
                case 2:
                    cancelReservation(scanner, username);
                    break;
                case 3:
                    updateReservation(scanner, username);
                    break;
                case 4:
                    showReservation(username);
                    break;
                case 5:
                    System.out.println();
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }

            System.out.println();
        }
    }

    private void makeReservation(Scanner scanner, String username) {
        System.out.println();
        System.out.print("Enter your basic details: ");
        String basicDetails = scanner.nextLine();
        System.out.print("Enter train number: ");
        String trainNumber = scanner.nextLine();
        String trainName = getTrainName(trainNumber);
        System.out.print("Enter class type: ");
        String classType = scanner.nextLine();
        System.out.print("Enter date of journey (YYYY-MM-DD): ");
        String dateOfJourney = scanner.nextLine();
        System.out.print("Enter from (place): ");
        String fromPlace = scanner.nextLine();
        System.out.print("Enter to (destination): ");
        String toDestination = scanner.nextLine();

        String pnr = generatePNR(username);

        Reservation reservation = new Reservation(basicDetails, trainNumber, trainName, classType, dateOfJourney, fromPlace, toDestination);
        reservations.put(pnr, reservation);

        System.out.println("Reservation created successfully with PNR: " + pnr);
    }

    private void cancelReservation(Scanner scanner, String username) {
        System.out.print("Enter your PNR number: ");
        String pnr = scanner.nextLine();

        if (reservations.containsKey(pnr)) {
            System.out.println();
            System.out.println("Your current reservation: " + reservations.get(pnr));
            System.out.print("Do you want to cancel this reservation? (Y/N): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("Y")) {
                reservations.remove(pnr);
                System.out.println();
                System.out.println("Reservation canceled successfully...");
            } else {
                System.out.println("Reservation not canceled.");
            }
        } else {
            System.out.println("No reservation found with the given PNR.");
        }
    }

    private void updateReservation(Scanner scanner, String username) {
        System.out.print("Enter your PNR number: ");
        String pnr = scanner.nextLine();

        if (reservations.containsKey(pnr)) {
            System.out.println();
            System.out.println("Your current reservation: " + reservations.get(pnr));
            System.out.print("Enter new reservation details: ");
            String newReservationDetails = scanner.nextLine();
            System.out.print("Enter new train number: ");
            String newTrainNumber = scanner.nextLine();
            String newTrainName = getTrainName(newTrainNumber);
            System.out.print("Enter new class type: ");
            String newClassType = scanner.nextLine();
            System.out.print("Enter new date of journey (YYYY-MM-DD): ");
            String newDateOfJourney = scanner.nextLine();
            System.out.print("Enter new from (place): ");
            String newFromPlace = scanner.nextLine();
            System.out.print("Enter new to (destination): ");
            String newToDestination = scanner.nextLine();

            Reservation updatedReservation = new Reservation(newReservationDetails, newTrainNumber, newTrainName, newClassType, newDateOfJourney, newFromPlace, newToDestination);
            reservations.put(pnr, updatedReservation);

            System.out.println("Reservation updated successfully...");
        } else {
            System.out.println("No reservation found with the given PNR.");
        }
    }

    private void showReservation(String username) {
        System.out.print("Enter your PNR number: ");
        Scanner scanner = new Scanner(System.in);
        String pnr = scanner.nextLine();

        if (reservations.containsKey(pnr)) {
            System.out.println();
            System.out.println("Your current reservation: " + reservations.get(pnr));
        } else {
            System.out.println("No reservation found with the given PNR.");
        }
    }

    private String getTrainName(String trainNumber) {
        // Simulating train name retrieval based on train number
        // In real-world application, this would fetch from a database or other data source
        switch (trainNumber) {
            case "123":
                return "Express Train";
            case "456":
                return "Local Train";
            case "789":
                return "Intercity Train";
            default:
                return "Unknown Train";
        }
    }

    private String generatePNR(String username) {
        // Generating a simple PNR based on the username and current timestamp
        return username + System.currentTimeMillis();
    }

    public static void main(String[] args) {
        OnlineReservation reservationSystem = new OnlineReservation();
        reservationSystem.execute();
    }
}

class Reservation {
    private String basicDetails;
    private String trainNumber;
    private String trainName;
    private String classType;
    private String dateOfJourney;
    private String fromPlace;
    private String toDestination;

    public Reservation(String basicDetails, String trainNumber, String trainName, String classType, String dateOfJourney, String fromPlace, String toDestination) {
        this.basicDetails = basicDetails;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.fromPlace = fromPlace;
        this.toDestination = toDestination;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "basicDetails='" + basicDetails + '\'' +
                ", trainNumber='" + trainNumber + '\'' +
                ", trainName='" + trainName + '\'' +
                ", classType='" + classType + '\'' +
                ", dateOfJourney='" + dateOfJourney + '\'' +
                ", fromPlace='" + fromPlace + '\'' +
                ", toDestination='" + toDestination + '\'' +
                '}';
    }
}
