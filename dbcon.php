<?php
$servername = "localhost";
$username = "root";
$password = "";
$db = "surgi_diet";

$conn = new mysqli($servername, $username, $password, $db);

// If connection fails, kill immediately with error JSON
if ($conn->connect_error) {
    http_response_code(500); // Internal Server Error
    die(json_encode([
        "status" => "error",
        "message" => "Database connection failed: " . $conn->connect_error
    ]));
}

// Otherwise do nothing — don't echo success!
?>
