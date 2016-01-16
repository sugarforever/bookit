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
                            <#assign cssClass = "">
                            <#if Request.currentPage?? && Request.currentPage == page>
                                <#assign cssClass = 'current'>
                            </#if>
                            <li><a class="page-item ${cssClass}" href="supplier.html?page=${page}">${page}</a></li>
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