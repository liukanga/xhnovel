function switchTo(value) {
    if(value===1){
        window.location = "/user/toUserList";
    }else if(value===2){
        window.location = "/user/toUserList?status="+2;
    }else if(value===3 || value===4){
        window.location = "/novel/query";
    }else if(value===5){
        window.location = "/comment/toCommentList";
    }else if(value===6){
        var uid = document.getElementById("cUserId").value;
        window.location = "/user/toHomePage?uid="+uid;
    }else if(value===7){
        window.location = "/user/bookshelf";
    }else{
        window.location = "/exit";
    }
}
const de = document.querySelector(".cDetails");
const signOut = document.querySelector(".signOut");
const currentUser = document.querySelector(".ccUser");
currentUser.addEventListener("mouseover", function () {
    de.style.display = "block";
    signOut.style.display = "block";
});
currentUser.addEventListener("click", function () {
    de.style.display = "none";
    signOut.style.display = "none";
});
const dlg = document.querySelector(".dlg");
const add = document.querySelector(".add");

add.addEventListener("click", function (){
    dlg.style.display= "block";
})

function closeDiv() {
    dlg.style.display= "none";
}


function removeUser(id) {
    alert("删除用户成功！")
    window.location = "/user/remove/" + id;
}


function modifyUser(id) {

}

function queryUser() {

    const username = document.getElementById("username").value;
    const statusDiv = document.getElementById("status");
    if (statusDiv === null){
        window.location = "/user/toUserList?username="+username;
    }
    status = statusDiv.value;
    if (status === '0'){
        alert("查找所有”" + username + "”")
        window.location = "/user/toUserList?username="+username;
    } else{
        alert("查找 ”" + username + "”")
        if (status === '2') {
            alert("查找所有”作者”")
        }
        if (status === '3') {
            alert("查找所有”读者”")
        }
        window.location = "/user/toUserList?username="+username+"&&status="+status;
    }
    
}


