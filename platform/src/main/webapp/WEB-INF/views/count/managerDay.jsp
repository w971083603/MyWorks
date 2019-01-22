<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<%@ include file="../../../currency/top.jsp"%>
<body   class="sidebar-mini">
<!-- 查询、添加、批量删除、导出、刷新 -->
<div class="content">
    <!-- 查询、添加、批量删除、导出、刷新 -->
    <div class="row-fluid">
        <form id="queryForm" class="form-horizontal" action="" method="post">
            <input type="hidden" name="loginUserId" id="loginUserId" value="<shiro:principal property="id"/>">
            <input type="hidden" name="addressId" id="addressId" value="<shiro:principal property="addressId"/>">
            <input type="hidden" name="rolesstr" id="rolesstr" value="<shiro:principal property="rolesstr"/>">
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">姓名</label>
                <div class="col-sm-2" >
                    <input type="text" name="name" id="name" class="form-control">
                </div>
                <label for="loginNumber" class="col-sm-2 control-label">工号</label>
                <div class="col-sm-2" >
                    <input type="text" name="loginNumber" id="loginNumber" class="form-control">
                </div>
                <label for="date1" class="col-sm-2 control-label">日期</label>
                <div class="col-sm-2" >
                    <input type="text" name="date1" id="date1" class="form-control"  >
                    <input type="text" id = "registerStartTime" name="registerStartTime" class="form-control" style="display: none"/>
                    <input type="text" id = "registerEndTime" name="registerEndTime" class="form-control" style="display: none;"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-4" >
                    <div class="pull-right" style="margin-top: 5px;">
                        <div class="btn-group">
                            <shiro:hasPermission name="21search">
                                <button type="button" class="btn btn-primary btn-sm" id="btn-query">
                                    <i class="fa fa-search"></i> 查询
                                </button>
                                <button type="button" class="btn btn-primary btn-sm" id="btn-re">
                                    <i class="fa fa-refresh"></i> 刷新
                                </button>
                            </shiro:hasPermission>
                        </div>
                        <shiro:hasPermission name="21excel">
                                <%--<div class="btn-group">--%>
                                    <%--<button type="button" class="btn btn-primary btn-sm" onclick="downDayExcel()">--%>
                                        <%--导出日统计excel--%>
                                    <%--</button>--%>
                                <%--</div>--%>
                        </shiro:hasPermission>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="input-group">
                        <shiro:hasPermission name="21excel">
                        <input type="text" class="form-control" name="endMonth" id="endMonth">
                        <span class="input-group-btn">
                                <button type="button" class="btn btn-primary btn-sm" onclick="downExcel()">
                                        导出月统计excel
                                    </button>
                             <span>
                                 </shiro:hasPermission>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="input-group">
                        <shiro:hasPermission name="21excel">
                            <input type="text" class="form-control" name="endDay" id="endDay">
                            <span class="input-group-btn">
                                <button type="button" class="btn btn-primary btn-sm" onclick="downAllExcel()">
                                    导出总统计excel
                                </button>
                             <span>
                        </shiro:hasPermission>
                    </div>
                </div>
            </div>
         </form>
    </div>

    <!--表格-->
    <table id="dataTable" class="table table-striped table-bordered table-hover table-condensed" align="center">
        <thead>
            <tr class="info">
                <th style="width: 10%;">日期</th>
                <th style="width: 10%;">工号</th>
                <th style="width: 10%;">姓名</th>
                <th style="width: 10%;">上班时长</th>
                <th style="width: 10%;">加班工时</th>
                <th style="width: 10%;">调休工时</th>
                <th style="width: 10%;">今日剩余工时</th>
                <th style="width: 10%;">迟到扣除工时</th>
                <th style="width: 10%;">未打卡扣除工时</th>
            </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>

<form id="formExcel" action=""  method="post"  enctype="multipart/form-data">
    <input type="file" id="uploadFile" name="uploadFile" onchange="loadExcel()" style="display: none;"/>
</form>


<div id="loadModal" style="display: none;background: rgba(0, 0, 0, 0.3);opacity: 1;position: fixed;top: 0;right: 0;bottom: 0;left: 0;z-index: 1060;" >
    <div style="width: 100%;text-align: center;font-size: 29px;position: relative;top: 37%;outline: 0;border: 0;color: #fdfdfd;">
        正在处理中，请勿刷新或反复操作！
    </div>
</div>

</body>
</html>
<%@ include file="../../../currency/js.jsp"%>
<script>
    $(function () {

        $("input[name='date1']").daterangepicker(platform.DATE_RANGE_PICKER_OPTIONS)
            .on('cancel.daterangepicker', function(ev, picker) {
                $("#date1").val("请选择日期范围");
                $("#registerStartTime").val("");
                $("#registerEndTime").val("");
            }).on('apply.daterangepicker', function(ev, picker) {
            $("#registerStartTime").val(picker.startDate.format('YYYY-MM-DD'));
            $("#registerEndTime").val(picker.endDate.format('YYYY-MM-DD'));
            $("#date1").val(picker.startDate.format('YYYY-MM-DD')+" 至 "+picker.endDate.format('YYYY-MM-DD'));
        });
        var time = platform.timeStamp3String(new Date());
        $("#date1").val(time + "至" + time);
        $("#registerStartTime").val(time);
        $("#registerEndTime").val(time);

        $('#endDay').datetimepicker({
            format: 'yyyy-mm-dd',
            minView:"2",
            autoclose:true
        });
        $('#endDay').val(time);

        $('#endMonth').datetimepicker({
            format: 'yyyy-mm',
            startView:"3",
            minView:"3",
            autoclose:true
        });
        $('#endMonth').val(platform.timeStamp4String(new Date()));

        var tables = $("#dataTable").dataTable(
            platform.getDatatableSettings('/api/count/dataGridDay',
                'queryForm',
                [
                    {
                        "data": 'recordTime'
                    },
                    {"data": "loginNumber"},
                    {"data": "name"},
                    {"data": "workingHours"},
                    {"data": "overtimeWorkHours"},
                    {"data": "breakTime"},
                    {"data": "lessTime"},
                    {"data": "lateTime"},
                    {"data": 'notHitTime'}
                ],
                //行操作按钮定义
                [
                ],
                // 在每次table被draw完后回调函数
                function () {
                    var api = this.api();
                     //获取到本页开始的条数
                    var startIndex = api.context[0]._iDisplayStart;
                    api.column(1).nodes().each(function (cell, i) {
                    });
                }
            ));
        //查询按钮
        $("#btn-query").on("click", function () {
            tables.fnDraw();//查询后不需要保持分页状态，回首页
        });
        //刷新
        $("#btn-re").on("click", function () {
            tables.fnDraw(false);//刷新保持分页状态
        });
        //导入excel
        $("#exceleLoad").click(function () {
            $("#uploadFile").click();
        });
    });

    function loadExcel(){
        //检验导入的文件是否为Excel文件
        var uploadFile = $("#uploadFile").val();
        if(uploadFile == null || uploadFile == ''){
            layer.msg("请选择要上传的Excel文件", {icon: 2});
            return;
        }else{
            var fileExtend = uploadFile.substring(uploadFile.lastIndexOf('.')).toLowerCase();
            if(!(fileExtend == '.xls' || fileExtend == '.xlsx')){
                layer.msg("文件格式需为'.xls'格式", {icon: 2});
                return;
            }
        }
        //提交表单
        $("#loadModal").show();
        $("#formExcel").ajaxSubmit({
            url: platform.CONSTS.URL_BASE_CMS + "api/count/improtExcel",
            type: "POST",//提交类型
            dataType: "json",
            success: function (data) {
                 console.log(data)
                $("#btn-re").click();
                $("#loadModal").hide();
            }, error: function (error) {
                $("#loadModal").hide();
                layer.msg("导入失败", {icon: 2});
            }
        });
        $("#uploadFile").val("")
    }

    //导出excel
    function downExcel(){
        if($("#endMonth").val().trim() == ""){
            layer.msg("时间不能为空", {icon: 2});
            return;
        }
        window.location.href=platform.CONSTS.URL_BASE_CMS + 'api/count/excel.do?endMonth=' + $("#endMonth").val().trim();
    }
    //导出excel
//    function downDayExcel(){
//        window.location.href=platform.CONSTS.URL_BASE_CMS + 'api/count/excelDay.do';
//    }
    //导出excel
    function downAllExcel(){
        window.location.href=platform.CONSTS.URL_BASE_CMS + 'api/count/excelAll.do?endDay='+$("#endDay").val().trim();
    }

</script>
