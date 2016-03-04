<#import "*/layout/layout.ftl" as layout>
<#-- Specify which navbar element should be flagged as active -->
<#global nav="home">
<@layout.orcidLayout>

<div class="container">
    <div class="header">
        <h3><a href="http://orcid.org/${profile.identifier}">http://orcid.org/${profile.identifier}</a></h3>

        <#if profile.lastModified?has_content>
            <p class="info light">${(profile.lastModified?datetime)!}</p>
        </#if>

        <#if profile.givenNames?has_content || profile.familyName?has_content>
            <p class="info">${(profile.givenNames?html)!} ${(profile.familyName?html)!}</p>
        </#if>
        
        <#if workGroups?has_content>
            <p class="info">${(workGroups?size)} works</p>
        </#if>
    </div>
</div>

<div class="container">
    <#if workGroups?has_content>

        <div class="table-responsive">
            <table class="table table-striped">

                <thead>
                    <tr class="tr">
                        <th>Type</th>
                        <th>Title</th>
                        <th width="100px">Date</th>
                        <th>Identifiers</th>
                    </tr>
                </thead>

                <tbody>
                    <#list workGroups as workGroup>
                        <tr class="tr">
                            <td>${workGroup.workType?html}</td>
                            <td>${workGroup.title?html}</td>

                            <#if workGroup.year??>
                                <#if workGroup.month??>
                                    <#if workGroup.day??>
                                        <td>${workGroup.year?c}-${workGroup.month?left_pad(2,"0")}-${workGroup.day?left_pad(2,"0")}</td>
                                    <#else>
                                        <td>${workGroup.year?c}-${workGroup.month?left_pad(2,"0")}</td>
                                    </#if>
                                <#else>
                                    <td>${workGroup.year?c}</td>
                                </#if>
                            <#else>
                                <td></td>
                            </#if>
                            
                            <td style="font-family:monospace;">
                                <#if workGroup.works?has_content>
                                    <#list workGroup.works as work>
                                        <#if work.identifier?has_content>
                                            <b>${(work.identifierType?html)!}</b>:&nbsp;${(work.identifier?html)!}<br>
                                        </#if>
                                    </#list>
                                </#if>
                            </td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </div>

    <#else>
        <div class="row">
            <div class="alert alert-info" role="alert">
                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                <span class="sr-only">Success:</span>
                No works found
            </div>
        </div>
    </#if>

</div>

</@layout.orcidLayout>
