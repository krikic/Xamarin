<?php

/**
 * Created by Alain Serge.
 * Project Name: User Login/signin ios
 * File Name imageUpload.php
 * Date: 06/10/15
 *  * Skype:sergealain08
 */


 $user_id = $_POST["userId"];
 $target_dir = "http://localhost.com/profile-pictures/" . $user_id;
 
if(!file_exists($target_dir)) 
{  
    mkdir($target_dir, 0744, true);
} 

$target_dir = $target_dir . "/" . basename($_FILES["file"]["name"]);

if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_dir)) {
    echo json_encode([
    	"message" => "The file ". basename( $_FILES["file"]["name"]). " has been uploaded.",
    	"status" => "OK",
	"userId" => $user_id
    ]);

} else {

	echo json_encode([
		"message" => "Sorry, there was an error uploading your file. Try Again",
		"status" => "Error",
	        "userId" => $user_id
	]);
}
 
?>
