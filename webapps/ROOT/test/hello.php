<html>
 <head>
 <title>A File Upload Script</title>
 </head>
 <body>
 <div>
 <?php
echo "Index page of Hello\n";
echo "What is happening, I can't see what I am looking for!\n";

$i = 6;

if ($i > 0){
  print "A value inferior or equals 3\n";
}
else{
  echo "A value superior to 3\n";
}
   $first = $_POST['firstname'];
   $last = $_POST['lastname'];

   echo("First name: " . $first . "<br />\n");
   echo("Last name: " . $last . "<br />\n");

 ?>
 </div>
<form action="myform5.php" method="post">
   <p>First name: <input type="text" name="firstname" /></p>
   <p>Last name: <input type="text" name="lastname" /></p>
   <input type="submit" name="submit" value="Submit" />
</form>
 </body>
 </html>
