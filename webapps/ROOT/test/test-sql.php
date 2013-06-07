<?php
// Create connection
 $con=mysqli_connect("localhost","pepsi","cola","test");

$desk = $_GET["table"];

#print "Table is: " . $desk . '\n';

// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error() . '\n';
  exit();
}
else  
{
  $sql = "SELECT * FROM " . $desk . ";";
  
  print "Query to execute: " . $sql . "\n";

  $result = mysqli_query($con, $sql);

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

echo "Now closing connection\n";

mysqli_close($con);

?> 
