<?php
include 'dbcon.php';

header('Content-Type: application/json');

// Get user_id from request
$user_id = $_GET['user_id'] ?? null;

if (!$user_id) {
    echo json_encode(['status' => 'error', 'message' => 'Missing user_id']);
    exit();
}

// Step 1: Fetch patient details
$stmt = $conn->prepare("SELECT name, date_of_birth, phone_number, email FROM patient_details WHERE user_id = ?");
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows == 0) {
    echo json_encode(['status' => 'error', 'message' => 'Patient not found']);
    exit();
}

$patient = $result->fetch_assoc();
$stmt->close();

// Step 2: Get meal preference
$stmt = $conn->prepare("SELECT preference FROM meal_preferences WHERE user_id = ?");
$stmt->bind_param("i", $user_id);
$stmt->execute();
$res = $stmt->get_result();

if ($res->num_rows == 0) {
    echo json_encode(['status' => 'error', 'message' => 'Meal preference not found']);
    exit();
}

$preference = $res->fetch_assoc()['preference'];
$stmt->close();

// Step 3: Get weekly meals from correct tracker
$table = ($preference === 'south_indian') ? 'south_meal_tracker' : 'north_meal_tracker';

$stmt = $conn->prepare("SELECT * FROM $table WHERE user_id = ?");
$stmt->bind_param("i", $user_id);
$stmt->execute();
$res = $stmt->get_result();

$meals = [];
while ($row = $res->fetch_assoc()) {
    $meals[] = $row;
}
$stmt->close();

// Final response
$response = [
    'status' => 'success',
    'patient' => $patient,
    'preference' => $preference,
    'weekly_meals' => $meals
];

echo json_encode($response);
?>
