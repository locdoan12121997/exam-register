//Exam INFO
var examInfo = new XMLHttpRequest();
var examObj;

const urlParams = new URLSearchParams(window.location.search);
const moduleId = urlParams.get('moduleId');
const semesterId = urlParams.get('semesterId');

examInfo.open('GET','http://localhost:8080/exam-register/rest/modules/' + moduleId + '/exam',true);
examInfo.responseType = 'text';
examInfo.send(null);

function toSession(){
	document.location.href = "session.html" +  "?semesterId=" + semesterId + "&moduleId=" + moduleId;
}

function toModule(){
	document.location.href = "module.html" +  "?semesterId=" + semesterId;
}
function toSemester(){
	document.location.href = "semester.html";
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

var examInfo2 = new XMLHttpRequest();
var examObj2;
//exam list by module ID    
examInfo2.open('GET','http://localhost:8080/exam-register/rest/modules/'+ moduleId + '/examList',true);
examInfo2.responseType = 'text';
examInfo2.send(null);

examInfo2.onload = function(){
    if (examInfo2.status === 200){
        
        examObj2 = JSON.parse(examInfo2.responseText);
        console.table(examObj2);
        
        var col = [];
        //CREATE A VARIABLE TO STORE ID INDEX
        var ID_index;
        
        for (var i = 0; i < examObj2.length; i++) {
            for (var key in examObj2[i]) {
                
                
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
        for (var i = 0; i < examObj2.length; i++) {
            

            tr = table.insertRow(-1);

            for (var j = 0; j < col.length; j++) {
                var tabCell = tr.insertCell(-1); 
                tabCell.innerHTML = examObj2[i][col[j]];

                tabCell.className = examObj2[i].id;
                console.log(tabCell.className);
                tabCell.addEventListener('click', function(){ alert(tabCell.className);}, false);
                
            }
        }

        // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
        var divContainer = document.getElementById("showData2");
        divContainer.innerHTML = "";
        divContainer.appendChild(table);
        }
    }
    

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
    



    

    //SHOW HIDE
    function div_show() {
        document.getElementById("formContainer").style.display = "block";
      }
      //Function to Hide Popup
      function div_hide() {
        document.getElementById("formContainer").style.display = "none";
      }
