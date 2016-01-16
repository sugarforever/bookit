<html>
<#include "header.ftl">
<body>
    <div class="m-page">
        <#include "leading.ftl">
            <div class="center-container">
                <div class="m-supplier">
                    <div class="m-pagination">
                        <ul class="pagination">
                        <#list Request.pages as page>
                            <li>
                            <#if Request.currentPage?? && Request.currentPage == page>
                                <span class="page-item current">${page}</span>
                            <#else>
                                <a class="page-item" href="supplier.html?page=${page}">${page}</a>
                            </#if>
                            </li>
                        </#list>
                        </ul>
                    </div>

                    <div class="m-supplier-items-wrapper">
                    <#list Request.suppliers as supplier>
                        <div class="m-supplier-item">
                            <a href="bookable.html?ownerId=${supplier.id}" class="m-supplier-bookable">${supplier.name}</a>
                        </div>
                    </#list>
                    </div>
                </div>
            </div>
        <#include "footer.ftl">
    </div>
</body>
</html>