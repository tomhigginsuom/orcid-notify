<#import "*/layout/layout.ftl" as layout>
<#-- Specify which navbar element should be flagged as active -->
<#global nav="home">
<@layout.orcidLayout>

<div class="container">
    <div class="header">
        <h3><a href="http://orcid.org/${profile.identifier}">http://orcid.org/${profile.identifier}</a></h3>
        <p class="info">${(profile.givenNames?html)!} ${(profile.familyName?html)!}</p>
        <p class="info light">Modified ${(profile.lastModified?datetime)!}</p>
    </div>
</div>

<div class="container">
    <#if works?has_content>

        <div class="table-responsive">
            <table class="table table-striped">

                <thead>
                    <tr class="tr">
                        <th>Group</th>
                        <th>PutCode</th>
                        <th>Type</th>
                        <th>Title</th>
                        <th width="100px">Date</th>
                        <th>ID</th>
                        <th>Timestamp</th>
                    </tr>
                </thead>

                <tbody>
                    <#list works as work>
                        <tr class="tr">
                            <td>${work.group?c}</td>
                            <td>${work.putCode?c}</td>
                            <td>${work.workType?html}</td>
                            <td>${work.title?html}</td>

                            <#if work.year??>
                                <#if work.month??>
                                    <#if work.day??>
                                        <td>${work.year?c}-${work.month?left_pad(2,"0")}-${work.day?left_pad(2,"0")}</td>
                                    <#else>
                                        <td>${work.year?c}-${work.month?left_pad(2,"0")}</td>
                                    </#if>
                                <#else>
                                    <td>${work.year?c}</td>
                                </#if>
                            <#else>
                                <td></td>
                            </#if>
                            
                            <td><b>${(work.identifierType?html)!}</b> ${(work.identifier?html)!}</td>
                            <td>${(work.created?datetime)!}</td>
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
