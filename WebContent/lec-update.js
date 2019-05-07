const urlParams = new URLSearchParams(window.location.search);
const lecturerId = urlParams.get('lecturerId');

var lectureraccount = new XMLHttpRequest();
var lectadd;

lectureraccount.open('GET','http://localhost:8080/exam-register/rest/lecturers',true);
lectureraccount.responseType = 'text';
lectureraccount.send();
console.log(lectureraccount.status);
lectureraccount.onload = function(){
    if (lectureraccount.status === 200){
        
        lectadd = JSON.parse(lectureraccount.responseText);
        console.log(lectadd);


        $('#edit').click(function(){

            

        	var jsondata = {
        		'firstName':    $('#f-name').val(),
        		'userName':     $('#username').val(),
        		'password':      $('#password').val(),
                'lastName':     $('#l-name').val()
        	};
        	console.log(jsondata);
        	
        	
        	send('http://localhost:8080/exam-register/rest/lecturers/'+ lecturerId, 'PUT', jsondata);
            
        })

        $('#create').click(function(){

            

            var jsondata = {
                'firstName':    $('#f-name').val(),
                'userName':     $('#username').val(),
                'password':     $('#password').val(),
                'lastName':     $('#l-name').val()
            };
            console.log(jsondata);
            
            
            send('http://localhost:8080/exam-register/rest/lecturers', 'POST', jsondata);
            
        })

    }
}
