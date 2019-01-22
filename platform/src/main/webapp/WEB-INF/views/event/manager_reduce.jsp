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
                <div class="col-sm-4">
                    <div class="pull-right" style="margin-top: 5px;">
                        <div class="btn-group">
                            <shiro:hasPermission name="30search">
                                <button type="button" class="btn btn-primary btn-sm" id="btn-query">
                                    <i class="fa fa-search"></i> 查询
                                </button>
                                <button type="button" class="btn btn-primary btn-sm" id="btn-re">
                                    <i class="fa fa-refresh"></i> 刷新
                                </button>
                            </shiro:hasPermission>
                        </div>
                        <div class="btn-group">
                            <shiro:hasPermission name="30add">
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
            <th style="width: 6%;">迟到/未打卡时间</th>
            <th style="width: 10%;">时间H</th>
            <th style="width: 10%;">备注</th>
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
                            <select  class="selectpicker show-tick form-control" data-live-search="true">

                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">类型</label>
                        <div class="col-sm-6">
                            <select class="form-control notEmtity" name="type" id="type">
                                <option value="迟到">迟到</option>
                                <option value="未打卡">未打卡</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">迟到/未打卡时间</label>
                        <div class="col-sm-6">
                            <input type='text' placeholder="请选择时间" class="form-control  notEmtity recordTime"  name="recordTime" id="recordTime"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">工时：H</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control notEmtity" name="hours" id="hours">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">备注</label>
                        <div class="col-sm-6">
                            <textarea style="margin: 0px; width: 100%; height: 100px;" name="remarks"
                                      id="remarks"></textarea>
                        </div>
                    </div>
                </form>
                <!-- modal-body END -->
                <div class="text-center">
                    <shiro:hasPermission name="30add">
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

</body>
</html>
<%@ include file="../../../currency/js.jsp" %>
<script>
    $(function () {
        $('#recordTime').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
//            minuteStep:1,
            autoclose:true
        });

        var tables = $("#dataTable").dataTable(
            platform.getDatatableSettings('/api/event_reduce/dataGrid',
                'queryForm',
                [
                    {"data": "id"},
                    {"data": "name"},
                    {"data": "loginNumber"},
                    {"data": "type"},
                    {
                        "data": 'recordTime',
                        "render": function (data, type, full, callback) {
                            console.log(full)
                            return platform.timeStamp2String(data)
                        }
                    },
                    {"data": "hours"},
                    {"data": "remarks"}
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
            //判断名字是否为空
            var sltText =$(".filter-option").html();
            if(sltText == "Nothing selected"){
                layer.msg("姓名不能为空", {icon: 2});
                return;
            }
            var  sltValue = SelectValue($(".selectpicker"),sltText);
            param["userId"] = sltValue;
            var i = 0;
            var str;
            $(".notEmtity").each(function (n, obj) {
                if ($(obj).val() == "") {
                    str = $(obj).parent().prev().html() + "不能为空";
                    i = 1;
                    return false; //跳出循环
                }
                if($(obj).attr("name") == "recordTime"){
                    param[$(obj).attr("name")] = $(obj).val().replace("T"," ") + ":00";
                }else{
                    param[$(obj).attr("name")] = $(obj).val();
                }
            });
            param["remarks"] = $("#remarks").val();
            //判断是否出错
            if (i == 1) {
                layer.msg(str, {icon: 2});
                return;
            }
            $.ajax({
                type: "POST",  //提交方式
                url: platform.CONSTS.URL_BASE_CMS + "api/event_reduce/save",
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

        $.ajax({
            type: "POST",  //提交方式
            url: platform.CONSTS.URL_BASE_CMS + "api/user/findAll",
            success: function (data) {//返回数据根据结果进行相应的处理
                console.log(data);
                if (data.success == true) {
                    $('.selectpicker').empty();
                    $('.selectpicker').selectpicker('refresh');
                    var list = data.data;
                    for (var n = 0; n < list.length; n++) {
                        $(".selectpicker").append("<option value='"+list[n].id+"'>"+list[n].name+"</option>");
                    }
                    $('.selectpicker').selectpicker('refresh');
                } else {
                    layer.msg(data.message, {icon: 2});
                    return;
                }
            }, error: function (error) {
                layer.msg(error.responseText, {icon: 2});
            }
        });


        //jquery select下拉框通过text找value
        function SelectValue(obj, text) {
            var val = "";
            $(obj).find("option").each(function () {
                if ($(this).text() == text) {
                    val = $(this).val();
                    return false;
                }
            });
            return val;
        }


    });

</script>
