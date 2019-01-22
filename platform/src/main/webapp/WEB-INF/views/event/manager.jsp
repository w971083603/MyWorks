<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<%@ include file="../../../currency/top.jsp" %>
<body class="sidebar-mini">
<!-- 查询、添加、批量删除、导出、刷新 -->
<div class="content">
    <!-- 查询、添加、批量删除、导出、刷新 -->
    <div class="row-fluid">
        <form id="queryForm" class="form-horizontal" action="" method="post">
            <input type="hidden" name="loginUserId" id="loginUserId" value="<shiro:principal property="id"/>">
            <input type="hidden" name="rolesstr" id="rolesstr" value="<shiro:principal property="rolesstr"/>">
            <input type="hidden" name="addressId" id="addressId" value="<shiro:principal property="addressId"/>">
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">姓名</label>
                <div class="col-sm-2">
                    <input type="text" name="name" id="name" class="form-control">
                </div>
                <label for="loginNumber" class="col-sm-2 control-label">工号</label>
                <div class="col-sm-2">
                    <input type="text" name="loginNumber" id="loginNumber" class="form-control">
                </div>
                <label for="status" class="col-sm-2 control-label">状态</label>
                <div class="col-sm-2">
                    <select class="form-control" name="status" id="status">
                        <option value="0">待审核</option>
                        <option value="">全部</option>
                        <option value="1">审核通过</option>
                        <option value="2">审核不通过</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="pull-right" style="margin-top: 5px;">
                        <div class="btn-group">
                            <shiro:hasPermission name="11search">
                                <button type="button" class="btn btn-primary btn-sm" id="btn-query">
                                    <i class="fa fa-search"></i> 查询
                                </button>
                                <button type="button" class="btn btn-primary btn-sm" id="btn-re">
                                    <i class="fa fa-refresh"></i> 刷新
                                </button>
                            </shiro:hasPermission>
                        </div>
                        <div class="btn-group">
                            <shiro:hasPermission name="11add">
                                <button type="button" class="btn btn-primary btn-sm" id="addEvent">
                                    新增
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
            <th style="width: 6%;">序号</th>
            <th style="width: 6%;">姓名</th>
            <th style="width: 6%;">工号</th>
            <th style="width: 6%;">类型</th>
            <th style="width: 8%;">开始日期</th>
            <th style="width: 6%;">开始时间</th>
            <th style="width: 8%;">结束日期</th>
            <th style="width: 6%;">结束时间</th>
            <th style="width: 6%;">工时：H</th>
            <th style="width: 6%;">申请理由</th>
            <th style="width: 8%;">申请时间</th>
            <th style="width: 6%;">审核时间</th>
            <th style="width: 6%;">审核备注</th>
            <th style="width: 6%;">审核状态</th>
            <th style="width: 15%;">操作</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>


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
                        <label class="col-sm-3 control-label">姓名</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control name" disabled
                                   value="<shiro:principal property="name"/>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">类型</label>
                        <div class="col-sm-6">
                            <select class="form-control notEmtity" name="type" id="type">
                                <option value="加班">加班</option>
                                <option value="调休">调休</option>
                                <%--<option value="年假">年假</option>--%>
                                <%--<option value="事件">事件</option>--%>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">开始日期</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control notEmtity timeHours" name="startDay" id="startDay">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">开始时间</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control notEmtity timeHours" name="startTime" id="startTime">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">结束日期</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control notEmtity timeHours" name="endDay" id="endDay">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">结束时间</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control notEmtity timeHours" name="endTime" id="endTime">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">工时：H</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control notEmtity" name="hours" id="hours" onchange="isOkDouble(this)">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">申请事由</label>
                        <div class="col-sm-6">
                            <textarea style="margin: 0px; width: 100%; height: 100px;" class="notEmtity" name="reason"
                                      id="reason"></textarea>
                        </div>
                    </div>
                </form>
                <!-- modal-body END -->
                <div class="text-center">
                    <shiro:hasPermission name="11add">
                        <div class="btn-group">
                            <button type="button" class="btn btn-primary btn-lg" id="sureAdd">
                                提交
                            </button>
                        </div>
                    </shiro:hasPermission>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:50%;">
        <div class="modal-content" style=" width: 100%; ">
            <div class="modal-header" style=" width: 100%; ">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only"></span>
                </button>
                <h4 class="modal-title" id="myModalLabel">审核</h4>
            </div>
            <div class="modal-body" style=" width: 100%; ">
                <form role="form" class="form-horizontal" id="updateForm" style=" width: 100%; ">
                    <input type="hidden" class="eventId" value="">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">审核备注</label>
                        <div class="col-sm-6">
                            <textarea style="margin: 0px; width: 100%; height: 100px;" class="remarks"
                                      name="remarks"></textarea>
                        </div>
                    </div>
                </form>
                <!-- modal-body END -->
                <div class="text-center dk">
                    <shiro:hasPermission name="11examine">
                        <div class="btn-group" style="margin-right: 10%;">
                            <button type="button" class="btn btn-default btn-lg" id="errorExamine">
                                审核不通过
                            </button>
                        </div>
                        <div class="btn-group">
                            <button type="button" class="btn btn-primary btn-lg" id="passExamine">
                                审核通过
                            </button>
                        </div>
                    </shiro:hasPermission>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>
<%@ include file="../../../currency/js.jsp" %>
<script>
    $(function () {

        $('#startDay').datetimepicker({
            format: 'yyyy-mm-dd',
            minView:"2",
            autoclose:true
        });
        $('#startTime').datetimepicker({
            format: 'hh:ii',
            autoclose: 1,
            startView: 1,
            minView: 0,
            maxView: 1,
            forceParse: 0
        });
        $('#endDay').datetimepicker({
            format: 'yyyy-mm-dd',
            minView:"2",
            autoclose:true
        });
        $('#endTime').datetimepicker({
            format: 'hh:ii',
            autoclose: 1,
            startView: 1,
            minView: 0,
            maxView: 1,
            forceParse: 0
        });


        var examine = "";
        <shiro:hasPermission  name="11examine">
        examine += "<div class='btn-group' style='margin-right: 5px;'>" +
            "<button  class='btn btn-primary btn-sm examine'   type='button'>审核</button>" +
            "</div>"
        </shiro:hasPermission>

        var deleteStr = "";
        <shiro:hasPermission name="11delete">
        deleteStr+= "<div class='btn-group'>" +
            "<button class='btn btn-danger btn-sm deleteStr' type='button'>删除</button>" +
            "</div>"
        </shiro:hasPermission>


        var tables = $("#dataTable").dataTable(
            platform.getDatatableSettings('/api/event/dataGrid',
                'queryForm',
                [
                    {"data": "id"},
                    {"data": "name"},
                    {"data": "loginNumber"},
                    {"data": "type"},
                    {"data": "startDay"},
                    {"data": "startTime"},
                    {"data": "endDay"},
                    {"data": "endTime"},
                    {"data": "hours"},
                    {"data": "reason"},
                    {
                        "data": 'createTime',
                        "render": function (data, type, full, callback) {
                            return platform.timeStamp2String(data)
                        }
                    },
                    {
                        "data": 'examineTime',
                        "render": function (data, type, full, callback) {
                            return data == null ? "" : platform.timeStamp2String(data)
                        }
                    },
                    {"data": "examineReason"},
                    {
                        "data": 'status',
                        "render": function (data, type, full, callback) {
                            return data == "0" ? "待审核" : data == "1" ? "审核通过" : "审核不通过";
                        }
                    },
                    {
                        "data": 'status',
                        "render": function (data, type, full, callback) {
                            if (data == "0") {
                                return examine + " " + deleteStr;
                            }
                            if(data == "2"){
                                return deleteStr;
                            }
                            return "";
                        }
                    }
                ],
                //行操作按钮定义
                [],
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

        $("#addEvent").click(function () {
            $("#addModal").modal("show");
        });



        $("#sureAdd").click(function () {
            var param = {};
            var i = 0;
            var str;
            $(".notEmtity").each(function (n, obj) {
                if ($(obj).val() == "") {
                    str = $(obj).parent().prev().html() + "不能为空";
                    i = 1;
                    return false; //跳出循环
                }
                param[$(obj).attr("name")] = $(obj).val();
            });
            if (i == 1) {
                layer.msg(str, {icon: 2});
                return;
            }
            param["userId"] = <shiro:principal property="id"/>;
            console.log(param);
            //比较时间
            var start = $("#startDay").val() + " " + $("#startTime").val() + ":00";
            var end = $("#endDay").val() + " " + $("#endTime").val() + ":00";
            var starttime = new Date(Date.parse(start));
            var endtime = new Date(Date.parse(end));
            console.log(starttime);
            console.log(endtime);
            if (starttime > endtime) {
                layer.msg("开始时间不能大于结束时间", {icon: 2});
                return;
            }
            $.ajax({
                type: "POST",  //提交方式
                url: platform.CONSTS.URL_BASE_CMS + "api/event/save",
                data: param,
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


        $("#dataTable tbody").on("click", ".examine", function () {
            var data = tables.api().row($(this).parents("tr")).data();
            console.log(data)
            $(".eventId").val(data.id);
            $("#editModal").modal("show");
        });

        //审核通过按钮
        $("#passExamine").on("click", function () {
            layer.confirm('<span style="color:red">确定审核通过吗？</span>', {
                title: "审核",
                btn: ['确认']
            }, function (index) {
                layer.close(index);
                changeStatus("1");
            });
        });

        //审核不通过通过按钮
        $("#errorExamine").on("click", function () {
            layer.confirm('<span style="color:red">确定不通过审核吗？</span>', {
                title: "审核",
                btn: ['确认']
            }, function (index) {
                layer.close(index);
                if ($(".remarks").val() == "") {
                    $("#loadModal").hide();
                    layer.msg("备注信息不能为空", {icon: 2});
                } else {
                    changeStatus("2");
                }
            });
        });

        $("#dataTable tbody").on("click", ".deleteStr", function () {
            var data = tables.api().row($(this).parents("tr")).data();
            console.log(data)
            layer.confirm('<span style="color:red">确定删除吗？</span>', {
                title: "审核",
                btn: ['确认']
            }, function (index) {
                layer.close(index);
                $.ajax({
                    type: "POST",  //提交方式
                    url: platform.CONSTS.URL_BASE_CMS + "api/event/delete",
                    data: {
                        id: data.id
                    },
                    success: function (data) {//返回数据根据结果进行相应的处理
                        console.log(data);
                        if (data.success == true) {
                            layer.msg('操作完成！');
                            tables.fnDraw(false);//刷新保持分页状态
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

        //改变审核状态
        function changeStatus(status) {
            $.ajax({
                type: "POST",  //提交方式
                url: platform.CONSTS.URL_BASE_CMS + "api/event/updateStatus",
                data: {
                    eventId: $(".eventId").val(),
                    status: status,
                    examineReason: $(".remarks").val()
                },
                success: function (data) {//返回数据根据结果进行相应的处理
                    console.log(data);
                    if (data.success == true) {
                        layer.msg('审核处理完成！');
                        $(".remarks").val("");
                        $(".modal").modal("hide");
                        tables.fnDraw(false);//刷新保持分页状态
                    } else {
                        layer.msg(data.message, {icon: 2});
                        return;
                    }
                }, error: function (error) {
                    layer.msg(error.responseText, {icon: 2});
                }
            });
        }


    });
    //判断当前是不是数字
    function isOkDouble(obj) {
        if(isNaN($(obj).val())){
            layer.msg("工时请输入数值", {icon: 2});
            return;
        }
        $(obj).val($(obj).val().to_fixed(1));
    }

</script>
