<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="../../currency/top.jsp"%>
<body class="hold-transition login-page" >
<div class="login-box">
    <input type="hidden" class="message" value="${sessionScope.message}"/>
    <div class="login-logo" style="color: #ffffff">
        管理后台
    </div><!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg"></p>
        <form id="form_login" action="" method="post">
            <div class="form-group has-feedback messageDiv" style="color: red;font-size: 18px;display: none;" >
                <span>${sessionScope.message}</span>
            </div>
            <div class="form-group has-feedback">
                <input name="jg_login_user" type="text" class="form-control" id="userName" placeholder="工号/手机号">
                <span class="glyphicon glyphicon-user form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input name="jg_login_pwd" type="password" class="form-control" id="password" placeholder="密码">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">

            </div>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox pull-left">
                        <a class="register" style="font-size: 12px;cursor: pointer;">&nbsp;前往注册</a>
                    </div>
                </div><!-- /.col -->
                <div class="col-xs-4" >
                    <button id="btn_login" type="submit" class="btn btn-primary btn-block btn-flat">登录
                    </button>
                </div><!-- /.col -->
            </div>
        </form>
    </div><!-- /.login-box-body -->
</div><!-- /.login-box -->
</body>
</html>
<%@ include file="../../currency/js.jsp"%>
<script language="javascript">
    if (top != window)
        top.location.href = window.location.href;
</script>
<script>
    //单点登录
    if($(".message").val() != ""){
        $(".messageDiv").show();
    }
    //登录
    $(document).ready(function() {
        //登录表单验证
        $('#form_login').bootstrapValidator({
            message: '提交数据不能全为空',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                /*验证*/
                jg_login_user: {
                    /*键名username和input name值对应*/
                    message: '用户验证失败',
                    validators: {
                        notEmpty: {
                            /*非空提示*/
                            message: '工号不能为空'
                        },
                        regexp: {
                            /* 只需加此键值对，包含正则表达式，和提示 */
                            regexp: /[a-zA-Z0-9_]{2,16}/ ,
                            message: '请正确输入您的工号'
                        }
                    }
                },
                jg_login_pwd: {
                    message: '密码无效',
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        stringLength: {
                            min: 1,
//                            max: 6,
                            message: '密码长度至少1位'
                        }
                    }
                }
            } //验证内容
        }).on('success.form.bv', function (e) {
            e.preventDefault();
            login();
        });
        function login(){
            //这里提交表单
            $.post(platform.CONSTS.URL_BASE_CMS+'login', {
                loginNumber: $("#userName").val(),
                password: $("#password").val()
            }, function (result) {
                if (result.msg == "") {
                    window.location.href=platform.CONSTS.URL_BASE_CMS+'index';
                }else{
                    layer.msg(result.msg, { icon: 2 });
                }
            });
        }
        //重新启用按钮
        $('#form_login').bootstrapValidator('disableSubmitButtons', false);


        $(".register").click(function () {
            window.location.href=platform.CONSTS.URL_BASE_CMS+'register';
        });

    });
</script>