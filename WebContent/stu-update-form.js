const urlParams = new URLSearchParams(window.location.search);
const studentId = urlParams.get('studentId');

var studentAccount = new XMLHttpRequest();
var stuadd;

studentAccount.open('GET','http://localhost:8080/exam-register/rest/students',true);
studentAccount.responseType = 'text';
studentAccount.send();
console.log(studentAccount.status);
studentAccount.onload = function(){
    if (studentAccount.status === 200){
        
        stuadd = JSON.parse(studentAccount.responseText);
        console.log(stuadd);


        $('#f-name').val(stuadd[0].firstname);
        $('#st-code').val(stuadd[0].code)  ;        
        $('#username').val(stuadd[0].username) ;
        $('#password').val(stuadd[0].userpassword) ;
        $('#l-name').val(stuadd[0].lastname);
        /*btn click to update*/
        $('#edit').click(function(){

            

        	var jsondata = {
        		'firstName':    $('#f-name').val(),
        		'code':         $('#st-code').val(),
        		'userName':     $('#username').val(),
        		'password': $('#password').val(),
                'lastName':     $('#l-name').val()
        	};
        	console.log(jsondata);
        	
        	
        	send('http://localhost:8080/exam-register/rest/students/'+ studentId, 'PUT', jsondata);
            
        })

        $('#create').click(function(){

            

            var jsondata = {
                'firstName':    $('#f-name').val(),
                'code':         $('#st-code').val(),
                'userName':     $('#username').val(),
                'password': $('#password').val(),
                'lastName':     $('#l-name').val()
            };
            console.log(jsondata);
            
            
            send('http://localhost:8080/exam-register/rest/students', 'POST', jsondata);
            
        })

    }
}
