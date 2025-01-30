import java.util.*;

class Room {
    private int roomId;
    private String roomType;
    private boolean isAvailable;
    private double pricePerNight;

    public Room(int roomId, String roomType, boolean isAvailable, double pricePerNight) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.isAvailable = isAvailable;
        this.pricePerNight = pricePerNight;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    @Override
    public String toString() {
        return "Room ID: " + roomId + ", Type: " + roomType + ", Price: $" + pricePerNight + ", Available: " + isAvailable;
    }
}

class Reservation {
    private int reservationId;
    private int roomId;
    private String guestName;
    private int numberOfNights;
    private double totalAmount;

    public Reservation(int reservationId, int roomId, String guestName, int numberOfNights, double totalAmount) {
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.guestName = guestName;
        this.numberOfNights = numberOfNights;
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + ", Room ID: " + roomId + ", Guest: " + guestName + ", Nights: " + numberOfNights + ", Total: $" + totalAmount;
    }
}

class HotelReservationSystem {
    private List<Room> rooms;
    private List<Reservation> reservations;
    private int nextReservationId;

    public HotelReservationSystem() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        nextReservationId = 1;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> searchAvailableRooms(String roomType) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable() && room.getRoomType().equalsIgnoreCase(roomType)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Reservation makeReservation(String guestName, int roomId, int numberOfNights) {
        for (Room room : rooms) {
            if (room.getRoomId() == roomId && room.isAvailable()) {
                room.setAvailable(false);
                double totalAmount = room.getPricePerNight() * numberOfNights;
                Reservation reservation = new Reservation(nextReservationId++, roomId, guestName, numberOfNights, totalAmount);
                reservations.add(reservation);
                return reservation;
            }
        }
        return null;
    }

    public List<Reservation> viewReservations() {
        return reservations;
    }

    public boolean processPayment(Reservation reservation) {
        System.out.println("Processing payment of $" + reservation.toString().split("Total: ")[1] + "... Payment successful!");
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        HotelReservationSystem system = new HotelReservationSystem();

        // Adding rooms
        system.addRoom(new Room(101, "Single", true, 100));
        system.addRoom(new Room(102, "Double", true, 150));
        system.addRoom(new Room(103, "Suite", true, 250));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Hotel Reservation System ---");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Reservations");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter room type (Single/Double/Suite): ");
                    String roomType = scanner.nextLine();
                    List<Room> availableRooms = system.searchAvailableRooms(roomType);
                    if (availableRooms.isEmpty()) {
                        System.out.println("No available rooms for the selected type.");
                    } else {
                        System.out.println("Available Rooms:");
                        for (Room room : availableRooms) {
                            System.out.println(room);
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter room ID: ");
                    int roomId = scanner.nextInt();
                    System.out.print("Enter number of nights: ");
                    int nights = scanner.nextInt();
                    Reservation reservation = system.makeReservation(name, roomId, nights);
                    if (reservation != null) {
                        System.out.println("Reservation successful! Details:");
                        System.out.println(reservation);
                        system.processPayment(reservation);
                    } else {
                        System.out.println("Failed to make reservation. Room might not be available.");
                    }
                    break;
                case 3:
                    System.out.println("Reservations:");
                    for (Reservation res : system.viewReservations()) {
                        System.out.println(res);
                    }
                    break;
                case 4:
                    System.out.println("Exiting... Thank you!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

