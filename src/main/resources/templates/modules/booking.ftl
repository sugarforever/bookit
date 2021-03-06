<div class="m-booking">
    <div class="m-pagination">
        <ul class="pagination">
        <#list Request.pages as page>
            <li>
                <#if Request.currentPage?? && Request.currentPage == page>
                    <span class="page-item current">${page}</span>
                <#else>
                    <a class="page-item" href="booking.html?page=${page}">${page}</a>
                </#if>
            </li>
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
        