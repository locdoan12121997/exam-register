//Exam INFO
var examInfo = new XMLHttpRequest();
var examObj;

const urlParams = new URLSearchParams(window.location.search);
const moduleId = urlParams.get('moduleId');
const studentId = urlParams.get('studentId');

examInfo.open('GET','http://localhost:8080/exam-register/rest/students/' + studentId + '/modules/' + moduleId + '/exam',true);
examInfo.responseType = 'text';
examInfo.send(null);

function toSession(){
	document.location.href = "studentsession.html?studentId=" + studentId + "&moduleId=" + moduleId;
}
function toModule(){
	document.location.href = "studentmodule.html?studentId=" + studentId ;
}

examInfo.onload = function(){
    if (examInfo.status === 200){
        
        examObj = JSON.parse(examInfo.responseText);
        console.table(examObj);
        
        var col = [];
        //CREATE A VARIABLE TO STORE ID INDEX
        var ID_index;
        
        for (var i = 0; i < examObj.length; i++) {
            for (var key in examObj[i]) {
                
                
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
        for (var i = 0; i < examObj.length; i++) {
            

            tr = table.insertRow(-1);

            for (var j = 0; j < col.length; j++) {
                var tabCell = tr.insertCell(-1); 
                tabCell.innerHTML = examObj[i][col[j]];

                tabCell.className = examObj[i].id;
                tabCell.addEventListener('click', function(){ alert(tabCell.className);}, false);
                
            }
        }

        // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
        var divContainer = document.getElementById("showData");
        divContainer.innerHTML = "";
        divContainer.appendChild(table);
        }
    
        

    }
    

    //SHOW HIDE
    function div_show() {
        document.getElementById("formContainer").style.display = "block";
      }
      //Function to Hide Popup
      function div_hide() {
        document.getElementById("formContainer").style.display = "none";
      }
