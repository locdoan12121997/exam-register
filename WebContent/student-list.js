
var std_examInfo = new XMLHttpRequest();
var std_examObj;

std_examInfo.open('GET','http://localhost:8080/exam-register/rest/modules/1/students',true);
std_examInfo.responseType = 'text';
std_examInfo.send(null);

std_examInfo.onload = function(){
    if (std_examInfo.status === 200){
        
        std_examObj = JSON.parse(std_examInfo.responseText);
        console.table(std_examObj);
        
        var col = [];
        //CREATE A VARIABLE TO STORE ID INDEX
        var ID_index;
        
        for (var i = 0; i < std_examObj.length; i++) {
            for (var key in std_examObj[i]) {
                
                
                if (col.indexOf(key) === -1){
                    col.push(key);

                }
            }
        }
        // CREATE DYNAMIC TABLE.
        var table = document.createElement("table");
        //add class for Styling with Bulma
        $(document).ready(function(){
            $("table").addClass("table is-hoverable is-bordered is-fullwidth");
        });


        // CREATE HTML TABLE HEADER ROW USING THE EXTRACTED HEADERS ABOVE.
        
        var tr = table.insertRow(-1);                   // TABLE ROW.

        for (var i = 0; i < col.length; i++) {
            var th = document.createElement("th");      // TABLE HEADER.
            th.innerHTML = col[i];
            tr.appendChild(th);
        }

        // ADD JSON DATA TO THE TABLE AS ROWS.
        for (var i = 0; i < std_examObj.length; i++) {
            

            tr = table.insertRow(-1);

            for (var j = 0; j < col.length; j++) {
                var tabCell = tr.insertCell(-1); 
                tabCell.innerHTML = std_examObj[i][col[j]];

                tabCell.className = std_examObj[i].id;
                console.log(tabCell.className);
                tabCell.addEventListener('click', function(){ alert(tabCell.className);}, false);
                
            }
        }

        // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
        var divContainer = document.getElementById("showData3");
        divContainer.innerHTML = "";
        divContainer.appendChild(table);
        }
    
        

    }
    

