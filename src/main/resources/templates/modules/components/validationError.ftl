<#if Request.errors??>
<div class="validation-error">
    <ul>
    <#list Request.errors as error>
        <#assign message = "">
        <#list error.codes as code>
            <#if message == "" >
                <#assign message><@spring.messageText code ''></@spring.messageText></#assign>
            </#if>
        </#list>
        <#if message == "" >
            <#assign message>${error.defaultMessage}</#assign>
        </#if>
        <li>${message}</li>
    </#list>
    </ul>
</div>
</#if>