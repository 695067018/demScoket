<form action="login" method="post">
    接收者用户:<input type="text" id="name" name="name" value="<%=session.getAttribute("loginName") %>" />
    <p>
        消息内容:<input type="text" id="msg" name="msg" />
    <p>
        <input type="button" id="send" value="发送" />
</form>
<script src="/websocket/jquery.js"></script>
<script type=text/javascript>

    $("#send").click(function(){
        $.post("send2user",
            {
                name: $('#name').val(),
                msg: $('#msg').val()
            },
            function(data, status){
                alert("Data: " + data + "\nStatus: " + status);
            });
    });
</script>