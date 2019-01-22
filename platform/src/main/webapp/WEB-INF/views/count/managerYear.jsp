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
                <div class="col-sm-4" >
                    <div class="pull-right" style="margin-top: 5px;">
                        <div class="btn-group">
                            <shiro:hasPermission name="24search">
                                <button type="button" class="btn btn-primary btn-sm" id="btn-query">
                                    <i class="fa fa-search"></i> 查询
                                </button>
                                <button type="button" class="btn btn-primary btn-sm" id="btn-re">
                                    <i class="fa fa-refresh"></i> 刷新
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
                <th style="width: 10%;">工号</th>
                <th style="width: 10%;">姓名</th>
                <th style="width: 10%;">上班时长</th>
                <th style="width: 10%;">加班工时</th>
                <th style="width: 10%;">调休工时</th>
                <th style="width: 10%;">事假工时</th>
                <th style="width: 10%;">年假工时</th>
            </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>

</body>
</html>
<%@ include file="../../../currency/js.jsp"%>
<script>
    $(function () {

        var tables = $("#dataTable").dataTable(
            platform.getDatatableSettings('/api/count/dataGridYear',
                'queryForm',
                [
                    {"data": "loginNumber"},
                    {"data": "name"},
                    {"data": "workingHours"},
                    {"data": "overtimeWorkHours"},
                    {"data": "breakTime"},
                    {"data": "timeOfWord"},
                    {"data": "annualHoliday"}
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

    });


</script>
