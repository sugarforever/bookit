<div class="m-change-password wide-form">
<#if Request.error??>
    <div class="m-error">
        <span>${Request.error}</span>
    </div>
</#if>
<#include 'components/validationError.ftl'>
    <form class="modal-content" enctype="multipart/form-data" method="post" action="/change-password.html">
        <div class="field">
            <label class="field-label">当前密码</label>
            <div class="field-ui">
                <input type="password" name="password" placeholder="" autocomplete="off" class="form-control">
            </div>
        </div>
        <div class="field">
            <label class="field-label">新密码</label>
            <div class="field-ui">
                <input type="password" name="newPassword" placeholder="" autocomplete="off" class="form-control">
            </div>
        </div>
        <div class="modal-footer">
            <button type="submit" class="btn btn-create">保存</button>
            <a href="/index.html"><button type="button" class="btn btn-link btn-cancel">取消</button></a>
        </div>
    </form>
</div>