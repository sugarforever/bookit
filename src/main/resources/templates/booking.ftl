<html>
<#include "header.ftl">
<body>
    <div class="m-page">
    <#include "leading.ftl">
        <div class="center-container">
            <div class="m-booking">
                <div class="m-pagination">
                    <ul class="pagination">
                    <#list Request.pages as page>
                        <#assign cssClass = "">
                        <#if Request.currentPage?? && Request.currentPage == page>
                            <#assign cssClass = 'current'>
                        </#if>
                        <li><a class="page-item ${cssClass}" href="booking.html?page=${page}">${page}</a></li>
                    </#list>
                    </ul>
                </div>
                <div class="m-booking-items-wrapper">
                <#list Request.bookedFors as bookedFor>
                    <div class="m-bookedfor"><span>${bookedFor}</span></div>
                    <#assign bookings = Request.bookings[bookedFor]>

                    <div class="m-booking-items">
                        <#list bookings as booking>
                            <div class="m-booking-item">
                                <a href="#" class="m-booking-cancel" data-booking-id="${booking.id}">取消</a>
                                <div class="m-booking-name" data-booking-id="${booking.id}">${booking.bookable.name}</div>
                            </div>
                        </#list>
                    </div>
                </#list>
                </div>
            </div>
        </div>
    <#include "footer.ftl">
</body>
</html>