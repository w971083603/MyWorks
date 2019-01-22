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
            <input type="hidden" name="rolesstr" id="rolesstr" value="<shiro:principal property="rolesstr"/>">
            <input type="hidden" name="loginUserId" id="loginUserId" value="<shiro:principal property="id"/>">
            <input type="hidden" name="addressId" id="addressId" value="<shiro:principal property="addressId"/>">
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">姓名</label>
                <div class="col-sm-2" >
                    <input type="text" name="name" id="name" class="form-control">
                </div>
                <label for="phone" class="col-sm-2 control-label">手机号</label>
                <div class="col-sm-2" >
                    <input type="text" name="phone" id="phone" class="form-control">
                </div>
                <label for="loginNumber" class="col-sm-2 control-label">工号</label>
                <div class="col-sm-2" >
                    <input type="text" name="loginNumber" id="loginNumber" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="searchAddressId" class="col-sm-2 control-label">所属地区</label>
                <div class="col-sm-2" >
                    <select class="form-control" name="searchAddressId" id="searchAddressId">

                    </select>
                </div>
                <div class="col-sm-8" >
                    <div class="pull-right" style="margin-top: 5px;">
                        <div class="btn-group">
                            <shiro:hasPermission name="2search">
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
                <th style="width: 10%;">手机号</th>
                <th style="width: 10%;">部门</th>
                <th style="width: 10%;">职位</th>
                <th style="width: 10%;">注册时间</th>
                <th style="width: 10%;">所属地区</th>
                <th style="width: 10%;">操作</th>
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
        var restartStr = "";
        <shiro:hasPermission  name="2update">
        restartStr+= "<div class='btn-group' style='margin-right: 5px;'>" +
            "<button  class='btn btn-primary btn-sm restartPassword' type='button'>重置密码</button>" +
            "</div>"
        </shiro:hasPermission>


        var tables = $("#dataTable").dataTable(
            platform.getDatatableSettings('/api/user/dataGrid',
                'queryForm',
                [
                    {"data": "loginNumber"},
                    {"data": "name"},
                    {"data": "phone"},
                    {"data": "department"},
                    {"data": "station"},
                    {
                        "data": 'createTime',
                        "render": function (data, type, full, callback) {
                            return platform.timeStamp2String(data)
                        }
                    },
                    {"data": "addressName"},
                    {"data": 'columnDefs'}
                ],
                //行操作按钮定义
                [
                    {
                        targets: -1,
                        defaultContent: restartStr
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


        //查询按钮
        $("#btn-query").on("click", function () {
            tables.fnDraw();//查询后不需要保持分页状态，回首页
        });

        //刷新
        $("#btn-re").on("click", function () {
            tables.fnDraw(false);//刷新保持分页状态
        });

        $("#dataTable tbody").on("click", ".restartPassword", function () {
            var data = tables.api().row($(this).parents("tr")).data();
            console.log(data)
            $.ajax({
                type: "POST",  //提交方式
                url: platform.CONSTS.URL_BASE_CMS + "api/user/restartPassword",
                data: {
                    userId: data.id
                },
                success: function (data) {//返回数据根据结果进行相应的处理
                    console.log(data);
                    if (data.success == true) {
                        layer.msg('密码重置成功！');
                    } else {
                        layer.msg(data.message, {icon: 2}); return;
                    }
                }, error: function (error) {
                    layer.msg(error.responseText, {icon: 2});
                }
            });
        });


        $.ajax({
            type: "POST",  //提交方式
            url: platform.CONSTS.URL_BASE_CMS + "api/address/listAll",
            data: {},
            success: function (data) {//返回数据根据结果进行相应的处理
                console.log(data);
                if (data.success == true) {
                    var list = data.data;
                    $("#searchAddressId").empty();
                    var s = "<option value=''>全部</option>";
                    for (var i = 0; i < list.length; i++) {
                        s += "<option value='" + list[i].id + "'>" + list[i].name + "</option>";
                    }
                    $("#searchAddressId").append(s);
                } else {
                    layer.msg(data.message, {icon: 2});
                    return;
                }
            }, error: function (error) {
                layer.msg(error.responseText, {icon: 2});
            }
        });

    });



</script>
