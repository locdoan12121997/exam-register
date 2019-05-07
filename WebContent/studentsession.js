
var sessionsInfo = new XMLHttpRequest();
var sessionsObj;

const urlParams = new URLSearchParams(window.location.search);
const moduleId = urlParams.get('moduleId');
const studentId = urlParams.get('studentId');

sessionsInfo.open('GET','http://localhost:8080/exam-register/rest/students/' + studentId + '/modules/' + moduleId + '/sessions',true);
sessionsInfo.responseType = 'text';
sessionsInfo.send(null);
function toExam(){
	document.location.href = "studentexam.html?studentId=" + studentId + "&moduleId=" + moduleId;
}
function toModule(){
	document.location.href = "studentmodule.html?studentId=" + studentId ;
}
sessionsInfo.onload = function(){
    if (sessionsInfo.status === 200){
        
        sessionsObj = JSON.parse(sessionsInfo.responseText);
        console.table(sessionsObj);
        
        var col = [];
        //CREATE A VARIABLE TO STORE ID INDEX
        var ID_index;
        
        for (var i = 0; i < sessionsObj.length; i++) {
            for (var key in sessionsObj[i]) {
                
                
                if (key === "id"){
                    ID_index = col.indexOf(key);
                    
                }
                
                if (col.indexOf(key) === -1) {
                    
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
        for (var i = 0; i < sessionsObj.length; i++) {
            

            tr = table.insertRow(-1);

            for (var j = 0; j < col.length; j++) {
                var tabCell = tr.insertCell(-1); 
                tabCell.innerHTML = sessionsObj[i][col[j]];

                tabCell.className = sessionsObj[i][col[ID_index]];
                console.log(tabCell.className);
                tabCell.addEventListener('click', function(){ alert(tabCell.className);}, false);
                
            }
        }

        // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
        var divContainer = document.getElementById("showData");
        divContainer.innerHTML = "";
        divContainer.appendChild(table);
        }
    
        

    }
    

