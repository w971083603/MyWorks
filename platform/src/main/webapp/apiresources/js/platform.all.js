(function(window,document){

    var platform = function () {};

    /**
     * 系统常量
     * @type {{TASK_STATUS: [*], DAYS_OF_WEEK: [*], MONTH_NAMES: [*]}}
     */
    platform.CONSTS ={

        URL_BASE_CMS:"",//当前项目接口URL

        DAYS_OF_WEEK:["日","一","二","三","四","五","六"],

        MONTH_NAMES:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],

        /**
         * 日期格式
         */
        DATE_FORMAT:"yyyy-MM-dd",
        /**
         * 日期格式
         */
        DATE_FORMAT_YMD_HM:"yyyy-MM-dd HH:mm",
        /**
         * 日期格式
         */
        DATE_FORMAT_YMD_HMS:"yyyy-MM-dd HH:mm:ss",
        /**
         * 日期格式
         */
        DATE_FORMAT_MD_HM:"yy-MM-dd HH:mm"
    };

    /**
     * daterangepicker参数设置
     * @type {{autoApply: boolean, locale: {format: string, separator: string, applyLabel: string, cancelLabel: string, fromLabel: string, toLabel: string, customRangeLabel: string, weekLabel: string, daysOfWeek: *[], monthNames: *[], firstDay: number, isInvalidDate: isInvalidDate}}}
     */
    platform.DATE_RANGE_PICKER_OPTIONS = {
        // autoApply: true,
        autoUpdateInput: false,
        // alwaysShowCalendars: true,
        ranges: {
            '今天': [moment(),moment()],
            '昨天': [moment().subtract(1, 'days'),moment().subtract(1, 'days')],
            '近7天': [moment().subtract(7, 'days'), moment()],
            '这个月': [moment().startOf('month'), moment().endOf('month')],
            '上个月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        },
        locale: {
            format: "YYYY/MM/DD",
            separator: " - ",
            applyLabel: "确认",
            cancelLabel: "清空",
            fromLabel: "开始时间",
            toLabel: "结束时间",
            customRangeLabel: "自定义",
            daysOfWeek: platform.CONSTS.DAYS_OF_WEEK,
            monthNames: platform.CONSTS.MONTH_NAMES
        },
    };




    /**
     * 时间转换（timeStamp转字符串）
     * @param time
     * @returns {string}
     */
    platform.timeStamp2String = function(time){
        var datetime = new Date();
        datetime.setTime(time);
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1;
        var date = datetime.getDate();
        var hour = datetime.getHours();
        var minute = datetime.getMinutes();
        var second = datetime.getSeconds();
        var mseconds = datetime.getMilliseconds();
        return year + "/" + month + "/" + date+" "+hour+":"+minute;
    };

    /**
     * 时间转换（timeStamp转字符串）
     * @param time
     * @returns {string}
     */
    platform.timeStamp3String = function(time){
        var datetime = new Date();
        datetime.setTime(time);
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1;
        var date = datetime.getDate();
        var hour = datetime.getHours();
        var minute = datetime.getMinutes();
        var second = datetime.getSeconds();
        var mseconds = datetime.getMilliseconds();
        if (month < 10) {
            month = "0" + month;
        }
        if (date < 10) {
            date = "0" + date;
        }
        return year + "-" + month + "-" + date;
    };


    /**
     * 时间转换（timeStamp转字符串）
     * @param time
     * @returns {string}
     */
    platform.timeStamp4String = function(time){
        var datetime = new Date();
        datetime.setTime(time);
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1;
        var date = datetime.getDate();
        var hour = datetime.getHours();
        var minute = datetime.getMinutes();
        var second = datetime.getSeconds();
        var mseconds = datetime.getMilliseconds();
        if (month < 10) {
            month = "0" + month;
        }
        return year + "-" + month;
    };


    /**
     * 时间转换（timeStamp转日期）
     * @param time
     * @returns {number}
     */
    platform.timeStamp2Date = function (time) {
        var strDate = platform.timeStamp2String(time);
        return new Date(strDate);
    };
    /**
     * 时间转换（获取月）
     * @param time
     * @returns {string}
     */
    platform.getMonthTime = function(time, num){
        var datetime = new Date(time);
        datetime.setMonth(datetime.getMonth() + num);
        return datetime;
    };

    /**
     * 时间转换（获取月）
     * @param time
     * @returns {string}
     */
    platform.getMonth = function(time){
        var datetime = new Date(time);
        var month = datetime.getMonth() + 1;
        return month;
    };

    /**
     * 判断是否为空值
     * @param value
     * @returns {boolean}
     */
    platform.isNullOrEmpty = function (value) {
        return (value || "") === "";
    };

    /**
     * 从对象数组中删除属性为objPropery，值为objValue元素的对象
     * @param Array arr  数组对象
     * @param String objPropery  对象的属性
     * @param String objPropery  对象的值
     * @return Array 过滤后数组
     */
    platform.removeArrayItem = function (arr, objPropery, objValue) {
        return $.grep(arr, function (cur, i) {
            return cur[objPropery] != objValue;
        });
    };

    /**
     * 从对象数组中获取属性为objPropery，值为objValue元素的对象
     * @param Array arr  数组对象
     * @param String objPropery  对象的属性
     * @param String objPropery  对象的值
     * @return Array 过滤后的数组
     */
    platform.getArrayItem = function (arr, objPropery, objValue) {
        return $.grep(arr, function (cur, i) {
            return cur[objPropery] == objValue;
        });
    };

    /**
     * 获取枚举类型描述
     * @param enumList 枚举列表
     * @param code
     * @returns {*}
     */
    platform.getEnumName = function (enumList,code) {
        var items = platform.getArrayItem(enumList,"id",code);

        if (items.length === 1) {
            return items[0].name;
        } else {
            return "";
        }
    };

    /**
     * 初始化fileinput控件（第一次初始化）
     * 需要引用类库 bootstrap fileinput
     *
     * @param ctrlName 控件名
     * @param maxFileCount 可上传照片张数
     * @param uploadUrl 图片上传URL
     */
    platform.initFileInput = function (ctrlName, maxFileCount, uploadUrl) {
        var control = $('#' + ctrlName);

        if(maxFileCount > 1){
            control.fileinput({
                language: 'zh', //设置语言
                uploadUrl: uploadUrl, //上传的地址
                allowedFileExtensions : ['jpg', 'jpeg','png','gif'],//接收的文件后缀,
                maxFileCount: maxFileCount,
                enctype: 'multipart/form-data',
                showUpload: false, //是否显示上传按钮
                showCaption: false,//是否显示标题
                browseClass: "btn btn-primary", //按钮样式
                previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
                msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
                // resizeImage:true,
                // maxImageWidth:500,
                // maxImageHeight:500
            });
        }else {
            control.fileinput({
                language: 'zh', //设置语言
                uploadUrl: uploadUrl, //上传的地址
                allowedFileExtensions : ['jpg','jpeg','png','gif'],//接收的文件后缀
                showUpload: false, //是否显示上传按钮
                showCaption: false,//是否显示标题
                browseClass: "btn btn-primary", //按钮样式
                previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
                // resizeImage:true,
                // maxImageWidth:500,
                // maxImageHeight:500
            });
        }
    };

    /**
     * 将表单数据序列化成JSON格式返回
     * @param formId
     * @returns {{}}
     */
    platform.serializeJson = function (formId) {
        var serializeObj = {};
        $($("#"+formId).serializeArray()).each(function () {
            serializeObj[this.name] = this.value;
        });
        return serializeObj;
    };

    /**
     * 封装POST请求
     * @param url 请求URL
     * @param formData 表单数据
     * @param fnSuccessCallback 成功时回调
     * @param fnErrorCallback 出错时回调
     * @param contentType 指定ContentType
     */
    platform.post = function (url,formData,fnSuccessCallback,fnErrorCallback,contentType) {
        $.ajax({
            url: platform.CONSTS.URL_BASE_CMS + url,
            type: 'post',
            data: formData,
            processData:false,
            contentType:contentType || false,
            // contentType:contentType || "application/json",
            success: function (response) {

                var resp = {};
                try{
                    resp = JSON.parse(response)
                }catch(error) {
                    resp = response;
                }
                console.log(url);
                console.log(formData);
                console.log("response-->"+resp.toString());

                if (resp.success) {
                    if(typeof(fnSuccessCallback) === "function")
                        fnSuccessCallback(resp.data);
                    else
                    {
                        layer.msg('保存成功！', {icon: 1});
                    }
                } else {
                    if(typeof(fnErrorCallback) === "function")
                        fnErrorCallback(new Error(resp.message,resp.code));
                    else
                        layer.msg('程序员跑火星去了！'+ err.statusText, {icon: 2});
                }
            },
            error: function (err) {
                console.log(url);
                console.log(formData);
                console.log(err);

                err.message = err.message || err.responseText;
                if(typeof(fnErrorCallback) === "function")
                    fnErrorCallback(err);
                else
                    layer.msg('程序员跑火星去了！'+ err.statusText, {icon: 2});
            }
        });
    };

    /**
     * 封装POST请求
     * @param url 请求URL
     * @param formData 表单数据
     * @param fnSuccessCallback 成功时回调
     * @param fnErrorCallback 出错时回调
     * @param contentType 指定ContentType
     */
    platform.get = function (url,formData,fnSuccessCallback,fnErrorCallback,contentType) {
        $.ajax({
            url: platform.CONSTS.URL_BASE_CMS + url,
            type: 'get',
            data: formData,
            processData:false,
            contentType:contentType || false,
            // contentType:"application/json",
            success: function (response) {
                var resp = {};
                try{
                    resp = JSON.parse(response)
                }catch(error) {
                    resp = response;
                }
                console.log(resp);

                if (resp.code == 0) {
                    if(typeof(fnSuccessCallback) === "function")
                        fnSuccessCallback(resp);
                    else
                    {
                        // TODO something
                        layer.msg('保存成功！', {icon: 1});
                    }
                } else {
                    if(typeof(fnErrorCallback) === "function")
                        fnErrorCallback(new Error(resp.message,resp.code));
                    else
                        layer.msg('程序员跑火星去了！'+ err.statusText, {icon: 2});
                }
            },
            error: function (err) {
                if(typeof(fnErrorCallback) === "function")
                    fnErrorCallback(err);
                else
                    layer.msg('程序员跑火星去了！'+ err.statusText, {icon: 2});
            }
        });
    };

    /**
     * 获取URL参数
     * @param paramName
     * @returns {null}
     */
    platform.getQueryString = function (paramName) {

        var reg = new RegExp("(^|&)"+ paramName +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);

        if(r!=null)return  unescape(r[2]); return null;

    };


    /**
     * 获取URL参数==带中文的参数
     * @param paramName
     * @returns {null}
     */
    platform.getQueryDecodeString = function (paramName) {

        var reg = new RegExp("(^|&)"+ paramName +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);

        if(r!=null)return  decodeURI(r[2]); return null;

    };

    /**
     * 将消息列表转换成HTML
     * @param errmsg
     * @returns {string}
     */
    platform.getMessageListHtml = function(errmsg){
        var html = "";
        if($.isArray(errmsg)) {
            html += "<ul>";
            for (var i = 0; i < errmsg.length; i++) {
                html += "<li>"+errmsg[i]+"</li>";
            }
            html += "</ul>";
        }else{
            html = errmsg;
        }

        return html;
    };

    /**
     * datatable初始化设置
     * @param url ajax获取数据URL
     * @param queryForm 查询条件表单ID
     * @param columnList 对应上面thead里面的序列
     * @param rowButtonDefs 行操作按钮定义
     * @param fnDrawCallback Table绘制后的回调函数
     */
    platform.getDatatableSettings = function (url,queryForm,columnList,rowButtonDefs,fnDrawCallback) {
        var settings = {
            sScrollY:'58vh',
            serverSide: true,//分页，取数据等等的都放到服务端去
            processing: true,//载入数据的时候是否显示“载入中”
            pageLength: 10,  //首次加载的数据条数
            ordering: false, //排序操作在服务端进行，所以可以关了。
            pagingType: "full_numbers",
            autoWidth: true,
            stateSave: true,//保持翻页状态，和comTable.fnDraw(false);结合使用
            searching: false,//禁用datatables搜索
            bPaginate:true,
            ajax: {
                type: "post",
                url: platform.CONSTS.URL_BASE_CMS+url,
                dataSrc: "data",
                dataType:'json',
                contentType:"application/json",
                data: function (d) {
                    var param = {};
                    param.draw = d.draw;
                    param.start = d.start;
                    param.length = d.length;
                    $("#"+queryForm).serializeArray().forEach(function (e) {
                        param[e.name] = e.value;
                    });
                    return JSON.stringify(param);//自定义需要传递的参数。
                }
            },
            //数据列
            columns: columnList,
            //操作按钮
            columnDefs: rowButtonDefs,
            language: {
                lengthMenu: "",
                processing: "正在加载数据，请稍候...",
                paginate: {
                    previous: "<",
                    next: ">",
                    first: "<<",
                    last: ">>"
                },
                zeroRecords: "没有找到符合条件的数据！",
                info: "",
                infoEmpty: "",
                infoFiltered: "",
                sSearch: "",
            },
            //在每次table被draw完后回调函数
            fnDrawCallback: fnDrawCallback
        };

        return settings;
    };

    /**
     * datatable初始化设置多表格
     * @param url ajax获取数据URL
     * @param queryForm 查询条件表单ID
     * @param columnList 对应上面thead里面的序列
     * @param rowButtonDefs 行操作按钮定义
     * @param fnDrawCallback Table绘制后的回调函数
     */
    platform.getDatatableMultiTableSettings = function (url,queryForm,columnList,rowButtonDefs,fnCreatedRow,fnDrawCallback) {
        var settings = {
            sScrollY:'58vh',
            serverSide: true,//分页，取数据等等的都放到服务端去
            processing: true,//载入数据的时候是否显示“载入中”
            pageLength: 10,  //首次加载的数据条数
            ordering: false, //排序操作在服务端进行，所以可以关了。
            pagingType: "full_numbers",
            autoWidth: true,
            stateSave: true,//保持翻页状态，和comTable.fnDraw(false);结合使用
            searching: false,//禁用datatables搜索
            bPaginate:true,
            ajax: {
                type: "post",
                url: platform.CONSTS.URL_BASE_CMS+url,
                dataSrc: "data",
                dataType:'json',
                contentType:"application/json",
                data: function (d) {
                    var param = {};
                    param.draw = d.draw;
                    param.start = d.start;
                    param.length = d.length;
                    $("#"+queryForm).serializeArray().forEach(function (e) {
                        param[e.name] = e.value;
                    });
                    return JSON.stringify(param);//自定义需要传递的参数。
                }
            },
            //数据列
            columns: columnList,
            //操作按钮
            columnDefs: rowButtonDefs,
            language: {
                lengthMenu: "",
                processing: "正在加载数据，请稍候...",
                paginate: {
                    previous: "<",
                    next: ">",
                    first: "<<",
                    last: ">>"
                },
                zeroRecords: "没有找到符合条件的数据！",
                info: "",
                infoEmpty: "",
                infoFiltered: "",
                sSearch: "",
            },
            fnCreatedRow:fnCreatedRow,
            //在每次table被draw完后回调函数
            fnDrawCallback: fnDrawCallback
        };

        return settings;
    };

    window.platform = platform;

}(window,document));


var platform = window.platform;

