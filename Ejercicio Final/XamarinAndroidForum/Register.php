<?php
  ini_set('display_errors', 'On');
    $con = mysqli_connect("localhost", "x","x", "x");
    if(isset($_POST["login"]) && isset($_POST["password"]))
    {
    $login = $_POST["login"];
    $password = $_POST["password"];
    }
    $statement = mysqli_prepare($con, "INSERT INTO user(login, password) VALUES (?, ?)");
    mysqli_stmt_bind_param($statement, "ss", $login, $password);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
   echo json_encode($response);
  // print_r(json_encode($response));
?>