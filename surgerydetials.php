<?php
header("Content-Type: application/json");
include "dbcon.php"; // Your DB connection file

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    echo json_encode(["status" => "error", "message" => "Only POST method allowed"]);
    exit;
}

$data = json_decode(file_get_contents("php://input"), true);

// Extract input
$user_id = $data['user_id'] ?? null;
$surgery_name = $data['surgery_name'] ?? null;
$surgery_type = $data['surgery_type'] ?? null;
$surgery_date = $data['surgery_date'] ?? null;
$doctor_or_hospital_name = $data['doctor_or_hospital_name'] ?? null;
$dietary_instructions = $data['dietary_instructions'] ?? null;

// Validate required fields
if (!$user_id || !$surgery_name || !$surgery_type || !$surgery_date || !$doctor_or_hospital_name) {
    echo json_encode(["status" => "error", "message" => "Missing required fields"]);
    exit;
}

// Validate ENUM values for surgery_type
$allowed_types = ['Cardio', 'General Surgery', 'Gynecology', 'Dental', 'Ortho'];
if (!in_array($surgery_type, $allowed_types)) {
    echo json_encode(["status" => "error", "message" => "Invalid surgery type"]);
    exit;
}

// Insert into database
$stmt = $conn->prepare("INSERT INTO surgery_details (user_id, surgery_name, surgery_type, surgery_date, doctor_or_hospital_name, dietary_instructions) VALUES (?, ?, ?, ?, ?, ?)");
$stmt->bind_param("isssss", $user_id, $surgery_name, $surgery_type, $surgery_date, $doctor_or_hospital_name, $dietary_instructions);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Surgery details saved"]);
} else {
    echo json_encode(["status" => "error", "message" => "Failed to save surgery details"]);
}

?>
