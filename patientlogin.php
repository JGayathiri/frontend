<?php
header("Content-Type: application/json");
include "dbcon.php";

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    echo json_encode(["status" => "error", "message" => "Only POST method allowed"]);
    exit;
}

// Validate required fields
$required_fields = ['user_id', 'name', 'dob', 'phone_number', 'city', 'region'];
foreach ($required_fields as $field) {
    if (!isset($_POST[$field])) {
        echo json_encode(["status" => "error", "message" => "Missing field: $field"]);
        exit;
    }
}

$user_id = $_POST['user_id'];
$name = $_POST['name'];
$dob = $_POST['dob'];
$phone_number = $_POST['phone_number'];
$city = $_POST['city'];
$region = $_POST['region'];

$stmt = $conn->prepare("INSERT INTO patient_details (user_id, name, dob, phone_number, city, region) VALUES (?, ?, ?, ?, ?, ?)");
$stmt->bind_param("isssss", $user_id, $name, $dob, $phone_number, $city, $region);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Patient details inserted successfully"]);
} else {
    echo json_encode(["status" => "error", "message" => "Database error: " . $stmt->error]);
}

$stmt->close();
$conn->close();
?>
