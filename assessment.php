<?php
header("Content-Type: application/json");
include "dbcon.php"; // database connection file

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    echo json_encode(["status" => "error", "message" => "Only POST method allowed"]);
    exit;
}

// Get POST data
$data = json_decode(file_get_contents("php://input"), true);

// Validate required fields
$required_fields = ['user_id', 'blood_type', 'allergies', 'height_cm', 'weight_kg', 'blood_pressure'];

foreach ($required_fields as $field) {
    if (empty($data[$field])) {
        echo json_encode(["status" => "error", "message" => "Missing field: $field"]);
        exit;
    }
}

// Prepare SQL
$stmt = $conn->prepare("
    INSERT INTO health_assessments (user_id, blood_type, allergies, height_cm, weight_kg, blood_pressure)
    VALUES (?, ?, ?, ?, ?, ?)
");

$stmt->bind_param(
    "issdds",
    $data['user_id'],
    $data['blood_type'],
    $data['allergies'],
    $data['height_cm'],
    $data['weight_kg'],
    $data['blood_pressure']
);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Health assessment saved successfully"]);
} else {
    echo json_encode(["status" => "error", "message" => "Failed to save health assessment"]);
}
?>
