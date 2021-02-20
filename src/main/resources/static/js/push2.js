$(function () {
    // 职位推荐
    $.ajax({
        type: "get",
        url: "http://localhost:8080/jobitem/push?id=" + $.cookie("name"),
        success: function (res) {
            // console.log(res);
            var rightDiv = $("#right");
            for (var i = 0 ; i < res.data.content.length; i++) {
                var jobDiv = $(`<a href="/detail/${res.data.content[i].jobId}" target="_blank" style="text-decoration: none">
                                        <div class="panel panel-success">
                                            <div class="panel-body text-muted">
                                                <h5>${res.data.content[i].jobName}</h5>
                                                <h6>${res.data.content[i].companyName}</h6>
                                                <h6><span>${res.data.content[i].jobArea}</span> <span>${res.data.content[i].salaryMin}-${res.data.content[i].salaryMax}</span></h6>
                                            </div>
                                        </div>
                                    </a>`);
                rightDiv.append(jobDiv);
            }
            var addPost = $(`<button type="button" class="btn btn-success btn-xs" data-toggle="modal" data-target="#myModal">
                                    发布
                                </button>`);
            rightDiv.append(addPost);
        }
    })
})