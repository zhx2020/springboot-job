$(function () {
    var token = $.cookie("token");
    $.ajax({
        type: "get",
        url: "http://localhost:8080/user/check",
        headers: {
            'token': token
        },
        success: function (res) {
            console.log(res);
            if (res.flag) {
                // alert("用户Id为：" + res.data.userId);
                var indexLi = $(".index");
                var userIdLi = $(`<li><a href='#' id="userId">${res.data.userId}</a></li>`);
                var userNameLi = $(`<li><a href='#' id="userName">${res.data.userName}</a></li>`);
                var logoutLi = $("<li><a href='#' id='logout'>退出</a></li>");
                indexLi.after(userIdLi, userNameLi, logoutLi);
            } else {
                // alert(res.message);
                var indexLi = $(".index");
                var loginLi = $("<li><a href='/login'>登录</a></li>");
                var registerLi = $("<li><a href='/register'>注册</a></li>");
                indexLi.after(loginLi, registerLi);
            }
        }
    });
    $(document).on("click", "#logout", function () {
        $.cookie("token", '', {expires: -1, path: "/"});
        window.location.reload();
    });
    Date.prototype.format = function(fmt) {
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S" : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        for(var k in o) {
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    };
})