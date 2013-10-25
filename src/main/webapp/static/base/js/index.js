$(function () {

    $("#addButton").click(function () {
        window.location.href = "add_input";
    });


    // validate signup form on keyup and submit
    var $addForm = $("#addForm");


    var options = {
        success: showResponse,
        timeout: 3000
    };

    // post-submit callback
    function showResponse(responseText, statusText, xhr, $form) {
        $.message({type: responseText.status, content: responseText.message});
    }

    $("#addForm").validate({
        rules: {
            name: {
                required: true,
                minlength: 2
            },
            email: {
                required: true,
                email: true,
                minlength: 4
            },
            phone: {
                required: true
            }
        },
        onfocusout: function (element, event) {
            this.element(element);
        },
        onkeyup: false,
        highlight: function (element) {
            $(element).closest('.control-group').removeClass('success').addClass('error');
        },
        success: function (element) {
            element
                .text('OK!').addClass('valid')
                .closest('.control-group').removeClass('error').addClass('success');
        },
        messages: {
            name: {
                required: "起个名字吧",
                minlength: "太短了啊"
            },
            email: {
                required: "没有邮箱怎么行呢",
                email: "这不是邮箱吧",
                minlength: "太短了啊"
            },
            phone: {
                required: "施舍个手机号吧"
            }
        },
        submitHandler: function () {
            $addForm.submit();
        }
    });
})