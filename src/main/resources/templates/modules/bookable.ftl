<div class="m-bookable">
<#if Request.owner??>

    <#assign current = .now?string("yyyy-MM-dd")>
    <div class="m-bookable-owner" data-owner-id="${Request.owner.id}">${Request.owner.name}的可预约资源</div>
    <div class="m-bookable-search">
        <label for="start-datetime-picker">起始日期</label>
        <input id="start-datetime-picker" class="datetimepicker datetime-start" type="text" value="${current}">
        <label for="end-datetime-picker">截止日期</label>
        <input id="end-datetime-picker" class="datetimepicker datetime-start" type="text" value="${current}">
        <input id="search-bookable" type="button" value="搜索">
    </div>
    <!--div class="controller">
        <input id="save-bookings" class="btn" type="button" value="Book Now">
        <input id="clear-bookings" class="btn" type="button" value="Clear All">
    </div-->
    <div class="m-bookable-items-wrapper"></div>
<#else>
    <div class="m-bookable-owner-not-found">该服务商不存在</div>
</#if>
</div>
