const http = require('http');
var crypto = require("crypto");

tests = 1000;
for (var i = 0; i < tests; i++)
{
   let key = 1;
   let value = i + 1;
   let mock = true;
   let sync = false;
   http.get(`http://localhost:8080/mt/hello?key=${key}&value=${value}&mock=${mock ? "on" : "off"}&sync=${sync ? "on" : "off"}`, (resp) => {
      let data = '';
      resp.on('data', (chunk) => {
        data += chunk;
      });
      resp.on('end', () => {
        console.log(data);
      });
   }).on("error", (err) => {
      console.log("Error: " + err.message);
   });
}