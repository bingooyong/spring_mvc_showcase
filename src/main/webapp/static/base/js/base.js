$(function () {
    /* ---------- List ---------- */

    var $listForm = $("#listForm");// 列表表单

    if ($listForm.size() > 0) {
        var $searchButton = $("#searchButton");// 查找按钮
        var $allCheck = $("#listTable input.allCheck");// 全选复选框
        var $listTableTr = $("#listTable tr:gt(0)");
        var $idsCheck = $("#listTable input[name='ids']");// ID复选框
        var $deleteButton = $("#deleteButton");// 删除按钮
        var $pageNumber = $("#pageNumber");// 当前页码
        var $pageSize = $("#pageSize");// 每页显示数
        var $sort = $("#listTable .sort");// 排序
        var $orderBy = $("#orderBy");// 排序字段
        var $order = $("#order");// 排序方式
        var $editButton = $(".editButton"); //编辑按钮

        // 全选
        $allCheck.click(function () {
            var $this = $(this);
            if ($this.attr("checked")) {
                $idsCheck.attr("checked", true);
                $deleteButton.attr("disabled", false);
                $listTableTr.addClass("checked");
            } else {
                $idsCheck.attr("checked", false);
                $deleteButton.attr("disabled", true);
                $listTableTr.removeClass("checked");
            }
        });

        // 无复选框被选中时,删除按钮不可用
        $idsCheck.click(function () {
            var $this = $(this);
            if ($this.attr("checked")) {
                $this.parent().parent().addClass("checked");
                $deleteButton.attr("disabled", false);
            } else {
                $this.parent().parent().removeClass("checked");
                var $idsChecked = $("#listTable input[name='ids']:checked");
                if ($idsChecked.size() > 0) {
                    $deleteButton.attr("disabled", false);
                } else {
                    $deleteButton.attr("disabled", true)
                }
            }
        });

        // 批量删除
        $deleteButton.click(function () {
            var url = $(this).attr("url");
            var $idsCheckedCheck = $("#listTable input[name='ids']:checked");
            $.dialog({type: "warn", content: "您确定要删除吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: batchDelete});
            function batchDelete() {
                $.ajax({
                    url: url,
                    data: $idsCheckedCheck.serialize(),
                    type: "POST",
                    dataType: "json",
                    cache: false,
                    success: function (data) {
                        if (data.status == "success") {
                            $idsCheckedCheck.parent().parent().remove();
                        }
                        $deleteButton.attr("disabled", true);
                        $allCheck.attr("checked", false);
                        $idsCheckedCheck.attr("checked", false);
                        $.message({type: data.status, content: data.message});
                    }
                });
            }
        });
        // 编辑

        $editButton.click(function () {
            var id = $(this).parents("tr").find("input[name='ids']").val();
            window.location.href = SimpleMVC.editAction + "/" + id;
        })

        // 查找
        $searchButton.click(function () {
            $pageNumber.val("1");
            $listForm.submit();
        });

        // 每页显示数
        $pageSize.change(function () {
            $pageNumber.val("1");
            $listForm.submit();
        });

        // 排序
        $sort.click(function () {
            var $currentOrderBy = $(this).attr("name");
            if ($orderBy.val() == $currentOrderBy) {
                if ($order.val() == "") {
                    $order.val("asc")
                } else if ($order.val() == "desc") {
                    $order.val("asc");
                } else if ($order.val() == "asc") {
                    $order.val("desc");
                }
            } else {
                $orderBy.val($currentOrderBy);
                $order.val("asc");
            }
            $pageNumber.val("1");
            $listForm.submit();
        });

        // 排序图标效果
        if ($orderBy.val() != "") {
            $sort = $("#listTable .sort[name='" + $orderBy.val() + "']");
            if ($order.val() == "asc") {
                $sort.removeClass("desc").addClass("asc");
            } else {
                $sort.removeClass("asc").addClass("desc");
            }
        }

        // 页码跳转
        $.gotoPage = function (id) {
            $pageNumber.val(id);
            $listForm.submit();
        }
    }
});


// dialog --
$().ready(function () {

    var $body;
    var dialogIdNumber = 0;
    var dialogZIndex = 100;
    var messageIdNumber = 0;

    $.dialog = function (settings) {

        var dialogId;

        if (settings.id != null) {
            dialogId = settings.id;
        } else {
            dialogId = "dialog" + dialogIdNumber;
            dialogIdNumber++;
        }
        if (settings.content == null) {
            settings.content = "";
        }
        if (settings.width == null || settings.width == "auto") {
            settings.width = 320;
        }
        if (settings.height == null) {
            settings.height = "auto";
        }

        if ($body == null) {
            $body = $("body");
        }

        var dialogHtml = "";

        if (settings.modal == true) {
            dialogHtml += '<div id="dialogOverlay' + dialogId + '" class="dialogOverlay"></div>';
        }

        if (settings.className != null) {
            dialogHtml += '<div id="' + dialogId + '" class="baseDialog ' + settings.className + '"><div class="dialogWrap"></div><div class="dialogMain">';
        } else {
            dialogHtml += '<div id="' + dialogId + '" class="baseDialog"><div class="dialogWrap"></div><div class="dialogMain">';
        }

        if (settings.title != null) {
            dialogHtml += '<div id="dialogTitle' + dialogId + '" class="dialogTitle">' + settings.title + '</div><div id="dialogClose' + dialogId + '" class="dialogClose"></div>';
        } else {
            dialogHtml += '<div id="dialogClose' + dialogId + '" class="dialogClose"></div>';
        }

        if (settings.type != null) {
            dialogHtml += '<div id="dialogContent' + dialogId + '" class="dialogContent dialog' + settings.type + 'Icon">' + settings.content + '</div>';
        } else {
            dialogHtml += '<div id="dialogContent' + dialogId + '" class="dialogContent">' + settings.content + '</div>';
        }

        if (settings.ok != null || settings.cancel != null) {
            dialogHtml += '<div id="dialogButtonArea' + dialogId + '" class="dialogButtonArea">';
        }

        if (settings.ok != null) {
            dialogHtml += '<input type="button" id="dialogOk' + dialogId + '" class="formButton" value="' + settings.ok + '" hidefocus="true" />';
        }

        if (settings.cancel != null) {
            dialogHtml += '<input type="button" id="dialogCancel' + dialogId + '" class="formButton" value="' + settings.cancel + '" hidefocus="true" />';
        }

        if (settings.ok != null || settings.cancel != null) {
            dialogHtml += '</div>';
        }

        if (!window.XMLHttpRequest) {
            dialogHtml += '<iframe id="dialogIframe' + dialogId + '" class="dialogIframe"></iframe>';
        }

        dialogHtml += '</div></div>';

        $body.append(dialogHtml);

        var dialogX;
        var dialogY;

        var $dialogOverlay = $("#dialogOverlay" + dialogId);
        var $dialog = $("#" + dialogId);
        var $dialogTitle = $("#dialogTitle" + dialogId);
        var $dialogClose = $("#dialogClose" + dialogId);
        var $dialogOk = $("#dialogOk" + dialogId);
        var $dialogCancel = $("#dialogCancel" + dialogId);

        $dialog.css({"width": settings.width, "height": settings.height, "margin-left": -parseInt(settings.width / 2), "z-index": dialogZIndex++});

        if (!window.XMLHttpRequest) {
            var $dialogIframe = $("#dialogIframe" + dialogId);
            $dialogIframe.css({"width": $dialog.width() + 20, "height": $dialog.height() + 20});
        }

        function dialogClose() {
            $dialogOverlay.remove();
            $dialog.remove();
        }

        if (settings.autoCloseTime != null) {
            setTimeout(dialogClose, settings.autoCloseTime);
        }

        $dialogClose.click(function () {
            if ($.isFunction(settings.cancelCallback)) {
                if (settings.cancelCallback.apply() != false) {
                    dialogClose();
                }
            } else {
                dialogClose();
            }
        });

        $dialogOk.click(function () {
            if ($.isFunction(settings.okCallback)) {
                if (settings.okCallback.apply() != false) {
                    dialogClose();
                }
            } else {
                dialogClose();
            }
        });

        $dialogCancel.click(function () {
            if ($.isFunction(settings.cancelCallback)) {
                if (settings.cancelCallback.apply() != false) {
                    dialogClose();
                }
            } else {
                dialogClose();
            }
        });

        $dialogTitle.mousedown(function (event) {
            $dialog.css({"z-index": dialogZIndex++});
            var offset = $(this).offset();
            if (!window.XMLHttpRequest) {
                dialogX = event.clientX - offset.left + 6;
                dialogY = event.clientY - offset.top + 6;
            } else {
                dialogX = event.pageX - offset.left + 6;
                dialogY = event.pageY - offset.top + 6;
            }

            $(document).bind("mousemove", function (event) {
                $dialog.css({"top": event.clientY - dialogY, "left": event.clientX - dialogX, "margin": 0});
            });
            return false;
        });

        $(document).mouseup(function () {
            $(document).unbind("mousemove");
        });

        $dialog.keypress(function (event) {
            if (event.keyCode == 13) {
                if ($.isFunction(settings.okCallback)) {
                    if (settings.okCallback.apply() != false) {
                        dialogClose();
                    }
                } else {
                    dialogClose();
                }
            }
        });

        $dialogOverlay.show();
        $dialog.show();
        $dialog.focus();

        return dialogId;
    }

    $.closeDialog = function (dialogId) {
        var $dialogOverlay = $("#dialogOverlay" + dialogId);
        var $dialog = $("#" + dialogId);

        $dialogOverlay.remove();
        $dialog.remove();
    }

    $.message = function (settings) {

        if (settings.content == null) {
            settings.content = "";
        }

        if ($body == null) {
            $body = $("body");
        }

        var messageId = "message" + messageIdNumber;
        messageIdNumber++;

        var messageHtml;

        if (settings.type != null) {
            messageHtml = '<div id="' + messageId + '" class="baseMessage"><div class="messageContent message' + settings.type + 'Icon">' + settings.content + '</div></div>';
        } else {
            messageHtml = '<div id="' + messageId + '" class="baseMessage"><div class="messageContent">' + settings.content + '</div></div>';
        }

        $body.append(messageHtml);

        var $message = $("#" + messageId);

        $message.css({"margin-left": "-" + parseInt($message.width() / 2) + "px"}).show();

        setTimeout(function () {
            $message.animate({left: 0, opacity: "hide"}, "slow", function () {
                $message.remove();
            });
        }, 2000);

        return messageId;
    }

});