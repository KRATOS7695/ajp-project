Mousetrap.bind('n', function()
{
    if(areHotKeysEnabled())
    {
        window.location.href = rootURL + '/transactions/newTransaction/normal';
    }
});

Mousetrap.bind('t', function()
{
    if(areHotKeysEnabled())
    {
        window.location.href = rootURL + '/transactions/newTransaction/transfer';
    }
});

Mousetrap.bind('v', function()
{
    if(areHotKeysEnabled())
    {
        window.location.href = rootURL + '/templates';
    }
});

Mousetrap.bind('f', function()
{
    if(areHotKeysEnabled())
    {
        window.location.href = rootURL + '/transactions#modalFilter';
    }
});

Mousetrap.bind('s', function(e)
{
    if(areHotKeysEnabled())
    {
        document.getElementById('search').focus();
        e.preventDefault();
    }
});

Mousetrap.bind('esc', function()
{
    if(isSearchFocused())
    {
        document.getElementById('nav-logo-container').focus();
    }
});

Mousetrap.bind('o', function()
{
    if(areHotKeysEnabled())
    {
        window.location.href = rootURL + '/transactions';
    }
});

Mousetrap.bind('a', function()
{
    if(areHotKeysEnabled())
    {
        document.getElementById('globalAccountSelect').click();
    }
});

let saveTransactionOrTemplateButton = document.getElementById('button-save-transaction');
if(saveTransactionOrTemplateButton !== null)
{
    Mousetrap(document.querySelector('body')).bind('mod+enter', function(e)
    {
        document.getElementById('button-save-transaction').click();
    });
}

function areHotKeysEnabled()
{
    return !isSearchFocused() && !isCustomSelectFocused()  && !isTemplateSearchFocused();
}

function isSearchFocused()
{
    let searchElement = document.getElementById('search');
    return document.activeElement === searchElement;
}

function isTemplateSearchFocused()
{
    let templateSearchElement = document.getElementById('searchTemplate');
    return document.activeElement === templateSearchElement;
}

function isCustomSelectFocused()
{
   let customSelects = document.querySelectorAll('.custom-select');
    for(let i = 0; i < customSelects.length; i++)
    {
        if(customSelects[i].classList.contains('open'))
        {
            return true;
        }
    }

    return false;
}
