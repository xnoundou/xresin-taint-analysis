<html>
 <head>
 <title>A File Upload Script</title>
 </head>
 <body>
 <div>
 <?php
 if ( isset( $_FILES['fupload'] ) ) {

     print "name: ".     $_FILES['fupload']['name']       ."<br />";
     print "size: ".     $_FILES['fupload']['size'] ." bytes<br />";
     #print "temp name: ".$_FILES['fupload']['tmp_name']   ."<br />";
     print "type: ".     $_FILES['fupload']['type']       ."<br />";
     print "error: ".    $_FILES['fupload']['error']      ."<br />";

     if ( $_FILES['fupload']['type'] == "image/gif" ) {

         $source = $_FILES['fupload']['tmp_name'];
         $target = "upload/".$_FILES['fupload']['name'];
         move_uploaded_file( $source, $target );// or die ("Couldn't copy");
         $size = getImageSize( $target );

         $imgstr = "<p><img width=\"$size[0]\" height=\"$size[1]\" ";
         $imgstr .= "src=\"$target\" alt=\"uploaded image\" /></p>";

         print $imgstr;
     }
 }
 ?>
 </div>
 <form enctype="multipart/form-data"
     action="<?php print $_SERVER['PHP_SELF']?>" method="post">
 <p>
 <input type="hidden" name="MAX_FILE_SIZE" value="102400" />
 <input type="file" name="fupload" /><br/>
 <input type="submit" value="upload!" />
 </p>
 </form>
 </body>
 </html>
