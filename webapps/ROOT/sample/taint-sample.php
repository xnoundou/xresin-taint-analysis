<?php
$age = $_POST["age"];
$name = $_POST["fname"];
$method = $_GET["database"];
$db = "test";
$table = "desk";

echo "Entered Name: " . $name . "\n";
echo "Entered Age: " . $age . "\n";

$birth_date = date("Y") - $age;
printf("%s was born in: %d\n", $name, $birth_date);

$con=mysqli_connect("localhost","pepsi","cola","test");

if (mysqli_connect_errno()) {
  echo "Failed to connect to MySQL: " . mysqli_connect_error() . '\n';
  exit();
}
else {
  $query = "select * from " . $table . " where age=" . $age . ";";
  echo "query to database: " . $query . "\n";
  $result = mysqli_query($con, $query);
  
  $name = mysql_real_escape_string($name);
  $query = "select * from " . $table . " where name='" . $name . "';";

  $result = mysqli_query($con, $query);

  if ($result) {
    printf("Select returned %d rows.\n", mysqli_num_rows($result));
    mysqli_free_result($result);
  }
  else {
    echo "DB Error, could not query the database\n";
    echo 'MySQL Error: ' . mysqli_error($con) . "\n";
    exit;
  }
  mysqli_free_result($result);
}
mysqli_close($con);
?> 
