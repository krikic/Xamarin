<?php


include 'header.php';
?>
<div class='container-fluid' style="margin-top: 172px;">
		<div class='row'>
		<div class='col-md-2'></div>
		<div class='col-md-8'>
			<div class="panel panel-default">
  				<div class="panel-heading"><h1>Thank you!</h1></div>
  				<div class="panel-body">
    				
              Hello 
              <?php
              include_once 'include/functions.php';

$user = new User();
$uid = $_SESSION['uid'];
$sql="SELECT * FROM customer_order WHERE uid='$uid'";
	$run_query=mysql_query($sql);
	$row=mysql_fetch_array($run_query);
	$trid=$row['tr_id'];

$user->get_fullname($uid);

             ?>, your payment is done successful.
             <br>Your Transaction ID is <?php echo $trid; ?> 
    				 
    				<br>You can continue with your shopping.
    				<p></p>
                                
    				<a onclick="window.location='profile.php'" href="#" class='btn btn-success btn-lg'>Back to store</a>
  				</div>
			</div>
		<div class='col-md-2'></div>
	</div>

	</div>

	</div>
	
<?php
include 'footer.php';
?>