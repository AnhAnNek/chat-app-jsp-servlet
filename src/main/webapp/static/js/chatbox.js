var chatIcon = document.getElementById("chat-icon");
var chatBox = document.getElementById("chat-box");
chatIcon.addEventListener("click", ()=>{
    if (chatBox.style.display === 'none'){
        openChatbox();
    } else{
        closeChatbox();
    }
})

var closeChatBtn= document.getElementById('close_chat')
closeChatBtn.addEventListener('click',()=>{
    closeChatbox();
})

function openChatbox() {
    chatBox.style.display = 'flex'
    chatIcon.style.boxShadow='0px 0px 1.77408px 0px #CDB2F5, 0px 0px 3.54815px 0px #CDB2F5, 0px 0px 12.41853px 0px #CDB2F5, 0px 0px 24.83706px 0px #CDB2F5, 0px 0px 42.57781px 0px #CDB2F5, 0px 0px 74.51117px 0px #CDB2F5'
}

function closeChatbox() {
    chatBox.style.display = 'none'
    chatIcon.style.boxShadow='3px 2px 8px 0px rgba(0, 0, 0, 0.10), 12px 10px 15px 0px rgba(0, 0, 0, 0.09), 26px 22px 20px 0px rgba(0, 0, 0, 0.05), 46px 39px 24px 0px rgba(0, 0, 0, 0.01), 72px 60px 26px 0px rgba(0, 0, 0, 0.00)'
}