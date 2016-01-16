<html>
<#include "header.ftl">
    <body>
        <div class="m-page">
            <div class="center-container">
            <#if Request.error??>
                <div>
                    <span>${Request.error}</span>
                </div>
            </#if>
                <div class="m-login wide-form">
                    <form class="modal-content" enctype="multipart/form-data" method="post" action="/login.html">
                        <div class="field">
                            <label class="field-label">用户名</label>
                            <div class="field-ui"><input type="text" name="name" class="form-control"></div>
                        </div>
                        <div class="field">
                            <label class="field-label">密码</label>
                            <div class="field-ui"><input type="password" name="password" placeholder="" autocomplete="off" class="form-control"></div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-create">登录</button>
                            <button type="button" class="btn btn-link btn-cancel"><a href="/index.html">取消</a></button>
                        </div>
                    </form>
                </div>
            </div>
            <#include "footer.ftl">
        </div>
    </body>
</html>