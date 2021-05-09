<?php

include_once("network.php");

function set_response($success, $message){
    $arr = array(
            "success" => $success,
            "message" => $message
    );
    
    echo json_encode($arr);
}

if(
    !empty($_POST["nama"]) &&
    !empty($_POST["email"]) &&
    !empty($_POST["password"]) &&
    !empty($_POST["tgl_lahir"])
) {
    $nama =$_POST["nama"];
    $email = $_POST["email"];
    $password = $_POST["password"];
    $tanggal = $_POST["tgl_lahir"];

    $query = "INSERT INTO users (nama,email,tgl_lahir,password) values ('$nama','$email','$tanggal','$password')";

    if(mysqli_query($connect,$query)) {
        set_response(true,"Sucess Inserting data");
    }
    else {
        set_response(false,"Failed Inserting data");
    }
}

else {
    set_response(false,"Null Values Detected");
}