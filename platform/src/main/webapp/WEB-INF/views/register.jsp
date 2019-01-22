<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<%@ include file="../../currency/top.jsp" %>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo" style="color: #ffffff">
        管理后台
    </div><!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg"></p>
        <form id="form_register" action="" method="post">
            <div class="form-group has-feedback">
                <select class="form-control" name="addressId" id="addressId">

                </select>
            </div>
            <div class="form-group has-feedback">
                <input name="name" type="text" class="form-control" id="name" placeholder="请输入姓名">
            </div>
            <div class="form-group has-feedback">
                <input name="loginNumber" type="text" class="form-control" id="loginNumber" placeholder="请输入工号">
            </div>
            <div class="form-group has-feedback">
                <input name="department" type="text" class="form-control" id="department" placeholder="请输入部门">
            </div>
            <div class="form-group has-feedback">
                <input name="station" type="text" class="form-control" id="station" placeholder="请输入岗位">
            </div>
            <div class="form-group has-feedback">
                <input name="phone" type="text" class="form-control" id="phone" placeholder="请输入手机号">
            </div>
            <div class="form-group has-feedback">
                <input name="password" type="text" class="form-control" id="password" placeholder="请输入密码">
            </div>
            <div class="form-group has-feedback">
                <input name="surePassword" type="text" class="form-control" id="surePassword" placeholder="请确认密码">
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox pull-left"><a class="goLogin" style="font-size: 12px;cursor: pointer;">已有账号，前往登录</a>
                    </div>
                </div><!-- /.col -->
                <div class="col-xs-4">
                    <button id="btn_login" type="submit" class="btn btn-primary btn-block btn-flat"> 注册</button>
                </div><!-- /.col -->
            </div>
        </form>
    </div><!-- /.login-box-body -->
</div><!-- /.login-box -->
</body>
</html>
<%@ include file="../../currency/js.jsp" %>
<script>
    //注册
    $(document).ready(function () {
        //登录表单验证
        $('#form_register').bootstrapValidator({
            message: '提交数据不能全为空',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                name: {
                    validators: {
                        notEmpty: {
                            message: '姓名不能为空'
                        }
                    }
                },
                loginNumber: {
                    validators: {
                        notEmpty: {
                            message: '工号不能为空'
                        }
                    }
                },
                department: {
                    validators: {
                        notEmpty: {
                            message: '部门不能为空'
                        }
                    }
                },
                station: {
                    validators: {
                        notEmpty: {
                            message: '岗位不能为空'
                        }
                    }
                },
                phone: {
                    validators: {
                        notEmpty: {
                            message: '电话不能为空'
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            message: '密码长度至少6位'
                        }
                    }
                },
                surePassword: {
                    validators: {
                        notEmpty: {
                            message: '确认密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            message: '密码长度至少6位'
                        }
                    }
                }
            } //验证内容
        }).on('success.form.bv', function (e) {
            e.preventDefault();
            login();
        });

        function login() {
            var password = $("#password").val();
            var surePassword = $("#surePassword").val();
            if (surePassword != password) {
                layer.msg("两个输入密码不一致", {icon: 2});
                return;
            }
            $.ajax({
                type: "POST",  //提交方式
                url: platform.CONSTS.URL_BASE_CMS + "api/user/register",
                data: {
                    addressId: $("#addressId").val(),
                    name: $("#name").val(),
                    loginNumber: $("#loginNumber").val(),
                    department: $("#department").val(),
                    station: $("#station").val(),
                    phone: $("#phone").val(),
                    password: $("#password").val()
                },
                success: function (data) {//返回数据根据结果进行相应的处理
                    console.log(data);
                    if (data.success == true) {
                        layer.msg('注册成功！');
                        window.location.href = platform.CONSTS.URL_BASE_CMS + 'login';
                    } else {
                        layer.msg(data.message, {icon: 2});
                        return;
                    }
                }, error: function (error) {
                    layer.msg(error.responseText, {icon: 2});
                }
            });
        }

        //重新启用按钮
        $('#form_register').bootstrapValidator('disableSubmitButtons', false);


        $(".goLogin").click(function () {
            window.location.href = platform.CONSTS.URL_BASE_CMS + 'login';
        });


        $.ajax({
            type: "POST",  //提交方式
            url: platform.CONSTS.URL_BASE_CMS + "api/address/listAll",
            data: {},
            success: function (data) {//返回数据根据结果进行相应的处理
                console.log(data);
                if (data.success == true) {
                    var list = data.data;
                    $("#addressId").empty();
                    var s = "";
                    for (var i = 0; i < list.length; i++) {
                        s += "<option value='" + list[i].id + "'>" + list[i].name + "</option>"
                    }
                    $("#addressId").append(s);
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