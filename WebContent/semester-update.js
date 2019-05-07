const urlParams = new URLSearchParams(window.location.search);
const semesterId = urlParams.get('semesterId');

var semester = new XMLHttpRequest();
var sem;

semester.open('GET','http://localhost:8080/exam-register/rest/semesters',true);
semester.responseType = 'text';
semester.send();
console.log(semester.status);
semester.onload = function(){
    if (semester.status === 200){
        
        sem = JSON.parse(semester.responseText);
        console.log(sem);


        $('#edit').click(function(){

            

        	var jsondata = {
        		'fromDate':    $('#f-name').val(),
        		'toDate':     $('#username').val()

        	};
        	console.log(jsondata);
        	
        	
        	send('http://localhost:8080/exam-register/rest/semesters/'+ semesterId, 'PUT', jsondata);
            
        })

        $('#create').click(function(){

            

            var jsondata = {
                'fromDate':    $('#f-name').val(),
                'toDate':     $('#username').val()
            };
            console.log(jsondata);
            
            
            send('http://localhost:8080/exam-register/rest/semesters', 'POST', jsondata);
            
        })

    }
}
