var sessionstart = new XMLHttpRequest();
var session;

var modulestart = new XMLHttpRequest();
var modules;

const urlParams = new URLSearchParams(window.location.search);
const moduleId = urlParams.get('moduleId');

modulestart.open('GET','http://localhost:8080/exam-register/rest/modules',true);
modulestart.responseType = 'text';
modulestart.send(null);

sessionstart.open('GET','http://localhost:8080/exam-register/rest/modules/' + moduleId + '/sessions',true);
sessionstart.responseType = 'text';
sessionstart.send(null);

console.log(sessionstart.status);
sessionstart.onload = function(){
		if (sessionstart.status === 200){
        modules = JSON.parse(modulestart.responseText);
        session = JSON.parse(sessionstart.responseText);

        console.log(modules);
        console.log(session);

        document.getElementById('module-name').innerHTML = modules[moduleId].name;
        document.getElementById('sessionDate').innerHTML = session[moduleId].sessiondate;
        document.getElementById('sessionStart').innerHTML = session[moduleId].fromtime;
        document.getElementById('sessionEnd').innerHTML = session[moduleId].totime;
        
    }
}
