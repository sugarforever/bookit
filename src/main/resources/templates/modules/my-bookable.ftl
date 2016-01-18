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
                    <div class="name cell">
                        <span>${bookable.name}</span>
                        <div class="icon"></div>
                        <div class="edit">
                            <ul>
                                <li><a href="/modify-bookable.html?id=${bookable.id}" class="modify">编辑</a></li>
                                <li><a href="javascript:void(0)" class="delete" data-bookable-id="${bookable.id}">删除</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="last-modified cell">${bookable.lastModified?string["yyyy-MM-dd HH:mm:ss"]}</div>
                </div>
            </li>
        </#list>
        </ul>
    </div>
</div>
