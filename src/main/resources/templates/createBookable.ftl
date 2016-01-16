<html>
    <#include "header.ftl">
<body>
    <div class="m-page">
    <#include "leading.ftl">
        <div class="center-container">
            <div class="m-create-bookable wide-form">
                <#if Request.error??>
                    <div>
                        <span>${Request.error}</span>
                    </div>
                </#if>
                <form class="modal-content" enctype="multipart/form-data" method="post" action="/create-bookable.html">
                    <div class="field">
                        <label class="field-label">名称</label>
                        <div class="field-ui"><input type="text" name="name" autocomplete="off" class="form-control"></div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-create">创建</button>
                        <button type="button" class="btn btn-link btn-cancel"><a href="/my-bookable.html">取消</a></button>
                    </div>
                </form>
            </div>
        </div>
    <#include "footer.ftl">
    </div>
</body>
</html>