<?php


    include_once("network.php");

    function set_response($success,$message,$data) {

        $arr = array(
            "success" => $success,
            "message" => $message,
            "data" => $data
        );

        echo json_encode(arr);
    }


    if(!empty($_POST["id"])) {
        $id = $_POST["id"];
        $query ="SELECT * FROM users where id = '$id'";
        $data = array();
        $mysqliRes = (mysqli_query($connect,$query));

        if(mysqli_num_rows($mysqliRes) > 0 ) {
            while($row = mysqli_fetch_assoc($get)) {
                $data[] = $row;
            }
            set_response(true,"Success",$data);

        }
        else {
            set_response(false,"failed",$data);    
        }
    }