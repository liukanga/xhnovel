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

function insertNovel() {

    const name = document.getElementById("name").value;
    const status = document.getElementById("status").value;
    const details = document.getElementById("details").value;
    const duration = document.getElementById("duration").value;

    window.location = "/novel/addNovel?name="+name+"&details="+details+"&status="+status+"&duration="+duration;
}

function showImg(){
    var file = document.getElementById('img_file').files[0];
    var re = new FileReader();
    re.readAsDataURL(file);
    re.onload = function(re){
        const formData = new FormData();
        formData.append("file", file);
        fetch("/image", {
            method: "POST",
            body: formData
        })
            .then(function (response) {
                return response.json();
            })
            .then(function (result){
                if(result.success === false || result.isSuccess===false){
                    alert("上传头像失败，请重新上传");
                    window.history.go(-1);
                }
            })
        document.getElementById('img_id').src=re.target.result;
    }
}
