(function ($) {

    $.fn.ajaxpager = function (options) {
        var opts = $.extend({}, $.fn.ajaxpager.defaults, options);
        var formSelector = opts.formSelector;
        var $pageForm = $(formSelector); // 列表表单
        var $this = $(this);
        var pageInfo = {
            totalRows: function () {
                return parseInt($(formSelector + " input[name=totalRows]").val());
            },
            totalPages: function () {
                return parseInt($(formSelector + " input[name=totalPages]").val());
            },
            currentPage: function () {
                return parseInt($(formSelector + " input[name=currentPage]").val());
            },
            pageRows: function () {
                return parseInt($(formSelector + " input[name=pageRows]").val());
            },
            goPageNum: function () {
                return parseInt($.trim($(formSelector + "IdInputPageNo").val()));
            },
            setStartIndex: function (pageIndex) {
                $(formSelector + " input[name=startIndex]").val((pageIndex - 1) * pageInfo.pageRows());
            },
            setPageRows: function (pageRows) {
                $(formSelector + " input[name=pageRows]").val(pageRows);
            },
            refreshTotalRows: function () {
                $(formSelector + " input[name=totalRows]").val(0);
            }
        };

        function gotoPage(pageIndex, refresh) {
            if (!pageIndex.toString().match(/^[0-9]+$/))
                return;
            if (0 >= pageIndex || pageIndex > pageInfo.totalRows() || (pageIndex == pageInfo.currentPage() && "refresh" != refresh))
                return;
            if ("refresh" == refresh)
                pageInfo.refreshTotalRows();
            pageInfo.setStartIndex(pageIndex);
            $pageForm.ajaxSubmit({
                success: function (result) {
                    $this.empty().html(result);
                    renderpager(pageInfo.currentPage(), pageInfo.totalPages());
                }
            });
        }

        function renderbutton() {
            $(formSelector + "Select").live("change", function () {
                pageInfo.setPageRows($(this).val())
                gotoPage(1, "refresh");
            });

            $(formSelector + " .prevPage").live("click", function () {
                gotoPage(pageInfo.currentPage() - 1);
            });

            $(formSelector + " .nextPage").live("click", function () {
                gotoPage(pageInfo.currentPage() + 1);
            });

            $(formSelector + " .determineBtn").live("click", function () {
                gotoPage(pageInfo.goPageNum());
            });
        }

        function renderpager(currentPage, totalPages) {
            var startPoint = 1;
            var endPoint = 9;

            if (currentPage > 4) {
                startPoint = currentPage - 4;
                endPoint = currentPage + 4;
            }

            if (endPoint > totalPages) {
                startPoint = totalPages - 8;
                endPoint = totalPages;
            }

            if (startPoint < 1) {
                startPoint = 1;
            }

            for (var page = startPoint; page <= endPoint; page++) {
                addDiv(page, currentPage);
            }

            function addDiv(pageNum, currentPage) {
                var $currentButton = $('<a class="page-number">' + pageNum + '</a>');
                $currentButton.click(function () {
                    gotoPage(pageNum);
                });
                if (pageNum == currentPage) $currentButton.addClass('pagingSelect');
                $currentButton.appendTo($(formSelector + "PageNum"));
            }
        }

        return this.each(function () {
            $pageForm.submit(function (event) {
                event.preventDefault();
                gotoPage(1, "refresh");
            });
            renderbutton();
            gotoPage(1, "refresh");
        })
    };

    $.fn.ajaxpager.defaults = {
        totalPages: 1,
        currentPage: 1,
        formSelector: "_ajaxForm"
    };

})(jQuery);





