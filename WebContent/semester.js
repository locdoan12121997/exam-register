
var semesterInfo = new XMLHttpRequest();
var semObj;

semesterInfo.open('GET','http://localhost:8080/exam-register/rest/semesters',true);
semesterInfo.responseType = 'text';
semesterInfo.send(null);

semesterInfo.onload = function(){
    if (semesterInfo.status === 200){
        
        semObj = JSON.parse(semesterInfo.responseText);
        console.table(semObj);
        
        var col = [];
        //CREATE A VARIABLE TO STORE ID INDEX
        var ID_index;
        
        for (var i = 0; i < semObj.length; i++) {
            for (var key in semObj[i]) {
                
                
                if (key === "id"){
                    ID_index = col.lastIndexOf(key);
                }
                
                if (col.indexOf(key) === -1) {
                    
                    col.push(key);

                }
            }
        }
        //create dynamic event
        function dynamicEvent(){
            document.location.href = "module.html?semesterId=" + this.className;
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
        for (var i = 0; i < semObj.length; i++) {
            

            tr = table.insertRow(-1);
            
            for (var j = 0; j < col.length; j++) {
                var tabCell = tr.insertCell(-1); 
                tabCell.innerHTML = semObj[i][col[j]];
                
                tabCell.className = semObj[i].id;
                tabCell.onclick = dynamicEvent;                
            }
            // CREATE 2 Button
            
           //EDIT
           var btn1 = document.createElement('button');
           btn1.innerHTML ="Edit";
           btn1.className= "button";
           //btn1.onlick= ;
           tr.appendChild(btn1);

            //DELETE
           var btn2 = document.createElement('button');
           btn2.innerHTML ="Delete";
           btn2.className= "button";
           //btn2.onclick= ;
           tr.appendChild(btn2);
        }

        // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
        var divContainer = document.getElementById("showData");
        divContainer.innerHTML = "";
        divContainer.appendChild(table);
    }
}
    

