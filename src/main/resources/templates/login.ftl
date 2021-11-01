<html>
    <head>
        <#import "helpers/header.ftl" as header>
        <@header.globals/>
        <@header.header "BudgetMaster - ${locale.getString('login.button')}"/>
        <@header.style "login"/>
        <#import "/spring.ftl" as s>
    </head>
    <@header.body>
        <main>
            <div class="row valign-wrapper full-height">
                <div class="col l4 offset-l4 m6 offset-m3 s10 offset-s1">
                    <div class="card" id="card-login">
                        <div class="card-content">
                            <div class="card-title">
                                <div id="logo-container" class="center-align"><@header.logo "logo" ""/></div>
                            </div>
                            <form action="<@s.url '/login'/>" method="post" onsubmit="return validateLoginForm()">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="username" value="Default">

                                <div class="row no-margin-bottom">
                                    <div class="input-field col s12">
                                        <input id="login-password" type="password" name="password">
                                        <label for="login-password">${locale.getString("login.password")}</label>
                                    </div>
                                </div>

                                <#if isError??>
                                    <div class="row no-margin-bottom">
                                        <div class="col s12 center-align">
                                            <table class="${redTextColor} login-message no-border-table">
                                                <tr>
                                                    <td><i class="material-icons">warning</i></td>
                                                    <td id="loginMessage">${locale.getString("warning.wrong.password")}</td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </#if>

                                <#if isLogout??>
                                    <div class="row no-margin-bottom">
                                        <div class="col s12 center-align">
                                            <table class="${greenTextColor} login-message no-border-table">
                                                <tr>
                                                    <td><i class="material-icons">info_outline</i></td>
                                                    <td id="loginMessage">${locale.getString("logout.success")}</td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </#if>

                                <div class="row login-margin-top">
                                    <div class="col s12 right-align">
                                        <@header.buttonSubmit name='action' icon='send' localizationKey='login.button'/>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <!--  Scripts-->
        <#import "helpers/scripts.ftl" as scripts>
        <@scripts.scripts/>
        <script>document.cookie = "currentDate=${dateService.getDateStringNormal(currentDate)}";</script>
    </@header.body>
</html>