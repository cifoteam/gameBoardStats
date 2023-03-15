/**
* This function hides the table rows that don't match the search query
*/
function filterRows(inputId, tableId, fieldNum) {
    // Declare variables
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById(inputId);
    filter = input.value.toUpperCase();
    table = document.getElementById(tableId);
    tr = table.getElementsByTagName("tr");

    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
        // Avoid filtering the heade
        if (tr[i].getAttribute("class") != "header") {
            // Filter by the fieldNum field
            td = tr[i].getElementsByTagName("td")[fieldNum];
            if (td) {
              txtValue = td.textContent || td.innerText;
              if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
              } else {
                tr[i].style.display = "none";
              }
            }
        }
    }
}