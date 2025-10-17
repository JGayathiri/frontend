<?php
$host = "localhost";
$db = "surgi_diet"; // Replace with your DB name
$user = "root";
$pass = "";
$conn = new mysqli($host, $user, $pass, $db);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Get all patients
$sql = "
SELECT 
    u.user_id, u.full_name, u.email, u.role,
    pd.name AS patient_name, pd.date_of_birth, pd.phone_number, pd.city,
    mp.preference,
    IF(mp.preference = 'south_indian',
        (SELECT notes FROM south_meal_tracker smt WHERE smt.user_id = u.user_id ORDER BY smt.date DESC LIMIT 1),
        (SELECT notes FROM north_meal_tracker nmt WHERE nmt.user_id = u.user_id ORDER BY nmt.date DESC LIMIT 1)
    ) AS latest_notes,
    IF(mp.preference = 'south_indian',
        (SELECT lunch_name FROM south_meal_tracker smt WHERE smt.user_id = u.user_id ORDER BY smt.date DESC LIMIT 1),
        (SELECT lunch_name FROM north_meal_tracker nmt WHERE nmt.user_id = u.user_id ORDER BY nmt.date DESC LIMIT 1)
    ) AS latest_lunch
FROM users u
JOIN patient_details pd ON u.user_id = pd.user_id
JOIN meal_preferences mp ON u.user_id = mp.user_id
WHERE u.role = 'patient'
";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        echo "<pre>";
        print_r($row);
        echo "</pre>";
    }
} else {
    echo "No patients found.";
}

$conn->close();
?>
