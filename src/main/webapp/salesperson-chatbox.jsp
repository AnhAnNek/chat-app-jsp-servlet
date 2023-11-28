<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        .chat-container {
            max-width: 600px;
            margin: 20px auto;
            border: 1px solid #ccc;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }

        .conversation-list {
            width: 30%;
            float: left;
            overflow-y: auto;
            border-right: 1px solid #ccc;
        }

        .conversation-list ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }

        .conversation-list li {
            padding: 10px;
            cursor: pointer;
            border-bottom: 1px solid #eee;
            background-color: #f9f9f9;
        }

        .conversation-list li:hover {
            background-color: #e0e0e0;
        }

        .message-pane {
            width: 70%;
            float: left;
            padding: 20px;
        }

        .chat {
            max-height: 400px;
            overflow-y: auto;
            border-bottom: 1px solid #ccc;
            margin-bottom: 10px;
        }

        .message-input {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
        }
    </style>
</head>
<body>

<div class="chat-container">
    <div class="conversation-list" id="conversationList">
        <ul id="conversationListUl">
<%--            <c:forEach var="customer" items="${customers}">--%>
<%--                <li onclick="loadConversationByUsername('${customer.username}')">${customer.username}</li>--%>
<%--            </c:forEach>--%>
        </ul>
    </div>

    <div class="message-pane">
        <div class="chat" id="chat">
<%--            <c:forEach var="message" items="${userMessages}">--%>
<%--                <p><strong>${message.senderUsername}</strong>: ${message.message}</p>--%>
<%--            </c:forEach>--%>
        </div>

        <input type="text" id="msg" class="message-input" placeholder="Type your message...">
        <button onclick="sendMessage()">Send</button>
    </div>
</div>

<script type="text/javascript">
    var currentUsername = "${user.username}";
    var curChattingUsername = "queanpham";
    var wsUrl;

    if (window.location.protocol == 'http:') {
        wsUrl = 'ws://';
    } else {
        wsUrl = 'wss://';
    }
    var contextPath = '<%= request.getContextPath() %>';
    var ws = new WebSocket(wsUrl + window.location.host + contextPath +
        '/chat/' + currentUsername + '/' + curChattingUsername);

    ws.onmessage = function(event) {
        var chatMessage = JSON.parse(event.data);
        addMessage(chatMessage)
    };

    function addMessage(chatMessage) {
        if (chatMessage.senderUsername !== curChattingUsername || chatMessage.senderUsername !== currentUsername) {
            return;
        }

        const chat = document.getElementById('chat');
        chat.innerHTML+="["+chatMessage.senderUsername+"]: " + chatMessage.message+"<br/>";
    }

    function loadConversationByUsername(receiver) {

        fetchMessages(currentUsername, receiver);

        curChattingUsername = receiver;
    }

    function fetchChattedUsers(sender, receiver) {
        fetch("/get-chatted-users?sender=" + sender + "&receiver=" + receiver)
            .then(response => response.json())
            .then(chattedUsers => {
                // Update the conversationList with the fetched chatted users
                const conversationList = document.getElementById('conversationList');
                conversationList.innerHTML = ''; // Clear existing users
                chattedUsers.forEach(user => {
                    var chattedUsername = user.username;
                    conversationList.innerHTML += `<li onclick="loadConversationByUsername('${chattedUsername}')">${chattedUsername}</li>`;
                });
            })
            .catch(error => console.error('Error fetching chatted users:', error));
    }


    function fetchMessages(username) {
        fetch(`/get-messages?username=` + username)
            .then(response => response.json())
            .then(messages => {
                // Update the messageContainer with the fetched messages
                const messageContainer = document.getElementById('messageContainer');
                messageContainer.innerHTML = ''; // Clear existing messages
                messages.forEach(message => {
                    var senderUsername = message.senderUsername;
                    var msg = messages.msg;
                    messageContainer.innerHTML += "<p><strong>" + senderUsername+ "</strong>: "+ msg +"</p>";
                });
            })
            .catch(error => console.error('Error fetching messages:', error));
    }

    function sendMessage() {
        console.log("WS: " + ws)
        var messageInput = document.getElementById('msg');
        var msgStr = messageInput.value;

        if(msgStr) {
            ws.send(msgStr);
            console.log(msgStr)
            document.getElementById("msg").value="";
        }
    }

    fetchChattedUsers(username)
    loadConversationByUsername(username)


    const conversationList = document.getElementById('conversationList');
    conversationList.innerHTML += `<li onclick="loadConversationByUsername('1')">vanannek</li>`;
</script>

</body>
</html>
