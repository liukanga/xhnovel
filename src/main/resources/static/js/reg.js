var reg_btn = document.querySelector(".reg-btn");

reg_btn.addEventListener("click", function (){

    var name = document.getElementById("name").value;
    var pwd = document.getElementById("password").value;
    var status = document.getElementById("status").value;
    var duration = document.getElementById("duration").value;
    var details = document.getElementById("details").value;

    if(name===''){
        alert("昵称不能为空！");
        self.location = "/user/reg";
    }
    if(pwd===''){
        alert("密码不能为空！");
        self.location = "/user/reg";
    }
    if(status==='0'){
        alert("请选择身份！");
        self.location = "/user/reg";
    }

    var user = {
        "id": null,
        "name": name,
        "password": pwd,
        "status": status,
        "novels": [],
        "duration": duration,
        "details": details
    };

    fetch(
        '/user/register',
        {
            body: JSON.stringify(user),
            cache: 'no-cache',
            headers: {
                'content-type': 'application/json'
            },
            method: 'POST'
        }
    )
        .then(function (response) {
            return response.json();
        })
        .then(function (result) {
            if(result.isSuccess == true || result.success == true){
                alert("注册成功！您的账号为："+result.data.id);
                window.location = "/user/loginPage";
            }else{
                alert("注册失败,请重新尝试！");
                self.location = "/user/reg";
            }
        })


});

function showImg(){
    var file = document.getElementById('img_file').files[0];
    var re = new FileReader();
    re.readAsDataURL(file);
    re.onload = function(re){
        alert("图片地址：" + JSON.stringify(file))
        const formData = new FormData();
        formData.append("file", file);
        fetch("/image", {
            method: "POST",
            body: formData
        })
            .then(function (response) {
                alert("response1: " + JSON.stringify(response))
                return response.json();
            })
            .then(function (result){
                alert("response2: " + JSON.stringify(result))
                if(result.success === false || result.isSuccess===false){
                    alert("上传头像失败，请重新上传");
                    window.location = "http://localhost:8026/user/reg";
                }
            })
        document.getElementById('img_id').src=re.target.result;
    }
}

