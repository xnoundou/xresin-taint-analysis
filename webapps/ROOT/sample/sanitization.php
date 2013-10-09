<?php
$req1 = $_GET["req1"];
$result = mysql_real_escape_string($req1);
printf ("Result: %f\n", $result);
?>
