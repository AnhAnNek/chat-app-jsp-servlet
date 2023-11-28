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
        </ul>
    </div>

    <div class="message-pane">
        <div class="chat" id="chat">
        </div>

        <input type="text" id="msg" class="message-input" placeholder="Type your message...">
        <button onclick="sendMessage()">Send</button>
    </div>
</div>

<script type="text/javascript">
    var currentUsername = "${user.username}";
    var curChattingUsername = "queanpham";
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
        const chatMessage = JSON.parse(event.data);
        addMessage(chatMessage)
    };

    function fetchChattedUsers(sender) {
        const contextPath = '<%= request.getContextPath() %>';
        const restUrl = window.location.protocol + "//" + window.location.host + contextPath + "/get-chatted-users" +
            "?username=" + sender;
        console.log("fetchChattedUsers - restUrl: " + restUrl);
        fetch(restUrl)
            .then(response => response.json())
            .then(chattedUsers => {
                clearConversations();
                chattedUsers.forEach(user => {
                    console.log(user.username);
                    console.log(user.role);
                    addConversationItem(user.username);
                });
            })
            .catch(error => console.error('Error fetching chatted users:', error));
    }

    function loadMessages(receiver) {
        curChattingUsername = receiver
        fetchMessages(currentUsername, receiver)
    }

    function fetchMessages(sender, receiver) {
        const contextPath = '<%= request.getContextPath() %>';
        const restUrl = window.location.protocol + "//" + window.location.host + contextPath +
            "/get-messages?sender=" + sender + "&receiver=" + receiver;
        console.log("fetchMessages - restUrl: " + restUrl);
        fetch(restUrl)
            .then(response => response.json())
            .then(chatMessages => {
                clearMsgs();
                chatMessages.forEach(cm => {
                    console.log(cm.message);
                    console.log(cm.sendingTime);
                    console.log(cm.senderUsername);
                    console.log(cm.receiverUsername);
                    addMessage(cm);
                });
            })
            .catch(error => console.error('Error fetching messages:', error));
    }

    function clearMsgs() {
        const chat = document.getElementById('chat');
        if (chat) {
            chat.innerHTML = '';
        } else {
            console.error("Element 'chat' not found.");
        }
    }

    function addMessage(chatMessage) {
        const sender = chatMessage.senderUsername;
        const chat = document.getElementById('chat');

        if (chat) {
            const msg = chatMessage.message;
            const isCurrentUser = sender === currentUsername;
            const senderColor = isCurrentUser ? 'green' : 'red';
            const formattedSender = '<span style="color: ' + senderColor + ';">[' + sender + ']</span>';
            const formattedMessage = formattedSender + ': ' + msg + '<br/>';
            chat.innerHTML += formattedMessage;
        } else {
            console.error("Element 'chat' not found.");
        }
    }

    function sendMessage() {
        const msgStr = document.getElementById("msg").value.trim();

        if(msgStr) {
            ws.send(msgStr);
            console.log(msgStr)
            document.getElementById("msg").value="";
        }
    }

    function clearConversations() {
        const conversationList = document.getElementById('conversationListUl');
        conversationList.innerHTML = '';
    }

    function addConversationItem(receiver) {
        console.log("OnClick addConversationItem")
        const conversationListUl = document.getElementById('conversationListUl');

        if (conversationListUl) {
            const li = document.createElement('li');
            li.textContent = receiver;
            li.onclick = function () {
                loadMessages(receiver);
            };
            conversationListUl.appendChild(li);
        } else {
            console.error("Element 'conversationListUl' not found.");
        }
    }

    document.addEventListener('DOMContentLoaded', function() {
        fetchChattedUsers(currentUsername);
    });

    document.getElementById("msg").addEventListener("keyup", function(event) {
        if (event.key === "Enter") {
            sendMessage();
        }
    });
</script>

</body>
</html>
