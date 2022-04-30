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


function modifyNovel() {

    const name = document.getElementById("name").value;
    const details = document.getElementById("details").value;
    const status = document.getElementById("status").value;

    const nid = document.getElementById("nid").value;

    var novel = {
        "nName": name,
        "details": details,
        "status": status
    }


    fetch("/novel/modifyNovel",
        {
            body: JSON.stringify(novel),
            cache: 'no-cache',
            headers: {
                'content-type': 'application/json'
            },
            method: 'POST'
        })
        .then(function (response) {
            return response.json();
        })
        .then(function (result) {
            if (result.success===true || result.isSuccess===true){
                alert("更新小说《"+name+"》成功");
                window.location = "/novel/query";
            }else {
                alert("更新小说《"+name+"》失败, 请重新尝试！");
                window.location = "/novel/toModifyNovel?nid="+nid;
            }
        })

}

function toAddChapter(id) {

    window.location="/chapter//toModifyChapter?page=2";

}