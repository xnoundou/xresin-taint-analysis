<?php
settype($f, "float");
settype($s, "string");

$s = $_GET["req1"];
$f = $s;
printf ("f: %f\n", $f);
?>
