
var modulesInfo = new XMLHttpRequest();
var moduleObj;

console.log("ASDAD");
const urlParams = new URLSearchParams(window.location.search);
const studentId = urlParams.get('studentId');

modulesInfo.open('GET','http://localhost:8080/exam-register/rest/students/' + studentId + '/modules',true);
modulesInfo.responseType = 'text';
modulesInfo.send(null);

modulesInfo.onload = function(){
    if (modulesInfo.status === 200){
        
        moduleObj = JSON.parse(modulesInfo.responseText);
        console.table(moduleObj);
        
        var col = [];
        //CREATE A VARIABLE TO STORE ID INDEX
        var ID_index;
        for (var i = 0; i < moduleObj.length; i++) {
        	var count = -1;
        	console.log(moduleObj[i]);
            for (var key in moduleObj[i]) {
               
            	
            	if (key === "id"){
                	
                    ID_index = col.lastIndexOf(key);
                    console.log(key + " " + count + " " + ID_index);
                }
                
                if (col.indexOf(key) === -1) {
                    col.push(key);
                }
                count ++;
            }
        }
      //create dynamic event
        function dynamicEvent(){
            document.location.href = "studentsession.html" + "?studentId=" + studentId + "&moduleId=" +  this.className;
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
        for (var i = 0; i < moduleObj.length; i++) {
            

            tr = table.insertRow(-1);

            for (var j = 0; j < col.length; j++) {
                var tabCell = tr.insertCell(-1); 
                tabCell.innerHTML = moduleObj[i][col[j]];

                tabCell.className = moduleObj[i].id;e
                tabCell.onclick = dynamicEvent;
            }
        }

        // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
        var divContainer = document.getElementById("showData");
        divContainer.innerHTML = "";
        divContainer.appendChild(table);
        }
    
        

}
    

