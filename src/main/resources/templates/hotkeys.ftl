<html>
    <head>
        <#import "helpers/header.ftl" as header>
        <@header.globals/>
        <@header.header "BudgetMaster - ${locale.getString('menu.hotkeys')}"/>
        <@header.style "hotkeys"/>
    </head>
    <body class="budgetmaster-blue-light">
        <#import "helpers/navbar.ftl" as navbar>
        <@navbar.navbar "hotkeys" settings/>

        <main>
            <div class="card main-card background-color">
                <div class="container">
                    <div class="section center-align">
                        <div class="headline">${locale.getString("menu.hotkeys")}</div>
                    </div>
                </div>

                <br>

                <div class="row">
                    <@cellKey locale.getString("hotkeys.transactions.new.normal.key")/>
                    <div class="col s8 m5 l5">${locale.getString("hotkeys.transactions.new.normal")}</div>
                </div>
                <div class="row">
                    <@cellKey locale.getString("hotkeys.transactions.new.repeating.key")/>
                    <div class="col s8 m5 l5">${locale.getString("hotkeys.transactions.new.repeating")}</div>
                </div>
                <div class="row">
                    <@cellKey locale.getString("hotkeys.transactions.new.transfer.key")/>
                    <div class="col s8 m5 l5">${locale.getString("hotkeys.transactions.new.transfer")}</div>
                </div>
                <div class="row">
                    <@cellKey locale.getString("hotkeys.transactions.new.template.key")/>
                    <div class="col s8 m5 l5">${locale.getString("hotkeys.transactions.new.template")}</div>
                </div>
                <div class="row">
                    <@cellKeyWithModifier locale.getString("hotkeys.transactions.save.modifier") locale.getString("hotkeys.transactions.save.key")/>
                    <div class="col s8 m5 l5">${locale.getString("hotkeys.transactions.save")}</div>
                </div>
                <div class="row">
                    <@cellKey locale.getString("hotkeys.transactions.filter.key")/>
                    <div class="col s8 m5 l5">${locale.getString("hotkeys.transactions.filter")}</div>
                </div>
                <div class="row">
                    <@cellKey locale.getString("hotkeys.search.key")/>
                    <div class="col s8 m5 l5">${locale.getString("hotkeys.search")}</div>
                </div>
            </div>
        </main>

        <!--  Scripts-->
        <#import "helpers/scripts.ftl" as scripts>
        <@scripts.scripts/>
    </body>
</html>

<#macro cellKey key>
    <div class="col s4 m3 offset-m2 l2 offset-l3 right-align bold">
        <div class="keyboard-key">${key}</div>
    </div>
</#macro>

<#macro cellKeyWithModifier modifier key>
    <div class="col s4 m3 offset-m2 l2 offset-l3 right-align bold">
        <div class="keyboard-key modifier-key">${modifier}</div>
        <span class="bold">+</span>
        <div class="keyboard-key">${key}</div>
    </div>
</#macro>