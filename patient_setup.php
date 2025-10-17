<?php
header('Content-Type: application/json');
include 'dbcon.php'; // Make sure this sets $conn as your MySQLi connection

// Get JSON input as associative array
$data = json_decode(file_get_contents("php://input"), true);

// Extract and sanitize fields with null coalescing and trim
$user_id = isset($data['user_id']) ? (int)$data['user_id'] : 0;
$name = isset($data['name']) ? trim($data['name']) : '';
$date_of_birth = isset($data['date_of_birth']) ? trim($data['date_of_birth']) : '';
$phone_number = isset($data['phone_number']) ? trim($data['phone_number']) : '';
$email = isset($data['email']) ? trim($data['email']) : '';
$city = isset($data['city']) ? trim($data['city']) : '';

// Validate required fields
if ($user_id <= 0 || empty($name) || empty($date_of_birth) || empty($phone_number) || empty($email) || empty($city)) {
    echo json_encode([
        "status" => "error",
        "message" => "All fields are required and user_id must be valid"
    ]);
    exit();
}

// Optional: validate email format
if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
    echo json_encode([
        "status" => "error",
        "message" => "Invalid email format"
    ]);
    exit();
}

// Prepare insert statement
$stmt = $conn->prepare("INSERT INTO patient_details (user_id, name, date_of_birth, phone_number, email, city) VALUES (?, ?, ?, ?, ?, ?)");

if (!$stmt) {
    echo json_encode([
        "status" => "error",
        "message" => "Prepare statement failed: " . $conn->error
    ]);
    exit();
}

// Bind parameters: i = integer, s = string
$stmt->bind_param("isssss", $user_id, $name, $date_of_birth, $phone_number, $email, $city);

// Execute and respond
if ($stmt->execute()) {
    echo json_encode([
        "status" => "success",
        "message" => "Patient profile setup complete"
    ]);
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Failed to insert patient profile: " . $stmt->error
    ]);
}

$stmt->close();
$conn->close();
?>
