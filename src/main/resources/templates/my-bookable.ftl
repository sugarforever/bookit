<!DOCTYPE html>
<html>
    <#include "header.ftl">
    <body>
        <div class="m-page">
        <#include "leading.ftl">
            <div class="center-container">
                <div class="m-my-bookable">
                    <div class="m-my-bookable-control">
                        <a href="/create-bookable.html" class="m-create-bookable">创建</a>
                    </div>
                    <div class="m-my-bookable-items">
                        <ul>
                            <li>
                                <div class="m-item header">
                                    <div class="name">名称</div>
                                    <div class="last-modified">最近修改时间</div>
                                </div>
                            </li>
                        <#list Request.bookables as bookable>
                            <li>
                                <div class="m-item">
                                    <div class="name">${bookable.name}</div>
                                    <div class="last-modified">${bookable.lastModified?string["yyyy-MM-dd HH:mm:ss"]}</div>
                                </div>
                            </li>
                        </#list>
                        </ul>
                    </div>
                </div>
            </div>
        <#include "footer.ftl">
        </div>
    </body>
</html>