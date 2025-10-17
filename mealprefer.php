<?php
header("Content-Type: application/json");
include "dbcon.php";

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    echo json_encode(["status" => "error", "message" => "Only POST method allowed"]);
    exit;
}

$data = json_decode(file_get_contents("php://input"), true);

if (!isset($data['user_id']) || !isset($data['preference'])) {
    echo json_encode(["status" => "error", "message" => "Missing required fields"]);
    exit;
}

$user_id = $data['user_id'];
$preference = $data['preference'];

$allowed_preferences = ['north_indian', 'south_indian'];
if (!in_array($preference, $allowed_preferences)) {
    echo json_encode(["status" => "error", "message" => "Invalid meal preference"]);
    exit;
}

$stmt = $conn->prepare("INSERT INTO meal_preferences (user_id, preference) VALUES (?, ?)");
$stmt->bind_param("is", $user_id, $preference);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Meal preference saved"]);
} else {
    echo json_encode(["status" => "error", "message" => $stmt->error]);
}

$stmt->close();
$conn->close();
?>
