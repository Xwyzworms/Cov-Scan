<?php


include_once("network.php");


function response($success, $message) {
    echo json_encode( array(
        "success" => $success,
        "message" => $message
    ));
}
if(!empty($_POST["password"]) && !empty($_POST["id"])) {
    $id = $_POST["id"];
    $pass = $_POST["password"];

    $query = "UPDATE users set password = '$pass' where id = '$id'";
    $data = array();
    $res = mysqli_query($connect,$query);

    if($res) {
        response(true,"Sucessfully Update Password");
    }
    
}
else {
    response(false,"Failed updating password");
}