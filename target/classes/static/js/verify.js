$(function () {
    $(document).on("click", ".limit", function () {
        var token = $.cookie("token");
        $.ajax({
            type: "get",
            url: "http://localhost:8080/user/check",
            headers: {
                'token': token
            },
            success: function (res) {
                if (!res.flag) {
                    window.location.href = "/login";
                }
            }
        });
    });
})