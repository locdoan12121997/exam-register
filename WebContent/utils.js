function send(myUrl, myType, jsondata){
    var isSucceed;
    $.ajax({
        url: 	myUrl,
        type:	myType,
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
    return isSucceed;
}