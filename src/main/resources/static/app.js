var stompClient = null;

var userId = guid();

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    // 建立连接对象（还未发起连接）
    var socket = new SockJS('/gs-guide-websocket');
    // 获取 STOMP 子协议的客户端对象
    stompClient = Stomp.over(socket);
    // 向服务器发起websocket连接并发送CONNECT帧
    stompClient.connect({}, //可添加客户端的认证信息
        function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
            //连接成功的回调函数
            //订阅频道
        /*stompClient.subscribe('/user/'+ userId +'/topic/greetings', function (greeting) {
            //把回调得到的结果展示在页面上
            showGreeting("userId:" + userId + JSON.parse(greeting.body).content);
        });*/
            stompClient.subscribe('/topic/greetings', function (greeting) {
                //把回调得到的结果展示在页面上
                showGreeting("broadcast:" + userId + JSON.parse(greeting.body).content);
            });
            stompClient.subscribe('/user/topic/toUser', function (greeting) {
                //把回调得到的结果展示在页面上
                showGreeting("userId:" + userId + JSON.parse(greeting.body).content);
            });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function sendName() {
    stompClient.send("/app/toAll", {}, JSON.stringify({'name': $("#name").val(),'userId':userId}));
    stompClient.send("/app/user", {}, JSON.stringify({'name': $("#name").val(),'userId':userId}));
}

function guid() {
    function s4() {
        return Math.floor((1 + Math.random()) * 0x10000)
            .toString(16)
            .substring(1);
    }
    return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});