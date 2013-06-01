<?php
// Create connection
 $con=mysqli_connect("localhost","pepsi","cola","test");

$db = $_GET["db"];

ECHO "DB IS: " . $db . '\n';

// Check connection
if (mysqli_connect_errno($con))
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error() . '\n';
}
else  
{
  $sql = "SELECT * FROM " . $db . ";";
  
  print "Query to execute: " . $sql . "\n";

  //mysql_query($sql, $con);
  $result = mysql_query($sql, $con);

  if (!$result) {
    echo "DB Error, could not query the database\n";
    echo 'MySQL Error: ' . mysql_error();
    exit;
  }
  
  mysql_free_result($result);
}

echo "Now closing connection\n";

mysqli_close($con);

?> 
