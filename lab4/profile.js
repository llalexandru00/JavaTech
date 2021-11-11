const http = require('http');
var crypto = require("crypto");

tests = 1000;
for (var i = 0; i < tests; i++)
{
   http.get(`http://localhost:8080/mt4/faces/allexams.xhtml`, (resp) => {
      let data = '';
      resp.on('data', (chunk) => {
        data += chunk;
      });
      resp.on('end', () => {
        // console.log(data);
      });
   }).on("error", (err) => {
      console.log("Error: " + err.message);
   });
   http.get(`http://localhost:8080/mt4/faces/allstuds.xhtml`, (resp) => {
      let data = '';
      resp.on('data', (chunk) => {
        data += chunk;
      });
      resp.on('end', () => {
        // console.log(data);
      });
   }).on("error", (err) => {
      console.log("Error: " + err.message);
   });
}