<?php

    include_once("koneksi.php");
    if(!empty($_GET["id"])) {
        $id = $_GET["id"];
        $query = "SELECT * FROM user WHERE id = '$id";

        $get = mysqli_query($connect, $query);
        $data = array();
    }
    else {
        $query = "SELECT * FROM user";
    }
        if(mysqli_num_rows($get) > 0) {
            while($row = mysqli_fetch_assoc($get)) {
                $data[] = $row;
            }

            set_response(true,"Data Ditemukan",$data);
        }
        else {
            set_response(false,"Data Tidak ditemukan",$data);
        }
    }

    function set_response($isSucces,$message,$data) {
        $result = array(
            "isSucess" => $isSucces,
            "message" => $message,
            "data" => $data
        );
        echo json_encode($result);
    }