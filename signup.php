<?php
header('Content-Type: application/json');
include 'dbcon.php'; // Replace with your DB connection file

// Get raw input
$data = json_decode(file_get_contents("php://input"), true);

// Check for required fields
if (
    !isset($data['full_name']) ||
    !isset($data['email']) ||
    !isset($data['password']) ||
    !isset($data['confirm_password']) ||
    !isset($data['role'])
) {
    echo json_encode(["status" => "error", "message" => "All fields are required"]);
    exit();
}

$full_name = trim($data['full_name']);
$email = trim($data['email']);
$password = $data['password'];
$confirm_password = $data['confirm_password'];
$role = strtolower(trim($data['role']));

// Validate
if ($password !== $confirm_password) {
    echo json_encode(["status" => "error", "message" => "Passwords do not match"]);
    exit();
}

if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
    echo json_encode(["status" => "error", "message" => "Invalid email format"]);
    exit();
}

if (!in_array($role, ['patient', 'doctor'])) {
    echo json_encode(["status" => "error", "message" => "Invalid role"]);
    exit();
}

// Check if email exists
$stmt = $conn->prepare("SELECT user_id FROM users WHERE email = ?");
$stmt->bind_param("s", $email);
$stmt->execute();
$stmt->store_result();
if ($stmt->num_rows > 0) {
    echo json_encode(["status" => "error", "message" => "Email already registered"]);
    exit();
}
$stmt->close();

// Hash password
$hashed_password = password_hash($password, PASSWORD_DEFAULT);

// Insert user
$stmt = $conn->prepare("INSERT INTO users (full_name, email, password, role) VALUES (?, ?, ?, ?)");
$stmt->bind_param("ssss", $full_name, $email, $hashed_password, $role);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "User registered successfully"]);
} else {
    echo json_encode(["status" => "error", "message" => "Registration failed"]);
}
$stmt->close();
$conn->close();
?>
