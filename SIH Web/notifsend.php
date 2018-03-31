<?php
$tokensx = $_POST['tokens'];
$msgx = $_POST['notif'];
//echo $tokensx;
// if(strpos($tokensx,"///"))
// {
  $tokens=explode("///",$tokensx);

function send_notification($tokens,$message)
{
	$url = 'https://fcm.googleapis.com/fcm/send';
	$fields = array(
		'registration_ids' => $tokens,
		'data' => $message
		);
	$headers = array(
		'Authorization: key=AIzaSyAwGTjf3AFuU8uHKuTN-7n9545wY3Sqrdk',
		'Content-Type: application/json'
		);

	$ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
    $result=curl_exec($ch);
    if($result === FALSE)
    {
    	die('Curl failed:'. curl_error($ch));
    }
    curl_close($ch);

echo json_encode($fields);
    return $result;

}

//  echo "<script>console.log( 'Debug Objects: " . $tokens[0] . "' );</script>";

   $message=array("msg" => $msgx);
   $message_status=send_notification($tokens,$message);
   echo $message_status;



?>
