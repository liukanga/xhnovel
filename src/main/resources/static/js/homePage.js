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
        var aid = document.getElementById("cUserId").value;
        window.location = "/novel/toNovelList/"+aid
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

function sendComment() {

    const content = document.getElementById("commentContent").value;
    const c1 = document.getElementById("commentator1").value;
    const c2 = document.getElementById("commentator2").value;

    var comment = {
        "commentator": {
            "id": c1
        },
        "user": {
            "id": c2
        },
        "content": content
    }

    fetch("/comment/addComment",
        {
            body: JSON.stringify(comment),
            method: 'POST',
            cache: 'no-cache',
            headers: {
                'content-type': 'application/json'
            }
        })
        .then(function (response) {
            return response.json();
        })
        .then(function (result) {
            if(result.isSuccess === true || result.success === true){
                alert("评论成功！");
            }else{
                alert("评论失败，请重新尝试！");
            }
            window.location = "/user/toHomePage?uid="+c2;
        })


}