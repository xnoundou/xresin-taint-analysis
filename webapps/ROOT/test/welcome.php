Welcome <?php echo $_POST["fname"]; ?>!<br>
You are <?php echo $_POST["age"]; ?> years old.<br/>

Sample value is: <?php echo $a = $_GET["sample"] ?><br/>
Sample value ADD: 
<?php 
$b=$a-5;
$c = 3 * 7;
$d = $b - 2;
echo $b;
echo $c;
echo $d ?> 
