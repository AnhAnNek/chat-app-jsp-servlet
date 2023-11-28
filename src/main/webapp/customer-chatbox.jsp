<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <style media="screen" type="text/css">

        .chat {
            width: 100%;
            height: 200px;
            border: 1px solid silver;
            overflow-y: scroll;
        }

        #msg {width: 99%;}

        h1 {text-align: center;}

    </style>
</head>
<body>
<h1>Live Chat updates</h1>
<h2>Username: ${user.username}</h2>

<input type="text" value="<%= session.getId() %>" style="height: auto;" disabled/>

<div>
    <div id="chat" class="chat">

    </div>
    <div>
        <input type="text" name="msg" id="msg" placeholder="Enter message here"/>
        <button onclick="sendMsg()">Enter</button>
    </div>
</div>

<script type="text/javascript">
    var currentUsername = "${user.username}";
    var curChattingUsername = "vanannek";
    var wsUrl;

    if (window.location.protocol === 'http:') {
        wsUrl = 'ws://';
    } else {
        wsUrl = 'wss://';
    }
    var contextPath = '<%= request.getContextPath() %>';
    var ws = new WebSocket(wsUrl + window.location.host + contextPath +
        '/chat/' + currentUsername + '/' + curChattingUsername);

    ws.onmessage = function(event) {
        var chatMessage = JSON.parse(event.data);
        curChattingUsername = chatMessage.senderUsername;
        addMessage(chatMessage)
    };

    function addMessage(chatMessage) {

        var mySpan = document.getElementById("chat");
        var color = chatMessage.senderUsername === currentUsername ? 'green' : 'red';
        var messageHTML = '<strong style="color: ' + color + ';">[' +
            chatMessage.senderUsername + ']:</strong> ' + chatMessage.message + "<br/>";

        mySpan.innerHTML += messageHTML;
    }

    function sendMsg() {
        var msgStr = document.getElementById("msg").value.trim();
        if(msgStr) {
            ws.send(msgStr);
            console.log(msgStr)
            document.getElementById("msg").value="";
        }
    }

</script>
</body>
</html>
