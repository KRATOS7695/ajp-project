<#import "/spring.ftl" as s>
<#import "../helpers/validation.ftl" as validation>
<#import "../categories/categoriesFunctions.ftl" as categoriesFunctions>


<#macro buttonNew>
    <@header.buttonLink url='/templates/newTemplate' icon='add' localizationKey='title.template.new'/>
</#macro>

<#macro buttonEditTemplateGroups>
    <@header.buttonLink url='/templateGroups' icon='folder' localizationKey='title.template.group.edit.short'/>
</#macro>

<#macro buttons>
    <div class="row valign-wrapper hide-on-small-only">
        <div class="col s6 right-align">
            <@buttonNew/>
        </div>

        <div class="col s6 left-align">
            <@buttonEditTemplateGroups/>
        </div>
    </div>
    <div class="hide-on-med-and-up">
        <div class="row valign-wrapper center-align">
            <div class="col s12">
                <@buttonNew/>
            </div>
        </div>
        <div class="row valign-wrapper center-align">
            <div class="col s12">
                <@buttonEditTemplateGroups/>
            </div>
        </div>
    </div>
</#macro>


<#macro listTemplates templatesByGroup>
    <div class="container">
        <div class="row">
            <div class="col s12">
                <#list templatesByGroup as templateGroup, templates>
                    <ul class="collapsible expandable templateCollapsible" data-group-id="${templateGroup.ID?c}">
                        <#if templatesByGroup?size != 1>
                            <div class="template-group-headline">${getTemplateGroupName(templateGroup)}</div>
                        </#if>

                        <#list templates as template>
                            <@templateItem template/>
                        </#list>
                    </ul>
                </#list>
            </div>
        </div>
    </div>

     <form id="form-move-template-to-group" method="post" action="<@s.url '/templateGroups/moveTemplateToGroup'/>">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="templateID" value=""/>
        <input type="hidden" name="groupID" value=""/>
     </form>
</#macro>

<#macro templateItem template>
    <li class="template-item z-depth-2" data-template-id="${template.ID?c}">
        <div class="collapsible-header bold">
            <@templateHeader template/>
            <div class="collapsible-header-button">
                <@header.buttonFlat url='/templates/' + template.ID?c + '/edit' icon='edit' localizationKey='' classes="no-padding text-default"/>
                <@header.buttonFlat url='/templates/' + template.ID?c + '/requestDelete' icon='delete' localizationKey='' classes="no-padding text-default button-request-delete-template" isDataUrl=true/>
                <@header.buttonLink url='/templates/' + template.ID?c + '/select' icon='note_add' localizationKey='' classes='button-select-template'/>
            </div>
        </div>
        <div class="collapsible-body">
            <div class="row no-margin-bottom">
                <table class="table-template-content text-default">
                    <@templateName template/>
                    <@templateAmount template/>
                    <@templateCategory template/>
                    <@templateDescription template/>
                    <@templateTags template/>
                    <@templateAccount template/>
                    <@templateTransferAccount template/>
                </table>
            </div>
        </div>
    </li>
</#macro>

<#function getTemplateGroupName templateGroup>
    <#if templateGroup.getType().name() == "DEFAULT">
        <#return locale.getString("template.group.default")>
    <#else>
        <#return templateGroup.getName()>
    </#if>
</#function>


<#macro templateHeader template>
    <span style="color: ${template.getFontColor(settings.isUseDarkTheme())}">
        <#if template.getIconReference()?? && (template.getIconReference().isImageIcon() || template.getIconReference().isBuiltinIcon())>
            <@header.entityIcon entity=template classes="template-icon"/>
        <#elseif template.getTransferAccount()??>
            <i class="material-icons">swap_horiz</i>
        <#else>
            <i class="material-icons">payment</i>
        </#if>
    </span>
    <div class="truncate template-header-name">${template.getTemplateName()}</div>
</#macro>

<#macro templateName template>
    <#if template.getName()?has_content>
        <tr>
            <td class="template-content-label">${locale.getString("transaction.new.label.name")}</td>
            <td>${template.getName()}</td>
        </tr>
    </#if>
</#macro>

<#macro templateAmount template>
    <#if template.getAmount()??>
        <tr>
            <td class="template-content-label">${locale.getString("transaction.new.label.amount")}</td>
            <td>${currencyService.getCurrencyString(template.getAmount())}</td>
        </tr>
    </#if>
</#macro>

<#macro templateCategory template>
    <#if template.getCategory()??>
        <tr>
            <td class="template-content-label">${locale.getString("transaction.new.label.category")}</td>
            <td>${categoriesFunctions.getCategoryName(template.category)}</td>
        </tr>
    </#if>
</#macro>

<#macro templateDescription template>
    <#if template.getDescription()?has_content>
        <tr>
            <td class="template-content-label">${locale.getString("transaction.new.label.description")}</td>
            <td>${template.getDescription()}</td>
        </tr>
    </#if>
</#macro>

<#macro templateTags template>
    <#if template.getTags()?? && template.getTags()?size gt 0>
        <tr>
            <td class="template-content-label">${locale.getString("transaction.new.label.tags")}</td>
            <td class="chips-placeholder">
                <#list template.getTags() as tag>
                    <div class="chip">${tag.getName()}</div>
                </#list>
            </td>
        </tr>
    </#if>
</#macro>

<#macro templateAccount template>
    <#if template.getAccount()??>
        <tr>
            <td class="template-content-label">${locale.getString("transaction.new.label.account")}</td>
            <td>${template.getAccount().getName()}</td>
        </tr>
    </#if>
</#macro>

<#macro templateTransferAccount template>
    <#if template.getTransferAccount()??>
        <tr>
            <td class="template-content-label">${locale.getString("transaction.new.label.transfer.account")}</td>
            <td>${template.getTransferAccount().getName()}</td>
        </tr>
    </#if>
</#macro>

<#macro templateNameInput template>
    <div class="row">
        <div class="input-field col s12 m12 l8 offset-l2">
            <i class="material-icons prefix">file_copy</i>
            <input id="template-name" type="text" name="templateName" <@validation.validation "templateName"/> value="<#if template.getTemplateName()??>${template.getTemplateName()}</#if>">
            <label for="template-name">${locale.getString("template.new.label.name")}</label>
        </div>
    </div>
</#macro>

<#macro templateIncludeAccountCheckbox id name label checked>
    <div class="row">
        <div class="input-field col s12 m12 l8 offset-l2">
            <label>
                <input id="${id}" name="${name}" type="checkbox" <#if checked>checked</#if>>
                <span class="columnName-checkbox-label text-default">${label}</span>
            </label>
        </div>
    </div>
</#macro>