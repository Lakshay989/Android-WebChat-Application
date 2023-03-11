"use strict" ;

console.log("http://localhost:8080") ;

let ws = new WebSocket("ws://localhost:8080") ;

let wsOpen = false ;

let ListOfUsers = document.getElementsByTagName('nav'); // for appending the list of users
let MainChat    = document.getElementsByTagName('article') ; // for updating the main window where the text and other things pop up
console.log(MainChat);
console.log(ListOfUsers);
let username      = document.getElementById("username") ;
let roomname      = document.getElementById("roomname") ;
let message       = document.getElementById("message") ;
let serverresponse = null;

console.log(username.value) ;
console.log(roomname.value) ;


function handleOpen(event)
{
    //alert("[open] Socket connection estabilished") ;
    wsOpen = true ;
    console.log(event) ;
    //alert("Sending to server") ;
   // ws.send("join <username> <roomname>") ;
}

function handleError()
{
    console.log("Web Socket Error") ;
}


function handleClose()
{
    console.log("Web Socket Closed") ;
}


function handleMessage( event )
{
    //console.log(event.data) ;
    //let msg = event.data

    console.log(event.data) ;
    serverresponse = event.data;
    let paragraph   = document.createElement('p') ;
    let paragraph1  = document.createElement('p') ;

    let response  = JSON.parse(serverresponse)
    console.log(response) ;


    if(response.type == "join" )
    {
    // serverresponse = event.data;
    // console.log(msg)
    // let paragraph   = document.createElement('p') ;
    // let paragraph1  = document.createElement('p') ;

    // paragraph1 = response.user ;

    // let response  = JSON.parse(serverresponse)
        paragraph.innerHTML = response.user + " has joined the " + response.room ;
        paragraph1.innerHTML = response.user;
        paragraph1.setAttribute("class",response.user);
        MainChat[0].appendChild(paragraph) ;
        ListOfUsers[0].appendChild(paragraph1)
    }

    if(response.type == "leave")
    {
        paragraph.innerHTML =  response.user + " has left the " + response.room ;
        //paragraph1.innerHTML = response.user;
        MainChat[0].appendChild(paragraph) ;
        //MainChat[0].removeChild(paragraph) ;
        let p = document.getElementsByClassName(response.user);
        console.log(p);
        ListOfUsers[0].removeChild(p[0])
        // MainChat[0].appendChild("Please close this tab")

    }


    if(response.type == "message")
    {
        paragraph.innerHTML = response.user + ": " + response.message ;
        MainChat[0].appendChild(paragraph) ;
    }
    //console.log(event.data["user"]+"has joined the room "+event.data["room"])
    //alert("username  <The message being sent>") ;
}

function handleKeyPressedCB( event )
{
  //console.log(event.keyCode);

  if (event.keyCode == 13)
  {
    let room = roomname.value;
    //console.log(event.data);
    event.preventDefault() ;
    for(let ch of room)
    {
        if(ch < 'a' || ch >'z')
        {
            alert("The room should have lowercase and no space !") ;
            return ;
        }
    }
    if (ws.OPEN && roomname.value != null && username.value != null)
    {
        // console.log(username.value);
        // console.log(roomname.value) ;
        // console.log(ws);
        ws.send("join "+username.value+" "+roomname.value)  ;
    }
    else
    {
        alert("The websocket is not open") ;
    }

  }
}

function handleMessageKeyPressedCB(event)
{
    console.log("in message");
    console.log(event);
    if (event.keyCode == 13)
    {
        //console.log(event);
        console.log(message.value);
        if(ws.OPEN && roomname.value != null && username.value != null && message.value!= null)
        {
            ws.send(username.value + " " + message.value )
        }
    }
    console.log(event.data) ;
}

function handleLeave(event) 
{
    console.log(event.data)
    if(event.type == "click")
    {
        ws.send("leave " +username.value+" "+roomname.value) ;
    }
}

let button   = document.getElementById("Leave") ;

username.addEventListener("keypress" , handleKeyPressedCB ) ;
roomname.addEventListener("keypress" , handleKeyPressedCB ) ;
message.addEventListener("keypress" , handleMessageKeyPressedCB ) ;
button.addEventListener("click" , handleLeave ) ;


ws.onopen = handleOpen ;
ws.onmessage = handleMessage ;
ws.onerror = handleError ;
ws.onclose = handleClose ;
