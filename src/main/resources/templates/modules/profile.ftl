<#assign email=''>
<#if Session.user??>
    <#assign email='${Session.user.email}'>
</#if>
<div class="m-profile wide-form">

<#if Request.error??>
    <div class="m-error">
        <span>${Request.error}</span>
    </div>
</#if>
<#include 'components/validationError.ftl'>
    <form class="modal-content" enctype="multipart/form-data" method="post" action="/profile.html">
        <div class="field">
            <label class="field-label">电子邮箱</label>
            <div class="field-ui"><input type="text" name="email" class="form-control" value="${email}"></div>
        </div>
        <div class="modal-footer">
            <button type="submit" class="btn btn-save">保存</button>
            <a href="/index.html"><button type="button" class="btn btn-link btn-cancel">取消</button></a>
        </div>
    </form>

    <div class="m-change-password-link"><a href="/change-password.html">修改密码</a></div>
</div>