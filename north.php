<?php
header("Content-Type: application/json");
include 'dbcon.php'; // Make sure this connects as $conn

// Read JSON from request
$raw = file_get_contents("php://input");
$data = json_decode($raw, true);

// Validate JSON
if (json_last_error() !== JSON_ERROR_NONE) {
    echo json_encode(["status" => "error", "message" => "Invalid JSON"]);
    exit;
}

// Extract values from JSON (null if missing)
$user_id              = $data['user_id'] ?? null;
$date                 = $data['date'] ?? null;
$day_of_week          = $data['day_of_week'] ?? null;
$recovery_percent     = $data['recovery_percent'] ?? null;
$energy_level         = $data['energy_level'] ?? null;
$meals_taken          = $data['meals_taken'] ?? null;
$water_intake         = $data['water_intake'] ?? null;
$breakfast_name       = $data['breakfast_name'] ?? null;
$breakfast_calories   = $data['breakfast_calories'] ?? null;
$breakfast_protein    = $data['breakfast_protein'] ?? null;
$breakfast_fiber      = $data['breakfast_fiber'] ?? null;
$breakfast_taken      = $data['breakfast_taken'] ?? null;
$lunch_name           = $data['lunch_name'] ?? null;
$lunch_calories       = $data['lunch_calories'] ?? null;
$lunch_protein        = $data['lunch_protein'] ?? null;
$lunch_fiber          = $data['lunch_fiber'] ?? null;
$lunch_taken          = $data['lunch_taken'] ?? null;
$dinner_name          = $data['dinner_name'] ?? null;
$dinner_calories      = $data['dinner_calories'] ?? null;
$dinner_protein       = $data['dinner_protein'] ?? null;
$dinner_fiber         = $data['dinner_fiber'] ?? null;
$dinner_taken         = $data['dinner_taken'] ?? null;
$notes                = $data['notes'] ?? null;
$region               = $data['region'] ?? null;

// Prepare INSERT with placeholders (24 values — excluding id)
$sql = "INSERT INTO north_meal_tracker (
    user_id, date, day_of_week, recovery_percent, energy_level, meals_taken, water_intake,
    breakfast_name, breakfast_calories, breakfast_protein, breakfast_fiber, breakfast_taken,
    lunch_name, lunch_calories, lunch_protein, lunch_fiber, lunch_taken,
    dinner_name, dinner_calories, dinner_protein, dinner_fiber, dinner_taken,
    notes, region
) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

$stmt = $conn->prepare($sql);

// Bind parameters (i = integer, d = double, s = string)
// user_id (i), date (s), day_of_week (s), recovery_percent (i), energy_level (s), meals_taken (s), water_intake (s),
// breakfast_name (s), breakfast_calories (i), breakfast_protein (d), breakfast_fiber (d), breakfast_taken (s),
// lunch_name (s), lunch_calories (i), lunch_protein (d), lunch_fiber (d), lunch_taken (s),
// dinner_name (s), dinner_calories (i), dinner_protein (d), dinner_fiber (d), dinner_taken (s),
// notes (s), region (s)

$stmt->bind_param(
    "ississssiddsiddsdiddssss",
    $user_id, $date, $day_of_week, $recovery_percent, $energy_level, $meals_taken, $water_intake,
    $breakfast_name, $breakfast_calories, $breakfast_protein, $breakfast_fiber, $breakfast_taken,
    $lunch_name, $lunch_calories, $lunch_protein, $lunch_fiber, $lunch_taken,
    $dinner_name, $dinner_calories, $dinner_protein, $dinner_fiber, $dinner_taken,
    $notes, $region
);

// Execute query
if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Data inserted successfully"]);
} else {
    echo json_encode(["status" => "error", "message" => $stmt->error]);
}

$stmt->close();
$conn->close();
?>
