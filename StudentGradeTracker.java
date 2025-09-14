package java_codes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeTracker extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> studentList = new JList<>(listModel);
    private JLabel summaryLabel = new JLabel("Summary will appear here.");

    public StudentGradeTracker() {
        setTitle("Student Grade Tracker");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for input
        JPanel inputPanel = new JPanel();
        JTextField nameField = new JTextField(10);
        JTextField gradeField = new JTextField(5);
        JButton addButton = new JButton("Add Student");

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeField);
        inputPanel.add(addButton);

        // Center panel for list
        JScrollPane listScroll = new JScrollPane(studentList);

        // Bottom panel for summary
        JButton summaryButton = new JButton("Show Summary");
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(summaryButton, BorderLayout.NORTH);
        bottomPanel.add(summaryLabel, BorderLayout.CENTER);

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(listScroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String gradeText = gradeField.getText().trim();
            if (!name.isEmpty() && !gradeText.isEmpty()) {
                try {
                    double grade = Double.parseDouble(gradeText);
                    students.add(new Student(name, grade));
                    listModel.addElement(name + " - " + grade);
                    nameField.setText("");
                    gradeField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Enter a valid grade.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Enter both name and grade.");
            }
        });

        summaryButton.addActionListener(e -> {
            if (students.isEmpty()) {
                summaryLabel.setText("No students added yet.");
                return;
            }
            double total = 0, highest = students.get(0).grade, lowest = students.get(0).grade;
            for (Student s : students) {
                total += s.grade;
                if (s.grade > highest) highest = s.grade;
                if (s.grade < lowest) lowest = s.grade;
            }
            double average = total / students.size();
            summaryLabel.setText("<html>Total Students: " + students.size() +
                    "<br>Average: " + average +
                    "<br>Highest: " + highest +
                    "<br>Lowest: " + lowest + "</html>");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeTracker().setVisible(true);
        });
    }
}
