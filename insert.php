<?php
include 'dbcon.php'; // Connects to DB via $conn

// Sample user data
$users = [
    [1, "Nishanth", "Ravi", "nish2@gmail.com", "abc123", "patient"],
    [2, "Aarav", "Kumar", "aaravkumar@gmail.com", "pass456", "patient"],
    [3, "Meera", "Rao", "meerarao@gmail.com", "secure789", "patient"],
    [4, "Divya", "Menon", "divyamenon@gmail.com", "mypassword", "patient"],
    [5, "Karthik", "Raj", "karthikraj@gmail.com", "karthikpass", "patient"],
    [6, "Sneha", "Sharma", "snehasharma@gmail.com", "snehapass", "patient"]
];

// Sample patient profile data
$patient_details = [
    [1, "Nishanth", "1992-04-10", "9876543210", "nish2@gmail.com", "Chennai", "South"],
    [2, "Aarav Kumar", "1990-06-15", "9000000001", "aaravkumar@gmail.com", "Bengaluru", "South"],
    [3, "Meera Rao", "1988-09-25", "9000000002", "meerarao@gmail.com", "Hyderabad", "South"],
    [4, "Divya Menon", "1995-12-01", "9000000003", "divyamenon@gmail.com", "Mumbai", "West"],
    [5, "Karthik Raj", "1985-11-17", "9000000004", "karthikraj@gmail.com", "Delhi", "North"],
    [6, "Sneha Sharma", "1993-03-08", "9000000005", "snehasharma@gmail.com", "Kolkata", "East"]
];

$success = true;

// Insert into `users`
foreach ($users as $user) {
    $stmt = $conn->prepare("INSERT INTO users (user_id, first_name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?, ?)");
    $stmt->bind_param("isssss", $user[0], $user[1], $user[2], $user[3], $user[4], $user[5]);
    if (!$stmt->execute()) {
        echo "Error inserting into users: " . $stmt->error . "<br>";
        $success = false;
    }
    $stmt->close();
}

// Insert into `patient_detial`
foreach ($patient_details as $patient) {
    $stmt = $conn->prepare("INSERT INTO patient_detial (user_id, name, dob, phone_number, email, city, region) VALUES (?, ?, ?, ?, ?, ?, ?)");
    $stmt->bind_param("issssss", $patient[0], $patient[1], $patient[2], $patient[3], $patient[4], $patient[5], $patient[6]);
    if (!$stmt->execute()) {
        echo "Error inserting into patient_detial: " . $stmt->error . "<br>";
        $success = false;
    }
    $stmt->close();
}

if ($success) {
    echo "✅ All users and patient details inserted successfully!";
}

$conn->close();
?>
