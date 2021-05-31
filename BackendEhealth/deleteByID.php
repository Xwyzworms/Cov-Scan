<?php

    include_once("network.php");

    function response($success, $message) {

        echo json_encode(
            array("success"=>$success,
            "message" => $message)
        );
    }

    if (($_POST["id"] != "")) {

        $id = $_POST["id"];

        $query = "DELETE FROM users where id = '$id'";

        if(mysqli_query($connect,$query)) {
            response(true,"Success Delete user data");
        }
        else {
            response(false,"Failed Delete user data");
        }

    }