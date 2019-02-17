<?php
		$con = mysqli_connect("localhost", "x","x", "x");
	
		//$statement = mysqli_prepare($con, "SELECT user_id, post FROM post ORDER BY post_id DESC limit 20");
		
		//mysqli_stmt_bind_param($statement,"is",$user_id,$post);
		//mysqli_stmt_execute($statement);
		
		//mysqli_stmt_store_result($statement);
		//mysqli_stmt_bind_result($statement, $post_id, $user_id, $post);
		
		//$response = array();
		$response["success"] = true;
		$sql="SELECT u.login, p.post FROM post p JOIN user u USING (user_id) ORDER BY p.post_id DESC limit 20";

	
	$result=mysqli_query($con,$sql);

// Fetch all
	//	mysqli_fetch_all($result,MYSQLI_ASSOC);

// Free result set
	//	mysqli_free_result($result);
		
	//	mysqli_close($con);
		
		$arr = array();

		$totalrecords = mysqli_num_rows($result);
		while($row = mysqli_fetch_assoc($result)) {
			$arr[] = $row;
		}
		
		//$result = mysqli_fetch_all($con->statement($statement), MYSQLI_ASSOC);
		
		//while(mysqli_stmt_fetch($statement)){
		//$response["success"] = true;
		//$response["user_id"] = $user_id;
		//$response["login"] = $login;
		//$response["password"] = $password;
		//}
		$json = array($arr);
		echo json_encode($json);
?>