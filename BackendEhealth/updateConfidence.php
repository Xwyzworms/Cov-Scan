<?php 


    include_once("network.php");

    function response($success,$message) {

        $res = array(
            "success" => $success,
            "message" => $message
        );

        echo json_encode($res);

    }


    if (!empty($_POST["id"]) && !empty($_POST["confidence"])) {
        $id = $_POST["id"];
        $confidence = $_POST["confidence"];

        $query = "UPDATE users set confidience = '$confidence' where id = '$id'";

        if(mysqli_query($connect,$query)){
    
            response(true,"Berhasil Update Confidence");
        }
    }
    else {
        response(false,"Gagal Update Confidence");
    }