<#import "*/layout/layout.ftl" as layout>
<#-- Specify which navbar element should be flagged as active -->
<#global nav="home">
<@layout.orcidLayout>

<div class="container">
    <div class="table-responsive">
        <table class="table table-striped">

            <thead>
                <tr class="tr">
                    <th>ORCID ID</th>
                    <th>Name</th>
                    <th>Last modified</th>
                </tr>
            </thead>

            <tbody>
                <#list profiles as profile>
                    <tr class="tr">
                        <td>
                            <a href="${springMacroRequestContext.getContextPath()}/${profile.identifier}">${profile.identifier}</a>
                        </td>
                        <td>${(profile.givenNames?html)!} ${(profile.familyName?html)!}</td>
                        <td>${(profile.lastModified?datetime)!}</td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>

</@layout.orcidLayout>
