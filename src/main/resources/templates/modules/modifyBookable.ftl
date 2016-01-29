<div class="m-modify-bookable wide-form">
<#if Request.error??>
    <div>
        <span>${Request.error}</span>
    </div>
</#if>
<#assign id = ''>
<#assign name = ''>
<#assign quantity = ''>
<#assign unit = ''>
<#if modifyBookableForm??>
    <#assign id = '${modifyBookableForm.id}'>
    <#assign name = '${modifyBookableForm.name}'>
    <#assign quantity = '${modifyBookableForm.quantity}'>
    <#assign unit = '${modifyBookableForm.unit}'>
</#if>
    <#include 'components/validationError.ftl'>
    <form class="modal-content" enctype="multipart/form-data" method="post" action="/modify-bookable.html">
        <input type="hidden" name="id" value="${id}">

        <div class="field">
            <label class="field-label">名称</label>
            <div class="field-ui"><input type="text" name="name" autocomplete="off" class="form-control" value="${name}"></div>
        </div>
        <div class="field">
            <label class="field-label">数量</label>
            <div class="field-ui"><input type="text" name="quantity" autocomplete="off" class="form-control" value="${quantity}"></div>
        </div>
        <div class="field">
            <label class="field-label">单位</label>
            <div class="field-ui"><input type="text" name="unit" autocomplete="off" class="form-control" value="${unit}"></div>
        </div>
        <div class="modal-footer">
            <button type="submit" class="btn btn-create">创建</button>
            <a href="/my-bookable.html">
                <button type="button" class="btn btn-link btn-cancel">取消</button>
            </a>
        </div>
    </form>
</div>