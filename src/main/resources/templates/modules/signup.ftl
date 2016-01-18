<#if Request.error??>
<div>
    <span>${Request.error}</span>
</div>
</#if>
<div class="m-signup wide-form">
    <form class="modal-content" enctype="multipart/form-data" method="post" action="/signup.html">
        <div class="field">
            <label class="field-label">用户名</label>

            <div class="field-ui"><input type="text" name="name" class="form-control"></div>
        </div>
        <div class="field">
            <label class="field-label">电子邮箱</label>

            <div class="field-ui"><input type="text" name="email" class="form-control"></div>
        </div>
        <div class="field">
            <label class="field-label">密码</label>

            <div class="field-ui"><input type="password" name="password" class="form-control"></div>
        </div>
        <div class="modal-footer">
            <button type="submit" class="btn btn-create">注册</button>
            <a href="/index.html"><button type="button" class="btn btn-link btn-cancel">取消</button></a>
        </div>
    </form>
</div>