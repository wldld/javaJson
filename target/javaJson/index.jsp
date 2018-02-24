<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>


<html>
<head>
    <title></title>
    <style type="text/css">
        #imgs {
            width: 60%;
            margin: auto 20%;
        }

        #imgs p{
            margin: 10px;
            float: right;
            background-image: url("/image/redfork.jpg");
        }

        img {
            width: 200px;
            height: 200px;
        }
        .opacity-black { width: 200px; height: 200px; background-color: #000; }
        .opacity-black img{ transition: opacity .5s; }
    </style>
    <script type="text/javascript" src="https://lib.sinaapp.com/js/jquery/2.0.2/jquery-2.0.2.min.js"></script>
</head>

<body>
<div id="imgs"></div>
<div style="margin: auto 20%" align="center">
    <button onclick="process()">下一批</button>
</div>

</body>

<script type="text/javascript">
    //ajax获取后台数据，动态渲染到页面
    function render(){
        $.ajax({
            url: "http://localhost:8080/servlet",
            success: function(res){
                //动态渲染到页面

                var json = eval(res)
                // for (var i = JsonArray.length - 1; i >= 0; i--) {
                //     $("#imgs").remove();//先清空
                //     $("#imgs").html()//再重新渲染
                // }
                for(var i=json.length-1;i>=0;i--){
                    $("#imgs").append(
                        $("<p>").addClass("opacity-black").append($("<img>").attr("src", json[i].img_url))
                    );

                }

            }
        });
    }

    //将处理的数据发送到后台，并重新渲染页面
    function process(){
        // sendJson();
        $("#imgs").html("");
        //重新渲染页面
        render();
    }
    // function sendJson() {
    //     $("#imgs")
    // }
    //
    // $(function(){
    //     render();
    // })

    $("#imgs").on("click","p img",function(e){
        var el = $(e.currentTarget);
        if (el.attr("status")) {
            el.css("opacity","1");
            el.attr("status", "")
        } else {
            el.css("opacity","0.4");
            el.attr("status", "1");
        }
    })

</script>

</html>
