<?php 


    include_once("network.php");

    function response($success,$message) {

        $res = array(
            "success" => $success,
            "message" => $message
        );

        echo json_encode(arr);

    }


    if ($_POST["id"] && $_POST["confidence"]) {
        $id = $_POST["id"];
        $confidence = $_POST["confidence"];

        $query = "UPDATE users set confidence = '$confidence' where id = '$id'";

        response(true,"Berhasil Update Confidence");
    
    }
    else {
        response(false,"Gagal Update Confidence");
    }