// Class: Subject
class Subject {
    private String subjectCode;
    private String subjectName;
    private int maxMarks;
    private int passMarks;

    public Subject(String subjectCode, String subjectName, int maxMarks, int passMarks) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.maxMarks = maxMarks;
        this.passMarks = passMarks;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public int getPassMarks() {
        return passMarks;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }
}
// Class: Student
class Student {
    private String studentId;
    private String studentName;
    private int grade;
    private double[] marks; // 5 subjects
    private double totalMarks;
    private double percentage;

    private static int studentCounter = 1;

    public Student(String name, int grade) {
        this.studentName = name;
        this.grade = grade;
        this.studentId = "S" + String.format("%03d", studentCounter++);
        this.marks = new double[5]; // For 5 subjects
    }

    public void setMark(int subjectIndex, double mark) {
        if (subjectIndex >= 0 && subjectIndex < marks.length) {
            marks[subjectIndex] = mark;
        }
    }

    public void calculateTotal() {
        totalMarks = 0;
        for (double m : marks) {
            totalMarks += m;
        }
    }

    public void calculatePercentage() {
        calculateTotal();
        percentage = totalMarks / marks.length;
    }

    public boolean isPass(Subject[] subjects) {
        for (int i = 0; i < marks.length; i++) {
            if (marks[i] < subjects[i].getPassMarks()) {
                return false;
            }
        }
        return true;
    }

    public void displayResult(Subject[] subjects) {
        calculatePercentage();
        System.out.println("Student ID   : " + studentId);
        System.out.println("Name         : " + studentName);
        System.out.println("Grade        : " + grade);
        for (int i = 0; i < subjects.length; i++) {
            System.out.println(subjects[i].getSubjectName() + ": " + marks[i]);
        }
        System.out.println("Total Marks  : " + totalMarks);
        System.out.println("Percentage   : " + percentage + "%");
        System.out.println("Result       : " + (isPass(subjects) ? "PASS" : "FAIL"));
        System.out.println("-------------------------------");
    }

    public double getPercentage() {
        return percentage;
    }

    public static Student getTopStudent(Student[] students) {
        Student top = null;
        double max = -1;
        for (Student s : students) {
            s.calculatePercentage();
            if (s.percentage > max) {
                max = s.percentage;
                top = s;
            }
        }
        return top;
    }

    public static double getClassAverage(Student[] students) {
        double total = 0;
        for (Student s : students) {
            s.calculatePercentage();
            total += s.percentage;
        }
        return total / students.length;
    }

    public static double getPassPercentage(Student[] students, Subject[] subjects) {
        int passed = 0;
        for (Student s : students) {
            if (s.isPass(subjects)) {
                passed++;
            }
        }
        return ((double) passed / students.length) * 100;
    }

    public String getName() {
        return studentName;
    }

    public String getId() {
        return studentId;
    }
}
// Class: Teacher
class Teacher {
    private String teacherId;
    private String teacherName;
    private String subject;
    private int studentsHandled;

    private static int totalTeachers = 0;
    private static int teacherCounter = 1;

    public Teacher(String name, String subject) {
        this.teacherName = name;
        this.subject = subject;
        this.teacherId = "T" + String.format("%03d", teacherCounter++);
        totalTeachers++;
    }

    public void assignGrades(Student student, Subject subject, double mark, int subjectIndex) {
        if (mark <= subject.getMaxMarks()) {
            student.setMark(subjectIndex, mark);
            studentsHandled++;
            System.out.println(teacherName + " assigned " + mark + " to " + student.getName() + " for " + subject.getSubjectName());
        } else {
            System.out.println("Invalid marks for " + subject.getSubjectName());
        }
    }

    public void displayTeacherInfo() {
        System.out.println("Teacher ID   : " + teacherId);
        System.out.println("Name         : " + teacherName);
        System.out.println("Subject      : " + subject);
        System.out.println("Students Taught: " + studentsHandled);
        System.out.println("-------------------------------");
    }

    public static int getTotalTeachers() {
        return totalTeachers;
    }
}
// Main class: SchoolManagement
public class SchoolManagement {
    public static void main(String[] args) {
        // Create 5 Subjects
        Subject[] subjects = new Subject[5];
        subjects[0] = new Subject("ENG", "English", 100, 35);
        subjects[1] = new Subject("MATH", "Mathematics", 100, 35);
        subjects[2] = new Subject("SCI", "Science", 100, 35);
        subjects[3] = new Subject("HIST", "History", 100, 35);
        subjects[4] = new Subject("COMP", "Computer", 100, 35);

        // Create 3 Teachers
        Teacher[] teachers = new Teacher[3];
        teachers[0] = new Teacher("Mrs. Smith", "English");
        teachers[1] = new Teacher("Mr. Brown", "Mathematics");
        teachers[2] = new Teacher("Ms. Green", "Science");

        // Create 3 Students
        Student[] students = new Student[3];
        students[0] = new Student("Alice", 10);
        students[1] = new Student("Bob", 10);
        students[2] = new Student("Charlie", 10);

        // Teachers assign grades (for simplicity, same teacher assigns all subjects)
        teachers[0].assignGrades(students[0], subjects[0], 88, 0);
        teachers[0].assignGrades(students[0], subjects[1], 92, 1);
        teachers[0].assignGrades(students[0], subjects[2], 79, 2);
        teachers[0].assignGrades(students[0], subjects[3], 85, 3);
        teachers[0].assignGrades(students[0], subjects[4], 90, 4);

        teachers[1].assignGrades(students[1], subjects[0], 60, 0);
        teachers[1].assignGrades(students[1], subjects[1], 75, 1);
        teachers[1].assignGrades(students[1], subjects[2], 40, 2);
        teachers[1].assignGrades(students[1], subjects[3], 55, 3);
        teachers[1].assignGrades(students[1], subjects[4], 70, 4);

        teachers[2].assignGrades(students[2], subjects[0], 30, 0); // Fail
        teachers[2].assignGrades(students[2], subjects[1], 28, 1); // Fail
        teachers[2].assignGrades(students[2], subjects[2], 45, 2);
        teachers[2].assignGrades(students[2], subjects[3], 50, 3);
        teachers[2].assignGrades(students[2], subjects[4], 65, 4);

        // Display student results
        System.out.println("\n=== Student Results ===");
        for (Student s : students) {
            s.displayResult(subjects);
        }

        // Display teacher info
        System.out.println("\n=== Teacher Information ===");
        for (Teacher t : teachers) {
            t.displayTeacherInfo();
        }

        // Display school-wide stats
        System.out.println("\n=== School Statistics ===");
        Student top = Student.getTopStudent(students);
        System.out.println("Top Student   : " + top.getName() + " (" + top.getId() + ") with " + top.getPercentage() + "%");
        System.out.println("Class Average : " + Student.getClassAverage(students) + "%");
        System.out.println("Pass %        : " + Student.getPassPercentage(students, subjects) + "%");
        System.out.println("Total Teachers: " + Teacher.getTotalTeachers());
    }
}
