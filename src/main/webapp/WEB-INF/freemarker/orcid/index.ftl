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
                </tr>
            </thead>

            <tbody>
                <#list orcids as orcid>
                    <tr class="tr">
                        <td>
                            <a href="${springMacroRequestContext.getContextPath()}/${orcid}">${orcid}</a>
                        </td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>

</@layout.orcidLayout>
