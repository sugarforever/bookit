<div class="m-bookable-status">
    <#assign current = .now?string("yyyy-MM-dd")>
    <div class="m-bookable-status-search m-start-end-date-search">
        <label for="start-datetime-picker">起始日期</label>
        <input id="start-datetime-picker" class="datetimepicker datetime-start" type="text" value="${current}">
        <label for="end-datetime-picker">截止日期</label>
        <input id="end-datetime-picker" class="datetimepicker datetime-end" type="text" value="${current}">
        <input id="search-bookable-status" class="search-button" type="button" value="搜索">
    </div>
    <!--div class="controller">
        <input id="save-bookings" class="btn" type="button" value="Book Now">
        <input id="clear-bookings" class="btn" type="button" value="Clear All">
    </div-->
    <div class="m-bookable-status-items-wrapper">
        <div class="header">
            <div class="name">资源名称</div>
            <div class="holders">预约者</div>
            <div class="clearfix"></div>
        </div>
        <div class="rows"></div>
    </div>
</div>
