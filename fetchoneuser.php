<?php
header("Content-Type: application/json");
include 'dbcon.php'; // your DB connection file

if (!isset($_GET['user_id'])) {
    echo json_encode(["status" => "error", "message" => "Missing user_id"]);
    exit;
}

$user_id = $_GET['user_id'];

// Fetch base user data
$user_stmt = $conn->prepare("SELECT user_id, full_name, email, role, created_at FROM users WHERE user_id = ?");
$user_stmt->bind_param("i", $user_id);
$user_stmt->execute();
$user_result = $user_stmt->get_result();
$user = $user_result->fetch_assoc();

if (!$user) {
    echo json_encode(["status" => "error", "message" => "User not found"]);
    exit;
}

// Fetch patient details
$patient_stmt = $conn->prepare("SELECT * FROM patient_details WHERE user_id = ?");
$patient_stmt->bind_param("i", $user_id);
$patient_stmt->execute();
$patient_result = $patient_stmt->get_result();
$patient = $patient_result->fetch_assoc();

// Fetch surgery details
$surgery_stmt = $conn->prepare("SELECT * FROM surgery_details WHERE user_id = ?");
$surgery_stmt->bind_param("i", $user_id);
$surgery_stmt->execute();
$surgery_result = $surgery_stmt->get_result();
$surgery = $surgery_result->fetch_assoc();

// Fetch health assessments
$health_stmt = $conn->prepare("SELECT * FROM health_assessments WHERE user_id = ?");
$health_stmt->bind_param("i", $user_id);
$health_stmt->execute();
$health_result = $health_stmt->get_result();
$health = $health_result->fetch_assoc();

// Fetch meal preference
$pref_stmt = $conn->prepare("SELECT preference FROM meal_preferences WHERE user_id = ?");
$pref_stmt->bind_param("i", $user_id);
$pref_stmt->execute();
$pref_result = $pref_stmt->get_result();
$preference_data = $pref_result->fetch_assoc();

$preference = $preference_data ? $preference_data['preference'] : null;
$meal_tracker_data = null;

// Fetch latest meal tracker based on preference
if ($preference === 'north_indian') {
    $meal_stmt = $conn->prepare("SELECT * FROM north_meal_tracker WHERE user_id = ? ORDER BY date DESC LIMIT 1");
    $meal_stmt->bind_param("i", $user_id);
    $meal_stmt->execute();
    $meal_result = $meal_stmt->get_result();
    $meal_tracker_data = $meal_result->fetch_assoc();
} elseif ($preference === 'south_indian') {
    $meal_stmt = $conn->prepare("SELECT * FROM south_meal_tracker WHERE user_id = ? ORDER BY date DESC LIMIT 1");
    $meal_stmt->bind_param("i", $user_id);
    $meal_stmt->execute();
    $meal_result = $meal_stmt->get_result();
    $meal_tracker_data = $meal_result->fetch_assoc();
}

// Final JSON response
$response = [
    "status" => "success",
    "user" => $user,
    "patient_details" => $patient,
    "surgery_details" => $surgery,
    "health_assessments" => $health,
    "meal_preference" => $preference,
    "latest_meal_tracker" => $meal_tracker_data
];

echo json_encode($response, JSON_PRETTY_PRINT);
?>
