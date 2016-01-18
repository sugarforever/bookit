<!DOCTYPE html>
<html>
<#if !Request.requireHeader?? || Request.requireHeader>
    <#include "modules/header.ftl">
</#if>
<body>
    <div class="m-page">
    <#if !Request.requireLeading?? || Request.requireLeading>
        <#include "modules/leading.ftl">
    </#if>
        <div class="center-container">
            <#if Request.modules??>
                <#list Request.modules as module>
                    <#include "modules/${module}.ftl">
                </#list>
            </#if>
        </div>
    <#if !Request.requireFooter?? || Request.requireFooter>
        <#include "modules/footer.ftl">
    </#if>
    </div>
</body>
</html>