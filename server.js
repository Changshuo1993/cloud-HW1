var http = require('http');
var fs = require('fs');
var elasticsearch = require('elasticsearch');



function send404Response(response) {
    response.writeHead(404, {"Content-Type": "text/plain"});
    response.write("Error 404: Page Not Found");
    response.end();
}

function onRequest(request, response) {
    if (request.method == 'GET' && request.url == '/') {
        response.writeHead(200, {"Content-Type": "text/html"});
        fs.createReadStream("./twittermap.html").pipe(response);
    }else {
        send404Response(response);
    }
}
function searchKey(keyword, socket,data) {
    var client = new elasticsearch.Client({
        host: 'localhost:9200',
        log: 'trace'
    });

    client.search({
        index: keyword,
        body: {
            "from" : 0,
            "size" : 400
        }
    }).then(function (resp) {
        var hits = resp.hits.hits;
        var result = [];
        for (var i = 0; i<hits.length; i++) {
            console.log(hits[i]);
            var longtitude = hits[i]._source.longtitude;
            var latitude = hits[i]._source.latitude;
            var item = {lat: longtitude, lng: latitude, usr:hits[i]._source.user, txt:hits[i]._source.text, ul:hits[i]._source.url };

            result.push(item);
        }
        console.log(result);
        socket.emit('marks',{message:result, id:data.id});
    }, function (err) {
        socket.emit('error', err.message);
    });
}
var app = http.createServer(onRequest);
console.log("server working...");

app.listen(8888);
var io = require('socket.io').listen(app);
io.on('connect',function(socket){
    socket.emit('welcome', { message: 'welcome!', id: socket.id });

    socket.on('keypass',function(data){   
        var key = data.message;
        searchKey(key,socket,data);
    });

});