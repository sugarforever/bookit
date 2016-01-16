<div class="top-container">
    <#if Session?? && Session.user?? && Session.user.name??>
        <span class="welcome">${Session.user.name}</span><a class="logout" href="logout.html">退出</a>

        <ul class="options">
            <li><a href="/my-bookable.html">我是预约资源供应商</a></li>
            <li><a href="/supplier.html">我要预约</a></li>
            <li><a href="/booking.html">我的预约</a></li>
        </ul>
    <#else>
        <a class="m-item" href="login.html">登录</a><a class="m-item" href="signup.html">注册</a>
    </#if>
</div>