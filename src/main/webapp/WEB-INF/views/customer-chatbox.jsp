<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" href="<c:url value="/static/css/base.css" />">
    <link rel="stylesheet" href="<c:url value="/static/css/chatbox.css" />">
</head>
<body>

<div id="chat-box" class="chat_box">
    <div class="chatbox__navbar">
        <img class="logo-chatbox" src="<c:url value="/static/images/logo-light.svg"/>" alt=""/>
        <img id="close_chat" class="close__chatbox" src="<c:url value="/static/images/btn-close.svg"/>" alt=""/>
    </div>

    <div class="chatbox__content">
    </div>

    <div class="chatbox__group-items">
        <input class="chatbox__input" name="" id="input-msg" cols="30" rows="10"></textarea>
        <img onclick="sendMessage()" class="chatbox__send" src="<c:url value="/static/images/send.svg"/>" alt=""/>
    </div>
</div>

<img id="chat-icon" class="chat_icon" src="<c:url value="/static/images/chat_btn.svg"/>" alt=""/>

<script type="text/javascript">
    var currentUsername = "${user.username}";
    var curChattingUsername = "vanannek";
    var ws = getWebsocket(curChattingUsername);

    function getWebsocket(receiver) {
        let wsUrl;

        if (window.location.protocol === 'http:') {
            wsUrl = 'ws://';
        } else {
            wsUrl = 'wss://';
        }
        const contextPath = '<%= request.getContextPath() %>';
        return new WebSocket(wsUrl + window.location.host + contextPath +
            '/chat/' + currentUsername + '/' + receiver);
    }

    function sendMessage() {
        console.log("Click sendMsg");
        const msgStr = document.getElementById("input-msg").value.trim();
        if (msgStr) {
            ws.send(msgStr);
            document.getElementById("input-msg").value = "";
        }
    }

    ws.onmessage = function (event) {
        var chatMessage = JSON.parse(event.data);
        curChattingUsername = chatMessage.senderUsername;
        addMessageToChatbox(chatMessage)
    };

    function addMessageToChatbox(chatMessage) {
        const msgStr = chatMessage.message;
        if (chatMessage.type === "TEXT") {
            const isSender = chatMessage.senderUsername === currentUsername;
            addTextToChatbox(msgStr, isSender);
        } else if (chatMessage.type === "NOTIFICATION") {
            addNotificationToChatbox(msgStr);
        } else {
            console.error("Unsupported message type:", chatMessage.type);
        }
    }

    function addTextToChatbox(message, isSender) {
        const chatboxContent = document.querySelector('.chatbox__content');

        const chatContainer = document.createElement('div');
        chatContainer.classList.add('chatbox__chat-' + (isSender ? 'Sent' : 'received'));

        if (isSender) {
            chatContainer.innerHTML += `
            <div class="chat__my-img"></div>
            <ul class="list__bubble-chat">
                <li class="bubble-chat__item">
                    <p>` + message + `</p>
                </li>
            </ul>
        `;
        } else {
            chatContainer.innerHTML += `
            <img class="chat__bot-img" src="<c:url value="/static/images/account_icon.svg"/>" alt=""/>
            <ul class="list__bubble-chat">
                <li class="bubble-chat__item">
                    <p>` + message + `</p>
                </li>
            </ul>
        `;
        }

        chatboxContent.appendChild(chatContainer);
    }

    function addNotificationToChatbox(message) {
        const chatboxContent = document.querySelector('.chatbox__content');
        if (!chatboxContent) {
            console.error("chatboxContent container not found.");
            return;
        }
        chatboxContent.innerHTML += `
        <div class="chatbox__notification" style="
                padding: 10px;
                text-align: center;">
            <p class="notification__text">`
            + message +
            `</p>
        </div>`;
    }

    document.getElementById("input-msg").addEventListener("keyup", function (event) {
        if (event.key === "Enter") {
            sendMessage();
        }
    });

    document.addEventListener("DOMContentLoaded", function () {
        // addNotificationToChatbox("hihihi");
    });
</script>

<script type="text/javascript" src="<c:url value="/static/js/chatbox.js" />" charset="utf-8"></script>
</body>
</html>
