import java.util.ArrayList;

public class HotelReservationSystem {
    public static void main(String[] args) {
        // Set hotel name
        Booking.setHotelName("Grand Royal Hotel");

        // Create rooms
        Room[] rooms = new Room[5];
        rooms[0] = new Room("101", "Single", 100.0, 1);
        rooms[1] = new Room("102", "Double", 180.0, 2);
        rooms[2] = new Room("103", "Suite", 350.0, 4);
        rooms[3] = new Room("104", "Single", 100.0, 1);
        rooms[4] = new Room("105", "Double", 180.0, 2);

        // Create guests
        Guest guest1 = new Guest("G001", "Alice Johnson", "1234567890", "alice@example.com");
        Guest guest2 = new Guest("G002", "Bob Smith", "0987654321", "bob@example.com");

        // Make bookings
        Booking booking1 = Booking.makeReservation(guest1, rooms, "Single", "2025-09-10", "2025-09-12");
        Booking booking2 = Booking.makeReservation(guest2, rooms, "Suite", "2025-09-11", "2025-09-15");

        if (booking1 != null) {
            System.out.println("Booking1 created:");
            booking1.displayBookingInfo();
        }

        if (booking2 != null) {
            System.out.println("Booking2 created:");
            booking2.displayBookingInfo();
        }

        // Cancel a booking
        if (booking1 != null) {
            Booking.cancelReservation(booking1);
            System.out.println("After cancellation of booking1:");
            booking1.displayBookingInfo();
        }

        // Display reports
        System.out.printf("Occupancy Rate: %.2f%%\n", Booking.getOccupancyRate(rooms));
        System.out.printf("Total Revenue: $%.2f\n", Booking.getTotalRevenue());
        System.out.println("Most Popular Room Type: " + Booking.getMostPopularRoomType());
    }
}
class Room {
    private String roomNumber;
    private String roomType;
    private double pricePerNight;
    private boolean isAvailable;
    private int maxOccupancy;

    private static int totalRooms = 0;

    public Room(String roomNumber, String roomType, double pricePerNight, int maxOccupancy) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true;
        this.maxOccupancy = maxOccupancy;
        totalRooms++;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public static int getTotalRooms() {
        return totalRooms;
    }

    public void displayRoomInfo() {
        System.out.printf("Room %s | Type: %s | Price/Night: $%.2f | Available: %b | Max Occupancy: %d\n",
                roomNumber, roomType, pricePerNight, isAvailable, maxOccupancy);
    }
}
class Guest {
    private String guestId;
    private String guestName;
    private String phoneNumber;
    private String email;
    private ArrayList<String> bookingHistory;

    public Guest(String guestId, String guestName, String phoneNumber, String email) {
        this.guestId = guestId;
        this.guestName = guestName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bookingHistory = new ArrayList<>();
    }

    public String getGuestId() {
        return guestId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void addBookingToHistory(String bookingId) {
        bookingHistory.add(bookingId);
    }

    public void displayGuestInfo() {
        System.out.printf("Guest ID: %s | Name: %s | Phone: %s | Email: %s\n", guestId, guestName, phoneNumber, email);
    }

    public void displayBookingHistory() {
        System.out.println("Booking History:");
        for (String bId : bookingHistory) {
            System.out.println(" - " + bId);
        }
    }
}
class Booking {
    private String bookingId;
    private Guest guest;
    private Room room;
    private String checkInDate;
    private String checkOutDate;
    private double totalAmount;

    private static int totalBookings = 0;
    private static double hotelRevenue = 0.0;
    private static String hotelName;

    private static int bookingCounter = 1001;

    public Booking(String bookingId, Guest guest, Room room, String checkInDate, String checkOutDate, double totalAmount) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = totalAmount;
    }

    // Method to calculate number of days between dates (assuming YYYY-MM-DD format)
    private static int calculateDays(String checkIn, String checkOut) {
        // Simple parsing, no validation - can be enhanced
        String[] inParts = checkIn.split("-");
        String[] outParts = checkOut.split("-");

        int inYear = Integer.parseInt(inParts[0]);
        int inMonth = Integer.parseInt(inParts[1]);
        int inDay = Integer.parseInt(inParts[2]);

        int outYear = Integer.parseInt(outParts[0]);
        int outMonth = Integer.parseInt(outParts[1]);
        int outDay = Integer.parseInt(outParts[2]);

        // Approximate days difference (ignoring months length complexity)
        int days = (outYear - inYear) * 365 + (outMonth - inMonth) * 30 + (outDay - inDay);

        return days > 0 ? days : 1;  // Minimum 1 day
    }

    public static Booking makeReservation(Guest guest, Room[] rooms, String roomType, String checkIn, String checkOut) {
        // Find available room of requested type
        for (Room r : rooms) {
            if (r.getRoomType().equalsIgnoreCase(roomType) && r.isAvailable()) {
                int days = calculateDays(checkIn, checkOut);
                double total = days * r.getPricePerNight();

                String bookingId = generateBookingId();
                Booking booking = new Booking(bookingId, guest, r, checkIn, checkOut, total);

                // Mark room as unavailable
                r.setAvailable(false);

                // Update stats
                totalBookings++;
                hotelRevenue += total;

                // Add booking to guest history
                guest.addBookingToHistory(bookingId);

                return booking;
            }
        }
        System.out.println("No available rooms found for type: " + roomType);
        return null;
    }

    public static void cancelReservation(Booking booking) {
        if (booking == null) {
            System.out.println("Invalid booking.");
            return;
        }
        if (!booking.room.isAvailable()) {
            booking.room.setAvailable(true);
            totalBookings--;
            hotelRevenue -= booking.totalAmount;
            System.out.println("Booking " + booking.bookingId + " cancelled successfully.");
        } else {
            System.out.println("Booking already cancelled or room available.");
        }
    }

    public static boolean checkAvailability(Room[] rooms, String roomType) {
        for (Room r : rooms) {
            if (r.getRoomType().equalsIgnoreCase(roomType) && r.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public double calculateBill() {
        return totalAmount;
    }

    public void displayBookingInfo() {
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Guest: " + guest.getGuestName());
        System.out.println("Room Number: " + room.getRoomNumber());
        System.out.println("Room Type: " + room.getRoomType());
        System.out.println("Check-In: " + checkInDate);
        System.out.println("Check-Out: " + checkOutDate);
        System.out.printf("Total Amount: $%.2f\n", totalAmount);
        System.out.println("Room Available: " + room.isAvailable());
        System.out.println("-------------------------");
    }

    public static String generateBookingId() {
        return "BKG" + (bookingCounter++);
    }

    public static int getTotalBookings() {
        return totalBookings;
    }

    public static double getTotalRevenue() {
        return hotelRevenue;
    }

    public static void setHotelName(String name) {
        hotelName = name;
    }

    public static String getHotelName() {
        return hotelName;
    }

    public static double getOccupancyRate(Room[] rooms) {
        int bookedRooms = 0;
        for (Room r : rooms) {
            if (!r.isAvailable()) {
                bookedRooms++;
            }
        }
        return ((double) bookedRooms / rooms.length) * 100;
    }

    public static String getMostPopularRoomType() {
        // For simplicity, let's assume popularity based on number of bookings by type stored in hotelRevenue context
        // We need to track roomType booking count separately. For demo, return hardcoded or null.

        // This can be enhanced if we maintain a Map<String,Integer> of bookings per roomType.
        return "Not Implemented"; 
    }
}
