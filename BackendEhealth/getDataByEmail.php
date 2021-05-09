<?php 


include_once("network.php");

function response($success, $message,$data) {
    echo json_encode(array("success" => $success,
                             "message" => $message,
                             "data" => $data));
}

if(!empty($_POST["email"])) {
    $email = $_POST["email"];
    $query = "SELECT * FROM users where email = '$email'";
    $dat = mysqli_query($connect, $query);
    $returned = array();
    if(mysqli_num_rows ($dat) > 0 ) {
        while($row = mysqli_fetch_assoc($dat)) {
            $returned[] = $row;
        }
        response(true,"success",$returned);
    }
    else {
        response(false,"Failed getting Email",$returned);
    }

}
else {
    response(false,"No data Available",0);
}