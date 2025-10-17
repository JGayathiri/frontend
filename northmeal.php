<?php
include 'dbcon.php';

$data = [
    // User 12 - 0 to 50% Recovery (Liquid diet)
    [12, '2025-08-04', 'Monday', 10, 'Low', '1/3', '3/8', 'Clear moong dal water', 45, 2, 0.4, 'yes', 'Rice starch with cumin', 90, 3.5, 1.1, 'yes', 'Light carrot soup', 65, 2.5, 1.0, 'Stable and responding', 'north'],
    [12, '2025-08-05', 'Tuesday', 15, 'Low', '2/3', '4/8', 'Light lauki broth', 50, 2.2, 0.5, 'yes', 'Masoor dal liquid with soft rice', 95, 4.0, 1.0, 'yes', 'Pumpkin soup', 70, 3.0, 1.1, 'Improving digestion', 'north'],
    [12, '2025-08-06', 'Wednesday', 20, 'Medium', '2/3', '5/8', 'Thin spinach soup', 55, 2.5, 0.6, 'yes', 'Soft khichdi (no oil)', 105, 4.2, 1.3, 'yes', 'Beetroot broth', 75, 3.5, 1.0, 'Patient more active', 'north'],
    [12, '2025-08-07', 'Thursday', 25, 'Medium', '2/3', '5/8', 'Moong dal soup', 60, 2.6, 0.7, 'yes', 'Rice porridge with ghee', 110, 4.5, 1.2, 'yes', 'Tomato rasam', 80, 3.0, 1.1, 'Eating better', 'north'],
    [12, '2025-08-08', 'Friday', 30, 'Medium', '3/3', '6/8', 'Bottle gourd soup', 65, 2.8, 0.9, 'yes', 'Curd rice', 115, 4.6, 1.4, 'yes', 'Spinach dal', 85, 3.8, 1.2, 'Consuming full meals', 'north'],
    [12, '2025-08-09', 'Saturday', 35, 'Medium', '3/3', '7/8', 'Carrot puree', 70, 3.0, 1.0, 'yes', 'Soft khichdi with veggies', 120, 5.0, 1.5, 'yes', 'Clear soup with lentils', 90, 4.0, 1.3, 'Improving stamina', 'north'],
    [12, '2025-08-10', 'Sunday', 40, 'Medium', '3/3', '7/8', 'Rice water with dal mash', 75, 3.2, 1.1, 'yes', 'Semisolid upma', 125, 5.2, 1.6, 'yes', 'Soup with paneer shreds', 95, 4.5, 1.4, 'Better energy levels', 'north'],

    // User 14 - Semi-solid diet
    [14, '2025-08-04', 'Monday', 55, 'Medium', '2/3', '6/8', 'Poha with vegetables (no oil)', 140, 5.0, 2.2, 'yes', 'Soft chapati + dal mash', 200, 8.0, 2.5, 'yes', 'Curd rice with beetroot', 180, 6.5, 2.1, 'Tolerating semi-solids', 'north'],
    [14, '2025-08-05', 'Tuesday', 60, 'Medium', '3/3', '6/8', 'Steamed suji upma (no oil)', 150, 6.0, 2.0, 'yes', 'Jeera rice with moong curry', 210, 7.5, 2.3, 'yes', 'Soft paneer bhurji + phulka', 190, 7.0, 2.4, 'Improved intake', 'north'],
    [14, '2025-08-06', 'Wednesday', 65, 'High', '3/3', '7/8', 'Besan chilla (no oil)', 160, 6.5, 2.2, 'yes', 'Soft vegetable pulao', 220, 9.0, 2.6, 'yes', 'Mixed dal soup + roti', 200, 8.0, 2.5, 'Active & eating well', 'north'],
    [14, '2025-08-07', 'Thursday', 67, 'High', '3/3', '7/8', 'Idli with tomato chutney', 170, 6.8, 2.3, 'yes', 'Khichdi with veggies', 230, 9.2, 2.7, 'yes', 'Palak soup + rice mash', 210, 8.5, 2.6, 'Continued progress', 'north'],
    [14, '2025-08-08', 'Friday', 70, 'High', '3/3', '8/8', 'Veg sandwich (grilled)', 180, 7.0, 2.5, 'yes', 'Vegetable poha', 240, 9.5, 2.9, 'yes', 'Soft roti with dal', 220, 9.0, 2.7, 'Full solid intake', 'north'],
    [14, '2025-08-09', 'Saturday', 72, 'High', '3/3', '8/8', 'Vegetable oats upma', 185, 7.2, 2.6, 'yes', 'Rice with chole', 250, 10, 3.0, 'yes', 'Chapati with paneer bhurji', 230, 9.2, 2.8, 'Very active', 'north'],
    [14, '2025-08-10', 'Sunday', 75, 'High', '3/3', '8/8', 'Roti + aloo sabzi', 190, 7.5, 2.7, 'yes', 'Pulav + raita', 260, 10.2, 3.1, 'yes', 'Roti with lauki curry', 240, 9.5, 2.9, 'Ready for normal diet', 'north'],

    // User 16 - Solid diet
    [16, '2025-08-04', 'Monday', 75, 'High', '3/3', '7/8', 'Oats porridge with fruits', 180, 6.5, 3.2, 'yes', 'Plain veg biryani (low spice)', 250, 10, 3.0, 'yes', 'Chapati with light palak paneer', 230, 9, 2.8, 'Eating solids well', 'north'],
    [16, '2025-08-05', 'Tuesday', 85, 'High', '3/3', '8/8', 'Vegetable poha + sprouts', 190, 7.0, 3.0, 'yes', 'Soft rajma chawal', 260, 11, 3.2, 'yes', 'Vegetable daliya', 240, 10, 3.0, 'Excellent energy', 'north'],
    [16, '2025-08-06', 'Wednesday', 90, 'High', '3/3', '8/8', 'Multigrain toast + paneer', 200, 8.0, 3.5, 'yes', 'Stuffed paratha (dry roast)', 270, 12, 3.3, 'yes', 'Chapati + mixed veg sabzi', 250, 10.5, 3.0, 'Normal recovery progress', 'north'],
    [16, '2025-08-07', 'Thursday', 92, 'High', '3/3', '8/8', 'Sprout salad + toast', 210, 8.5, 3.6, 'yes', 'Rajma rice + curd', 280, 12.2, 3.4, 'yes', 'Khichdi + palak mash', 260, 11, 3.2, 'Fully solid diet', 'north'],
    [16, '2025-08-08', 'Friday', 94, 'High', '3/3', '8/8', 'Upma + chutney', 220, 9.0, 3.7, 'yes', 'Vegetable rice', 290, 13, 3.6, 'yes', 'Phulka with dal', 270, 11.5, 3.4, 'Strength regained', 'north'],
    [16, '2025-08-09', 'Saturday', 97, 'High', '3/3', '8/8', 'Besan toast', 225, 9.2, 3.8, 'yes', 'Paneer rice bowl', 300, 13.5, 3.8, 'yes', 'Curd rice + veg curry', 280, 12, 3.5, 'Back to normal', 'north'],
    [16, '2025-08-10', 'Sunday', 100, 'High', '3/3', '8/8', 'Roti + mixed veg', 230, 9.5, 4.0, 'yes', 'Rice + dal + salad', 310, 14, 4.0, 'yes', 'Chapati + paneer curry', 290, 13, 3.9, 'Full recovery', 'north']
];


$stmt = $conn->prepare("INSERT INTO north_meal_tracker (
    user_id, date, day_of_week, recovery_percent, energy_level, meals_taken, water_intake,
    breakfast_name, breakfast_calories, breakfast_protein, breakfast_fiber, breakfast_taken,
    lunch_name, lunch_calories, lunch_protein, lunch_fiber, lunch_taken,
    dinner_name, dinner_calories, dinner_protein, dinner_fiber, dinner_taken,
    notes, region
) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

$stmt->bind_param(
    "issississidssiddsidsssss",
    $user_id, $date, $day, $recovery, $energy, $meals, $water,
    $b_name, $b_cal, $b_prot, $b_fib, $b_taken,
    $l_name, $l_cal, $l_prot, $l_fib, $l_taken,
    $d_name, $d_cal, $d_prot, $d_fib, $d_taken,
    $notes, $region
);

foreach ($data as $row) {
    [
        $user_id, $date, $day, $recovery, $energy, $meals, $water,
        $b_name, $b_cal, $b_prot, $b_fib, $b_taken,
        $l_name, $l_cal, $l_prot, $l_fib, $l_taken,
        $d_name, $d_cal, $d_prot, $d_fib, $d_taken,
        $notes, $region
    ] = $row;
    $stmt->execute();
}

echo json_encode(["status" => "success", "message" => "North Indian meal plan added for all users"]);
