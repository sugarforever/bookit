<div class="m-login wide-form">
<#if Request.error??>
    <div class="m-error">
        <span>${Request.error}</span>
    </div>
</#if>

    <form class="modal-content" enctype="multipart/form-data" method="post" action="/login.html">
        <div class="field">
            <label class="field-label">用户名</label>

            <div class="field-ui"><input type="text" name="name" class="form-control"></div>
        </div>
        <div class="field">
            <label class="field-label">密码</label>

            <div class="field-ui"><input type="password" name="password" placeholder="" autocomplete="off"
                                         class="form-control"></div>
        </div>
        <div class="modal-footer">
            <button type="submit" class="btn btn-create">登录</button>
            <a href="/index.html"><button type="button" class="btn btn-link btn-cancel">取消</button></a>
        </div>
    </form>
</div>