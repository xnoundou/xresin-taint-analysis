<?php
// Create connection
$con=mysqli_connect("localhost","pepsi","cola","test");

$desk = $_GET["table"];

echo "Entered Name: " . $_POST["fname"] . "\n";
echo "Entered Age: " . $_POST["age"] . "\n";

$k = 0;
$k += $_POST["age"];
printf("Here is the value of k: %d\n", $k);

// Check connection
if (mysqli_connect_errno()) {
  echo "Failed to connect to MySQL: " . mysqli_connect_error() . '\n';
  exit();
}
else {
  //Before sanitization
  $sql2 = "SELECT * FROM " . $desk . ";";
  $result = mysqli_query($con, $sql2);
  
  $desk = mysql_real_escape_string($desk);
  $sql = "SELECT * FROM " . $desk . ";";

  //Again, after sanitization
  $result = mysqli_query($con, $sql);

  if ($result) {
    echo "A test\n"
    printf("Select returned %d rows.\n", mysqli_num_rows($result));
    printf("Query was %s .\n", $sql);
    mysqli_free_result($result);
  }
  else {
    echo "DB Error, could not query the database\n";
    echo 'MySQL Error: ' . mysqli_error($con) . "\n";
    exit;
  }
  mysqli_free_result($result);
}

echo "Now closing connection\n";
mysqli_close($con);
phpinfo();
?> 
