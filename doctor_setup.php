<?php
header('Content-Type: application/json');
include 'dbcon.php';

$data = json_decode(file_get_contents("php://input"), true);

// Extract & validate
$user_id = $data['user_id'] ?? '';
$name = trim($data['name'] ?? '');
$specialization = trim($data['specialization'] ?? '');
$hospital_name = trim($data['hospital_name'] ?? '');
$phone_number = trim($data['phone_number'] ?? '');
$email = trim($data['email'] ?? '');

if (empty($user_id) || empty($name) || empty($specialization) || empty($hospital_name) || empty($phone_number) || empty($email)) {
    echo json_encode(["status" => "error", "message" => "All fields are required"]);
    exit();
}

// Insert into doctor_details table
$stmt = $conn->prepare("INSERT INTO doctor_details (user_id, name, specialization, hospital_name, phone_number, email) VALUES (?, ?, ?, ?, ?, ?)");
$stmt->bind_param("isssss", $user_id, $name, $specialization, $hospital_name, $phone_number, $email);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Doctor profile setup complete"]);
} else {
    echo json_encode(["status" => "error", "message" => "Failed to insert doctor profile"]);
}
?>
