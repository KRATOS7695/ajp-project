$(document).ready(function()
{
    M.Modal.init(document.getElementById("modalDate"), {
        onOpenStart: function f()
        {
            cleanup();
        }
    });

    $("#global-datepicker-previous").click(function()
    {
        updateYearGrid(-11, document.getElementById('currentYear').innerHTML);
    });

    $("#global-datepicker-next").click(function()
    {
        updateYearGrid(11, document.getElementById('currentYear').innerHTML);
    });

    $("#global-datepicker-select-year .global-datepicker-item").click(function()
    {
        selectYear(this.innerText.substr(2, 2));
    });

    $("#global-datepicker-select-month .global-datepicker-item").click(function()
    {
        let month = $("#global-datepicker-select-month .global-datepicker-item").index(this) + 1
        month = String(month).padStart(2, 0);
        selectMonth(month);
    });

    enableGlobalDatePickerHotKeys();
});

let year;


function cleanup()
{
    year = undefined;
    $("#global-datepicker-select-month").hide();
    $("#global-datepicker-select-year").show();
}

function updateYearGrid(modifier, currentYear)
{
    $("#global-datepicker-select-year").fadeOut(200, function()
    {
        let items = $("#global-datepicker-select-year .global-datepicker-item");
        let firstYear = parseInt(items[0].innerText);
        let newFirstYear = firstYear + modifier;

        for(let i = 0; i < items.length; i++)
        {
            items[i].innerText = newFirstYear + i;
            if(items[i].innerText === currentYear)
            {
                items[i].classList.add("global-datepicker-selected");
            }
            else
            {
                items[i].classList.remove("global-datepicker-selected");
            }
        }

        $("#global-datepicker-select-year").fadeIn(200);
    });
}

function selectYear(selectedYear)
{
    year = selectedYear;
    $("#global-datepicker-select-year").fadeOut(200, function()
    {
        $("#global-datepicker-select-month").fadeIn(200);
    });
}

function selectMonth(selectedMonth)
{
    let dateString = "01." + selectedMonth + "." + year;
    document.cookie = "currentDate=" + dateString;
    document.getElementById('buttonChooseDate').click();
}

function enableGlobalDatePickerHotKeys()
{
    Mousetrap.bind('left', function()
    {
        if(areHotKeysEnabled())
        {
            document.getElementById('global-datepicker-previous-month').click();
        }
    });

    Mousetrap.bind('right', function()
    {
        if(areHotKeysEnabled())
        {
            document.getElementById('global-datepicker-next-month').click();
        }
    });

    Mousetrap.bind('0', function()
    {
        if(areHotKeysEnabled())
        {
            document.getElementById('global-datepicker-today').click();
        }
    });
}
