var studentAccount = new XMLHttpRequest();
var stuadd;

studentAccount.open('GET','http://localhost:8080/exam-register/rest/students',true);
studentAccount.responseType = 'text';
studentAccount.send();
console.log(studentAccount.status);
studentAccount.onload = function(){
    if (studentAccount.status === 200){
        
        stuadd = JSON.parse(studentAccount.responseText);
        console.log("stuadd" + stuadd.toString());

        $('#f-name').val(stuadd[0].firstname);
        $('#l-name').val(stuadd[0].lastname);
        $('#st-code').val(stuadd[0].code)  ;
        $('#username').val(stuadd[0].username) ;
        $('#password').val(stuadd[0].userpassword) ;

        /*btn click to update*/
        $('#btn').click(function(){

        	/*var fname = $('f-name').val();
        	var lname = $('l-name').val();
        	var stcode = $('st-code').val();
        	var username = $('username').val();
        	var password = $('password').val();*/

        	var fname = 'hi';
        	var lname = 'he';
        	var stcode = 'he';
        	var username = 'he';
        	var password = 'he';


        	var jsondata = {
        		'firstName': $('#f-name').val(),
        		'lastName': $('#l-name').val(),
        		'code':$('#st-code').val(),
        		'userName':$('#username').val(),
        		'password':$('#password').val()
        	};
        	console.log("jsondata: " + JSON.stringify(jsondata));
        	
        	

        	send('/exam-register/rest/students/1', 'PUT', jsondata);
        	/*
        	$.ajax({
        		url: 	'/exam-register/rest/students/1',
        		type:	'PUT',
   				data: JSON.stringify(jsondata),
   				headers: { 
   			        'Accept': 'application/json',
   			        'Content-Type': 'application/json' 
   			    },
   				
   				success: function (data, text) {
   			        alert("Ok");
   			    },
   			    error: function (request, status, error) {
   			        alert(request.responseText);
   			    }
        	})
        	console.log(stuadd[0]);*/
        })

    }
}
