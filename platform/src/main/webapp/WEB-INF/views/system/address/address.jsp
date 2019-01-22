<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<%@ include file="../../../../currency/top.jsp"%>
<body   class="sidebar-mini">
<!-- 查询、添加、批量删除、导出、刷新 -->
<div class="content">
    <!-- 查询、添加、批量删除、导出、刷新 -->
    <div class="row-fluid">
        <form id="queryForm" class="form-horizontal" action="" method="post">
            <%--<div class="form-group">--%>
                <%--<label for="name" class="col-sm-2 control-label">姓名</label>--%>
                <%--<div class="col-sm-2" >--%>
                    <%--<input type="text" name="name" id="name" class="form-control">--%>
                <%--</div>--%>
            <%--</div>--%>
            <div class="form-group">
                <div class="col-sm-12" >
                    <div class="pull-right" style="margin-top: 5px;">
                        <%--<div class="btn-group">--%>
                            <%--<shiro:hasPermission name="34search">--%>
                                <%--<button type="button" class="btn btn-primary btn-sm" id="btn-query">--%>
                                    <%--<i class="fa fa-search"></i> 查询--%>
                                <%--</button>--%>
                                <%--<button type="button" class="btn btn-primary btn-sm" id="btn-re">--%>
                                    <%--<i class="fa fa-refresh"></i> 刷新--%>
                                <%--</button>--%>
                            <%--</shiro:hasPermission>--%>
                        <%--</div>--%>
                        <div class="btn-group">
                            <shiro:hasPermission name="34add">
                                <button type="button" class="btn btn-primary btn-sm" id="addEvent">
                                    <i class="fa fa-search"></i> 新增
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
                <th style="width: 10%;">编号</th>
                <th style="width: 10%;">地区名称</th>
                <th style="width: 10%;">操作</th>
            </tr>
        </thead>
        <tbody></tbody>
    </table>



    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabe" aria-hidden="true">
        <div class="modal-dialog" style="width:50%;">
            <div class="modal-content" style=" width: 100%; ">
                <div class="modal-header" style=" width: 100%; ">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                        <span class="sr-only"></span>
                    </button>
                    <h4 class="modal-title" id="myModalLabe">新增</h4>
                </div>
                <div class="modal-body" style=" width: 100%; ">
                    <form role="form" class="form-horizontal" id="addForm" style=" width: 100%; ">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">地区名称</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control notEmtity" name="name" id="addName">
                            </div>
                        </div>
                    </form>
                    <!-- modal-body END -->
                    <div class="text-center">
                            <div class="btn-group">
                                <button type="button" class="btn btn-primary btn-lg" id="sureAdd">
                                    提交
                                </button>
                            </div>
                    </div>
                </div>
            </div>
        </div>
    </div>



    <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabe2" aria-hidden="true">
        <div class="modal-dialog" style="width:50%;">
            <div class="modal-content" style=" width: 100%; ">
                <div class="modal-header" style=" width: 100%; ">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                        <span class="sr-only"></span>
                    </button>
                    <h4 class="modal-title" id="myModalLabe2">修改</h4>
                </div>
                <div class="modal-body" style=" width: 100%; ">
                    <form role="form" class="form-horizontal" id="editForm" style=" width: 100%; ">
                        <input type="hidden" class="form-control notEmtity" name="id" id="updateId">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">地区名称</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" name="name" id="updateName">
                            </div>
                        </div>
                    </form>
                    <!-- modal-body END -->
                    <div class="text-center">
                            <div class="btn-group">
                                <button type="button" class="btn btn-primary btn-lg" id="sureUpdate">
                                    提交
                                </button>
                            </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
</html>
<%@ include file="../../../../currency/js.jsp"%>
<script>
    $(function () {
        var updateStr = "";
        <shiro:hasPermission  name="34update">
        updateStr+= "<div class='btn-group' style='margin-right: 5px;'>" +
            "<button  class='btn btn-primary btn-sm updateStr' type='button'>修改</button>" +
            "</div>"
        </shiro:hasPermission>


        var tables = $("#dataTable").dataTable(
            platform.getDatatableSettings('/api/address/dataGrid',
                'queryForm',
                [
                    {"data": "id"},
                    {"data": "name"},
                    {"data": 'columnDefs'}
                ],
                //行操作按钮定义
                [
                    {
                        targets: -1,
                        defaultContent: updateStr
                    }
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

//
//        //查询按钮
//        $("#btn-query").on("click", function () {
//            tables.fnDraw();//查询后不需要保持分页状态，回首页
//        });
//        //刷新
//        $("#btn-re").on("click", function () {
//            tables.fnDraw(false);//刷新保持分页状态
//        });


        $("#addEvent").click(function () {
            $("#addModal").modal("show");
        });

        $("#sureAdd").click(function () {
            if($("#addName").val() == ""){
                layer.msg("地区不能为空", {icon: 2});
                return;
            }
            $.ajax({
                type: "POST",  //提交方式
                url: platform.CONSTS.URL_BASE_CMS + "api/address/add",
                data: {
                    "name":$("#addName").val()
                },
                success: function (data) {//返回数据根据结果进行相应的处理
                    console.log(data);
                    if (data.success == true) {
                        layer.msg('提交成功！');
                        window.location.reload();
                    } else {
                        layer.msg(data.message, {icon: 2});
                        return;
                    }
                }, error: function (error) {
                    layer.msg(error.responseText, {icon: 2});
                }
            });
        });



        $("#dataTable tbody").on("click", ".updateStr", function () {
            var data = tables.api().row($(this).parents("tr")).data();
            console.log(data)
            $("#updateId").val(data.id);
            $("#updateName").val(data.name);
            $("#editModal").modal("show");
        });
        $("#sureUpdate").on("click", function () {
            if($("#updateName").val() == ""){
                layer.msg("地区不能为空", {icon: 2});
                return;
            }
            $.ajax({
                type: "POST",  //提交方式
                url: platform.CONSTS.URL_BASE_CMS + "api/address/update",
                data: {
                    "name":$("#updateName").val(),
                    "id":$("#updateId").val()
                },
                success: function (data) {//返回数据根据结果进行相应的处理
                    console.log(data);
                    if (data.success == true) {
                        layer.msg('提交成功！');
                        window.location.reload();
                    } else {
                        layer.msg(data.message, {icon: 2});
                        return;
                    }
                }, error: function (error) {
                    layer.msg(error.responseText, {icon: 2});
                }
            });
        });







    });



</script>
