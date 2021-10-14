const http = require('http');
var crypto = require("crypto");

tests = 200;
for (var i = 0; i < tests; i++)
{
   let key = crypto.randomBytes(5).toString('hex');
   let value = Math.floor(Math.random() * 10 + 1);
   let mock = i % 2 == 0;
   let sync = i % 3 == 0;
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