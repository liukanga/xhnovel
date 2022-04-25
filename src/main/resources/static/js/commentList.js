function switchTo(value) {
    if(value===1){
        window.location = "/user/toUserList";
    }else if(value===2){
        window.location = "/user/toUserList?status="+2;
    }else if(value===3){
        window.location = "/novel/query";
    }else if(value===4){
        window.location = "/novel/query";
    }else if(value===5){
        window.location = "/comment/toCommentList";
    }else if(value===6){
        var uid = document.getElementById("cUserId").value;
        window.location = "/user/toHomePage?uid="+uid;
    }else{
        window.location = "/user/loginPage"
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

function queryComment() {

    const username = document.getElementById("username").value;
    const status = document.getElementById("status").value;

    if (status === '0'){
        alert("查找所有”" + username + "”")
        window.location = "/user/toUserList?username="+username;
    } else{
        alert("查找 ”" + username + "”")
        window.location = "/user/toUserList?username="+username+"&&status="+status;
    }
    
}