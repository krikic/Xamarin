<?php
		$con = mysqli_connect("localhost", "x","x", "x");
		
		$login = $_POST["login"];
		$password = $_POST["password"];
		
		$statement = mysqli_prepare($con, "SELECT user_id, login, password  FROM user WHERE login = ? AND password = ?");
		mysqli_stmt_bind_param($statement,"ss",$login,$password);
		mysqli_stmt_execute($statement);
		
		mysqli_stmt_store_result($statement);
		mysqli_stmt_bind_result($statement, $user_id, $login, $password);
		
		$response = array();
		$response["success"] = false;
		
		while(mysqli_stmt_fetch($statement)){
		$response["success"] = true;
		$response["user_id"] = $user_id;
		$response["login"] = $login;
		$response["password"] = $password;
		}
		echo json_encode($response);
?>
		