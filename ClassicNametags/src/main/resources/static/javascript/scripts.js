
window.onload = function(){
    document.getElementById("preview").onclick = function() {val()};

}



async function pullColor(){
	var colorId = document.getElementById("itemColor").value;
	var response = await fetch("/APIs/Colors/"+colorId);
	var data = await response.json();
	var background = data["BGColor"];
	var letters = data["lColor"];
	console.log(data["BGColor"]);
	if(data["BGColor"] == "Yellow"){
		background = "gold"
	}
	if(data["BGColor"] == "Gold"){
		background = "goldenrod"
	}
	
	
	$("#previewNameTag").css("color", letters);
	$("#previewNameTag").css("background-color", background);
	$("#previewNameTag").css("border", "solid 1px " + letters);
}

async function line1get(){
	line1Text = document.getElementById("line1entry").value;
	$("#previewLine1").text(line1Text);
}

async function line2get(){
	line2Text = document.getElementById("line2entry").value;
	$("#previewLine2").text(line2Text);
}
