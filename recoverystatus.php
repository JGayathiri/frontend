<?php
header("Content-Type: application/json");
include "dbcon.php";

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    echo json_encode(["status" => "error", "message" => "Only GET method allowed"]);
    exit;
}

$user_id = $_GET['user_id'] ?? null;

if (!$user_id) {
    echo json_encode(["status" => "error", "message" => "Missing user ID"]);
    exit;
}

// Fetch surgery date from DB
$query = "SELECT surgery_date FROM surgery_details WHERE user_id = ?";
$stmt = $conn->prepare($query);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

if ($row = $result->fetch_assoc()) {
    $surgery_date = new DateTime($row['surgery_date']);
    $today = new DateTime();
    $days_passed = $today->diff($surgery_date)->days;

    // Recovery percentage logic
    if ($days_passed <= 5) $recovery = 10;
    elseif ($days_passed <= 10) $recovery = 50;
    elseif ($days_passed <= 20) $recovery = 70;
    elseif ($days_passed <= 30) $recovery = 90;
    else $recovery = 100;

    echo json_encode([
        "status" => "success",
        "user_id" => $user_id,
        "days_passed" => $days_passed,
        "recovery_percent" => $recovery
    ]);
} else {
    echo json_encode(["status" => "error", "message" => "No surgery data found for this user"]);
}
?>
