<?php
header("Content-Type: application/json");
include 'dbcon.php';

$base_date = strtotime("2025-08-05");

$users = [
    11 => [
        "recovery_percent" => 10,
        "energy_level" => "Low",
        "meals_taken" => "1/3",
        "water_intake" => "3/8",
        "region" => "south",
        "notes" => [
            "Responding well to liquids.", "Hydrated, good response", "Protein intake improved",
            "Accepting meals", "Digestion stable", "Energy slightly better", "Ready for soft solids next"
        ],
        "meals" => [
            ["Thin dal water", 50, 2, 0.5, "yes", "Rice gruel with moong broth", 100, 4, 1.2, "yes", "Vegetable soup", 70, 3, 1.0, "yes"],
            ["Barley water", 40, 1.5, 0.3, "yes", "Carrot soup", 80, 3, 1.0, "yes", "Spinach clear soup", 60, 2.5, 1.0, "yes"],
            ["Moong broth", 45, 2, 0.6, "yes", "Rice starch with veg puree", 90, 3.5, 1.1, "yes", "Light tomato soup", 65, 3, 1.0, "yes"],
            ["Clear veg soup", 50, 2, 0.5, "yes", "Mashed lauki gruel", 95, 4, 1.3, "yes", "Beetroot broth", 70, 3, 1.1, "yes"],
            ["Rice kanji", 55, 2.5, 0.7, "yes", "Moong dal soup", 85, 3.8, 1.2, "yes", "Pumpkin clear soup", 65, 2.8, 1.0, "yes"],
            ["Coconut water", 30, 0.5, 0.2, "yes", "Curd rice mashed + cumin", 110, 4.2, 1.4, "yes", "Lemon clear soup", 50, 2, 0.8, "yes"],
            ["Jeera water", 35, 0.5, 0.2, "yes", "Mashed carrot rice", 100, 4, 1.3, "yes", "Cabbage broth", 60, 2.6, 1.0, "yes"]
        ]
    ],
    13 => [
        "recovery_percent" => 60,
        "energy_level" => "Medium",
        "meals_taken" => "2/3",
        "water_intake" => "5/8",
        "region" => "south",
        "notes" => [
            "Eating semisolid food, moderate recovery.", "Good appetite", "Handles soft textures well",
            "Intake improved", "Recovery on track", "Stable digestion", "Preparing for solids"
        ],
        "meals" => [
            ["Ragi porridge", 120, 4, 2.0, "yes", "Soft idli with carrot puree", 180, 6, 2.5, "yes", "Broken rice upma", 160, 5, 2.3, "no"],
            ["Idiyappam with coconut milk", 130, 4.2, 2.1, "yes", "Soft curd rice", 190, 6.5, 2.6, "yes", "Steamed veg mash", 170, 5.2, 2.4, "yes"],
            ["Rava upma (soft)", 125, 4.1, 2.0, "yes", "Lauki khichdi", 185, 6.2, 2.5, "yes", "Light veg rice", 160, 5, 2.3, "yes"],
            ["Moong dosa (no spice)", 135, 4.3, 2.2, "yes", "Semiya upma with vegs", 180, 6, 2.4, "yes", "Spinach dal rice", 175, 5.5, 2.6, "yes"],
            ["Banana porridge", 110, 3.5, 1.8, "yes", "Mashed mixed veg + rice", 170, 6, 2.3, "yes", "Carrot-moong dal", 160, 5.2, 2.2, "yes"],
            ["Steamed idli", 140, 4.6, 2.0, "yes", "Dalia khichdi", 195, 6.7, 2.7, "yes", "Tomato veg mash", 165, 5.4, 2.3, "yes"],
            ["Rice sevai", 130, 4.1, 2.0, "yes", "Pumpkin rice mash", 180, 6, 2.4, "yes", "Cabbage-carrot stir mash", 160, 5, 2.2, "yes"]
        ]
    ],
    15 => [
        "recovery_percent" => 85,
        "energy_level" => "High",
        "meals_taken" => "3/3",
        "water_intake" => "8/8",
        "region" => "south",
        "notes" => [
            "Back to light solids, good recovery.", "Energy level high", "Balanced intake",
            "Normal hunger pattern", "No digestion issues", "Regular solid meals taken", "Ready for full diet"
        ],
        "meals" => [
            ["Vegetable upma", 200, 6, 3.0, "yes", "Plain rice with sambar and boiled vegetables", 300, 10, 4.0, "yes", "Chapati with vegetable kurma", 280, 9, 3.5, "yes"],
            ["Idli with sambar", 180, 5, 2.5, "yes", "Vegetable biryani (light)", 310, 11, 4.2, "yes", "Chapati with dal", 270, 8.5, 3.3, "yes"],
            ["Rava dosa with coconut chutney", 220, 6.5, 3.1, "yes", "Lemon rice with dal", 290, 10.5, 4.1, "yes", "Rice with mixed veg curry", 300, 9.2, 3.8, "yes"],
            ["Poha with peanuts", 210, 6, 3.0, "yes", "Curd rice with cucumber", 280, 9, 3.9, "yes", "Chapati with spinach curry", 260, 8, 3.6, "yes"],
            ["Whole wheat bread with boiled egg", 230, 7, 3.2, "yes", "Vegetable pulao", 320, 11, 4.5, "yes", "Mixed dal with rice", 310, 10, 3.7, "yes"],
            ["Oats porridge with banana", 190, 5.5, 2.8, "yes", "Chickpea curry with rice", 300, 10.2, 4.3, "yes", "Roti with lauki sabzi", 275, 8.7, 3.4, "yes"],
            ["Upma with vegetables", 210, 6.2, 3.0, "yes", "Khichdi with ghee", 310, 10.5, 4.2, "yes", "Paratha with curd", 290, 9.5, 3.6, "yes"]
        ]
    ]
];

$stmt = $conn->prepare("INSERT INTO south_meal_tracker (
    user_id, date, day_of_week, recovery_percent, energy_level, meals_taken, water_intake,
    breakfast_name, breakfast_calories, breakfast_protein, breakfast_fiber, breakfast_taken,
    lunch_name, lunch_calories, lunch_protein, lunch_fiber, lunch_taken,
    dinner_name, dinner_calories, dinner_protein, dinner_fiber, dinner_taken,
    notes, region
) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

if (!$stmt) {
    echo json_encode(["status" => "error", "message" => "Prepare failed: " . $conn->error]);
    exit();
}

foreach ($users as $user_id => $data) {
    foreach ($data['meals'] as $i => $meal) {
        $date = date('Y-m-d', strtotime("+{$i} days", $base_date));
        $day = date('l', strtotime($date));
        list(
            $breakfast_name, $breakfast_calories, $breakfast_protein, $breakfast_fiber, $breakfast_taken,
            $lunch_name, $lunch_calories, $lunch_protein, $lunch_fiber, $lunch_taken,
            $dinner_name, $dinner_calories, $dinner_protein, $dinner_fiber, $dinner_taken
        ) = $meal;

        $stmt->bind_param(
            "issississidssisdsssisdss",
            $user_id, $date, $day, $data['recovery_percent'], $data['energy_level'], $data['meals_taken'], $data['water_intake'],
            $breakfast_name, $breakfast_calories, $breakfast_protein, $breakfast_fiber, $breakfast_taken,
            $lunch_name, $lunch_calories, $lunch_protein, $lunch_fiber, $lunch_taken,
            $dinner_name, $dinner_calories, $dinner_protein, $dinner_fiber, $dinner_taken,
            $data['notes'][$i], $data['region']
        );

        $stmt->execute();
    }
}

$stmt->close();
$conn->close();
echo json_encode(["status" => "success", "message" => "All weekly meal plans inserted successfully"]);
?>
