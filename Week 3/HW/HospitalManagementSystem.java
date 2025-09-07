import java.util.*;

public class HospitalManagementSystem {
    public static void main(String[] args) {
        Hospital.setHospitalName("CityCare Hospital");

        // Create patients
        Patient p1 = new Patient("P001", "John Doe", 30, "Male", "123-456-7890");
        Patient p2 = new Patient("P002", "Jane Smith", 45, "Female", "987-654-3210");

        // Add medical history and treatments
        p1.addMedicalHistory("Diabetes");
        p2.addMedicalHistory("Hypertension");
        p1.addTreatment("Metformin 500mg");
        p2.addTreatment("Lisinopril 10mg");

        // Create doctors
        Doctor d1 = new Doctor("D001", "Dr. Alice", "Cardiology", new String[]{"10:00AM", "11:00AM"});
        Doctor d2 = new Doctor("D002", "Dr. Bob", "General Medicine", new String[]{"09:00AM", "10:30AM"});

        // Schedule appointments
        Appointment a1 = Consultation.scheduleAppointment("A001", p1, d1, "2025-09-15", "10:00AM");
        Appointment a2 = FollowUp.scheduleAppointment("A002", p2, d2, "2025-09-15", "09:00AM");
        Appointment a3 = Emergency.scheduleAppointment("A003", p1, d2, "2025-09-14", "08:00AM");

        // Generate bills
        System.out.println("\n--- Billing ---");
        System.out.println("Bill for appointment " + a1.getAppointmentId() + ": $" + a1.generateBill());
        System.out.println("Bill for appointment " + a2.getAppointmentId() + ": $" + a2.generateBill());
        System.out.println("Bill for appointment " + a3.getAppointmentId() + ": $" + a3.generateBill());

        // Cancel an appointment
        a2.cancelAppointment();

        // Update treatment for patient
        p1.addTreatment("Insulin Therapy");

        // Discharge a patient
        p1.dischargePatient();

        // Generate reports
        System.out.println("\n--- Hospital Report ---");
        Hospital.generateHospitalReport();

        System.out.println("\n--- Doctor Utilization ---");
        Hospital.getDoctorUtilization();

        System.out.println("\n--- Patient Statistics ---");
        Hospital.getPatientStatistics();
    }
}

// Patient class
class Patient {
    private String patientId;
    private String patientName;
    private int age;
    private String gender;
    private String contactInfo;
    private List<String> medicalHistory;
    private List<String> currentTreatments;
    private boolean discharged;

    // Static tracker
    private static int totalPatients = 0;

    public Patient(String patientId, String patientName, int age, String gender, String contactInfo) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.age = age;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.medicalHistory = new ArrayList<>();
        this.currentTreatments = new ArrayList<>();
        this.discharged = false;
        totalPatients++;
        Hospital.incrementPatients();
    }

    public String getPatientId() { return patientId; }
    public String getPatientName() { return patientName; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public boolean isDischarged() { return discharged; }

    public void addMedicalHistory(String record) {
        medicalHistory.add(record);
    }

    public void addTreatment(String treatment) {
        currentTreatments.add(treatment);
    }

    public void dischargePatient() {
        discharged = true;
        currentTreatments.clear();
        System.out.println("Patient " + patientName + " discharged.");
    }

    public void displayPatientInfo() {
        System.out.println("Patient ID: " + patientId + ", Name: " + patientName + ", Age: " + age +
                ", Gender: " + gender + ", Contact: " + contactInfo);
        System.out.println("Medical History: " + medicalHistory);
        System.out.println("Current Treatments: " + currentTreatments);
        System.out.println("Discharged: " + discharged);
    }

    public static int getTotalPatients() {
        return totalPatients;
    }
}

// Doctor class
class Doctor {
    private String doctorId;
    private String doctorName;
    private String specialization;
    private List<String> availableSlots;
    private int patientsHandled;
    private double consultationFee;

    // Static doctor list to help generate reports
    private static List<Doctor> doctors = new ArrayList<>();

    public Doctor(String doctorId, String doctorName, String specialization, String[] availableSlots) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.availableSlots = new ArrayList<>(Arrays.asList(availableSlots));
        this.patientsHandled = 0;
        // Fees vary by specialization (example)
        switch (specialization.toLowerCase()) {
            case "cardiology":
                this.consultationFee = 150;
                break;
            case "general medicine":
                this.consultationFee = 100;
                break;
            default:
                this.consultationFee = 120;
        }
        doctors.add(this);
    }

    public String getDoctorId() { return doctorId; }
    public String getDoctorName() { return doctorName; }
    public String getSpecialization() { return specialization; }
    public List<String> getAvailableSlots() { return availableSlots; }
    public double getConsultationFee() { return consultationFee; }

    public boolean isSlotAvailable(String slot) {
        return availableSlots.contains(slot);
    }

    public void bookSlot(String slot) {
        if (isSlotAvailable(slot)) {
            availableSlots.remove(slot);
            patientsHandled++;
        } else {
            System.out.println("Slot " + slot + " not available for Dr. " + doctorName);
        }
    }

    public int getPatientsHandled() { return patientsHandled; }

    public static List<Doctor> getDoctors() {
        return doctors;
    }

    public void displayDoctorInfo() {
        System.out.println("Doctor ID: " + doctorId + ", Name: " + doctorName + ", Specialization: " + specialization +
                ", Patients Handled: " + patientsHandled + ", Consultation Fee: $" + consultationFee);
        System.out.println("Available Slots: " + availableSlots);
    }
}

// Abstract Appointment class
abstract class Appointment {
    protected String appointmentId;
    protected Patient patient;
    protected Doctor doctor;
    protected String appointmentDate;
    protected String appointmentTime;
    protected String status; // Scheduled, Cancelled, Completed

    // Static counters
    protected static int totalAppointments = 0;

    public Appointment(String appointmentId, Patient patient, Doctor doctor, String date, String time) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = date;
        this.appointmentTime = time;
        this.status = "Scheduled";
        totalAppointments++;
        Hospital.incrementAppointments();
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public boolean scheduleAppointment() {
        if (!doctor.isSlotAvailable(appointmentTime)) {
            System.out.println("Appointment time slot not available for doctor.");
            return false;
        }
        doctor.bookSlot(appointmentTime);
        System.out.println("Appointment " + appointmentId + " scheduled for patient " + patient.getPatientName() +
                " with Dr. " + doctor.getDoctorName() + " on " + appointmentDate + " at " + appointmentTime);
        return true;
    }

    public void cancelAppointment() {
        if (status.equalsIgnoreCase("Cancelled")) {
            System.out.println("Appointment already cancelled.");
            return;
        }
        status = "Cancelled";
        System.out.println("Appointment " + appointmentId + " cancelled.");
    }

    public abstract double generateBill();

    public String getStatus() {
        return status;
    }

    public void displayAppointmentInfo() {
        System.out.println("Appointment ID: " + appointmentId + ", Patient: " + patient.getPatientName() +
                ", Doctor: " + doctor.getDoctorName() + ", Date: " + appointmentDate +
                ", Time: " + appointmentTime + ", Status: " + status);
    }
}

// Consultation appointment subclass
class Consultation extends Appointment {
    private static final double RATE_MULTIPLIER = 1.0;

    public Consultation(String appointmentId, Patient patient, Doctor doctor, String date, String time) {
        super(appointmentId, patient, doctor, date, time);
    }

    public static Consultation scheduleAppointment(String appointmentId, Patient patient, Doctor doctor, String date, String time) {
        Consultation c = new Consultation(appointmentId, patient, doctor, date, time);
        if (c.scheduleAppointment()) {
            return c;
        }
        return null;
    }

    @Override
    public double generateBill() {
        if (status.equalsIgnoreCase("Cancelled")) return 0;
        double fee = doctor.getConsultationFee() * RATE_MULTIPLIER;
        Hospital.addRevenue(fee);
        return fee;
    }
}

// Follow-up appointment subclass
class FollowUp extends Appointment {
    private static final double RATE_MULTIPLIER = 0.75;

    public FollowUp(String appointmentId, Patient patient, Doctor doctor, String date, String time) {
        super(appointmentId, patient, doctor, date, time);
    }

    public static FollowUp scheduleAppointment(String appointmentId, Patient patient, Doctor doctor, String date, String time) {
        FollowUp f = new FollowUp(appointmentId, patient, doctor, date, time);
        if (f.scheduleAppointment()) {
            return f;
        }
        return null;
    }

    @Override
    public double generateBill() {
        if (status.equalsIgnoreCase("Cancelled")) return 0;
        double fee = doctor.getConsultationFee() * RATE_MULTIPLIER;
        Hospital.addRevenue(fee);
        return fee;
    }
}

// Emergency appointment subclass
class Emergency extends Appointment {
    private static final double RATE_MULTIPLIER = 2.0;

    public Emergency(String appointmentId, Patient patient, Doctor doctor, String date, String time) {
        super(appointmentId, patient, doctor, date, time);
    }

    public static Emergency scheduleAppointment(String appointmentId, Patient patient, Doctor doctor, String date, String time) {
        Emergency e = new Emergency(appointmentId, patient, doctor, date, time);
        if (e.scheduleAppointment()) {
            return e;
        }
        return null;
    }

    @Override
    public double generateBill() {
        if (status.equalsIgnoreCase("Cancelled")) return 0;
        double fee = doctor.getConsultationFee() * RATE_MULTIPLIER;
        Hospital.addRevenue(fee);
        return fee;
    }
}

// Hospital class for static tracking and reports
class Hospital {
    private static int totalPatients = 0;
    private static int totalAppointments = 0;
    private static double totalRevenue = 0;
    private static String hospitalName;

    public static void setHospitalName(String name) {
        hospitalName = name;
    }

    public static void incrementPatients() {
        totalPatients++;
    }

    public static void incrementAppointments() {
        totalAppointments++;
    }

    public static void addRevenue(double amount) {
        totalRevenue += amount;
    }

    public static void generateHospitalReport() {
        System.out.println("Hospital: " + hospitalName);
        System.out.println("Total Patients: " + totalPatients);
        System.out.println("Total Appointments: " + totalAppointments);
        System.out.println("Total Revenue: $" + totalRevenue);
    }

    public static void getDoctorUtilization() {
        System.out.println("Doctor Utilization Report:");
        for (Doctor d : Doctor.getDoctors()) {
            System.out.println("Doctor " + d.getDoctorName() + " handled " + d.getPatientsHandled() + " patients.");
        }
    }

    public static void getPatientStatistics() {
        // Could be expanded, for now, just show total patients discharged vs active
        System.out.println("Patient Statistics:");
        System.out.println("Total Patients Registered: " + totalPatients);
        // We don't track all patients globally here, so let's leave it at that for now
    }
}
