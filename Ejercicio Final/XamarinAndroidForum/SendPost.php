<?php
  ini_set('display_errors', 'On');
    $con = mysqli_connect("localhost", "x","x", "x");
    if(isset($_POST["user_id"]) && isset($_POST["post"]))
    {
    $user_id = $_POST["user_id"];
    $post = $_POST["post"];
    }
    $statement = mysqli_prepare($con, "INSERT INTO post(user_id, post) VALUES (?, ?)");
    mysqli_stmt_bind_param($statement, "is", $user_id, $post);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
   echo json_encode($response);
  // print_r(json_encode($response));
?>