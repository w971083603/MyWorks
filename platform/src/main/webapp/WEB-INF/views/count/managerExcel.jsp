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
                <label for="date1" class="col-sm-2 control-label">刷卡日期</label>
                <div class="col-sm-2" >
                    <input type="text" name="date1" id="date1" class="form-control"  >
                    <input type="text" id = "registerStartTime" name="registerStartTime" class="form-control" style="display: none"/>
                    <input type="text" id = "registerEndTime" name="registerEndTime" class="form-control" style="display: none;"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-12" >
                    <div class="pull-right" style="margin-top: 5px;">
                        <div class="btn-group">
                            <shiro:hasPermission name="27search">
                                <button type="button" class="btn btn-primary btn-sm" id="btn-query">
                                    <i class="fa fa-search"></i> 查询
                                </button>
                                <button type="button" class="btn btn-primary btn-sm" id="btn-re">
                                    <i class="fa fa-refresh"></i> 刷新
                                </button>
                            </shiro:hasPermission>
                        </div>
                        <div class="btn-group">
                            <shiro:hasPermission name="27add">
                                <button type="button" class="btn btn-primary btn-sm" id="exceleLoad">
                                    导入excel
                                </button>
                            </shiro:hasPermission>
                        </div>
                    </div>
                </div>
            </div>
         </form>
    </div>

    <!--表格-->
    <table id="dataTable" class="table table-striped table-bordered table-hover table-condensed" align="center">
        <thead>
            <tr class="info">
                <th style="width: 10%;">机构</th>
                <th style="width: 10%;">人员编号</th>
                <th style="width: 10%;">姓名</th>
                <th style="width: 10%;">刷卡日期</th>
                <th style="width: 10%;">星期</th>
                <th style="width: 10%;">打卡1</th>
                <th style="width: 10%;">打卡2</th>
                <th style="width: 10%;">打卡3</th>
                <th style="width: 10%;">打卡4</th>
            </tr>
        </thead>
        <tbody></tbody>
    </table>

<form id="formExcel" action=""  method="post"  enctype="multipart/form-data">
    <input type="file" id="uploadFile" name="uploadFile" onchange="loadExcel()" style="display: none;"/>
</form>

</div>

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


        var tables = $("#dataTable").dataTable(
            platform.getDatatableSettings('/api/count/dataGridExcel',
                'queryForm',
                [
                    {"data": "organizationName"},
                    {"data": "loginNumber"},
                    {"data": "name"},
                    {"data": "days"},
                    {"data": "week"},
                    {"data": "oneTime"},
                    {"data": "twoTime"},
                    {"data": "threeTime"},
                    {"data": "fourTime"}
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

</script>
